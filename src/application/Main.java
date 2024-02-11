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

			primaryStage.setTitle("Festmeter Schadbaumerkennung");
//			SessionSelector root = new SessionSelector();
			RootPane root = new RootPane();
			primaryStage.setMaximized(true);
			Scene scene = new Scene(root,700,700);
			//MicasenseExifExtractor.extractMetaDataFromFile("C:\\Users\\felix\\Desktop\\RaggerTal\\0005SET\\000\\IMG_0000_1.tif");
			
			Opener opener = new Opener();
			
			BufferedImage image = ImageIO.read(new File("E:\\newEntities\\EPN-N14-2304-K-Q2.svs"));
			
			Image fximage = SwingFXUtils.toFXImage(image, null);
			ImageView imageView = new ImageView(fximage);
			Stage newWindow = new Stage();
			HBox box = new HBox();
			box.getChildren().add(imageView);
			Scene shower = new Scene(box,700,700);
			newWindow.setScene(shower);
			newWindow.show();
			
			
			
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
