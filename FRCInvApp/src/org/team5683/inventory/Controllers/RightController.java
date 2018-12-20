package org.team5683.inventory.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.team5683.inventory.CRUD.SQLController;
import org.team5683.inventory.MODEL.Product;
import org.team5683.inventory.MODEL.ProductImage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class RightController implements Initializable {
	@FXML
	private MainController main;
	@FXML
	private GridPane smallForm;

	// small form labels
	@FXML
	private Label fLProdId;
	@FXML
	private Label fLName;
	@FXML
	private Label fLDescr;
	@FXML
	private Label fLPrice;
	@FXML
	private Label fLLocation;
	@FXML
	private Label fLNeededStock;
	@FXML
	private Label fLInStock;
	// small form fields
	@FXML
	private TextField ffProdId;
	@FXML
	private TextArea ffName;
	@FXML
	private TextArea ffDescr;
	@FXML
	private TextField ffPrice;
	@FXML
	private TextField ffLocation;
	@FXML
	private TextField ffNeededStock;
	@FXML
	private TextField ffInStock;
	// small form buttons
	@FXML
	private Button fbNew;
	@FXML
	private Button fbAdd;
	@FXML
	private Button fbUpdate;
	@FXML
	private Button fbDelete;
	@FXML
	private Button fbCancel;
	// side panel info
	@FXML
	private ImageView imgDetail;
	@FXML
	private TextArea txtDetail;
	@FXML
	private SplitPane middleContent;
	@FXML
	private AnchorPane right;

	private final ObjectProperty<Product> item = new SimpleObjectProperty<>();
	private final StringProperty refresh = new SimpleStringProperty();
	private final StringProperty fetch = new SimpleStringProperty();
	private final StringProperty errorMessage = new SimpleStringProperty();
	private int changes = 0; // used as refresh counter
	private String myImgPath="";

	// simple string property used to refresh table in mainController
	public StringProperty refreshProperty() {
		return refresh;
	}

	// simple string property used to fetch product Id in table (mainController)
	public StringProperty fetchProperty() {
		return fetch;
	}

	public RightController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imgDetail.setVisible(false);
		txtDetail.setVisible(false);
		smallForm.setVisible(false); // hide small form
		errorMessage.get();
		setForm();
	}

	// item property from mainController
	public ObjectProperty<Product> itemProperty() {
		return item;
	}

	// errorMessage from mainController
	public StringProperty errorMessageProperty() {
		return errorMessage;
	}

	@FXML
	public void setForm() {
		// clear and hide message area
		txtDetail.clear();
		txtDetail.setVisible(false);

		Product prod = item.get(); // get current product

		if (prod != null) {
			// reset & show smallForm (grid-pane)
			fbAdd.setVisible(false);
			fbCancel.setVisible(false);
			fbNew.setVisible(true);
			fbUpdate.setVisible(true);
			fbDelete.setVisible(true);
			smallForm.setVisible(true);

			if (prod.getName().equals(prod.getProdDescr())) { // if product name=product description
				fLDescr.setVisible(false); // hide description label
				ffDescr.setVisible(false); // hide description text-area
				GridPane.setRowSpan(ffName, 2); // set name text-area to cover two rows
				ffName.setMaxHeight(ffProdId.getPrefHeight() * 2); // set name max-height to double top row
			} else { // if product name != product description
				fLDescr.setVisible(true); // show description label
				ffDescr.setVisible(true); // show description text-area
				GridPane.setRowSpan(ffName, 1); // set name text-area to cover one row
				ffName.setMaxHeight(ffProdId.getPrefHeight()); // set name max-height to match top row
			}

			// set form field text values
			ffProdId.setText(prod.getProdId());
			ffName.setText(prod.getName());
			ffDescr.setText(prod.getProdDescr());
			ffPrice.setText(prod.getPriceStr());
			if (!prod.getLocation().equals("null")) {
				ffLocation.setText(prod.getLocation());
			}
			ffInStock.setText(prod.getInStockStr());
			ffNeededStock.setText(prod.getNeededStockStr());

			// if error message is not empty: hide smallForm, show text detail,
			// show error message
			if (errorMessage.get() != null && !errorMessage.get().isEmpty()) {
				smallForm.setVisible(false);
				txtDetail.setVisible(true);
				txtDetail.setText(errorMessage.get());
			} else {
				txtDetail.setVisible(true);
				txtDetail.setText(prod.getProdDescr());
			}

			// set and show image
			try {
				Image image = new ProductImage(prod.getMyImgPath()).getImg();
				imgDetail.setImage(image);
				imgDetail.setVisible(true);
				imgDetail.setFitHeight(middleContent.getPrefHeight() * .40); // fit to divider
				if (image == null) {
					middleContent.setDividerPosition(0, 0);
				} else {
					middleContent.setDividerPosition(0, 0.40);
				}
			} catch (IOException e) {
				// System.out.println("Problem Loading Image");
			}

		} else {
			resetForm();
		} // if product == null reset form
	}

	public void resetForm() {
		// clear form fields
		ffProdId.clear();
		ffName.clear();
		ffDescr.clear();
		ffPrice.clear();
		ffLocation.clear();
		ffNeededStock.clear();
		ffInStock.clear();
	}

	private void updateDB(Product p) throws SQLException, IOException {
		new SQLController().updateProduct(p); // update product in db
	}

	private void newProductDB(Product p) throws SQLException, IOException {
		new SQLController().newProduct(p); // add product in db
	}

	private void delProductDB(String pi) throws SQLException, IOException {
		new SQLController().deleteByProdId(pi);
	}

	private Product setThisProduct(String ProdId) {
		Product product = new Product.ProductBuilder(ProdId)
				.setName(ffName.getText())
				.setProdDescr(ffDescr.getText())
				.setPrice(ffPrice.getText())
				.setInStock(ffInStock.getText())
				.setNeededStock(ffNeededStock.getText())
				.setImgPath(myImgPath)
				.createProduct();
		return product;
		// TODO: Add drop down for location		
	}
	
	// update on search-button press
	@FXML
	private void updateProduct(ActionEvent event) throws SQLException, IOException {
		try {
			Product p = setThisProduct(item.get().getProdId());
			updateDB(p);
			changes++; // update change counter
			refresh.set(String.valueOf(changes)); // change string to refresh table
		} catch (Exception e) {
			System.out.println("Problem updating item...");
		}
	}

	// reset form on new-button press
	@FXML
	private void newProduct(ActionEvent event) throws SQLException, IOException {
		resetForm(); // reset form to blank fields

		ffProdId.setEditable(true); // make product id editable
		fLDescr.setVisible(true); // show description label
		ffDescr.setVisible(true); // show description text-area
		GridPane.setRowSpan(ffName, 1); // set name text-area to cover one row
		ffName.setMaxHeight(ffProdId.getPrefHeight()); // set name max-height to match top row

		fbNew.setVisible(false);
		fbUpdate.setVisible(false);
		fbDelete.setVisible(false);

		fbAdd.setVisible(true);
		fbCancel.setVisible(true);
		smallForm.setVisible(true);
	}

	// add on add-button press
	@FXML
	private void addProduct(ActionEvent event) throws SQLException, IOException {
		if (!ffProdId.getText().isEmpty()) {
			try {
				Product p = setThisProduct(ffProdId.getText());
				newProductDB(p);
			} catch (Exception e) {
				// System.out.println("Problem getting new product to add.");
			}
		}
		changes++; // update change counter
		fetch.set(ffProdId.getText());
		refresh.set(String.valueOf(changes)); // change string to refresh table
		ffProdId.setEditable(false);
	}

	@FXML
	private void cancel(ActionEvent event) {
		changes++; // update change counter
		fetch.set(ffProdId.getText());
		refresh.set(String.valueOf(changes)); // change string to refresh table
		ffProdId.setEditable(false);
	}

	// delete on delete-button press
	@FXML
	private void deleteProduct(ActionEvent event) throws SQLException, IOException {
		try {
			delProductDB(ffProdId.getText());
			changes++; // update change counter
			refresh.set(String.valueOf(changes)); // change string to refresh table
			resetForm();
		} catch (Exception e) {
			// System.out.println("Problem deleting product...");
		}
	}
}
