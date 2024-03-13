package application;
	

import gui.RootPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;



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
