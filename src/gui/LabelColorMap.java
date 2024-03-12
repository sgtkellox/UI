package gui;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class LabelColorMap {
	
	private static LabelColorMap map = null;
	private static HashMap<String,Color> colorMap = new HashMap<String,Color>();
	
	private LabelColorMap() {
		colorMap.put("LYM", Color.RED) ;
		colorMap.put("MB", Color.GREEN) ;
		colorMap.put("MEL", Color.YELLOW) ;
		colorMap.put("MEN", Color.GREY) ;
		colorMap.put("MET", Color.ORANGE) ;
		colorMap.put("PIT", Color.PURPLE) ;
		colorMap.put("SCHW", Color.PINK) ;
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
	
	

}


