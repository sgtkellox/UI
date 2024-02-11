package yolointerface;

import ai.djl.modality.cv.output.Rectangle;

public class Detection extends Rect {
	

	
	
	private int confidenz;
	
	private ImageType type;
	
	
	
	private double longitude = Double.NaN;
	private double latitude = Double.NaN;
	private double altitude;
	
	private double meanNDVI = Double.NaN;
	
	private double meanGNDVI = Double.NaN;
	
	public Detection(int[] dims, ImageType type) {
		super(dims[0],dims[1],dims[2],dims[3]);
		
		this.type = type;
		
		confidenz = dims[4];	
	}
	
	public Detection(Rectangle box, int confidenz, ImageType type) {
		super(box);
		
		this.type = type;
		
		this.confidenz = confidenz;	
	}
	
	public int getConfidenz() {
		return confidenz;
	}

	public void setConfidenz(int confidenz) {
		this.confidenz = confidenz;
	}
	
	@Override
	public String toString() {
		return "Confidenz: " +Integer.toString(confidenz) + "    X: " + Integer.toString(this.x) + "    Y: " +Integer.toString(this.y) +"    width: " +Integer.toString(this.width) + "    height: " +Integer.toString(this.height);
	}
	
	public String toAnnotationString(double imgWidth, double imgHeight) {
		return "0 "+ Integer.toString(confidenz)+" " + Double.toString(this.getX()/imgWidth)+" "+ Double.toString(this.getY()/imgHeight)+" "+ Double.toString(this.getWidth()/imgWidth)+" "+ Double.toString(this.getHeight()/imgHeight);
	}
	
	public String toAbsoluteAnnotation() {
		return "0 "+ Integer.toString(confidenz)+" " + Double.toString(this.getX())+" "+ Double.toString(this.getY())+" "+ Double.toString(this.getWidth())+" "+ Double.toString(this.getHeight());
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}



	public double getMeanNDVI() {
		return meanNDVI;
	}



	public void setMeanNDVI(double meanNDVI) {
		this.meanNDVI = meanNDVI;
	}



	public double getMeanGNDVI() {
		return meanGNDVI;
	}



	public void setMeanGNDVI(double meanGNDVI) {
		this.meanGNDVI = meanGNDVI;
	}

}
