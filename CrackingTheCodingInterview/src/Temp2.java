public class Temp2 {
	public enum Elvis {
		something;
		public String getName() {
			return "Elvis";
		}
	}
	
	public static void main(String[] args) {
		Temp2.Elvis e = Temp2.Elvis.something;
		System.out.println(e.getName());
	}
}
