package FinalProject;

import java.sql.*;
import java.util.ArrayList;
/**
 * This is a class to read information from derbyDB and create runner threads based on the information(name, speed and rest).
 *
 */
public class DerbyDatabase implements RunnerDAO{
	
	/**
	 * This is a method to connect to derbyDB
	 * @return Connection
	 */
    private Connection getConnection() {
        Connection connection = null;
        try {
            String dbDirectory = "Resources";
            System.setProperty("derby.system.home", dbDirectory);

            String url = "jdbc:derby:RunnersDB";
            String user = "";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e)
        {
            System.err.println("Error loading database driver: " + e);
        }
        return connection;
    }

    /**
     * Override the RunnerDAO interface method.
     * This method reads the data from the derbyDB and creates thread objects for each runner.
     * @return ArrayList of RaceRunner objects
     */
	@Override
    public ArrayList<RaceRunner> getRunners() {
    	
	    try {
	    	Connection connection = getConnection();
	    	ArrayList<RaceRunner> runners = new ArrayList<RaceRunner>();
	    	
	        String query = "SELECT * FROM RunnersStats";
	        PreparedStatement ps = connection.prepareStatement(query);
	        ResultSet rs = ps.executeQuery();            
	    
	    	while(rs.next()) {
	    		String name = rs.getString("Name");
	            int speed = rs.getInt("RunnersSpeed");
	            int rest = rs.getInt("RestPercentage");
	            
	            RaceRunner runner = new RaceRunner(name, speed, rest);
	            
	            runners.add(runner);
	        }
	    	
	        return runners;
	    } 
	    catch(SQLException sqle) {
	    	 return null;
	    }
    }
	
}
