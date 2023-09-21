package main;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserPage {
	//variabel
	Scene scene;
	BorderPane bp;
	Stage stage;
	Text title;
	File file;
	Button button;
 	Media media;
 	MediaPlayer player;
 	MediaView view;
 	VBox container;
 	GridPane btnContainer;	
	
	public void start() {
		title = new Text("NEW KEYBOARD ARRIVED!");
		Font font = Font.font("Arial", FontWeight.BLACK, 40);
        title.setFont(font);
        
		file = new File("./src/keyboard.mp4");
 		media = new Media(file.toURI().toString());
 		player = new MediaPlayer(media);
 		view = new MediaView(player);
 		
 		button = new Button("CONTINUE >>");
 		button.setStyle("-fx-background-color: #515151;");
		button.setTextFill(Color.WHITE);
		
 		view.setFitHeight(300);
 		view.setFitWidth(400);
 		
 		player.setAutoPlay(true);
 		player.setCycleCount(MediaPlayer.INDEFINITE);
 		
 		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("Menu");
		MenuItem viewKeeb = new MenuItem("View Keyboard");
		MenuItem logOut = new MenuItem("Logout");
		
		viewKeeb.setOnAction(e->{
			player.stop();
			new UserSplit(stage);
		});
		
		logOut.setOnAction(e->{
			player.stop();
			new LoginPage(stage);
		});
       
        // Add the MenuItems to the Menu
        fileMenu.getItems().addAll(viewKeeb, logOut);

        // Add the Menu to the MenuBar
        menuBar.getMenus().add(fileMenu);
 		
 		
 		
 		container = new VBox();
 		container.getChildren().addAll(title, view);
 		container.setAlignment(Pos.CENTER);
 		container.setSpacing(10);
 		btnContainer = new GridPane();
 		btnContainer.add(container, 0, 0);
 		btnContainer.add(button, 0, 1);
 		btnContainer.setAlignment(Pos.CENTER);
 		btnContainer.setVgap(10);
 		
 		button.setOnMouseClicked(e->{
			player.stop();
			new UserSplit(stage);
		});
 		
 		bp = new BorderPane();
 		bp.setCenter(btnContainer);
 		BorderPane.setAlignment(btnContainer, Pos.CENTER);
 		bp.setTop(menuBar);
 		
 		scene = new Scene(bp,1000,700);
 		
 		stage.setScene(scene);
 		stage.setTitle("USER");
		
	}
	
	public UserPage(Stage stage) {
		this.stage = stage;
		start();
		stage.setScene(scene);
		stage.setTitle("USER");
		stage.getIcons().add(new Image("/logo.png"));
		stage.show();
	}
}

