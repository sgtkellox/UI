module Detector {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires ejml.simple;
	requires javafx.swing;
	requires opencv;
	requires java.sql;
	requires metadata.extractor;
	
	requires java.base;
	requires ai.djl.api;
	requires tiff;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.core;
	requires org.kordamp.ikonli.dashicons;
	requires com.jfoenix;
	requires xmpcore;
	requires org.controlsfx.controls;
	requires ij;
	
	
	

	opens yolointerface to javafx.base;
	opens application to javafx.graphics, javafx.fxml;
}
