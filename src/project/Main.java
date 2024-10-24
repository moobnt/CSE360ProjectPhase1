package project;

import java.sql.JDBCType;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.stage.Stage;
import project.DatabaseModel;

import project.OneTimePassPage;

public class Main extends Application {
	DatabaseModel databaseModel;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		User user = new User();
		databaseModel = new DatabaseModel();
		databaseModel.connect();	
//		databaseModel.dropUserTable();
		
//		LoginService login = new LoginService(primaryStage, user, databaseModel);
		Admin login = new Admin(primaryStage, user, databaseModel);
	}
	
}


