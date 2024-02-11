package application;

public class Session {
	
	private int id;
	private String projectName = null;
	private String rgbDir = null;
	private String cirDir = null;
	private String  yoloDir = null;
	private String cirModelDir = null;
	private String rgbModelDir = null;
	
	public  Session(String projectName, String rgbDir,String cirDir,String yoloDir,String rgbModelDir,String cirModelDir ) {
		this.projectName = projectName;
		this.rgbDir = rgbDir;
		this.cirDir = cirDir;
		this.yoloDir = yoloDir;
		this.cirModelDir = cirModelDir;
		this.rgbModelDir = rgbModelDir;
	}
	
	public String getRgbDir() {
		return rgbDir;
	}

	public void setRgbDir(String rgbDir) {
		this.rgbDir = rgbDir;
	}

	public String getCirDir() {
		return cirDir;
	}

	public void setCirDir(String cirDir) {
		this.cirDir = cirDir;
	}

	public String getYoloDir() {
		return yoloDir;
	}

	public void setYoloDir(String yoloDir) {
		this.yoloDir = yoloDir;
	}

	public String getCirModelDir() {
		return cirModelDir;
	}

	public void setCirModelDir(String cirModelDir) {
		this.cirModelDir = cirModelDir;
	}

	public String getRgbModelDir() {
		return rgbModelDir;
	}

	public void setRgbModelDir(String rgbModelDir) {
		this.rgbModelDir = rgbModelDir;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Override
	public String toString() {
		return this.projectName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
