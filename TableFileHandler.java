import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class TableFileHandler extends FileHandler {
	private List<List<Cell>> randTable;
	private Map<String, String> cellRecords;

	public static final String CELL_FORMAT = "\\|\\(\\p{ASCII}+, \\p{ASCII}+\\)\\|";

	public TableFileHandler(File file) throws InvalidTableFormatException, DuplicateKeyException {
		super(file);
		reset();

		try {
			parseFileLineByLine();
		} catch (InvalidTableFormatException e) {
			throw new InvalidTableFormatException();
		} catch (DuplicateKeyException e) {
			throw new DuplicateKeyException();
		} catch (EmptyTextFileException e) {
			reset();
		}
	}

	private String removeBordersAndExtraSpaces(String str) {
		return str.replace("|(", "")
		          .replace(")|", "")
				  .replaceFirst(",\\s+", ", ");
	}

	public Cell parseStringToCell(String str) throws InvalidTableFormatException, DuplicateKeyException {
		if( !str.matches(CELL_FORMAT) ) { throw new InvalidTableFormatException(); }

		String noBorder = removeBordersAndExtraSpaces(str); // "|(key,   value)|" >>> "key, value"

		String key = noBorder.substring( 0, noBorder.indexOf(", ") );
		String value = noBorder.replace(key + ", ", "");

		if(cellRecords.containsKey(key)) {
			throw new DuplicateKeyException();
		}

		cellRecords.put(key, value);
		return new Cell(key, value);
	}

	public List<Cell> parseLineToListOfCells(String row) throws InvalidTableFormatException, DuplicateKeyException {
		String[] separated = row.split( "(?<=(\\)\\|))" );

		List<Cell> converted = new ArrayList<Cell>();

		try {
	    	for(String str : separated)
				converted.add( parseStringToCell(str) );
		} catch (InvalidTableFormatException e) {
			throw new InvalidTableFormatException();
		} catch (DuplicateKeyException e) {
			throw new DuplicateKeyException();
		}

		return converted;
	}

	public void parseFileLineByLine() throws InvalidTableFormatException, DuplicateKeyException, EmptyTextFileException {
		if( fileIsEmpty() ) { throw new EmptyTextFileException(); }

		List<String> lines = new ArrayList<String>( super.storeLinesToList() );

		try {
			for(String line : lines)
				randTable.add( parseLineToListOfCells(line) );
		} catch (InvalidTableFormatException e) {
			reset();
			throw new InvalidTableFormatException();
		} catch (DuplicateKeyException e) {
			reset();
			throw new DuplicateKeyException();
		}

	}

	private boolean fileIsEmpty() {
		return (super.file.length() == 0);
	}

	private void reset() {
		randTable = new ArrayList<List<Cell>>();
		cellRecords = new HashMap<String, String>();
	}

	public List<List<Cell>> getTable() {
		return randTable;
	}

	public Map<String, String> getCellRecords() {
		return cellRecords;
	}




}
