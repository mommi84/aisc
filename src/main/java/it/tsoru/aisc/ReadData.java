package it.tsoru.aisc;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class ReadData {

	public static void main(String[] args) throws FileNotFoundException {
		
		Words.load();
		
		ArrayList<String> term = new ArrayList<>(Words.getAllTerms());
		
		for(String t : term)
			System.out.print("\t"+t);
		System.out.println();
		
		for(int i=0; i<term.size(); i++) {
			double[] t1 = Words.get(term.get(i)).getVec();
			System.out.print(term.get(i));
			for(int j=0; j<term.size(); j++) {
				double[] t2 = Words.get(term.get(j)).getVec();
				double sim = dist(t1, t2);
				System.out.print("\t"+sim);
			}
			System.out.println();
		}
		
		dist(
				sum(
						Words.get("Barack_Obama"), 
						Words.get("wife")
				),
				Words.get("Michelle_Obama")
		);

		dist(
				sum(
						Words.get("Barack_Obama"), 
						Words.get("wife")
				),
				Words.get("Rome")
		);

		dist(
				diff(
						sum(
								Words.get("Italy"),
								Words.get("Beijing")
						),
						Words.get("China")
				),
				Words.get("Rome")
		);

		dist(
				diff(
						sum(
								Words.get("Italy"),
								Words.get("Beijing")
						),
						Words.get("China")
				),
				Words.get("Michelle_Obama")
		);

		dist(
				diff(
						sum(
								Words.get("king"),
								Words.get("woman")
						),
						Words.get("man")
				),
				Words.get("queen")
		);
		
		
//		System.out.println("sim(Barack_Obama, Michelle_Obama + husband) = " + 
//		
//		System.out.println("cos(wifeOf_Obamas, wifeOf_Clintons) = " + 
//		
//		System.out.println("sim(Michelle_Obama, Barack_Obama + Hillary_Clinton - Bill_Clinton) = " + euclidean(m, diff(sum(b, h), c)));
//		System.out.println("sim(Rome, Italy + Beijing - China) = " + euclidean(m, diff(sum(b, h), c)));
		
//		System.out.println("cos(Barack_Obama + spouse, Michelle_Obama) = " + Dl4j.cosine(sum(b, s), m));
//		System.out.println("cos(Michelle_Obama + spouse, Barack_Obama) = " + Dl4j.cosine(sum(m, s), b));
		
	}

	private static double dist(Word w1, Word w2) {
		System.out.print("Computing dist("+w1.getWord()+", "+w2.getWord()+") = ");
		double d = dist(w1.getVec(), w2.getVec());
		System.out.println(d);
		return d;
	}

	private static double dist(double[] a, double[] b) {
		double num = 0d;
		for(int i=0; i<a.length; i++) {
			num += Math.pow(a[i] - b[i], 2d);
		}
		return Math.sqrt(num);
	}

	private static Word sum(Word w1, Word w2) {
		double[] a = w1.getVec(), b = w2.getVec();
		double[] d = new double[a.length];
		for(int i=0; i<a.length; i++)
			d[i] = a[i] + b[i];
		return new Word(w1.getWord()+"+"+w2.getWord(), d);
	}


	private static Word diff(Word w1, Word w2) {
		double[] a = w1.getVec(), b = w2.getVec();
		double[] d = new double[a.length];
		for(int i=0; i<a.length; i++) {
			d[i] = a[i] - b[i];
		} 
		return new Word(w1.getWord()+"-"+w2.getWord(), d);
	}
}
