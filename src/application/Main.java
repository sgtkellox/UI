package application;
	

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gui.RootPane;
import ij.ImagePlus;
import ij.io.Opener;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import modelCollection.EffNet;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			primaryStage.setTitle("Intraoperativ Classifier");
//			SessionSelector root = new SessionSelector();
			RootPane root = new RootPane();
			primaryStage.setMaximized(true);
			Scene scene = new Scene(root,700,700);
			//MicasenseExifExtractor.extractMetaDataFromFile("C:\\Users\\felix\\Desktop\\RaggerTal\\0005SET\\000\\IMG_0000_1.tif");
			
			
			
			
			
			primaryStage.setScene(scene);
			root.setSizeChangeListener(primaryStage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
