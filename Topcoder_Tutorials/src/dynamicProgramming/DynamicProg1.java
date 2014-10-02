package dynamicProgramming;


import java.util.HashSet;


public class DynamicProg1 {
	public static void main(String[] args) {
		int[] coins = {1,3,5,7};
		int sum = 100;
		int[] min = new int[sum+1];
		int[] backTrackCoin = new int[sum+1];
		int[] backTrackSolution = new int[sum+1];
		
		for (int i = 0; i <= sum; i++) {
			min[i] = Integer.MAX_VALUE;
		}
		min[0] = 0;
		
		//
		// This is all there is to it! Solution in N*S time, where N = number of coins
		// and S = the final sum. Opposed to finding a bunch of combinations taking 
		// about n!
		//
		for (int s = 1; s <= sum; s++) {
			for (int j = 0; j < coins.length; j++) {
				if (coins[j] <= s && min[s-coins[j]]+1 < min[s]) {
					min[s] = min[s-coins[j]]+1;
					backTrackCoin[s] = coins[j];
					backTrackSolution[s] = s-coins[j];
				}
			}
		}
		
		//
		// Fancy output
		//
		HashSet<Integer> coinSet = new HashSet<Integer>(); 
		coinSet.add(0);
		for (int j = 0; j < coins.length; j++) {
			coinSet.add(coins[j]);
		}
		System.out.println("sum \tmin \tcValue \tpath");
		for (int i = 0; i <= sum; i++) {
			String path = "";
			if (i > 0) {
				path += backTrackCoin[i];
				
				int s = backTrackSolution[i];
				while (!coinSet.contains(s)) {
					path += " + " + backTrackCoin[s];
					s = backTrackSolution[s];
				}
				if (s != 0) {
					path += " + " + backTrackCoin[s];
				}
			}
			System.out.println(i + "\t" + min[i] + "\t" + backTrackCoin[i] + "\t" + path);
			
		}
		
	}

}
