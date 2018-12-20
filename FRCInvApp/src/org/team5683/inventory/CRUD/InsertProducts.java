package org.team5683.inventory.CRUD;
import java.io.IOException;
/*
 * @author: Katie Markham
 * @date: 10/7/2018
 * CRUD: Create
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.team5683.inventory.MODEL.Product;
//import org.team5683.inventory.Main.Ex;

public class InsertProducts {
    private ArrayList<Statement> statements = new ArrayList<Statement>(); 
	private PreparedStatement psInsert;
	private String prod_id ="null"; 
	private String prod_name="null"; 
	private String prod_descr="null";
	private int src_id = 0;
	private String img_path="null"; 
	private String my_img_path="null"; 
	private Double price=0.0; 
	private Double in_stock=0.0; 
	private Double needed_stock=0.0;
	private String location = "null";
	private Connection con;
	
	//constructor for inserting
	public InsertProducts(Connection con, Product product) throws SQLException, IOException{
		this.con = con;
		this.con.setAutoCommit(false);
		//TODO Check against database before inserting.
		this.prod_id = product.getProdId(); 
		this.prod_name = product.getName(); 
		this.prod_descr = product.getProdDescr(); 
		this.src_id = product.getSrcId();
		this.img_path = product.getImgPath(); 
		this.my_img_path = product.getMyImgPath();
		this.price = product.getPrice();
		this.in_stock = product.getInStock(); 
		this.needed_stock = product.getNeededStock();
		this.location = product.getLocation();
	}
	
public void insertProduct() throws SQLException{	
	//search database for matching product id
	ResultSet rs1 = new SelectProducts(con).oneByProdId(prod_id);	
	if (!rs1.next()) {//if no match found insert record
			psInsert = con.prepareStatement(
				       "insert into products (prod_id, prod_name, prod_descr"
				       + ", src_id, img_path, my_img_path, price, in_stock, "
				       + "needed_stock, location) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statements.add(psInsert);					
			psInsert.setString(1,this.prod_id);
			psInsert.setString(2,this.prod_name);
			psInsert.setString(3,this.prod_descr);
			psInsert.setInt(4,this.src_id);
			psInsert.setString(5,this.img_path);
			psInsert.setString(6,this.my_img_path);
			psInsert.setDouble(7,this.price);
			psInsert.setDouble(8,this.in_stock);
			psInsert.setDouble(9,this.needed_stock);
			psInsert.setString(10,this.location);
			psInsert.executeUpdate();
			this.con.commit();	
	}	
		//Commit the transaction for be persisted to the database.
        this.con.commit();  
	}
 }

