package it.tsoru.aisc.tokens;

import it.tsoru.aisc.io.Stopwords;

import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class BasicTokenizer implements Tokenizer {

	public TreeSet<String> tokenize(String sentence) {
		String[] qTokens = sentence.toLowerCase().split(" ");
		TreeSet<String> words = new TreeSet<String>();
		for (String t : qTokens)
			if (!Stopwords.isPresent(t))
				words.add(t);
		return words;
	}

}
