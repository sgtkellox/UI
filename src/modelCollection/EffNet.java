package modelCollection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.*;
import ai.djl.modality.*;
import ai.djl.modality.cv.*;

import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;
import ai.djl.training.util.*;
import javafx.concurrent.Task;
import vegetationIndices.Calculator;

import yolointerface.ImageContainer;

public class EffNet {

	Predictor<Image, Classifications> predictor;

	ImageContainer container = ImageContainer.instance();

	private String modelPath = "D:\\non_glial\\non_glial\\test.pt";

	Calculator calculator = new Calculator();
	
	ArrayList<String> synset = new ArrayList<String>();
	

	public EffNet() {
		synset.add("LYM");
		synset.add("MB");
		synset.add("MEL");
		synset.add("MEN");
		synset.add("MET");
		synset.add("PIT");
		synset.add("SCHW");
		Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
		        .addTransform(new ToTensor())
		        .addTransform(new Normalize(
		            new float[] {0.5f, 0.5f, 0.5f},
		            new float[] {0.5f, 0.5f, 0.5f}))
		        .optSynset(synset)
		        .build();

		Criteria<Image, Classifications> criteria = Criteria.builder()
		        .setTypes(Image.class, Classifications.class)
		        .optModelPath(Paths.get(modelPath))
		        .optOption("mapLocation", "true") // this model requires mapLocation for GPU
		        .optTranslator(translator)
		        .optProgress(new ProgressBar()).build();

		
		
		try {
			Model model = Model.newInstance("model name");
			model.load(Paths.get(modelPath));
			model = criteria.loadModel();
			predictor = model.newPredictor(translator);

			
			
			Image img = ImageFactory.getInstance().fromFile(Paths.get("D:\\testSets\\384_10x\\LYM\\LYM-N14-3029-K-Q2_768_4224.jpg"));
			img.getWrappedImage();
				
			
			Classifications classifications = predictor.predict(img);
			System.out.println(classifications.getAsString());
			
		} catch (ModelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranslateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

	

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}
