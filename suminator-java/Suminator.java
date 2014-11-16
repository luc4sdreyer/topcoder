import java.util.Stack;

public class Suminator {

	public int findMissing(int[] program, int w) {
		long wantedResult = w;
		int varIdx = 0;
		for (int i = 0; i < program.length; i++) {
			if (program[i] == -1) {
				varIdx = i;
				break;
			}
		}
		if (displayResult(program, varIdx, 0) == wantedResult) {
			return 0;
		}
		if (displayResult(program, varIdx, 1) == wantedResult) {
			return 1;
		}
		if (displayResult(program, varIdx, 1) == displayResult(program, varIdx, 2)) {
			return -1;
		} else {
			long u = displayResult(program, varIdx, 1);
			long a = wantedResult - u;
			if (1+a < 1) {
				return -1;
			}
			if (wantedResult != displayResult(program, varIdx, 1+a)) {
				return -1;
			}
			return (int) (1+a);
		}
	}
	
	public long displayResult(int[] p, int varIdx, long value) {
		long[] program = new long[p.length];
		for (int i = 0; i < program.length; i++) {
			program[i] = p[i];
		}
		program[varIdx] = value;
		Stack<Long> s = new Stack<>();
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
				s.push(a + b);
			} else {
				s.push((long) program[i]);
			}
		}
		long a = 0;
		if (!s.isEmpty()) {
			a = s.pop();
		}
		return a;
	}

}
