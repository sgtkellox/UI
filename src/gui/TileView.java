package gui;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class TileView extends BorderPane {
	private ImageView imageView = new ImageView();
	



	public TileView() {
		imageView.setPreserveRatio(true);
		this.setCenter(imageView);
		
		imageView.fitHeightProperty().bind(this.heightProperty());
		imageView.fitWidthProperty().bind(this.widthProperty());
		imageView.setManaged(false);
		
		
		//BorderPane.setAlignment(imageView, Pos.CENTER);
		
		Button btnLeft = new Button("<");
		Button btnRight = new Button(">");
		//btnLeft.setPrefWidth(this.getWidth()*0.1);
		VBox filler = new VBox();
		this.setAlignment(imageView, Pos.BASELINE_CENTER);
		BorderPane.setMargin(imageView, new Insets(12,12,12,12));
		this.setLeft(btnLeft);
		this.setRight(btnRight);
		//this.setPadding(new Insets(10, 10, 10, 10));
		
		

	}
	
	
	public void displayTile(String file) {
		imageView.setImage(new Image(file));
	}
}

