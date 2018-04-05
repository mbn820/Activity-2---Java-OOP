import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class FileHandler {
	protected File file;
	protected Scanner scan;

	public FileHandler(File f) {
		this.file = f;
	}

	public void printToConsole() {
		try {
			scan = new Scanner(this.file);
			while(scan.hasNextLine()) {
				System.out.println(scan.nextLine());
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			scan.close();
		}
	}

	public void saveToFile(String str) {
		try {
			FileWriter writer = new FileWriter(this.file);
			writer.write(str);
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public List<String> storeLinesToList() {
		List<String> lines = new ArrayList<String>();

		try {
			scan = new Scanner(file);
			while( scan.hasNextLine() ) {
				lines.add( scan.nextLine() );
			}
		} catch(Exception e) {
			System.out.println("Error Occured");
		} finally {
			scan.close();
		}

		return lines;
	}


}

class InvalidTableFormatException extends Exception {}

class DuplicateKeyException extends Exception {}

class EmptyTextFileException extends Exception {}
