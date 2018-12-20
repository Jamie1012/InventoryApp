package org.team5683.inventory.CRUD;
/*
 * @author: Katie Markham
 * @date: 10/7/2018
 * CRUD: Read
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectProducts {
	private ResultSet rs;
    private PreparedStatement psSelect;
    private String prod_id;
    private int ID;
    private Connection con;
    
    //constructor
	public SelectProducts(Connection con) throws SQLException{
		rs = null;//result set
		this.con = con;//connection
	}
	
	// get all records
	public ResultSet getAll() throws SQLException{
	    String query = "SELECT * FROM products";
		psSelect = this.con.prepareStatement(query);
		rs = psSelect.executeQuery();
	    return rs;
	}

	//get records by product id : case insensitive
	public ResultSet getByProdId(String pi) throws SQLException{
	    this.prod_id = pi;  
	    String query = "SELECT * FROM products WHERE prod_id LIKE ?";
		psSelect = this.con.prepareStatement(query);
		psSelect.setString(1, this.prod_id);
		rs = psSelect.executeQuery();
	    return rs;
	}
	
	//get one record by product id : case sensitive
	public ResultSet oneByProdId(String pi) throws SQLException{
	    this.prod_id = pi;
	    String query = "SELECT id, prod_id, prod_name  FROM products WHERE prod_id LIKE ?";
		psSelect = this.con.prepareStatement(query);
		psSelect.setString(1, this.prod_id);
		rs = psSelect.executeQuery();
	    return rs;
	}
	
	//get one record by id
	public ResultSet oneById(int id) throws SQLException{
	    this.ID = id; 
	    String query = "SELECT id, prod_id, prod_name FROM products WHERE id = ?";
		psSelect = this.con.prepareStatement(query);
		psSelect.setInt(1, this.ID);
		rs = psSelect.executeQuery();
	    return rs;
	}

	//get records by keyword
	public ResultSet getByKeyword(String keyword) throws SQLException{
	    keyword = keyword.toUpperCase();
	    String query = "SELECT * FROM products WHERE UPPER(prod_name) LIKE ? OR UPPER(prod_descr) LIKE ? OR UPPER(prod_id) LIKE ? ";
		psSelect = this.con.prepareStatement(query);
		psSelect.setString(1, "%" + keyword + "%");
		psSelect.setString(2, "%" + keyword + "%");
		psSelect.setString(3, "%" + keyword + "%");
		rs = psSelect.executeQuery();
	    return rs;
	}
}