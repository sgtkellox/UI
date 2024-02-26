package gui;


import java.io.File;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import yolointerface.Detection;
import yolointerface.GroundTruth;



public class ImageGridPane extends SplitPane {
	ImageBox rgbPane = new ImageBox();
	StatsView statsView = new StatsView();

	VegetationIndexPane vegPane = new VegetationIndexPane();
	TableViewPane table = new TableViewPane(this);
	
	
	
	public ImageGridPane() {
		
		
		this.setOrientation(Orientation.VERTICAL);
		this.setDividerPositions(0.5);
		
		SplitPane top = new SplitPane();
		SplitPane bot = new SplitPane();
		
	
		top.getItems().addAll(rgbPane,statsView);
		bot.getItems().addAll(vegPane,table);
		
		this.getItems().addAll(top,bot);	
		 
	}
	
	public void showRGBImage(Image image) {
		rgbPane.showImage(image);
	}
	
	
	
	public void showNDVIImage(File image) {
		vegPane.calcAndShowVegetationIndeizes(image);
	}
	
	public void drawRGBBoundingBoxes(ObservableList<Detection> s) {
		rgbPane.drawBoundingBoxes(s);
		table.setData(s);
	}
	
	public void drawRGBGroundTruth(ArrayList<GroundTruth> gds) {
		rgbPane.drawGroundTruth(gds);	
	}
	
	
		
	
	
	public void changeOpacitys(double opacity) {
		rgbPane.setBbOpacity(opacity);
		
	}
	
	

}
