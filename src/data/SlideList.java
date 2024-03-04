package data;


import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SlideList {
	
	private Preparations prep;
	private ObservableList<Slide> slides = FXCollections.observableArrayList();
	
	
	
	public SlideList() {
		
	}
	
	public SlideList(List<Slide> data) {
		this.slides.addAll(data);
	}



	public Preparations getPrep() {
		return prep;
	}



	public void setPrep(Preparations prep) {
		this.prep = prep;
	}



	public ObservableList<Slide> getSlides() {
		return slides;
	}



	public void setSlides(ObservableList<Slide> slides) {
		this.slides = slides;
	}

}
