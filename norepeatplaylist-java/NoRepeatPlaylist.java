public class NoRepeatPlaylist {

	public int numPlaylists(int N, int M, int P) {
		long m = 0;
		long x = 1;
		for (long p = 0; p < P; p++) {
			x = (x * (N - m)) % 1000000007;
			if (m < M) {
				m++;
			}
		}
		
		x = negMod((int) (x - N * numPlaylistsSub(N - 1, M , P)), 1000000007);
		return (int)x;
	}
	
	public int numPlaylistsSub(int N, int M, int P) {
		if (N == 1) {
			return 1;
		}
		long m = 0;
		long x = 1;
		for (long p = 0; p < P; p++) {
			x = (x * (N - m)) % 1000000007;
			if (m < M) {
				m++;
			}
		}
		return (int)x;
	}
	
	public static int negMod(int x, int mod) {
		if (x >= 0) {
			return x%mod;
		} else {
			return (mod+(x%mod))%mod;
		}
	}

}
