package org.team5683.inventory.MODEL;

import java.io.IOException;
import java.sql.SQLException;

import org.team5683.inventory.CRUD.SQLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductList {
	private String key;
//	private ObservableList<Product> olist;
	public ProductList() {
		this.key = "all";
	}
	public ProductList(String key){
		this.key = key;
	}
	
	// get list to populate table use keyword to narrow results
	private ObservableList<Product> productList() throws SQLException, IOException {
		ObservableList<Product> olist = FXCollections.observableArrayList();
		Product noProduct = new Product.ProductBuilder("###").setName("NO RESULTS").createProduct();
		olist.add(noProduct);

		if (key.equals("all") | key.isEmpty()) {
			olist.removeAll();
			olist = new SQLController().allProd();
		} else {
			olist.removeAll();
			olist = new SQLController().keywordSearch(key);
		}
		return olist;
	}
		
	public ObservableList<Product> getProductList() throws SQLException, IOException{
		return productList();
	}
}
