package org.team5683.inventory.Main;
	
import javafx.application.Application;
import javafx.stage.Stage;
import org.team5683.inventory.CRUD.SQLController;
import org.team5683.inventory.View.MainView;

public class Main extends Application {
	static int run = 0;
	@Override
	public void start(Stage primaryStage) throws Exception {
		SQLController sql = new SQLController();
		//sql.deleteTables();
		sql.createTables();		
		new MainView().prodTable(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
