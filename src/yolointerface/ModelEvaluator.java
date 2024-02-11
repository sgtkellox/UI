package yolointerface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ModelEvaluator {
	
	ImageContainer imageContainer = ImageContainer.instance();
	
	private int imgWidth = 1254;
	private int imgHeight = 921;
	
	private int falsePositives;
	private int falseNegatives;
	private int truePositives;
	
	String labelFolder = "C:\\Users\\felix\\Desktop\\TrattenbergLabeled\\labels";
	
	
	public ModelEvaluator() {
		
	}
	
	
	public void evaluate() {
		for(File f : ImageContainer.getRGBDetections().keySet()) {
			
		}
	}
	
	private void evaluateFile(File f) throws IOException {
		String fileName = f.getName().replaceFirst(".jpg", ".txt");
		File groundTruthFile = new File(labelFolder+"\\"+fileName);
		List<Rect> groundTruthLabels = readGroundTruth(groundTruthFile);
	}
	
	private void assignDetectionToGroundTruth(List<Detection> detections, List<Rect> groundTruths) {
		
		List<Detection> unmatchedDetections = new ArrayList<Detection>(detections);
		List<Detection> detectionsWithoutLabel = new ArrayList<Detection>(detections);
		HashMap<Rect,Double> labelsAndIOUs = new HashMap<Rect,Double>();
		HashMap<Rect,Detection> labelsAndDetections = new HashMap<Rect,Detection>();
		
		for(Rect label:groundTruths ) {
			labelsAndIOUs.put(label, 0.0);
			labelsAndDetections.put(label, null);
		}
		
		while(!unmatchedDetections.isEmpty()||everyLabelHasIOU(labelsAndIOUs)) {
			List<Detection> currentlyToCheck = new ArrayList<Detection>(detections);
			for(Detection d: currentlyToCheck) {
				double maxFit = 0;
				Rect mate = null;
				int nullCounter = 0;
				for(Rect label: groundTruths) {
					Rect intersect = calcIntersect(d,label);
					if(intersect!=null) {
						double iou = intersect.calcSize()/calcUnion(d,label,intersect);
						if(iou>maxFit) {
							maxFit = iou;
							mate = label;
						}
					}else {
						nullCounter++;
					}
					
				}
				if(nullCounter==groundTruths.size()) {
					detectionsWithoutLabel.add(d);
					unmatchedDetections.remove(d);
					continue;
				}
				if(labelsAndIOUs.get(mate)<maxFit) {
					labelsAndIOUs.put(mate, maxFit);
					if(labelsAndDetections.get(mate)!= null) {
						unmatchedDetections.add(labelsAndDetections.get(mate));
					}
					labelsAndDetections.put(mate,d);
					unmatchedDetections.remove(d);
				}
			}
		}
		int falseNegativesCounter = 0;
		for(Rect rect :labelsAndIOUs.keySet()) {
			if(labelsAndIOUs.get(rect)==0.0) {
				falseNegativesCounter++;
			}
		}
		
		
		
		
		
	}
	
	private boolean everyLabelHasIOU(HashMap<Rect,Double> map) {
		int zeroCounter = 0;
		for(double iou: map.values()) {
			if(iou==0) {
				zeroCounter++;
			}
		}
		if(zeroCounter==0) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	private Rect calcIntersect(Detection detection, Rect groundTruth) {
		
		int x2 = detection.getX() + detection.getWidth();
		int x4 = groundTruth.getX() + groundTruth.getWidth();
		int y1 = detection.getY() - detection.getHeight();
		int y3 = groundTruth.getY() - groundTruth.getHeight();

		
		int xL = Math.max(detection.getX(), groundTruth.getX());
		int xR = Math.min(x2, x4);
		if (xR <= xL)
		    return null;
		else {
		    int yT = Math.max(y1, y3);
		    int yB = Math.min(detection.getY(), groundTruth.getY());
		    if (yB <= yT)
		        return null;
		    else
		        return new Rect(xL, yB, xR-xL, yB-yT);
		}
		
	}
	
	private int calcUnion(Detection detection, Rect groundTruth, Rect intersect) {
		return (detection.calcSize()+groundTruth.calcSize())-intersect.calcSize();
	}
	
	
	
	private ArrayList<Rect> readGroundTruth(File f) throws IOException {
		
		 	ArrayList<Rect> boxes = new ArrayList<Rect>();
			BufferedReader br = new BufferedReader(new FileReader(f));
		    
		    String line = br.readLine();

		    while (line != null) {
		    	
		    	String[] parts = line.split(" ");
		    	int x = (int) (Double.valueOf(parts[1])*imgWidth);
		    	int y = (int) (Double.valueOf(parts[2])*imgWidth);
		    	int width = (int) (Double.valueOf(parts[3])*imgHeight);
		    	int height = (int) (Double.valueOf(parts[4])*imgHeight);
		    	Rect groundTruth = new Rect(x,y,width,height);
		    	boxes.add(groundTruth);
		        line = br.readLine();
		    }
		    
		    br.close();
		    return boxes;
		    
	}


	public int getFalsePositives() {
		return falsePositives;
	}


	public void setFalsePositives(int falsePositives) {
		this.falsePositives = falsePositives;
	}


	public int getFalseNegatives() {
		return falseNegatives;
	}


	public void setFalseNegatives(int falseNegatives) {
		this.falseNegatives = falseNegatives;
	}


	public int getTruePositives() {
		return truePositives;
	}


	public void setTruePositives(int truePositives) {
		this.truePositives = truePositives;
	}

}
