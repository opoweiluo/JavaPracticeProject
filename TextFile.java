package FinalProject;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a class to read information from text file and create runner threads based on the information(name, speed and rest).
 *
 */
public class TextFile implements RunnerDAO{

	private File textFile = null;
	private Path textPath = null;
	private final String FIELD_SEP = "\t";
	/**
	 * This is the constructor
	 * @param user input the text file name
	 */
    public TextFile(Scanner input) {
    	System.out.println("Enter your Textfile name: ");
    	String textfilename = input.nextLine(); 
    	textPath = Paths.get(textfilename);
    	textFile = textPath.toFile();
    }
    
    /**
     * Override the RunnerDAO interface method.
     * This method reads the data from the text file and creates thread objects for each runner.
     * @return ArrayList of RaceRunner objects.
     */
    @Override
    public ArrayList<RaceRunner> getRunners() {
    	
    	ArrayList<RaceRunner> runners = new ArrayList<RaceRunner>();
    	
    	if (textFile .exists()) {
    		try (BufferedReader data = new BufferedReader(new FileReader(textFile));){
    			String line = data.readLine();
    			while(line != null) {
    				String[] columns = line.split(FIELD_SEP);
    				
    	    		String name = columns[0];
    	    		String speedString = columns[1];
    	    		String restString = columns[2];
    	    		int speed = Integer.parseInt(speedString);
    	    		int rest = Integer.parseInt(restString);
    	    		
    				RaceRunner runner = new RaceRunner(name, speed, rest);
    				runners.add(runner);  
    				
    				line = data.readLine();
    			}
    			
    		}catch (IOException e) {
    			System.out.println(e);
    			return null;
    		}
    	}else {
    		System.out.println("No such file.");
    	}
    	
    	return runners;
    }
    
}