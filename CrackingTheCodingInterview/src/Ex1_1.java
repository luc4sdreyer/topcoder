
public class Ex1_1 {

	public static void main(String args[]) {
		//String t1 = "PklhsdFjzL4NRQgfiHP";
		String t1 = "aAbcdefghijklmnopqrstuvwxyz";
		Ex1_1 ex = new Ex1_1();
		ex.isUnique(t1);
		ex.isUniqueBitwise(t1);
		
	}
	
	public boolean isUnique(String t1) {
		int[] freq = new int[256];
		for (int i = 0; i < t1.length(); i++) {
			int intVal = (int)t1.charAt(i);
			freq[intVal]++;
			if (freq[i] > 1) {
				System.out.println((char)i + " is not unique.");
				return false;
			}
		}
		System.out.println("isUnique(): String is unique.");
		return true; 
	}
	
	public boolean isUniqueBitwise(String t1) {
		long freq = 0;
		for (int i = 0; i < t1.length(); i++) {
			long intVal = (int)t1.charAt(i) - 'A';
			if (((1 << intVal) & freq) != 0) {
				System.out.println(t1.charAt(i) + " is not unique.");
				return false;
			}
			freq = freq | (1L << intVal);
		}
		System.out.println("isUniqueBitwise(): String is unique.");
		return true;
	}

}
