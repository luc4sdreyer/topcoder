package gcj;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;


public class util2 {

	//-10^9 < Integer.MAX_VALUE (2^31) < 10^10
	//-10^18 < Long.MAX_VALUE (2^63) < 10^20

	public static void main(String[] args) {
		//System.out.println(combination(52000, 5));
		/*
		int[] freq = {0,1,	0,	2,	1,	1,	3,	0,	4,	2,	5,	2,	2,	3,	1,	0,	2};
		binaryIndexedTree = new int[freq.length];
		for (int i = 0; i < freq.length; i++) {
			bitUpdate(i, freq[i]);
		}
		for (int i = 0; i < freq.length; i++) {
			System.out.println(i+"\t"+freq[i]+"\t"+bitReadSingle(i)+"\t"+bitRead(i)+"\t"+binaryIndexedTree[i]);
		}
*/
	}


	/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////  Frequency Counter  ////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	
	public static void fcount() {
		String[] data = {"hello", "sdf", "sdf", "fg", "asas", "tgrrgt", "fg", "sdf"};
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		for (int i = 0; i < data.length; i++) {
			map.put(data[i], map.get(data[i]) == null ? 1 : map.get(data[i])+1);
		}
		for (String string : map.keySet()) {
			System.out.println(string+":"+map.get(string));
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////  Map sorting  ////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	static Map sortByValue(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
				.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	} 

	static Map sortByKey(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getKey())
				.compareTo(((Map.Entry) (o2)).getKey());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	} 
}
