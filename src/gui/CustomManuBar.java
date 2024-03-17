package gui;

import data.Loader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


public class CustomManuBar extends MenuBar {
	
	
	public CustomManuBar() {
		this.getStylesheets().add(String.valueOf(this.getClass().getResource("css/RootPane.css")));
		Menu menuFile = new Menu("File");
		Menu menuSettings = new Menu("Settings");
		Menu menuHelp = new Menu("Help");
		

		MenuItem loadTilesItem = new MenuItem("Load Tiles");
		//menuItem1.setGraphic(new ImageView("file:soccer.png"));

		MenuItem loadSvsItem = new MenuItem("load svs");
		//menuItem1.setGraphic(new ImageView("file:basketball.png"));

		menuFile.getItems().add(loadTilesItem);
		menuFile.getItems().add(loadSvsItem);


		this.getMenus().addAll(menuFile, menuSettings, menuHelp);
		
		loadTilesItem.setOnAction(e->{
			
			Loader.loadWSIsFromTiles();
			
		});
		
		
		
	}

	
	

}
