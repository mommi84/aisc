package it.tsoru.aisc.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.opencsv.CSVReader;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Questions {
	
	private String dataset;
	
	private CSVReader reader = null;
	private String[] next = null;
	
	public Questions(String dataset) {
		this.dataset = dataset;
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
	
	public String[] next() throws IOException {
		if(next == null) {
			return reader.readNext();
		} else {
			String[] temp = Arrays.copyOf(next, next.length);
			next = null;
			return temp;
		}
	}

	public void close() throws IOException {
		reader.close();
	}
	
}
