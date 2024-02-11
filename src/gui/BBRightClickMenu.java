package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import yolointerface.Detection;
import yolointerface.ImageContainer;
import yolointerface.ImageType;

public class BBRightClickMenu extends ContextMenu {
	
	ImageContainer imageContainer = ImageContainer.instance();
	
	public BBRightClickMenu(BoundingBox parent,ImageBox imageView) {
		
		super();
		MenuItem enhanceItem = new MenuItem("Hervorheben");
		MenuItem deleteItem = new MenuItem("Löschen");
		

		enhanceItem.setOnAction((event) -> {
		    System.out.println("Choice 3 clicked!");
		});
		
		deleteItem.setOnAction((event) -> {
			
			if(parent.getDetection().getType().equals(ImageType.CIR)) {
				int index = ImageContainer.getCurrentSelectedCirIndex();
				
				
				
				File currentFile = ImageContainer.getCirImages().get(index);
				List<Detection> detections = new ArrayList<Detection>();
				for(Detection d : ImageContainer.getCIRDetections().get(currentFile)) {
					detections.add(d);
				}
				detections.remove(parent.getDetection());
				ImageContainer.getCIRDetections().get(currentFile).clear();

				imageView.clearBundingBoxes();
				
				
				ImageContainer.getCIRDetections().get(currentFile).addAll(detections);
				imageView.drawBoundingBoxes(ImageContainer.getCIRDetections().get(currentFile));
				
			}else if(parent.getDetection().getType().equals(ImageType.RGB)) {
				int index = ImageContainer.getCurrentSelectedIndex();
				
				
				
				File currentFile = ImageContainer.getRgbImages().get(index);
				List<Detection> detections = new ArrayList<Detection>();
				for(Detection d : ImageContainer.getRGBDetections().get(currentFile)) {
					detections.add(d);
				}
				
				
				
				detections.remove(parent.getDetection());
				
				ImageContainer.getRGBDetections().get(currentFile).clear();

				imageView.clearBundingBoxes();
				
				
				
				
				ImageContainer.getRGBDetections().get(currentFile).addAll(detections);
				
				imageView.drawBoundingBoxes(ImageContainer.getRGBDetections().get(currentFile));
			}
			
			

		});

		this.getItems().addAll(enhanceItem,deleteItem);
	}

}
