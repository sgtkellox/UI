package vegetationIndices;

import javafx.scene.paint.Color;

public class NDVI implements VegetationIndexInterface {

	@Override
	public double applyFormula(int pixels) {
		double nir = (pixels >> 16) & 0xff;
		double red = (pixels >> 8) & 0xff;
		return  (nir - red) / (nir + red);
	}

	@Override
	public Color getColor(double indexValue) {
		Color rColor = null;
		if (indexValue > 0.706) {
			rColor = Color.rgb(0, 102, 0);
		} else if (indexValue <= 0.706 && indexValue > 0.588) {
			rColor = Color.rgb(0, 255, 0);
		} else if (indexValue <= 0.588 && indexValue > 0.471) {
			rColor = Color.rgb(255, 255, 0);
		} else if (indexValue <= 0.471 && indexValue > 0.353) {
			rColor = Color.rgb(255, 204, 0);
		} else if (indexValue <= 0.353 && indexValue > 0.0) {
			rColor = Color.rgb(255, 0, 0);
		} else if (indexValue <= 0.0 && indexValue > -0.118) {
			rColor = Color.rgb(238, 0, 0);
		} else if (indexValue <= -0.118 && indexValue > -0.235) {
			rColor = Color.rgb(221, 0, 0);
		} else if (indexValue <= -0.235) {
			rColor = Color.rgb(136, 0, 0);
		}
		return rColor;
	}

	@Override
	public String toString() {
		return "NDVI";
	}
	
}
