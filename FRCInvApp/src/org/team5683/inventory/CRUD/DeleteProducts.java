package org.team5683.inventory.CRUD;
/*
 * @author: Katie Markham
 * @date: 10/7/2018
 * CRUD: Delete
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeleteProducts {
	private ArrayList<Statement> statements = new ArrayList<Statement>(); 
	private Connection con;
	private ResultSet rs;
    private PreparedStatement psDelete;   
	private int ID = 0;
	private String prod_id="";

	//TODO create deleteAllProducts for use in restore
	
	//constructor for deleting given int id
	public DeleteProducts(Connection con, int id) throws SQLException{
		rs = null;//result set
		this.con = con;//connection
		this.con.setAutoCommit(false);
		ID = id;
	}
	//constructor for deleting given String product id
	public DeleteProducts(Connection con, String prod_id){
		rs = null;//result set
		this.con = con;//connection
		this.prod_id = prod_id;
	}
	//delete record from product table
	public String deleteProduct() throws SQLException{
		String delStr = null;
		if(ID == 0){ //checks for empty ID & retrieves by product id
			rs = new SelectProducts(this.con).oneByProdId(this.prod_id);
			if(rs.next()){ 
				ID = rs.getInt(1);
				delStr = "Deleted Product: " + rs.getString(2) + " " + rs.getString(3);
				}
		}
		else{
			rs = new SelectProducts(this.con).oneById(ID);	
			if(rs.next()){
				delStr = "Deleted Product: " + rs.getString(2) + " " + rs.getString(3);
				}
		}	    
		psDelete = this.con.prepareStatement("DELETE FROM products WHERE id = ?");
		statements.add(psDelete);	
		psDelete.setInt(1, ID);
		psDelete.executeUpdate();	
		this.con.commit();
		return delStr; //returns record deleted
	}
	
}
