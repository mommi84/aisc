package it.tsoru.aisc;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.TreeSet;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;
import it.tsoru.aisc.io.QuestionHandler;
import it.tsoru.aisc.io.Stopwords;
import it.tsoru.aisc.model.NLPNode;
import it.tsoru.aisc.model.Question;
import it.tsoru.aisc.tokens.BasicTokenizer;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class AISCNlpParser {
	
	private static LexicalizedParser parser = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
	private static TokenizerFactory<Word> tokenizerFactory;

	public static void main(String[] args) throws IOException {
		
		String filepath = args[0];
		Boolean isTraining = Boolean.parseBoolean(args[1]);
		
		QuestionHandler qs = new QuestionHandler(filepath, new BasicTokenizer(), isTraining);
		qs.load();
		
		Stopwords.load();

		while(qs.hasNext()) {
			Question q = qs.next();
			String sentence = q.getQuestion();
			List<Word> tokens = getTokenizerFactory().getTokenizer(new StringReader(sentence)).tokenize();
			Tree tree = parser.parse(tokens);
			System.out.println(tree.pennString());
			TreeSet<NLPNode> nodes = new TreeSet<NLPNode>();
			toNodes(null, tree, nodes);
			boolean[] values = new boolean[4];
			String wh = null;
			for(NLPNode n : nodes)
				if(n.getName().startsWith("WH")) {
					values[0] = values[0] || n.getName().equals("WHADJP");
					values[1] = values[1] || n.getName().equals("WHAVP");
					values[2] = values[2] || n.getName().equals("WHNP");
					values[3] = values[3] || n.getName().equals("WHPP");
					wh = n.getFirstChild();
				}
			for(boolean v : values)
				System.out.print(v+"\t");
			System.out.println(wh);
		}
		
	}
	
	private static void toNodes(NLPNode parent, Tree tree, TreeSet<NLPNode> nodes) {
		
		String treeName = tree.value();
		
		NLPNode node = new NLPNode(parent, treeName, tree.getChild(0).toString());
		for(NLPNode n : nodes)
			if(n.getName().equals(treeName))
				node.incr();
		nodes.add(node);
		
		for(Tree subtree : tree.children()) {
			if(!subtree.isLeaf())
				toNodes(node, subtree, nodes);
			else
				node.setValue(subtree.value());
		}
	}


	private static TokenizerFactory<Word> getTokenizerFactory() {
		if (tokenizerFactory == null) {
			tokenizerFactory =  PTBTokenizer.factory();
		}
		return tokenizerFactory;
	}

}
