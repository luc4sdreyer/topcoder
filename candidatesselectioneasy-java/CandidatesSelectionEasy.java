import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CandidatesSelectionEasy {


	private class Sc implements Comparable<Sc> {
		public Character c;
		public int i;
		
		public Sc (char c, int i) {
			this.c = c;
			this.i = i;
		}
		
		public int compareTo(Sc o) {
	        return this.c.compareTo(o.c);
	    }
		
	}

	public int[] sort2(String[] score, int x) {
		ArrayList<Sc> s = new ArrayList<Sc>();
		for (int i = 0; i < score.length; i++) {
			s.add(new Sc(score[i].charAt(x), i));
		}
		Collections.sort(s);
		
		int[] idx = new int[score.length];
		for (int i = 0; i < idx.length; i++) {
			idx[i] = s.get(i).i;
		}
		return idx;
	}
	


public class Pair<A extends Comparable<A>, B extends Comparable<B>>	implements Comparable<Pair<A, B>> {
    private A first;
    private B second;

    public Pair(A first, B second) {
    	super();
    	this.first = first;
    	this.second = second;
    }

    public int hashCode() {
    	int hashFirst = first != null ? first.hashCode() : 0;
    	int hashSecond = second != null ? second.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		@SuppressWarnings("unchecked")
			Pair<A, B> otherPair = (Pair<A, B>) other;
    		return 
    		((  this.first == otherPair.first ||
    			( this.first != null && otherPair.first != null &&
    			  this.first.equals(otherPair.first))) &&
    		 (	this.second == otherPair.second ||
    			( this.second != null && otherPair.second != null &&
    			  this.second.equals(otherPair.second))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }

    public A getFirst() {
    	return first;
    }

    public void setFirst(A first) {
    	this.first = first;
    }

    public B getSecond() {
    	return second;
    }

    public void setSecond(B second) {
    	this.second = second;
    }

	@Override
	public int compareTo(Pair<A, B> o) {
		int compareFirst = this.first.compareTo(o.first);
		if (compareFirst != 0) {
			return compareFirst;
		} else {
			return this.second.compareTo(o.second);
		}
	}
}

	public int[] sort(String[] score, int x) {
		ArrayList<Pair<Character, Integer>> s = new ArrayList<Pair<Character, Integer>>();
		for (int i = 0; i < score.length; i++) {
			s.add(new Pair<Character, Integer>(score[i].charAt(x), i));
		}
		Collections.sort(s);
		
		int[] idx = new int[score.length];
		for (int i = 0; i < idx.length; i++) {
			idx[i] = s.get(i).second;
		}
		
		System.out.println(Arrays.toString(idx));
		
		return idx;
	}

}