package gui;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class LabelColorMap {
	
	private static LabelColorMap map = null;
	private static HashMap<String,Color> colorMap = new HashMap<String,Color>();
	private static HashMap<String,String> colorStringMap = new HashMap<String,String>();
	
	private LabelColorMap() {
		colorMap.put("LYM", Color.RED) ;
		colorMap.put("MB", Color.GREEN) ;
		colorMap.put("MEL", Color.YELLOW) ;
		colorMap.put("MEN", Color.GREY) ;
		colorMap.put("MET", Color.ORANGE) ;
		colorMap.put("PIT", Color.PURPLE) ;
		colorMap.put("SCHW", Color.PINK) ;
		
		colorMap.put("A", Color.RED) ;
		colorMap.put("EPN", Color.GREEN) ;
		colorMap.put("GBM", Color.YELLOW) ;
		colorMap.put("H3", Color.GREY) ;
		colorMap.put("O", Color.ORANGE) ;
		colorMap.put("PA", Color.PURPLE) ;
		colorMap.put("PXA", Color.PINK) ;
		
		
		colorStringMap.put("LYM", "red") ;
		colorStringMap.put("MB", "green") ;
		colorStringMap.put("MEL", "yellow") ;
		colorStringMap.put("MEN", "grey") ;
		colorStringMap.put("MET", "orange") ;
		colorStringMap.put("PIT", "purple") ;
		colorStringMap.put("SCHW", "pink") ;
		
		colorStringMap.put("A", "red") ;
		colorStringMap.put("EPN", "green") ;
		colorStringMap.put("GBM", "Blue") ;
		colorStringMap.put("H3", "grey") ;
		colorStringMap.put("O", "orange") ;
		colorStringMap.put("PA", "purple") ;
		colorStringMap.put("PXA", "pink") ;
		
		
	}
	
	public static LabelColorMap instance() {
		if(map == null) {
			map = new LabelColorMap();
			return  map;
		}else {
			return map;
		}
	}

	public static HashMap<String,Color> getColormap() {
		return colorMap;
	}
	
	public static Color lookUpColor(String label) {
		return colorMap.get(label);
	}

	public static HashMap<String,String> getColorStringMap() {
		return colorStringMap;
	}
	
	public static String lookUpColorString(String label) {
		return colorStringMap.get(label);
	}
	
	

}


