import java.util.*;

public class Test {
	public static void main(String[] args) {
		String rawText = "|(a@@as,  asd)||(asdas, a&&sd)||(asd~~as, asd)||(asdas, asd)||(asdas, asd)|";
		String raw2 = "asdfsdfsdfsdffs||||||";

		String[] splat = rawText.split( "(?<=(\\)\\|))" );
		String[] splat2 = raw2.split( "(?<=(\\)\\|))" );

		System.out.println(raw2);
		raw2.replace("a", "*");
		System.out.println(raw2);

		String str = "|(key,       value)|";

		String noBorders = str.replace("|(", "")
		                      .replace(")|", "")
							  .replaceFirst("(\\s)+", " ");

		System.out.println(noBorders);
		System.out.println(str);

		String key = noBorders.substring(0, noBorders.indexOf(", "));
		String val = noBorders.replace(key + ", ", "");
		System.out.println(key);
		System.out.println(val);

		System.out.println("    s".replace("\\s+", " "));
		System.out.println(" s");

		/*for(String str : splat)
			System.out.println(str);

		System.out.println("------------------");

		for(String str : splat2) {
			System.out.println(str);
		}

		/*System.out.println( substringBetween("key, value)|", "|(", ")|") );
		System.out.println( "key, vall)|".indexOf("|(") );

		System.out.println(checkLine(rawText));
		System.out.println( checkLine("|(key,  value)|") );
		System.out.println( ",  ".matches(", ") );*/
		System.out.println(checkLine("|(qwe,  qee)|"));


	}

	public static boolean checkLine(String line) {
		String pattern = "((\\|\\(\\p{ASCII}+)(, )(\\p{ASCII}+\\)\\|))";

		return line.matches(pattern);
	}

	public static String substringBetween(String str, String start, String end) {
		return str.substring( str.indexOf(start) + start.length(), str.indexOf(end) );

	}

	/*public static String[] splitKeepDelimeters(String str, String delimeter) {
		if str.contains(delimeter)
	}*/
}
