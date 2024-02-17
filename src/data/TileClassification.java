package data;

import java.util.List;

public class TileClassification {
	
	private String label;
	private List<String> labels;
	private Tile tile;
	private int xShift;
	private int yShift;
	
	public TileClassification() {
		
	}
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
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
	 
	
	
	

}
