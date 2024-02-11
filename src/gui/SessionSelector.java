package gui;

import java.io.File;
import java.util.ArrayList;

import application.Session;
import database.DataBaseConector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import yolointerface.DetectionExecuter;
import yolointerface.ImageContainer;

public class SessionSelector extends SplitPane {

	ImageContainer imageContainer = ImageContainer.instance();
	DetectionExecuter detector;

	DataBaseConector dataBase = new DataBaseConector();

	public SessionSelector() {

		this.setOrientation(Orientation.VERTICAL);

		VBox topBox = new VBox();
		VBox botBox = new VBox();

		HBox topHeaderBox = new HBox();
		HBox botHeaderBox = new HBox();
		HBox nextBox = new HBox();

		Label lblNewSession = new Label("Neues Projekt");

		// apply css
		this.getStyleClass().add("sessionSelector");
		this.setId("sessionSelector");

		topBox.getStyleClass().add("vbox");

		topHeaderBox.getStyleClass().add("header");
		botHeaderBox.getStyleClass().add("header");

		nextBox.getStyleClass().add("next");

		lblNewSession.getStyleClass().add("caption");
		
		Label lblProjectName = new Label("Projektname");
		TextField tfProjectName = new TextField("Projektname");
		tfProjectName.setOnMouseClicked(e->{
			tfProjectName.setStyle("-fx-text-inner-color: black;");
		});

		TextField lblRGBPath = new TextField("RGB Verzeichnis");
		Button btnSelectRGBDirectory = new Button("RGB Verzeichnis auswählen");
		btnSelectRGBDirectory.setOnAction(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();

			File selectedDirectory = directoryChooser.showDialog(new Stage());

			if (selectedDirectory == null) {
				return;
			}

			imageContainer.insertRGBFiles(selectedDirectory.listFiles());

			lblRGBPath.setText(selectedDirectory.getAbsolutePath());

		});

		TextField lblCIRPath = new TextField("CIR Verzeichnis");
		Button btnSelectCIRDirectory = new Button("Cir Verzeichnis auswählen");
		btnSelectCIRDirectory.setOnAction(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(new Stage());

			if (selectedDirectory == null) {
				return;
			}
			
			imageContainer.insertCIRFiles(selectedDirectory.listFiles());

			lblCIRPath.setText(selectedDirectory.getAbsolutePath());

		});

		TextField lblYoloDirectory = new TextField("Yolo Verzeichnis");
		Button btnSelectYoloDirectory = new Button("YOLO Verzeichnis wählen");
		btnSelectYoloDirectory.setOnAction(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(new Stage());
			lblYoloDirectory.setText(selectedDirectory.getAbsolutePath());
			
		});

		TextField lblRGBModelLocation = new TextField("RGB Modell");
		Button btnSelectRGBModell = new Button("RGB Modell wählen");
		btnSelectRGBModell.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File selectedDirectory = fileChooser.showOpenDialog(new Stage());
			lblRGBModelLocation.setText(selectedDirectory.getAbsolutePath());
			
		});

		TextField lblCIRModelLocation = new TextField("CIR Modell ");
		Button btnSelectCirDirectory = new Button("CIR Modell wählen");
		btnSelectCirDirectory.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File selectedDirectory = fileChooser.showOpenDialog(new Stage());
			lblCIRModelLocation.setText(selectedDirectory.getAbsolutePath());
			
		});

		Button btnNext = new Button("weiter");
		btnNext.setOnAction(e -> {
			if(tfProjectName.getText()!=null && tfProjectName.getText().strip()!="") {
				Session currentSession = new Session(tfProjectName.getText(),lblRGBPath.getText(), lblCIRPath.getText(), lblYoloDirectory.getText(),
						lblRGBModelLocation.getText(), lblCIRModelLocation.getText());
				this.imageContainer.setCurrentSession(currentSession);
				int key = dataBase.safeSession(currentSession);
				currentSession.setId(key);
				ParentPlane root = new ParentPlane();
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}else {
				tfProjectName.setStyle("-fx-text-inner-color: red;");
				tfProjectName.setText("Projektname angeben");
			}
			
		});

		nextBox.getChildren().add(btnNext);

		topHeaderBox.getChildren().add(lblNewSession);

		topBox.getChildren().addAll(topHeaderBox, lblProjectName, tfProjectName,btnSelectRGBDirectory, lblRGBPath, btnSelectCIRDirectory, lblCIRPath,
				btnSelectYoloDirectory, lblYoloDirectory, btnSelectRGBModell, lblRGBModelLocation,
				btnSelectCirDirectory, lblCIRModelLocation, nextBox);

		// bottom part of splitpane

		Label lblLoadSession = new Label("Projekt Laden");

		// some more css

		lblLoadSession.getStyleClass().add("caption");

		ObservableList sessionList = FXCollections.observableList(dataBase.loadSessions());
		ListView<Session> sessionListView = new ListView<Session>();

		sessionListView.setItems(sessionList);

		sessionListView.setOnMouseClicked(e -> {

			if (e.getClickCount() == 2) {
				Session currentSession = sessionListView.getSelectionModel().getSelectedItem();
				this.imageContainer.setCurrentSession(currentSession);
				ParentPlane root = new ParentPlane();
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		});

		botHeaderBox.getChildren().add(lblLoadSession);

		botBox.getChildren().addAll(botHeaderBox, sessionListView);

		this.getItems().addAll(topBox, botBox);

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
