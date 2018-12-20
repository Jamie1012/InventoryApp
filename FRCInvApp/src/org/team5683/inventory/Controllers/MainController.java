package org.team5683.inventory.Controllers;

import org.team5683.inventory.CRUD.SQLController;
import org.team5683.inventory.MODEL.Product;
import org.team5683.inventory.MODEL.Product.ProductBuilder;
import org.team5683.inventory.MODEL.ProductImage;
import org.team5683.inventory.MODEL.ProductList;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class MainController implements Initializable {
	@FXML
	private RightController rightController;
	private final ObjectProperty<Product> item = new SimpleObjectProperty<>();
	private final StringProperty errorMessage = new SimpleStringProperty();
	
	@FXML
	ObservableList<Product> items;

	private String errorMsg = "";
	private String lastSelected = "";
	// top-panel search bar
	@FXML
	private TextField keywordText;
	@FXML
	private Button keywordBtn;
	// main-panel table
	@FXML
	private TableView<Product> tvProducts;
	@FXML
	private TableColumn<Product, String> prodIdCol;
	@FXML
	private TableColumn<Product, ProductImage> imgCol;
	@FXML
	private TableColumn<Product, String> nameCol;
	@FXML
	private TableColumn<Product, String> priceCol;
	@FXML
	private TableColumn<Product, String> inStockCol;
	@FXML
	private TableColumn<Product, String> neededStockCol;
	@FXML
	private TableColumn<Product, String> srcCol;
	
	public MainController() {
	} // constructor

	// update table on search button press
	@FXML
	private void updateSearch(ActionEvent event) throws SQLException, IOException {
		getTable(getProductList());
	}

	@Override
	public void initialize(URL location, ResourceBundle Resources) {
		
		// bind selected item to smallForm item
		rightController.itemProperty().bind(item);
		
		// bind error message to smallForm errorMsg
		rightController.errorMessageProperty().bind(errorMessage);

		try {
			getTable(getProductList());
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set action on commit for search bar
		keywordText.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					getTable(getProductList());
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// update table when smallForm is used to add, update or delete item
		// listen for change to string property in smallForm
		// update table on change
		rightController.refreshProperty().addListener(new ChangeListener<String>() {
			@FXML
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					getTable(getProductList());
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		rightController.fetchProperty().addListener(new ChangeListener<String>() {
			@FXML
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(newValue);
				lastSelected = newValue;
				try {
					getTable(getProductList());
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// populate table with observable list
	void getTable(ObservableList<Product> olist) {

		prodIdCol.setEditable(false);
		prodIdCol.setCellValueFactory(new PropertyValueFactory<>("prodId"));

		imgCol.setCellValueFactory(new PropertyValueFactory<>("Thumb"));
		imgCol.setEditable(false);
	
		nameCol.setEditable(true);
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
			@Override
			public void handle(CellEditEvent<Product, String> t) {
				Product p = getThisProduct(t.getRowValue());
				ProductBuilder pb = new ProductBuilder(p.getProdId());
				pb.setName(t.getNewValue());
				try {
					updateDB(pb.createProduct());
				} catch (SQLException | IOException e) {
					errorMsg += "Problem Updating: " + p.getProdId() + "\n";
				}
			}
		});

		priceCol.setEditable(true);
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		priceCol.setCellValueFactory(new PropertyValueFactory<>("PriceStr"));
		priceCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
			@Override
			public void handle(CellEditEvent<Product, String> t) {
				Product p = getThisProduct(t.getRowValue());
				ProductBuilder pb = new ProductBuilder(p.getProdId());
				pb.setPrice(t.getNewValue());
				try {
					updateDB(pb.createProduct());
				} catch (SQLException | IOException e) {
					errorMsg += "Problem Updating: " + p.getProdId() + "\n";
				}
			}
		});

		inStockCol.setEditable(true);
		inStockCol.setCellFactory(TextFieldTableCell.forTableColumn());
		inStockCol.setCellValueFactory(new PropertyValueFactory<>("inStockStr"));
		inStockCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
			@Override
			public void handle(CellEditEvent<Product, String> t) {
				Product p = getThisProduct(t.getRowValue());
				ProductBuilder pb = new ProductBuilder(p.getProdId());
				pb.setInStock(t.getNewValue());
				try {
					updateDB(pb.createProduct());
				} catch (SQLException | IOException e) {
					errorMsg += "Problem Updating: " + p.getProdId() + "\n";
				}
			}
		});

		neededStockCol.setEditable(true);
		neededStockCol.setCellFactory(TextFieldTableCell.forTableColumn());
		neededStockCol.setCellValueFactory(new PropertyValueFactory<>("neededStockStr"));
		neededStockCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
			@Override
			public void handle(CellEditEvent<Product, String> t) {
				Product p = getThisProduct(t.getRowValue());
				ProductBuilder pb = new ProductBuilder(p.getProdId());
				pb.setNeededStock(t.getNewValue());
				try {
					updateDB(pb.createProduct());
				} catch (SQLException | IOException e) {
					errorMsg += "Problem Updating: " + p.getProdId() + "\n";
				}
			}
		});

		srcCol.setCellValueFactory(new PropertyValueFactory<>("srcIdStr"));

		// add items to table
		tvProducts.getItems().setAll(olist);

		// listen for table item selection change
		// display errorMsg in place of smallForm if errorMsg exists
		// update item displayed in smallForm
		// update image displayed in imgDetail
		TableViewSelectionModel<Product> tvSelProduct = tvProducts.getSelectionModel();
		tvSelProduct.selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@FXML
			public void changed(ObservableValue<? extends Number> changed, Number oldVal, Number newVal) {
				int index;
				// update index
				index = newVal.intValue();
				
				if (index >= 0) { // if an item is selected
					item.set(tvSelProduct.getSelectedItem()); // set item
					//errorMsg += item.get().getMyImgPath(); //TODO Get image paths for adding them
					lastSelected = item.get().getProdId(); // track item as last selected
					errorMessage.set(errorMsg);
					rightController.setForm();
					errorMsg="";
				} else {
					rightController.setForm(); // displays smallForm if item != null
				}
			}
		});

		// keep last selected in-focus on update
		if (!lastSelected.isEmpty()) { // if an item was selected
			String prod_id;
			// loop through table
			for (int index = 0; index < tvProducts.getItems().size(); index++) {
				prod_id = tvProducts.getItems().get(index).getProdId();
				if (prod_id.equals(lastSelected)) { // match prod_id to last
													// selected
					tvProducts.getSelectionModel().select(index); // select row
					tvProducts.scrollTo(index); // scroll to row
				}
			}
		}
	}

	// TODO: Check if this is getThisProduct is really needed
	// returns creates and returns a product object
	private Product getThisProduct(Product p) {
		Product product = new Product.ProductBuilder(p.getProdId()).setName(p.getName()).setProdDescr(p.getProdDescr())
				.setSrcId(p.getSrcIdStr()).setImgPath(p.getImgPath()).setMyImgPath(p.getMyImgPath())
				.setPrice(p.getPriceStr()).setInStock(p.getInStockStr()).setNeededStock(p.getNeededStockStr())
				.setLocation(p.getLocation()).createProduct();
		return product;
	}
	
	private ObservableList<Product> getProductList() throws SQLException, IOException{
		if(!keywordText.getText().isEmpty()){ 	
			items = new ProductList(keywordText.getText()).getProductList();
		}
		else{
			items = new ProductList("all").getProductList();
		}
		return items;
	}
	
	private void updateDB(Product p) throws SQLException, IOException {
		new SQLController().updateProduct(p); // update product in db
		getTable(getProductList());// re-load table
		keywordText.clear();
	}

}
