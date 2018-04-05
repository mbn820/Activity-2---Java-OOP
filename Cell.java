public class Cell implements Comparable<Cell> {
	private String key, value;
	private StringCounter counter = new StringCounter();

	public Cell(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public void editKey(String replacement) {
		key = replacement;
	}

	public void editValue(String replacement) {
		value = replacement;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public int getKeyLength() {
		return key.length();
	}

	public int getValueLength() {
		return value.length();
	}

	public boolean contains(String text) {
		if(key.contains(text) || value.contains(text)) { return true; }
		return false;
	}

	public int countOccurencesInKey(String text) {
		int countInKey = counter.countOccurencesOf(text, key);
		return countInKey;
	}

	public int countOccurencesInValue(String text) {
		int countInValue = counter.countOccurencesOf(text, value);
		return countInValue;
	}

	public int compareTo(Cell c) {
		return key.compareTo(c.key);
	}

	public String toString() {
		return String.format("(%s, %s)", key, value);
	}

}//endClass
