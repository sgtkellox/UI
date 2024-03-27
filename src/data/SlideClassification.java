package data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import modelCollection.ModelDefinition;

public class SlideClassification {
	
	private String slideClassification;
	private Slide slide;
	private List<TileClassification> tileClassifications;
	private List<String> possibleClassifications;
	private HashMap<String, Integer> sumConfidenzes; 
	private HashMap<String, Double> wheightedSumConfidenzes;
	private  List<TileClassification> bewlowTH;
	
	private ModelDefinition usedModel;
	
	public SlideClassification() {
		possibleClassifications = new ArrayList<String>();
		tileClassifications = new ArrayList<TileClassification>();
		wheightedSumConfidenzes = new HashMap<String, Double>();
		sumConfidenzes = new HashMap<String, Integer>();
		bewlowTH = new ArrayList<TileClassification>();
	}
	public SlideClassification(List<String> possibleClassifications) {
		this.possibleClassifications = possibleClassifications;
		tileClassifications = new ArrayList<TileClassification>();
		wheightedSumConfidenzes = new HashMap<String, Double>();
		sumConfidenzes = new HashMap<String, Integer>();
		bewlowTH = new ArrayList<TileClassification>();
		
	}

	public List<TileClassification> getTileClassifications() {
		return tileClassifications;
	}
	public void setTileClassifications(List<TileClassification> tileClassifications) {
		this.tileClassifications = tileClassifications;
	}
	public String getSlideClassification() {
		return slideClassification;
	}
	public void setSlideClassification(String slideClassification) {
		this.slideClassification = slideClassification;
	}
	
	public List<String> getPossibleClassifications() {
		return possibleClassifications;
	}

	public void setPossibleClassifications(List<String> possibleClassifications) {
		this.possibleClassifications = possibleClassifications;
	}
	public Slide getSlide() {
		return slide;
	}

	public void setSlide(Slide slide) {
		this.slide = slide;
	}
	
	public HashMap<String, Integer> getSumConfidenzes() {
		return sumConfidenzes;
	}

	public HashMap<String, Double> getWheightedSumConfidenzes() {
		return wheightedSumConfidenzes;
	}
	

	
	public void calcWeightedSums() {
		
		for(String label: this.possibleClassifications) {
			wheightedSumConfidenzes.put(label, 0.0);
			
		}
		for(TileClassification tileClassification: tileClassifications) {
			for(Entry<String, Double> e :tileClassification.getPropabilities().entrySet() ) {				
				wheightedSumConfidenzes.put(e.getKey(), wheightedSumConfidenzes.get(e.getKey()) + e.getValue());  
			}
		}	
	}
	
	public void calcSums(double conf) {
		for(String label: this.possibleClassifications) {
			sumConfidenzes.put(label, 0);		
		}
		for(TileClassification tileClassification: tileClassifications) {
			if(tileClassification.getMaxConf()>conf) {
				String label = tileClassification.getBest();
				sumConfidenzes.put(label, sumConfidenzes.get(label) + 1);
			}else {
				bewlowTH.add(tileClassification);
			}
			  
		}	
	}
	
	
	
	public ModelDefinition getUsedModel() {
		return usedModel;
	}
	public void setUsedModel(ModelDefinition usedModel) {
		this.usedModel = usedModel;
	}
	
	public HashMap<String,Double> applyThreshOldWEighted(double conf){
		HashMap<String,Double> updateWeighted = new HashMap<String, Double>(wheightedSumConfidenzes) ;
		
		
		for(TileClassification tileClassification: tileClassifications) {
			if(tileClassification.getMaxConf()> conf) {
				
				for(Entry<String, Double> e :tileClassification.getPropabilities().entrySet() ) {				
					updateWeighted.put(e.getKey(), updateWeighted.get(e.getKey()) - e.getValue());
				}
			}
			
		}
		
		return updateWeighted;
	}
	
	public void applyThreshOld(double conf){	
		for(TileClassification tileClassification: tileClassifications) {
			if(tileClassification.getMaxConf()> conf) {
				bewlowTH.add(tileClassification);				
			}			
		}		
	}
	
	public void restSumsDown(double conf){
			
		for(TileClassification tileClassification: bewlowTH) {
			if(tileClassification.getMaxConf()> conf) {
				String label =tileClassification.getBest();
				sumConfidenzes.put(label, sumConfidenzes.get(label) +1);
				System.out.println(tileClassification.getTile().getPath()+ " added th= "+ Double.toString(tileClassification.getMaxConf()) + " and conf= " + Double.toString(conf));				
				bewlowTH.remove(tileClassification);
			}
			
		}	
	}
	
	public void restSumsUp(double conf){
		for(TileClassification tileClassification: tileClassifications) {
			if(tileClassification.getMaxConf()< conf &&!bewlowTH.contains(tileClassification)) {
				String label =tileClassification.getBest();
				sumConfidenzes.put(label, sumConfidenzes.get(label) -1);
				System.out.println(tileClassification.getTile().getPath()+ " removed th= "+ Double.toString(tileClassification.getMaxConf()) + " and conf= " + Double.toString(conf) );				
				bewlowTH.add(tileClassification);
			}
			
		}	
	}
	
	public void sortByConfidenz() {
		Collections.sort(tileClassifications);
	}
	
	

	

	
	

	

}


