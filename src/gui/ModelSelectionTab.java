package gui;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelCollection.ModelDefinition;
import yolointerface.DetectionExecuter;
import yolointerface.ImageContainer;

public class ModelSelectionTab extends VBox {

	ImageContainer imageContainer = ImageContainer.instance();
	DetectionExecuter detector = DetectionExecuter.instance();

	public ModelSelectionTab(ImageGridPane display) {
		
		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/WorkTab.css")));
		this.setId("parent");

		

		ListView<ModelDefinition> listView = new ListView<ModelDefinition>();
		
		
		
		
		Button btnSelectCirDirectory = new Button("Add Model");
		btnSelectCirDirectory.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			File selectedDirectory = fileChooser.showOpenDialog(new Stage());
			
			
		});

		this.getChildren().addAll(listView,btnSelectCirDirectory);

		
	}
	
	private ArrayList<File> filterImageList(File[] files) {
		ArrayList<File> filtered = new ArrayList<File>();
		for(int i = 0; i<files.length;i++) {
			if(files[i].getAbsolutePath().endsWith(".pt")) {
				filtered.add(files[i]);
			}
		}
		return filtered;
	}

}
