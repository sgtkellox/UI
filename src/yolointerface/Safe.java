package yolointerface;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Safe {

	ImageContainer imagecontainer = ImageContainer.instance();

	String pathToRGBSafeFolder = "C:\\Users\\felix\\Desktop\\Demo\\res";
	String pathToCIRSafeFolder = "C:\\Users\\felix\\Desktop\\finalcir\\CIRResult";
	String pathToCIRImgFolder = "C:\\Users\\felix\\Desktop\\javaTest\\CIRAnnotations";
	String pathToRGBImgFolder = "C:\\Users\\felix\\Desktop\\Demo\\RGB";
	String pathToRGBLabelFilder = "C:\\Users\\felix\\Desktop\\FinalTraining\\results4";
	String pathToCIRLabelFilder = "C:\\Users\\felix\\Desktop\\finalcir\\results4";

	public Safe() {

	}

	public void createRGBAnnotationFile() throws IOException {

		for (File f : ImageContainer.getRGBDetections().keySet()) {

			String path = pathToRGBLabelFilder + "\\" + f.getName().replace(".jpg", ".txt");
			FileWriter myWriter = new FileWriter(path);

			for (Detection d : ImageContainer.getRGBDetections().get(f)) {
				myWriter.write(d.toAbsoluteAnnotation() + System.lineSeparator());
			}

			myWriter.close();

		}

	}

	public void createCIRAnnotationFile() throws IOException {

		for (File f : ImageContainer.getCIRDetections().keySet()) {

			String path = pathToCIRLabelFilder + "\\" + f.getName().replace(".jpg", ".txt");
			FileWriter myWriter = new FileWriter(path);

			for (Detection d : ImageContainer.getCIRDetections().get(f)) {
				myWriter.write(d.toAbsoluteAnnotation() + System.lineSeparator());
			}

			myWriter.close();

		}

	}

	public void loadRGBAnnotationFile(List<File> files) {
		for (File f : files) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));

				String path = pathToRGBImgFolder + "\\" + f.getName().replace(".txt", ".jpg");

				File imgFile = new File(path);

				Image image = new Image(path);
				String line = br.readLine();

				while (line != null) {
					Detection det = new Detection(extractLineInfo(line, image), ImageType.RGB);
					ImageContainer.getRGBDetections().get(imgFile).add(det);
				}

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadCIRAnnotationFile(List<File> files) {
		for (File f : files) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));

				String path = pathToCIRImgFolder + "\\" + f.getName().replace(".txt", ".jpg");

				File imgFile = new File(path);

				Image image = new Image(path);
				String line = br.readLine();

				while (line != null) {
					Detection det = new Detection(extractLineInfo(line, image), ImageType.RGB);
					ImageContainer.getCIRDetections().get(imgFile).add(det);
				}

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private int[] extractLineInfo(String line, Image image) {
		int[] info = new int[5];

		String[] parts = line.split(" ");

		info[4] = Integer.parseInt(parts[1]);
		info[0] = (int) (Double.parseDouble(parts[2]) * image.getWidth());
		info[1] = (int) (Double.parseDouble(parts[3]) * image.getHeight());
		info[2] = (int) (Double.parseDouble(parts[4]) * image.getWidth());
		info[3] = (int) (Double.parseDouble(parts[5]) * image.getHeight());

		return info;
	}

	public void safeRGBImages() throws IOException {
		for (Entry<File, ObservableList<Detection>> e : ImageContainer.getRGBDetections().entrySet()) {
			FileInputStream fileInput = new FileInputStream(e.getKey());
			Image image = new Image(fileInput);
			Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.drawImage(image, 0, 0);
			for (Detection d : e.getValue()) {
				gc.setStroke(Color.RED);
				gc.setLineWidth(3);
				gc.strokeRect(d.getX(), d.getY(), d.getWidth(), d.getHeight());
			}
			
			
			String path = this.pathToRGBSafeFolder + "\\" + insertIntoFileName(e.getKey()).replaceAll("jpg", "png");

			File safeFile = new File(path);
			
			Image img =  canvas.snapshot(null, null);
			RenderedImage renderedImage = SwingFXUtils.fromFXImage(img, null);
			ImageIO.write(renderedImage, "png", safeFile);
		}
	}

	public void safeCIRImages() throws IOException {
		for (Entry<File, ObservableList<Detection>> e : ImageContainer.getCIRDetections().entrySet()) {
			FileInputStream fileInput = new FileInputStream(e.getKey());
			Image image = new Image(fileInput);
			Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.drawImage(image, 0, 0);
			for (Detection d : e.getValue()) {
				gc.setStroke(Color.RED);
				gc.setLineWidth(3);
				gc.strokeRect(d.getX(), d.getY(), d.getWidth(), d.getHeight());
			}
			
			
			String path = this.pathToCIRSafeFolder + "\\" + insertIntoFileName(e.getKey()).replaceAll("jpg", "png");
			File safeFile = new File(path);
			Image img =  canvas.snapshot(null, null);
			RenderedImage renderedImage = SwingFXUtils.fromFXImage(img, null);
			ImageIO.write(renderedImage, "png", safeFile);
		}
	}

	private String insertIntoFileName(File file) {
		String fileName = file.getName();
		int lastDotIndex = fileName.lastIndexOf('.');
		return fileName.substring(0, lastDotIndex) + "annotated" + fileName.substring(lastDotIndex);

	}

}
