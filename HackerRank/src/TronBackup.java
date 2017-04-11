import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class TronBackup {
	public static final int map_size = 15;
	
	public static void main(String[] args) {
//		BFS.runTest(); // Test BFS
//		util.main(args);
//		Play.main(args);
		playGame(System.in);
	}

	public enum State {Battle, Survival};
	
	public static void playGame(InputStream in) {
		MyScanner scan = new MyScanner(in);
		
		int pos = scan.nextLine().toCharArray()[0] == 'r' ? 0 : 1;
		
		int y1 = scan.nextInt();
		int x1 = scan.nextInt();
		Position[] start = new Position[2];
		start[0] = new Position(x1, y1);
		
		y1 = scan.nextInt();
		x1 = scan.nextInt();
		start[1] = new Position(x1, y1);
		
		byte[][] bm = new byte[15][15];
		for (int i = 0; i < bm.length; i++) {
			char[] c = scan.nextLine().toCharArray();
			for (int j = 0; j < c.length; j++) {
				if (c[j] == '-') {
					bm[i][j] = 0;
				} else if (c[j] == '#' || c[j] == 'r') {
					bm[i][j] = 1;
				} else if (c[j] == 'g') {
					bm[i][j] = 2;
				}
			}
		}
		TronMap map = new TronMap(bm, start[0], start[1]);
		map.outputLevel = -1;
		MegaTron bot = new MegaTron(pos);
		bot.timeLimit = 200;
		
		Position newPos = bot.move(map);
		if (!map.move(newPos, pos)) {
			System.out.println("Game over");
		}
		String[] dir = {"UP", "RIGHT", "DOWN", "LEFT"};
		System.out.println(dir[map.posToDir(start[pos], newPos) -1]);
		
	}
	
	public static class BFS {
		public TronMap map;
		public Position start;
		//private boolean slow;
		public int availableSpace;
		private Queue<Position> q;
		private Queue<PositionV> qv;
		public Position start2;
		public TronMap[] ref;
		public short[][][] smap;

		//BFS will modify map
		public BFS (TronMap map, Position start, boolean slow) {
			this.map = map;
			this.start = start;
			//this.slow = slow;
			this.availableSpace = 0;

			q = new LinkedList<Position>();
			q.add(start);
		}

		//used to step through a list of concurrent BFSs, and terminate the last one when the others finish to save time
		public boolean next() {
			boolean r = false;
			if (q.size() != 0) {
				r = true;
				Position top = q.poll();

				if (!(top.y < map.m.length && top.y >= 0)) return r;
				if (map.m[top.y][top.x] != 0) return r;
				map.m[top.y][top.x] = 5;

				availableSpace++;

				// visit every adjacent node
				ArrayList<Position> moves = map.getAllMoves(top);
				for (int i = 0; i < moves.size(); i++) {
					if (map.canMove(moves.get(i), top)) {
						q.add(moves.get(i));
					}
				}
			}
			return r;
		}
		
		public short[][] distance() {
			return this.distance(null);
		}
		
		//build distance map
		public short[][] distance(Position clear) {
			//int counter  =0 ;
			//int counter2 =0 ;
			qv = new LinkedList<PositionV>();
			qv.add(new PositionV(start, 0));
			short[][] smap = map.getBigCopy();
			TronMap ref = map.getCopy();

			util.fill(smap, Short.MAX_VALUE);

			ref.m[start.y][start.x] = 0;
			
			if (clear != null) {
				ref.m[clear.y][clear.x] = 0;
			}
			
			PositionV top;
			ArrayList<Position> moves;
			while (qv.size() > 0) {
				top = qv.poll();
				//counter2++;

				if (smap[top.y][top.x] <= top.v) continue;
				//			if (smap[top.y][top.x] < Short.MAX_VALUE) {
				//				int x = 1;x++;
				//			}
				smap[top.y][top.x] = (short) top.v;
				ref.m[top.y][top.x] = (short) 1;

				// visit every adjacent node
				moves = new ArrayList<Position>();
				byte bYH = (byte) (ref.m.length-1);		
				byte bYH1 = (byte) (ref.m.length-2);	
				if (top.y != 0) {
					if (ref.m[top.y-1][top.x] == 0) {
						moves.add(new Position(top.x,top.y-1));
					}
				} else {
					for (byte x = 0; x < ref.m[0].length; x++) {
						if (ref.m[1][x] == 0) {
							if (x != top.x) {
								moves.add(new Position(x,1));
							}
						}
					}
				}
				if (top.y != bYH) {
					if (ref.m[top.y+1][top.x] == 0) {
						moves.add(new Position(top.x,top.y+1));
					}

					if (top.y != 0) {
						byte newX = (byte) ((top.x+1)%ref.m[0].length);
						if (ref.m[top.y][newX] == 0) {
							moves.add(new Position(newX,top.y));
						}				
						newX = (byte) util.negMod(top.x-1, ref.m[0].length);			
						if (ref.m[top.y][newX] == 0) {
							moves.add(new Position(newX,top.y));
						}
					}
				} else {
					for (byte x = 0; x < ref.m[0].length; x++) {
						if (ref.m[bYH1][x] == 0) {
							if (x != top.x) {
								moves.add(new Position(x,bYH1));
							}
						}
					}
				}

				int nextV = top.v+1;
				Position nextM;
				for (int i = 0; i < moves.size(); i++) {
					nextM = moves.get(i);
					if (ref.m[nextM.y][nextM.x] == 0) {
						qv.add(new PositionV(nextM,nextV));
					} 
				}
				//counter++;
			}
			//System.out.println(counter2);
			return smap;		
		}
		
		//build 2 distance maps and terminate on the boundary
		//@SuppressWarnings("unchecked")
		public short[][] dualDistance(boolean[] endgame) {		
			final byte bXH0 = (byte) (map.m[0].length);		
			//final byte bXH1 = (byte) (map.m[0].length-1);	
			final byte bYH0 = (byte) (map.m.length);	
			final byte bYH1 = (byte) (map.m.length-1);		
			final byte bYH2 = (byte) (map.m.length-2);	
			//int counter  =0 ;
			//int counter2 =0 ;
			PositionV[][] dualQ = new PositionV[2][5000];
			int[] qf = {0,0};
			int[] qb = {0,0};
			dualQ[0][qb[0]++] = new PositionV(start, 0);
			dualQ[1][qb[1]++] = new PositionV(start2, 0);
			smap = new short[2][map.m.length][map.m[0].length]; 
			ref = new TronMap[2];
			ref[0] = map.getCopy();
			ref[1] = map.getCopy();

			util.fill(smap[0], Short.MAX_VALUE);
			util.fill(smap[1], Short.MAX_VALUE);

			ref[0].m[start.y][start.x] = 0;
			ref[1].m[start2.y][start2.x] = 0;
			PositionV top;
			int topX;
			int topY;
			int topV;
			int nextV;
			//Position[] moves;
			boolean[] reachedYbot = {false,false};
			boolean[] reachedYtop = {false,false};

			//moves = new Position[34];
			//short mIndex;
			while (qf[0] < qb[0] || qf[1] < qb[1]) {
				for (int i = 0; i < 2; i++) {
					if (qf[i] >= qb[i]) {
						continue;
					}
					top = dualQ[i][qf[i]++];
					topX = top.x;
					topY = top.y;
					topV = top.v;

					if (smap[i][topY][topX] <= topV) continue;
					if (smap[(i+1)%2][topY][topX] < topV) continue;
					if (ref[i].m[topY][topX] != 0) continue;

					nextV = top.v+1;
					ref[i].m[topY][topX] = (short) 9;
					smap[i][topY][topX] = (short) topV;
					
					// visit every adjacent node
					if (topY != 0) {
						if (ref[i].m[topY-1][topX] == 0) {
							dualQ[i][qb[i]++] = new PositionV(topX,topY-1,nextV);
						}
					} else if (!reachedYbot[i]) {
						reachedYbot[i] = true;
						for (byte x = 0; x < bXH0; x++) {
							if (ref[i].m[1][x] == 0) {
								if (x != topX) {
									dualQ[i][qb[i]++] = new PositionV(x,1,nextV);
								}
							}
						}
					}
					if (topY != bYH1) {
						if (ref[i].m[topY+1][topX] == 0) {
							dualQ[i][qb[i]++] = new PositionV(topX,topY+1,nextV);
						}

						if (topY != 0) {
							byte newX = (byte) ((topX+1)%bXH0);
							if (ref[i].m[topY][newX] == 0) {
								dualQ[i][qb[i]++] = new PositionV(newX,topY,nextV);
							}				
							newX = (byte) util.negMod(topX-1, bXH0);			
							if (ref[i].m[topY][newX] == 0) {
								dualQ[i][qb[i]++] = new PositionV(newX,topY,nextV);
							}
						}
					} else if (!reachedYtop[i]) {
						reachedYtop[i] = true;
						for (byte x = 0; x < bXH0; x++) {
							if (ref[i].m[bYH2][x] == 0) {
								if (x != topX) {
									dualQ[i][qb[i]++] = new PositionV(x,bYH2,nextV);
								}
							}
						}
					}
				}
				//counter++;
			}
			short[][] mm = new short[bYH0][bXH0];
			short a;
			short b;
			for (byte i = 0; i < bYH0; i++) {
				for (byte j = 0; j < bXH0; j++) {
					a = ref[0].m[i][j];
					b = ref[1].m[i][j];
					if ((a != Short.MAX_VALUE) ||(b != Short.MAX_VALUE)) {
						if (a == 9 && b != 9) {
							mm[i][j] = 1;
						} else if (a != 9 && b == 9) {
							mm[i][j] = 2;
						} else {
							if (smap[0][i][j] < smap[1][i][j]) {
								mm[i][j] = 1;
							} else if (smap[0][i][j] > smap[1][i][j]) {
								mm[i][j] = 2;
							} else {
								mm[i][j] = 3;
							}
						}
					}
				}
			}

			endgame[0] = true;
			for (byte i = 0; i < bYH0; i++) {
				for (byte j = 0; j <  bXH0; j++) {
					if ((ref[0].m[i][j] == 9) && (ref[1].m[i][j] == 9)) {
						endgame[0] = false;
						i = (byte) bYH0;
						j = (byte) bXH0;
					}
				}
			}
			
			//might STILL be endgame
			if (endgame[0] == true) {
				ArrayList<Position> moves1 = map.getAllMoves(1);
				for (int i = 0; i < moves1.size(); i++) {
					DijkstraPath dp = new DijkstraPath(map.getCopy(),map.H[0],moves1.get(i));
					int length = dp.shortestPath();
					if (length != Short.MAX_VALUE) {
						endgame[0] = false;
						break;
					}
				}
			}
			
			//might STILL be endgame
			if (endgame[0] == true) {
				ArrayList<Position> moves0 = map.getAllMoves(0);
				for (int i = 0; i < moves0.size(); i++) {
					DijkstraPath dp = new DijkstraPath(map.getCopy(),moves0.get(i),map.H[1]);
					int length = dp.shortestPath();
					if (length != Short.MAX_VALUE) {
						endgame[0] = false;
						break;
					}
				}
			}
			return mm;
		}
	}
	
	public static class DijkstraPath {
		private TronMap map;
		private Node target;
		private Node origin;
		public short[][] dmap;
		public boolean foundTarget;
		public Position movedOriginP;
		public int stepsLeft;
		ArrayList<Position> path;
		
		//DijkstraPath will modify map
		public DijkstraPath (TronMap map, Position originP, Position targetP) {
			this.map = map;
			this.origin = new Node(originP);
			this.origin.cost = 0;
			this.target = new Node(targetP);	
			this.dmap = map.getBigCopy();
			this.foundTarget = false;
			this.movedOriginP = new Position(originP);
			this.stepsLeft = Short.MAX_VALUE;
			this.path = new ArrayList<Position>();
		}
		
		public ArrayList<Position> getPath() {
			if (!foundTarget) {
				this.shortestPath();
			}
			return path;
		}
		
		/**
		 * Won't work if the target is ON the pole
		 */
		public int shortestPath() {	
			if (foundTarget) {
				return stepsLeft;
			}
			if (origin.equals(target)) {
				return 0;
			}
			util.fill(dmap, Short.MAX_VALUE);
			
			Position[][] prev = new Position[dmap.length][dmap[0].length];
			ArrayList<Position> moves;
			PriorityQueue<Node> pq = new PriorityQueue<Node>();
			pq.add(origin);
			if ((map.m[target.p.y][target.p.x] == 3) || (map.m[target.p.y][target.p.x] == 4)) {
				map.m[target.p.y][target.p.x] = 0;
			}
			
			dmap[origin.p.y][origin.p.x] = (short) origin.cost;
			while (pq.size() > 0) {
				Node top = pq.poll();
				if (top.p.equals(target.p)) {	//found target!
					//System.out.println(map.toBigString(dmap));
					foundTarget = true;
					stepsLeft = top.cost;
					path = new ArrayList<Position>();
					Position u = new Position(target.p);
					while (prev[u.y][u.x] != null) {
						path.add(0, u);
						u = new Position(prev[u.y][u.x]);
					}
					
					return top.cost;
				}
				moves = map.getAllMoves(top.p);
				for (int i = 0; i < moves.size(); i++) {
					if (map.m[moves.get(i).y][moves.get(i).x] == 0) {
						if (dmap[moves.get(i).y][moves.get(i).x] > (top.cost+1)) {
							dmap[moves.get(i).y][moves.get(i).x] = (short) (top.cost+1);
							prev[moves.get(i).y][moves.get(i).x] = new Position(top.p);
							pq.add(new Node(moves.get(i),top.cost+1));
						}
					} 
				}
			}
			return Short.MAX_VALUE;
		}
		
		class Node implements Comparable<Node> {
			public int cost, at;
			public Position p;
			
			public Node(Position p) {
				this.p = p;
				this.at = map.getInt(p);
			}
			
			public Node(Position p, int cost) {
				this(p);
				this.cost = cost;
			}
			
			
			public String toString() {
				return p.toString()+"c:"+cost+",at:"+at;
			}
			
			@Override
			public int compareTo(Node o) {
				Node next = (Node)o;
				if (cost < next.cost) return -1;
				if (cost > next.cost) return 1;
				if (at < next.at) return -1;
				if (at > next.at) return 1;
				return 0;
			}
		}
	}
	
	public static class Node<E> implements Comparable<Node<E>>, java.io.Serializable{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 3358389393300911760L;
		
		public E element;
	    public Node<E> parent;
	    public List<Node<E>> children;
	    public boolean built;
	    public byte pos;
	    public short alpha;
	    public byte depth;
	    
	    public Node (E element, Node<E> parent) {
	    	construct(element,parent);
	    }
	    
	    public Node (E element, Node<E> parent, int pos) {
	    	construct(element,parent);
	    	this.pos = (byte) pos;
	    }
	    
	    private void construct(E element, Node<E> parent) {
	    	this.element = element;
	    	this.parent = parent;
	        this.children = new ArrayList<Node<E>>(3);
	        this.built = false;
	        if (parent != null) {
	        	this.depth = (byte) (parent.depth+1);
	        } else {
	        	this.depth = 0;
	        }
	        
	    }

		@Override
		public int compareTo(Node<E> o) {
			if (this.alpha == o.alpha)
				return 0;
			else if (this.alpha > o.alpha)
				return 1;
			else
				return -1;
		}
	    
	    @Override
	    public String toString() {
	    	String s = element.toString().replaceFirst("\n", "   alpha:"+alpha+"\n");
	    	s = s.replace("alpha", "pos:"+pos+"   alpha");
	    	return "\n"+s+"\n";
	    }

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + alpha;
			result = prime * result + (built ? 1231 : 1237);
			result = prime * result
					+ ((children == null) ? 0 : children.hashCode());
			result = prime * result + ((element == null) ? 0 : element.hashCode());
			result = prime * result + ((parent == null) ? 0 : parent.hashCode());
			result = prime * result + pos;
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("rawtypes")
			Node other = (Node) obj;
			if (alpha != other.alpha)
				return false;
			if (built != other.built)
				return false;
			if (element == null) {
				if (other.element != null)
					return false;
			} else if (!element.equals(other.element))
				return false;
			if (pos != other.pos)
				return false;
			return true;
		}
	}
	
	public static class Position implements java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5184020962467993157L;
		public byte x;
		public byte y;
		public Position(int x, int y) {
			this.x = (byte)x;
			this.y = (byte)y;
		}
		public Position(byte x, byte y) {
			this.x = x;
			this.y = y;
		}
		public Position(Position p) {
			this.x = p.x;
			this.y = p.y;
		}
		public Position() {
			this.x = 0;
			this.y = 0;
		}
		public String toString() {
			return "("+x+","+y+")";
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
	
	public static class PositionV extends Position implements Comparable<PositionV> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5828259544283422628L;
		public int v;
		public PositionV(int x, int y, int v) {
			super(x,y);
			this.v = v;
		}
		public PositionV(Position p, int v) {
			super(p);
			this.v = v;
		}
		public String toString() {
			return "("+x+","+y+","+v+")";
		}
		
		public int compareTo(PositionV o) {
			if (this.v == o.v)
				return 0;
			else if (this.v > o.v)
				return 1;
			else
				return -1;
		}
		
		public boolean equals(PositionV p) {
			if (p.x != this.x) {
				return false;		
			}
			if (p.y != this.y) {
				return false;		
			}
			if (p.v != this.v) {
				return false;		
			}
			return true;
			
		}
	}/**
	 * Bots move on their respective maps, BUT both maps are checked before a move is allowed 
	 *
	 */
	public static class TronMap implements java.io.Serializable {
		/**
		 *  
		 */
		private static final long serialVersionUID = 1622781235561739536L;

		public byte[][] m;
		public Position[] H;
		public Position[] oldH;
		public byte outputLevel;	
		public State state;
		public boolean[] dirty;
		public boolean[][] shaded;
		public int moves;
		public int movesTemp;
		public boolean noP2;

		private long hashKey;
		
		public TronMap (byte[][] newm, boolean noP2) {
			this.noP2 = noP2;
			this.buildMap(newm);
		}

		public TronMap (byte[][] newm, Position H1, Position H2) {
			this.buildMap(newm);
			addPos(H1,H2);
		}
		
		public void buildMap (byte[][] newM) {
			final byte bXH0 = (byte) (newM[0].length);	
			final byte bYH0 = (byte) (newM.length);	
			this.m = new byte[bYH0][bXH0];
			Position H1 = null;
			Position H2 = null;
			dirty = new boolean[newM.length];
			shaded = new boolean[newM.length][newM[0].length];
			int cleanCells = 0;
			moves = 0;
			//noP2 = false;
			
			// All maps are surrounded by barriers
			for (int y = 0; y < newM.length; y++) {
				newM[y][0] = 1;
				newM[y][bXH0-1] = 1;
			}
			for (int x = 0; x < bXH0; x++) {
				newM[0][x] = 1;
				newM[bYH0-1][x] = 1;
			}

			//this.m1[1] = new boolean[newM.length][newM[0].length];
			for (byte y = 0; y < bYH0; y++) {
				cleanCells = 0;
				for (byte x = 0; x < bXH0; x++) {
					if (newM[y][x] == 1) {
						this.m[y][x] = 1;
					} else if (newM[y][x] == 2) {
						this.m[y][x] = 2;
					} else if (newM[y][x] == 3) {
						H1 = new Position(x,y);
					} else if (newM[y][x] == 4) {
						H2 = new Position(x,y);
					} else if (newM[y][x] == 0) {
						cleanCells++;
					}
				}
				if (cleanCells != bXH0) {		//not every cell in this row is clean
					dirty[y] = true;					//this column is dirty
				}
			}
			if (H1 != null || H2 != null) {
				addPos(H1,H2);
			}
			state = State.Battle;
			
			//gen hash
			long[][] zobristnumbers = util.zNum;;
			int[] mapTM2Z = {0,1,1,2,3};		
			hashKey = 0;
			for (byte i = 0; i < bYH0; i++) {
				for (byte j = 0; j < bXH0; j++) {
					hashKey ^= zobristnumbers[i*bYH0+j][mapTM2Z[m[i][j]]];
				}
			}
		}

		private void addPos(Position newH1,Position newH2) {
			if (H != null) {
				System.out.println("POSITIONS ALREADY SET");
				m = null;
			} else {
				H = new Position[2];
				oldH = new Position[2];
				this.H[0] = newH1;
				if (!noP2) {
					this.H[1] = newH2;
				}			
				for (int tpos = 0; tpos < 2; tpos++) {
					if (tpos == 1 && noP2) continue;
					if (H[tpos].y == 0 || H[tpos].y == m[tpos].length-1) {
						for (byte x = 0; x < m[0].length; x++) {
							m[H[tpos].y][x] = (byte) (tpos+1);
						}
					}
				}			
				long[][] zobristnumbers = util.zNum;;
				int[] mapTM2Z = {0,1,1,2,3};
				hashKey ^= zobristnumbers[H[0].y*m.length+H[0].x][mapTM2Z[m[H[0].y][H[0].x]]];
				if (!noP2) {
					hashKey ^= zobristnumbers[H[1].y*m.length+H[1].x][mapTM2Z[m[H[1].y][H[1].x]]];
				}
				
				this.m[H[0].y][H[0].x] = 3;
				if (!noP2) {
					this.m[H[1].y][H[1].x] = 4;
				}
				hashKey ^= zobristnumbers[H[0].y*m.length+H[0].x][mapTM2Z[m[H[0].y][H[0].x]]];
				if (!noP2) {
					hashKey ^= zobristnumbers[H[1].y*m.length+H[1].x][mapTM2Z[m[H[1].y][H[1].x]]];
				}
			}
		}

		public byte[][] getMap() {
			return m;
		}

		
		public ArrayList<Position> getAllMoves(int pos) {
			return getAllMoves(H[pos]);
		}

		public ArrayList<Position> getAllMoves(Position old) {
			return getAllMoves(old, (byte)0, (byte)(m.length-1));
		}

		/**
		 * 
		 * @param old
		 * @param bYL The lowest row that may be accessed (0)
		 * @param bYH The highest row that may be accessed (m.length-1)
		 * @return
		 */
		public ArrayList<Position> getAllMoves(Position old, byte bYL, byte bYH2) {
			ArrayList<Position> moves2;
			moves2 = new ArrayList<Position>();
			byte bYH = (byte) (this.m.length-1);		
			byte bYH1 = (byte) (this.m.length-2);	
			if (old.y != 0) {
				if (this.m[old.y-1][old.x] == 0) {
					moves2.add(new Position(old.x,old.y-1));
				}
			} else {
				for (byte x = 0; x < this.m[0].length; x++) {
					if (this.m[1][x] == 0) {
						if (x != old.x) {
							moves2.add(new Position(x,1));
						}
					}
				}
			}
			if (old.y != bYH) {
				if (this.m[old.y+1][old.x] == 0) {
					moves2.add(new Position(old.x,old.y+1));
				}

				if (old.y != 0) {
					byte newX = (byte) ((old.x+1)%this.m[0].length);
					if (this.m[old.y][newX] == 0) {
						moves2.add(new Position(newX,old.y));
					}				
					newX = (byte) util.negMod(old.x-1, this.m[0].length);			
					if (this.m[old.y][newX] == 0) {
						moves2.add(new Position(newX,old.y));
					}
				}
			} else {
				for (byte x = 0; x < this.m[0].length; x++) {
					if (this.m[bYH1][x] == 0) {
						if (x != old.x) {
							moves2.add(new Position(x,bYH1));
						}
					}
				}
			}
			return moves2;
		}

		public byte posToDir(Position origin, Position p2) {

			int x = p2.x - origin.x;		
			int y = p2.y - origin.y;

			if (x == 1) {
				return 2;
			} else if (x == -1) {
				return 4;
			} else if (y == 1) {
				return 3;
			} else if (y == -1) {
				return 1;
			} else {
				return -1;
			}
		}

		public boolean canMove(byte dir,int pos) {
			if (dir >= 1 && dir <= 4) {
				return canMove(dirToPos(dir,pos),pos);
			}
			return false;
		}

		public Position dirToPos(byte dir, int pos) {
			Position newH;
			switch(dir) {
			//N
			case 1: {newH = new Position(H[pos].x,H[pos].y-1);break;}
			//S
			case 3: {newH = new Position(H[pos].x,H[pos].y+1);break;}
			//E
			case 2: {newH = new Position((H[pos].x+1)%m[0].length,H[pos].y);break;}
			//W
			case 4: {newH = new Position(util.negMod(H[pos].x-1, m[0].length),H[pos].y);break;}
			default: return null;
			}
			return newH;
		}
		public Position dirToPos(byte dir, Position pos) {
			Position newH;
			byte newX = pos.x;
			byte newY = pos.y;
			switch(dir) {
			//N
			case 1: {newY = (byte) (newY-1);break;}
			//S
			case 3: {newY = (byte) (newY+1);break;}
			//E
			case 2: {newX = (byte) ((pos.x+1)%m[0].length);break;}
			//W
			case 4: {newX = (byte) util.negMod(newX-1, m[0].length);break;}
			default: return null;
			}
			newH = new Position(newX,newY);
			return newH;
		}

		public byte headingToDir(int heading) {
			switch(heading) {
			case 0: {return 2;}
			case 90: {return 3;}
			case 180: {return 4;}
			case 270: {return 1;}
			default: return 0;
			}
		}

		public Position headingToPos(int heading, Position p) {
			return dirToPos(headingToDir(heading),p);
		}

		public boolean canMove(Position newH,Position old) {
			return canMove(newH, old, (byte)0, (byte)m.length);
		}

		public boolean canMove(Position newH,Position old, byte bYL, byte bYH) {
			if (newH == null || old == null ) {
				return false;
			}
			if (newH.y <= bYH && newH.y >= bYL) { //array bounds
				if (distManh(newH, old) == 1) {
					if (m[newH.y][newH.x] == 0) {
						return true;
					} 
				}
			}
			return false;
		}

		public boolean canMove(Position newH,int pos) {
			if (newH.y < m.length && newH.y >= 0) { //array bounds
				if (distManh(newH, H[pos]) == 1) {
					if (m[newH.y][newH.x] == 0) {
						return true;
					} 
				}
			}
			return false;
		}

		public boolean move(Position newMove, int pos) {
			if(canMove(newMove,pos)) {

				long[][] zobristnumbers = util.zNum;
				int[] mapTM2Z = {0,1,1,2,3};

				hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
				m[H[pos].y][H[pos].x] = (byte) (pos+1);
				hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];

				H[pos] = new Position(util.negMod(newMove.x, m[0].length),newMove.y);
				//			m[H[pos].y][H[pos].x] = (byte) (pos+3);
				//			if (H[pos].y == 0 || H[pos].y == m.length-1) {
				//				for (byte x = 0; x < m[0].length; x++) {
				//					m[H[pos].y][x] = (byte) (pos+3);
				//				}
				//			}
				if (H[pos].y == 0 || H[pos].y == m.length-1) {
					for (byte x = 0; x < m[0].length; x++) {
						hashKey ^= zobristnumbers[H[pos].y*m.length+x][mapTM2Z[m[H[pos].y][x]]];
						m[H[pos].y][x] = (byte) (pos+1);
						hashKey ^= zobristnumbers[H[pos].y*m.length+x][mapTM2Z[m[H[pos].y][x]]];
					}
				}
				hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
				m[H[pos].y][H[pos].x] = (byte) (pos+3);
				hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
				dirty[H[pos].y] = true;
				moves++;
				if (H[pos].y > 1)			shaded[H[pos].y-1][H[pos].x] = true;
				if (H[pos].y < m.length-1)	shaded[H[pos].y+1][H[pos].x] = true;
				shaded[H[pos].y][(H[pos].x+1)%m[0].length] = true;
				shaded[H[pos].y][util.negMod(H[pos].x-1,m[0].length)] = true;
				return true;
			}
			return false;
		}
		
		//undo the newMove
		public boolean undoMove(Position oldPosition, int pos) {
			if (oldPosition.y < m.length && oldPosition.y >= 0) { //array bounds
				if (distManh(oldPosition, H[pos]) == 1) {
					if (m[oldPosition.y][oldPosition.x] == (byte) (pos+1)) {	//move back into your own wall
						long[][] zobristnumbers = util.zNum;
						int[] mapTM2Z = {0,1,1,2,3};
						
						hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
						m[H[pos].y][H[pos].x] = 0;
						hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
						if (H[pos].y == 0 || H[pos].y == m.length-1) {
							for (byte x = 0; x < m[0].length; x++) {
								hashKey ^= zobristnumbers[H[pos].y*m.length+x][mapTM2Z[m[H[pos].y][x]]];
								m[H[pos].y][x] = 0;
								hashKey ^= zobristnumbers[H[pos].y*m.length+x][mapTM2Z[m[H[pos].y][x]]];
							}
						}
						H[pos] = new Position(util.negMod(oldPosition.x, m[0].length),oldPosition.y);
						
						hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
						m[H[pos].y][H[pos].x] = (byte) (pos+3);
						hashKey ^= zobristnumbers[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
						//dirty[H[pos].y] = true;
						moves--;
						return true;
					} 
				}
			}
			return false;
		}

		public void fill(byte f) {
			for (byte i = 0; i < m.length; i++) {
				for (byte j = 0; j < m[0].length; j++) {
					m[i][j] = f;
				}
			}
		}

		/**
		 * @return an integer equal to m.length*p.y + p.x
		 */
		public int getInt(Position p) {
			return m.length*p.y + p.x;
		}

		public TronMap getCopy() {
			final byte bXH0 = (byte) (m[0].length);	
			final byte bYH0 = (byte) (m.length);	
			byte[][] newM = new byte[bYH0][bXH0];
			for (byte y = 0; y < bYH0; y++) {
				newM[y] = Arrays.copyOf(m[y], bXH0);
				//for (byte x = 0; x < bXH0; x++) {
				//	newM[y][x] = m[y][x];
				//}
			}

			//		Position[] newH = new Position[2];
			//		newH[0] = new Position(H[0].x,H[0].y);
			//		newH[1] = new Position(H[1].x,H[1].y);

			TronMap newTM = new TronMap(newM,noP2);		
			return newTM;
		}

		public short[][] getBigCopy() {
			short[][] newM = new short[m.length][m[0].length];
			for (byte y = 0; y < newM.length; y++) {
				for (byte x = 0; x < newM[0].length; x++) {
					newM[y][x] = m[y][x];
				}
			}
			return newM;
		}

		public int[][] getBiggerCopy() {
			int[][] newM = new int[m.length][m[0].length];
			for (byte y = 0; y < newM.length; y++) {
				for (byte x = 0; x < newM[0].length; x++) {
					newM[y][x] = m[y][x];
				}
			}
			return newM;
		}

		public int distManh(Position p1, Position p2) {
			if (p1.y == 0 || p1.y == m.length-1 || p2.y == 0 || p2.y == m.length-1) { //only vertical matters
				return Math.abs(p2.y-p1.y);
			} else {
				int[] d = new int[4];
				int xd = Math.abs(util.negMod((p2.x), m[0].length)-util.negMod((p1.x), m[0].length));
				d[0] = Math.abs(p2.y-p1.y)+xd;
				//-1 at the end because this is distance, not index, relating modding
				d[1] = Math.abs(p2.y-p1.y)+m[0].length-xd; 
				d[2] = p2.y+p1.y;
				d[3] = ((m.length)-1)*2 - (p2.y+p1.y);
				int minD = Integer.MAX_VALUE;
				for (int i = 0; i < d.length; i++) {
					if(d[i] < minD) {
						minD = d[i]; 
					}
				}
				return minD;			
			}		
		}

		/**
		 * Build a distance map
		 * @param p
		 * @param type 1: Use the faster(?) dijkstra method \n 2: use BFS \n 3: calculate with distMan
		 * @return
		 */
		public short[][] mapDistance(Position p, int type) {
			//if (type == 2) {
				BFS bfs = new BFS(this,p,true);
				short[][] smalld = bfs.distance();
				return smalld;	 
			//}
			//return null;
		}

		/**
		 * Build a Voronoi diagram 
		 * @param type 1: Use the faster(?) dijkstra method \n 2: use BFS \n 3: calculate with distMan
		 * @return
		 */
		public short[][] mapVoronoi(int type, int[] territory, boolean[] endgame) {
			boolean oldV = false;
			boolean newV = true;
			boolean testDirectCalc= true;
			
			//long startTime = System.nanoTime();
			endgame[0] = true;
			
			short[][] mVor = null;
			if (oldV) {		
				short[][] md1 = this.mapDistance(this.H[0], type);
				short[][] md2 = this.mapDistance(this.H[1], type);
		
				if (this.outputLevel > 2) {
					System.out.println(this.toBigString(md1));
					System.out.println();
					System.out.println(this.toBigString(md2));
				} 
				short[][][] md = {md1,md2};
				//byte[][] m1 = this.mapDistance(this.H[1], dijkstra);
				mVor = new short[m.length][m[0].length];
				for (byte i = 1; i < m.length-1; i++) {
					for (byte j = 0; j < m[0].length; j++) {
						if ((md[0][i][j] != Short.MAX_VALUE) ||(md[1][i][j] != Short.MAX_VALUE)) {
							if (md[0][i][j] > md[1][i][j]) {
								mVor[i][j] = 2;
								territory[1]++;
							} else if (md[0][i][j] < md[1][i][j]) {
								mVor[i][j] = 1;
								territory[0]++;
							} else { //equal, we're on the ridge
								mVor[i][j] = (short) (-1*md[0][i][j]);
								territory[2]++;
							}
						}
					}
				}
				for (byte i = 0; i < m.length; i++) {
					for (byte j = 0; j < m[0].length; j++) {
						if ((md[0][i][j] != Short.MAX_VALUE) && (md[1][i][j] != Short.MAX_VALUE)) {
							endgame[0] = false;
							i = (byte) m.length;
							j = (byte) m[0].length;
						}
					}
				}		
			}
			//int[] territory2 = new int[3];
			short[][] mVor2 = null;
			//boolean[] endgame = {true};
			BFS bfs = null;
			if (newV) {
				
				bfs = new BFS(this,this.H[0],true);
				bfs.start2 = this.H[1];
				mVor2 = bfs.dualDistance(endgame); 

				int[] redTiles = new int[3];
				int[] blackTiles = new int[3];
				int rbCounter = 0; 

				int target = 0;
				for (byte i = 0; i < m.length; i++) {
					for (byte j = 0; j < m[0].length; j++) {
						if (mVor2[i][j] == 1) {
							target = 0;
						} else if (mVor2[i][j] == 2) {
							target = 1;
						} else {
							target = 2;
						}
						territory[target]++;
						
						if (i != 0 && i != m.length-1) {
							rbCounter++;
							if (rbCounter % 2 == 0) {
								redTiles[target]++;
							} else {
								blackTiles[target]++;
							}
						}
					}
					rbCounter++;
				}
				for (int i = 0; i < 3; i++) {
					territory[i] = territory[i] - Math.abs(redTiles[i]-blackTiles[i]);
				}
				
				//bfs.dualDistance();
			}
			return mVor2;
		}

		public short[][] mapVoronoiSolo(int type, int[] territory, boolean[] endgame) {
			short[][] mVor = null;

			BFS bfs = new BFS(this,this.H[0],true);
			short[][] md1 = bfs.distance(); //DONT CLEAR this.H[1]
			
			mVor = new short[m.length][m[0].length];
			for (byte i = 0; i < m.length; i++) {
				for (byte j = 0; j < m[0].length; j++) {
					if ((md1[i][j] != Short.MAX_VALUE && md1[i][j] != 0)) {	//don't count the currently occupied spot
						territory[0]++;
						if (i == 0 || i == m.length-1) {
							break;
						}
					}
				}
			}
			endgame[0] = true;
			return mVor;
		}

		/**
		 * Computes the value of the current map according to some heuristic  
		 * @return
		 */
		public short hValue(int pos, int type) {
			int[] t = new int[3];
			boolean[] endgame = {false};
			short hv = 0;
			short[][] v1;
			if (!noP2) {
				v1 = this.mapVoronoi(type,t,endgame);
			} else {
				v1 = this.mapVoronoiSolo(type,t,endgame);
			}
			//byte[][] v2 = this.mapVoronoi(!dijkstra,t,endgame);
			if (endgame[0]) {
				state = State.Survival;
			} else {
				state = State.Battle;
			}

			if (this.outputLevel > 2) {
				System.out.println(this.toString(v1));
			}
			
			hv = (short) (t[pos]-t[(pos+1)%2]);
			if (state == State.Survival) {
				if (hv == 0) {
					hv = 0;
				} else if (hv > 0) {
					hv = (short) (hv + 1000);
				} else {
					hv = (short) (hv - 1000);
				}
			}
			return hv;
		}

		public String toBigString(short[][] out) {
			StringBuilder sb2 = new StringBuilder();
			for (int i = 0; i < m.length; i++) {
				for (int j = 0; j < m[0].length; j++) {
					sb2.append(util.padLeft(Integer.toString(out[i][j]),6));
				}
				sb2.append('\n');
			}
			return sb2.toString();
		}

		public String toVoronoiMap(short[][] out) {
			StringBuilder sb = new StringBuilder();
			for (byte i = 0; i < m.length; i++) {
				for (byte j = 0; j < m[0].length; j++) {

					switch (m[i][j]) {
					case 0: { 
						switch (out[i][j]) {
						case 1: sb.append('x'); break;
						case 2: sb.append('o'); break;
						case 3: sb.append('#'); break;
						}
					} break;
					case 1: sb.append('X'); break;
					case 2: sb.append('O'); break;
					case 3: sb.append('+'); break;
					case 4: sb.append('.'); break;
					}
				}
				sb.append("|\n");

				if (i == m.length-1) {
					for (byte j = 0; j < m[0].length; j++) {
						sb.append("#");
					}
					sb.append("#");
				}
			}
			return sb.toString();
		}

		public String toString(short[][] out) {
			StringBuilder sb = new StringBuilder();
			for (byte i = 0; i < out.length; i++) {
				sb.append('\t');
				for (byte j = 0; j < out[0].length; j++) {
					if (out[i][j] == 0) {
						switch (m[i][j]) {
						case 0: sb.append(' '); break;
						case 1: sb.append('X'); break;
						case 2: sb.append('O'); break;
						case 3: sb.append('+'); break;
						case 4: sb.append('.'); break;
						case 5: sb.append('B'); break;
						}
					} else if (out[i][j] == 1) {
						sb.append('1');
					} else if (out[i][j] == 2) {
						sb.append('2');
					} else {
						sb.append('?');
					}
				}
				sb.append("|\n");
			}
			sb.append("\n");
			//System.out.println(sb.toString());
			return sb.toString();
		}

		public int heading(Position origin, Position p2) {
			double x = p2.x-origin.x*0.99999;
			double y = p2.y-origin.y;
			int bias = 0;
			if (x < 0) {
				bias = 180;
			} else if (y < 0 ) {
				bias = 360;			
			}
			//double r = Math.hypot(x, y);
			return (int) Math.round(Math.toDegrees(Math.atan((y)/(x)))) + bias;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			int[] t = new int[3];
			boolean[] endgame = {false};
			short[][] v1;
			if (!noP2) {
				v1 = this.mapVoronoi(1,t,endgame);	
			} else {
				v1 = this.mapVoronoiSolo(1,t,endgame);	
			}
			for (byte i = 0; i < m.length; i++) {
				for (byte j = 0; j < m[0].length; j++) {

					switch (m[i][j]) {
					//case 0: sb.append(' '); break;
					case 0: {
						if (v1[i][j] == 3) {
							sb.append('\'');
						} else {
							sb.append(' ');
						}
					} break;
					case 1: sb.append('X'); break;
					case 2: sb.append('O'); break;
					case 3: sb.append('+'); break;
					case 4: sb.append('.'); break;
					case 5: sb.append('B'); break;
					}
				}
				sb.append("|\n");

				if (i == m.length-1) {
					for (byte j = 0; j < m[0].length; j++) {
						sb.append("#");
					}
					sb.append("#");				

					sb.append("\thVals:"+this.hValue(0, 1)+"\t"+this.hValue(1, 1));				
				}
			}
			return sb.toString();
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {		
			//computed incrementally
			return (int)hashKey;
		}
		
		public long hashCodeLong() {
			return hashKey;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TronMap other = (TronMap) obj;
			if (m.length != other.m.length) {
				return false;
			}
			if (!Arrays.deepEquals(m,other.m)) {
				return false;
			}
			return true;
		}
	}
	
	public static class util {
		public static int negMod(int x, int mod) {
			if (x >= 0) {
				return x%mod;
			} else {
				//return ((x*-1+1)*mod+x*-1)%mod;
				return (mod+(x%mod))%mod;
			}
		}
		public static String padRight(String s, int n) {
		     return String.format("%1$-" + n + "s", s);  
		}

		public static String padLeft(String s, int n) {
		    return String.format("%1$#" + n + "s", s);  
		}
		


		public static void fill(short[][] target, short v) {
			for (short i = 0; i < target.length; i++) {
				for (short j = 0; j < target[0].length; j++) {
					target[i][j] = v;
				}
			}
		}
		
		public static void fill(int[][] target, int v) {
			for (short i = 0; i < target.length; i++) {
				for (short j = 0; j < target[0].length; j++) {
					target[i][j] = v;
				}
			}
		}

		public static void set(short[][] target, Position p,  short v) {
			target[p.y][p.x] = v;
		}
		
		public static void subtract(short[][] base, short[][] diff) {
			for (short i = 0; i < base.length; i++) {
				for (short j = 0; j < base[0].length; j++) {
					base[i][j] -= diff[i][j];
				}
			}
		}
		
		public static void main(String[] args) {
			//Ranlux r = new Ranlux();
			
			Random r = new Random();
			HashSet<Long> h = new HashSet<Long>();
			int map_sizeSquared = map_size * map_size;

			boolean complete = false;
			while (!complete) {
				while (h.size() < map_sizeSquared*4) {
					long rand = r.nextLong();
					if (!h.contains(rand)) {
						h.add(rand);
					}
				}
				
				boolean dirty = false;
				long d1 = 0;
				long d2 = 0;
				long d3 = 0;
				for (Long i : h) {
					if (dirty) {
						break;
					}
					for (Long i2 : h) {
						if (h.contains(i^i2)) {
							d1 = i^i2;
							d2 = i;
							d3 = i2;
							System.out.println("dirty:"+ (i^i2));
							dirty = true;
							complete = false;
							break;
						}
					}
				}
				if (dirty) {
					h.remove(d1);
					h.remove(d2);
					h.remove(d3);
				}
				if (!dirty && h.size() == map_sizeSquared*4) {
					complete = true;
				}
			}

			long[][] z = new long[map_sizeSquared][4];
			int k = 0;
			for (Long i : h) {
				z[k%map_sizeSquared][k/map_sizeSquared] = i;
				k++;
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < map_sizeSquared; i++) {
				sb.append('{');
				for (int j = 0; j < 4; j++) {
					if (j != 0) {
						sb.append(',');
					}
					//int l = (int) ((r.raw()*2-1)*Integer.MAX_VALUE);
					if (z[i][j] == 0) {
						z[i][j] = r.nextLong();
					}
					long l = z[i][j];
					sb.append(l);
					sb.append('l');
				}
				sb.append("},\n");
			}
			System.out.println(sb.toString());
		}
		
		public static long[][] zNum = {
			{2017350528918112300l,-8374660337016933991l,5878822997615059064l,3946462984350048825l},
			{-2578657450694825339l,8072281952519300011l,3905819022250054280l,3079784073854126722l},
			{210361594003533179l,-6963882881459481007l,192118952666891630l,3983441593760172853l},
			{-6436841172166092189l,-6527441821003057501l,3608706570233849478l,5374971298310275363l},
			{-6259157261697244929l,-5220006893368398681l,4590584906027331105l,-2756887042285472217l},
			{3478184025225077567l,-8163560771985318720l,7799276142647684613l,8373159303051479097l},
			{-3857044108054941547l,-2307982461876539071l,-4992883344460303442l,-4336366869946824910l},
			{-7333896589209644396l,6106946148088984520l,-4005632459731236462l,-6989321026990262350l},
			{-6784403536691618560l,6419676421623339527l,3917498477464906653l,-3282760820698472853l},
			{-224432951312753694l,6120755816233492202l,-1385351774082874721l,-4091155924336381131l},
			{-1369880032283988626l,-4009029811378407540l,872120667943607714l,5512516662446495642l},
			{-9044289394532467996l,2969490017802834767l,-4732095839410929557l,2148133192045649597l},
			{7145600997843384882l,6055719273532879465l,7717489467268861292l,6833095393092730878l},
			{-2343956068671296873l,-402012380240861067l,3969808089013142840l,-2972704162759133231l},
			{4903236473372934215l,-8964805114140753412l,-4054422016818444920l,-685276244490053527l},
			{-2026365264972528694l,7940906378264201601l,1406914922578829549l,-4912861096108261075l},
			{-690670595145262023l,-5548458361423675846l,-8356703968601642747l,2289932604569318692l},
			{2769695123258064666l,-4001428160206445475l,1182043676055259959l,6132030372504447081l},
			{3820755407247595183l,-5134800648122584730l,-6033714509054841770l,5844421207459316562l},
			{6603850371411778611l,-7926489526052167704l,-2853041552011642714l,5080808707224102096l},
			{-901606743732225706l,3979006066067909316l,7414558702545467320l,-9186470913307096786l},
			{4006760167332043430l,2747622833968237735l,1944446812554883377l,-5777726695485300948l},
			{5966802361968566362l,-7107941185134383667l,-1369753504425989025l,7577022994511851208l},
			{-4704626303395114795l,-1773581838443814771l,8435435072063700720l,6998853308916615269l},
			{4322997945991159787l,-2373174449656802670l,-7675607496314500153l,-6373584122467114201l},
			{7850646321085760632l,-598816519201203542l,6461030180500692703l,-2005526925707347545l},
			{2977008986783381676l,-2998301139573943857l,-7327550536359684523l,-5004526670644880731l},
			{7293698581360937454l,-5674602682300973468l,-1385747570394746956l,2625120501021464578l},
			{8841437919174196005l,-5669363658262544083l,1713496300017777828l,328038840154486144l},
			{7747913396122968547l,-8531123928101604715l,-1163035860761290977l,9133100516788954312l},
			{1140376379949207905l,-7407033417854926705l,-2298257101546929048l,-6019253084672674434l},
			{3012388903336059765l,6747586294981222667l,-7690089621875714979l,-7398384247153109313l},
			{-6910739414310383042l,4346718806501824215l,3633799152841193011l,3286458056923524147l},
			{6492075435009529582l,-7281253718279784299l,8298544795217021915l,-1743241310681972103l},
			{-8994283453061425879l,1467369049980979554l,7795376783844193707l,-7597077241795826497l},
			{-6318799453141210018l,1758993280654265691l,8188226728984239094l,8993337179755534292l},
			{-2303465352229392454l,-5601001018245598381l,2716286299719873690l,3529047933550324371l},
			{3434490737851107009l,-4909314327666684713l,-7768315464326181163l,4451658764652581102l},
			{3563454314750977559l,-2771596185393568679l,3108206183413040846l,-3542065691169669231l},
			{-6062382672801348565l,941983445229939771l,4865340010993703560l,-1471866750940556620l},
			{5267636429925811431l,7345539444006790874l,-9210426400961706026l,-1946613713790442408l},
			{4890804196621594531l,-4580079997129908859l,8201429730981880518l,189531992961155508l},
			{4366561344421809435l,-2944776359140628360l,6614543161376861272l,4267247083381621217l},
			{6745070948412218345l,5391017849845739629l,3144632809700499172l,2365042862501721279l},
			{8646975519629469350l,-4442902304685293505l,-5389599714922905733l,7910378639993168175l},
			{8427377417476196715l,-7068113675275904683l,6716283237701941380l,2235526102397473990l},
			{3557265620673976970l,-2606616893007927652l,-8606555385228347156l,2707237433694366619l},
			{-7118856924552828668l,7195559312255004606l,6773484066194660767l,2005347393703199588l},
			{3474799470496783352l,7528947211056560003l,-5671194906470980978l,-7919576551881408216l},
			{7418406497628004213l,3720453937349169362l,1282553366427132591l,-2783600324803862209l},
			{8432309493422240913l,-3472975686215155684l,4902412408835449215l,-4933481741036901899l},
			{8289657358837759900l,-8785993343432550636l,-776379445941248549l,20960674229142879l},
			{5127701962034603197l,-7225052707202092239l,-8200706489003491427l,5693891831231525619l},
			{1787116420934256580l,238583987325419473l,-3029274687209790421l,3447329398003683644l},
			{892085085729285656l,-967819167317644326l,5615019398574746687l,-1500209319799095290l},
			{-1861590697946060718l,7745804966039278301l,3584464286866800918l,357751383683459931l},
			{6169264738844576839l,2203031336905041628l,4612578439975253163l,8402470346319110139l},
			{6853295992782402800l,3180058332198128950l,6398200167194657864l,-2891930242780315877l},
			{5221939249253880095l,-5572723003495119939l,2450607094819724748l,1077731971489878639l},
			{6883627662433860387l,-8245922213427499848l,5080437693197344869l,2767951471845674824l},
			{-2753281116573263272l,2126386389522724287l,7698617876941719629l,3868532307458532725l},
			{-5716362145980825963l,-934318268980688201l,-4781261474977415293l,4091315802803605239l},
			{-7724501864069886368l,-2589160089045353340l,-6016415449973458984l,2812942566258763136l},
			{9048337518887860603l,7754856888001422130l,8670891718569586965l,-5784083670349800255l},
			{6233949011013179109l,-8097518014361631488l,7593253673010101225l,4292678566658803033l},
			{-618709742006581194l,-730157646647177585l,-6438292024289127956l,-1747646349507136852l},
			{4607888866442001119l,2951095074844142601l,-2485321714234896039l,1780521386770684078l},
			{-5403473240538967080l,1012054058535045222l,4393024015649816606l,646689102914546815l},
			{-6773340812342670200l,-5015274105642270080l,7532009692578534420l,-1679787521358731840l},
			{-5652258668852236174l,8052541843458717616l,-6693295958451480090l,-8823379944865670802l},
			{5964011312322791972l,-6608587868384493984l,-384106913327687448l,401973796738345024l},
			{5601885566701665547l,7494320274796394635l,4906302985964192870l,-6969526042603994246l},
			{3664641929531088028l,7237125336373535426l,-5741952068837616744l,-2750136481445773167l},
			{-520011854176187903l,-8667586223342769373l,4340173812543201620l,-3231955731177979350l},
			{2949359092546428371l,-7466177865552993107l,5560166188700003950l,-2028444383604563636l},
			{-181259622271978097l,5248892534171721631l,-489148612412457411l,8132753865202836206l},
			{-7883357886471476862l,-7278745791759776013l,5650680076672699928l,-3302015762660963176l},
			{-2951924613431916503l,2701130218784418618l,-2886600562076883387l,-215483892124807405l},
			{-455463372438972289l,6053366750376134583l,-7052034543381119850l,5184979545551728611l},
			{4309643784992895335l,-6015826283230598916l,-3572555810449331721l,8425312234541440968l},
			{5579335902813987517l,5369367415309907655l,-628250700967237303l,7334097561676826200l},
			{-4647266689259561788l,988417815767892896l,-592703238864441814l,2140414189060610418l},
			{-8037057452615203550l,5608331270386115792l,3801725034958127879l,8275319369996604472l},
			{1120879248002552832l,-3108885635775254085l,7048561680143654859l,2052905997292732460l},
			{-8960064822863596190l,-6190808034575255068l,8691991470758799378l,-8637555954233651183l},
			{6336080994889194278l,443392834307749185l,-1043379748520626383l,6067341146533638586l},
			{8874266947688980069l,6502086682650369576l,4056801908995931487l,-1260747960309751734l},
			{-1732395588770271498l,-7361263415934165436l,7013127506548508335l,-2848634660435983849l},
			{-4214295061318003956l,-4423885685892203254l,324685179888101361l,8991431892132125610l},
			{5147401790587309195l,4700855285569070829l,26105678962678404l,4879430046248057086l},
			{778083054760587648l,-4738923082159532981l,-7607136239939705357l,-5133261519976484648l},
			{5213598685822801446l,-766176852495017945l,-8711368704949648814l,-6741538863415983268l},
			{-1364813860851386908l,-8484609471757118694l,-1882745029046598631l,5954047149326828290l},
			{7657226106178070157l,-8108653304935261909l,-1298674801997284657l,-6586468316020381210l},
			{7949654991435417213l,1518976731615939707l,76796214267248380l,1471485282757836798l},
			{-1769398252332902406l,-1317659312269596396l,7156183522092289335l,4585599735002407353l},
			{-4530389179999981875l,-7917432180719818418l,7295807255098737373l,8146303604358563147l},
			{1158880785392974310l,-7350151060973110903l,-5449568126065091448l,856826235241495484l},
			{-4277301666386660751l,-3348140602596802465l,-767017284843862796l,-6004370368521797729l},
			{7216200048371645237l,6966566858134892597l,3864276691268077674l,-1793734436876582026l},
			{4461225684577002964l,1484969675299721178l,3931352079981464569l,5526973930738138537l},
			{8569704370697472560l,5627963317278219865l,2231124800291303850l,8715916160951013982l},
			{-2626616658559785298l,286232488006310820l,-8315700737631691675l,3114916390886651590l},
			{-3711032443562725088l,8798193960313592097l,4013564855470561064l,-8541614102655865005l},
			{7084316610620416955l,255177001859699058l,-285710244953548189l,5253234008448111979l},
			{-6911075768666994933l,3580592976707574865l,1670396446039321488l,-4437712396503162841l},
			{-6780141991987286398l,2614143466132106825l,-9028372969906706647l,-4984009649530036683l},
			{-3045752618010138342l,-733051452205012061l,7155593114406089587l,-5129788107088300153l},
			{-7337500700039702723l,8265788312571884977l,4090972041607567618l,5240765109388852707l},
			{-812438116579310835l,8278013119453354332l,4779064134684292072l,7842900255209806307l},
			{9201028338676020398l,8153145575195295446l,-7942113726007930099l,59241688117360696l},
			{8853031640837969380l,-5883548804879635047l,6981169040393295497l,7648594765835192804l},
			{-6513714285588327392l,1922211644998465477l,211976007287142323l,1396838690423830043l},
			{-4647080762851736973l,3691893884873568308l,-2363219656292554787l,-1125757022770162575l},
			{1316577855106548772l,-7038069856360463680l,-4121220765589418923l,-3624665156053739179l},
			{3728305979359428129l,-6459338589683256618l,-786981412264141439l,-1578592952419763858l},
			{2760873602360310760l,6012812904738564461l,-3030477437240909793l,-2729664186629821860l},
			{-3600675167753011431l,4134503661521334618l,640068727408059841l,4631522726701860044l},
			{-1141774740114672573l,939892583125688055l,1180077710151807699l,957772536205523335l},
			{5329695671919369897l,-8586663897703552159l,-5430618053937450790l,-6825769241209943954l},
			{-576260265791724071l,-4067183590111498789l,-3154693692277500310l,-8182818815010355418l},
			{-7088449001903646305l,-3273603795522364628l,1935263251821386481l,6897588854346953739l},
			{3432678769588784079l,-5383829417793007586l,-8520877603958202700l,3340175839946551916l},
			{-2629435030617600144l,-3239265856590068192l,-7514067914123304821l,1101716554187921245l},
			{5846998724933184219l,-9094214972970754503l,-8025770295848534012l,-628251059187235036l},
			{4320419277776060810l,-3556329284598226084l,90001954154734421l,7679061750606259401l},
			{-8851182059394261387l,458775930804195961l,3870655441052129094l,1636367578739649040l},
			{-696948862442130672l,5401770829735237423l,-2393373013566543743l,-8327676953425055242l},
			{2090226701194513291l,7497093188855994362l,-8757706619096777899l,8439471374066358679l},
			{-4747094522006767909l,-7115837481316708925l,-4229764927313471065l,-1872084042887606690l},
			{232188219969157310l,-5984288675671374091l,-6148566476863186118l,5841901174904078834l},
			{-2713156651287811518l,1689645965305378297l,6179791199787700616l,744337126440488724l},
			{-2664451639819265307l,-6499557726686205486l,-4348702997739525151l,-4850881569420328715l},
			{3473834322088773129l,-5191004482074695907l,1045356102843902539l,-3347996938420328996l},
			{-4603829594053189041l,-5104202187264200911l,-7904299296153942584l,-4929844721514298810l},
			{5844462658377773731l,7879765666486879833l,7655331573669762524l,-6930074789719362555l},
			{4864096296789196568l,-7130632569405116779l,-5232056779995632313l,6477139908600324715l},
			{5301077239165318629l,-2751782926013353189l,-2904358076188934387l,-5517230966429424279l},
			{-6971013075741256589l,-3189234683654372306l,4679884680079304354l,4875002736736000911l},
			{-5868565094203911830l,8616449506704863275l,6819487937952172363l,1367236411645469611l},
			{-5077417364230839019l,7514699541438852563l,-2578559620542284094l,-6892859944685644791l},
			{-4199078065512017658l,168559949958960048l,-611147853270644574l,3046823330401521104l},
			{-3129196732034463096l,-1028972207557551861l,641433343205875515l,5342177144406087491l},
			{566734435208042670l,-8106655512189784306l,-6009195568231147372l,-970481277024076410l},
			{8572387222552957374l,4999740686769403422l,251227256981546964l,8673589924675399495l},
			{-7305049497205685272l,3458894391543010128l,-9055909100786546633l,-83877655727870783l},
			{-4009341798635951831l,4484855459162039099l,7143582469906531065l,-245074604753422686l},
			{8727420175725877278l,-1046029970609056476l,-2507625849883319888l,1249974006058294602l},
			{1754730314790687904l,4289633927361179546l,297287037269209763l,-1556412248660192764l},
			{-1974237517721782200l,-9058160503029437782l,-4700223390342532613l,-5210938083169058335l},
			{-7854420880101824095l,3745743670132998771l,-3681041860048899894l,-8405304342867204889l},
			{-1274269033887540803l,-8144502116038691998l,8301501614462029878l,-8822315955002193819l},
			{-6459744458747570401l,4244478861284572764l,5271893429157235066l,6749059042213404714l},
			{-5291114918711136287l,7817095031558613888l,-442821713948502414l,-9177367544095871758l},
			{5596676957842635061l,7437015033918293205l,-6316874070265128613l,5910620597755476047l},
			{-8570217882637991462l,-5738161282206577161l,-7804291755629270842l,-1239544899174155310l},
			{4928803792979741265l,-6936318112752750866l,3604531519207263031l,-3663898366114534579l},
			{4274782718524991627l,2307886604470788917l,4364913428655769419l,3436383225663896745l},
			{1008823488486273857l,256309262709251121l,2796917609835178945l,5499405839058761460l},
			{7281451851945479873l,-8475930193500369504l,5926638351472087042l,-2173770312830760045l},
			{1659796352764984163l,1094914650677008905l,1427960044926084865l,-1956827215137759703l},
			{-4838545195470681233l,-6320408709182122815l,8255310227776865736l,1567634423261795682l},
			{8459907238438370079l,1519789039126534832l,-2035107807477173226l,3816022748230229326l},
			{3887449647047508921l,-5071238611879201387l,-9056877706732772044l,7257424282320054967l},
			{-3689361134844105428l,-1652035903788212807l,-5678787210258487919l,3153119788360741387l},
			{4248414965141326451l,6361015122783860869l,3471202313594733197l,7929111617552352379l},
			{3108592468623139807l,6850454098958038162l,666488131265836955l,7434101070482080010l},
			{-2556526916032665197l,-5508789291651043435l,-2552574140319635081l,2758291679676250002l},
			{-3052091792220530890l,-2786169099023519050l,211948365440443823l,9071228901428251758l},
			{2890870272026710913l,5499934362515785867l,8721922406783378720l,-8924269699542370222l},
			{5119261611986520476l,8392003691746348635l,-3338566045085909282l,370389079362891571l},
			{5085849200093418870l,5669281192545588970l,7718961480766921945l,-8457537590086088791l},
			{7305598656910266337l,-4936846267603810657l,-7303935816077388480l,1154866819176387832l},
			{-6634550021954870297l,-8201199841264453441l,6258762061916487447l,-2316135937579289646l},
			{-5905273130362229562l,-8396554546190924091l,379377367827344747l,4942467654764266568l},
			{1689351667542927594l,-7565022699928843811l,4670103670608929428l,-2144832772429655767l},
			{-9020408898987228651l,-6987180573196984257l,3504335334332798865l,8307038047407724648l},
			{-2592685388137285564l,5266994796812108149l,8120331796265279088l,1875620424083428907l},
			{2781949724785118471l,-1543885693216603934l,-6090295900670015723l,684118725351738342l},
			{-4319739559824801388l,-4719489818262164517l,-5466819355568081307l,7349132480078062542l},
			{-38327136771764737l,-5698693131645964697l,-3429468079004339951l,-879269640528364694l},
			{7148474540470716898l,5088137810725809422l,-649036897672244677l,-5766880660211910883l},
			{-463473072534511557l,4767541589051656531l,3624064115107195139l,988358050907415192l},
			{-8625107089899963028l,7882177611918115731l,3517500757736506933l,-1790534046703291586l},
			{-6877006086810296128l,7725537435255923776l,-5481329403778105919l,9207261715359855992l},
			{-6431938477235725768l,4273900080746417763l,-3270099949925619701l,3424354266434937548l},
			{-4639773756615158037l,8031848664797226450l,8458495484514683955l,7577112034789137793l},
			{7714910923919933390l,502193931928505189l,-62033500708041525l,-6539838019002003381l},
			{8423446991472224793l,-5499447624900760763l,-1854021332798211418l,-548292057254499888l},
			{-7181416766608158240l,-402065587288851084l,-545350888481035901l,7113927039918207814l},
			{3753248783329603650l,-9148832285800776371l,-8674339004262738244l,-7966266811787151312l},
			{-1953720404619440007l,1251469324302963008l,6236074185991020103l,-5764379430655734706l},
			{-6076642850199113062l,-7256350277531746170l,-8682274078091919983l,-4932155705956328358l},
			{4055469499369434908l,8020018141234293104l,-2772048833603152577l,5334981262124345913l},
			{5320564435511043737l,713996666603391384l,-4541820135709101339l,1898406316652383463l},
			{8311425645064973677l,4016291407926770925l,4712528071507362440l,2856404197600998628l},
			{5287913521912852345l,-7699150628451876892l,3361277519473870687l,7089379893338823841l},
			{6867698177657239698l,7499522372215464135l,3472348929144443546l,-3738981337763375033l},
			{4062507916586861581l,1437783575551436730l,-6561373978987348108l,3366859094997961980l},
			{2456860852290847766l,7166093742256260763l,2843427393689008218l,370389556449839345l},
			{-7770044954329533339l,4865159221345342151l,-9000889213781817872l,-7755879487666316428l},
			{7674541322518285226l,-3052529936060253061l,1730133140343220236l,7694154327541802154l},
			{8196716146201967118l,5129367467845310214l,3450996637549455586l,-7618432512261009079l},
			{-1614842720156984598l,-222592974180863588l,-8593736292543897467l,-6986730690707981688l},
			{8048763007525975329l,-263498314890794941l,148879275039110356l,-3779336254198262170l},
			{-1285929739779316836l,-7485086178327828381l,5033959343992028147l,-5144653685765256072l},
			{-7318165560055915353l,2472980706727705723l,7561772153816340264l,949630077601022655l},
			{-4280876385229786853l,2190784491046890853l,4390520350933671297l,189687877034378511l},
			{3270482549545640896l,642671217609543569l,-836887766394547584l,-1193066230533564288l},
			{1543575172653987787l,6465705538306299263l,-4502833851335929606l,119580484369394618l},
			{8293406975694725234l,722818078994499092l,1526858405643920419l,-5206788675199094028l},
			{2106562739574585819l,-4832289617120803185l,5611270088778821180l,4956012184127980969l},
			{-4006175873886953874l,-7836376725115908826l,5363718652267283707l,-4977301015772622696l},
			{5940743068240373022l,1288259835254647939l,-6561090455069854035l,-1851918012411668042l},
			{1696744425596525880l,17009402361042886l,7658036241384147374l,-4083753501706539581l},
			{-1479281633982229020l,-1478629444947024159l,-4366295181782840294l,-8298273232662356449l},
			{9023367621486876655l,-1264957064068649864l,7505700856537547335l,7858103625111394666l},
			{2497130602694961453l,-7210762802049622352l,1280126840760748247l,-9184112701093866057l},
			{-7819833352013521389l,4914410211145986393l,-2170413329199315097l,-6908719597371201145l},
			{6008655717638138272l,-3660469564219898028l,-7216817435498133524l,56514037805230414l},
			{3081026092695136476l,-3177109744083792438l,-5588985751106317351l,3715088803878926837l},
			{-6420304272202925772l,3554117112526614521l,-9067251516797327272l,-6108692785538056884l},
			{-8244284341244977345l,2242765649837546201l,-8691149681737521951l,2548284609653964378l},
			{-6522075466460187988l,4208163120931890477l,-7343019384944783373l,-1203080588412658609l},
			{-139266443892915546l,8063389101922239374l,1703337442382739718l,-7459624211611432018l},
		};
	}
	
	public static abstract class Robot implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 497453031907704510L;
		
		public State state;
		public long totalTime;
		public long timer;
		public long timeLimit;
		public int pos;
		public Position newP;

		public Robot(int pos) {
			this.pos = pos;
			state = State.Survival;
			timeLimit = (long) (5000*0.95);
			totalTime = 0;
		}
		
		public abstract Position nextMove(TronMap m, ArrayList<Position> p, boolean edgeCase);
		
		public Position move(TronMap m) {
			timer = System.currentTimeMillis();
			
			ArrayList<Position> p = m.getAllMoves(pos);
			if (p.size() > 0) {
				if (m.H[pos].y == 0 || m.H[pos].y == m.m.length-1) {//edge case
					newP = nextMove(m, p, true);
				} else {
					newP = nextMove(m, p, false);
				}
			} else{
				newP = m.dirToPos((byte) 2,pos);
			}
			
			m.oldH[pos] = m.H[pos];
			//currentPos = newP;
			totalTime += (System.currentTimeMillis()-timer); 
			return newP;
		}
		public abstract String toString();
		
		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}
	}
	
	public static class TptValue implements java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2076299711980150626L;
		public short bestvalue, alpha, beta, d, expireDepth, flag;
		public byte bestPx, bestPy, bestP2x, bestP2y,rootMoveX,rootMoveY;
		public boolean old;
		
		public long hash;
		public boolean checked;
		
		//private final 
		
		public TptValue() {
			super();
			this.bestvalue = 0;
			this.alpha = 0;
			this.beta = 0;
			this.bestPx = 0;
			this.bestPy = 0;
			this.d = 0;
			this.bestP2x = 0;
			this.bestP2y = 0;
			this.flag = 0;
			this.expireDepth = 0;
			this.old= false;
			this.checked= false;
		}

		public TptValue(short bestvalue, short alpha, short beta, byte bestPx,
				byte bestPy, short d, short expireDepth, byte bestP2x, byte bestP2y, short flag, byte rootMoveX, byte rootMoveY) {
			super();
			this.bestvalue = bestvalue;
			this.alpha = alpha;
			this.beta = beta;
			this.bestPx = bestPx;
			this.bestPy = bestPy;
			this.d = d;
			this.expireDepth = expireDepth;
			this.bestP2x = bestP2x;
			this.bestP2y = bestP2y;
			this.flag = flag;
			this.rootMoveX = rootMoveX;
			this.rootMoveY = rootMoveY;
			this.old= false;
			this.checked= false;
		}
		
		public TptValue clone() {
			return new TptValue(bestvalue, alpha, beta, bestPx, bestPy, d, expireDepth, bestP2x, bestP2y, flag, rootMoveX, rootMoveY);		
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "TptEntry [bestvalue=" + bestvalue + ", alpha=" + alpha + ", beta=" + beta
					+ ", bestPx=" + bestPx + ", bestPy=" + bestPy
					
					+ ", bestP2x=" + bestP2x
					+ ", bestP2y=" + bestP2y + ",   d=" + d + ", flag=" + flag + "]";
		}
		
		
	}
	
	public static class Holder implements Comparable<Holder> {
		public int x;
		public int y;

		public Holder(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Holder o) {
			if (this.y == o.y)
				return 0;
			else if (this.y > o.y)
				return 1;
			else
				return -1;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(x);
			sb.append(':');
			sb.append(y);
			return sb.toString();
		}
	}
	
	public static class Strat extends Robot {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static enum Strategy {RANDOM, RANDOMPATH, WALLHUG, OFFENSIVE, DEFENSIVE, MIXED, COMBINED};
		public Strategy strat;
		public boolean doCV;
		public boolean mixed;
		
		public Strat(int pos, Strategy strat) {
			super(pos);
			this.strat = strat;
			state = State.Battle;
			doCV = true;
			mixed = false;
		}

		@Override
		public Position nextMove(TronMap m, ArrayList<Position> p, boolean edgeCase) {
			
			//WALLHUG never does battle mode
			if (strat == Strategy.WALLHUG) {
				state = State.Survival;
			}
			if (strat == Strategy.MIXED) {
				strat = Strategy.OFFENSIVE;
				mixed = true;
			}		
			if (mixed) {
				Random r = new Random();
				if (r.nextDouble() > 0.75) {
					//pick random strat
					int rInt = r.nextInt(3);
					if (rInt == 0) {
						strat = Strategy.WALLHUG;
					} else if (rInt == 1) {
						strat = Strategy.OFFENSIVE;
					} else if (rInt == 2) {
						//strat = Strategy.DEFENSIVE;
					}
				}
			}
			if (state == State.Battle) {
				m.hValue(pos, 1);
				if (m.state == State.Survival) {
					state = State.Survival;
					if(m.outputLevel > 0) {System.out.println("\t\t\tendgame mode");}
				}
			}

			Position po = null;
			switch (state) {
			case Battle: {
				switch (strat) {
				case RANDOM: {
					Random r = new Random();
					Position rdir;
					ArrayList<Position> moves;
					moves = m.getAllMoves(pos);
					//rdir = 0;
					if (moves.size() == 0) {
						return m.dirToPos((byte) 1,pos);
					}
					rdir = moves.get(r.nextInt(moves.size()));
					int loop = 0;
					while(!m.canMove(rdir,pos)) {
						if (moves.size() == 0) {
							return m.dirToPos((byte) 1,pos);
						}
						rdir = moves.get(r.nextInt(moves.size()));
						loop++;
						if (loop > 100) {
							loop = 101;
						}
					}
					return rdir;
				}
				case RANDOMPATH: {
					Random r = new Random();
					Position rdir;
					//20% chance to switch direction
					if ((m.oldH[pos] != null && (r.nextDouble() < 0.80))) {
						byte dir = m.posToDir(m.oldH[pos], m.H[pos]);
						rdir = m.dirToPos(dir, m.H[pos]);
						if (rdir != null && m.canMove(rdir,pos)) {
							return rdir;
						}
					}
					
					rdir = p.get(r.nextInt(p.size()));
					int loop = 0;
					while(!m.canMove(rdir,pos)) {
						if (p.size() == 0) {
							return m.dirToPos((byte) 1,pos);
						}
						rdir = p.get(r.nextInt(p.size()));
						loop++;
						if (loop > 100) {
							loop = 101;
						}
					}
					return rdir;
				}
				case WALLHUG: {
					//WALLHUG never does battle mode
					return po;
				}
				case OFFENSIVE: {
					//find enemy and try to hug him
					DijkstraPath dp = new DijkstraPath(m.getCopy(),m.H[pos],m.H[(pos+1)%2]);
					ArrayList<Position> a = dp.getPath();
//					if (m.m[9][22] == 3) {
//						int x = 0; x++;
//					}
					if (a.size() > 0) {
						if (m.canMove(a.get(0),pos)) {
							po = a.get(0);
						} else {		//can't get to the enemy, try to get to the closest spot next to him
							po = p.get(0);
							ArrayList<Position> enemyMoves = m.getAllMoves(m.H[(pos+1)%2]);	
							ArrayList<Holder> h = new ArrayList<Holder>();
							ArrayList<ArrayList<Position>> enemyPaths = new ArrayList<ArrayList<Position>>();
							for (int i = 0; i < enemyMoves.size(); i++) {
								DijkstraPath dp2 = new DijkstraPath(m.getCopy(),m.H[pos],enemyMoves.get(i));
								h.add(new Holder(i, dp2.shortestPath()));
								enemyPaths.add(dp2.getPath());
							}
							if (h.size() > 0) {
								Collections.sort(h);
								for (int i = 0; i < h.size(); i++) {
									if ((enemyPaths.size() > 0 && enemyPaths.get(0).size() > 0) && m.canMove(enemyPaths.get(h.get(0).x).get(0),pos)) {
										po = enemyPaths.get(h.get(0).x).get(0);
										break;
									}
								}							
							} else {
								po = p.get(0);
							}					
							
						}
					} else {
						po = p.get(0);
					}
					return po;
				}
				case DEFENSIVE: {
					//find shortest path for every move, take the longest one
					ArrayList<Holder> h = new ArrayList<Holder>();
					for (int i = 0; i < p.size(); i++) {
						DijkstraPath dp = new DijkstraPath(m.getCopy(),p.get(i),m.H[(pos+1)%2]);
						h.add(new Holder(i,dp.shortestPath()));
					}
					if (h.size() > 0) {
						Collections.sort(h);
						Collections.reverse(h);
						po = p.get(h.get(0).x);
					} else {
						po = p.get(0);
					}
					return po;
				}
				}
				
			}
			case Survival: {
				ArrayList<Holder> h = new ArrayList<Holder>();
				for (int i = 0; i < p.size(); i++) {
					ArrayList<Position> nMov = m.getAllMoves(p.get(i));
					if (nMov.size() >0) {
						h.add(new Holder(i, nMov.size()));
					}
				}
				Position newP = null;
				if (h.size() > 0) {
					Collections.sort(h);
					newP = p.get(h.get(0).x);
				} else { //no good moves left
					if (p.size() > 0) {
						newP = p.get(0);
					} else {
						newP = m.dirToPos((byte) 1,pos);
					}
				}

				if (doCV) {
					//are we at a cut vertex?			
					if (m.oldH[pos] != null) {
						int heading = (int) (Math.round(m.heading(m.oldH[pos], m.H[pos])/90.0)*90);
						byte dir = m.headingToDir(heading);
						Position pos2 = m.dirToPos(dir,m.H[pos]);
						Position p05 = null;
						Position p15 = null;
						if (pos2 != null) {
							p05 = m.dirToPos((byte)(util.negMod(dir+1-1,4)+1),pos2);
							p15 = m.dirToPos((byte)(util.negMod(dir-1-1,4)+1),pos2);
						}
						if (!m.canMove(p05, newP) && !m.canMove(p15, newP)) {
							//yes, do multi BFS until all but 1 terminates
							ArrayList<BFS> bfs = new ArrayList<BFS>();

							ArrayList<Position> nMov = m.getAllMoves(m.H[pos]);
							if (nMov.size() > 1) {
								for (int i = 0; i < nMov.size(); i++) {
									bfs.add(new BFS(m.getCopy(),nMov.get(i),true));
								}
								while (bfs.size() > 1) {
									for (int i = 0; i < bfs.size(); i++) {
										if (!bfs.get(i).next()) {
											if(bfs.size() ==1) {
												break;
											}
											bfs.remove(i);
											i--;
										} 
									}
								}
								return bfs.get(0).start;
							} else if (nMov.size() > 0) {
								return nMov.get(0);
							}
						}
					}					
				}
				return newP;
			}
			default: return m.dirToPos((byte) 1,pos);
			}
		}

		@Override
		public String toString() {
			if (!mixed) {
				return "Strat:"+strat.toString();
			} else {
				return "Strat:MIXED";	
			}		
		}

	}
	
	public static class Brute extends Robot {
		public ArrayList<Position> p;
		public Brute(int pos) {
			super(pos);
			p = new ArrayList<Position>();
		}
		

		@Override
		public Position nextMove(TronMap m, ArrayList<Position> p, boolean edgeCase) {
			this.p = p;
			return null;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Brute";
		}

	}
	
	public static class MegaTron extends Robot implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2534268330545989856L; 

		//HashMap<Integer, HashMap<Long,short[]>> transPosTable;
		HashMap<Long,short[]> transPosTableV18;
		public HashMap<Long,TptValue> transPosTable;
		public TptValue[] transPosTableStore;
		@SuppressWarnings("unchecked")
		public LinkedHashSet cache;
		public LinkedHashMap<TronMap,Short> hCache;
		public int maxdepth;
		public int finalDepth;
		public int endDepth;
		public int startDepth;
		public short bestAlpha;
		public final short negInf = (short) -5000;
		public final short posInf = (short) 5000;
		public final short hEXACT =  0x04;
		public final short hLOWER =  0x02;
		public final short hUPPER  =  0x01;
		public final int maxTableSize = 140000;
		public short[][] pvMap; 
		public short[][] hMap;
		public short[][] orig_hmap;
		public short[][] orig_dmap;
		public short hValue;
		public String criticalError;

		public long[] timers;
		public int oldDepth;
		public Tree<TronMap> tree;
		public Tree<TronMap> oldTree;
		public Tree<TronMap> oldTree2;
		public int variation;
		public long randomSpace;	
		private int oldCacheSize;
		private int curCacheSize;
		private int currentSearchDepth;
		private int skippedInterior;
		private int notSkippedInterior;
		public int moves;
//		v 19:	Added GifWriter, upgraded TPT to use an TptValue for storage, added the option of trimming the TPT
//		v 18:	new code from laptop, mainly heuristic speed improvements
//		v 17:	01/09/2012
//		v 16:	upgraded TPT, evaluation WIP
//		v 15:	alpha/beta replacement: WIP
		public final int v = 19;

		
		//stats
		public int cacheSize;
		public int cacheHits;
		public int numPositionsVisited;
		public int numEval; 
		public int numCacheHits;
		public int numCHexact;
		public int numCHalpha;
		public int numCHbeta;
		public int numCHcutoff;
		public int numCHwaste;

		//settings
		public boolean doCV;
		public boolean doBattle;
		public boolean doDijkstra;
		public boolean doAssumeFirstLeft;
		public boolean firstMove;
		public boolean doIterativeDeepening;
		public boolean doAlphaBeta;
		public boolean doReuseTree;
		public boolean doTimeLimit;
		public boolean doSmallCache;
		public boolean doCache;
		public boolean doTrimming;
		public boolean doSkipEndgame;
		public boolean doHCache;
		public boolean doRandomAtEqual;
		public boolean doWCache;
		public boolean doAspirationSearch;
		public boolean doDynamicWindow;
		public int window = 20;
		public boolean doSingleIncrement;
		public boolean doAltChase;
		public boolean doRandomScout;
		public boolean doTrimInterior;
		public static boolean doTrimTPT;
		public boolean doWarnsdorff;
		

		private TronMap orig_map;

		public MegaTron(int pos) {
			super(pos);
			ArrayList<ArrayList<Integer>> params = new ArrayList<ArrayList<Integer>>();

			ArrayList<Integer> par;

			maxdepth = 6;
			firstMove = true;
			timers = new long[5];
			numCacheHits = 0;
			numPositionsVisited = 0;
			randomSpace = 1;
			par = new ArrayList<Integer>();
			params.add(0,par);
			par.add(0);		//during Survival: look forward 1 move and enter the largest chambers 
			par.add(1);		//
			oldCacheSize = 0;
			curCacheSize = 0;
			criticalError = "";

			doCV = true;
			doDijkstra = true;
			doAssumeFirstLeft = false;
			doReuseTree = false;
			doTrimming = false;
			//doBattle = true;
			//doLongestPath = false;
			//doRandomPlay = false;
			//doTopLevelCache = false;
			doIterativeDeepening = true;
			doSmallCache = false;
			doTimeLimit = true;
			doRandomAtEqual = false;

			doAlphaBeta = true;
			doSkipEndgame = false;
			doCache = false;
			doHCache = false;
			doWCache = true;
			doAspirationSearch = false;
			doDynamicWindow = false;
			doSingleIncrement = false;
			doAltChase = false;
			doRandomScout = false;
			doTrimInterior = false;
			doTrimTPT = true;
			doWarnsdorff = true;


			state = State.Battle;
			cache = new LinkedHashSet<TronMap>();
			hCache = new LinkedHashMap<TronMap,Short>();
			//smallCache = new LinkedHashSet<Integer>();
			transPosTable = new HashMap<Long,TptValue>();
			transPosTableV18 = new HashMap<Long,short[]>();
		}
		public MegaTron(int pos, int depth) {
			this(pos);
			this.maxdepth =  depth;
		} 

		@Override
		public Position nextMove(TronMap m, ArrayList<Position> p, boolean edgeCase) {

			//if (!doBattle) {
			state = State.Battle;
			//}
			Position nextMove;
			hValue = m.hValue(pos, 1);

			//battle mode until BFS can't find enemy's head
			if (state == State.Battle) {
				int[] t = new int[3];
				boolean[] endgame = {false};
				//short hv = 0;
				orig_hmap = m.mapVoronoi(1,t,endgame);
				orig_map = m;

				BFS bfs = new BFS(m,m.H[0],true);
				bfs.start2 = m.H[1];			
				orig_dmap = bfs.distance();
				if (m.state == State.Survival) {
					state = State.Survival;
					if(m.outputLevel > 0) {System.out.println("\t\t\tendgame mode");}
				}
			}

			switch (state) {
			case Battle: {
				nextMove = nextBattleMove(m,p,edgeCase);
				break;
			}
			case Survival: {
				nextMove = nextSurvivalMove(m,p,edgeCase,false);	
				break;		
			}
			default: {
				nextMove = p.get(0);
				break;
			}
			}
			this.trimTPT(nextMove);
			cacheSize = transPosTable.size();
			return nextMove;
		}

		private Position nextBattleMove(TronMap m, ArrayList<Position> p, boolean edgeCase) {

			if (doAssumeFirstLeft) {
				if (pos == 0 && firstMove) {
					firstMove = false;
					return m.dirToPos((byte) 4, pos);
				}
			}
			//int startDepth;
			if (doIterativeDeepening) {
				startDepth = 2;
				if (doTimeLimit) {
					endDepth = 1001;
				} else {
					endDepth = maxdepth;
				}
			} else {
				startDepth = maxdepth;
				endDepth = maxdepth;
			}
			pvMap = m.getBigCopy();
			util.fill(pvMap, (short) 0);
			hMap = m.getBigCopy();
			util.fill(hMap, (short) 0);
			Position po = null;
			String output = "";
			short bestLocalAlpha = 0;
			short prevBestLocalAlpha = 0;
			int failLow = 0;
			int failHigh = 0;
			int failNot = 0;
			String t = "";
			int tempRandomSpace = 0;
			int depthIncrement;
			if (doSingleIncrement) {
				startDepth -= 1;
				depthIncrement = 2;
			} else {
				depthIncrement = 2;
			}

			this.moves += 2;

			criticalError = "";

			for (int d = startDepth; d <= endDepth; d=d+depthIncrement) {
				currentSearchDepth = d;
				//for (int d = startDepth; d <= endDepth; d=d+1) {
				if (doSmallCache) {
					cache = new LinkedHashSet<Integer>();
					cacheHits = 0;
				} else {
					cache = new LinkedHashSet<TronMap>();
					cacheHits = 0;
				}

				tree = new Tree<TronMap>(m.getCopy(),pos);

				if (d == 7){
					int x = 0;x++;
				}
				if (doAlphaBeta) {
					//if (d%2==0) {
					Position rootMove = new Position(-1,-1);
					try {
						tree.root.alpha = negaMaxAB2(tree, tree.root, d, cache,negInf, posInf,pos,rootMove);
					} catch (Exception e) {
						String msg = e.getMessage();
						if (!msg.equals("timeout")) {
							e.printStackTrace();
						}

					}
					//} else {
					//	tree.root.alpha = negaMaxAB2(tree, tree.root, d, cache,(short)(negInf*-1), (short)(posInf*-1),pos);
					//}

				} else {
					tree.root.alpha = negaMax(tree, tree.root, d, cache, pos);
				}

				finalDepth = d;
				//if time limit expired take last round's best go
				if (((System.currentTimeMillis() - timer > timeLimit) && doTimeLimit)) {					
					if (po == null) {	//didn't complete 1 ply
						po = p.get(0);
						criticalError += "TIMEOUT: MT found no moves, using default\n";
						System.out.println("MT found no moves, using default");
					}
					break;
				}

				//the current ply completed, so get the best move
				Collections.sort(tree.root.children);
				ArrayList<Position> equalmoves = new ArrayList<Position>();
				if (tree.root.children.size() > 1 && doWCache == false) {
					equalmoves.add(tree.root.children.get(0).element.H[pos]);
					for (int i = 1; i < tree.root.children.size(); i++) {
						if (tree.root.children.get(0).alpha == tree.root.children.get(i).alpha) {
							equalmoves.add(tree.root.children.get(i).element.H[pos]);
						}
					}
					bestAlpha = tree.root.children.get(0).alpha;			
				} else {
					if (v >= 19) {
						if (equalmoves.size() == 0) {
							TptValue hData = new TptValue();
							hData.flag = hEXACT;
							TptValue[] hDataHolder = {hData};
							if (this.lookup(m, hDataHolder)) {
								//if (pos == 0) {
								hData = hDataHolder[0];
								prevBestLocalAlpha = bestLocalAlpha;
								bestLocalAlpha = hData.bestvalue;
								oldCacheSize = curCacheSize;
								curCacheSize = transPosTable.size();
								if (prevBestLocalAlpha != 5000 && m.outputLevel > 1) {
									System.out.println(d+"\t"+prevBestLocalAlpha+"\t"+bestLocalAlpha+"\t"+Math.abs(prevBestLocalAlpha-bestLocalAlpha)+"\tcache incr:"+(curCacheSize-oldCacheSize));
								}
								//}
								if (hData.bestPx == -1) {
									if (hData.bestvalue == negInf) {		//game over for me: best move is certain death
										equalmoves.add(this.nextSurvivalMove(m,p,edgeCase,true));
									} else {
										criticalError += "INVALID POSITION RECALLED FROM CACHE at d = "+d+"\n";
										System.out.println(criticalError);
									}
								} else 
									equalmoves.add(new Position(hData.bestPx,hData.bestPy));
								//if (hData[8] != 4) {
								//}
								bestAlpha = (short) (hData.bestvalue*-1);
							} else {
								criticalError += "NOTHING FOUND IN CACHE at d = "+d+"\n";
								System.out.println(criticalError);
								this.lookup(m, hDataHolder);
							}
						}
					}
				}
				if (equalmoves.size() > 1) {	//take move that is closest to enemy
					//System.out.println("equalmoves found: "+equalmoves.size());
					if (doRandomAtEqual) {
						tempRandomSpace = equalmoves.size();
						Random r = new Random();
						po = equalmoves.get(r.nextInt(equalmoves.size()));
					} else {
						int minDist = 100000;
						int minIdx = 0;
						for (int i = 0; i < equalmoves.size(); i++) {
							int dm = m.distManh(equalmoves.get(0), m.H[(pos+1)%2]);
							if (dm < minDist) {
								minDist = dm;
								minIdx = i;
							}
						}
						po = equalmoves.get(minIdx);
					}
				} else if (equalmoves.size() == 1) {
					po = equalmoves.get(0);
				} else {
					//no moves found AT ALL, serious problem should be reported
					if (po == null) {	//didn't complete 1 ply
						criticalError += "NOCACHE: MT found no moves, using default\n";
						po = p.get(0);
					}
				}
				if (bestAlpha == negInf || bestAlpha == posInf) {	//game over: we've won/lost
					if (bestAlpha == negInf) {
						t += "   \t(Bot"+(pos+1)+") wins";
					} else {
						t += "   \t(Bot"+((pos+1)%2+1)+") wins";
					}
					break;
				}
				oldTree2 = tree;
				//System.out.println("\t\t\t\t\t\tpo:"+po.toString());
				if (m.outputLevel > 2) {
					System.out.println("skippedInterior:    "+skippedInterior);
					System.out.println("notSkippedInterior: "+notSkippedInterior);
				}
				skippedInterior = 0;
				notSkippedInterior = 0;
			}
			if (doAspirationSearch && m.outputLevel > 1) {
				System.out.println(failLow+"\t"+failHigh+"\t"+failNot+"\t"+finalDepth+"\tcache size:"+transPosTable.size());
			}

			ArrayList<Position> equalmoves2 = new ArrayList<Position>();
			if (doRandomAtEqual) {
				ArrayList<Position> moves = m.getAllMoves(pos);
				ArrayList<Holder> alphas = new ArrayList<Holder>();
				for (int i = 0; i < moves.size(); i++) {
					TronMap newMap = m.getCopy();
					if (newMap.move(moves.get(i),pos)) {
						if (v >= 19) {
							TptValue[] hDataHolder = new TptValue[1];
							if (this.lookup(newMap, hDataHolder)) {
								if ((hDataHolder[0].bestPx != -1) && ((hDataHolder[0].flag & hEXACT) == hEXACT)) {	 	
									alphas.add(new Holder(i, hDataHolder[0].bestvalue));
								}
							}
						}
					}
				}
				if (alphas.size() > 0) {
					Collections.sort(alphas);
					Collections.reverse(alphas);
					for (int i = 0; i < alphas.size(); i++) {
						if (alphas.get(i).y == alphas.get(0).y) {
							equalmoves2.add(moves.get(alphas.get(i).x));
						}
					}
					bestAlpha = (short) alphas.get(0).y;

					tempRandomSpace = equalmoves2.size();
					Random r = new Random();
					po = equalmoves2.get(r.nextInt(equalmoves2.size()));
				}
			}

			if (doRandomAtEqual) {
				randomSpace *= tempRandomSpace;
				t += "\trandomSpace: "+tempRandomSpace+"\t";
			}
			
			if (m.outputLevel > 2) {
				System.out.println(output);
				if (oldTree2 != null) {
					System.out.println(oldTree2.toString(4));
				} else if (tree != null) {
					System.out.println(tree.toString(4));
				}
				System.out.println(m.toBigString(pvMap));
				System.out.println(m.toBigString(hMap));
			}
			if (doAltChase) {
				if (bestAlpha > -1*m.m.length*m.m[0].length/5) {
					t += "\tCHASING (bestA = "+bestAlpha+")\t";
					Robot b = new Strat(0, Strat.Strategy.OFFENSIVE);
					TronMap newMap = m.getCopy();
					po = b.nextMove(newMap, p, edgeCase);
				}
			}
			if (m.outputLevel > 1) {
				//			for (int i = 0; i < tree.root.children.size(); i++) {
				//				t += ","+tree.root.children.get(i).alpha;
				//			}
				output = pos+" finaldepth:   "+finalDepth+"\tbestAlpha: "+bestAlpha/*+" chosen alpha: "+tree.root.children.get(0).alpha*/+" other: "+t;
				System.out.println(output);

			} 

			if (po == null) {
				System.out.println("po == null");
			}
			//tree = null;
			//oldTree2 = null;
			return po;
		}
		
		boolean store(TronMap map, int d, short bestvalue, short alpha, short beta, Position bestP, Position bestP2, short flag, Position rootMove) {	
			boolean replace = false;
			boolean newEntry = false;
			if (transPosTable.containsKey(map.hashCodeLong())) {
				TptValue val = transPosTable.get(map.hashCodeLong());
				if (val.d < d) {
					replace = true;
				} else if (val.d == d && ((val.flag&flag) == 0)) {	//just update
					if (val.flag > flag) {
						if (val.bestPx != -1)	val.bestPx = bestP.x;
						if (val.bestPy != -1)	val.bestPy = bestP.y;
						if (val.bestP2x != -1)	val.bestP2x = bestP2.x;
						if (val.bestP2y != -1)	val.bestP2y = bestP2.y;
					}
					val.flag = (short) (val.flag|flag);
					if (flag == hEXACT) {
					} else if (flag == hLOWER) {
						val.alpha = alpha;
					} else {
						val.beta = beta;
					}
					transPosTable.put(map.hashCodeLong(), val);	
				}
			} else {
				newEntry = true;
			}

			if (replace || (newEntry && transPosTable.size() <= maxTableSize)) {
				TptValue values = new TptValue(bestvalue, alpha, beta, (byte)bestP.x, (byte)bestP.y, (byte)d, (byte)(currentSearchDepth-d+moves), (byte)bestP2.x, (byte)bestP2.y, (byte)flag, (byte)rootMove.x, (byte)rootMove.y);
				transPosTable.put(map.hashCodeLong(), values);		
			}
			if (newEntry && transPosTable.size() > maxTableSize) {
				return false;
			}

			return true;
		}

		boolean lookup(TronMap map, TptValue[] hData) {
			if (transPosTable.containsKey(map.hashCodeLong())) {
				TptValue hStore = transPosTable.get(map.hashCodeLong());
				hData[0] = hStore.clone();
				if (hStore.old) {
					//System.out.println("OLD MARK FAIL");
				}
				return true;
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		public short negaMaxAB2(Tree<TronMap> tree, Node<TronMap> c, int depth, LinkedHashSet cache, short alpha, short beta, int MaxPos, Position rootMove) throws Exception {
			if ((System.currentTimeMillis() - timer > timeLimit) && doTimeLimit) {
				throw new Exception("timeout");
			}

			TronMap currentM = c.element;
			long temp = System.nanoTime();
			Position suggestedP = null; 
			Position suggestedP2 = null;

			if (doWCache) {
				short val = 0;
				temp = System.nanoTime(); 

				if (v >= 19) {
				TptValue hData = new TptValue();
				hData.alpha = alpha;
				hData.beta = beta;
				TptValue[] hDataHolder = {hData};
				if (this.lookup(currentM, hDataHolder)) {
					timers[1] += System.nanoTime()-temp;
					hData = hDataHolder[0];

					if (hData.d >= depth) {
						numCacheHits++;
						if ((hData.flag & hEXACT) == hEXACT) {
							numCHexact++;
							val = hData.bestvalue;
							return val;
						}
						if (((hData.flag & hLOWER) == hLOWER) && (hData.alpha > alpha)) {
							numCHalpha++;
							alpha = hData.alpha;
						} else if (((hData.flag & hUPPER) == hUPPER) && (hData.beta < beta)) {
							numCHbeta++;
							beta = hData.beta;
						} else {
							numCHwaste++;
						}
						if (alpha >= beta)
						{
							numCHwaste--;
							numCHcutoff++;
							val = hData.bestvalue;
							return val;
						}
					}
					//				if (hData[5] >= depth) {
					//					v = hData[0];
					//					return v;
					//				} else {	//suggest move
					//System.out.print("");
					suggestedP = new Position(hData.bestPx,hData.bestPy);
					suggestedP2 = new Position(hData.bestP2x,hData.bestP2y);
					//}
				}
				}
			} 
			Position bestP = new Position(-1,-1);
			Position bestP2 = new Position(-1,-1);
			//short hf = hLOWER;

			ArrayList<Position> moves = currentM.getAllMoves(c.pos);
			timers[0] += System.nanoTime()-temp;
			if (moves.size() == 0) {	//this position has lost
				return negInf;
			}		

			ArrayList<Position> enemyMoves = currentM.getAllMoves((c.pos+1)%2);
			if (enemyMoves.size() == 0) {	//this position wins
				return posInf;
			}

			if (doSkipEndgame) {
				int type = doDijkstra ? 1 : 2;
				short val = 0;
				temp = System.nanoTime();
				if (doHCache) {
					if (hCache.containsKey(currentM)) {
						val = hCache.get(currentM);
						timers[1] += System.nanoTime()-temp;
						numCacheHits++;
					} else {
						val = currentM.hValue(MaxPos, type);
						timers[2] += System.nanoTime()-temp;
						temp = System.nanoTime();
						hCache.put(currentM, val);
						timers[1] += System.nanoTime()-temp;
					}
				} else {
					val = currentM.hValue(MaxPos, type);
					timers[2] += System.nanoTime()-temp;
				}
				if (val >= 1000 || val <= -1000) {
					if (val >= 1000) {
						return posInf;
					} else {
						return negInf;
					}
				}
			}
			if (depth > 0) {
				pvMap[currentM.H[c.pos].y][currentM.H[c.pos].x]++;
				pvMap[currentM.H[(c.pos+1)%2].y][currentM.H[(c.pos+1)%2].x]++;
			}
			if (currentSearchDepth - depth == 1) {
				rootMove = new Position(currentM.H[(c.pos+1)%2]);
			}
			if (depth <= 0) {
				int type = doDijkstra ? 1 : 2;
				temp = System.nanoTime();

				short val = 0;
				temp = System.nanoTime();
				if (doWCache) {
					numEval++;
					val = currentM.hValue(MaxPos, type);
					timers[2] += System.nanoTime()-temp;


					//				ArrayList<Position> pv = this.getPV(currentM.getCopy(), pos);
					//				for (Position position : pv) {
					//					pvMap[position.y][position.x]++;
					//				}
					hMap[currentM.H[c.pos].y][currentM.H[c.pos].x]++;
					temp = System.nanoTime();
					if (v >= 19) {
					if(v <= alpha)
						this.store(currentM, 0, negInf,  val, beta, bestP, bestP2, hLOWER, rootMove);
					else if(v >= beta)
						this.store(currentM, 0, negInf, alpha, val, bestP, bestP2, hUPPER, rootMove);
					else                           // a true minimax value
						this.store(currentM, 0, val  , alpha, beta, bestP, bestP2, hEXACT, rootMove);
					}
					//hCache.put(currentM, v);
					timers[1] += System.nanoTime()-temp;
				} else if (doHCache) {
					if (hCache.containsKey(currentM)) {
						val = hCache.get(currentM);
						timers[1] += System.nanoTime()-temp;
						numCacheHits++;
					} else {
						val = currentM.hValue(MaxPos, type);
						timers[2] += System.nanoTime()-temp;
						temp = System.nanoTime();
						hCache.put(currentM, val);
						timers[1] += System.nanoTime()-temp;
					}
				} else {
					val = currentM.hValue(MaxPos, type);
					timers[2] += System.nanoTime()-temp;
				}
				c.alpha = val;
				return val;
			}
			//int alpha = Integer.MIN_VALUE;

			short localalpha = alpha;
			short bestvalue = negInf;
			short value;
			Position currentP = new Position(currentM.H[c.pos]);
			
			if (v >= 18) {

			int moveOrdering;

			//moveOrdering = 0; //None
			//moveOrdering = 1; //Random
			//moveOrdering = 2; //TPT
			moveOrdering = 2;

			//moves.add(moves.remove(0));
			if (moveOrdering == 0) {
				//
			} else if (moveOrdering == 1) {
				Collections.shuffle(moves);
			} else if (moveOrdering == 2) {
				if ((suggestedP != null) && (suggestedP.x != -1)) {
					for (int i = 0; i < moves.size(); i++) {
						if (suggestedP.equals(moves.get(i))) {
							moves.add(0, moves.remove(i));
							break;
						}
					}
				}
				if ((suggestedP2 != null) && (suggestedP2.x != -1)) {
					for (int i = 1; i < moves.size(); i++) {
						if (suggestedP2.equals(moves.get(i))) {
							moves.add(1, moves.remove(i));
							break;
						}
					}
				}
			} else if (moveOrdering == 3) {
				ArrayList<Holder> moveholder = new ArrayList<Holder>();
				ArrayList<Position> newOrder = new ArrayList<Position>();
				for (int i = 0; i < moves.size(); i++) {
					TronMap newM = currentM.getCopy();
					if (!newM.move(moves.get(i),c.pos)) {
						System.out.println("MOVE ORDER FAIL");
					}
					moveholder.add(new Holder(i, newM.hValue(c.pos, 1)));
				}
				Collections.sort(moveholder);
				//Collections.reverse(moveholder);
				for (int i = 0; i < moveholder.size(); i++) {
					newOrder.add(moves.get(moveholder.get(i).x));
				}
				moves = newOrder;
			}
			}


			int depthLimit = currentSearchDepth-depth;
			if (depthLimit%2 == 0) {
				depthLimit++;
			}
			int enemyPos = (c.pos+1)%2;
			if (doTrimInterior) {
				int type = 1; 
				for (int i = 0; i < moves.size(); i++) {
					if (orig_hmap[moves.get(i).y][moves.get(i).x] == c.pos+1 && orig_hmap[currentM.H[enemyPos].y][currentM.H[enemyPos].x] == enemyPos+1) {
						if (!orig_map.shaded[moves.get(i).y][moves.get(i).x]) {
							if (type == 1) {
								if (depthLimit >= orig_dmap[moves.get(i).y][moves.get(i).x]*2) {
									skippedInterior++;
									moves.remove(moves.get(i--));
								} else {
									notSkippedInterior++;
								}
							} else if (type == 2) {
								if (moves.get(i).y > 1 && moves.get(i).y < orig_map.m.length-2) {
									//Position oldMove = moves.get(i);
									//Position replacementMove = currentM.dirToPos(currentM.headingToDir(orig_map.heading(currentM.H[c.pos], moves.get(i))),c.pos);
									Position replacementMove = orig_map.headingToPos(orig_map.heading(currentM.H[c.pos], moves.get(i)),moves.get(i));
									moves.set(i, replacementMove);
									int x= 0;x++;
								}
							}
						}
					}
				}
			}
			for (int i = 0; i < moves.size(); i++) {
				if(currentM.move(moves.get(i),c.pos)) {
					numPositionsVisited++;
					//				if ((doSmallCache && !cache.contains(newM.hashCode())) || (!doSmallCache && !cache.contains(newM))) {
					if (doCache) {if (doSmallCache) {cache.add(currentM.hashCode());} else {cache.add(currentM);}}
					Node<TronMap> newChild = tree.add(currentM,c);

					value  = (short) (-1*negaMaxAB2(tree, newChild, depth-1, cache, (short)(-1*beta), (short)(-1*localalpha),MaxPos, rootMove));
					if (value > bestvalue) {	
						bestP2 = new Position(bestP);					
						bestP = new Position(currentM.H[c.pos]);

					}
					bestvalue = (short) Math.max(value,bestvalue);

					if(bestvalue>=beta) {
						if (!currentM.undoMove(currentP, c.pos)) {
							System.out.print("FAILED UNDO MOVE");
						}
						//hf = hUPPER;
						break;
					}
					if(bestvalue>localalpha) { 
						localalpha=bestvalue;
						//hf = hEXACT;

					}
					if (!currentM.undoMove(currentP, c.pos)) {
						System.out.print("FAILED UNDO MOVE");
					}

				} 
			}
			if (doTrimming) {tree.trimByAlpha(c);}
			c.alpha = bestvalue;
			if (doWCache) {
				temp = System.nanoTime();
				//this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hf);
				if (v >= 19) {
				if(bestvalue <= alpha)
					this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hLOWER, rootMove);
				else if(bestvalue >= beta)
					this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hUPPER, rootMove);
				else
					this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hEXACT, rootMove);
				}
				timers[1] += System.nanoTime()-temp;
			}		
			return bestvalue;
		}
		
		@SuppressWarnings("unchecked")
		public short negaMaxAB2Fill(Tree<TronMap> tree, Node<TronMap> c, int depth, LinkedHashSet cache, short alpha, short beta, int MaxPos, Position rootMove) throws Exception {
			if ((System.currentTimeMillis() - timer > timeLimit) && doTimeLimit) {
				throw new Exception("timeout");
			}

			TronMap currentM = c.element;
			long temp = System.nanoTime();
			Position suggestedP = null; 
			Position suggestedP2 = null;

			//		if ((currentM.H[0].equals(new Position(19,5)) && c.pos == 0) && depth > 0) {
			//			this.doTimeLimit = false;
			//		}

			if (doWCache) {
				short val = 0;
				temp = System.nanoTime(); 

				if (v >= 19) {
				TptValue hData = new TptValue();
				hData.alpha = alpha;
				hData.beta = beta;
				TptValue[] hDataHolder = {hData};
				if (this.lookup(currentM, hDataHolder)) {
					timers[1] += System.nanoTime()-temp;
					hData = hDataHolder[0];

					if (hData.d >= depth) {
						numCacheHits++;
						if ((hData.flag & hEXACT) == hEXACT) {
							numCHexact++;
							val = hData.bestvalue;
							return val;
						}
						if (((hData.flag & hLOWER) == hLOWER) && (hData.alpha > alpha)) {
							numCHalpha++;
							alpha = hData.alpha;
						} else if (((hData.flag & hUPPER) == hUPPER) && (hData.beta < beta)) {
							numCHbeta++;
							beta = hData.beta;
						} else {
							numCHwaste++;
						}
						if (alpha >= beta)
						{
							numCHwaste--;
							numCHcutoff++;
							val = hData.bestvalue;
							return val;
						}
					}
					//				if (hData[5] >= depth) {
					//					v = hData[0];
					//					return v;
					//				} else {	//suggest move
					//System.out.print("");
					suggestedP = new Position(hData.bestPx,hData.bestPy);
					suggestedP2 = new Position(hData.bestP2x,hData.bestP2y);
					//}
				}
				}
			} 
			Position bestP = new Position(-1,-1);
			Position bestP2 = new Position(-1,-1);
			//short hf = hLOWER;

			ArrayList<Position> moves = currentM.getAllMoves(c.pos);
			timers[0] += System.nanoTime()-temp;
			//if (moves.size() == 0) {	//this position has lost
			//	return negInf;
			//}
			if (depth > 0) {
				pvMap[currentM.H[c.pos].y][currentM.H[c.pos].x]++;
				pvMap[currentM.H[(c.pos+1)%2].y][currentM.H[(c.pos+1)%2].x]++;
			}
			if (currentSearchDepth - depth == 1) {
				rootMove = new Position(currentM.H[(c.pos+1)%2]);
			}
			if (depth <= 0) {
				int type = doDijkstra ? 1 : 2;
				temp = System.nanoTime();

				short val = 0;
				temp = System.nanoTime();
				if (doWCache) {
					numEval++;
					val = currentM.hValue(MaxPos, type);
					timers[2] += System.nanoTime()-temp;


					//				ArrayList<Position> pv = this.getPV(currentM.getCopy(), pos);
					//				for (Position position : pv) {
					//					pvMap[position.y][position.x]++;
					//				}
					hMap[currentM.H[c.pos].y][currentM.H[c.pos].x]++;
					temp = System.nanoTime();
					if (v >= 19) {
					if(v <= alpha)
						this.store(currentM, 0, negInf,  val, beta, bestP, bestP2, hLOWER, rootMove);
					else if(v >= beta)
						this.store(currentM, 0, negInf, alpha, val, bestP, bestP2, hUPPER, rootMove);
					else                           // a true minimax value
						this.store(currentM, 0, val  , alpha, beta, bestP, bestP2, hEXACT, rootMove);
					}
					//hCache.put(currentM, v);
					timers[1] += System.nanoTime()-temp;
				} else if (doHCache) {
					if (hCache.containsKey(currentM)) {
						val = hCache.get(currentM);
						timers[1] += System.nanoTime()-temp;
						numCacheHits++;
					} else {
						val = currentM.hValue(MaxPos, type);
						timers[2] += System.nanoTime()-temp;
						temp = System.nanoTime();
						hCache.put(currentM, val);
						timers[1] += System.nanoTime()-temp;
					}
				} else {
					val = currentM.hValue(MaxPos, type);
					timers[2] += System.nanoTime()-temp;
				}
				c.alpha = val;
				return val;
			}
			//int alpha = Integer.MIN_VALUE;

			short localalpha = alpha;
			short bestvalue = negInf;
			short value;
			Position currentP = new Position(currentM.H[c.pos]);
			
			if (v >= 18) {

			int moveOrdering;

			//moveOrdering = 0; //None
			//moveOrdering = 1; //Random
			//moveOrdering = 2; //TPT
			moveOrdering = 2;

			//moves.add(moves.remove(0));
			if (moveOrdering == 0) {
				//
			} else if (moveOrdering == 1) {
				Collections.shuffle(moves);
			} else if (moveOrdering == 2) {
				if ((suggestedP != null) && (suggestedP.x != -1)) {
					for (int i = 0; i < moves.size(); i++) {
						if (suggestedP.equals(moves.get(i))) {
							moves.add(0, moves.remove(i));
							break;
						}
					}
				}
				if ((suggestedP2 != null) && (suggestedP2.x != -1)) {
					for (int i = 1; i < moves.size(); i++) {
						if (suggestedP2.equals(moves.get(i))) {
							moves.add(1, moves.remove(i));
							break;
						}
					}
				}
			} else if (moveOrdering == 3) {
				ArrayList<Holder> moveholder = new ArrayList<Holder>();
				ArrayList<Position> newOrder = new ArrayList<Position>();
				for (int i = 0; i < moves.size(); i++) {
					TronMap newM = currentM.getCopy();
					if (!newM.move(moves.get(i),c.pos)) {
						System.out.println("MOVE ORDER FAIL");
					}
					moveholder.add(new Holder(i, newM.hValue(c.pos, 1)));
				}
				Collections.sort(moveholder);
				//Collections.reverse(moveholder);
				for (int i = 0; i < moveholder.size(); i++) {
					newOrder.add(moves.get(moveholder.get(i).x));
				}
				moves = newOrder;
			}
			}

			if (doWarnsdorff) {	//
				ArrayList<Position> moves2 = new ArrayList<Position>();
				ArrayList<Holder> h = new ArrayList<Holder>();
				for (int i = 0; i < moves.size(); i++) {
					ArrayList<Position> nMov = currentM.getAllMoves(moves.get(i));
					if (nMov.size() >0) {
						h.add(new Holder(i, nMov.size()));
					}
				}
				if (h.size() > 0) {
					Collections.sort(h);
					if (h.get(0).y != 3) {
						for (int i = 0; i < h.size(); i++) {
							if (h.get(i).y < 3) {
								moves2.add(moves.get(h.get(i).x));
							}							
						}
					} else {
						for (int i = 0; i < h.size(); i++) {
							moves2.add(moves.get(h.get(i).x));
						}
					}
					moves = moves2;
				}

			}	
		
			int depthLimit = currentSearchDepth-depth;
			if (depthLimit%2 == 0) {
				depthLimit++;
			}
			int enemyPos = (c.pos+1)%2;

			for (int i = 0; i < moves.size(); i++) {
				if(currentM.move(moves.get(i),c.pos)) {
					numPositionsVisited++;
					//				if ((doSmallCache && !cache.contains(newM.hashCode())) || (!doSmallCache && !cache.contains(newM))) {
					if (doCache) {if (doSmallCache) {cache.add(currentM.hashCode());} else {cache.add(currentM);}}
					Node<TronMap> newChild = tree.add(currentM,c);
					newChild.pos = (byte) ((c.pos+1)%2);

					value  = (short) (negaMaxAB2Fill(tree, newChild, depth-1, cache, (short)(localalpha), (short)(beta),MaxPos, rootMove));
					if (value > bestvalue) {	
						bestP2 = new Position(bestP);					
						bestP = new Position(currentM.H[c.pos]);

					}
					bestvalue = (short) Math.max(value,bestvalue);

					if(bestvalue>=beta) {
						if (!currentM.undoMove(currentP, c.pos)) {
							System.out.print("FAILED UNDO MOVE");
						}
						//hf = hUPPER;
						break;
					}
					if(bestvalue>localalpha) { 
						localalpha=bestvalue;
						//hf = hEXACT;

					}
					if (!currentM.undoMove(currentP, c.pos)) {
						System.out.print("FAILED UNDO MOVE");
					}

				} 
			}
			if (doTrimming) {tree.trimByAlpha(c);}
			c.alpha = bestvalue;
			if (doWCache) {
				temp = System.nanoTime();
				//this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hf);
				if (v >= 19) {
				if(bestvalue <= alpha)
					this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hLOWER, rootMove);
				else if(bestvalue >= beta)
					this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hUPPER, rootMove);
				else
					this.store(currentM, depth, bestvalue, alpha, beta, bestP, bestP2, hEXACT, rootMove);
				}
				timers[1] += System.nanoTime()-temp;
			}		
			return bestvalue;
		}

		@SuppressWarnings("unchecked")
		public short negaMax(Tree<TronMap> tree, Node<TronMap> c, int depth, LinkedHashSet cache, int startPos) {
			if ((System.currentTimeMillis() - timer > timeLimit) && doTimeLimit) {
				return 0;
			}
			//Tree<TronMap> tree = new Tree<TronMap>(tm);
			TronMap currentM = c.element;
			long temp = System.nanoTime();

			ArrayList<Position> moves = currentM.getAllMoves(c.pos);		
			timers[0] += System.nanoTime()-temp;
			if (moves.size() == 0) {	//this position has lost or drawn
				c.alpha = negInf;
				return negInf;
			}		

			ArrayList<Position> enemyMoves = currentM.getAllMoves((c.pos+1)%2);
			if (enemyMoves.size() == 0) {	//this position has won
				c.alpha = posInf;
				return posInf;
			}

			if (doSkipEndgame) {
				int type = doDijkstra ? 1 : 2;
				short v = currentM.hValue(c.pos, type);
				if (v >= 1000 || v <= -1000) {
					if (v >= 1000) {
						c.alpha = posInf;
						return posInf;
					} else {
						c.alpha = negInf;
						return negInf;
					}
				}
			}
			if (depth <= 0) {
				int type = doDijkstra ? 1 : 2;
				temp = System.nanoTime();
				short v = currentM.hValue(startPos, type);
				timers[1] += System.nanoTime()-temp;
				c.alpha = v;
				return v;
			}
			//int alpha = Integer.MIN_VALUE;

			short bestvalue = negInf;
			short value;

			for (int i = 0; i < moves.size(); i++) {
				TronMap newM = currentM.getCopy();
				if(newM.move(moves.get(i),c.pos)) {
					if ((doSmallCache && !cache.contains(newM.hashCode())) || (!doSmallCache && !cache.contains(newM))) {
						if (doCache) {if (doSmallCache) {cache.add(newM.hashCode());} else {cache.add(newM);}}
						if (newM.hashCode() == 736084072) {
							int x=0; x++;
						}
						//System.out.println("added:\t"+newM.hashCode());
						Node<TronMap> newChild = tree.add(newM, c);
						value  = (short) (-1*negaMax(tree, newChild, depth-1, cache, startPos));
						bestvalue = (short) Math.max(value,bestvalue);
					} else {
						cacheHits++;
						//System.out.println("\t\t\tMATCH FOUND:");
						//System.out.println(newM);
						//System.out.println(newM.hashCode());
					}
				} 
			}
			if (doTrimming) {tree.trimByAlpha(c);}
			c.alpha = bestvalue;
			return bestvalue;
		}

		@Override
		public String toString() {
			return "MT:"+variation;
		}


		private Position nextSurvivalMove(TronMap m, ArrayList<Position> p,
				boolean edgeCase, boolean followWall) {

			//survival
			//boolean followWall = false;
			if (followWall) {

				//find longest path
				//hug the walls		
				//find the move that has the least number of edges, but not zero
				ArrayList<Holder> h = new ArrayList<Holder>();
				for (int i = 0; i < p.size(); i++) {
					ArrayList<Position> nMov = m.getAllMoves(p.get(i));
					if (nMov.size() >0) {
						h.add(new Holder(i, nMov.size()));
					}
				}
				Position newP = null;
				if (h.size() > 0) {
					Collections.sort(h);
					newP = p.get(h.get(0).x);
				} else { //no good moves left
					if (p.size() > 0) {
						newP = p.get(0);
					} else {
						newP = m.dirToPos((byte) 1,pos);
					}
				}

				if (doCV) {
					//are we at a cut vertex?			
					if (m.oldH[pos] != null) {
						int heading = (int) (Math.round(m.heading(m.oldH[pos], m.H[pos])/90.0)*90);
						byte dir = m.headingToDir(heading);
						Position pos2 = m.dirToPos(dir,m.H[pos]);
						Position p05 = null;
						Position p15 = null;
						if (pos2 != null) {
							p05 = m.dirToPos((byte)(util.negMod(dir+1-1,4)+1),pos2);
							p15 = m.dirToPos((byte)(util.negMod(dir-1-1,4)+1),pos2);
						}
						if (!m.canMove(p05, newP) && !m.canMove(p15, newP)) {
							//yes, do multi BFS until all but 1 terminates
							ArrayList<BFS> bfs = new ArrayList<BFS>();

							ArrayList<Position> nMov = m.getAllMoves(m.H[pos]);
							if (nMov.size() > 1) {
								for (int i = 0; i < nMov.size(); i++) {
									bfs.add(new BFS(m.getCopy(),nMov.get(i),true));
								}
								while (bfs.size() > 1) {
									for (int i = 0; i < bfs.size(); i++) {
										if (!bfs.get(i).next()) {
											if(bfs.size() ==1) {
												break;
											}
											bfs.remove(i);
											i--;
										} 
									}
								}
								return bfs.get(0).start;
							} else if (nMov.size() > 0) {
								return nMov.get(0);
							}
						}
					}					
				}

				if (newP == null) {
					System.out.println("newP == null");
				}
				return newP;
			} else {
				if (doAssumeFirstLeft) {
					if (pos == 0 && firstMove) {
						firstMove = false;
						return m.dirToPos((byte) 4, pos);
					}
				}
				//int startDepth;
				if (doIterativeDeepening) {
					startDepth = 2;
					if (doTimeLimit) {
						endDepth = 10001;
					} else {
						endDepth = maxdepth;
					}
				} else {
					startDepth = maxdepth;
					endDepth = maxdepth;
				}
				pvMap = m.getBigCopy();
				util.fill(pvMap, (short) 0);
				hMap = m.getBigCopy();
				util.fill(hMap, (short) 0);
				Position po = null;
				String output = "";
				short bestLocalAlpha = 0;
				short prevBestLocalAlpha = 0;
				int failLow = 0;
				int failHigh = 0;
				int failNot = 0;
				String t = "";
				int tempRandomSpace = 0;
				int depthIncrement;
				
				depthIncrement = 1;
				if (m.outputLevel > 2) {
					System.out.println(m.toBigString(orig_dmap));
					TronMap newM;
					short[][] dmap2;
					BFS bfs;
					System.out.println(m.hValue(0, 1));
					
					newM = m.getCopy();
					newM.move(new Position(newM.H[0].x,newM.H[0].y+1), 0);
					newM.outputLevel = m.outputLevel;
					bfs = new BFS(newM,newM.H[0],true);
					bfs.start2 = m.H[1];			
					dmap2 = bfs.distance();
					util.subtract(dmap2, orig_dmap);
					System.out.println(m.toBigString(dmap2));
					System.out.println(newM.hValue(0, 1));

					newM = m.getCopy();
					newM.move(new Position(newM.H[0].x,newM.H[0].y-1), 0);
					newM.outputLevel = m.outputLevel;
					bfs = new BFS(newM,newM.H[0],true);
					bfs.start2 = m.H[1];			
					dmap2 = bfs.distance();
					util.subtract(dmap2, orig_dmap);
					System.out.println(m.toBigString(dmap2));
					System.out.println(newM.hValue(0, 1));

					newM = m.getCopy();
					newM.move(new Position(newM.H[0].x+1,newM.H[0].y), 0);
					newM.outputLevel = m.outputLevel;
					bfs = new BFS(newM,newM.H[0],true);
					bfs.start2 = m.H[1];			
					dmap2 = bfs.distance();
					util.subtract(dmap2, orig_dmap);
					System.out.println(m.toBigString(dmap2));
					System.out.println(newM.hValue(0, 1));
				}

				this.moves += 2;

				criticalError = "";

				for (int d = startDepth; d <= endDepth; d=d+depthIncrement) {
					currentSearchDepth = d;
					//for (int d = startDepth; d <= endDepth; d=d+1) {
					if (doSmallCache) {
						cache = new LinkedHashSet<Integer>();
						cacheHits = 0;
					} else {
						cache = new LinkedHashSet<TronMap>();
						cacheHits = 0;
					}

					tree = new Tree<TronMap>(m.getCopy(),pos);

					if (d == 7){
						int x = 0;x++;
					}
					if (doAlphaBeta) {
						//if (d%2==0) {
						Position rootMove = new Position(-1,-1);
						try {
							tree.root.alpha = negaMaxAB2Fill(tree, tree.root, d, cache,negInf, posInf,pos,rootMove);
						} catch (Exception e) {
							String msg = e.getMessage();
							if (!msg.equals("timeout")) {
								e.printStackTrace();
							}
						}
					} else {
						tree.root.alpha = negaMax(tree, tree.root, d, cache, pos);
					}

					finalDepth = d;
					//if time limit expired take last round's best go
					if (((System.currentTimeMillis() - timer > timeLimit) && doTimeLimit)) {					
						if (po == null) {	//didn't complete 1 ply
							po = p.get(0);
							criticalError += "TIMEOUT: MT found no moves, using default\n";
							System.out.println("MT found no moves, using default");
						}
						break;
					}

					//the current ply completed, so get the best move
					Collections.sort(tree.root.children);
					ArrayList<Position> equalmoves = new ArrayList<Position>();
					if (tree.root.children.size() > 1 && doWCache == false) {
						equalmoves.add(tree.root.children.get(0).element.H[pos]);
						for (int i = 1; i < tree.root.children.size(); i++) {
							if (tree.root.children.get(0).alpha == tree.root.children.get(i).alpha) {
								equalmoves.add(tree.root.children.get(i).element.H[pos]);
							}
						}
						bestAlpha = tree.root.children.get(0).alpha;			
					} else {
						if (v >= 19) {
							if (equalmoves.size() == 0) {
								TptValue hData = new TptValue();
								hData.flag = hEXACT;
								TptValue[] hDataHolder = {hData};
								if (this.lookup(m, hDataHolder)) {
									//if (pos == 0) {
									hData = hDataHolder[0];
									prevBestLocalAlpha = bestLocalAlpha;
									bestLocalAlpha = hData.bestvalue;
									oldCacheSize = curCacheSize;
									curCacheSize = transPosTable.size();
									if (prevBestLocalAlpha != 5000 && m.outputLevel > 1) {
										System.out.println(d+"\t"+prevBestLocalAlpha+"\t"+bestLocalAlpha+"\t"+Math.abs(prevBestLocalAlpha-bestLocalAlpha)+"\tcache incr:"+(curCacheSize-oldCacheSize));
									}
									//}
									if (hData.bestPx == -1) {
										if (hData.bestvalue == negInf) {		//game over for me: best move is certain death
											equalmoves.add(this.nextSurvivalMove(m,p,edgeCase,true));
										} else {
											criticalError += "INVALID POSITION RECALLED FROM CACHE at d = "+d+"\n";
											System.out.println(criticalError);
										}
									} else 
										equalmoves.add(new Position(hData.bestPx,hData.bestPy));
									//if (hData[8] != 4) {
									//}
									bestAlpha = (short) (hData.bestvalue*-1);
								} else {
									criticalError += "NOTHING FOUND IN CACHE at d = "+d+"\n";
									System.out.println(criticalError);
									this.lookup(m, hDataHolder);
								}
							}
						}
					}
					if (equalmoves.size() > 1) {	//take move that is closest to enemy
						//System.out.println("equalmoves found: "+equalmoves.size());
						if (doRandomAtEqual) {
							tempRandomSpace = equalmoves.size();
							Random r = new Random();
							po = equalmoves.get(r.nextInt(equalmoves.size()));
						} else {
							int minDist = 100000;
							int minIdx = 0;
							for (int i = 0; i < equalmoves.size(); i++) {
								int dm = m.distManh(equalmoves.get(0), m.H[(pos+1)%2]);
								if (dm < minDist) {
									minDist = dm;
									minIdx = i;
								}
							}
							po = equalmoves.get(minIdx);
						}
					} else if (equalmoves.size() == 1) {
						po = equalmoves.get(0);
					} else {
						//no moves found AT ALL, serious problem should be reported
						if (po == null) {	//didn't complete 1 ply
							criticalError += "NOCACHE: MT found no moves, using default\n";
							po = p.get(0);
						}
					}
					if (bestAlpha == negInf || bestAlpha == posInf) {	//game over: we've won/lost
						if (bestAlpha == negInf) {
							t += "   \t(Bot"+(pos+1)+") wins";
						} else {
							t += "   \t(Bot"+((pos+1)%2+1)+") wins";
						}
						break;
					}
					oldTree2 = tree;
					//System.out.println("\t\t\t\t\t\tpo:"+po.toString());
					if (m.outputLevel > 2) {
						System.out.println("skippedInterior:    "+skippedInterior);
						System.out.println("notSkippedInterior: "+notSkippedInterior);
					}
					skippedInterior = 0;
					notSkippedInterior = 0;
				}
				if (doAspirationSearch && m.outputLevel > 1) {
					System.out.println(failLow+"\t"+failHigh+"\t"+failNot+"\t"+finalDepth+"\tcache size:"+transPosTable.size());
				}

				ArrayList<Position> equalmoves2 = new ArrayList<Position>();
				if (doRandomAtEqual) {
					ArrayList<Position> moves = m.getAllMoves(pos);
					ArrayList<Holder> alphas = new ArrayList<Holder>();
					for (int i = 0; i < moves.size(); i++) {
						TronMap newMap = m.getCopy();
						if (newMap.move(moves.get(i),pos)) {
							if (v >= 19) {
								TptValue[] hDataHolder = new TptValue[1];
								if (this.lookup(newMap, hDataHolder)) {
									if ((hDataHolder[0].bestPx != -1) && ((hDataHolder[0].flag & hEXACT) == hEXACT)) {	 	
										alphas.add(new Holder(i, hDataHolder[0].bestvalue));
									}
								}
							}
						}
					}
					if (alphas.size() > 0) {
						Collections.sort(alphas);
						Collections.reverse(alphas);
						for (int i = 0; i < alphas.size(); i++) {
							if (alphas.get(i).y == alphas.get(0).y) {
								equalmoves2.add(moves.get(alphas.get(i).x));
							}
						}
						bestAlpha = (short) alphas.get(0).y;

						tempRandomSpace = equalmoves2.size();
						Random r = new Random();
						po = equalmoves2.get(r.nextInt(equalmoves2.size()));
					}
				}

				if (doRandomAtEqual) {
					randomSpace *= tempRandomSpace;
					t += "\trandomSpace: "+tempRandomSpace+"\t";
				}

				if (m.outputLevel > 2) {
					System.out.println(output);
					if (oldTree2 != null) {
						System.out.println(oldTree2.toString(4));
					} else if (tree != null) {
						System.out.println(tree.toString(4));
					}
					System.out.println(m.toBigString(pvMap));
					System.out.println(m.toBigString(hMap));
				}
				if (doAltChase) {
					if (bestAlpha > -1*m.m.length*m.m[0].length/5) {
						t += "\tCHASING (bestA = "+bestAlpha+")\t";
						Robot b = new Strat(0, Strat.Strategy.OFFENSIVE);
						TronMap newMap = m.getCopy();
						po = b.nextMove(newMap, p, edgeCase);
					}
				}
				if (m.outputLevel > 1) {
					output = pos+" finaldepth:   "+finalDepth+"\tbestAlpha: "+bestAlpha/*+" chosen alpha: "+tree.root.children.get(0).alpha*/+" other: "+t;
					System.out.println(output);

				} 

				if (po == null) {
					System.out.println("po == null");
				}
				//tree = null;
				//oldTree2 = null;
				return po;
			}
		}

		public ArrayList<Position> getPV(TronMap m, int pos) {
			ArrayList<Position> pv = new ArrayList<Position>();

			pos = (pos+1)%2;
			if (v >= 19) {
			while (true) {
				//short[] hData = {0,0,0,0,0,0,0,0,hEXACT};
				TptValue hData = new TptValue();
				hData.flag = (byte)hEXACT;
				TptValue[] hDataHolder = {hData};
				if (this.lookup(m, hDataHolder)) {
					hData = hDataHolder[0];
					if (hData.bestPx == -1) {
						if (hData.bestvalue == negInf) {		//game over for me: best move is certain death
							pv.add(this.nextSurvivalMove(m,m.getAllMoves(pos),false,true));
						} else {
							break;
						}
					} else 
						pv.add(new Position(hData.bestPx,hData.bestPy));
				} else {
					this.lookup(m, hDataHolder);
					break;
				}
				if(!m.move(pv.get(pv.size()-1), pos)) {
					System.out.println("FAILED MOVE");
					break;
				} 
				pos = (pos+1)%2;

			}
			}

			return pv;
		}

		public static boolean doCompression = true;
			
		public void trimTPT(Position nextMove) {
			int debug = 0;
			if (nextMove == null) {
				return;
			}
			//trim
			ArrayList<Long> old = new ArrayList<Long>();
			for (Entry<Long, TptValue> e : this.transPosTable.entrySet()) {
				if (e.getValue().expireDepth < this.moves) {
					old.add(e.getKey());
					e.getValue().old = true;
				} else {

					if (e.getValue().rootMoveX != nextMove.x || e.getValue().rootMoveY != nextMove.y) {
						if (!e.getValue().checked) {
							old.add(e.getKey());
							e.getValue().old = true;
						}
					} else {
						e.getValue().checked = true;
					}
				}			
			}

			doTrimTPT = true;
			if (debug > 0) System.out.println("\t\t\tremoved entries:  "+old.size()+" %:"+(old.size()*100/((double)(this.transPosTable.size()))));
			if (debug > 0) System.out.println("\t\t\ttransPosTable.size():"+this.transPosTable.size());
			if (doTrimTPT) {
				for (int i = 0; i < old.size(); i++) {
					this.transPosTable.remove(old.get(i));
				}
			}
		}
	}

	public static class Tree<E> implements java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3538748384710159838L;
		
		public Node<E> root;
		public int size;
		public int maxDepth;
	
	    public Tree(E rootData, int pos) {
	        root = new Node<E>(rootData, null, pos);
	        root.depth = 0;
	        size = 1;
	        maxDepth = 0;
	        
	        //root.element = rootData;
	        //root.children = new ArrayList<Node<E>>();
	    }
	    
	    public Node<E> add(E element, Node<E> parent) {
	    	Node<E> newChild = new Node<E>(element, parent, (parent.pos+1)%2);
	    	parent.children.add(newChild);
	    	size++;
	    	if (newChild.depth > maxDepth) {maxDepth = newChild.depth;}
	    	return newChild;
	    }
	    
	    public void remove(Node<E> n) {
	    	if (n != root) {
				n.parent.children.remove(n);
			} else {
				root = null;
			}
	    	size--;
	    }
	    
	    public void trimByAlpha() {
	    	trimByAlpha(root);
	    }
	    
	    public void trimByAlpha(Node<E> n) {
			Queue<Node<E>> q = new LinkedList<Node<E>>();
			q.add(n);
			Node<E> top;
			while (q.size() > 0) {
				top = q.poll();
				
				if (top.children.size() == 0) continue;
				
				//sort and trim			
				Collections.sort(top.children);
				//if (top.pos != root.pos) {
				//	Collections.reverse(top.children);
				//}
				for (int i = 0; i < top.children.size(); i++) {
					if (top.children.get(i).alpha != top.children.get(0).alpha) {
						top.children.get(i).parent = null;
						top.children.remove(i);
						i--;
					}
				}
				for (Node<E> child : top.children) {
					q.add(child);
				}
			}		
	    }
	    
	    public String toString(int depth) {
	    	String[] output = new String[depth+1];
	    	for (int i = 0; i < output.length; i++) {
	    		output[i] = new String(); 
			}
	    	Node<Node<E>> top = new Node<Node<E>>(root, null);
	    	top.pos = 0;
			Queue<Node<Node<E>>> q = new LinkedList<Node<Node<E>>>();
			q.add(top);
			StringBuilder sb = new StringBuilder();
			while (q.size() > 0) {
				top = q.poll();
				
				if (top.pos >= depth) continue;
				// visit every adjacent node
				for (Node<E> node : top.element.children) {
					Node<Node<E>> child = new Node<Node<E>>(node, null);
					child.pos = (byte) (top.pos+1);
					q.add(child);
					output[child.pos] += child.element.alpha + ",";
				}
				if (top.element.children.size() >0) {
					output[top.pos+1] += "|";
				}
			}
			for (int i = 0; i < output.length; i++) {
				sb.append(output[i]);
				sb.append('\n');
			}
			return sb.toString();
	    }
	}

	public static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream in) {
			this.br = new BufferedReader(new InputStreamReader(in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					String s = br.readLine();
					if (s != null) {
						st = new StringTokenizer(s);
					} else {
						return null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextLong();
			}
			return a;
		}

		int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextInt();
			}
			return a;
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
