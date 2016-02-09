package it.tsoru.aisc.model;

import it.tsoru.aisc.tokens.Tokenizer;

import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Sentence {
	
	private String sentence;
	private TreeSet<String> tokens;
	private Tokenizer tokenizer;
	
	public Sentence(String sentence) {
		this.sentence = sentence;
	}

	public Sentence(String sentence, Tokenizer tokenizer) {
		this.sentence = sentence;
		this.tokenizer = tokenizer;
		this.tokens = tokenizer.tokenize(sentence);
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

	public Tokenizer getTokenizer() {
		return tokenizer;
	}


}
