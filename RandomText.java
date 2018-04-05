import java.util.Random;

public class RandomText {
	public char nextPrintableChar() {
		Random rand = new Random();
		int decimalForm = 32 + rand.nextInt(126 - 32); // 32 - 126, decimal number of ascii printable characters
		char randomChar = (char) decimalForm;
		return randomChar;
	}

	public String nextString(int stringLength) {
		char[] randomChars = new char[stringLength];
		for(int i = 0; i < stringLength; i++) {
			randomChars[i] = nextPrintableChar();
		}
		String randomString = new String(randomChars);
		return randomString;
	}
}
