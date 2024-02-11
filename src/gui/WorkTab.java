package gui;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelCollection.YoloPytorchCIR;
import modelCollection.YoloPytorchRGB;
import vegetationIndices.Calculator;

import yolointerface.Detection;
import yolointerface.DetectionExecuter;
import yolointerface.ImageContainer;

public class WorkTab extends VBox {

	String groundTruthFolder = "C:\\Users\\felix\\Desktop\\FinalTraining\\testLabels";

	ImageContainer imageContainer = ImageContainer.instance();

	Calculator ndviCalculator = new Calculator();
	DetectionExecuter detector = DetectionExecuter.instance();
	ImageGridPane display;

	public WorkTab(ImageGridPane display) {

		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/WorkTab.css")));
		this.setId("parent");

		this.display = display;

		VBox progressBarContent = new VBox();
		progressBarContent.getStylesheets().add(String.valueOf(this.getClass().getResource("css/WorkTab.css")));
		progressBarContent.setId("popup");
		Label lblDetectionInProgress = new Label("Detection läuft");
		
		
		ProgressBar progressBar = new ProgressBar(0);
		progressBar.setStyle("-fx-accent: green;");
		Popup popup = new Popup();
		
		
		progressBarContent.getChildren().addAll(lblDetectionInProgress,progressBar);
		

		popup.getContent().add(progressBarContent);

		HBox backAndForBox = new HBox();

		Button btnPreviousImage = new Button("<<");
		btnPreviousImage.setOnAction(e -> {

			if (ImageContainer.getCurrentSelectedIndex() > 0) {
				display.rgbPane.clearBundingBoxes();
				display.cirPane.clearBundingBoxes();
				ImageContainer.setCurrentSelectedIndex(ImageContainer.getCurrentSelectedIndex() - 1);

				File currentRGBFile = ImageContainer.getRgbImages().get(ImageContainer.getCurrentSelectedIndex());
				File matchingCirFile = displaySelectedFile(currentRGBFile);
				if (ImageContainer.getRGBDetections().get(currentRGBFile) != null) {
					display.drawRGBBoundingBoxes(ImageContainer.getRGBDetections().get(currentRGBFile));

				}

				if (matchingCirFile != null && ImageContainer.getCIRDetections().get(matchingCirFile) != null) {
					display.drawCIRBoundingBoxes(ImageContainer.getCIRDetections().get(matchingCirFile));

				}

			}

		});
		Button btnNextImage = new Button(">>");
		btnNextImage.setOnAction(e -> {

			if (ImageContainer.getCurrentSelectedIndex() < ImageContainer.getRgbImages().size()) {
				display.rgbPane.clearBundingBoxes();
				display.cirPane.clearBundingBoxes();
				ImageContainer.setCurrentSelectedIndex(ImageContainer.getCurrentSelectedIndex() + 1);
				File currentRGBFile = ImageContainer.getRgbImages().get(ImageContainer.getCurrentSelectedIndex());
				File matchingCirFile = displaySelectedFile(currentRGBFile);
				if (ImageContainer.getRGBDetections().get(currentRGBFile) != null) {
					display.drawRGBBoundingBoxes(ImageContainer.getRGBDetections().get(currentRGBFile));
					// display.drawRGBGroundTruth(ImageContainer.getGroundtruth().get(currentRGBFile.getName()));
				}

				if (matchingCirFile != null && ImageContainer.getCIRDetections().get(matchingCirFile) != null) {
					display.drawCIRBoundingBoxes(ImageContainer.getCIRDetections().get(matchingCirFile));
					// display.drawCIRGroundTruth(ImageContainer.getGroundtruth().get(currentRGBFile.getName()));
				}

			}

		});

		ListView<File> fileList = new ListView<File>();
		fileList.setItems(ImageContainer.getRgbImages());
		fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {

				if (click.getClickCount() == 2) {

					display.rgbPane.clearBundingBoxes();
					display.cirPane.clearBundingBoxes();

					File currentItemSelected = fileList.getSelectionModel().getSelectedItem();
					File matchingCirFile = displaySelectedFile(currentItemSelected);

					if (ImageContainer.getRGBDetections().get(currentItemSelected) != null) {
						display.drawRGBBoundingBoxes(ImageContainer.getRGBDetections().get(currentItemSelected));
					}

					if (matchingCirFile != null && ImageContainer.getCIRDetections().get(matchingCirFile) != null) {
						display.drawCIRBoundingBoxes(ImageContainer.getCIRDetections().get(matchingCirFile));
					}

				}
			}
		});

		Label lblConfidenz = new Label("Konfidenz in %");

		Slider rgbCondfidenz = new Slider();
		rgbCondfidenz.setMin(0);
		rgbCondfidenz.setMax(100);
		rgbCondfidenz.setValue(20);
		rgbCondfidenz.setShowTickMarks(true);
		rgbCondfidenz.setShowTickLabels(true);
		rgbCondfidenz.setMinorTickCount(1);
		rgbCondfidenz.setMajorTickUnit(10);
		rgbCondfidenz.setBlockIncrement(5);
		rgbCondfidenz.setSnapToTicks(true);
		rgbCondfidenz.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				detector.setConfidenzNiveau(new_val.doubleValue() / 100);

			}
		});

		Button btnMakeClassification = new Button("Classify");
		btnMakeClassification.setOnAction(e -> {
			progressBar.progressProperty().unbind();
			progressBar.setProgress(0);

			Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
			double[] pos = progressBarPosition();
			progressBarContent.setPrefWidth(pos[2]);
			progressBarContent.setPrefHeight(pos[3]);
			progressBar.setPrefWidth(pos[2]);
			popup.show(owner, pos[0], pos[1]);
			
			YoloPytorchRGB yoloPT = new YoloPytorchRGB();

			

			// Bind progress property
			progressBar.progressProperty().bind(yoloPT.progressProperty());
			
			

			yoloPT.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
					new EventHandler<WorkerStateEvent>() {

						@Override
						public void handle(WorkerStateEvent t) {
							File rgbFile = ImageContainer.getRgbImages().get(ImageContainer.getCurrentSelectedIndex());
							display.drawRGBBoundingBoxes(ImageContainer.getRGBDetections().get(rgbFile));

							File matchingCirFile = getMatchingCirFile(rgbFile);
							if (matchingCirFile != null
									&& ImageContainer.getCIRDetections().get(matchingCirFile) != null) {
								display.drawCIRBoundingBoxes(ImageContainer.getCIRDetections().get(matchingCirFile));
							}
							popup.hide();
						}
					});

			// Start the Task.
			new Thread(yoloPT).start();

		});

		

		Label lblOpacity = new Label("Boundingbox Transparenz");

		Slider opacitySlider = new Slider();
		opacitySlider.setMin(0);
		opacitySlider.setMax(100);
		opacitySlider.setValue(100);
		opacitySlider.setShowTickMarks(true);
		opacitySlider.setShowTickLabels(true);
		opacitySlider.setMinorTickCount(1);
		opacitySlider.setMajorTickUnit(10);
		opacitySlider.setBlockIncrement(5);
		opacitySlider.setSnapToTicks(true);
		opacitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				display.changeOpacitys(new_val.doubleValue() / 100);

			}
		});

			

		backAndForBox.getChildren().addAll(btnPreviousImage, btnNextImage);

		this.getChildren().addAll(backAndForBox, fileList, lblConfidenz, rgbCondfidenz, btnMakeClassification,
				 lblOpacity, opacitySlider);

	}

	private File displaySelectedFile(File file) {
		try {
			display.showRGBImage(new Image(new FileInputStream(file)));
			ImageContainer.setCurrentSelectedIndex(ImageContainer.getRgbImages().indexOf(file));
			for (File cirFile : ImageContainer.getCirImages()) {
				if (cirFile.getName().equals(file.getName())) {

					FileInputStream fileInput = new FileInputStream(cirFile);

					display.showCIRImage(new Image(fileInput));
					ImageContainer.setCurrentSelectedCirIndex(ImageContainer.getCirImages().indexOf(cirFile));
					fileInput.close();
					return cirFile;
				}
			}
			Image notFoundPlaceholder = new Image("image-not-found.png");
			display.showCIRImage(notFoundPlaceholder);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private void displayNDVIImage(File image) {
		display.showNDVIImage(image);
	}

	

	

	

	private double[] progressBarPosition() {
		double[] pos = new double[4];
		Bounds bounds;
		Bounds screenBounds;

		bounds = display.getBoundsInLocal();
		screenBounds = display.localToScreen(bounds);
		
		

		double x = screenBounds.getMinX();
		double y = screenBounds.getMinY();
		double width = screenBounds.getWidth();
		double height = screenBounds.getHeight();
		double popupWidth = width*0.2;
		double popupHeight = height*0.1;
		pos[0] = x + (width / 2) - popupWidth/2;
		pos[1] = y + (height / 2) - popupHeight/2;
		pos[2] = popupWidth;
		pos[3] = popupHeight;
		return pos;

	}

	private File getMatchingCirFile(File rgbFile) {
		for (File cirFile : ImageContainer.getCirImages()) {
			if (cirFile.getName().equals(rgbFile.getName())) {

				ImageContainer.setCurrentSelectedCirIndex(ImageContainer.getCirImages().indexOf(cirFile));

				return cirFile;
			}
		}
		return null;
	}

}
