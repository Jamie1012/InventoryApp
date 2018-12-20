package org.team5683.inventory.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TxtFileWriter {
	private String logfile;
	File file = null;
	TxtFileReader txtFile;
	FileWriter fileWriter;
	PrintWriter printWriter; 
		
	public TxtFileWriter(String logfile) throws IOException{
		this.logfile = logfile;
		file = new File(this.logfile);
		//create backup file if it doesn't exist
		if(!file.exists() && this.logfile.equals("./Resources/data/backup.csv")){file.createNewFile();}
		txtFile = new TxtFileReader(this.logfile);	    
		fileWriter = new FileWriter(this.logfile, false); 
	    printWriter = new PrintWriter(fileWriter);
	}
	
	public void printDoc(ResultSet rs3) throws IOException, SQLException {	
    	printWriter.append("id,prod_id,prod_name,prod_descr"
    			+ ",source,img_path,my_img_path,"
    			+ "price_dbl,in_stock, needed_stock, location");
    	printWriter.write("\n");
    	
		while(rs3.next()){
			String str = String.valueOf(rs3.getInt(1)) + "," //id
					+ rs3.getString(2) + "," //prod_id
	        		+ rs3.getString(3) + "," //prod_name
					+ rs3.getString(4) + "," //prod_descr 
	        		+ rs3.getString(5) + "," //source 
	        		+ rs3.getString(6) + "," //img_path
	        		+ rs3.getString(7) + "," //my_img_path 
	        		+ String.valueOf(rs3.getDouble(8)) + ","  //price
	        		+ String.valueOf(rs3.getDouble(9)) + ","  //in_stock 
	        		+ String.valueOf(rs3.getDouble(10)) + "," //needed_stock
	        		+ rs3.getString(11); //location
			printWriter.append(str);
			printWriter.write("\n");
		}
		printWriter.close();
	}


	public void emptyDoc() throws IOException{
		FileWriter fileEmptier = new FileWriter(logfile);
		PrintWriter printEmptier = new PrintWriter(fileEmptier);
		printEmptier.print("");
		printEmptier.close();
	}
}
