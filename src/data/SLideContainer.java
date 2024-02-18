package data;

public class SLideContainer {
	
	private static SLideContainer sLideContainer = null;
	
	private List<>
	
	private SLideContainer() {
		
	}
	
	public static SLideContainer instance() {
		if(sLideContainer==null) {
			sLideContainer = new SLideContainer();
			return sLideContainer;
		}else {
			return sLideContainer;
		}
	}

}
