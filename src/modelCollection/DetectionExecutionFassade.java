package modelCollection;

import java.io.IOException;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import javafx.collections.ObservableList;

import yolointerface.ImageContainer;
import yolointerface.ImageType;

public class DetectionExecutionFassade {
	
	
	
	private ObservableList<String> models;

	ImageContainer imageContainer = ImageContainer.instance();
	
	public DetectionExecutionFassade() {
		
	}
	
	
	
	public void runDetection(String modelName, double confidenzNiveau, ImageType imageType ) throws ModelNotFoundException, MalformedModelException, TranslateException, IOException {
		switch(modelName){ 
			case "effNetv2":
				YoloPytorchRGB yoloPytorch = new YoloPytorchRGB();
		}
	}
	
	

}
