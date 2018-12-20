package org.team5683.inventory.CRUD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.team5683.inventory.DBConnect.DBConnect;
import org.team5683.inventory.IO.TxtFileReader;
import org.team5683.inventory.IO.TxtFileWriter;
import org.team5683.inventory.MODEL.Product;
import org.team5683.inventory.SETUP.ProductsTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQLController {
	
	//constructor
	public SQLController(){}
	
    //insert records from .csv file 
    public void insertFromFile(String file) throws NumberFormatException, IOException, SQLException{
    	Connection con = new DBConnect().connection();
		new TxtFileReader(file).readInsrt(con);
		con.close();
    }
    
    //TODO validate data entered for fields
    //insert record by direct entry
    public void newProduct(Product product) throws SQLException, IOException{
    	Connection con = new DBConnect().connection();
    	new InsertProducts(con, product).insertProduct();
    	con.close();   	
	}
    
    //return ObservableList products selected by keyword match name | description | product id
    public ObservableList<Product> keywordSearch(String keyword) throws SQLException, IOException{     
    	//System.out.println("keywordSearch");
    	Connection con = new DBConnect().connection();
    	ResultSet rs = new SelectProducts(con).getByKeyword(keyword);
    	ObservableList<Product> p = FXCollections.observableArrayList();
		while(rs.next()) {
			//System.out.println(rs.getString(2));
			Product product = new Product.ProductBuilder(rs.getString(2))
					.setId(String.valueOf(rs.getInt(1)))
					.setName(rs.getString(3))
					.setProdDescr(rs.getString(4))
					.setSrcId(String.valueOf(rs.getInt(5)))
					.setImgPath(rs.getString(6))
					.setMyImgPath(rs.getString(7))
					.setPrice(String.valueOf(rs.getDouble(8)))
					.setInStock(String.valueOf(rs.getDouble(9)))
					.setNeededStock(String.valueOf(rs.getDouble(10)))
					.setLocation(rs.getString(11))
				.createProduct();
		p.add(product);	
		}
		if(p.isEmpty()){
			Product product = new Product.ProductBuilder("###")
				.setName("SEARCH RETURNED NO RESULTS")
				.createProduct();
			p.add(product);
		}
		con.close();
		return p;
    }

    // returns ObservableList: all products 
    public ObservableList<Product> allProd() throws SQLException, IOException{
    	Connection con = new DBConnect().connection();
		ResultSet rs = new SelectProducts(con).getAll();
		ObservableList<Product> p = FXCollections.observableArrayList();
		while(rs.next()) {
			Product product = new Product.ProductBuilder(rs.getString(2))
				.setId(String.valueOf(rs.getInt(1)))	
				.setName(rs.getString(3))
				.setProdDescr(rs.getString(4))
				.setSrcId(String.valueOf(rs.getInt(5)))
				.setImgPath(rs.getString(6))
				.setMyImgPath(rs.getString(7))
				.setPrice(String.valueOf(rs.getDouble(8)))
				.setInStock(String.valueOf(rs.getDouble(9)))
				.setNeededStock(String.valueOf(rs.getDouble(10)))
				.setLocation(rs.getString(11))
			.createProduct();				
			p.add(product);		
		}
		con.close();
		return p;
    }
    //print to file: backup all files
    public void backUp() throws SQLException, IOException{
    	Connection con = new DBConnect().connection();
		ResultSet rs = new SelectProducts(con).getAll();
    	new TxtFileWriter("./Resources/data/backup.csv").printDoc(rs);
    	con.close();
    }

    //TODO Finish & Test UpdateProduct
    public void updateProduct(Product product) throws SQLException, IOException {
		Connection con;
		con = new DBConnect().connection();
		new UpdateProducts(con, product).updateByID(); 	
		con.close();
	}
    
    //delete record by prod_id
    public String deleteByProdId(String pi) throws SQLException{
    	Connection con = new DBConnect().connection();
    	String del = new DeleteProducts(con, pi).deleteProduct();
    	con.close();
    	if(del != null){ return del; }
    	else{ return "Record doesn't exist"; }
    	
    }
    //delete record by id
    public String deleteById(int id) throws SQLException {
    	Connection con = new DBConnect().connection();
    	String del = new DeleteProducts(con, id).deleteProduct();
    	con.close();
    	
    	if(del != null){ return del; }
    	else{ return "Record doesn't exist"; }	
     }    
    
    //TODO Make Setup & Restore Actions: When should app create & drop tables
    //create sql tables
    public void createTables() throws SQLException{ 	
    	Connection con = new DBConnect().connection(); 
    	DatabaseMetaData dbInfo = con.getMetaData(); // Get MetaData to test if table exists	
    	
    	//If PRODUCTS table does not exist create table (will load data from file) 
    	ResultSet rs = dbInfo.getTables("inventoryDB", null, "PRODUCTS", null); 	
    	if(!rs.next()) { new ProductsTable(con).createProductsTable("/data/allproducts.csv"); }
    	con.close();
    }
    
    // Drop product table
    public void deleteTables() throws SQLException{
    	Connection con = new DBConnect().connection();
    	DatabaseMetaData dbInfo = con.getMetaData(); // Get MetaData to test if table exists	
    	
    	// If PRODUCTS table exists drop table
    	ResultSet rs = dbInfo.getTables("inventoryDB", null, "PRODUCTS", null); 	
    	if(rs.next()) { new ProductsTable(con).DropTable(); }
    	con.close();
    } 
}
