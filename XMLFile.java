package FinalProject;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


/**
 * This is a class to read information from XMLfile and create runner threads based on the information(name, speed and rest).
 * 
 */

public class XMLFile implements RunnerDAO{
	
	
	private Path RunnerPath = null;
    private ArrayList<RaceRunner> runners = new ArrayList<RaceRunner>();

    /**
     * This is the constructor to 
     * @param user input the XML file name
     */
    public XMLFile(Scanner input)  {
    	System.out.println("Enter your XMLFile name: ");
    	String xmlFileName = input.nextLine(); 
    	RunnerPath = Paths.get(xmlFileName);
    }
    
   
    /**
     * Override the RunnerDAO interface method.
     * This method reads the data from the XML file and creates thread objects for each runner.
     * @return ArrayList of RaceRunner objects.
     */
    @Override
    public ArrayList<RaceRunner> getRunners() {
    	
    	runners = new ArrayList<RaceRunner>();  
    	RaceRunner runner = null;  
    	String name;
    	String speed;
    	String rest;
    	
		if (Files.exists(RunnerPath))  
		{
			
			XMLInputFactory inputFactory = XMLInputFactory.newFactory();
			try
			{
				FileReader fileReader = new FileReader(RunnerPath.toFile());
				XMLStreamReader reader = inputFactory.createXMLStreamReader(fileReader);
	
				while (reader.hasNext()) {
					int eventType = reader.getEventType();
					switch (eventType) {
						case XMLStreamConstants.START_ELEMENT :
							String elementName = reader.getLocalName();
							if (elementName.equals("Runner")) 
							{	
								runner = new RaceRunner(); // create new RaceRunner object
								name = reader.getAttributeValue(0);
								runner.setRunnerName(name);
							}
							if (elementName.equals("RunnersMoveIncrement")) 
							{
								speed = reader.getElementText();
								int speedInt = Integer.parseInt(speed);
								runner.setSpeed(speedInt);
							}
							if (elementName.equalsIgnoreCase("RestPercentage")) 
							{
								rest = reader.getElementText();
								int restInt = Integer.parseInt(rest);
								runner.setRest(restInt);
							}
							break;
						case XMLStreamConstants.END_ELEMENT:
							elementName = reader.getLocalName();
							if (elementName.equals("Runner")) {
								if (runner != null){
									runners.add(runner);
								}
							}
							break;
						default:
							break;
					}
				reader.next();
				}
			}catch (IOException | XMLStreamException e){
	         System.out.println(e);
	         return null;
			}
		 }else {
			 System.out.println("No such file.");
		 }
		 return runners;
     }
}
   