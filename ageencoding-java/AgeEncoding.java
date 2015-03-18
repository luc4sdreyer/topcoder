import java.math.BigDecimal;

public class AgeEncoding {
	
	public static final double eps = 1e-9;
	public double getRadix(int age, String candlesLine) {
		char[] candles = candlesLine.toCharArray();
		double low = 0.0001;
		double high = 200;
		double a = age;
		if (getAge(candles, 1.0) == getAge(candles, 2.0)) {
			double newAge = getAge(candles, 1.0);
			if (newAge == age) {
				return -2;
			} else {
				return -1;
			}
		}
		double mid = 0;
		double best = 0;
		for (int i = 0; i < 1000; i++) {
			mid = (low + high)/2;
			double newAge = getAge(candles, mid);
			if (Math.abs(a - newAge) < eps) {
				best = mid;
			} else if (newAge > a) {
				high = mid;
			} else {
				low = mid;
			}
		}
		if (best == 0.0) {
			return -1;
		}
		return best;
	}
	
	public double getAge(char[] candles, double base) {
//		double ret = 0;
//		for (int i = 0; i < candles.length; i++) {
//			ret += (candles[candles.length - i -1]-'0') * Math.pow(base, i);
//		}
//		return ret;
		BigDecimal ret = BigDecimal.ZERO;
		for (int i = 0; i < candles.length; i++) {
			ret = ret.add(BigDecimal.valueOf(candles[candles.length - i -1]-'0').multiply(BigDecimal.valueOf(base).pow(i)));
		}
		return ret.doubleValue();
	}

}
