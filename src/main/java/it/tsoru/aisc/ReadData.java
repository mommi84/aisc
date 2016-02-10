package it.tsoru.aisc;

import it.tsoru.aisc.io.Dl4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class ReadData {

	public static void main(String[] args) throws FileNotFoundException {
		
		String[] term = {"Barack_Obama", "Michelle_Obama", "wife", "husband",
				"Bill_Clinton", "Hillary_Clinton", "spouse"};
		
		double[] b = getVector(term[0]);
		double[] m = getVector(term[1]);
		double[] w = getVector(term[2]);
		double[] d = getVector(term[3]);
		
		double[] c = getVector(term[4]);
		double[] h = getVector(term[5]);
		
		double[] s = getVector(term[6]);
		
		ArrayList<double[]> terms = new ArrayList<>();
		terms.add(b);
		terms.add(m);
		terms.add(w);
		terms.add(d);
		terms.add(c);
		terms.add(h);
		terms.add(s);
		
		for(String t : term)
			System.out.print(","+t);
		System.out.println();
		
		for(int i=0; i<terms.size(); i++) {
			double[] t1 = terms.get(i);
			System.out.print(term[i]);
			for(int j=0; j<terms.size(); j++) {
				double[] t2 = terms.get(j);
				double sim = Dl4j.cosine(t1, t2);
				System.out.print(","+sim);
			}
			System.out.println();
		}
		
		System.out.println("cos(Barack_Obama + wife, Michelle_Obama) = " + Dl4j.cosine(sum(b, w), m));
		System.out.println("cos(Barack_Obama, Michelle_Obama + husband) = " + Dl4j.cosine(sum(m, d), b));
		
		System.out.println("cos(wifeOf_Obamas, wifeOf_Clintons) = "+Dl4j.cosine(diff(b, m), diff(c, h)));
		
		System.out.println("cos(Michelle_Obama, Barack_Obama + Hillary_Clinton - Bill_Clinton) = " + Dl4j.cosine(m, diff(sum(b, h), c)));
		
		System.out.println("cos(Barack_Obama + spouse, Michelle_Obama) = " + Dl4j.cosine(sum(b, s), m));
		System.out.println("cos(Michelle_Obama + spouse, Barack_Obama) = " + Dl4j.cosine(sum(m, s), b));
		
	}

	private static double[] getVector(String input) throws FileNotFoundException {
		double[] d = new double[300];
		Scanner in = new Scanner(new File("dat/" +input + ".dat"));
		for(int i=0; in.hasNextLine() && i<300; i++)
			d[i] = Double.parseDouble(in.nextLine());
		in.close();
		return d;
	}

	private static double[] sum(double[] a, double[] b) {
		double[] d = new double[a.length];
		for(int i=0; i<a.length; i++)
			d[i] = a[i] + b[i];
		return d;
	}


	private static double[] diff(double[] a, double[] b) {
		double[] d = new double[a.length];
		for(int i=0; i<a.length; i++) {
			d[i] = a[i] - b[i];
//			System.out.print(d[i]+",");
		} 
//		System.out.println();
		return d;
	}
}
