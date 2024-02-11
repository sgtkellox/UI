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

	

	public List<Detection> executeRGBDetection(File img) throws IOException, InterruptedException {

		String command = "C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\darknet.exe detector test C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\data\\obj.data C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\cfg\\yolov4-obj.cfg C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\backup\\rgbmodel\\yolov4-obj_last.weights -thresh "
				+ Double.toString(confidenzNiveau) + " -dont_show -ext_output " + img.getAbsolutePath();
		Process proc = Runtime.getRuntime().exec(command, null,
				new File("C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64"));

		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line;
		ArrayList<Detection> detection = new ArrayList<Detection>();
		while ((line = br.readLine()) != null)
			if (line.startsWith("damagedTree")) {
				Detection bb = new Detection(extractLineInfo(line), ImageType.RGB);
				detection.add(bb);
			}

		while ((line = stdInput.readLine()) != null)
			if (line.startsWith("damagedTree")) {
				Detection bb = new Detection(extractLineInfo(line), ImageType.RGB);
				detection.add(bb);
			}

		proc.waitFor();

		ImageContainer.getRGBDetections().get(img).addAll(detection);

		return detection;

	}

	public void executeRGBDetectionOnFolder() throws IOException, InterruptedException {

		File input = this.makeInputFile(imageContainer.getRgbImages());
		File batFile = this.makeRGBBatFile(input);

		Process proc = Runtime.getRuntime().exec("cmd /c start /wait " + batFile.getAbsolutePath(), null,
				new File(this.getPath(batFile)));

		proc.waitFor();

		File result = new File(getPath(input) + "\\result.txt");

		readRGBResultFile(result);

		input.delete();
		batFile.delete();
		//result.delete();
	}

	public void executeCIRDetectionOnFolder() throws IOException, InterruptedException {

		File input = this.makeInputFile(imageContainer.getCirImages());
		File batFile = this.makeCIRBatFile(input);

		Process proc = Runtime.getRuntime().exec("cmd /c start /wait " + batFile.getAbsolutePath(), null,
				new File(this.getPath(batFile)));

		proc.waitFor();

		File result = new File(getPath(input) + "\\result.txt");
		readCIRResultFile(result);

		input.delete();
		batFile.delete();
		result.delete();
	}

	public List<Detection> executeCIRDetection(File img) throws IOException, InterruptedException {

		Calculator ndviCalculator = new Calculator(img);

		String command = "C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\darknet.exe detector test C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\data\\obj.data C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\cfg\\yolov4-obj.cfg C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64\\backup\\cirmodel\\yolov4-obj_last.weights -thresh "
				+ Double.toString(confidenzNiveau) + " -dont_show -ext_output " + img.getAbsolutePath();
		Process proc = Runtime.getRuntime().exec(command, null,
				new File("C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64"));

		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line;
		ArrayList<Detection> detection = new ArrayList<Detection>();
		while ((line = br.readLine()) != null)
			if (line.startsWith("damagedTree")) {
				Detection bb = new Detection(extractLineInfo(line), ImageType.CIR);
				double[] indizes = ndviCalculator.calcMeanNDVI(bb);
				bb.setMeanNDVI(indizes[0]);
				bb.setMeanGNDVI(indizes[1]);
				detection.add(bb);
			}

		while ((line = stdInput.readLine()) != null)
			if (line.startsWith("damagedTree")) {
				Detection bb = new Detection(extractLineInfo(line), ImageType.CIR);
				detection.add(bb);
			}

		proc.waitFor();

		ImageContainer.getCIRDetections().get(img).addAll(detection);

		return detection;

	}

	private File makeInputFile(List<File> images) {
		String path = getPath(images.get(0));
		try {
			File inputFile = new File(path + "\\input.txt");
			FileWriter myWriter = new FileWriter(inputFile);
			for (File image : images) {
				myWriter.write(image.getAbsolutePath());
				myWriter.write(System.getProperty("line.separator"));
			}

			myWriter.close();

			return inputFile;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private File makeRGBBatFile(File input) {
		String path = getPath(input);
		try {
			File batFile = new File(path + "\\yolo.bat");
			FileWriter myWriter = new FileWriter(batFile);
			myWriter.write("cd C:\\AI\\Yolo_v4\\darknet\\build\\darknet\\x64 ");
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(
					"darknet.exe detector test data/obj.data cfg/yoloDrone.cfg backup/rgb4LayersOrigBoxes/yoloDrone_best.weights -thresh "
							+ Double.toString(confidenzNiveau) + " -dont_show -ext_output < " + input.getAbsolutePath()
							+ " > " + path + "\\result.txt");
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write("exit 0");

			myWriter.close();

			return batFile;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private File makeCIRBatFile(File input) {
		String path = getPath(input);
		try {
			File batFile = new File(path + "\\yolo.bat");
			FileWriter myWriter = new FileWriter(batFile);
			myWriter.write("cd " + imageContainer.getCurrentSession().getYoloDir());
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(
					"darknet.exe detector test data/obj.data cfg/yoloDrone.cfg backup/cir4Layers/yoloDrone_best.weights -thresh "
							+ Double.toString(confidenzNiveau) + " -dont_show -ext_output < " + input.getAbsolutePath()
							+ " > " + path + "\\result.txt");
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write("exit 0");

			myWriter.close();

			return batFile;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String getPath(File file) {
		String path = "";
		String regEx4Win = "\\\\(?=[^\\\\]+$)";
		String[] tokens = file.getAbsolutePath().split(regEx4Win);
		if (tokens.length > 0) {
			path = tokens[0];
			return path;
		} else {
			return "";
		}

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
