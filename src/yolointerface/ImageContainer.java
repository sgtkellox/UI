package yolointerface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import application.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageContainer {

	private static ImageContainer imageContainer = null;

	private static final ObservableList<File> rgbImages = FXCollections.observableArrayList();
	private static final ObservableList<File> cirImages = FXCollections.observableArrayList();
	private static final HashMap<File, ObservableList<Detection>> cirDetections = new HashMap<File, ObservableList<Detection>>();
	private static final HashMap<File, ObservableList<Detection>> rgbDetections = new HashMap<File, ObservableList<Detection>>();
	private static final HashMap<String, ArrayList<GroundTruth>> groundTruth = new HashMap<String, ArrayList<GroundTruth>>();
	private static int currentSelectedIndex = 0;
	private static int currentSelectedCirIndex = 0;
	
	private static Session currentSession;

	private ImageContainer() {

	}

	public static ImageContainer instance() {
		if (imageContainer != null) {
			return imageContainer;
		} else {
			return new ImageContainer();
		}
	}

	public static ObservableList<File> getRgbImages() {
		return rgbImages;
	}

	public static ObservableList<File> getCirImages() {
		return cirImages;
	}

	public static int getCurrentSelectedIndex() {
		return currentSelectedIndex;
	}

	public static void setCurrentSelectedIndex(int currentSelectedIndex) {
		
		ImageContainer.currentSelectedIndex = currentSelectedIndex;
	}

	public static int getCurrentSelectedCirIndex() {
		return currentSelectedCirIndex;
	}

	public static void setCurrentSelectedCirIndex(int currentSelectedCirIndex) {
		ImageContainer.currentSelectedCirIndex = currentSelectedCirIndex;
	}

	public static HashMap<File, ObservableList<Detection>> getCIRDetections() {
		return cirDetections;
	}

	public static HashMap<File, ObservableList<Detection>> getRGBDetections() {
		return rgbDetections;
	}

	public void preFillRGBDetections(ArrayList<File> files) {
		for (File f : files) {
			rgbDetections.put(f, FXCollections.observableArrayList());
		}
	}

	public void preFillCIRDetections(ArrayList<File> files) {
		for (File f : files) {
			cirDetections.put(f, FXCollections.observableArrayList());
		}
	}

	public void printDetections() {
		for (Entry<File, ObservableList<Detection>> e : rgbDetections.entrySet()) {
			System.out.println(e.getKey().getAbsolutePath());
			if (e.getValue() != null) {
				for (Detection d : e.getValue()) {

					System.out.println(d.toString());

				}
			} else {
				System.out.println("null");
			}

		}
	}
	
	public void clearRGBDetections() {
		for(Entry<File, ObservableList<Detection>> e : rgbDetections.entrySet()) {
			e.getValue().clear();
		}
	}
	
	public void clearCIRDetections() {
		for(Entry<File, ObservableList<Detection>> e : cirDetections.entrySet()) {
			e.getValue().clear();
		}
	}

	public static HashMap<String, ArrayList<GroundTruth>> getGroundtruth() {
		return groundTruth;
	}

	public static Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		ImageContainer.currentSession = currentSession;
	}
	
	public void insertRGBFiles(File[] files) {
		ArrayList<File> filteredFiles = filterImageList(files);
		rgbImages.addAll(filteredFiles);
		preFillRGBDetections(filteredFiles);
	}
	
	public void insertCIRFiles(File[] files) {
		ArrayList<File> filteredFiles = filterImageList(files);
		cirImages.addAll(filteredFiles);
		preFillRGBDetections(filteredFiles);
	}
	
	private ArrayList<File> filterImageList(File[] files) {
		ArrayList<File> filtered = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getAbsolutePath().endsWith(".jpg") || files[i].getAbsolutePath().endsWith(".png")
					|| files[i].getAbsolutePath().endsWith(".JPG")) {
				filtered.add(files[i]);
			}
		}
		return filtered;
	}
}
