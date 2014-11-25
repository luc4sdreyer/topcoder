import java.util.Stack;

public class Suminator {

	public int findMissing(int[] program, int w) {
		long wantedResult = w;
		int idx = 0;
		for (int i = 0; i < program.length; i++) {
			if (program[i] == -1) {
				idx = i;
				break;
			}
		}
		
		long x0 = eval(program, idx, 0);
		if (x0 == wantedResult) {
			return 0;
		}
		long x1 = eval(program, idx, 1);
		if (x1 == wantedResult) {
			return 1;
		}
		
		if (x1 == x0) {
			return -1;
		}
		
		for (int i = 0; i < 10; i++) {
			if (wantedResult == eval(program, idx, i)) {
				return i;
			}			
		}
		
		long ret = wantedResult - x0;
		if (ret < 0) {
			return -1;
		}	
		return (int) ret;
	}
	
	public long eval(int[] p, int idx, int value) {
		Stack<Long> s = new Stack<>();
		int[] program = p.clone();
		program[idx] = value;
		for (int i = 0; i < program.length; i++) {
			if (program[i] == 0) {
				long a = 0;
				long b = 0;
				if (!s.isEmpty()) {
					a = s.pop();
				}
				if (!s.isEmpty()) {
					b = s.pop();
				}
				s.push(a+b);
			} else {
				s.push((long) program[i]);
			}
		}
		long ret = 0;
		if (!s.isEmpty()) {
			ret = ((long)s.pop());
		}
		return ret;
	}

}
