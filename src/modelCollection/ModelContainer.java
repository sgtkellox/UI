package modelCollection;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelContainer {
	
	private static ModelContainer container = null;
	
	private static final ObservableList<ModelDefinition> models = FXCollections.observableArrayList();
	private List<ModelDefinition> currentChecked;
	
	private ModelContainer() {
		currentChecked = new ArrayList<ModelDefinition>();
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
		ngLabels.add("LYM");
		ngLabels.add("MB");
		ngLabels.add("MEL");
		ngLabels.add("MEN");
		ngLabels.add("MET");
		ngLabels.add("PIT");
		ngLabels.add("SCHW");
		models.add(new ModelDefinition("EffNet-V2-NG", 10,384, "hallo",7,ngLabels ));
		
		List<String> gLabels = new ArrayList<>();
		gLabels.add("A");
		gLabels.add("EPN");
		gLabels.add("GBM");
		gLabels.add("H3");
		gLabels.add("O");
		gLabels.add("PA");
		gLabels.add("PXA");
		models.add(new ModelDefinition("EffNet-V2-G", 10,384, "E:\\models\\kryo\\glial\\v2_384_10x_pt\\model_100.pt",7,gLabels ));
		
		
	}

	public List<ModelDefinition> getCurrentChecked() {
		return currentChecked;
	}

}
