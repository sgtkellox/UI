package gui;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class LabelColorMap {
	
	private LabelColorMap map = null;
	private HashMap<String,Color> colorMap  = new HashMap<String,Color>();
	
	private LabelColorMap() {
		
		
	}
	
	public LabelColorMap instance() {
		if(map == null) {
			return new LabelColorMap();
		}else {
			return map;
		}
	}

}


