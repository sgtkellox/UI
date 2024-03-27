package gui;



import data.SlideClassification;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

public class ImageGridPane extends SplitPane {
	
	StatsView statsView = new StatsView();

	MapView mapView = new MapView();
	TableViewPane table = new TableViewPane(this);
	
	TileView tileBox = new TileView();
		
	
	public ImageGridPane() {
		
		
		this.setOrientation(Orientation.VERTICAL);
		this.setDividerPositions(0.5);
		
		SplitPane top = new SplitPane();
		SplitPane bot = new SplitPane();
		
	
		top.getItems().addAll(mapView,statsView);
		bot.getItems().addAll(tileBox,table);
		
		this.getItems().addAll(top,bot);	
		 
	}
	
	
	
	public void displayTile(String path) {
		tileBox.displayTile(path);
	}
	
	
	
	
	public void showWeightedVote(SlideClassification classification) {
		statsView.showStats(classification);
	}
	
	public void showMap(SlideClassification classification,double confThreshOld) {
		mapView.makeVisualisation(classification,confThreshOld);
	}
			
	

}
