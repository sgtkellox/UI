package gui;


import data.SlideClassification;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;

public class StatsView extends VBox {
	final BarChart<String, Number> bc;

	public StatsView() {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Classification");
		xAxis.setLabel("Cancer");
		yAxis.setLabel("confidenz");
		
		this.getChildren().add(bc);

	}

	

	public void showWeightedVote(SlideClassification slideClassification) {
		Series<String, Number> series1 = new XYChart.Series<String,Number>();
		series1.setName("2003");

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {
			series1.getData().add(new Data<String, Number>(label, slideClassification.getWheightedSumConfidenzes().get(label)));
		
			bc.getData().add(series1);

		}

	}

}
