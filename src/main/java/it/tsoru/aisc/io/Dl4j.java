package it.tsoru.aisc.io;

import it.tsoru.aisc.util.DataIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Dl4j {
	
	private static final int CACHE_INTERVAL = 20;

	private static HashMap<String, double[]> vectors = new HashMap<String, double[]>();

	private static Word2Vec vec;

	public static double sim(String a, String b) throws FileNotFoundException, IOException {
//		 return Math.random();

		double[] vecA, vecB;

		if (vectors.containsKey(a))
			vecA = vectors.get(a);
		else {
			vecA = vec.getWordVector(a);
			System.out.println("vecA loaded");
//			vecA = getRandomVector();
			vectors.put(a, vecA);
			// once in CACHE_INTERVAL new words...
			if(vectors.size() % CACHE_INTERVAL == 0)
				saveVectors();
		}

		if (vectors.containsKey(b))
			vecB = vectors.get(b);
		else {
			vecB = vec.getWordVector(b);
			System.out.println("vecB loaded");
//			vecB = getRandomVector();
			vectors.put(b, vecB);
			// once in CACHE_INTERVAL new words...
			if(vectors.size() % CACHE_INTERVAL == 0)
				saveVectors();
		}
				
		return cosine(vecA, vecB);
	}
	
	public static void saveVectors() throws FileNotFoundException, IOException {
		DataIO.serialize(vectors, "vectors.map");
	}

	@SuppressWarnings("unused")
	private static double[] getRandomVector() {
		double[] v = new double[100];
		for (int i = 0; i < v.length; i++)
			v[i] = Math.random() - 0.5;
		return v;
	}

	private static double cosine(double[] a, double[] b) {
		
		double sum = 0.0, denA = 0.0, denB = 0.0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i] * b[i];
			denA += a[i] * a[i];
			denB += b[i] * b[i];
		}
		
		System.out.println("cosine calculated");
		
		return sum / (Math.sqrt(denA) * Math.sqrt(denB));
	}
	
	public static void loadAll() throws IOException, ClassNotFoundException {
		loadCorpus();
		loadVectors();
	}

	public static void loadVectors() throws ClassNotFoundException, IOException {
		try {
			vectors = DataIO.readMap("vectors.map");
			System.out.println("vectors.map loaded");
		} catch (FileNotFoundException e) {
			System.out.println("vectors.map not found, skipping...");
		}
	}

	public static void loadCorpus() throws IOException {
		File gModel = new File("word2vec/GoogleNews-vectors-negative300.bin.gz");
		vec = WordVectorSerializer.loadGoogleModel(gModel, true);
	}

}
