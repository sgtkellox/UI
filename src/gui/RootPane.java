package gui;

import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXDrawer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RootPane extends BorderPane {

	private HBox topStripe;
	private HBox bot;
	private ImageView logo;
	private FontIcon twitterIcon;
	private FontIcon openIcon;
	private FontIcon closeIcon;
	private FontIcon ytIcon;
	private FontIcon instaIcon;
	private FontIcon linkeInIcon;
	private Label lblCopyRight;

	private Region topSpacer;
	private Region botSpacer;
	

	
	
	
	JFXDrawer drawer;

	public RootPane() {
		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/RootPane.css")));
		
		ImageGridPane display = new ImageGridPane();

		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuSettings = new Menu("Einstellungen");
		Menu menuHelp = new Menu("Hilfe");

		menuBar.getMenus().addAll(menuFile, menuSettings, menuHelp);

		topStripe = new HBox();
		bot = new HBox();
		topStripe.setId("title");
		bot.setId("title");

		VBox top = new VBox();

		ControlPane workTab = new ControlPane(display);
		drawer = new JFXDrawer();
		drawer.setSidePane(workTab);
		
		
		
		
		
		closeIcon = new FontIcon("dashicons-arrow-left-alt");
		openIcon = new FontIcon("dashicons-arrow-right-alt");
		
		HBox buttonHolder = new HBox();
		buttonHolder.getChildren().add(openIcon);
		
		openIcon.setOnMouseClicked(e -> {
			drawer.open();
			buttonHolder.getChildren().clear();
			buttonHolder.getChildren().add(closeIcon);
		});
		
		drawer.setOnDrawerClosed(e->{
			this.getLeft().setVisible(false);
			this.getLeft().setManaged(false);
		});
		
		drawer.setOnDrawerOpening(e->{
			this.getLeft().setVisible(true);
			this.getLeft().setManaged(true);
		});
		
		closeIcon.setOnMouseClicked(e->{
			drawer.close();
			buttonHolder.getChildren().clear();
			buttonHolder.getChildren().add(openIcon);
		});

		this.setLeft(drawer);

		lblCopyRight = new Label("Neuropathologie Heidelberg");
		lblCopyRight.setId("copyright");

		logo = new ImageView();
		Image image = new Image("02_Logo_UKHD.png");

		logo.setImage(image);
		logo.setPreserveRatio(true);

		botSpacer = new Region();
		botSpacer.setPrefWidth(300);
		
		topSpacer = new Region();
		topSpacer.setPrefWidth(300);

		twitterIcon = new FontIcon("dashicons-twitter");
		ytIcon = new FontIcon("dashicons-youtube");
		linkeInIcon = new FontIcon("dashicons-linkedin");
		instaIcon = new FontIcon("dashicons-instagram");

		
		
		

		top.getChildren().addAll(menuBar,topStripe);
		topStripe.getChildren().addAll(buttonHolder,topSpacer, logo);
		bot.getChildren().addAll(lblCopyRight, botSpacer, twitterIcon, instaIcon, linkeInIcon, ytIcon);
		bot.setAlignment(Pos.CENTER_LEFT);

		this.setTop(top);
		this.setBottom(bot);
		this.setCenter(display);
		
		this.getLeft().setVisible(false);
		this.getLeft().setManaged(false);

	}

	public void setSizeChangeListener(Stage stage) {

		stage.heightProperty().addListener((obs, oldVal, newVal) -> {
			double desiredHight = ((double) newVal) * 0.05;
			topStripe.setPrefHeight(desiredHight);
			bot.setPrefHeight(desiredHight);
			logo.setFitHeight(desiredHight);

			double iconSize = desiredHight * 0.6;
			twitterIcon.setStyle("-fx-icon-size:" + Double.toString(iconSize) + ";");
			ytIcon.setStyle("-fx-icon-size:" + Double.toString(iconSize) + ";");
			linkeInIcon.setStyle("-fx-icon-size:" + Double.toString(iconSize) + ";");
			instaIcon.setStyle("-fx-icon-size:" + Double.toString(iconSize) + ";");
			lblCopyRight.setStyle("-fx-font-size:" + Double.toString(iconSize*0.5) + ";");
			
			
			closeIcon.setStyle("-fx-icon-size:" + Double.toString(desiredHight) + ";");
			openIcon.setStyle("-fx-icon-size:" + Double.toString(desiredHight) + ";");

		});
		
		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
			
			
			double desiredWidthBot = ((double) newVal) * 0.84;
			double desiredWidthTop = ((double) newVal) * 0.92;
			botSpacer.setPrefWidth(desiredWidthBot);
			topSpacer.setPrefWidth(desiredWidthTop);
			drawer.setDefaultDrawerSize(((double) newVal)*0.2);

		});
	}

}
