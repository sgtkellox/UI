package gui;

import org.controlsfx.control.CheckListView;

import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import vegetationIndices.VegetationIndexFasade;
import vegetationIndices.VegetationIndexInterface;
import yolointerface.ImageContainer;

public class VegetationIndexTab extends VBox {
	
	ImageContainer imageContainer = ImageContainer.instance();
	
	ImageGridPane display;
	
	public VegetationIndexTab(ImageGridPane display) {
		
		
		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/WorkTab.css")));
		this.setId("parent");

		this.display = display;
		
		CheckListView <VegetationIndexInterface> cbSelectIndex = new CheckListView <VegetationIndexInterface>(VegetationIndexFasade.vegIndicesAvalable);
		Button btnCalcIndizes = new Button("Indizes berechnen");
		
		btnCalcIndizes.setOnAction(e->{
			WritableImage[] imgs = VegetationIndexFasade.createNDVIImage(ImageContainer.getCirImages().get(ImageContainer.getCurrentSelectedCirIndex()),cbSelectIndex.getCheckModel().getCheckedItems());
			display.vegPane.setTabsForSelectedIndizes(cbSelectIndex.getCheckModel().getCheckedItems());
			display.vegPane.showIndizesOnTabs(imgs);
		});
				
		this.getChildren().addAll(cbSelectIndex,btnCalcIndizes);
	}

}
