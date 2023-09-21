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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RegisterPage {
	private boolean isEmailValid(String email) {
        // Check if email ends with '@email.com' or '@gmail.com'
        if (!email.endsWith("@email.com")&&!email.endsWith("@gmail.com")) {
            return false;
        }

        // Check if email has only one '@' character
        else if (email.indexOf('@') != email.lastIndexOf('@')) {
            return false;
        }

        // Check if email has no spaces
        else if (email.contains(" ")) {
            return false;
        }

        // Check if email does not start with '@'
        else if (email.startsWith("@")) {
            return false;
        }
        
        // Check if email is unique
        for (User user : UserContainer.getInstance().getUsers()) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
	
	private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
	
	BorderPane bp;
	GridPane gp;
	Scene sc;
	Stage stage;
	  
	Label judul, mail, pass, confirmPass;
	TextField email;
	PasswordField password, confirmPassword;   
	Button loginBtn, regBtn;
	HBox btnHB;
	
	public void start() {
//		setScene
		gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(5);
		    
//		title and grid pane
		judul = new Label("Register");
		Font font = Font.font("Arial", FontWeight.BLACK, 40);
		judul.setFont(font);
		gp.add(judul, 0, 0);
		GridPane.setColumnSpan(judul, 3);
		GridPane.setHalignment(judul, HPos.CENTER);
		     
//		email
		mail = new Label("Email  ");
		email = new TextField();
		gp.add(mail, 0, 1);
		gp.add(email, 1, 1);
		   
//		password
		pass = new Label("Password  ");
		password = new PasswordField();
		gp.add(pass, 0, 2);
		gp.add(password, 1, 2);
		
//		confirm password
		confirmPass = new Label("Confirm Password  ");
		confirmPassword = new PasswordField();
		gp.add(confirmPass, 0, 3);
		gp.add(confirmPassword, 1, 3);
		
//		button
		loginBtn = new Button("Login");
		regBtn = new Button("Register");
		loginBtn.setMinWidth(100);
		regBtn.setMinWidth(100);
		loginBtn.setStyle("-fx-background-color: #515151;");
		loginBtn.setTextFill(Color.WHITE);
		regBtn.setStyle("-fx-background-color: #515151;");
		regBtn.setTextFill(Color.WHITE);
		//		  Button Hbox
		btnHB = new HBox();
		btnHB.getChildren().addAll(loginBtn, regBtn);
		btnHB.setSpacing(5);
		gp.add(btnHB, 1, 4);
		
		    
		regBtn.setOnMouseClicked((e) -> {
			String enteredEmail = email.getText();
	        String enteredPassword = password.getText();
	        String enteredConfirmPassword = confirmPassword.getText();
            if (enteredEmail.isEmpty() && enteredPassword.isEmpty() && enteredConfirmPassword.isEmpty()) {
            	showAlert("Registration Failed", "All fields can't be empty");
            }else if (enteredEmail.isEmpty()) {
            	showAlert("Registration Failed", "Email can't be empty");
            }else if (enteredPassword.isEmpty()) {
            	showAlert("Registration Failed", "Password can't be empty");
            }else if (enteredConfirmPassword.isEmpty()) {
            	showAlert("Registration Failed", "Confirm Password can't be empty");
            }else if (!enteredPassword.equals(enteredConfirmPassword)) {
            	showAlert("Registration Failed", "Password doens't match");
            }else if(!isEmailValid(enteredEmail)) {
            	showAlert("Registration Failed", "Email doesn't met the requirements");
            }else {
            	UserContainer.getInstance().addUser(enteredEmail, enteredPassword);
            	TransactionContainer.getInstance().addTransaction(enteredEmail);
            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Register Success!");
                alert.setHeaderText(null);
                alert.setContentText("Registration Success!, Please Login to continue.");
                alert.show();
            	new LoginPage(stage);
            }
			});
		
		loginBtn.setOnMouseClicked((e)->{
			new LoginPage(stage);
		});
		   
//		  showScene
		  sc = new Scene(gp, 720, 500);
	}
	public RegisterPage(Stage stage) {
		this.stage = stage;
		start();
		stage.setScene(sc);
		stage.setTitle("Register");
		stage.getIcons().add(new Image("/logo.png"));
		stage.show();
	}
}
