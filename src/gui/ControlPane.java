package gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ControlPane extends TabPane {
	
	 public ControlPane(ImageGridPane display) {
		 
		 Tab workTab = new Tab();
		 workTab.setText("Klassifizieren");
		 workTab.setContent(new WorkTab(display));
		 
		 Tab yoloSetUpTab = new Tab();
		 yoloSetUpTab.setText("Einstellungen");
		 yoloSetUpTab.setContent(new YoloSetUpTab(display));
		 
		 Tab ndviTab = new Tab();
		 ndviTab.setText("Vegetationsindizes");
		 ndviTab.setContent(new VegetationIndexTab(display));
		 
		 this.getTabs().addAll(workTab,yoloSetUpTab,ndviTab);	 
	 }

}
