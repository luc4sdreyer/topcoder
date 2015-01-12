public class LateProfessor {

	public double getProbability(int waitTime, int walkTime, int lateTime, int bestArrival, int worstArrival) {
		if (lateTime > walkTime) {
			return 0;
		}
		double ww = walkTime + waitTime;
		
		double t1 = bestArrival % ww;
		double p1, p2, p3;
		if (t1 < waitTime) {
			p1 = (walkTime - lateTime)/(ww - t1);					
		} else if (t1 < ww - lateTime) {
			p1 = (walkTime - lateTime - (t1 - waitTime)) / (ww-t1);
		} else {
			p1 = 0;
		}
		
		double t3 = worstArrival % ww;
		if (t3 < waitTime) {
			p3 = 0;
		} else if (t3 < ww - lateTime) {
			p3 = (t3 - waitTime) / t3;
		} else {
			p3 = (walkTime - lateTime) / t3;
		}
		
		double totalTime = worstArrival - bestArrival;
		
		double t2 = totalTime - t3 - (ww - t1);
		
		p2 = (walkTime - lateTime)/ww;
		
		int n = (int) (t2/ww);
		
		double pT;
		if (n > 0) {
			pT = (p1 + n*p2 + p3) / (n+2.0);
		} else {
			pT = p1+p3;
		}		
		
		return pT;
	}

}
