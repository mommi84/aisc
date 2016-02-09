package it.tsoru.aisc.model;

import it.tsoru.aisc.tokens.Tokenizer;
import it.tsoru.aisc.util.StringProcessing;

import java.util.HashMap;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Question {
	
	private String id;
	private String question;
	private String correct;
	private Sentence qSent, aSent, bSent, cSent, dSent;
	private String answerA, answerB, answerC, answerD;
	private HashMap<String, Sentence> answers;
	
	public Question(String[] q, Tokenizer tokenizer, boolean isTraining) {
		
		this.id = q[0];
		this.question = StringProcessing.alphanum(q[1]);
		
		int offset = isTraining ? 1 : 0;
			
		this.correct = isTraining ? q[2] : null;
		
		this.answerA = StringProcessing.alphanum(q[3 + offset]);
		this.answerB = StringProcessing.alphanum(q[4 + offset]);
		this.answerC = StringProcessing.alphanum(q[5 + offset]);
		this.answerD = StringProcessing.alphanum(q[6 + offset]);
//		System.out.println("\n" + id + ". " + question);
		
		// tentative
		this.qSent = new Sentence(question, tokenizer);
		this.aSent = new Sentence(answerA, tokenizer);
		this.bSent = new Sentence(answerB, tokenizer);
		this.cSent = new Sentence(answerC, tokenizer);
		this.dSent = new Sentence(answerD, tokenizer);
		this.answers = new HashMap<String, Sentence>();
		answers.put("A", aSent);
		answers.put("B", bSent);
		answers.put("C", cSent);
		answers.put("D", dSent);
		
	}

	public String getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public Sentence getqSent() {
		return qSent;
	}

	public HashMap<String, Sentence> getAnswers() {
		return answers;
	}

	public String getCorrect() {
		return correct;
	}
	
	
	
}
