package modelCollection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelContainer {
	
	private static ModelContainer container = null;
	
	private static final ObservableList<ModelDefinition> models = FXCollections.observableArrayList();
	
	private ModelContainer() {
		
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

}
