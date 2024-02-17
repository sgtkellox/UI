package data;

import java.io.File;
import java.util.ArrayList;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Loader {
	
	public WSI loadWSIFromTiles() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		
		File selectedDirectory = directoryChooser.showDialog(new Stage());
		
		if(selectedDirectory== null) {
			return null;
		}
		
		ArrayList<File> files = filterImageList(selectedDirectory.listFiles());
		return null;
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
	
	private int 

}


