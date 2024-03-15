package gui;

import data.SlideClassification;
import data.TileClassification;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MapView extends StackPane {

	private ImageView imageView = new ImageView();

	LabelColorMap colors = LabelColorMap.instance();

	public MapView() {

		imageView.setPreserveRatio(true);
		imageView.fitHeightProperty().bind(this.heightProperty());
		imageView.fitWidthProperty().bind(this.widthProperty());
		imageView.setManaged(false);

		this.getChildren().add(imageView);
	}

	public void makeVisualisation(SlideClassification slideClassification, double confidenzThreshOld) {

		int pixelSize = 20;
		int offSet = 100;
		int tileSize = slideClassification.getTileClassifications().get(0).getTile().getSize();

		int width = (slideClassification.getSlide().getWsiWith() / tileSize) * pixelSize + offSet;
		int height = slideClassification.getSlide().getWsiHeight() / tileSize * pixelSize + offSet;
		
		WritableImage resultImage = new WritableImage(width, height);
		PixelWriter writer = resultImage.getPixelWriter();
		for (TileClassification tc : slideClassification.getTileClassifications()) {
			if (tc.getMaxConf() >= confidenzThreshOld) {
				int[] pos = tc.getScaledPosition();

				Color color = LabelColorMap.lookUpColor(tc.getBest());
				for (int x = pos[0] * pixelSize + offSet / 2; x < pos[0] * pixelSize + pixelSize + offSet / 2; x++) {
					for (int y = pos[1] * pixelSize + offSet / 2; y < pos[1] * pixelSize + pixelSize
							+ offSet / 2; y++) {
						
						writer.setColor(x, y, color);
					}
				}
			}
		}

		imageView.setImage(resultImage);

	}

	public void showPlaceholder() {
		imageView.setImage(null);
	}

}
