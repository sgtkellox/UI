package gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ControlPane extends TabPane {
	
	 public ControlPane(ImageGridPane display) {
		 
		 Tab workTab = new Tab();
		 workTab.setText("Classify");
		 workTab.setContent(new WorkTab(display));
		 
		 Tab yoloSetUpTab = new Tab();
		 yoloSetUpTab.setText("Models");
		 yoloSetUpTab.setContent(new YoloSetUpTab(display));
		 		 
		 this.getTabs().addAll(workTab,yoloSetUpTab);	 
	 }

}
