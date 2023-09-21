package main;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class AdminPage {
	Scene sc;
	BorderPane scene;
	GridPane window;
	GridPane gp;
	Stage stage;
	ArrayList<Keyboard> keyboards;
	int index;
	
	private static final double MAX_SCALE = 2.5;
	private static final double MIN_SCALE = 0.4;
    private static final double SCALE_DELTA_IN = 1.2;
    private static final double SCALE_DELTA_OUT = 0.8;
    double currScale = 1.0;
	
	public void start() {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("Menu");
		MenuItem logOut = new MenuItem("Logout");
		
		logOut.setOnAction(e->{
			new LoginPage(stage);
		});
       
        // Add the MenuItems to the Menu
        fileMenu.getItems().addAll(logOut);

        // Add the Menu to the MenuBar
        menuBar.getMenus().add(fileMenu);
		
		window = new GridPane();
		window.setPadding(new Insets(10));
		scene = new BorderPane();
		menuBar.setPrefWidth(800);
		scene.setTop(menuBar);
		
		for (int i=0; i<4; i++) {
			GridPane gp1 = content(i);
			window.add(gp1, 0, i+1);
		}
		
		ScrollPane scrollPane = new ScrollPane(window);
		window.setAlignment(Pos.CENTER);
		scene.setCenter(scrollPane);
		
		sc = new Scene(scene, 940, 740);
	}
	
	public GridPane content(int idx) {
		keyboards = KeyboardContainer.getInstance().getKeyboards();
		String name = keyboards.get(idx).getName();
		GridPane gp = new GridPane();
		
		Label name1, price1, stock1, desc1;
		TextField nametx1, pricetx1, stocktx1, desctx1;
		Image keeb1;
		ImageView view;
		Button update1;

		gp = new GridPane();
		gp.setHgap(1);
		
		keeb1 = new Image(getClass().getResource("/keyboard"+(idx+1)+".png").toExternalForm());
		view = new ImageView(keeb1);
		view.setFitHeight(100);
		view.setFitWidth(150);
		gp.add(view, 0, 0);
		
		name1 = new Label("Name:");
		price1 = new Label("Price:");
		stock1 = new Label("Stock:");
		
		nametx1 = new TextField();
		nametx1.setDisable(true);
		nametx1.setPromptText(name);
		pricetx1 = new TextField();
		stocktx1 = new TextField();
		
		gp.add(name1, 1, 0);
		gp.add(nametx1, 2, 0);
		gp.add(price1, 3, 0);
		gp.add(pricetx1, 4, 0);
		gp.add(stock1, 5, 0);
		gp.add(stocktx1, 6, 0);
		
		desc1 = new Label("Description:");
		desctx1 = new TextField();
		GridPane.setColumnSpan(desctx1, 4);
		update1 = new Button("Update");
		update1.setOnMouseClicked(e->{
			if(validate(pricetx1,stocktx1,desctx1)) {
				keyboards.get(idx).setPrice(pricetx1.getText());
				keyboards.get(idx).setStock(stocktx1.getText());
				keyboards.get(idx).setDescription(desctx1.getText());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Update");
		        alert.setContentText("Update Success");
		        alert.setHeaderText(null);
		        alert.show();
			};
		});
		
		gp.add(desc1, 1, 1);
		gp.add(desctx1, 2, 1);
		gp.add(update1, 2, 2);
		
		view.setOnMouseClicked(e->{
			viewImage(view, idx);
		});
		
		return gp;
	}
	
	private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.show();
    }
	
	public boolean validate(TextField price, TextField stock, TextField description) {
		String priceString = price.getText();
		String stockString = stock.getText();
		String descString = description.getText();
				
		if(priceString.isEmpty()) {
			showAlert("Error", "Make sure price is not empty");
			return false;
		} else if(!priceString.matches("\\d+")) {
			showAlert("Error", "Make sure price is integer");
			return false;
		} else if(Integer.parseInt(priceString) <= 0) {
			showAlert("Error", "Make sure price is more than zero");
			return false;
		} else if(stockString.isEmpty()) {
			showAlert("Error", "Make sure stock is not empty");
			return false;
		} else if(!stockString.matches("\\d+")) {
			showAlert("Error", "Make sure stock is integer");
			return false;
		} else if(Integer.parseInt(stockString) < 0) {
			showAlert("Error", "Make sure stock is more than or equal to zero");
			return false;
		} else if(descString.isEmpty()) {
			showAlert("Error", "Make sure description is not empty");
			return false;
		} 
		return true;
	}
	
	public void viewImage(ImageView imageView, int idx) {
		index = idx;
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		GridPane gp;
		gp = new GridPane();
		gp.setHgap(5);
		gp.setVgap(5);
		
		ImageView img = new ImageView(imageView.getImage());
		img.setFitHeight(200);
		img.setFitWidth(300);
		
		Label desc = new Label(keyboards.get(index).getDescription());
		
		Button zoomIn, zoomOut, rotateLeft, rotateRight;
		
		rotateLeft = new Button("Rotate Left");
		rotateRight = new Button("Rotate Right");
		zoomIn = new Button("Zoom In");
		zoomOut= new Button("Zoom Out");
		
		zoomIn.setOnMouseClicked(e->{ 
			double newScale = currScale * SCALE_DELTA_IN;
			if(newScale > MAX_SCALE) {
				newScale = MAX_SCALE;
			}
			
			img.setScaleX(newScale);
			img.setScaleY(newScale);
			
			currScale = newScale;
		});
		
		zoomOut.setOnMouseClicked(e->{
			double newScale = currScale * SCALE_DELTA_OUT;

            if (newScale < MIN_SCALE) {
                newScale = MIN_SCALE;
            }

            img.setScaleX(newScale);
            img.setScaleY(newScale);

            currScale = newScale;
		});
		
		rotateLeft.setOnMouseClicked(e->{
			Rotate left;
			left = new Rotate(-90,img.getFitWidth()/2,img.getFitHeight()/2);
			img.getTransforms().add(left);
		});
		
		rotateRight.setOnMouseClicked(e->{
			Rotate right;
			right = new Rotate(90,img.getFitWidth()/2,img.getFitHeight()/2);
			img.getTransforms().add(right);
		});

		zoomIn.setMinWidth(125);
		zoomOut.setMinWidth(125);
		rotateLeft.setMinWidth(125);
		rotateRight.setMinWidth(125);
		
		gp.add(rotateLeft, 0, 0);
		gp.add(rotateRight, 0, 1);
		gp.add(zoomIn, 1, 0);
		gp.add(zoomOut, 1, 1);
		gp.setAlignment(Pos.CENTER);
		
		
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(img,desc,gp);
		vBox.setAlignment(Pos.CENTER);

		bp.setCenter(vBox);
		
		Scene scene = new Scene(bp, 600, 600);
		stage.setScene(scene);
		stage.setTitle("View Image");
		stage.show();
	}

	public AdminPage(Stage stage) {
		this.stage = stage;
		start();
		stage.setScene(sc);
		stage.setTitle("Register");
		stage.getIcons().add(new Image("/logo.png"));
		stage.show();
	}
}
