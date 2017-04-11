package dataStructures;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import dataStructures.YAST.BinaryOperation;
import dataStructures.YAST.YASTI;

public class YASTTest {
	
	public static class Adder implements BinaryOperation<Adder> {
		public long value;
		
		public Adder(long value) {
			this.value = value;
		}
		
		public static Adder[] fromArray(long[] a) {
			Adder[] b = new Adder[a.length];
			for (int i = 0; i < b.length; i++) {
				b[i] = new Adder(a[i]);
			}
			return b;
		}
		
		@Override
		public Adder function(Adder e1, Adder e2) {
			return new Adder(e1.value + e2.value);
		}
		
		public String toString() {
			return Long.toString(value);
		}
	}

	@Test
	public void testTrees() {
		int maxLength = 100;
		int maxValue = 1000;
		int numTests = 1000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			long[] a = new long[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue);
			}
			
			//fromArray(a)
			YASTI<Adder> st = new YASTI<>(Adder.fromArray(a));
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				long expected = 0;
				for (int k = low; k <= high; k++) {
					expected = st.tree[1].getValue().function(new Adder(a[k]), new Adder(expected)).value;
				}
				System.out.println(i);
				
				long actual = st.getValue(low, high).value;
				
				if (actual != expected) {
					st.getValue(low, high);
				}
				assertEquals(expected, actual);
				
				int change = rand.nextInt(maxValue);
				a[low] = change;
				st.update(low, new Adder(change));
			}
		}
	}

}
