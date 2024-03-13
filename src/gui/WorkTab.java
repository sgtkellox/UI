package gui;

import org.controlsfx.control.CheckComboBox;

import data.Slide;
import data.SlideContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelCollection.EffNet;
import modelCollection.ModelContainer;
import modelCollection.ModelDefinition;
import yolointerface.DetectionExecuter;


public class WorkTab extends VBox {

	SlideContainer container = SlideContainer.instance();
	ModelContainer modelContainer = ModelContainer.instance();
	DetectionExecuter detector = DetectionExecuter.instance();
	ImageGridPane display;

	public WorkTab(ImageGridPane display) {

		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/WorkTab.css")));
		this.setId("parent");

		this.display = display;

		
		

		HBox backAndForBox = new HBox();
		
		

		Button btnPreviousImage = new Button("<<");
		btnPreviousImage.setOnAction(e -> {

			if (SlideContainer.getCurrentSelectedIndex() > 0) {
				int currentIndex = SlideContainer.getCurrentSelectedIndex() - 1;
				SlideContainer.setCurrentSelectedIndex(currentIndex);
				if(!SlideContainer.getSlides().get(currentIndex).getClassifications().isEmpty()) {
					display.statsView.showStats(SlideContainer.getSlides().get(currentIndex).getClassifications().get(0));
					display.showMap(SlideContainer.getSlides().get(currentIndex).getClassifications().get(0));
					
				}else {
					display.statsView.clear();
					display.mapView.showPlaceholder();
					
				}

				
				

			}

		});
		Button btnNextImage = new Button(">>");
		btnNextImage.setOnAction(e -> {

			if (SlideContainer.getCurrentSelectedIndex() < SlideContainer.getSlides().size()-1) {
				int currentIndex = SlideContainer.getCurrentSelectedIndex() + 1;
				SlideContainer.setCurrentSelectedIndex(currentIndex);
				if(!SlideContainer.getSlides().get(currentIndex).getClassifications().isEmpty()) {
					display.statsView.showStats(SlideContainer.getSlides().get(currentIndex).getClassifications().get(0));
					display.showMap(SlideContainer.getSlides().get(currentIndex).getClassifications().get(0));
					
				}else {
					display.statsView.clear();
					display.mapView.showPlaceholder();
					
				}
				
			}

		});
		
		

		ListView<Slide> fileList = new ListView<Slide>();
		fileList.setItems(SlideContainer.getSlides());
				
		fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {

				if (click.getClickCount() == 2) {
					
					Slide currentItemSelected = fileList.getSelectionModel().getSelectedItem();
					int index = SlideContainer.getSlides().indexOf(currentItemSelected);
					SlideContainer.setCurrentSelectedIndex(index);
					callClassifier();
						
				}
				
				else if (click.getClickCount() == 1) {
					
					Slide currentItemSelected = fileList.getSelectionModel().getSelectedItem();
					int index = SlideContainer.getSlides().indexOf(currentItemSelected);
					SlideContainer.setCurrentSelectedIndex(index);
						
				}
			}
		});
		
		//select Model combobox
		
		 // Create the CheckComboBox with the data 
		 final CheckComboBox<ModelDefinition> modelComboBox = new CheckComboBox<ModelDefinition>(ModelContainer.getModels());
		 
		 // and listen to the relevant events (e.g. when the selected indices or 
		 // selected items change).
		 modelComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<ModelDefinition>() {
		     public void onChanged(ListChangeListener.Change<? extends ModelDefinition> c) {
		         System.out.println(modelComboBox.getCheckModel().getCheckedItems());
		     }
		 });
		 modelComboBox.setTitle("Select a Model");
		 
		 modelComboBox.setMaxWidth(Double.MAX_VALUE);
		 
		 
		
		
		//confidenz niveau slider 
		Label lblConfidenz = new Label("Confidenz in %");
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
			
			callClassifier();
			
		});
		
		HBox boxCLassifyButton = new HBox();
		boxCLassifyButton.setAlignment(Pos.BASELINE_RIGHT);
		boxCLassifyButton.getChildren().add(btnMakeClassification);
		backAndForBox.getChildren().addAll(btnPreviousImage, btnNextImage);

		this.getChildren().addAll(backAndForBox, fileList, lblConfidenz, rgbCondfidenz,modelComboBox, boxCLassifyButton);

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

	
	private void callClassifier() {
		
		VBox progressBarContent = new VBox();
		progressBarContent.getStylesheets().add(String.valueOf(this.getClass().getResource("css/WorkTab.css")));
		progressBarContent.setId("popup");
		Label lblDetectionInProgress = new Label("processing tiles");
		
		
		ProgressBar progressBar = new ProgressBar(0);
		progressBar.setStyle("-fx-accent: #00486f;");
		Popup popup = new Popup();
		
		
		progressBarContent.getChildren().addAll(lblDetectionInProgress,progressBar);
		

		popup.getContent().add(progressBarContent);
		
		progressBar.progressProperty().unbind();
		progressBar.setProgress(0);
		EffNet effNet = new EffNet();
		
		effNet.setSlide(SlideContainer.getSlides().get(SlideContainer.getCurrentSelectedIndex()));

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
						display.statsView.showStats(SlideContainer.getSlides().get(SlideContainer.getCurrentSelectedIndex()).getClassifications().get(0));
						display.showMap(SlideContainer.getSlides().get(SlideContainer.getCurrentSelectedIndex()).getClassifications().get(0));
						
					}
				});

		// Start the Task.
		new Thread(effNet).start();
	}
}
