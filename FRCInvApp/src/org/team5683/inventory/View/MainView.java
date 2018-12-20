package org.team5683.inventory.View;

import java.io.InputStream;

import org.team5683.inventory.IO.ResourceLoader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainView {
	public MainView(){} 

	public void prodTable(Stage primaryStage) throws Exception {
		
			Parent root = FXMLLoader.load(getClass().getResource("/org/team5683/inventory/FXML/MainFXML.fxml"));
			FXMLLoader.load(getClass().getResource("/org/team5683/inventory/FXML/MainFXML.fxml"));

			// set icon
			InputStream input = ResourceLoader.load("Imgs/world.jpg");
    		primaryStage.getIcons().add(new Image(input));
	        //Image Copyright: <a href='https://www.123rf.com/profile_1xpert'>1xpert / 123RF Stock Photo</a>
	      
	        // TODO: Customize for teams
	        primaryStage.setTitle("FRC Inventory");			
			Scene scene = new Scene(root, 1200, 680);
			scene.getStylesheets().add(getClass().getResource("/org/team5683/inventory/CSS/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();	
	}
 }
    
