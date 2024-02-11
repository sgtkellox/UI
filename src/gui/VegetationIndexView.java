package gui;

import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class VegetationIndexView extends Tab {
	
	private ImageView imageView = new ImageView();

	private Image image;
	
	public VegetationIndexView() {
		StackPane box = new StackPane();
		imageView.setPreserveRatio(true);
		imageView.fitHeightProperty().bind(box.heightProperty());
		imageView.fitWidthProperty().bind(box.widthProperty());
		imageView.setManaged(false);
		
		box.getChildren().add(imageView);
		this.setContent(box);
	}
	
	public void showImage(Image image) {
		this.setImage(image);
		imageView.setImage(image);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}


}
