package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import yolointerface.Detection;


public class BoundingBox extends Rectangle {
	
	private Detection detection;
	
	
	public BoundingBox(int x, int y , int width, int height ,Detection detection, ImageBox parent) {
			
		super(x,y,width, height);
		
		this.setDetection(detection);
		
		Popup infoBox = new Popup();
		
		Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
		this.setOnContextMenuRequested(e->{
			BBRightClickMenu menu = new BBRightClickMenu(this, parent);
			menu.show(this, e.getScreenX(), e.getScreenY());
		});
		
		
		EventHandler<MouseEvent> showInfo = new EventHandler<MouseEvent>() { 
	         @Override
	         public void handle(MouseEvent e) { 
	           
	           Label lblConfidenz = new Label("Konfidenz: "+Integer.toString(detection.getConfidenz())+"%");
	           infoBox.getContent().add(lblConfidenz);
	           infoBox.show(owner,e.getSceneX(),e.getSceneY());
	           
	         }
	        
	    };
	    
	    EventHandler<MouseEvent> hideInfo = new EventHandler<MouseEvent>() { 
	         @Override
	         public void handle(MouseEvent e) { 
	        	 infoBox.hide();
	        	 
	         }
	        
	    };
	    
		
		this.setOnMouseEntered(showInfo);
		this.setOnMouseExited(hideInfo);
	}

	public Detection getDetection() {
		return detection;
	}

	public void setDetection(Detection detection) {
		this.detection = detection;
	}
	
	
}
