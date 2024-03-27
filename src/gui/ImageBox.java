package gui;



import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import yolointerface.Detection;
import yolointerface.GroundTruth;

public class ImageBox extends StackPane {
	private ImageView imageView = new ImageView();
	private Pane pane = new Pane();

	private Image image;

	private double bbOpacity = 1.0;

	public ImageBox() {
		imageView.setPreserveRatio(true);
		imageView.fitHeightProperty().bind(this.heightProperty());
		imageView.fitWidthProperty().bind(this.widthProperty());
		imageView.setManaged(false);

		this.getChildren().addAll(imageView, pane);

	}

	public void showImage(Image image) {
		this.image = image;
		imageView.setImage(image);
	}

	public void drawBoundingBoxes(List<Detection> result) {
		ArrayList<BoundingBox> boundingBoxes = new ArrayList<BoundingBox>();

		for (Detection bb : result) {
			
			boundingBoxes.add(drawBoundingBox(bb));
		}

		pane.getChildren().addAll(boundingBoxes);

	}

	

	private int scaleXValue(int unscaled) {

		double aspectRatio = image.getWidth() / image.getHeight();
		double realWidth = Math.min(imageView.getFitWidth(), imageView.getFitHeight() * aspectRatio);

		return (int) ((realWidth / image.getWidth()) * unscaled);

	}

	private int scaleYValue(int unscaled) {
		double aspectRatio = image.getWidth() / image.getHeight();
		double realHeight = Math.min(imageView.getFitHeight(), imageView.getFitWidth() / aspectRatio);
		return (int) ((realHeight / image.getHeight()) * unscaled);

	}

	private BoundingBox drawBoundingBox(Detection bb) {
		BoundingBox rect = new BoundingBox(scaleXValue(bb.getX()), scaleYValue(bb.getY()), scaleXValue(bb.getWidth()), scaleYValue(bb.getHeight()), bb, this);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.RED);
		rect.setStrokeWidth(2);
		rect.opacityProperty().set(this.bbOpacity);
		return rect;
	}

	public void clearBundingBoxes() {
		pane.getChildren().clear();
		
	}

	public double getBbOpacity() {
		return bbOpacity;
	}

	public void setBbOpacity(double bbOpacity) {
		this.bbOpacity = bbOpacity;

		for (Node rect : this.pane.getChildren()) {
			rect.opacityProperty().set(bbOpacity);
		}

	}
	
	public void drawGroundTruth(ArrayList<GroundTruth> gds) {
		for(GroundTruth gd: gds) {
			int width = scaleXValue((int)Math.round(gd.getWidth()*this.image.getWidth()));
			int height =  scaleYValue((int)Math.round(gd.getHeight()*this.image.getHeight()));
			
			int x = scaleXValue((int)Math.round(gd.getX()*this.image.getWidth()))-width/2;
			int y = scaleYValue((int)Math.round(gd.getY()*this.image.getHeight()))-height/2;
			Rectangle rect = new Rectangle(x,y,width,height);
			rect.setFill(Color.TRANSPARENT);
			rect.setStroke(Color.BLUE);
			rect.setStrokeWidth(2);
			this.pane.getChildren().add(rect);
		}
	}
		
	public void hightLightDetection(Detection d) {
		for(Node b: this.pane.getChildren()) {
			if(b instanceof BoundingBox) {
				if(((BoundingBox) b).getDetection().equals(d)) {
					((BoundingBox) b).setStroke(Color.BLACK);
				}else {
					((BoundingBox) b).setStroke(Color.RED);
				}
			}
		}
	}
	
	public void displayTile(String file) {
		imageView.setImage(new Image(file));
	}
}
