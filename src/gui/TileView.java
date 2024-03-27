package gui;



import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class TileView extends BorderPane {
	private ImageView imageView = new ImageView();
	



	public TileView() {
		imageView.setPreserveRatio(true);
		
		imageView.fitHeightProperty().bind(this.heightProperty());
		imageView.fitWidthProperty().bind(this.widthProperty());
		imageView.setManaged(false);
		
		
		//BorderPane.setAlignment(imageView, Pos.CENTER);
		
		Button btnLeft = new Button("<");
		Button btnRight = new Button(">");
		
		this.setLeft(btnLeft);
		this.setRight(btnRight);

		this.setCenter(imageView);

	}
	
	
	public void displayTile(String file) {
		imageView.setImage(new Image(file));
	}
}

