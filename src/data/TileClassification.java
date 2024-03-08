package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class TileClassification {
	
	private String best;
	private double maxConf;
	private HashMap<String,Double> propabilities;
	private Tile tile;
	private int xShift;
	private int yShift;
	
	
	public TileClassification(Tile tile,List<String> labels) {
		propabilities = new HashMap<String, Double>();
		this.tile = tile;
	}
	public TileClassification(Tile tile,String json) {
		this.tile = tile;
		extractJsonInformation(json);
		calcBest();		
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
	
	public double getMaxConf() {
		return maxConf;
	}
	public void setMaxConf(double maxConf) {
		this.maxConf = maxConf;
	}
	
	private void calcBest() {
		double max = 0;
		for(Entry<String, Double> e :propabilities.entrySet()) {
			if(e.getValue() > max) {
				max = e.getValue();
				this.best = e.getKey();
			}
		}
		this.maxConf = max;
	}
	
	private void extractJsonInformation(String s) {
		s = s.replaceAll("\\{", "");
		s = s.replaceAll("\\}", "");
		s = s.replaceAll("\\[", "");
		s = s.replaceAll("\\]", "");
		
		s = s.replaceAll("\n", "");
		s = s.strip();
		//System.out.println(s);
		String[] split2 = s.split(",");
		
		String label = "";
		double prop = 0;
		
		for(int i = 0; i<split2.length; i++) {
			String s2 = split2[i];
			s2 = s2.trim();
			s2 = s2.replaceAll("\"", "");
			s2 = s2.split(":")[1].trim();
			
			if(i%2 == 0) {
				label = s2;
				
			}else {
				 prop = Double.valueOf(s2);
				 this.propabilities.put(label, prop);
			}	
		}
	}
	
	 
	
	
	

}
