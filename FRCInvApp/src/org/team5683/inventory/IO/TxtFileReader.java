package org.team5683.inventory.IO;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.team5683.inventory.CRUD.InsertProducts;
import org.team5683.inventory.MODEL.Product;

public class TxtFileReader{
	private BufferedReader br;
	//constructor
	public TxtFileReader(String file) throws FileNotFoundException{
		br = new BufferedReader(new InputStreamReader(ResourceLoader.load(file)));
	}

	//read comma separated values into database
	public void readInsrt(Connection con) throws NumberFormatException, IOException, SQLException{
		
		br.readLine(); //read first line (titles) to omit from insert
		while(br.ready()){
			String [] line = br.readLine().split(",");
			
			Product product = new Product.ProductBuilder(line[1])
					.setName(line[2])
					.setProdDescr(line[3])
					.setSrcId(line[4])
					.setImgPath(line[5])
					.setMyImgPath(line[6])
					.setPrice(line[7])
					.setInStock(line[8])
					.setNeededStock(line[9])
					.setLocation(line[10])
					.createProduct();	
		  product.prodToStringArray(product);
	      new InsertProducts(con, product).insertProduct(); 	
		}		
	}
}
