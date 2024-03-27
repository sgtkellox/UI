package data;

public class Tile {
	private String path;
	private String WSIName;
	private int x;
	private int y;
	
	private int xShift;
	private int yShift;
	
	private int  size;
	private int level;
	
	public Tile(String WSIName, int x, int y ,int size){
		this.WSIName = WSIName;
		this.x = x;
		this.y = y;
		this.size  = size;
	}
	public Tile(String WSIName, String path, int[] pos ,int size){
		this.path = path;
		this.WSIName = WSIName;
		this.x = pos[0];
		this.y = pos[1];
		this.size  = size;
	}
	
	public Tile() {
		
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
	public int getSize() {
		return size;
	}
	public void setSize(int tileSize) {
		this.size = tileSize;
		
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getWSIName() {
		return WSIName;
	}
	public void setWSIName(String wSIName) {
		WSIName = wSIName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getxShift() {
		return xShift;
	}
	public void setxShift(int xShift) {
		this.xShift = xShift;
	}
	public int getyShift() {
		return yShift;
	}
	public void setyShift(int yShift) {
		this.yShift = yShift;
	}
	
	

}
