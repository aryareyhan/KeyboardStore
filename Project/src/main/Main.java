package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Add a dummy user
		UserContainer.getInstance().addUser("user@gmail.com", "user123");
		
		// add keyboards
		KeyboardContainer.getInstance().addKeyboard("Igoltech Keebs XO200","800000","56","Classic black and yellow combination with modern architecture.");
		KeyboardContainer.getInstance().addKeyboard("Dark Black RGB","1500000","116","RGB LED has come to this futuristic keyboard with elegant style.");
		KeyboardContainer.getInstance().addKeyboard("Watermelon Keebs Z200","750000","9","Did you ever seen watermelon in keyboard? Now you see!");
		KeyboardContainer.getInstance().addKeyboard("Igoltech Classic Keebs","1250000","56","Business keyboard with modern design make your style cool.");
		
		// test transaction
    	TransactionContainer.getInstance().addTransaction("user@gmail.com");
        
        // tambahin ke database
//        for (Transaction trans : TransactionContainer.getInstance().getTransactions()) {
//        if (trans.getUser().equals("user@gmail.com")) {
//            trans.addKeyboard("Igoltech Keebs XO200");
//            trans.addKeyboard("Igoltech Keebs XO200");
//            trans.addKeyboard("Watermelon Keebs Z200");
//        }
//        }
        
		new LoginPage(primaryStage);
	}
}
