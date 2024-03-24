package modelCollection;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelContainer {
	
	private static ModelContainer container = null;
	
	private static final ObservableList<ModelDefinition> models = FXCollections.observableArrayList();
	
	private ModelContainer() {
		addDefaultModels();
	}
	
	public static ModelContainer instance() {
		if(container == null) {
			container = new ModelContainer();
		}
		return container;
	}

	public static ObservableList<ModelDefinition> getModels() {
		return models;
	}
	
	private void addDefaultModels() {
		List<String> ngLabels = new ArrayList<>();
		ngLabels.add(null)
		models.add(new ModelDefinition("EfficientNet-V2", 10,384, "hallo",7,ngLabels ));
	}

}
