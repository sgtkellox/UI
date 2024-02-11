package data;

import java.util.List;

public class WsiClassification {
	private int wsiWith;
	private int wsiHeight;
	private int tileSize;
	private String slideClassification;
	private List<TileClassification> tileClassifications;
	public int getWsiWith() {
		return wsiWith;
	}
	public void setWsiWith(int wsiWith) {
		this.wsiWith = wsiWith;
	}
	public int getWsiHeight() {
		return wsiHeight;
	}
	public void setWsiHeight(int wsiHeight) {
		this.wsiHeight = wsiHeight;
	}
	public int getTileSize() {
		return tileSize;
	}
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
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

}


