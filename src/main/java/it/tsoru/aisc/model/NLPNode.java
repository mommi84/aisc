package it.tsoru.aisc.model;

import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class NLPNode implements Comparable<NLPNode> {

	private NLPNode parent;
	private String name;
	private int incr = 1;
	private String value;
	private String firstChild;
	// TODO
	private TreeSet<NLPNode> children;

	public NLPNode(NLPNode parent, String name, String firstChild) {
		this.setParent(parent);
		this.setName(name);
		this.setFirstChild(firstChild);
	}

	public NLPNode getParent() {
		return parent;
	}

	public void setParent(NLPNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueName() {
		return name + incr;
	}

	public void incr() {
		this.incr++;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		if(parent == null)
			return this.getUniqueName()+"<-null";
		return this.getUniqueName()+"("+value+")<-"+parent.getUniqueName();
	}

	public int compareTo(NLPNode o) {
		return this.getUniqueName().compareTo(o.getUniqueName());
	}

	public String getFirstChild() {
		return firstChild;
	}

	public void setFirstChild(String firstChild) {
		this.firstChild = firstChild;
	}

}
