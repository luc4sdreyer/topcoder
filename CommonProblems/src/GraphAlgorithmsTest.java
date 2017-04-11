import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;

import org.junit.Test;

public class GraphAlgorithmsTest {

	@Test
	public void CutVerticesTest() {
		Random ra = new Random(0);
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		int N = 9;
		for (int i = 0; i < N; i++) {
			graph.add(new ArrayList<Integer>());
		}
		graph.get(0).add(1);	graph.get(1).add(0);
		graph.get(1).add(2);	graph.get(2).add(1);
		graph.get(0).add(2);	graph.get(2).add(0);
		graph.get(3).add(4);	graph.get(4).add(3);
		graph.get(4).add(5);	graph.get(5).add(4);
		graph.get(5).add(6);	graph.get(6).add(5);
		graph.get(5).add(7);	graph.get(7).add(5);
		graph.get(5).add(8);	graph.get(8).add(5);
		graph.get(6).add(7);	graph.get(7).add(6);
		graph.get(6).add(8);	graph.get(8).add(6);
		graph.get(7).add(8);	graph.get(8).add(7);
		
		// Visually:
		// 0--2     4--5--6
		// | /      |  |\/|
		// |/       |  |/\|           
		// 1        3  7--8
		
		GraphAlgorithms.CutVertices cv = new GraphAlgorithms.CutVertices(graph);
		
		// Three biconnected components in the graph
		assertEquals(cv.biConnected[N-1], 2);
	}
	
	@Test
	public void maxBpmTest() {
		int n = 1000;
		Random ra = new Random(0);
		boolean[][] bpGraph = new boolean[n][n];
		for (int i = 0; i < bpGraph.length; i++) {
			for (int j = 0; j < bpGraph.length; j++) {
				if (ra.nextDouble() < 0.001) {
					bpGraph[i][j] = true;
				}
			}
		}

		assertEquals(GraphAlgorithms.maxBPM(bpGraph), 551);
	}

}
