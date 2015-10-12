package it.tsoru.aisc.io;

import java.io.File;
import java.io.IOException;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Dl4j {
	
	private static Word2Vec vec;
	
	public static double sim(String a, String b) {
//		return Math.random();
		return vec.similarity(a, b);
	}
	
	public static void loadCorpus() throws IOException {
		File gModel = new File("word2vec/GoogleNews-vectors-negative300.bin.gz");
		vec = WordVectorSerializer.loadGoogleModel(gModel, true);
	}

}
