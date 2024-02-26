package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import yolointerface.DetectionExecuter;
import yolointerface.ImageContainer;

public class YoloSetUpTab extends VBox {

	ImageContainer imageContainer = ImageContainer.instance();
	DetectionExecuter detector = DetectionExecuter.instance();

	public YoloSetUpTab(ImageGridPane display) {
		
		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/WorkTab.css")));
		this.setId("parent");

		TextField lblRGBPath = new TextField("RGB Verzeichnis");

		Button btnSelectRGBDirectory = new Button("RGB Verzeichnis auswählen");
		btnSelectRGBDirectory.setOnAction(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			
			File selectedDirectory = directoryChooser.showDialog(new Stage());
			
			if(selectedDirectory== null) {
				return;
			}
			
			ArrayList<File> files = filterImageList(selectedDirectory.listFiles());

			ImageContainer.getRgbImages().addAll(files);
			
			imageContainer.preFillRGBDetections(files);

			lblRGBPath.setText(selectedDirectory.getAbsolutePath());
			if (!ImageContainer.getRgbImages().isEmpty()) {
				try {
					display.showRGBImage(new Image(new FileInputStream(ImageContainer.getRgbImages().get(0))));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		TextField lblCIRPath = new TextField("CIR Verzeichnis");
		Button btnSelectCIRDirectory = new Button("Cir Verzeichnis auswählen");
		btnSelectCIRDirectory.setOnAction(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(new Stage());
			
			if(selectedDirectory== null) {
				return;
			}
			
			ArrayList<File> files = filterImageList(selectedDirectory.listFiles());
			

			ImageContainer.getCirImages().addAll(files);
			
			imageContainer.preFillCIRDetections(files);

			lblCIRPath.setText(selectedDirectory.getAbsolutePath());
			

		});

		
		
		TextField lblYoloDirectory = new TextField("Yolo Verzeichnis");
		Button btnSelectYoloDirectory = new Button("YOLO Verzeichnis wählen");
		btnSelectYoloDirectory.setOnAction(e->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(new Stage());
			lblYoloDirectory.setText(selectedDirectory.getAbsolutePath());
			
		});
		
		TextField lblRGBModelLocation = new TextField("RGB Modell");
		Button btnSelectRGBModell = new Button("RGB Modell wählen");
		btnSelectRGBModell.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			File selectedDirectory = fileChooser.showOpenDialog(new Stage());
			lblRGBModelLocation.setText(selectedDirectory.getAbsolutePath());
			
		});
		
		TextField lblCIRModelLocation = new TextField("CIR Modell ");
		Button btnSelectCirDirectory = new Button("CIR Modell wählen");
		btnSelectCirDirectory.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			File selectedDirectory = fileChooser.showOpenDialog(new Stage());
			lblCIRModelLocation.setText(selectedDirectory.getAbsolutePath());
			
		});

		this.getChildren().addAll(btnSelectRGBDirectory, lblRGBPath, btnSelectCIRDirectory, lblCIRPath,
				btnSelectYoloDirectory,lblYoloDirectory,btnSelectRGBModell,lblRGBModelLocation,btnSelectCirDirectory,lblCIRModelLocation);

		
	}
	
	private ArrayList<File> filterImageList(File[] files) {
		ArrayList<File> filtered = new ArrayList<File>();
		for(int i = 0; i<files.length;i++) {
			if(files[i].getAbsolutePath().endsWith(".jpg")||files[i].getAbsolutePath().endsWith(".png")||files[i].getAbsolutePath().endsWith(".JPG")) {
				filtered.add(files[i]);
			}
		}
		return filtered;
	}

}
