package org.team5683.inventory.SETUP;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.team5683.inventory.IO.TxtFileReader;

public class ProductsTable {
    /* Creating a statement object that we can use for running various
     * SQL statements commands against the database.*/
    Statement s;
    ArrayList<Statement> statements = new ArrayList<Statement>(); 
    Connection connection;
    public ProductsTable(Connection connection){
    	this.connection = connection;
    }
    
	public void createProductsTable(String file) throws SQLException{
				
	    s = connection.createStatement();
	    
	    // Try to create the table & insert from file. 
	    try{
	    statements.add(s);
	   
	    // Create products table
	    s.execute("CREATE TABLE products("
    		+ "id int NOT NULL PRIMARY KEY "
    		+ "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
    		+ "prod_id varchar(30), "
    		+ "prod_name varchar(256), "
    		+ "prod_descr varchar(2500), "
			+ "src_id int, "
			+ "img_path varchar(256), "
			+ "my_img_path varchar(256), "
			+ "price double, "
			+ "in_stock double, "
			+ "needed_stock double, "
			+ "location varchar(256) "
			+ ")");
	    
	    	// Try to insert data from file.
			try {
				new TxtFileReader(file).readInsrt(connection);
			} catch (NumberFormatException | IOException e1) {
				throw e1;
			}
	    }
	    catch(Exception e){
	    //	e.printStackTrace();
	    }
	    
	    // Connection is closed in SQLController
	 }
	
	public void DropTable() throws SQLException{
	    s = connection.createStatement();    
	    // Delete the table
        s.execute("drop table products");	
        
        // Connection is closed in SQLController
	}		
}
	
