package main;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginPage {
	GridPane gp;
	Scene sc;
	Label judul, mail, pass;
	TextField email;
	PasswordField password;   
	Button loginBtn, regBtn;
	HBox btnHB;
	Stage stage;
	
	public void start() {
		gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(5);
		    
		//	  title and grid pane
		judul = new Label("Jee Keyboard Store");
		Font font = Font.font("Arial", FontWeight.EXTRA_BOLD, 35);
		judul.setFont(font);
		gp.add(judul, 0, 0);
		GridPane.setColumnSpan(judul, 3);
		GridPane.setHalignment(judul, HPos.CENTER);
		      
		//	  email
		mail = new Label("Email  ");
		email = new TextField();
		gp.add(mail, 0, 1);
		gp.add(email, 1, 1);
		    
		//	  password
		pass = new Label("Password  ");
		password = new PasswordField();
		gp.add(pass, 0, 2);
		gp.add(password, 1, 2);
		  
		//	  button
		loginBtn = new Button("Login");
		regBtn = new Button("Register");
		  
		loginBtn.setMinWidth(100);
		regBtn.setMinWidth(100);
		loginBtn.setStyle("-fx-background-color: #515151;");
		loginBtn.setTextFill(Color.WHITE);
		regBtn.setStyle("-fx-background-color: #515151;");
		regBtn.setTextFill(Color.WHITE);
		 
		//	  Button HBox
		btnHB = new HBox();
		btnHB.getChildren().addAll(regBtn, loginBtn);
		btnHB.setSpacing(5);
		gp.add(btnHB, 1, 4);
		
		// Listeners
		loginBtn.setOnAction((e) -> {
		    String enteredEmail = email.getText();
		    String enteredPassword = password.getText();
		
		    if (enteredEmail.isEmpty() && enteredPassword.isEmpty()) {
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Login Failed");
		        alert.setHeaderText(null);
		        alert.setContentText("All fields can't be empty");
		        alert.show();
		    }else if (enteredEmail.isEmpty()) {
			    Alert alert = new Alert(Alert.AlertType.ERROR);
			    alert.setTitle("Login Failed");
			    alert.setHeaderText(null);
			    alert.setContentText("Email can't be empty");
			    alert.show();
		    }else if (enteredPassword.isEmpty()) {
			    Alert alert = new Alert(Alert.AlertType.ERROR);
			    alert.setTitle("Login Failed");
			    alert.setHeaderText(null);
			    alert.setContentText("Password can't be empty");
			    alert.show();
		    }
		    // admin
		    else if (enteredEmail.equals("admin") && enteredPassword.equals("admin")) {
		    	new AdminPage(stage);
		    }
		    else {
	            boolean loginSuccessful = false;
	            for (User user : UserContainer.getInstance().getUsers()) {
	                if (user.getEmail().equals(enteredEmail) && user.getPassword().equals(enteredPassword)) {
	                    loginSuccessful = true;
	                    break;
	                }
	            }

	            if (loginSuccessful) {
	            	TransactionContainer.getInstance().setLoggedIn(enteredEmail);
	            	new UserPage(stage);
	            } else {
	            	Alert alert = new Alert(Alert.AlertType.ERROR);
	            	alert.setTitle("Login Failed");
	            	alert.setHeaderText(null);
	            	alert.setContentText("User doesn't exist");
	            	alert.show();
	            }
		    }
		});
		    
		regBtn.setOnMouseClicked((e) -> {
			new RegisterPage(stage);
		});
		sc = new Scene(gp, 720, 500);
	}
	public LoginPage(Stage stage) {
		this.stage = stage;
		start();
		stage.setScene(sc);
		stage.setTitle("Login");
		stage.getIcons().add(new Image("/logo.png"));
		stage.show();
	}
}
