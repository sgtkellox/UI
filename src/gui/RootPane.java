package gui;

import org.kordamp.ikonli.javafx.FontIcon;
import com.jfoenix.controls.JFXDrawer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RootPane extends BorderPane {
	

	private HBox topStripe;
	
	private ImageView logo;
	
	private FontIcon openIcon;
	private FontIcon closeIcon;
	

	private Region topSpacer;
		
	JFXDrawer drawer;

	public RootPane() {
		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/RootPane.css")));
		ImageGridPane display = new ImageGridPane();
		
		CustomManuBar menuBar = new CustomManuBar(display);

		topStripe = new HBox();
		
		topStripe.setId("title");
		
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

		logo = new ImageView();
		Image image = new Image("02_Logo_UKHD.png");

		logo.setImage(image);
		logo.setPreserveRatio(true);
	
		topSpacer = new Region();
		topSpacer.setPrefWidth(300);

		top.getChildren().addAll(menuBar,topStripe);
		topStripe.getChildren().addAll(buttonHolder,topSpacer, logo);
		

		this.setTop(top);
		
		this.setCenter(display);
		
		this.getLeft().setVisible(false);
		this.getLeft().setManaged(false);

	}

	public void setSizeChangeListener(Stage stage) {

		stage.heightProperty().addListener((obs, oldVal, newVal) -> {
			double desiredHight = ((double) newVal) * 0.03;
			topStripe.setPrefHeight(desiredHight);
			
			logo.setFitHeight(desiredHight);
			
					
			closeIcon.setStyle("-fx-icon-size:" + Double.toString(desiredHight) + ";");
			openIcon.setStyle("-fx-icon-size:" + Double.toString(desiredHight) + ";");

		});
		
		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
						
			double desiredWidthTop = ((double) newVal) * 0.92;
			
			topSpacer.setPrefWidth(desiredWidthTop);
			drawer.setDefaultDrawerSize(((double) newVal)*0.2);

		});
	}

}
