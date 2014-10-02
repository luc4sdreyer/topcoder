import java.util.ArrayList; 
import java.util.Stack; 

public class BracketExpressions { 

	public String brackets = "{}[]()"; 
	public String ifPossible(String expression) { 
		ArrayList<Integer> posX = new ArrayList<>(); 

		for (int i = 0; i < expression.length(); i++) { 
			if (expression.charAt(i) == 'X') { 
				posX.add(i); 
			} 
		}
		
		int[] posBrackets = new int[posX.size()];
		
		do {
			char[] str = expression.toCharArray();			
			for (int i = 0; i < posX.size(); i++) {
				str[posX.get(i)] = brackets.charAt(posBrackets[i]);
			}
			if (check(str)) { 
				return "possible"; 
			}
		} while (next_number(posBrackets, posX.size()));

		return "impossible"; 

	}
	
	public static boolean next_number(int list[], int base) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}

	public boolean check(char[] expression) { 
		Stack<Character> s = new Stack<>(); 
		for (int i = 0; i < expression.length; i++) { 
			if (expression[i] == '[' || expression[i] == '{' || expression[i] == '(') { 
				s.push(expression[i]); 
			} else { 
				if (s.isEmpty()) { 
					return false; 
				} 
				char close = s.pop(); 
				switch (close) { 
				case '[': { 
					if (expression[i] != ']') { 
						return false; 
					} 
					break; 
				} 
				case '{': { 
					if (expression[i] != '}') { 
						return false; 
					} 
					break; 
				} 
				case '(': { 
					if (expression[i] != ')') { 
						return false; 
					} 
					break; 
				} 
				} 
			} 
		} 
		if (s.isEmpty()) 
			return true; 

		return false; 
	} 

}