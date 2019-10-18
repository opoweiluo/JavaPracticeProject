package FinalProject;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Main App : First it displays the menu to user to select data source, and then collect the data from the data source.
 * After the data collected, this app will run a race for multiple treads(runners). 
 * Runners' performance are based on their "Speed" and "RestPercentage" information in the data source.
 * 
 * @author POWEILU
 *
 */
public class MarathonApp {
	
	private static ArrayList<RaceRunner> runners;
	private static RunnerDAO runnerDAO = null;
	private static Scanner input = new Scanner(System.in);
	
	/**
	 * Main method shows the selection menu to the user and starts the race until one runner wins.
	 * Keep asking user to type the valid input until user's input is exit.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		
		int choice;
		boolean isValid = true;
		Validator getInput = new Validator(input);
		
		System.out.println("Welcome to the Marathon Race Runner Program");
		System.out.println();
		
        while (isValid) {
        		
        	//display selection menu  
        	displayMenu();
    			
    		//user input validation 
    		choice = getInput.getIntWithinRange("Enter your choice: ", 0, 6);
    		
    		
    		switch(choice) {
    			case 1:
    				runnerDAO  = new DerbyDatabase(); //create data object
    		   		runners = runnerDAO.getRunners(); //create thread objects
    		   		starting(runners);                //make threads alive
    	    		break;
    	    	case 2:		    	
    	    		runnerDAO = new XMLFile(input);
    	    		runners = runnerDAO.getRunners();
    	    		starting(runners);
    		    	break;
    		    case 3:
    		    	runnerDAO = new TextFile(input);
    		    	runners = runnerDAO.getRunners();    		   			
    		    	starting(runners);
    		   		break;
    		   	case 4:
    		   		runners = new ArrayList<RaceRunner>();
    		   		//create default runner objects
    		   		runners.add(new RaceRunner("Tortoise", 10, 0));
    		   		runners.add(new RaceRunner("Hare", 100, 90));  		    	
    		   		starting(runners);
    		    	break;   	
    		 }
    		 
    		 if (choice == 5) {
    			 break;
    		 }
    		 
        	System.out.println();
        	System.out.println("Press any key to continue . . .");
  		    input.nextLine();	
        }
        
        System.out.println("Thank you for using my Marathon Race Program");
        input.close();
	}
	
	/**
	 * This method displays selection menu to user
	 */
	public static void displayMenu()
    {
		System.out.println("Select your data source:");
		System.out.println();
		System.out.println("1. Derby database");
		System.out.println("2. XML file");
		System.out.println("3. Text file");
		System.out.println("4. Default two runners");
		System.out.println("5. Exit");
		System.out.println();
    }
	
	/**
	 * This method starts all the threads in the runners ArrayList
	 * And also make main thread to wait until all thread finished
	 * @param runners //ArrayList of RaceRunner objects
	 * 
	 */
	public static void starting(ArrayList<RaceRunner> runners) {	
		for (RaceRunner rt : runners) {
			rt.setName(rt.getRunnerName());
			rt.start();
		} 
		//main thread will wait for these threads to run first.
		try {
			 for (RaceRunner rt : runners) {
				 rt.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	
	}
	
	/**
	 * When there is a winner, this method makes the race finished.
	 * 
	 */
    public static synchronized void finished(){
    	
    	for (RaceRunner a : runners) {
    		a.exit();
    	}
    }
     
    /**
	 * This method show the result of the race.
	 * @param winner //the winner RaceRunner object
	 */
    public static synchronized void printResult (RaceRunner winner) {
    	
    	System.out.println(winner.getRunnerName() + " : I finished!");
		System.out.println();
		System.out.println("The race is over! The " +  winner.getRunnerName() + " is the winner.\n");
		
		for (RaceRunner loser : runners) {
			if (loser.getRunnerName().equals(winner.getRunnerName())) {
				continue;
			}
			else {
				System.out.println(loser.getRunnerName() + " : You beat me fair and square.");
			}
		}
    }
    
}