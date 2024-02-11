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
	ImageBox cirPane = new ImageBox();
	ImageBox rgbPane = new ImageBox();

	VegetationIndexPane vegPane = new VegetationIndexPane();
	TableViewPane table = new TableViewPane(this);
	
	
	
	public ImageGridPane() {
		
		
		this.setOrientation(Orientation.VERTICAL);
		this.setDividerPositions(0.5);
		
		SplitPane top = new SplitPane();
		SplitPane bot = new SplitPane();
		
	
		top.getItems().addAll(rgbPane,cirPane);
		bot.getItems().addAll(vegPane,table);
		
		this.getItems().addAll(top,bot);	
		 
	}
	
	public void showRGBImage(Image image) {
		rgbPane.showImage(image);
	}
	
	public void showCIRImage(Image image) {
		cirPane.showImage(image);
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
	
	public void drawCIRGroundTruth(ArrayList<GroundTruth> gds) {
		cirPane.drawGroundTruth(gds);	
	}
	
	public void drawCIRBoundingBoxes(ObservableList<Detection> s) {
		cirPane.drawBoundingBoxes(s);
		table.addData(s);
		
	}
	
	public void changeOpacitys(double opacity) {
		rgbPane.setBbOpacity(opacity);
		cirPane.setBbOpacity(opacity);
	}
	
	

}
