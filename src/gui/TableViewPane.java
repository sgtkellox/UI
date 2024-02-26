package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import yolointerface.Detection;
import yolointerface.ImageType;

public class TableViewPane extends VBox {
	TableView<Detection> detectionTable = new TableView<Detection>();

	@SuppressWarnings("unchecked")
	public TableViewPane(ImageGridPane parent) {
		

		TableColumn<Detection,Integer> confidenzCol = new TableColumn<Detection,Integer>("Confidenz");
		TableColumn<Detection,Double> ndviCol = new TableColumn<Detection,Double>("NDVI");
		TableColumn<Detection,Double> gndviCol = new TableColumn<Detection,Double>("gNDVI");
		TableColumn<Detection,ImageType> typeCol = new TableColumn<Detection,ImageType>("Type");
		TableColumn<Detection,Double> gpsLatCol = new TableColumn<Detection,Double>("GPS Latitude");
		TableColumn<Detection,Double> gpsLongCol = new TableColumn<Detection,Double>("GPS Longitude");
		confidenzCol.setCellValueFactory(new PropertyValueFactory<Detection,Integer>("confidenz"));
		typeCol.setCellValueFactory(new PropertyValueFactory<Detection,ImageType>("type"));
		ndviCol.setCellValueFactory(new PropertyValueFactory<Detection,Double>("meanNDVI"));
		gndviCol.setCellValueFactory(new PropertyValueFactory<Detection,Double>("meanGNDVI"));
		gpsLatCol.setCellValueFactory(new PropertyValueFactory<Detection,Double>("latitude"));
		gpsLongCol.setCellValueFactory(new PropertyValueFactory<Detection,Double>("longitude"));
		detectionTable.setRowFactory(tv -> {
            TableRow<Detection> row = new TableRow<Detection>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() ) {
                	Detection rowData = row.getItem();
                	if(rowData.getType().equals(ImageType.RGB)) {
                		parent.rgbPane.hightLightDetection(rowData);
                	}
                    
                }
            });
            return row ;
        });
		
		
		detectionTable.getColumns().addAll(confidenzCol,ndviCol,gndviCol,typeCol,gpsLatCol,gpsLongCol);

		this.getChildren().add(detectionTable);
	}
	
	
	public void setData(ObservableList<Detection> data) {
		this.detectionTable.setItems(data);
	}
	
	public void addData(ObservableList<Detection> data) {
		
		List<Detection> listToAdd = new ArrayList<Detection>();
		for(Detection d: data) {
			boolean found = false;
			for(Detection i:this.detectionTable.getItems()) {
				if(d.equals(i)) {
					found = true;
					break;
				}
			}
			if(found == true) {
				continue;
			}else {
				listToAdd.add(d);
			}
		}
		
		this.detectionTable.getItems().addAll(listToAdd);
	}

}
