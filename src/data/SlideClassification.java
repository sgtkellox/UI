package data;

import java.util.HashMap;
import java.util.List;

public class SlideClassification {
	
	private String slideClassification;
	private int tileSize;
	private int magnification;
	private Slide wsi;
	private List<TileClassification> tileClassifications;
	private List<String> possibleClassifications;
	private HashMap<String, Double> sumConfidenzes; 
	private HashMap<String, Double> wheightedSumConfidenzes;
	
	public SlideClassification() {
		wheightedSumConfidenzes = new HashMap<String, Double>();
	}

	public int getTileSize() {
		return tileSize;
	}
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}
	public int getMagnification() {
		return magnification;
	}
	public void setMagnification(int magnification) {
		this.magnification = magnification;
	}
	
	public List<TileClassification> getTileClassifications() {
		return tileClassifications;
	}
	public void setTileClassifications(List<TileClassification> tileClassifications) {
		this.tileClassifications = tileClassifications;
	}
	public String getSlideClassification() {
		return slideClassification;
	}
	public void setSlideClassification(String slideClassification) {
		this.slideClassification = slideClassification;
	}
	
	public List<String> getPossibleClassifications() {
		return possibleClassifications;
	}

	public void setPossibleClassifications(List<String> possibleClassifications) {
		this.possibleClassifications = possibleClassifications;
	}
	public Slide getWsi() {
		return wsi;
	}

	public void setWsi(Slide wsi) {
		this.wsi = wsi;
	}
	
	public HashMap<String, Double> getSumConfidenzes() {
		return sumConfidenzes;
	}

	public HashMap<String, Double> getWheightedSumConfidenzes() {
		return wheightedSumConfidenzes;
	}
	
	public void calcWeightedSums() {
		for(String label: this.possibleClassifications) {
			wheightedSumConfidenzes.put(label, 0.0);
		}
			
		for(TileClassification tileClassification : tileClassifications) {
			String label = tileClassification.getLabel();
			Double d = this.wheightedSumConfidenzes.get(label)+tileClassification.getConfidenz();
			wheightedSumConfidenzes.put(label, d);
		}
	}
	
	
	public void makeVisualisation() {
		
		for(TileClassification tc : tileClassifications) {
			int[] pos = tc.getScaledPosition();
			
			
			
		}	
	}

	

	
	

	

}


