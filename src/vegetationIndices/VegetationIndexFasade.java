package vegetationIndices;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.WritableImage;

public class VegetationIndexFasade {
	public static final ObservableList<VegetationIndexInterface> vegIndicesAvalable = FXCollections.observableArrayList(new NDVI(),new GNDVI());
	
	public static WritableImage[] createNDVIImage(File f,List<VegetationIndexInterface> indicesToCalculate) {
		                 
		return Calculator.createImages(f, indicesToCalculate);
		
	}

}


