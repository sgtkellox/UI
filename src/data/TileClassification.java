package data;

import java.util.HashMap;
import java.util.List;

public class TileClassification {
	
	private String best;
	private HashMap<String,Double> propabilities;
	private Tile tile;
	private int xShift;
	private int yShift;
	private double confidenz;
	
	public TileClassification(Tile tile,List<String> labels) {
		propabilities = new HashMap<String, Double>();
		this.tile = tile;
	}
	
	public Tile getTile() {
		return tile;
	}
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public int[] getScaledPosition() {
		int x = (tile.getX()-xShift)/tile.getSize();
		int y = (tile.getY()-yShift)/tile.getSize();
		
		int[] scaledPos = new int[2];
		scaledPos[0] = x;
		scaledPos[1] = y;
		return scaledPos;
	}


	public double getConfidenz() {
		return confidenz;
	}


	public void setConfidenz(double confidenz) {
		this.confidenz = confidenz;
	}



	public HashMap<String,Double> getPropabilities() {
		return propabilities;
	}



	public void setPropabilities(HashMap<String,Double> propabilities) {
		this.propabilities = propabilities;
	}



	public String getBest() {
		return best;
	}



	public void setBest(String best) {
		this.best = best;
	}
	 
	
	
	

}
