package it.tsoru.aisc;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Word {
	
	private String word;
	private double[] vec;

	public Word(String w, double[] d) {
		this.word = w;
		this.vec = d;
	}

	public String getWord() {
		return word;
	}

	public double[] getVec() {
		return vec;
	}

}
