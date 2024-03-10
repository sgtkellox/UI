package gui;


import data.SlideClassification;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;

public class StatsView extends StackPane {
	final BarChart<String, Number> bc;

	public StatsView() {
		
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setAnimated(false);
		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Classification");
		xAxis.setLabel("Cancer");
		yAxis.setLabel("confidenz");
		
		bc.setMaxHeight(Double.MAX_VALUE);
		
		this.getChildren().add(bc);                       
		

	}

	

	
	public void showWeightedVote(SlideClassification slideClassification) {
		Series<String, Number> series1 = new XYChart.Series<String,Number>();
		series1.setName("weigted");

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {
			series1.getData().add(new XYChart.Data<String, Number>(label, slideClassification.getWheightedSumConfidenzes().get(label)));
		
		}
		Series<String, Number> series2 = new XYChart.Series<String,Number>();
		series2.setName("simple");

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {
			
			series2.getData().add(new XYChart.Data<String, Number>(label, slideClassification.getSumConfidenzes().get(label)));
		}
		
		bc.getData().add(series1);	
		bc.getData().add(series2);
	}
	
	public void showVote(SlideClassification slideClassification) {
		Series<String, Number> series1 = new XYChart.Series<String,Number>();
		series1.setName(slideClassification.getSlide().getName());

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {
			System.out.println(label);
			System.out.println(slideClassification.getWheightedSumConfidenzes().get(label));
			series1.getData().add(new XYChart.Data<String, Number>(label, slideClassification.getWheightedSumConfidenzes().get(label)));
		}
		
		bc.getData().add(series1);

	}

}
