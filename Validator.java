package FinalProject;

import java.util.Scanner;

/** 
 * This class is for user input validation.
 */

public class Validator {
 
	private  Scanner sc = null;
	
	public Validator(Scanner sc) {
		this.sc =sc;
	}
	
	public int getInt(String prompt) {
		int a = 0;
		boolean isValid = false;
		while(isValid == false) {
			System.out.println(prompt);
			if(sc.hasNextInt()) {
				a = sc.nextInt();
				isValid = true;
			}else {
				System.out.println("Error! Invalid integer value. Try again.");
				isValid = false;
			}
			sc.nextLine();
		}
		return a;
	}
	
	public int getIntWithinRange(String prompt, int min, int max) {
		int a = 0;
		boolean isValid = false;
		while(isValid == false) {
			a = getInt(prompt);
			
			if(a <= min){
				System.out.println("Error! Enter an interger greater than " + min);
				isValid = false;
				
			}else if(a >= max) {
				System.out.println("Error! Enter an interger less than " + max);
				isValid = false;
				
			}else {
				isValid = true;
			}
		}
		return a;
	}
}