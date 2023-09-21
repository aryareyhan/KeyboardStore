package main;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class UserSplit {
	ArrayList<Keyboard> keyboards;
	ArrayList<Transaction> transactions;
	Stage stage;
	Scene scene;
    BorderPane bp, rightbp;
    SplitPane sp;
    Label title, subTitle;
    VBox vb;
    GridPane gp, leftGp;
    Label name, price, stock, desc;
    Text nameT, priceT, stockT, descT;
    HBox nameHB, priceHB, stockHB, descHB, cartHB;
    VBox container, cartContainer;
    Image keeb;
    Label cart,cartStrip;
    Text cartName, cartStock;
    int draggedItemIndex;
    String loggedIn;
    
    private static final double MAX_SCALE = 2.5;
	private static final double MIN_SCALE = 0.4;
    private static final double SCALE_DELTA_IN = 1.2;
    private static final double SCALE_DELTA_OUT = 0.8;
    double currScale = 1.0;
    
    int kibs1,kibs2,kibs3,kibs4;
	
	public void start(){
		keyboards = KeyboardContainer.getInstance().getKeyboards();

		loggedIn = TransactionContainer.getInstance().getLoggedIn();
		
		bp = new BorderPane();
		
		// bgm
		  String audioFileName = "src/lofi.mp3";
		  File audioFile = new File(audioFileName);
		  Media audioMedia = new Media(audioFile.toURI().toString());
		  MediaPlayer mediaPlayer = new MediaPlayer(audioMedia);
		  mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		  mediaPlayer.play();
		
		// menu bar
		MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Menu");
        MenuItem viewKeeb = new MenuItem("View Keyboard");
        MenuItem logOut = new MenuItem("Logout");
        
        logOut.setOnAction(e->{
        	mediaPlayer.stop();
			new LoginPage(stage);
		});
        
        // Add the MenuItems to the Menu
        fileMenu.getItems().addAll(viewKeeb, logOut);

        // Add the Menu to the MenuBar
        menuBar.getMenus().add(fileMenu);
        
        //title
        title = new Label("Keyboard Catalogue");
        
        // menu bar and title
        vb = new VBox();
        vb.getChildren().addAll(menuBar, title);
        vb.setAlignment(Pos.CENTER);
		
        bp.setTop(vb);
        
        // right scrollPane
        rightbp = new BorderPane();
        
        Font font = Font.font("Arial", FontWeight.BOLD, 30);
	    cart = new Label("Your Cart");
        cart.setFont(font);
        rightbp.setTop(cart);

        cartContainer = new VBox();
        
        kibs1 = 0 ; kibs2 = 0 ; kibs3 = 0 ; kibs4 = 0 ;
        
        ArrayList<String> purchases;
        // ambil data di database loggedIn
        for (Transaction trans : TransactionContainer.getInstance().getTransactions()) {
            if (trans.getUser().equals(loggedIn)) {
                purchases = trans.getList();
             // display datas di vboxnya
                
                for (String kibs : purchases) {
                	if(kibs.equals("Igoltech Keebs XO200") && kibs1 < Integer.parseInt(keyboards.get(0).getStock())) {
                		kibs1 += 1;
                	} else if (kibs.equals("Dark Black RGB")&& kibs2 < Integer.parseInt(keyboards.get(1).getStock())) {
                		kibs2+= 1;
                	} else if (kibs.equals("Watermelon Keebs Z200")&& kibs3 < Integer.parseInt(keyboards.get(2).getStock())) {
                		kibs3+= 1;
                	} else if (kibs.equals("Igoltech Classic Keebs")&& kibs4 < Integer.parseInt(keyboards.get(3).getStock())) {
                		kibs4+= 1;
                	}
                }
                break;
            }
        }
        if (kibs1!=0) {
        	Text text = new Text("Igoltech Keebs XO200 - " + String.valueOf(kibs1));
        	cartContainer.getChildren().add(text);
        }
        if (kibs2!=0) {
        	Text text = new Text("Dark Black RGB - " + String.valueOf(kibs2));
        	cartContainer.getChildren().add(text);
        }
        if (kibs3!=0) {
        	Text text = new Text("Watermelon Keebs Z200 - " + String.valueOf(kibs3));
        	cartContainer.getChildren().add(text);
        }
        if (kibs4!=0) {
        	Text text = new Text("Igoltech Classic Keebs - " + String.valueOf(kibs4));
        	cartContainer.getChildren().add(text);
        }
        
        cartContainer.setPadding(new Insets(10));
        rightbp.setCenter(cartContainer);
        
        // buttons
        VBox rightBtn = new VBox();
        Button buy = new Button("Buy");
        buy.setMaxWidth(125);
        Button clear = new Button("Clear all");
        clear.setMinWidth(125);
        
        buy.setOnMouseClicked(e->{
        	int stock1 , stock2 , stock3, stock4;
        	stock1 = Integer.parseInt(keyboards.get(0).getStock())-kibs1;
        	stock2 = Integer.parseInt(keyboards.get(1).getStock())-kibs2;
        	stock3 = Integer.parseInt(keyboards.get(2).getStock())-kibs3;
        	stock4 = Integer.parseInt(keyboards.get(3).getStock())-kibs4;
        	KeyboardContainer.getInstance().getKeyboards().get(0).setStock(String.valueOf(stock1));
        	KeyboardContainer.getInstance().getKeyboards().get(1).setStock(String.valueOf(stock2));
        	KeyboardContainer.getInstance().getKeyboards().get(2).setStock(String.valueOf(stock3));
        	KeyboardContainer.getInstance().getKeyboards().get(3).setStock(String.valueOf(stock4));
        	for (Transaction trans : TransactionContainer.getInstance().getTransactions()) {
                if (trans.getUser().equals(loggedIn)) {
                	trans.clear();
                    break;
                }
            }
        	new UserSplit(stage);
        });
        
        clear.setOnMouseClicked(e->{
        	for (Transaction trans : TransactionContainer.getInstance().getTransactions()) {
                if (trans.getUser().equals(loggedIn)) {
                	trans.clear();
                    break;
                }
            }
        	new UserSplit(stage);
        });
        
        rightBtn.getChildren().addAll(buy,clear);
        rightBtn.setSpacing(10);
        
        rightbp.setBottom(rightBtn);
        rightbp.setPadding(new Insets(10));
        
        BorderPane scrollRight = new BorderPane(rightbp);
        
        // left scrollPane
        leftGp = new GridPane();
		
		for(int i = 0; i<4; i++) {
			leftGp.add(itemContainer(i, scrollRight), 0, i);
		}
        ScrollPane scrollLeft = new ScrollPane(leftGp);

        sp = new SplitPane(scrollLeft, scrollRight);
        sp.setDividerPositions(0.7);
        
        bp.setCenter(sp);
        
        // subTitle
        subTitle = new Label("You can drag & drop item from catalogue to the cart!");
        bp.setBottom(subTitle);
        BorderPane.setAlignment(subTitle, Pos.CENTER);

        scene = new Scene(bp, 940, 740);
	}
	
	public GridPane itemContainer(int idx, BorderPane scrollRight) {
     	keeb = new Image(getClass().getResource("/keyboard"+(idx+1)+".png").toExternalForm());
        ImageView view = new ImageView(keeb);
        view.setFitHeight(100);
        view.setFitWidth(150);

        name = new Label("Name: ");
        price = new Label("Price: ");
        stock = new Label("Stock: ");
        desc = new Label("Description: ");

        nameT = new Text(keyboards.get(idx).getName());
        priceT = new Text(keyboards.get(idx).getPrice());
        stockT = new Text(keyboards.get(idx).getStock());
        descT = new Text(keyboards.get(idx).getDescription());

        nameHB = new HBox();
        nameHB.getChildren().addAll(name, nameT);
        priceHB = new HBox();
        priceHB.getChildren().addAll(price, priceT);
        stockHB = new HBox();
        stockHB.getChildren().addAll(stock, stockT);
        descHB = new HBox();
        descHB.getChildren().addAll(desc, descT);
        container = new VBox();
        container.getChildren().addAll(view, nameHB, priceHB, stockHB, descHB);
             
        gp = new GridPane();
        gp.setPadding(new Insets(10));
        gp.setVgap(20);
        gp.add(container, 0, 0);
        
        view.setOnMouseClicked(e->{
			viewImage(view);
		});
        
        	//handle ketika mulai di drag
      		view.setOnDragDetected(e->{
      			draggedItemIndex = idx;
      			Dragboard db = view.startDragAndDrop(TransferMode.ANY);
      			ClipboardContent content = new ClipboardContent();
      			content.putImage(view.getImage());
      			db.setContent(content);
      			e.consume();
      		});
      		
      		//handle ketika objek yang didrag berada diatas tujuan
      		scrollRight.setOnDragOver(e->{
      			if(e.getGestureSource() != scrollRight && e.getDragboard().hasImage()) {
      				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
      			}
      			e.consume();
      		});
      		
      		//handle ketika objek yang didrag dilepas
      		scrollRight.setOnDragDropped(e->{
      			Dragboard db = e.getDragboard();
      			boolean success = false;
      			
      			if(db.hasImage()) {
      				// cek database user
      				// cek si user udh ad database ato blom
      				boolean flag = true;
      		        for (Transaction trans : TransactionContainer.getInstance().getTransactions()) {
      		            if (trans.getUser().equals(loggedIn)) {
      		                flag = false;
      		                break;
      		            }
      		        }
      		        
      		        // kalo belom, bikinin database
      		        if(flag) {
      		        	TransactionContainer.getInstance().addTransaction(loggedIn);
      		        }
      		        
      		        // tambahin ke database
      		        for (Transaction trans : TransactionContainer.getInstance().getTransactions()) {
    		            if (trans.getUser().equals(loggedIn)) {
    		                trans.addKeyboard(keyboards.get(draggedItemIndex).getName());
    		            }
    		        }
      		      success = true;
      			}
      			
      			e.setDropCompleted(success);
      			new UserSplit(stage);
      		});
        
        return gp;
    }
	
	public void viewImage(ImageView imageView) {
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		GridPane gp;
		gp = new GridPane();
		gp.setHgap(5);
		gp.setVgap(5);
		
		ImageView img = new ImageView(imageView.getImage());
		img.setFitHeight(200);
		img.setFitWidth(300);
		
		Label desc = new Label("description");
		
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
	
	public UserSplit(Stage stage) {
		this.stage = stage;
		start();
		stage.setScene(scene);
        stage.setTitle("View Image");
        stage.getIcons().add(new Image("/logo.png"));
        stage.show();
	}
}
