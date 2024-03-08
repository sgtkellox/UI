package data;


import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SlideContainer {
	
	private static SlideContainer sLideContainer = null;
	private static final ObservableList<Slide> slides = FXCollections.observableArrayList();	
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

	public static ObservableList<Slide> getSlides() {
		return slides;
	}

	
	
	public static void addSlides(List<Slide> slides) {
		slides.addAll(slides);
	}
	
	public static void addClassification(SlideClassification classification) {
		for(Slide slide: slides) {
			if (slide.equals(classification.getSlide())){
				slide.getClassifications().add(classification);
			}
		}
	}

	
	
	

	

		

}
