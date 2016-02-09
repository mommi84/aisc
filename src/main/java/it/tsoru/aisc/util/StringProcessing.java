package it.tsoru.aisc.util;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class StringProcessing {
	
	public static String alphanum(String string) {
		return string.replaceAll("[^A-Za-z0-9 ]", "");
	}


}
