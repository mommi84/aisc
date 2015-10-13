package it.tsoru.aisc.model;

import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Sentence {
	
	private String sentence;
	private TreeSet<String> tokens;
	
	public Sentence(String sentence) {
		this.sentence = sentence;
	}

	public Sentence(String sentence, TreeSet<String> tokens) {
		this.sentence = sentence;
		this.tokens = tokens;
	}

	public TreeSet<String> getTokens() {
		return tokens;
	}

	public void setTokens(TreeSet<String> tokens) {
		this.tokens = tokens;
	}

	public String getSentence() {
		return sentence;
	}
	
	public String toString() {
		return sentence + "\t" + tokens;
	}

}
