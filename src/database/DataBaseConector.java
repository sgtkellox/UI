package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.Session;
import yolointerface.Detection;
import yolointerface.ImageContainer;
import yolointerface.ImageType;

public class DataBaseConector {


	private Connection connection = null;

	public DataBaseConector() {
		
	}

	public void safeRGBDetections() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/festmeterdrone", "root",
					"eHOADpeJISyr7ErTDzTb");
			for (File img : ImageContainer.getRGBDetections().keySet()) {
				String safeImgStatement = "INSERT INTO detections (path ) VALUES(?)";
				PreparedStatement preparedImgStmt = connection.prepareStatement(safeImgStatement);
				preparedImgStmt.setString(1, img.getAbsolutePath());
				preparedImgStmt.execute();
				
				ResultSet rs = preparedImgStmt.getGeneratedKeys();
				
				int autoIncKeyFromApi = -1;
				if (rs.next()) {
			         autoIncKeyFromApi = rs.getInt(1);
			    }
				
				for (Detection det : ImageContainer.getRGBDetections().get(img)) {
					String safeDetStatement = "INSERT INTO detections (x , y , width, height, confidenz, type, longitude, latitude, altitude, ImageID, sessionID) ()";					
					PreparedStatement preparedDetStmt = connection.prepareStatement(safeDetStatement);
					preparedDetStmt.setInt(1, det.getX());
					preparedDetStmt.setInt(2, det.getY());
					preparedDetStmt.setInt(3, det.getWidth());
					preparedDetStmt.setInt(4, det.getHeight());
					preparedDetStmt.setDouble(5, det.getConfidenz());
					if(det.getType()==ImageType.RGB) {
						preparedDetStmt.setInt(6, 0);
					}else {
						preparedDetStmt.setInt(6, 1);
					}
					preparedDetStmt.setDouble(7, det.getLongitude());
					preparedDetStmt.setDouble(8, det.getLatitude());
					preparedDetStmt.setDouble(9, det.getAltitude());
					preparedDetStmt.setInt(10, autoIncKeyFromApi);
					preparedDetStmt.setInt(11, ImageContainer.getCurrentSession().getId());
				}
			}
			
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	public void loadDetections() {

	}

	public int safeSession(Session session) {
		String safeStatement = "INSERT INTO session (projectname, rgbdir, cirdir, yolodir, rgbmodel, cirmodel) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Setup the connection with the DB
			connection = DriverManager.getConnection("jdbc:mysql://localhost/festmeterdrone", "root",
					"eHOADpeJISyr7ErTDzTb");
			PreparedStatement preparedStmt = connection.prepareStatement(safeStatement,Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, session.getProjectName());
			preparedStmt.setString(2, session.getRgbDir());
			preparedStmt.setString(3, session.getCirDir());
			preparedStmt.setString(4, session.getYoloDir());
			preparedStmt.setString(5, session.getRgbModelDir());
			preparedStmt.setString(6, session.getCirModelDir());
			
			preparedStmt.execute();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			
			int autoIncKeyFromApi = -1;
			if (rs.next()) {
		         autoIncKeyFromApi = rs.getInt(1);
		    }
		      
			connection.close();
			
			return autoIncKeyFromApi;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}
	
	public List<Session> loadSessions(){
		try {
			
			List<Session> result = new ArrayList<Session>();
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/festmeterdrone", "root",
					"eHOADpeJISyr7ErTDzTb");
			
			String query = "SELECT * FROM session";

		      // create the java statement
		      Statement st = connection.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs = st.executeQuery(query);
		      
		      // iterate through the java resultset
		      while (rs.next())
		      {
		    	int id = rs.getInt(1);
		    	String projectname = rs.getString("projectname");
		        String rgbdir = rs.getString("rgbdir");
		        String cirdir = rs.getString("cirdir");
		        String yolodir = rs.getString("yolodir");
		        String rgbmodel = rs.getString("rgbmodel");
		        String cirmodel = rs.getString("cirmodel");
		        
		        Session session = new Session(projectname,rgbdir,cirdir,yolodir,rgbmodel,cirmodel);
		        session.setId(id);
		        result.add(session);
		       
		      }
		      st.close();
		      return result;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		// Setup the connection with the DB
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
