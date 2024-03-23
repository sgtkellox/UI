package modelCollection;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.*;
import ai.djl.modality.*;
import ai.djl.modality.cv.*;
import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;
import data.Slide;
import data.SlideClassification;
import data.SlideContainer;
import data.Tile;
import data.TileClassification;
import ai.djl.training.util.*;
import javafx.concurrent.Task;



public class EffNet extends Task<Object> {

	Predictor<Image, Classifications> predictor;

	

	private String modelPath = "C:\\Users\\felix\\Desktop\\model_60.pt";

	
	
	private ArrayList<String> synset = new ArrayList<String>();
	
	private double conf;
	
	private Slide slide;
	

	public EffNet() {
		synset.add("LYM");
		synset.add("MB");
		synset.add("MEL");
		synset.add("MEN");
		synset.add("MET");
		synset.add("PIT");
		synset.add("SCHW");
		
		

	}

	@Override
	protected Object call() throws Exception {
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
		        .optDevice(Device.gpu())
		        .optProgress(new ProgressBar()).build();

		
		
		try {
			Model model = Model.newInstance("model name");
			model.load(Paths.get(modelPath));
			model = criteria.loadModel();
			predictor = model.newPredictor(translator);
			
			int count = slide.getTiles().size();

			int index = 0;
			
			SlideClassification slidePrediction = new SlideClassification(synset);
			slidePrediction.setSlide(slide);
			
			for (Tile tile : slide.getTiles()) {
								
				Image img = ImageFactory.getInstance().fromFile(Paths.get(tile.getPath()));
				img.getWrappedImage();
				
				Classifications classifications = predictor.predict(img);
				
				String json = classifications.getAsString();
				
				
				
				TileClassification tileClassification = new TileClassification(tile, json);
				
				
				
				slidePrediction.getTileClassifications().add(tileClassification);
				
				
				index++;
				this.updateProgress(index, count);
				
								
			}	
			slidePrediction.calcWeightedSums();
			slidePrediction.calcSums(conf);
			SlideContainer.addClassification(slidePrediction);
			
			//model.close();
			
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

		return null;
	}
	
	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public Slide getSlide() {
		return slide;
	}

	public void setSlide(Slide slide) {
		this.slide = slide;
	}

	public double getConf() {
		return conf;
	}

	public void setConf(double conf) {
		this.conf = conf;
	}
	
	

}
