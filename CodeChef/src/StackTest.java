
public class StackTest {
	public static void main(String[] args) {
		dfs(0);
		System.out.println("done!");
	}
	
	public static void dfs(int a) {
		//if (a < 18968) { // 128 MB
		if (a < (1 << 21)) {
			dfs(a+1);
		}
	}
}
