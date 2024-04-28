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
		// Create a map to store the product id and price
        Map<String, Integer> productsPrice = new HashMap<>();
		// Create a map to store the seller name
        Map<String, String> namesSeller = new HashMap<>();
		// Create a map to store the seller's name and their sales amount out of order
        Map<String, Integer> sellerSales = new HashMap<>();
		// Create a map to store the seller's name and sales amount neatly
        Map<String, Integer> sellerSalesOrder = new HashMap<>();
		// Create a map to store product name and sales amount out of order
        Map<String, Integer> productSales = new HashMap<>();
		// Create a map to store the product name and sales quantity orderly
        Map<String, Integer> productSalesOrder = new HashMap<>();
		// Create a map to store the product name
        Map<String, String> namesProduct = new HashMap<>();
		// Read sales, product and order files to update maps
        try {
        	// We declare and initialize the counter
        	int counterSeller = 0;
        	BufferedReader brSeller = new BufferedReader(new FileReader("SalesMenInfo.txt"));
        	String lineSeller;
        	lineSeller = brSeller.readLine();
            while ((lineSeller = brSeller.readLine()) != null) {
            	String[] parts = lineSeller.split(";");
            	System.out.println(parts[0]);
            	// We increment the counter if appropriate
            	counterSeller++; 
            	//Seller id variable
            	String idSeller = parts[1];
            	numsSeller.add(idSeller);
            	//Seller name variable
            	String nameSeller = parts[2] + parts[3];
            	//Save the seller's name to the dictionary
            	namesSeller.put(idSeller, nameSeller);
            	
            }
         // We declare and initialize the counter
        	int counterProduct = 0;
        	BufferedReader brProduct = new BufferedReader(new FileReader("ProductsInfo.txt"));
        	String lineProduct;
        	lineProduct = brProduct.readLine();
            while ((lineProduct = brProduct.readLine()) != null) {
            	String[] parts = lineProduct.split(";");
            	System.out.println(parts[0]);
            	// We increment the counter if appropriate
            	counterProduct++; 
            	String idProduct = parts[0];
            	numsProduct.add(idProduct);
                int price = Integer.parseInt(parts[2]);
            	// We save price per product in the dictionary
            	productsPrice.put(idProduct, price);
            	productSales.put(idProduct, 0);
            	//Product name variable
            	String nameProduct = parts[1];
            	//save the seller's name in the dictionary
            	namesProduct.put(idProduct, nameProduct);
            	
            	
            }
            //The order files that were generated randomly are reviewed.
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
                    	// We increase the seller's total sales
                    	totalSales += moneyAmount;
                    	
                	
                }            	
                
            }
            // We save the total sales per seller in the dictionary
            sellerSales.put(numSeller,totalSales);
        	}
            	
            //Sort seller csv file
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

            //Write the seller report to a CSV file
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
            //The sales report is closed
            bwSeller.close();
            
        	
            //Sort product csv file
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

            // Write the product report to a CSV file
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
            //The product report is closed
            bwProduct.close();
            
        } catch (IOException e) {
        	//try catch exception
            e.printStackTrace();
        }
        
        
    }
	
	
}
