import java.math.BigInteger;
import java.util.HashMap;

public class NetworkXOneTimePad {

	public int crack(String[] plaintexts, String[] ciphertexts) {
		HashMap<Long, Integer> keys = new HashMap<>();
		for (int i = 0; i < plaintexts.length; i++) {
			for (int j = 0; j < ciphertexts.length; j++) {
				long key = Long.parseLong(plaintexts[i], 2) ^ Long.parseLong(ciphertexts[j], 2);
				if (!keys.containsKey(key)) {
					keys.put(key, 0);
				}
				keys.put(key, keys.get(key) + 1);
			}
		}
		int max = 0;
		for (Long key : keys.keySet()) {
			max = Math.max(max, keys.get(key));
		}

		int c = 0;
		for (Long key : keys.keySet()) {
			if (keys.get(key) == max) {
				c++;
			}
		}
		return c;
	}
	
	public int crack2(String[] plaintexts, String[] ciphertexts) {
		HashMap<BigInteger, Integer> keys = new HashMap<>();
		for (int i = 0; i < plaintexts.length; i++) {
			for (int j = 0; j < ciphertexts.length; j++) {
				BigInteger key = (new BigInteger(plaintexts[i])).xor(new BigInteger(ciphertexts[j]));
				if (!keys.containsKey(key)) {
					keys.put(key, 0);
				}
				keys.put(key, keys.get(key) + 1);
				
			}
		}
		int max = 0;
		for (BigInteger key : keys.keySet()) {
			max = Math.max(max, keys.get(key));
		}

		int c = 0;
		for (BigInteger key : keys.keySet()) {
			if (keys.get(key) == max) {
				c++;
			}
		}
		return c;
	}

}
