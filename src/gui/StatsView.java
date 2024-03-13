package gui;

import data.SlideClassification;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

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

	public void showStats(SlideClassification slideClassification) {
		bc.getData().clear();
		Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("weigted");
		
		Series<String, Number> series2 = new XYChart.Series<String, Number>();
		series2.setName("simple");

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {
			
			
			Color color = LabelColorMap.lookUpColor(label);
			String rgb = String.format("%d, %d, %d",
				    (int) (color.getRed() * 255),
				    (int) (color.getGreen() * 255),
				    (int) (color.getBlue() * 255));
			
			XYChart.Data<String, Number> dataS1 = new XYChart.Data<>(label, slideClassification.getSumConfidenzes().get(label));
			dataS1.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
					if (newNode != null) {
						
						//newNode.setStyle("-fx-bar-fill: "+colorString+";");
						newNode.setStyle("-fx-bar-fill: rgba(" + rgb + ", 1.0);");
					}
				}
			});
			series1.getData().add(dataS1);
			XYChart.Data<String, Number> dataS2 = new XYChart.Data<>(label, slideClassification.getWheightedSumConfidenzes().get(label));
			dataS2.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
					if (newNode != null) {
						
						
						//newNode.setStyle("-fx-bar-fill: "+colorString+";");
						newNode.setStyle("-fx-bar-fill: rgba(" + rgb + ", 0.5);");

					}
				}
			});
			series2.getData().add(dataS2);

		}
		
		bc.setLegendVisible(false);
		bc.getData().add(series1);
		bc.getData().add(series2);
	}
	
	public void clear() {
		bc.getData().clear();
	}


}
