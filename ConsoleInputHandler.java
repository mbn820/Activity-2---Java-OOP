//getAnyString(String message)
//getDesiredString(String message, String...desiredInputs) - will loop until user enters one of the desired inputs
//getFixedString(String message, int desiredStringLength) - will loop until user enters input with the desired length
//getAnyInteger(String message) - will loop until user enters an integer
//getIntegerBetween(String message, int desiredMin, int desiredMax, String errorMessage) - will loop until user enters an integer between the specified range

import java.util.Scanner;
import java.util.Set;

public class ConsoleInputHandler {
	private static Scanner scan = new Scanner(System.in);
	public final String DEFAULT_ERROR_MESSAGE = "ERROR: Invalid Input";
	public final String DEFAULT_MESSAGE = "";
	private static ArrayPrinter printer = new ArrayPrinter();

	public String getAnyString(String message) {
		System.out.print(message);
		String input = scan.nextLine();
		return input;
	}

	public String getAnyStringExcept(String message, Set<String> undesiredInputs, String errorMessage) {
		String correctInput;
		do {
			correctInput = getAnyString(message);
			if(undesiredInputs.contains(correctInput)) {
				System.out.println(errorMessage);
			}
		} while(undesiredInputs.contains(correctInput));

		return correctInput;
	}

	public String getDesiredString(String message, String...desiredInputs) {
		String correctInput;

		do {
			correctInput = getAnyString(message);
			if(!isDesired(correctInput, desiredInputs)) {
				System.out.print(DEFAULT_ERROR_MESSAGE + "/Valid: ");
				printer.printArray(desiredInputs);
			}
		} while(!isDesired(correctInput, desiredInputs));

		return correctInput;
	}

	public String getFixedStringLength(String message, int desiredStringLength) {
		String correctInput;
		do {
			correctInput = getAnyString(message);
			if(correctInput.length() != desiredStringLength)
				System.out.println("ERROR: String length should be " + desiredStringLength);
		} while(correctInput.length() != desiredStringLength);

		return correctInput;
	}


	public int getAnyInteger(String message) {
		String input;

		do {
			input = getAnyString(message);
			if(!isInteger(input)) System.err.println(DEFAULT_ERROR_MESSAGE);
		} while(!isInteger(input));

		int correctInput = Integer.parseInt(input);
		return correctInput;
	}

	//desired minimum and maximum input
	public int getIntegerBetween(String message, int desiredMin, int desiredMax, String errorMessage) {
		int input;

		do {
			input = getAnyInteger(message);
			if(input < desiredMin || input > desiredMax)
				System.err.println(errorMessage);
		} while(input < desiredMin || input > desiredMax);

		return input;
	}

	private boolean isInteger(String str) {
		try {
			int test = Integer.parseInt(str);
		} catch(Exception e) {
			return false;
		}

		return true;
	}

	private boolean isDesired(String str, String...desiredValues) {
		for(String elem : desiredValues) {
			if(str.equalsIgnoreCase(elem)) return true;
	    }
		return false;
	}




}//endClass
