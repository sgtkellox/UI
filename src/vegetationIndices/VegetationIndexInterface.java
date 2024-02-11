package vegetationIndices;

import javafx.scene.paint.Color;

public interface VegetationIndexInterface {
	public double applyFormula(int pixels);
	
	public Color getColor(double indexValue);
	
	public String toString();
}
