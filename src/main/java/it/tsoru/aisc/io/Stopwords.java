package it.tsoru.aisc.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Stopwords {
	
	private static TreeSet<String> list;
	
	public static void load() throws FileNotFoundException {
		
		list = new TreeSet<String>();
		
		Scanner in = new Scanner(new File("stopwords-en.txt"));
		while(in.hasNextLine())
			list.add(in.nextLine());
		in.close();
		
	}
	
	public static boolean isPresent(String word) {
		return list.contains(word);
	}

}
