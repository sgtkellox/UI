package gui;


import javafx.scene.control.SplitPane;




public class ParentPlane extends SplitPane {
	
	public ParentPlane() {
		
		ImageGridPane imagePane = new ImageGridPane();
		imagePane.minWidthProperty().bind(this.widthProperty().multiply(0.8));
		ControlPane controlPane = new ControlPane(imagePane);
		controlPane.minWidthProperty().bind(this.widthProperty().multiply(0.2));
		
		
		this.getItems().addAll(controlPane,imagePane);
		
		
		
	}
	
	
	
	

}
