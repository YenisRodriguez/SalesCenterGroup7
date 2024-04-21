package CentralSalesCenterOperations;

import java.io.FileReader;
import java.io.*;
import java.util.*;

public class GenerateSalesReport {

	public static void main(String[] args) {
		// Create an ArrayList object
		ArrayList<String> numsSeller = new ArrayList<String>(); 
		// Create an ArrayList object
		ArrayList<String> numsSellerOrder = new ArrayList<String>(); 
		// Create an ArrayList object
		ArrayList<String> numsProduct = new ArrayList<String>(); 
		// Create an ArrayList object
		ArrayList<String> numsProductOrder = new ArrayList<String>(); 
		// Crear un mapa para almacenar el id y el precio del producto
        Map<String, Integer> productsPrice = new HashMap<>();
		// Crear un mapa para almacenar el nombre del vendedor
        Map<String, String> namesSeller = new HashMap<>();
		// Crear un mapa para almacenar el nombre del vendedor y su cantidad de ventas desordenado
        Map<String, Integer> sellerSales = new HashMap<>();
		// Crear un mapa para almacenar el nombre del vendedor y su cantidad de ventas ordenado
        Map<String, Integer> sellerSalesOrder = new HashMap<>();
		// Crear un mapa para almacenar el nombre del vendedor y su cantidad de ventas desordenado
        Map<String, Integer> productSales = new HashMap<>();
		// Crear un mapa para almacenar el nombre del vendedor y su cantidad de ventas ordenado
        Map<String, Integer> productSalesOrder = new HashMap<>();
		// Crear un mapa para almacenar el nombre del vendedor
        Map<String, String> namesProduct = new HashMap<>();
		// Leer archivos de ventas y actualizar el mapa
        try {
        	// Declaramos e inicializamos el contador
        	int counterSeller = 0;
        	BufferedReader brSeller = new BufferedReader(new FileReader("SalesMenInfo.txt"));
        	String lineSeller;
        	lineSeller = brSeller.readLine();
            while ((lineSeller = brSeller.readLine()) != null) {
            	String[] parts = lineSeller.split(";");
            	System.out.println(parts[0]);
            	// Incrementamos el contador si corresponde
            	counterSeller++; 
            	//Variable del id del vendedor
            	String idSeller = parts[1];
            	numsSeller.add(idSeller);
            	//Variable del nombre del vendedor
            	String nameSeller = parts[2] + parts[3];
            	//Guardar el nombre del vendedor en el diccionario
            	namesSeller.put(idSeller, nameSeller);
            	
            }
         // Declaramos e inicializamos el contador
        	int counterProduct = 0;
        	BufferedReader brProduct = new BufferedReader(new FileReader("ProductsInfo.txt"));
        	String lineProduct;
        	lineProduct = brProduct.readLine();
            while ((lineProduct = brProduct.readLine()) != null) {
            	String[] parts = lineProduct.split(";");
            	System.out.println(parts[0]);
            	// Incrementamos el contador si corresponde
            	counterProduct++; 
            	String idProduct = parts[0];
            	numsProduct.add(idProduct);
                int price = Integer.parseInt(parts[2]);
            	// Guardamos precio por producto en el diccionario
            	productsPrice.put(idProduct, price);
            	productSales.put(idProduct, 0);
            	//Variable del nombre del product
            	String nameProduct = parts[1];
            	//Guardar el nombre del vendedor en el diccionario
            	namesProduct.put(idProduct, nameProduct);
            	
            	
            }
            for (int i = 0; i < counterSeller; i++) {
            	BufferedReader brOrder = new BufferedReader(new FileReader("Orders/Order0" + i + ".txt"));
            	String lineOrder;
            	lineOrder = brOrder.readLine();
            	String[] parts = lineOrder.split(";");
            	String numSeller = parts[1];
            	int totalSales = 0;
            	int totalProduct = 0;
            	while ((lineOrder = brOrder.readLine()) != null) {
                	parts = lineOrder.split(";");
                	System.out.println(parts[0]);
                	String productsId = parts[0];
                	if (!productsId.equals("0")) {
                		int productAmount = Integer.parseInt(parts[1]);
                    	int productPrice = productsPrice.get(productsId);
                    	int moneyAmount = productPrice * productAmount;
                    	totalProduct = productSales.get(productsId) + productAmount;
                    	productSales.put(productsId, totalProduct);
                    	// Incrementamos el total de las ventas del vendedor
                    	totalSales += moneyAmount;
                    	
                	
                }            	
                
            }
            // Guardamos el total ventar por vendedor en el diccionario
            sellerSales.put(numSeller,totalSales);
        	}
            	
            //Ordenar
            int max = 0;
            String id = "";
            for (int i = 0; i < counterSeller; i++) {
            	for (int j = 0; j < counterSeller; j++) {
            		String idSeller = numsSeller.get(j);

                	if (sellerSales.containsKey(idSeller)) {
                		int totalSales = sellerSales.get(idSeller);
	            		if (totalSales > max) {
	            			max = totalSales;
	            			id = idSeller;
	            		}
                	}
                }
            	numsSellerOrder.add(id);
            	sellerSalesOrder.put(id, max);
            	sellerSales.remove(id);
            	max = 0;

            }

            // Escribir el reporte  de vendedores en un archivo CSV
            BufferedWriter bwSeller = new BufferedWriter(new FileWriter("SalesReportInfo.csv"));
            for (int i = 0; i < counterSeller; i++) {
            	String idSeller = numsSellerOrder.get(i);
            	String nameSeller = namesSeller.get(idSeller);
            	if (sellerSalesOrder.containsKey(idSeller)) {
            	System.out.println( idSeller);
            	int totalSales = sellerSalesOrder.get(idSeller);
            	bwSeller.write(nameSeller + ";" + totalSales + "\n");       	
            	}
            }
            //Se cierra el reporte de ventas
            bwSeller.close();
            
        	
            //Ordenar products
            max = 0;
            id = "";
            for (int i = 0; i < counterProduct; i++) {
            	for (int j = 0; j < counterProduct; j++) {
            		String idProduct = numsProduct.get(j);

                	if (productSales.containsKey(idProduct)) {
                		int totalProduct = productSales.get(idProduct);
	            		if (totalProduct > max) {
	            			max = totalProduct;
	            			id = idProduct;
	            		}
                	}
                }
            	numsProductOrder.add(id);
            	productSalesOrder.put(id, max);
            	productSales.remove(id);
            	max = 0;

            }             

            // Escribir el reporte  de productos  en un archivo CSV
            BufferedWriter bwProduct = new BufferedWriter(new FileWriter("ProductsReportInfo.csv"));
            for (int i = 0; i < counterProduct; i++) {
            	String idProduct = numsProductOrder.get(i);
            	String nameProduct = namesProduct.get(idProduct);
            	if (productSalesOrder.containsKey(idProduct)) {
            	System.out.println( idProduct);
            	int totalProduct = productSalesOrder.get(idProduct);
            	bwProduct.write(nameProduct + ";" + totalProduct + "\n");       	
            	}
            }
            //Se cierra el reporte de ventas
            bwProduct.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
	
	
}
