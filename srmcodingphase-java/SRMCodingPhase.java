import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SRMCodingPhase {

	public int countScore(int[] points, int[] skills, int luck) {
		int max = 0;
		int[] pMod = {-2, -4, -8};
		for (int luck0= 0; luck0 <= luck; luck0++) {
			if (skills[0] > luck0) {
				int modS0 = skills[0] - luck0;
				for (int luck1 = 0; luck1 <= luck - luck0; luck1++) {
					if (skills[1] > luck1) {
						int modS1 = skills[1] - luck1;
						for (int luck2 = 0; luck2 <= luck - luck0 - luck1; luck2++) {
							if (skills[2] > luck2) {
								int modS2 = skills[2] - luck2;
								int[] modSkills = {modS0, modS1, modS2};
								
								int[] problems = {0, 1, 2};
								while(true) {
									max = Math.max(max, calc(problems, modSkills, pMod, points));
									if (!next_permutation(problems)) {
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return max;
	}

	private int calc(int[] problems, int[] modSkills, int[] pMod, int[] points) {
		int max = 0;
		int time = 75;
		int score = 0;
		for (int j = 0; j < problems.length; j++) {
			int idx = problems[j];
			
			if (modSkills[idx] <= time) {
				score += points[idx] + pMod[idx] * modSkills[idx];
				time -= modSkills[idx];
			} else {
				break;
			}
		}
		return score;
		
	}
	
	public boolean next_permutation(int str[])	{
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}

}
