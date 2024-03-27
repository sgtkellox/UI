package modelCollection;

import java.util.List;

public class ModelDefinition {
	
	private String name;
	private int resolution;
	private int tileSize;	
	private String path;
	private int numClasses;
	private List<String> possibleLabels;
	
	public ModelDefinition() {
			
	}
	
	public ModelDefinition(String name, int resolution, int tileSize, String path, int numClasses, List<String> possibleLabels) {
		this.name = name; 
		this.resolution = resolution;
		this.tileSize = tileSize;
		this.path = path;
		this.numClasses = numClasses;
		this.possibleLabels = possibleLabels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getNumClasses() {
		return numClasses;
	}

	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}

	public List<String> getPossibleLabels() {
		return possibleLabels;
	}

	public void setPossibleLabels(List<String> possibleLabels) {
		this.possibleLabels = possibleLabels;
	}
	
	public String toString() {
		return this.name;
	}
	
	

}
