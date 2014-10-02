package gcj;

import java.io.*;
import java.util.*;
import java.math.*;

public class ProblemA {
	void doMain() throws Exception {
		Scanner sc = new Scanner(new FileReader("D:\\gcj\\2008\\1B\\A-small-practice.in"));
		PrintWriter pw = new PrintWriter(new FileWriter("D:\\gcj\\2008\\1B\\A-small-practice.out"));
		int caseCnt = sc.nextInt();
		for (int caseNum=1; caseNum <= caseCnt; caseNum++) {
			pw.print("Case #" + caseNum + ": ");
			long n = sc.nextLong();
			long A = sc.nextLong();
			long B = sc.nextLong();
			long C = sc.nextLong();
			long D = sc.nextLong();
			long x0 = sc.nextLong();
			long y0 = sc.nextLong();
			long M = sc.nextLong();
			long[][] cnt = new long[3][3];
			int tot = 0;
			long[] x = new long[1000], y = new long[1000];
			Set<String> points = new HashSet<String>();
			long X = x0, Y = y0;
			for (int i=0; i < n; i++) {
				if (!points.contains(X+" "+Y)) {
					points.add(X+" "+Y);
					cnt[(int)(X%3)][(int)(Y%3)]++;
					x[tot] = X;
					y[tot++] = Y;
					X = (A * X + B) % M;
					Y = (C * Y + D) % M;
				}
			}
			long res = 0;
			for (int i=0; i < tot; i++)
				for (int j=i+1; j < tot; j++)
					for (int k=j+1; k < tot; k++)
						if ((x[i]+x[j]+x[k])%3==0 && (y[i]+y[j]+y[k])%3==0)
							res++;
			pw.println(res);
		}
		pw.flush();
		pw.close();
		sc.close();
	}
	
	public static void main(String[] args) throws Exception {
		(new ProblemA()).doMain();
	}
}
