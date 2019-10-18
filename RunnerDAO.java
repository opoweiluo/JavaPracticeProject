package FinalProject;

import java.util.ArrayList;
/**
 * This is a interface for every database class to have the same method to read data source and create threads for the runners.
 */
public interface RunnerDAO {
	
	ArrayList<RaceRunner> getRunners();
	
}
