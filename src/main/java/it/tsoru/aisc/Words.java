package it.tsoru.aisc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Words {
	
	private static HashMap<String, Word> map = new HashMap<>();
	
	public static void load() throws FileNotFoundException {
		
		File dir = new File("dat/");
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".dat");
			}
		});
		
		for(File f : files) {
			double[] d = new double[300];
			Scanner in = new Scanner(f);
			int h = 0;
			for(int i=0; in.hasNextLine() && i<300 + h; i++) {
				String line = in.nextLine();
				try {
					d[i-h] = Double.parseDouble(line);
				} catch (NumberFormatException e) {
					System.out.println("Header found in "+f.getName()+": "+line);
					h++;
				}
			}
			in.close();
			String w = f.getName().substring(0, f.getName().length() - 4);
			map.put(w, new Word(w, d));
		}			
	}
	
	public static Word get(String word) {
		return map.get(word);
	}

	public static Set<String> getAllTerms() {
		return map.keySet();
	}
	
}
