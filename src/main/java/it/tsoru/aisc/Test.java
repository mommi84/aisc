package it.tsoru.aisc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import it.tsoru.aisc.io.Dl4j;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Test {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		Dl4j.loadAll();
				
		String input = null;
		while(!(input = readInput()).equals("")) {
			saveVector(input);
		}
		
	}

	private static void saveVector(String input) throws FileNotFoundException {
		
		PrintWriter pw = new PrintWriter(new File(input+".dat"));
		
		double[] vec = Dl4j.getVector(input);
		
		for(double d : vec)
			pw.write(d + "\n");
		
		pw.close();
	}
	
	private static String readInput() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String input;

			if ((input = br.readLine()) != null) {
				return input;
			}

		} catch (IOException io) {
			io.printStackTrace();
		}
		return null;
	}

}
