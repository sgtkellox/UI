package gui;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import data.Slide;
import data.SlideContainer;
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
import modelCollection.EffNet;
import modelCollection.YoloPytorchCIR;
import modelCollection.YoloPytorchRGB;
import vegetationIndices.Calculator;

import yolointerface.Detection;
import yolointerface.DetectionExecuter;


public class WorkTab extends VBox {

	SlideContainer container = SlideContainer.instance();
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

			if (SlideContainer.getCurrentSelectedIndex() > 0) {
				
				SlideContainer.setCurrentSelectedIndex(SlideContainer.getCurrentSelectedIndex() - 1);

				
				

			}

		});
		Button btnNextImage = new Button(">>");
		btnNextImage.setOnAction(e -> {

			if (SlideContainer.getCurrentSelectedIndex() < SlideContainer.getSlides().size()) {
				
				SlideContainer.setCurrentSelectedIndex(SlideContainer.getCurrentSelectedIndex() + 1);
				

			}

		});
		
		

		ListView<Slide> fileList = new ListView<Slide>();
		fileList.setItems(SlideContainer.getSlides());
		System.out.println(SlideContainer.getSlides().toString());
		
		
		
		fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {

				if (click.getClickCount() == 2) {

					
					Slide currentItemSelected = fileList.getSelectionModel().getSelectedItem();
					
					
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
			EffNet effNet = new EffNet();
			
			effNet.setSlide(SlideContainer.getSlides().get(0));

			Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
			double[] pos = progressBarPosition();
			progressBarContent.setPrefWidth(pos[2]);
			progressBarContent.setPrefHeight(pos[3]);
			progressBar.setPrefWidth(pos[2]);
			popup.show(owner, pos[0], pos[1]);
			
			

		
			// Bind progress property
			progressBar.progressProperty().bind(effNet.progressProperty());
			
			

			effNet.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
					new EventHandler<WorkerStateEvent>() {

						@Override
						public void handle(WorkerStateEvent t) {
							
							
							popup.hide();
						}
					});

			// Start the Task.
			new Thread(effNet).start();

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
		return null;

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

	

}
