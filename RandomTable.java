import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class RandomTable {
	private List<List<Cell>> randomTable;
	private Map<String, String> cellRecords = new HashMap<String, String>();

	public static final int MAX_SIZE = 800;
	public static final int MIN_SIZE = 1;

	private TableFileHandler tableFile;

	//*****************************************************************************************//
	private Cell generateRandomCell(int keyLength, int valueLength) {
		RandomText rand = new RandomText();
		String randomKey, randomValue;

		do {
			randomKey = rand.nextString(keyLength);
		} while(cellRecords.containsKey(randomKey));

		randomValue = rand.nextString(valueLength);

		cellRecords.put(randomKey, randomValue);
		return new Cell(randomKey, randomValue);
	}

	private List<Cell> generateNewRow(int numberOfElements, int keyLength, int valueLength) {
		List<Cell> cells = new ArrayList<Cell>();

		for(int size = 0; size < numberOfElements; size++) {
			cells.add(generateRandomCell(keyLength, valueLength));
		}

		return cells;
	}

	private List<List<Cell>> generateCellTable(int numberOfRows, int numberOfColumns, int keyLength, int valueLength) {
		List<List<Cell>> cellTable = new ArrayList<List<Cell>>();

		for(int row = 0; row < numberOfRows; row++) {
			cellTable.add(generateNewRow(numberOfColumns, keyLength, valueLength));
		}

		return cellTable;
	}

	//*****************Constructors****************//
	public RandomTable(TableFileHandler tableFile) {
		this.tableFile = tableFile;
		randomTable = tableFile.getTable();
		cellRecords = tableFile.getCellRecords();
		updateFile();
	}

	public RandomTable(int numberOfRows, int numberOfColumns, int stringLength) {
		create(numberOfRows, numberOfColumns, stringLength);
	}

	//*****************Properties****************//
	public boolean isEmpty() {
		return randomTable.isEmpty() || randomTable == null; // change!
	}

	public boolean containsKey(String key) {
		if(cellRecords.containsKey(key)) { return true; }
		return false;
	}

	public Cell getCell(int rowIndex, int colIndex) {
		return randomTable.get(rowIndex).get(colIndex);
	}

	public int getNumberOfRows() {
		return randomTable.size();
	}

	public int getNumberOfColumns() {
		return randomTable.get(0).size();
	}

	public int getStringLength() {
		return getCell(0, 0).getKeyLength();
	}

	public String getKey(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getKey();
	}

	public String getValue(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getValue();
	}

	public int maxRowIndex() {
		return getNumberOfRows() - 1;
	}

	public int maxColIndex() {
		return getNumberOfColumns() - 1;
	}

	public Set<String> getKeySet() {
		return cellRecords.keySet();
	}

	//*****************Methods****************//
	public void create(int numberOfRows, int numberOfColumns, int stringLength) {
		reset();
		randomTable = generateCellTable(numberOfRows, numberOfColumns, stringLength, stringLength);
		updateFile();
	}

	public void display() {
		tableFile.printToConsole();
	}

	private void editCell(int rowIndex, int colIndex, String newKey, String newValue) {
		Cell targetCell = getCell(rowIndex, colIndex);
		String oldKey = targetCell.getKey();

		targetCell.editKey(newKey);
		targetCell.editValue(newValue);
		cellRecords.remove(oldKey);
		cellRecords.put(newKey, newValue);
		updateFile();
	}

	public void editKeyAt(int rowIndex, int colIndex, String newKey) {
		String origValue = getCell(rowIndex, colIndex).getValue();
		editCell(rowIndex, colIndex, newKey, origValue);
	}

	public void editValueAt(int rowIndex, int colIndex, String newValue) {
		String origKey = getCell(rowIndex, colIndex).getKey();
		editCell(rowIndex, colIndex, origKey, newValue);
	}

	public void addRow() {
		int numberOfCellsPerRow = getNumberOfColumns();
		randomTable.add(generateNewRow(numberOfCellsPerRow, 3, 3));
		updateFile();
	}

	public void addColumn() {
		for(List<Cell> row : randomTable) {
			row.add(generateRandomCell(3, 3));
		}
		updateFile();
	}

	public void sort() {
		//convert 2d to 1d array
		List<Cell> oneDimensional = new ArrayList<Cell>();
		for(List<Cell> row : randomTable) {
			for(Cell c : row) {
				oneDimensional.add(c);
			}
		}
		//sort
		Collections.sort(oneDimensional);
		//convert back to 2d
		int index = 0;
		for(int row = 0; row < getNumberOfRows(); row++) {
			for(int col = 0; col < getNumberOfColumns(); col++) {
				randomTable.get(row).set(col, oneDimensional.get(index));
				index++;
			}
		}

		updateFile();
	}

	public void reset() {
		randomTable = new ArrayList<List<Cell>>();
		cellRecords = new HashMap<String, String>();
		updateFile();
	}

	public void search(String text) {
		List<TableIndex> results = findIndicesContaining(text);

		if(results.isEmpty()) {
			System.out.println("No occurences found");
			return;
		}

		Cell currentCell;
		int countInKey, countInValue;
		int totalCountInKey = 0;
		int totalCountInValue = 0;

		System.out.printf("%-15s%-20s%-15s%-15s%s\n", "INDICES", "CELL", "KEY", "VALUE", "TOTAL");
		for(TableIndex index : results) {
			currentCell = getCell(index.getRow(), index.getCol());
			countInKey = currentCell.countOccurencesInKey(text);
			countInValue = currentCell.countOccurencesInValue(text);
			System.out.printf("%-15s%-20s%-15d%-15d%-15d\n", index, currentCell, countInKey, countInValue, countInKey + countInValue);
			totalCountInKey += countInKey;
			totalCountInValue += countInValue;
		}

		System.out.printf("%-35s%-15d%-15d[%d]\n", "[TOTAL]", totalCountInKey, totalCountInValue, totalCountInKey + totalCountInValue);
	}




	//**************************************************************************************//
	public String toString() {
		if(randomTable.isEmpty()) {
			return "";
	 	}

		StringBuilder table = new StringBuilder();
		for(List<Cell> list : randomTable) {
			for(Cell cell : list) {
				table.append("|" + cell + "|");
			}
			table.append("\n");
		}

		return table.toString();
	}

	private void updateFile() {
		tableFile.saveToFile(this.toString());
	}

	/*private void readFile() {
		try {
			tableFile.parseFile();
		} catch(Exception e) {
			System.out.println("Error");
		}
		randomTable = tableFile.getTable();
	}*/

	private List<TableIndex> findIndicesContaining(String text) {
		List<TableIndex> indices = new ArrayList<TableIndex>();
		Cell currentCell;

		for(int row = 0; row < getNumberOfRows(); row++) {
			for(int col = 0; col < getNumberOfColumns(); col++) {
				currentCell = getCell(row, col);
				if(currentCell.contains(text)) {
					indices.add(new TableIndex(row, col));
				}
			}
		}

		return indices;
	}



	private class TableIndex {
		private int rowIndex, colIndex;

		public TableIndex(int r, int c) {
			rowIndex = r;
			colIndex = c;
		}

		public int getRow() {
			return rowIndex;
		}

		public int getCol() {
			return colIndex;
		}

		public String toString() {
			return String.format("(%d, %d)", rowIndex, colIndex);
		}
	}







}//endClass
