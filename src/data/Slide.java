package data;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Slide {
	private int wsiWith;
	private int wsiHeight;
	private String name;
	private int emptyBorderX;
	private int emptyBorderY;
	private List<Tile> tiles;
	private List<SlideClassification> classifications;

	public Slide() {

	}

	public Slide(String name, List<Tile> tiles) {
		this.tiles = tiles;
		this.name = name;

		int[] shiftValues = calcShiftValues(tiles);
		this.wsiWith = shiftValues[1] - shiftValues[0];
		this.wsiHeight = shiftValues[3] - shiftValues[2];

		this.classifications = new ArrayList<SlideClassification>();

	}

	public List<SlideClassification> getClassifications() {
		return classifications;
	}

	public void setClassifications(List<SlideClassification> classifications) {
		this.classifications = classifications;
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

		int tileSize = tiles.get(0).getSize();

		for (Tile t : tiles) {
			int xTile = t.getX();
			int yTile = t.getY();
			
			xMin = Math.min(xMin, xTile);

			xMax = Math.max(xMax, xTile);

			yMin = Math.min(yMin, yTile);
			yMax = Math.max(yMax, yTile);

		}
		int[] result = new int[4];
		result[0] = xMin;
		result[1] = xMax + tileSize;
		result[2] = yMin;
		result[3] = yMax + tileSize;
		
		for (Tile t : tiles) {
			t.setxShift(xMin);
			t.setyShift(yMin);
		}
		
		return result;
	}

	@Override
	public String toString() {
		return name;
	}

}
