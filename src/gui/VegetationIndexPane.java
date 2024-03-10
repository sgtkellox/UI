package gui;

import java.io.File;
import java.util.List;

import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import vegetationIndices.Calculator;
import vegetationIndices.VegetationIndexInterface;
import yolointerface.ImageContainer;

public class VegetationIndexPane extends TabPane {
	
	ImageContainer imageContainer = ImageContainer.instance(); 
	MapView ndviView = new MapView();
	MapView gNDVIView = new MapView();
	
	public VegetationIndexPane() {
		
		
		ndviView.setText("NDVI");
		gNDVIView.setText("gNDVI");
		
		
		this.getTabs().addAll(ndviView,gNDVIView);
	}
	
	public void setTabsForSelectedIndizes(List<VegetationIndexInterface> selectedIndizes) {
		this.getTabs().clear();
		
		for(VegetationIndexInterface index: selectedIndizes) {
			MapView vegView = new MapView();
			vegView.setText(index.toString());
			this.getTabs().add(vegView);
		}
	}
	
	public void showIndizesOnTabs(WritableImage[] indexImages) {
		for(int i = 0; i<indexImages.length;i++) {
			((MapView)(this.getTabs().get(i))).showImage(indexImages[i]);;
			
		}
	}
	
	public void calcAndShowVegetationIndeizes(File image) {
		Calculator vegIndexCalculator = new Calculator();
		WritableImage[] indexImages = vegIndexCalculator.createNVVIImage(image);
		ndviView.showImage(indexImages[0]);
		gNDVIView.showImage(indexImages[1]);
		
	}

}
