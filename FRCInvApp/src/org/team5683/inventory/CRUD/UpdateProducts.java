package org.team5683.inventory.CRUD;
import java.io.IOException;
/*
 * @author: Katie Markham
 * @date: 10/7/2018
 * CRUD: Update
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.team5683.inventory.MODEL.Product;

public class UpdateProducts{
	private ArrayList<Statement> statements=new ArrayList<Statement>(); 
	private PreparedStatement psUpdate;
	private ResultSet rs = null;	
	private int ID;
	private String prod_id = "null"; 
	private String prod_name = "null";
	private Integer src_id = 0;
	private String prod_descr = "null";
	private String img_path = "null"; 
	private String my_img_path = "null"; 
	private Double price = 0.0; 
	private Double in_stock = 0.0; 
	private Double needed_stock = 0.0;
	private String location = "null";
	private Connection con;
	
	//constructor 
	public UpdateProducts(Connection con, Product product) throws SQLException, IOException{
		
		this.con = con;
		this.con.setAutoCommit(false);
		
		 //get initial product information (before change)
		this.prod_id = product.getProdId();
		rs = new SelectProducts(this.con).getByProdId(this.prod_id);
		
		while(rs.next()){
			ID = rs.getInt(1);
			String prevN=rs.getString(3); 
			String prevD=rs.getString(4);
			Integer prevS=rs.getInt(5);
			String prevIP=rs.getString(6); 
			String prevMIP=rs.getString(7); 
			Double prevP = Double.valueOf(rs.getString(8));
			Double prevIS = Double.valueOf(rs.getString(9));
			Double prevNS = Double.valueOf(rs.getString(10));
			String prevL = rs.getString(11);
			
			//Update if new values !empty
			if(!product.getName().equals("null")){this.prod_name = product.getName();} 
			else{this.prod_name = prevN;}
			
			if(!product.getProdDescr().equals("null")){this.prod_descr = product.getProdDescr();} 
			else{this.prod_descr = prevD;}
			
			if(product.getSrcId() != 0){this.src_id = product.getSrcId();} 
			else{this.src_id = prevS;}
			
			if(!product.getImgPath().equals("null")){this.img_path = product.getImgPath();} 
			else{this.img_path = prevIP;} 
			
			if(!product.getMyImgPath().equals("null")){this.my_img_path = product.getMyImgPath();} 
			else{this.my_img_path = prevMIP;}
						
			if(product.getPrice() != 0.0){this.price = product.getPrice();}
			else{this.price = prevP;}
									
			if(product.getInStock() != 0.0){this.in_stock = product.getInStock();}
			else{this.in_stock = prevIS;};
						
			if(product.getNeededStock()!= 0.0){this.needed_stock = product.getNeededStock();}
			else{this.needed_stock = prevNS;}
						
			if(!product.getLocation().equals("null")){this.location = product.getLocation();}
			else{this.location = prevL;}
		}

	}
	
	//update product using given id 
	public void updateByID() throws SQLException{
		String query = "update products set prod_name=?, prod_descr=?, src_id=?, img_path=?,"
				+ "my_img_path=?, price=?, in_stock=?, needed_stock=?, location=?"
				+ "where ID=?";
		psUpdate = this.con.prepareStatement(query);
		statements.add(psUpdate);		
		psUpdate.setString(1, this.prod_name);
		psUpdate.setString(2, this.prod_descr);
		psUpdate.setInt(3, this.src_id);
		psUpdate.setString(4, this.img_path);
		psUpdate.setString(5, this.my_img_path);
		psUpdate.setDouble(6, this.price);
		psUpdate.setDouble(7, this.in_stock);
		psUpdate.setDouble(8, this.needed_stock);
		psUpdate.setString(9,  this.location);
		psUpdate.setInt(10, ID);
		psUpdate.executeUpdate();	
		//Commit the transaction for be persisted to the database.
        this.con.commit();
	}

	//TODO add image loader later to let user add photos?
	//TODO create new item if attempting to update to product id or source
	//TODO show product before update ... updating product: -Use getByProdID(String pi)
	//TODO show product after update...updated product: -Use getByProdID(String pi)
}
