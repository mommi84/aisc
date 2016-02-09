package it.tsoru.aisc;

import it.tsoru.aisc.io.Dl4j;
import it.tsoru.aisc.io.QuestionHandler;
import it.tsoru.aisc.io.Stopwords;
import it.tsoru.aisc.model.Question;
import it.tsoru.aisc.model.Sentence;
import it.tsoru.aisc.tokens.BasicTokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class AISCMain {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		String filepath;
		boolean isTraining;
		if(args.length < 2) {
			// default values
			filepath = "datasets/training_set.tsv";
			isTraining = true;
		} else {
			filepath = args[0];
			isTraining = Boolean.parseBoolean(args[1]);
		}
		
		Stopwords.load();
		
		Dl4j.loadAll();
//		Dl4j.loadVectors();
		
		QuestionHandler qs = new QuestionHandler(filepath, new BasicTokenizer(), isTraining);
		qs.load();
				
//		evaluation(qs);
		validation(qs);
		
		Dl4j.saveVectors();
		
	}

	private static void validation(QuestionHandler qs) throws FileNotFoundException, IOException {
		
		PrintWriter pw = new PrintWriter(new File("predictions.csv"));
		pw.write("id,correctAnswer");
		
		for(int i=1; qs.hasNext(); i++) {
			
			Question q = qs.next();
			
			HashMap<String, Sentence> ans = q.getAnswers();
			Sentence qSent = q.getqSent();
			String id = q.getId();
						
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
	private static void evaluation(QuestionHandler qs) throws IOException {
		
		int corrPred = 0;

		PrintWriter pw = new PrintWriter(new File("predictions.csv"));
		pw.write("id,correctAnswer");
		
		for(int i=1; qs.hasNext(); i++) {
			
			Question q = qs.next();
			
			HashMap<String, Sentence> ans = q.getAnswers();
			Sentence qSent = q.getqSent();
			String correct = q.getCorrect();
			String id = q.getId();
						
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


	

}
