package org.team5683.inventory.MODEL;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class Product{
	private int id;
	private String prodId;
	private String name;
	private String prodDescr;
	private int src_id;
	private String imgPath;
	private String myImgPath;
	private Double price;
	private Double inStock;
	private Double neededStock;
	private String location;
	private ImageView thumb = new ImageView();	

	private Product(){ }
	
	public int getId(){
		return id;
	}
	
	public String getProdId() {
		return prodId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getProdDescr() {
		return prodDescr;
	}
	
	public int getSrcId() {
		return src_id;
	}	
	
	public String getSrcIdStr(){
		return String.valueOf(src_id);
	}
	
	public String getImgPath() {
		return imgPath;
	}	
	
	public String getMyImgPath() {
		return myImgPath;
	}
	
	public String getPriceStr() {
		return "$" + price.toString();
	}
	
	public Double getPrice() {
		return price;
	}
	
	public Double getInStock() {	
		return inStock;
	}
	
	public String getInStockStr(){
		return inStock.toString();
	}
	
	public Double getNeededStock() {
		return neededStock;
	}		
	
	public String getNeededStockStr(){
		return neededStock.toString();
	}
	
	public String getLocation(){
		return location;
	}
	
	public ImageView getThumb() throws IOException{	
		if(!myImgPath.equals("null")){
			 ProductImage img = new ProductImage(this.myImgPath);
			 this.thumb = img.getThumb();			 
		}
		return thumb;
	}

	public static class ProductBuilder{
		private Integer id = 0;
		private String prod_id = "null";
		private String name = "null";
		private String prod_descr = "null";
		private Integer src_id = 0;
		private String img_path = "null";
		private String my_img_path = "null";
		private Double price = 0.0;
		private Double in_stock = 0.0;
		private Double needed_stock = 0.0;
		private String location = "null";
		

					
		public ProductBuilder(String prod_id){
			this.prod_id = prod_id;
		}
		
		public ProductBuilder setId(String id){
			this.id = Integer.valueOf(id);
			return this;
		}
		
		public ProductBuilder setName(String name) {
			this.name = name;
			return this;
		}	
		
		public ProductBuilder setProdDescr(String prod_descr) {
			this.prod_descr = prod_descr;
			return this;
		}
		
		public ProductBuilder setSrcId(String src_id) {
			src_id = src_id.replaceAll("[^0-9.]+","");
			try{
				this.src_id = Integer.valueOf(src_id);
			}
			catch(Exception e){
				this.src_id = 0;
			}
			return this;
		}
		
		public ProductBuilder setImgPath(String img_path) {
			this.img_path = img_path;
			return this;
		}
		
		public ProductBuilder setMyImgPath(String my_img_path) {
			//TODO Using img_path?
			this.my_img_path = my_img_path;
			
			if(this.my_img_path.equals("null") | this.my_img_path.isEmpty()){
				//TODO allow uploads	
				if(this.src_id == 2){//folder set up
						this.my_img_path = "/VexPro/" + prod_id +".jpg";
				}
				
				if(this.src_id == 1){//folder set up
					this.my_img_path = "/AndyMark/" + prod_id + ".jpg";
				}	
				
				//TODO: Add method of getting uploaded image_path
			}

			return this;	
		}
	
		public ProductBuilder setPrice(String price_txt) {
			String ptxt = price_txt.replaceAll("[^0-9.]+","");
				try{
					this.price = Double.parseDouble(ptxt);
				}
				catch(Exception e){
					this.price = 0.0;
				}
			return this;
		}
	
		public ProductBuilder setInStock(String in_stock) {
			in_stock = in_stock.replaceAll("[^0-9.]+","");
			try{
				this.in_stock = Double.parseDouble(in_stock);
			}
			catch(Exception e){
				this.in_stock = 0.0;
			}
			return this;
		}
		
		public ProductBuilder setNeededStock(String needed_stock) {
			needed_stock = needed_stock.replaceAll("[^0-9.]+","");
			try{
				this.needed_stock = Double.parseDouble(needed_stock);
			}
			catch(Exception e){
				this.needed_stock = 0.0;
			}
			return this;
		}
		
		public ProductBuilder setLocation(String location) {
			this.location = location;
			return this;
		}
		
		public Product createProduct(){
			Product product = new Product();
			product.id = this.id;
			product.prodId = this.prod_id;
			product.name = this.name;
			product.prodDescr = this.prod_descr;
			product.src_id = this.src_id; 
			product.imgPath = this.img_path;
			product.myImgPath = this.my_img_path;
			product.price = this.price;
			product.inStock = this.in_stock;
			product.neededStock = this.needed_stock;
			product.location = this.location;
			
			return product;
		}
	}
	
	 public ArrayList<String> prodToStringArray(Product prod){
		ArrayList<String> s = new ArrayList<String>();
		String str = prod.getProdId() + ","
					+ prod.getName() +  ","
					+ prod.getProdDescr() + ","
					+ prod.getSrcId() + ","
					+ prod.getImgPath() + ","
					+ prod.getMyImgPath() + ","
					+ prod.getPrice().toString() + ","
					+ prod.getInStockStr() + ","
					+ prod.getNeededStockStr() + ","
					+ prod.getLocation();
		s.add(str);
		return s;
	 }
}
