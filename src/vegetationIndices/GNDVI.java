package vegetationIndices;

import javafx.scene.paint.Color;

public class GNDVI implements VegetationIndexInterface {

	@Override
	public double applyFormula(int pixels) {
		double nir = (pixels >> 16) & 0xff;

		double green = pixels & 0xff;

		return (nir - green) / (nir + green);
	}

	@Override
	public Color getColor(double indexValue) {
		Color gColor = null;
		if (indexValue > 0.706) {
			gColor = Color.rgb(0, 102, 0);
		} else if (indexValue <= 0.706 && indexValue > 0.588) {
			gColor = Color.rgb(0, 255, 0);
		} else if (indexValue <= 0.588 && indexValue > 0.471) {
			gColor = Color.rgb(255, 255, 0);
		} else if (indexValue <= 0.471 && indexValue > 0.353) {
			gColor = Color.rgb(255, 204, 0);
		} else if (indexValue <= 0.353 && indexValue > 0.0) {
			gColor = Color.rgb(255, 0, 0);
		} else if (indexValue <= 0.0 && indexValue > -0.118) {
			gColor = Color.rgb(238, 0, 0);
		} else if (indexValue <= -0.118 && indexValue > -0.235) {
			gColor = Color.rgb(221, 0, 0);
		} else if (indexValue <= -0.235) {
			gColor = Color.rgb(136, 0, 0);
		} else if (Double.isNaN(indexValue)) {
			gColor = Color.rgb(0, 0, 0);
		}
		
		return gColor;
	}

	@Override
	public String toString() {
		return "GNDVI";
	}

}
