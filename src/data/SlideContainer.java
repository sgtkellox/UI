package data;

import java.util.HashMap;

public class SlideContainer {
	
	private static SlideContainer sLideContainer = null;
	
	private static HashMap<String, SlideList> slideLists = new HashMap<>();
	
	
	private static int currentSelectedIndex = 0;
	
	
	
	private SlideContainer() {
		
	}
	
	public static SlideContainer instance() {
		if(sLideContainer==null) {
			sLideContainer = new SlideContainer();
			return sLideContainer;
		}else {
			return sLideContainer;
		}
	}
	

	public static int getCurrentSelectedIndex() {
		return currentSelectedIndex;
	}

	public static void setCurrentSelectedIndex(int currentSelectedIndex) {
		SlideContainer.currentSelectedIndex = currentSelectedIndex;
	}

	public HashMap<String, SlideList> getSlideLists() {
		return slideLists;
	}

	

		

}
