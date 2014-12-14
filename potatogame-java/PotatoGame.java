public class PotatoGame {
	public String theWinner(int n) {
		return theWinnerFast(n) ? "Taro" : "Hanako";
	}
	public boolean theWinnerFast(int n) {
		if (n % 5 == 0 || n % 5 == 2) {
			return false;
		} else {
			return true;
		}
	}

}
