package data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class WsiClassification {
	
	private String slideClassification;
	private int tileSize;
	private int magnification;
	private WSI wsi;
	private List<TileClassification> tileClassifications;
	private List<String> possibleClassifications;
	
	public WsiClassification() {
		
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
	public WSI getWsi() {
		return wsi;
	}

	public void setWsi(WSI wsi) {
		this.wsi = wsi;
	}
	
	
	
	
	public void makeVisualisation() {
		
		for(TileClassification tc : tileClassifications) {
			int[] pos = tc.getScaledPosition();
			
			
			
		}	
	}

	
	

	

}


