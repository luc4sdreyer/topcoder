public class PlatypusDuckAndBeaver {

	public int minimumAnimals(int webbedFeet, int duckBills, int beaverTails) {
		for (int i = 0; i <= 500; i++) {
			for (int j = 0; j <= 250; j++) {
				for (int k = 0; k <= 250; k++) {
					if (webbedFeet == i*2 + j*4 + k*4 && duckBills == i+k && beaverTails == j+k) {
						return i+j+k;
					}
				}
			}
		}
		return 0;
	}

}
