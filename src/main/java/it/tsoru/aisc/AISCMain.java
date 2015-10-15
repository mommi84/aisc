package it.tsoru.aisc;

import it.tsoru.aisc.io.Dl4j;
import it.tsoru.aisc.io.Questions;
import it.tsoru.aisc.io.Stopwords;
import it.tsoru.aisc.model.Sentence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class AISCMain {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		String filepath;
		if(args.length != 1)
			filepath = "datasets/training_set.tsv";
		else
			filepath = args[0];
		
		Stopwords.load();
		
		Dl4j.loadAll();
//		Dl4j.loadVectors();
		
		Questions qs = new Questions(filepath);
		qs.load();
				
//		evaluation(qs);
		validation(qs);
		
		Dl4j.saveVectors();
		
	}

	private static void validation(Questions qs) throws FileNotFoundException, IOException {
		
		PrintWriter pw = new PrintWriter(new File("predictions.csv"));
		pw.write("id,correctAnswer");
		
		for(int i=1; qs.hasNext(); i++) {
			
			String[] q = qs.next();
			String id = q[0], question = alphanum(q[1]);
			String aA = alphanum(q[2]), aB = alphanum(q[3]), aC = alphanum(q[4]), aD = alphanum(q[5]);
			System.out.println("\n" + id + ". " + question);
			
			// tentative
			Sentence qSent = new Sentence(question, getWords(question));
			Sentence aSent = new Sentence(aA, getWords(aA));
			Sentence bSent = new Sentence(aB, getWords(aB));
			Sentence cSent = new Sentence(aC, getWords(aC));
			Sentence dSent = new Sentence(aD, getWords(aD));
			HashMap<String, Sentence> ans = new HashMap<String, Sentence>();
			ans.put("A", aSent);
			ans.put("B", bSent);
			ans.put("C", cSent);
			ans.put("D", dSent);
						
			double high = Double.MIN_VALUE;
			String best = "N/A";
			for(String k : ans.keySet()) {
				Sentence s = ans.get(k);
				System.out.println(s.getTokens());
				double score = 0.0;
				int tot = 0;
				for(String qToken : qSent.getTokens()) {
					for(String aToken : s.getTokens()) {
						double d = Dl4j.sim(qToken, aToken);
						System.out.println("sim("+qToken+", "+aToken+") = "+d);
						score += d;
						tot ++;
					}
				}
				score /= tot;
				System.out.println("totSim(q, "+k+") = "+score);
				if(score > high) {
					high = score;
					best = k;
				}
			}
			System.out.println("best = "+best);
			
			System.out.println("processed = "+i);
			
			pw.write("\n"+id+","+best);

		}
		
		pw.close();
	}

	@SuppressWarnings("unused")
	private static void evaluation(Questions qs) throws IOException {
		
		int corrPred = 0;

		PrintWriter pw = new PrintWriter(new File("predictions.csv"));
		pw.write("id,correctAnswer");
		
		for(int i=1; qs.hasNext(); i++) {
			
			String[] q = qs.next();
			String id = q[0], question = alphanum(q[1]), correct = q[2];
			String aA = alphanum(q[3]), aB = alphanum(q[4]), aC = alphanum(q[5]), aD = alphanum(q[6]);
			System.out.println("\n" + id + ". " + question);
			
			// tentative
			Sentence qSent = new Sentence(question, getWords(question));
			Sentence aSent = new Sentence(aA, getWords(aA));
			Sentence bSent = new Sentence(aB, getWords(aB));
			Sentence cSent = new Sentence(aC, getWords(aC));
			Sentence dSent = new Sentence(aD, getWords(aD));
			HashMap<String, Sentence> ans = new HashMap<String, Sentence>();
			ans.put("A", aSent);
			ans.put("B", bSent);
			ans.put("C", cSent);
			ans.put("D", dSent);
						
			double high = Double.MIN_VALUE;
			String best = "N/A";
			for(String k : ans.keySet()) {
				Sentence s = ans.get(k);
				System.out.println(s.getTokens());
				double score = 0.0;
				int tot = 0;
				for(String qToken : qSent.getTokens()) {
					for(String aToken : s.getTokens()) {
						double d = Dl4j.sim(qToken, aToken);
						System.out.println("sim("+qToken+", "+aToken+") = "+d);
						score += d;
						tot ++;
					}
				}
				score /= tot;
				System.out.println("totSim(q, "+k+") = "+score);
				if(score > high) {
					high = score;
					best = k;
				}
			}
			System.out.println("best = "+best+", correct = "+correct);
			if(best.equals(correct))
				corrPred ++;
			
			System.out.println(corrPred + " over " + i + " = " + ((double) corrPred / i));
			
			pw.write("\n"+id+","+best);

		}
		
		pw.close();
	}

	private static String alphanum(String string) {
		return string.replaceAll("[^A-Za-z0-9 ]", "");
	}

	private static TreeSet<String> getWords(String sentence) {
		String[] qTokens = sentence.toLowerCase().split(" ");
		TreeSet<String> words = new TreeSet<String>();
		for(String t : qTokens)
			if(!Stopwords.isPresent(t))
				words.add(t);
		return words;
	}
	
	

}
