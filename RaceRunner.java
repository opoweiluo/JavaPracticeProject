package FinalProject;
/**
 * This is a class to create thread object for each runner from data source.
 * Every thread will have their own name, speed and rest.
 *
 */
public class RaceRunner extends Thread {

	private String name;
	private int rest;
	private int speed;
	private volatile boolean hasResult ; //when there is a winner, hasResult will be set to true.
	private int distance = 0;
	
	/**
	 * Default constructor
	 */
	public RaceRunner(){
		hasResult = false;
	}
	
	/**
	 * This is the constructor with parameters inputs.
	 * @param name information from data source 
	 * @param speed information from data source 
	 * @param rest information from data source 
	 */
	public RaceRunner(String name, int speed, int rest){
		this.name = name;
		this.speed = speed;
		this.rest = rest;
		hasResult = false;
	}
	/**
	 * Override the method in Thread Class.
	 * when a runner completes the condition(distance >= 1000), the result boolean will become true, which will end the race.
	 */
	@Override
	public void run() {
		
		//loop until there is a winner
		while (!hasResult && !isInterrupted())  {
			
			int randomNumber = (int)(Math.random() * 100);
			
			if (randomNumber > rest && !hasResult && !isInterrupted()) {
				distance += speed;
				if (distance >= 1000 && !hasResult && !isInterrupted()) {
					//make all runner's hasResult to true.
					MarathonApp.finished();
					System.out.println( name + " : " + distance);
					MarathonApp.printResult(this);
					break;
				}
				System.out.println( name + " : " + distance);
				
			}
			
			try {
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				if(hasResult){
	                break;
	             }
			}
		}
	}
	
	
    public String getRunnerName(){
        return name;
    }   
    
    /**
     * This method lets the RaceRunner's hasResult become true and also interrupts the thread.
     */
    public synchronized  void exit () {
    	hasResult = true;
    	interrupt();
    }
    
    public void setRunnerName(String name) {
    	this.name = name;
    }
    
    public void setSpeed(int speed) {
    	
    	this.speed = speed;
    }
    
    public void setRest(int rest) {
    	
    	this.rest = rest;
    }
    
  
    
}
