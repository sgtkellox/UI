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

	public void showWeightedVote(SlideClassification slideClassification) {
		Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("weigted");

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {
			XYChart.Data<String, Number> dataS1 = new XYChart.Data<>(label, slideClassification.getSumConfidenzes().get(label));
			dataS1.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
					if (newNode != null) {
						Color color = LabelColorMap.lookUpColor(label);
						newNode.setStyle("-fx-bar-fill: "+toString()+";");

					}
				}
			});
			series1.getData().add(dataS1);

		}
		Series<String, Number> series2 = new XYChart.Series<String, Number>();
		series2.setName("simple");

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {

			XYChart.Data<String, Number> dataS1 = new XYChart.Data<>(label, slideClassification.getSumConfidenzes().get(label));
			dataS1.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
					if (newNode != null) {
						Color color = LabelColorMap.lookUpColor(label);
						newNode.setStyle("-fx-bar-fill: "+color.toString()+";");

					}
				}
			});
			series2.getData().add(dataS1);
		}

		bc.getData().add(series1);
		bc.getData().add(series2);
	}

	public void showVote(SlideClassification slideClassification) {
		Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName(slideClassification.getSlide().getName());

		for (String label : slideClassification.getWheightedSumConfidenzes().keySet()) {
			XYChart.Data<String, Number> dataS1 = new XYChart.Data<>(label, slideClassification.getSumConfidenzes().get(label));
			dataS1.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
					if (newNode != null) {
						Color color = LabelColorMap.lookUpColor(label);
						newNode.setStyle("-fx-bar-fill: "+color.hashCode()+";");

					}
				}
			});
			series1.getData().add(dataS1);
		}

		bc.getData().add(series1);

	}

}
