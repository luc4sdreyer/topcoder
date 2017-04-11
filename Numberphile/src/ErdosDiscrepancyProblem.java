import java.util.Arrays;


public class ErdosDiscrepancyProblem {

	public static void main(String[] args) {
		int count = 0;
		for (int size = 1; size < 32; size++) {
			System.out.println(size);
			for (int i = 0; i < (1 << size); i++) {
				boolean[] cmd = new boolean[size];
				for (int j = 0; j < cmd.length; j++) {
					if (((1 << j) & i) != 0) {
						cmd[j] = true;
					}
				}
	//			boolean[] tmp = {true, false, false, true, true, false, false, true, true, false, false, true};
	//			cmd = tmp;
				count++;
				boolean valid = true;
				for (int step = 1; step <= cmd.length && valid; step++) {
					int position = 0;
					for (int j = step-1; j < cmd.length; j += step) {
						if (cmd[j]) {
							position++;
						} else {
							position--;
						}
						if (Math.abs(position) >= 2) {
							valid = false;
							break;
						}
					}
					if (Math.abs(position) >= 2) {
						valid = false;
					}
				}
				if (valid) {
					System.out.println(Arrays.toString(cmd));
				}
			}
		}
	}

}
