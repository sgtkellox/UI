package vegetationIndices;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import yolointerface.Detection;

public class Calculator {
	BufferedImage img;

	public Calculator() {

	}

	public Calculator(File f) {
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setImage(File f) {
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WritableImage[] createNVVIImage(File f) {

		try {
			BufferedImage img = ImageIO.read(f);
			int width = img.getWidth();
			int height = img.getHeight();

			WritableImage ndviResult = new WritableImage(width, height);
			PixelWriter pwNDVI = ndviResult.getPixelWriter();

			WritableImage gndviResult = new WritableImage(width, height);
			PixelWriter pwGNDVI = gndviResult.getPixelWriter();

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {

					int p = img.getRGB(x, y);

					double nir = (p >> 16) & 0xff;

					double red = (p >> 8) & 0xff;

					double green = p & 0xff;

					double ndvi = (nir - red) / (nir + red);

					double gndvi = (nir - green) / (nir + green);

					Color rColor = null;
					Color gColor = null;

					if (ndvi > 0.706) {
						rColor = Color.rgb(0, 102, 0);
					} else if (ndvi <= 0.706 && ndvi > 0.588) {
						rColor = Color.rgb(0, 255, 0);
					} else if (ndvi <= 0.588 && ndvi > 0.471) {
						rColor = Color.rgb(255, 255, 0);
					} else if (ndvi <= 0.471 && ndvi > 0.353) {
						rColor = Color.rgb(255, 204, 0);
					} else if (ndvi <= 0.353 && ndvi > 0.0) {
						rColor = Color.rgb(255, 0, 0);
					} else if (ndvi <= 0.0 && ndvi > -0.118) {
						rColor = Color.rgb(238, 0, 0);
					} else if (ndvi <= -0.118 && ndvi > -0.235) {
						rColor = Color.rgb(221, 0, 0);
					} else if (ndvi <= -0.235) {
						rColor = Color.rgb(136, 0, 0);
					}

					if (gndvi > 0.706) {
						gColor = Color.rgb(0, 102, 0);
					} else if (gndvi <= 0.706 && gndvi > 0.588) {
						gColor = Color.rgb(0, 255, 0);
					} else if (gndvi <= 0.588 && gndvi > 0.471) {
						gColor = Color.rgb(255, 255, 0);
					} else if (gndvi <= 0.471 && gndvi > 0.353) {
						gColor = Color.rgb(255, 204, 0);
					} else if (gndvi <= 0.353 && gndvi > 0.0) {
						gColor = Color.rgb(255, 0, 0);
					} else if (gndvi <= 0.0 && gndvi > -0.118) {
						gColor = Color.rgb(238, 0, 0);
					} else if (gndvi <= -0.118 && gndvi > -0.235) {
						gColor = Color.rgb(221, 0, 0);
					} else if (gndvi <= -0.235) {
						gColor = Color.rgb(136, 0, 0);
					} else if (Double.isNaN(gndvi)) {
						gColor = Color.rgb(0, 0, 0);
					}

					pwNDVI.setColor(x, y, rColor);
					pwGNDVI.setColor(x, y, gColor);

				}
			}

			WritableImage[] result = new WritableImage[2];
			result[0] = ndviResult;
			result[1] = gndviResult;
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}

	public static WritableImage[] createImages(File f, List<VegetationIndexInterface> indices) {

		WritableImage[] result = new WritableImage[indices.size()];

		try {

			BufferedImage img = ImageIO.read(f);
			int width = img.getWidth();
			int height = img.getHeight();

			for (int i = 0; i < result.length; i++) {
				WritableImage indexImage = new WritableImage(width, height);
				result[i] = indexImage;

			}

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {

					int p = img.getRGB(x, y);

					int i = 0;
					for (VegetationIndexInterface vegIndex : indices) {
						double value = vegIndex.applyFormula(p);
						Color color = vegIndex.getColor(value);
						result[i].getPixelWriter().setColor(x, y, color);
					}

				}
			}

			return result;
		} catch (IOException e) {
			return null;

		}
	}

	public double[] calcMeanNDVI(Detection det) {

		double meanNDVI = 0;
		double meangNDVI = 0;

		int width = img.getWidth();
		int height = img.getHeight();

		for (int x = det.getX(); x < det.getX() + det.getWidth() && x < width; x++) {
			for (int y = det.getY(); y < det.getY() + det.getHeight() && y < height; y++) {

				int p = img.getRGB(x, y);

				double nir = (p >> 16) & 0xff;

				double red = (p >> 8) & 0xff;

				double green = p & 0xff;

				double ndvi = (nir - red) / (nir + red);

				double gndvi = (nir - green) / (nir + green);

				meanNDVI += ndvi;
				meangNDVI += gndvi;

			}
		}
		meanNDVI = meanNDVI / (det.getWidth() * det.getHeight());
		meangNDVI = meangNDVI / (det.getWidth() * det.getHeight());
		double[] res = new double[2];
		res[0] = meanNDVI;
		res[1] = meangNDVI;
		return res;

	}

}
