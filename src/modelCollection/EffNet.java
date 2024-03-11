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

	

	private String modelPath = "E:\\models\\kryo\\non-glial\\384_10x_pt\\model_60.pt";

	
	
	private ArrayList<String> synset = new ArrayList<String>();
	
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
			slidePrediction.calcSums();
			SlideContainer.addClassification(slidePrediction);
			System.out.println("name " + SlideContainer.getSlides().get(0).getName());
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
	
	public void process(String json) {
		String[] split = json.split(":");
		
		for (String s: split) {
			s = s.replaceAll("\\{", "");
			s = s.replaceAll("\\}", "");
			s = s.replaceAll("\\[", "");
			s = s.replaceAll("\\]", "");
			
			s = s.replaceAll("\n", "");
			s = s.strip();
			String[] split2 = s.split(",");
			
			System.out.println(split2[0]);
			//System.out.println(split2[3]);
			
		}
	}
	
	public void process2(String s) {
		
		
		
			s = s.replaceAll("\\{", "");
			s = s.replaceAll("\\}", "");
			s = s.replaceAll("\\[", "");
			s = s.replaceAll("\\]", "");
			
			s = s.replaceAll("\n", "");
			s = s.strip();
			//System.out.println(s);
			String[] split2 = s.split(",");
			
			
			
			for(int i = 0; i<split2.length; i++) {
				String s2 = split2[i];
				s2 = s2.trim();
				s2 = s2.replaceAll("\"", "");
				s2 = s2.split(":")[1].trim();
				if(i%2 == 0) {
					System.out.println("name " + s2);
				}else {
					System.out.println("prop " + s2);
				}	
			}
			//System.out.println(split2[3]);
			
		
	}
	
	


}
