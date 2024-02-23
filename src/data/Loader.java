package data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Loader {
	
	public static SlideList loadWSIsFromTiles(){
		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		
		File selectedDirectory = directoryChooser.showDialog(new Stage());
		
		if(selectedDirectory== null) {
			return null;
		}
		
		ArrayList<File> files = filterImageList(selectedDirectory.listFiles());
		List<Slide> wsis = createWsisFromTiles(files);
		SlideList slideList = new SlideList();
		slideList.setSlides(wsis);
		return slideList;
	}
	
	private static ArrayList<File> filterImageList(File[] files) {
		ArrayList<File> filtered = new ArrayList<File>();
		for(int i = 0; i<files.length;i++) {
			if(files[i].getAbsolutePath().endsWith(".jpg")||files[i].getAbsolutePath().endsWith(".png")||files[i].getAbsolutePath().endsWith(".JPG")) {
				filtered.add(files[i]);
			}
		}
		return filtered;
	}
	
	private Slide createWsiFromTiles(String name, List<Tile> tiles) {
		
		Slide wsi = new Slide();	
		return wsi;	
	}
	
	private static List<Slide> createWsisFromTiles(List<File> files) {
		HashMap<String,List<Tile>> wsis = sortByWsi(files);
		ArrayList<Slide> results = new ArrayList<Slide>();
		
		for(String wsiName: wsis.keySet()) {
			Slide wsi = new Slide(wsiName,wsis.get(wsiName));
			results.add(wsi);
		}	
		return results;
	}
	
	private static HashMap<String,List<Tile>> sortByWsi(List<File> files) {
		HashMap<String,List<Tile>> map = new HashMap<String,List<Tile>>();
		for(File f: files) {
			String nNumber = getName(f.getName());
			
			Tile tile = new Tile(getName(f.getName()), getCoords(f.getName()), 384);
			if(map.keySet().contains(nNumber)) {
				map.get(nNumber).add(tile);
			}else {
				ArrayList<Tile> images = new ArrayList<Tile>();
				images.add(tile);
				map.put(nNumber, images);
			}
				
		}
		return map;
	}
	
	private String getNNumber(String file) {
		String[] split = file.split("-");
		String nNumber = split[1].concat("-").concat(split[2]);
		return nNumber;
	}
	
	private static String getName(String file) {
		String name = file.split("_")[0];	
		return name;	
	}
	
	private static int[] getCoords(String file) {
		int[] pos = new int[2];
		String[] split = file.split(".")[0].split("-");
		pos[0] = Integer.parseInt(split[0]);
		pos[1] = Integer.parseInt(split[1]);
		return pos;
		
	}
	
	

}


