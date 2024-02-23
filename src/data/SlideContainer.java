package data;

import java.util.List;

public class SlideContainer {
	
	private static SlideContainer sLideContainer = null;
	
	private List<SlideList> slideLists;
	
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

	public List<SlideList> getSlideLists() {
		return slideLists;
	}

	public void setSlideLists(List<SlideList> slideLists) {
		this.slideLists = slideLists;
	}

}
