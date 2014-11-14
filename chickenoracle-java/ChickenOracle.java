public class ChickenOracle {

	public String theTruth(int n, int eggCount, int lieCount, int liarCount) {
		int valid = -1;
		int numValid = 0;
		for (int i = 0; i < 2; i++) {
			int target = i == 0 ? eggCount : n - eggCount;
			int error = n - target;
			for (int j = 0; j <= lieCount && j <= liarCount; j++) {
				if (lieCount + liarCount - j*2 == error && target >= j) {
					numValid++;
					valid = i;
					break;
				}
			}
		}
		if (numValid == 1) {
			if (valid == 0) {
				return "The egg";
			} else {
				return "The chicken";
			}
		} else if (numValid == 2) {
			return "Ambiguous";
		} else {
			return "The oracle is a lie"; 
		}
	}

}
