package yolointerface;

import ai.djl.modality.cv.output.Rectangle;

public class Rect implements Comparable<Rect> {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public Rect(int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		
	}
	
	public Rect(Rectangle box) {
		this.setX((int)Math.round((box.getX()/640)*1254));
		this.setY((int)Math.round((box.getY()/640)*921));
		this.setWidth((int)Math.round((box.getWidth()/640)*1254));
		this.setHeight((int)Math.round((box.getHeight()/640)*921));
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int calcSize() {
		return this.width*this.height;
	}

	@Override
	public int compareTo(Rect r) {
		return this.calcSize() -r.calcSize();
	}

}
