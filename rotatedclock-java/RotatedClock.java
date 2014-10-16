public class RotatedClock {

	public String getEarliest(int hourHand, int minuteHand) {
		int min = Integer.MAX_VALUE;
		for (int rot = 0; rot < 360; rot+=30) {
			int hHand = (rot + hourHand) % 360;
			int mHand = (rot + minuteHand) % 360;
			int minute = (hHand % 30) * 2;
			if (mHand - minute*6 == 0) {
				min = Math.min(min, hHand * 2);
			}
		}
		if (min == Integer.MAX_VALUE) {
			return "";
		} else {
			String shour = (min/60) + "";
			if (shour.length() < 2) {
				shour = "0" + shour;
			}
			String smin = (min%60) + "";
			if (smin.length() < 2) {
				smin = "0" + smin;
			}
			return shour + ":" + smin;
		}
	}

}
