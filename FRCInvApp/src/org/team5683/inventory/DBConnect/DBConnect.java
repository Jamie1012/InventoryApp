package org.team5683.inventory.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
	private Connection con;
	private Properties props;
	private String dbName;
	private String protocol;
	private String framework;
	public DBConnect(){
    	con = null;
        /* the default framework is embedded */
        framework = "embedded";
        protocol = "jdbc:derby:";
        props = new Properties(); // connection properties
        props.put("user", "user1");
        props.put("password", "user1");
        dbName = "inventoryDB"; // the name of the database
	}
	public Connection connection() throws SQLException{	
        con = DriverManager.getConnection(protocol + dbName
            + ";create=true", props);         
        //Connection
        return con;
    }
	   //Close Connection
	public void closeConnection(){
	       try {
				if (con!= null) {
				    con.close();
				    dbName = null;
				}
			} catch (SQLException sqle) {
				Error.printSQLException(sqle);
			}

		   if (framework.equals("embedded"))
		    {
		        try
		        {
		            // the shutdown=true attribute shuts down Derby
		            DriverManager.getConnection("jdbc:derby:;shutdown=true");
		        }
		        catch (SQLException se)
		        {
			        if (
		        		((se.getErrorCode() == 50000) && ("XJ015".equals(se.getSQLState())))
		        		|((se.getErrorCode()== 45000) && ("08006".equals(se.getSQLState())))
			        	) {
			            // we got the expected exception
		                System.out.println("Derby shut down normally");
		
		            } else {
		                // if the error code or SQLState is different, we have
		                // an unexpected exception (shutdown failed)
		                System.err.println("Database did not shut down normally");
		                Error.printSQLException(se);
		            }
		        }
		    }
		}
}
