package gui;


import java.util.ArrayList;

import data.SlideClassification;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import yolointerface.Detection;
import yolointerface.GroundTruth;



public class ImageGridPane extends SplitPane {
	
	StatsView statsView = new StatsView();

	MapView mapView = new MapView();
	TableViewPane table = new TableViewPane(this);
	
	ImageBox rgbPane = new ImageBox();
	
	
	
	public ImageGridPane() {
		
		
		this.setOrientation(Orientation.VERTICAL);
		this.setDividerPositions(0.5);
		
		SplitPane top = new SplitPane();
		SplitPane bot = new SplitPane();
		
	
		top.getItems().addAll(mapView,statsView);
		bot.getItems().addAll(rgbPane,table);
		
		this.getItems().addAll(top,bot);	
		 
	}
	
	public void showRGBImage(Image image) {
		rgbPane.showImage(image);
	}
	
	
	public void drawRGBBoundingBoxes(ObservableList<Detection> s) {
		rgbPane.drawBoundingBoxes(s);
		table.setData(s);
	}
	
	public void drawRGBGroundTruth(ArrayList<GroundTruth> gds) {
		rgbPane.drawGroundTruth(gds);	
	}
	
	public void showWeightedVote(SlideClassification classification) {
		statsView.showStats(classification);
	}
	
	public void showMap(SlideClassification classification) {
		mapView.makeVisualisation(classification);
	}
		
	
	
	public void changeOpacitys(double opacity) {
		rgbPane.setBbOpacity(opacity);
		
	}
	
	

}
