import java.io.InputStream;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

/**
 * This should technically be called combinatorial game theory, but game theory sounds nicer.
 * 
 * Normal play Nim (or more precisely the system of nimbers) is fundamental to the Sprague–Grundy theorem,
 * which essentially says that in normal play every impartial game is equivalent to a Nim heap that yields
 * the same outcome when played in parallel with other normal play impartial games.
 * 
 * In other words: in order to determine the winning move in any impartial game, you have to:
 *  - Determine the Sprague-Grundy function of each position x: g(x) = mex{g(y) : y element of F(x)}. In other 
 *    words: g(x) is the smallest non-negative value not found among the SG values of the followers of x, 
 *    followers meaning all the states that can be reached from x. Terminal states are labelled zero.
 *     
 *  - If the SG value of a position is zero it is a P position, meaning the previous player will win given this
 *    position. If it is nonzero it is a N position, and the current player will win. The fact that the SG value
 *    is larger than zero also means that you can always move to P from N, and only to N from P. If the game has
 *    only one heap it is now solved. Otherwise:
 *    
 *  - According to the Sprague-Grundy Theorem: If the game has multiple heaps (each heap can be considered a
 *    separate game), the games can be combined by taking a disjunctive sum or nim-sum, meaning the XOR of their
 *    SG values.
 *     
 *  - The winning move is to move so that the disjunctive sum is equal to zero. There is always such a move,
 *    unless the disjunctive sum is already zero. In that case any move will make the disjunctive sum nonzero
 *    and is therefore a losing move.
 *    
 *  - In short: a position is a winning one if the disjunctive sum is nonzero,
 *    and a losing one if the disjunctive sum is zero.
 *  
 * Excellent references:
 * Theory of Impartial Games, SP.268 - The Mathematics of Toys and Games, February 3, 2009, http://web.mit.edu/sp.268/www/nim.pdf
 * Game Theory, Thomas S. Ferguson, http://www.math.ucla.edu/~tom/Game_Theory/comb.pdf
 */

public class GameTheory {
	/***********************************************************************************************************************************************************
	 * Big Cat and Little Cat love playing games. Today, they decide to play a Game of Stones, the Kitties are Coming
	 * edition. The game's rules are as follows:
	 * 
	 *  - The game starts with N stones that are randomly divided into K piles.
	 *  - The cats move in alternating turns, and Little Cat always moves first.
	 *  - During a move, a cat picks a pile having a number of stones >=2 and splits it into any number of non-empty
	 *    piles in the inclusive range from 2 to K.
	 *  - The first cat to be unable to make a move (e.g., because all piles contain exactly 1 stone) loses the game.
	 *  
	 *  Little Cat is curious about the number of ways in which the stones can be initially arranged so that she is
	 *  guaranteed to win. Two arrangements of stone piles are considered to be different if they contain different
	 *  sequences of values. For example, arrangements (2,1,2) and (2,2,1) are different.
	 *  
	 *  Given the values for N, M, and K, find the number of winning configurations for Little Cat and print it modulo
	 *  10^9 + 7.
	 *  
	 *  Note: Each cat always moves optimally, meaning that they're both playing to win and neither cat will make a move
	 *  that causes them to lose the game if some other (winning) sequence of moves can be made.
	 *  
	 *  https://www.hackerrank.com/contests/w20/challenges/simple-game
	 *  
	 ***********************************************************************************************************************************************************
	 * This game is similar to Grundy's game with a few exceptions.
	 * 
	 */
	public static class SimpleGame {
		/**
		 * Return the Sprague-Grundy function of a given stack size.
		 */
		
		public static int[] sgf(int N, int K) {
			int[] sg = new int[N+1];
			BitSet[][] followers = new BitSet[K+1][N+1];
			
			for (int n = 1; n <= N; n++) {
				for (int k = 1; k <= K; k++) {
					followers[k][n] = new BitSet();
				}
			}
			
			for (int k = 1; k <= K; k++) {
				// terminal state
				followers[k][1].set(0);
			}

			for (int n = 2; n <= N; n++) {
				BitSet bigset = new BitSet();

				for (int k = 2; k <= K; k++) {
					if (k == 1) {
						followers[1][n].set(0);
					} else {
						for (int newPile = 1; newPile < n; newPile++) {
							BitSet fset = followers[k-1][n-newPile];
							for (int j = fset.nextSetBit(0); j >= 0; j = fset.nextSetBit(j+1)) {
								followers[k][n].set(j ^ sg[newPile]);
							}
						}
					}
					bigset.or(followers[k][n]);
				}
				int mex = bigset.nextClearBit(0);
				sg[n] = mex;
				followers[1][n].set(sg[n]);
			}
			return sg;
		}

		public static long countWays(int M, int N, int K) {
			long mod = 1000000007;
			int lowK = K >= 4 ? 4 : K;
			int[] sgf = sgf(N, lowK);
			long t1 = System.currentTimeMillis();
						
			int len3 = 1 << (32 - Integer.numberOfLeadingZeros(N - 1) );
			long[][] F = new long[N+1][len3];
			
			for (int n = 1; n <= N; n++) {
				F[n][sgf[n]] = 1;
			}
			
			for (int m = 2; m <= M; m++) {
				long[][] F_new = new long[N+1][len3];
				for (int n = 1; n <= N; n++) {
					for (int i = 1; i < n; i++) {
						int sgfi = sgf[n-i];
						for (int x = 0; x < len3; x++) {
							F_new[n][x] += F[i][sgfi ^ x];
						}
					}
				}
				for (int n = 1; n <= N; n++) {
					for (int x = 0; x < len3; x++) {
						F_new[n][x] = F_new[n][x] % mod;
					}
				}
				F = F_new;
			}
			
			long ret = 0;
			for (int i = 1; i < F[0].length; i++) {
				ret = (ret + F[N][i]) % mod;
			}
			System.out.println((System.currentTimeMillis()- t1));
			return ret;
		}
	}
	
	/***********************************************************************************************************************************************************
	 *  Alice and Bob play the following game:
	 *
	 *	They choose a permutation of the first  numbers to begin with.
	 *	They play alternately and Alice plays first.
	 *	In a turn, they can remove any one remaining number from the permutation.
	 *	The game ends when the remaining numbers form an increasing sequence. The person who played the last turn
	 *  (after which the sequence becomes increasing) wins the game.
	 *  
	 *	Assuming both play optimally, who wins the game?
	 *
	 *	https://www.hackerrank.com/challenges/permutation-game
	 *
	 ***********************************************************************************************************************************************************
	 *
	 *	Here I first explored all reachable states starting from the top, marked all terminal states as P states,
	 *	and then worked my way back up to mark the rest. Conceptually quite simple but the bitmask implementation
	 *	is difficult to read and understand and I initially made some mistakes in the implementation.
	 *	
	 *	There was only one stack in the game so calculating the SG function wasn't necessary. This game could be
	 *	extended to include N stacks.   
	 */
	
	public static void permutationGame(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int[] state = new int[(1 << n)];
			state[0] = 1;
			
			for (int i = 0; i < state.length; i++) {
				if (state[i] == 1) { // reachable
					int prev = -1;
					boolean sorted = true;
					for (int j = 0; j < n; j++) {
						if (!getBit(i, j)) {
							if (a[j] > prev) {
								prev = a[j];
							} else {
								sorted = false;
								break;
							}
						}
					}
					if (sorted) {
						state[i] = 2; // terminal
					} else {
						for (int j = 0; j < n; j++) {
							if (state[setBit(i, j)] == 0) {
								state[setBit(i, j)] = 1;
							}
						}
					}
				}
			}
			
			int[] sg = new int[(1 << n)];
			for (int i = state.length-1; i >= 0; i--) {
				if (state[i] == 2) {
					sg[i] = 2; // P
				} else if (state[i] == 1) {
					int numP = 0;
					for (int j = 0; j < n; j++) {
						if (sg[setBit(i, j)] == 2) {
							numP++;
						}
					}
					if (numP > 0) {
						sg[i] = 1;
					} else {
						sg[i] = 2; // P
					}
				}
			}
			
			if (sg[0] == 2) {
				System.out.println("Bob");
			} else if (sg[0] == 1)  {
				System.out.println("Alice");
			}
		}
	}
	
	public static int clearBit(int x, int i) {
		return (x & ~(1 << i));
	}
	
	public static int setBit(int x, int i) {
		return (x | (1 << i));
	}
	
	public static boolean getBit(int x, int i) {
		return (x & (1 << i)) != 0;
	}
	
	/***********************************************************************************************************************************************************
	 *  Dexter and Debra are playing a game. They have N containers each having one or more chocolates. Containers are
	 *	numbered from 1 to N, where the ith container has A[i] number of chocolates.
	 *
	 *	The game goes like this. First player will choose a container and take one or more chocolates from it. 
	 *	Then, second player will choose a non-empty container and take one or more chocolates from it. And then they
	 *	alternate turns. This process will continue, until one of the players is not able to take any chocolates
	 *	(because no chocolates are left). One who is not able to take any chocolates loses the game. Note that player
	 *	can choose only non-empty container.
	 *
	 *	The game between Dexter and Debra has just started, and Dexter has got the first Chance. He wants to know the
	 *	number of ways to make a first move such that under optimal play, the first player always wins.
	 *
	 ***********************************************************************************************************************************************************
	 *
	 *	This game is just Nim with N stacks. For N >= 3 the number of ways to win on the first move is the number of
	 *	stacks that have a bit set in the position of the highest set bit in the nim-sum of all the stacks, because
	 *	only these stacks can influence the aforementioned highest bit.  
	 */

	public static long chocolateInBox(int n, long[] a) {
		long ret = 0;
		if (n == 1) {
			return 1;
		} else if (n == 2) {
			if (a[0] == a[1]) {
				return 0;
			} else {
				return 1;
			}
		} else {
			long x = 0;
			for (int i = 0; i < a.length; i++) {
				x ^= a[i];
			}
			
			if (x == 0) {
				return 0;
			} else {
				int idx = 0;
				for (int i = 63; i  >= 0; i--) {
					if (getBit(x, i)) {
						idx = i;
						break;
					}
				}
				
				ret = 0;
				for (int i = 0; i < a.length; i++) {
					if (getBit(a[i], idx)) {
						ret++;
					}
				}
				
				return ret;
			}
		}
	}
	
	public static boolean getBit(long x, int i) {
		return (x & (1 << i)) != 0;
	}
	
	public static void main(String[] args) {
		System.out.println("countWays: " + SimpleGame.countWays(3, 4, 3));
		System.out.println("countWays: " + SimpleGame.countWays(10,600, 600));
		System.out.println("chocolateInBox: " + GameTheory.chocolateInBox(4, new long[]{1,2,3,4,5}));
	}
}
