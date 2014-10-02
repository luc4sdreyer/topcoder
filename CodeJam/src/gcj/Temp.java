package gcj;

import java.awt.RadialGradientPaint;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.TreeSet;

public class Temp {
	public static void main(String[] args) {

		int numTests = 20;
		long t = System.currentTimeMillis();
		for (int i = 0; i < numTests; i++) {
			//System.out.println(i + " " + util.Math.eulerTotientFunction(i));
			//util.eulerTotientFunction(1000000000 + (int)(Math.random()*100000));
			//util.gcd(1000000000 + (int)(Math.random()*100000), 1000000000 + (int)(Math.random()*100000)); 
		}
		System.out.println((System.currentTimeMillis() - t)/(float)numTests*1000 + " us");
		
		Node[] nodes = new Node[10];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i, nodes.length - i - 1);
		}
		Arrays.sort(nodes);
		System.out.println(Arrays.toString(nodes));
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		TreeSet<Integer> treeSet = new TreeSet<Integer>();
		treeSet.add(-3);
		treeSet.add(10);
		treeSet.add(3);
		treeSet.add(1);
		treeSet.add(-5);
		treeSet.add(3);
		treeSet.add(3);
		treeSet.add(8);
		treeSet.add(8);
		
		System.out.println(Elvis.INSTANCE.whereIsElvis());
		
		new RaggedArray();
		
		HashMap<String, Integer> set = new HashMap<>();
		
		varargsTest();
		
		CloneTest c1 = new CloneTest(3);
		System.out.println(c1);
		CloneTest c2 = c1.clone();
		System.out.println(c2);
	}
	
	public static void varargsTest() {
		varargs(-1);
		varargs(0);
		varargs(0, 1);
		varargs(0, 1, 2);
	}
	
	public static void varargs(int a, int... params) {
		System.out.println(a + " +  " + Arrays.toString(params));
	}
	
	public static class Node implements Comparable<Node> {
		public int index;
		public int value;
		public Node(int index, int value) {
			this.index = index;
			this.value = value;
		}
		public int compareTo(Node n) {
			if (this.value < n.value) return -1;
			if (this.value > n.value) return 1;
			return 0;
		}
		public String toString() {
			return "[idx: " + this.index + ", v: " + this.value + "]";
		}
	}
	
	public static enum Elvis {
		INSTANCE;
		public String whereIsElvis() {
			return "He has left the building.";
		}
	}
	
	public static class RaggedArray {
		private int[][] array;
		public RaggedArray() {
			int n = 10;
			this.array = new int[n][];
			for (int i = 0; i < n; i++) {
				this.array[i] = new int[i];
			}
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[i].length; j++) {
					System.out.print('.');
				}
				System.out.println();
			}
		}
	}
	
	public static class CloneTest implements ReallyCloneable<CloneTest> {
		int a;
		public CloneTest(int a) {
			this.a = a;
		}
		public String toString() {
			return "[" + this.a  + "]";
		}
		@Override
		public CloneTest clone() {
			return new CloneTest(this.a);
		}
	}
	
	public interface ReallyCloneable<T> {
		public T clone();
	}
	
	public interface Extended<T> extends ReallyCloneable<T> {
		public void extended();
	}
}
