package it.tsoru.aisc.tokens;

import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public interface Tokenizer {

	public TreeSet<String> tokenize(String sentence);
	
}
