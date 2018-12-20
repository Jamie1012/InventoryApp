package org.team5683.inventory.MODEL;
import java.io.IOException;
import java.io.InputStream;

import org.team5683.inventory.IO.ResourceLoader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductImage{
	private ImageView imageView;
    private Image img;
	private InputStream input;
    
    public ProductImage(String myImgPath) throws IOException {	
    	
    	try {
    		input = ResourceLoader.load(myImgPath);
    		img = new Image(input); 		
    	}
    	catch(Exception e){
    		//e.printStackTrace();
    		try {
    			myImgPath="Resources/Imgs/nopic1.jpg";
    			input = ResourceLoader.load(myImgPath);
    			img = new Image(input);
        	}
        	catch(Exception e1){
        		img = null;
        	} 	
    	} 	
    	finally{if(input!=null){input.close();}}
    	imageView = new ImageView(img);       
    	imageView.setPreserveRatio(true);
    }
    
    public Image getImg(){
		return img;
	}

    public ImageView getImageView() {
        return imageView;
    }
    
    public ImageView getThumb(){
    	imageView.setFitHeight(80);
    	return imageView;
    }
}