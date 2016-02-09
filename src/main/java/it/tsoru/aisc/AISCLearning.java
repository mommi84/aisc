package it.tsoru.aisc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class AISCLearning {

	private static HashMap<String, Double> sim = new HashMap<String, Double>();
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// FIXME log files are corrupted!
		
		// index similarities...
		Scanner in = new Scanner(new File("experiments/sim_training_.txt"));
		while(in.hasNextLine()) {
			String l = in.nextLine().substring(4);
			String[] line = l.split("\\) = ");
			// line[0] is "word1, word2"
			sim.put(line[0], Double.parseDouble(line[1]));
		}
		in.close();
		
		System.out.println(sim.size() + " similarities loaded.");
		
		System.out.println(getSim("epidemic", "symptoms"));
		
		// split training set in 90/10
		
		// 

	}
	
	private static Double getSim(String word1, String word2) {
		String k = word1 + ", " + word2;
		if(sim.containsKey(k))
			return sim.get(k);
		String kInv = word2 + ", " + word1;
		if(sim.containsKey(kInv))
			return sim.get(kInv);
		return 0.0;
	}

}