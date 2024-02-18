package data;

import java.util.List;

public class WSI {
	private int wsiWith;
	private int wsiHeight;
	private String name;
	private int emptyBorderX;
	private int emptyBorderY;
	private List<Tile> tiles;
	
	public WSI() {
		
	}
	
	public WSI(String name ,List<Tile> tiles) {
		this.tiles = tiles;
		this.name = name;
		
		int[] shiftValues = calcShiftValues(tiles);
		this.wsiWith = shiftValues[1]-shiftValues[0];
		this.wsiHeight = shiftValues[3]-shiftValues[2];
		
		
	}
	
	
	public int getWsiHeight() {
		return wsiHeight;
	}
	public void setWsiHeight(int wsiHeight) {
		this.wsiHeight = wsiHeight;
	}
	
	public int getWsiWith() {
		return wsiWith;
	}
	public void setWsiWith(int wsiWith) {
		this.wsiWith = wsiWith;
	}
	public String getName() {
		return name;
	}
	public void setName(String path) {
		this.name = path;
	}
	public int getEmptyBorderX() {
		return emptyBorderX;
	}
	public void setEmptyBorderX(int emptyBorderX) {
		this.emptyBorderX = emptyBorderX;
	}
	public int getEmptyBorderY() {
		return emptyBorderY;
	}
	public void setEmptyBorderY(int emptyBorderY) {
		this.emptyBorderY = emptyBorderY;
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
	private int[] calcShiftValues(List<Tile> tiles) {
		int xMin = 100000;
		int xMax = 0;
		int yMin = 100000;
		int yMax = 0;
		
		for(Tile t: tiles) {
			int xTile = t.getX();
			int yTile = t.getY();
			if(xTile<xMin) {
				xMin = xTile;
			}
			if(xTile>xMax) {
				xMax = xTile;
			}
			if(yTile<yMin) {
				xMin = xTile;
			}
			if(yTile>xMax) {
				xMax = yMax;
			}
		}
		int[] result = new int[4] ;
		result[0] = xMin;
		result[1] = xMax;
		result[2] = xMin;
		result[3] = xMin;
		
		return result;
	}



}
