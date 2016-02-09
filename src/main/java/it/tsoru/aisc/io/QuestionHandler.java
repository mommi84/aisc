package it.tsoru.aisc.io;

import it.tsoru.aisc.model.Question;
import it.tsoru.aisc.tokens.Tokenizer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.opencsv.CSVReader;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class QuestionHandler {
	
	private String dataset;
	private Tokenizer tokenizer;
	private boolean isTraining;
	
	private CSVReader reader = null;
	private String[] next = null;
	
	public QuestionHandler(String dataset, Tokenizer tokenizer, boolean isTraining) {
		this.dataset = dataset;
		this.tokenizer = tokenizer;
	}
	
	public void load() throws IOException {
		reader = new CSVReader(new FileReader(new File(dataset)), '\t');
		reader.readNext(); // skip header
	}

	public boolean hasNext() throws IOException {
		if(next == null)
			next = reader.readNext();
		return next != null;
	}
	
	public Question next() throws IOException {
		if(next == null) {
			return new Question(reader.readNext(), tokenizer, isTraining);
		} else {
			String[] temp = Arrays.copyOf(next, next.length);
			next = null;
			return new Question(temp, tokenizer, isTraining);
		}
	}

	public void close() throws IOException {
		reader.close();
	}
	
}
