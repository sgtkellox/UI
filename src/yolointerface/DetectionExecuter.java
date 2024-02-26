package yolointerface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vegetationIndices.Calculator;


public class DetectionExecuter {

	
	private double confidenzNiveau = 0.3;

	ImageContainer imageContainer = ImageContainer.instance();
	
	private static DetectionExecuter detectionExecutor = null;
	
	

	private DetectionExecuter() {

	}
	
	
	public static DetectionExecuter instance() {
		if(detectionExecutor== null) {
			return new DetectionExecuter();
		}else {
			return detectionExecutor;
		}
	}
	
	public double getConfidenzNiveau() {
		return confidenzNiveau;
	}

	public void setConfidenzNiveau(double confidenzNiveao) {
		this.confidenzNiveau = confidenzNiveao;
	}

	

	

	

	

	

	
	

	

	public void readRGBResultFile(File result) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(result));
		
		String line = br.readLine();

		while (line != null) {
			if (line.startsWith("Enter Image Path")) {
				ArrayList<Detection> detections = new ArrayList<Detection>();
				line = br.readLine();
				line = br.readLine();
				line = br.readLine();
				line = br.readLine();
				String path = "";
				if (line != null) {
					String[] split = line.split(":");
					path = split[0] + ":" + split[1];

				} else {
					break;
				}

				line = br.readLine();

				while (line.startsWith("damagedTree")) {
					Detection bb = new Detection(extractLineInfo(line), ImageType.RGB);
					detections.add(bb);
					line = br.readLine();
				}
				ImageContainer.getRGBDetections().get(new File(path)).addAll(removeRedundantDetections(detections));

			} else {
				line = br.readLine();
			}

		}
		br.close();

	}

	public void readCIRResultFile(File result) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(result));
		String line = br.readLine();
		while (line != null) {
			if (line.startsWith("Enter Image Path")) {
				ArrayList<Detection> detection = new ArrayList<Detection>();
				line = br.readLine();
				line = br.readLine();
				line = br.readLine();
				line = br.readLine();
				String path = "";
				if (line != null) {
					String[] split = line.split(":");
					path = split[0] + ":" + split[1];
				} else {
					break;
				}
				line = br.readLine();
				while (line.startsWith("damagedTree")) {
					Detection bb = new Detection(extractLineInfo(line), ImageType.CIR);
					detection.add(bb);
					line = br.readLine();
				}

				File imageFile = new File(path);
				ImageContainer.getCIRDetections().get(imageFile).addAll(removeRedundantDetections(detection));
				Calculator ndviCalculator = new Calculator(imageFile);
				for (Detection d : ImageContainer.getCIRDetections().get(imageFile)) {
					double[] indizes = ndviCalculator.calcMeanNDVI(d);
					d.setMeanNDVI(indizes[0]);
					d.setMeanGNDVI(indizes[1]);
				}
			} else {
				line = br.readLine();
			}

		}
		br.close();
	}

	private void readGroundTruthFile(File file) throws IOException {
		String imgFileName = file.getName().replaceAll(".txt", ".jpg");
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList<GroundTruth> groundTruths = new ArrayList<GroundTruth>();
		String line = br.readLine();
		while (line != null) {
			String[] parts = line.split("\\s+");
			GroundTruth gd = new GroundTruth(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]),
					Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
			groundTruths.add(gd);
			line = br.readLine();
		}
		ImageContainer.getGroundtruth().put(imgFileName, groundTruths);
		br.close();
	}

	public void readGroundTruths(File folder) throws IOException {
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				readGroundTruthFile(listOfFiles[i]);
			}
		}

//		for(Entry e : ImageContainer.getGroundtruth().entrySet()) {
//			System.out.println(e.getKey());
//			
//			for(GroundTruth g :ImageContainer.getGroundtruth().get(e.getKey()) ) {
//				System.out.println(g.toString());
//			}
//		}
	}

	private int[] extractLineInfo(String line) {
		int[] info = new int[5];

		String[] parts = line.split("\\s+");

		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].replaceAll("[^0-9]", "");
		}

		info[4] = Integer.parseInt(parts[1]);
		info[0] = Integer.parseInt(parts[3]);
		info[1] = Integer.parseInt(parts[5]);
		info[2] = Integer.parseInt(parts[7]);
		info[3] = Integer.parseInt(parts[9]);

		return info;
	}

	private List<Detection> removeRedundantDetections(List<Detection> detections) {
		
		Collections.sort(detections, Collections.reverseOrder());
		
		List<Detection> filtered = new ArrayList<Detection>();
		filtered.addAll(detections);
		List<Detection> toRemove = new ArrayList<Detection>();
		boolean nothingToRemove = true;
		for (Detection d : detections) {
			int i = filtered.size()-1;
			while(!d.equals(filtered.get(i))) {
				if(isSubDetection(d,filtered.get(i))) {
					toRemove.add(filtered.get(i));
				}
				if(i>0) {
					i--;
				}else {
					break;
				}
				
			}
			
			toRemove.stream().forEach(o -> filtered.remove(o));
			Collections.sort(filtered, Collections.reverseOrder());
			toRemove.clear();
		}
		
		return filtered;
	}
	
	private boolean isSubDetection(Detection detection1, Rect detection2) {
		Rect intersect = calcIntersect(detection1,  detection2);
		
		if(intersect!= null) {
			if((double)intersect.calcSize()/(double)detection2.calcSize()>0.7) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}

	private Rect calcIntersect(Detection detection1, Rect detection2) {

		int x2 = detection1.getX() + detection1.getWidth();
		int x4 = detection2.getX() + detection2.getWidth();
		int y1 = detection1.getY() + detection1.getHeight();
		int y3 = detection2.getY() + detection2.getHeight();

		int xL = Math.max(detection1.getX(), detection2.getX());
		int xR = Math.min(x2, x4);
		if (xR <= xL)
			return null;
		else {
			int yT = Math.max(detection1.getY(), detection2.getY());
			int yB = Math.min(y1, y3);
			if (yB <= yT)
				return null;
			else
				return new Rect(xL, yT, xR - xL, yB - yT);
		}

	}
}
