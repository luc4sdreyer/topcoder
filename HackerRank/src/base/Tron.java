package base;
import java.io.*;
import java.util.*;
import java.util.Map.*;
public class Tron {
static final int map_size = 15;
public static void main(String[] args) {
	playGame(System.in);
}
enum State {Battle,Survival};
static void playGame(InputStream in) {
	MyScanner scan = new MyScanner(in);
	int pos = scan.nextLine().toCharArray()[0] == 'r' ? 0 : 1;
	int y1 = scan.nextInt();
	int x1 = scan.nextInt();
	Po[] start = new Po[2];
	start[0] = new Po(x1,y1);
	y1 = scan.nextInt();
	x1 = scan.nextInt();
	start[1] = new Po(x1,y1);
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
		}}
	TM map = new TM(bm,start[0],start[1]);
	MgT bot = new MgT(pos);
	bot.timeLimit = 1500;
	Po newPos = bot.move(map);
	if (!map.move(newPos,pos)) {
		System.out.println("Game over");
	}
	String[] dir = {"UP","RIGHT","DOWN","LEFT"};
	System.out.println(dir[map.posToDir(start[pos],newPos) -1]);
}
static class BFS {
	TM map;Po st;int avSp;Queue<Po> q;Queue<PoV> qv;Po start2;TM[] ref;short[][][] smap;
	BFS (TM map,Po start,boolean slow) {
		this.map = map;this.st = start;this.avSp = 0;q = new LinkedList<Po>();q.add(start);
	}
	//used to step through a list of concurrent BFSs,and terminate the last one when the others finish to save time
	boolean next() {
		boolean r = false;
		if (q.size() != 0) {
			r = true;
			Po top = q.poll();
			if (!(top.y < map.m.length && top.y >= 0)) return r;
			if (map.m[top.y][top.x] != 0) return r;
			map.m[top.y][top.x] = 5;
			avSp++;
			// visit every adjacent node
			ArrayList<Po> moves = map.getAllMoves(top);
			for (int i = 0; i < moves.size(); i++) {
				if (map.cm(moves.get(i),top)) {
					q.add(moves.get(i));
				}
			}}
		return r;
	}
	short[][] dst() {return this.dst(null);}
	//build distance map
	short[][] dst(Po clear) {
		qv = new LinkedList<PoV>();
		qv.add(new PoV(st,0));
		short[][] smap = map.getBigCopy();
		TM ref = map.getCopy();
		util.fill(smap,Short.MAX_VALUE);
		ref.m[st.y][st.x] = 0;
		if (clear != null) {
			ref.m[clear.y][clear.x] = 0;
		}
		PoV top;
		ArrayList<Po> moves;
		while (qv.size() > 0) {
			top = qv.poll();
			//counter2++;
			if (smap[top.y][top.x] <= top.v) continue;
			smap[top.y][top.x] = (short) top.v;
			ref.m[top.y][top.x] = (short) 1;
			// visit every adjacent node
			moves = new ArrayList<Po>();
			byte bYH = (byte) (ref.m.length-1);
			byte bYH1 = (byte) (ref.m.length-2);
			if (top.y != 0) {
				if (ref.m[top.y-1][top.x] == 0) {
					moves.add(new Po(top.x,top.y-1));
				}
			} else {
				for (byte x = 0; x < ref.m[0].length; x++) {
					if (ref.m[1][x] == 0) {
						if (x != top.x) {
							moves.add(new Po(x,1));
						}
					}
				}
			}
			if (top.y != bYH) {
				if (ref.m[top.y+1][top.x] == 0) {
					moves.add(new Po(top.x,top.y+1));
				}
				if (top.y != 0) {
					byte newX = (byte) ((top.x+1)%ref.m[0].length);
					if (ref.m[top.y][newX] == 0) {
						moves.add(new Po(newX,top.y));
					}
					newX = (byte) util.negMod(top.x-1,ref.m[0].length);
					if (ref.m[top.y][newX] == 0) {
						moves.add(new Po(newX,top.y));
					}
				}
			} else {
				for (byte x = 0; x < ref.m[0].length; x++) {
					if (ref.m[bYH1][x] == 0) {
						if (x != top.x) {
							moves.add(new Po(x,bYH1));
						}
					}
				}
			}
			int nextV = top.v+1;
			Po nextM;
			for (int i = 0; i < moves.size(); i++) {
				nextM = moves.get(i);
				if (ref.m[nextM.y][nextM.x] == 0) {
					qv.add(new PoV(nextM,nextV));
				}
			}}
		return smap;
	}
	//build 2 distance maps and terminate on the boundary
	//@SuppressWarnings("unchecked")
	short[][] duDst(boolean[] endgame) {
		final byte bXH0 = (byte) (map.m[0].length);
		final byte bYH0 = (byte) (map.m.length);
		final byte bYH1 = (byte) (map.m.length-1);
		final byte bYH2 = (byte) (map.m.length-2);
		PoV[][] dualQ = new PoV[2][5000];
		int[] qf = {0,0};
		int[] qb = {0,0};
		dualQ[0][qb[0]++] = new PoV(st,0);
		dualQ[1][qb[1]++] = new PoV(start2,0);
		smap = new short[2][map.m.length][map.m[0].length];
		ref = new TM[2];
		ref[0] = map.getCopy();
		ref[1] = map.getCopy();
		util.fill(smap[0],Short.MAX_VALUE);
		util.fill(smap[1],Short.MAX_VALUE);
		ref[0].m[st.y][st.x] = 0;
		ref[1].m[start2.y][start2.x] = 0;
		PoV top;
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
						dualQ[i][qb[i]++] = new PoV(topX,topY-1,nextV);
					}
				} else if (!reachedYbot[i]) {
					reachedYbot[i] = true;
					for (byte x = 0; x < bXH0; x++) {
						if (ref[i].m[1][x] == 0) {
							if (x != topX) {
								dualQ[i][qb[i]++] = new PoV(x,1,nextV);
							}
						}
					}
				}
				if (topY != bYH1) {
					if (ref[i].m[topY+1][topX] == 0) {
						dualQ[i][qb[i]++] = new PoV(topX,topY+1,nextV);
					}
					if (topY != 0) {
						byte newX = (byte) ((topX+1)%bXH0);
						if (ref[i].m[topY][newX] == 0) {
							dualQ[i][qb[i]++] = new PoV(newX,topY,nextV);
						}
						newX = (byte) util.negMod(topX-1,bXH0);
						if (ref[i].m[topY][newX] == 0) {
							dualQ[i][qb[i]++] = new PoV(newX,topY,nextV);
						}
					}} else if (!reachedYtop[i]) {
						reachedYtop[i] = true;
						for (byte x = 0; x < bXH0; x++) {
							if (ref[i].m[bYH2][x] == 0) {
								if (x != topX) {
									dualQ[i][qb[i]++] = new PoV(x,bYH2,nextV);
								}
							}
						}
					}
			}
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
			}}
		endgame[0] = true;
		for (byte i = 0; i < bYH0; i++) {
			for (byte j = 0; j <  bXH0; j++) {
				if ((ref[0].m[i][j] == 9) && (ref[1].m[i][j] == 9)) {
					endgame[0] = false;
					i = (byte) bYH0;
					j = (byte) bXH0;
				}
			}}
		//might STILL be endgame
		if (endgame[0] == true) {
			ArrayList<Po> moves1 = map.gam(1);
			for (int i = 0; i < moves1.size(); i++) {
				Djk dp = new Djk(map.getCopy(),map.H[0],moves1.get(i));
				int length = dp.shortestPath();
				if (length != Short.MAX_VALUE) {
					endgame[0] = false;
					break;
				}
			}}
		//might STILL be endgame
		if (endgame[0] == true) {
			ArrayList<Po> moves0 = map.gam(0);
			for (int i = 0; i < moves0.size(); i++) {
				Djk dp = new Djk(map.getCopy(),moves0.get(i),map.H[1]);
				int length = dp.shortestPath();
				if (length != Short.MAX_VALUE) {
					endgame[0] = false;
					break;
				}
			}}
		return mm;
	}
}
static class Djk {TM map;Node tgt;Node org;short[][] dm;boolean ftgt;int lft;ArrayList<Po> pt;Djk (TM map,Po originP,Po targetP) {this.map = map;this.org = new Node(originP);this.org.cost = 0;this.tgt = new Node(targetP);this.dm = map.getBigCopy();this.ftgt = false;this.lft = Short.MAX_VALUE;this.pt = new ArrayList<Po>();}int shortestPath() {if (ftgt) {return lft;}if (org.equals(tgt)) {return 0;}util.fill(dm,Short.MAX_VALUE);Po[][] prev = new Po[dm.length][dm[0].length];ArrayList<Po> moves;PriorityQueue<Node> pq = new PriorityQueue<Node>();pq.add(org);if ((map.m[tgt.p.y][tgt.p.x] == 3) || (map.m[tgt.p.y][tgt.p.x] == 4)) {map.m[tgt.p.y][tgt.p.x] = 0;}dm[org.p.y][org.p.x] = (short) org.cost;while (pq.size() > 0) {Node top = pq.poll();if (top.p.equals(tgt.p)) {ftgt = true;lft = top.cost;pt = new ArrayList<Po>();Po u = new Po(tgt.p);while (prev[u.y][u.x] != null) {pt.add(0,u);u = new Po(prev[u.y][u.x]);}return top.cost;}moves = map.getAllMoves(top.p);for (int i = 0; i < moves.size(); i++) {if (map.m[moves.get(i).y][moves.get(i).x] == 0) {if (dm[moves.get(i).y][moves.get(i).x] > (top.cost+1)) {dm[moves.get(i).y][moves.get(i).x] = (short) (top.cost+1);prev[moves.get(i).y][moves.get(i).x] = new Po(top.p);pq.add(new Node(moves.get(i),top.cost+1));}}}}return Short.MAX_VALUE;}class Node implements Comparable<Node> {int cost,at;Po p;Node(Po p) {this.p = p;this.at = map.getInt(p);}Node(Po p,int cost) {this(p);this.cost = cost;}public String toString() {return p.toString()+"c:"+cost+",at:"+at;}
@Override
public int compareTo(Node o) {Node next = (Node)o;if (cost < next.cost) {return -1;}if (cost > next.cost) {return 1;}if (at < next.at) {return -1;}if (at > next.at) {return 1;}return 0;}}}static class No<E> implements Comparable<No<E>>{static final long serialVersionUID = 3358389393300911760L;E e;No<E> p;List<No<E>> c;boolean built;byte pos;short a;byte d;No (E element,No<E> parent,int pos) {construct(element,parent);this.pos = (byte) pos;}void construct(E element,No<E> parent) {this.e = element;this.p = parent;this.c = new ArrayList<No<E>>(3);this.built = false;if (parent != null) {this.d = (byte) (parent.d+1);} else {this.d = 0;}}
@Override
public int compareTo(No<E> o) {if (this.a == o.a) {return 0;}else if (this.a > o.a) {return 1;}else {return -1;}}
@Override
public String toString() {String s = e.toString().replaceFirst("\n","   alpha:"+a+"\n");s = s.replace("alpha","pos:"+pos+"   alpha");return "\n"+s+"\n";}
@Override
public int hashCode() {final int prime = 31;int result = 1;result = prime * result+a;result = prime * result+(built ? 1231 : 1237);result = prime * result+((c == null) ? 0 : c.hashCode());result = prime * result+((e == null) ? 0 : e.hashCode());result = prime * result+((p == null) ? 0 : p.hashCode());result = prime * result+pos;return result;}
@Override
public boolean equals(Object obj) {if (this == obj) {return true;}if (obj == null) {return false;}if (getClass() != obj.getClass()) {return false;}No other = (No) obj;if (a != other.a) {return false;}if (built != other.built) {return false;}if (e == null) {if (other.e != null) {return false;}} else if (!e.equals(other.e)) {return false;}if (pos != other.pos) {return false;}return true;}}public static class Po {byte x;byte y;Po(int x,int y) {this.x = (byte)x;this.y = (byte)y;}Po(byte x,byte y) {this.x = x;this.y = y;}Po(Po p) {this.x = p.x;this.y = p.y;}Po() {this.x = 0;this.y = 0;}public String toString() {return "("+x+","+y+")";}
@Override
public int hashCode() {final int prime = 31;int result = 1;result = prime * result+x;result = prime * result+y;return result;}
@Override
public boolean equals(Object obj) {if (this == obj) {return true;}if (obj == null) {return false;}if (getClass() != obj.getClass()) {return false;}Po other = (Po) obj;if (x != other.x) {return false;}if (y != other.y) {return false;}return true;}}static class PoV implements Comparable<PoV> {static final long serialVersionUID = -5828259544283422628L;int v;int x;int y;PoV(int x,int y,int v) {this.x = x;this.y = y;this.v = v;}PoV(Po p,int v) {this.x = p.x;this.y = p.y;this.v = v;}public String toString() {return "("+x+","+y+","+v+")";}public int compareTo(PoV o) {return Integer.compare(this.v, o.v);}boolean equals(PoV p) {if (p.x != this.x) {return false;}if (p.y != this.y) {return false;}if (p.v != this.v) {return false;}return true;}public Po toPo() {return new Po(this.x, this.y);}}
static class TM {
	byte[][] m;Po[] H;Po[] oH;State stt;boolean[] drt;boolean[][] shd;int mvs;boolean noP2;long hk;
	TM (byte[][] newm,boolean noP2) {this.noP2 = noP2;this.buildMap(newm);}
	TM (byte[][] newm,Po H1,Po H2) {this.buildMap(newm);addPos(H1,H2);}
	void buildMap (byte[][] newM) {
		final byte bXH0 = (byte) (newM[0].length);
		final byte bYH0 = (byte) (newM.length);
		this.m = new byte[bYH0][bXH0];
		Po H1 = null;
		Po H2 = null;
		drt = new boolean[newM.length];
		shd = new boolean[newM.length][newM[0].length];
		int cleanCells = 0;
		mvs = 0;
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
					H1 = new Po(x,y);
				} else if (newM[y][x] == 4) {
					H2 = new Po(x,y);
				} else if (newM[y][x] == 0) {
					cleanCells++;
				}
			}
			if (cleanCells != bXH0) {		//not every cell in this row is clean
				drt[y] = true;					
			}
		}
		if (H1 != null || H2 != null) {
			addPos(H1,H2);
		}
		stt = State.Battle;
		//gen hash
		long[][] zn = z.zNum;
		int[] mapTM2Z = {0,1,1,2,3};
		hk = 0;
		for (byte i = 0; i < bYH0; i++) {
			for (byte j = 0; j < bXH0; j++) {
				hk ^= zn[i*bYH0+j][mapTM2Z[m[i][j]]];
			}
		}}
	void addPos(Po newH1,Po newH2) {
		if (H != null) {
			System.out.println("POSITIONS ALREADY SET");
			m = null;
		} else {
			H = new Po[2];
			oH = new Po[2];
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
				}}
			long[][] zn = z.zNum;
			int[] mapTM2Z = {0,1,1,2,3};
			hk ^= zn[H[0].y*m.length+H[0].x][mapTM2Z[m[H[0].y][H[0].x]]];
			if (!noP2) {
				hk ^= zn[H[1].y*m.length+H[1].x][mapTM2Z[m[H[1].y][H[1].x]]];
			}
			this.m[H[0].y][H[0].x] = 3;
			if (!noP2) {
				this.m[H[1].y][H[1].x] = 4;
			}
			hk ^= zn[H[0].y*m.length+H[0].x][mapTM2Z[m[H[0].y][H[0].x]]];
			if (!noP2) {
				hk ^= zn[H[1].y*m.length+H[1].x][mapTM2Z[m[H[1].y][H[1].x]]];
			}
		}}
	ArrayList<Po> gam(int pos) {
		Po old = this.H[pos];
		ArrayList<Po> moves2;
		moves2 = new ArrayList<Po>();
		byte bYH = (byte) (this.m.length-1);
		byte bYH1 = (byte) (this.m.length-2);
		if (old.y != 0) {
			if (this.m[old.y-1][old.x] == 0) {
				moves2.add(new Po(old.x,old.y-1));
			}
		} else {
			for (byte x = 0; x < this.m[0].length; x++) {
				if (this.m[1][x] == 0) {
					if (x != old.x) {
						moves2.add(new Po(x,1));
					}
				}
			}
		}
		if (old.y != bYH) {
			if (this.m[old.y+1][old.x] == 0) {
				moves2.add(new Po(old.x,old.y+1));
			}
			if (old.y != 0) {
				byte newX = (byte) ((old.x+1)%this.m[0].length);
				if (this.m[old.y][newX] == 0) {
					moves2.add(new Po(newX,old.y));
				}
				newX = (byte) util.negMod(old.x-1,this.m[0].length);
				if (this.m[old.y][newX] == 0) {
					moves2.add(new Po(newX,old.y));
				}
			}} else {
				for (byte x = 0; x < this.m[0].length; x++) {
					if (this.m[bYH1][x] == 0) {
						if (x != old.x) {
							moves2.add(new Po(x,bYH1));
						}
					}
				}
			}
		for (int i = 0; i < moves2.size(); i++) {
			if (mdist(moves2.get(i), this.H[(pos + 1) % 2]) == 1) {
				moves2.remove(i--);
			}
		}
		return moves2;
	}
	int mdist(Po a, Po b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); 
	}
	ArrayList<Po> getAllMoves(Po old) {
		ArrayList<Po> moves2;
		moves2 = new ArrayList<Po>();
		byte bYH = (byte) (this.m.length-1);
		byte bYH1 = (byte) (this.m.length-2);
		if (old.y != 0) {
			if (this.m[old.y-1][old.x] == 0) {
				moves2.add(new Po(old.x,old.y-1));
			}
		} else {
			for (byte x = 0; x < this.m[0].length; x++) {
				if (this.m[1][x] == 0) {
					if (x != old.x) {
						moves2.add(new Po(x,1));
					}
				}
			}
		}
		if (old.y != bYH) {
			if (this.m[old.y+1][old.x] == 0) {
				moves2.add(new Po(old.x,old.y+1));
			}
			if (old.y != 0) {
				byte newX = (byte) ((old.x+1)%this.m[0].length);
				if (this.m[old.y][newX] == 0) {
					moves2.add(new Po(newX,old.y));
				}
				newX = (byte) util.negMod(old.x-1,this.m[0].length);
				if (this.m[old.y][newX] == 0) {
					moves2.add(new Po(newX,old.y));
				}
			}} else {
				for (byte x = 0; x < this.m[0].length; x++) {
					if (this.m[bYH1][x] == 0) {
						if (x != old.x) {
							moves2.add(new Po(x,bYH1));
						}
					}
				}
			}
		return moves2;
	}
	byte posToDir(Po origin,Po p2) {
		int x = p2.x - origin.x;
		int y = p2.y - origin.y;
		if (x == 1) {return 2;} else if (x == -1) {return 4;} else if (y == 1) {return 3;} else if (y == -1) {return 1;} else {return -1;}
	}
	Po dirToPos(byte dir,int pos) {
		Po newH;
		switch(dir) {
		//N
		case 1: {newH = new Po(H[pos].x,H[pos].y-1);break;
		}
		//S
		case 3: {newH = new Po(H[pos].x,H[pos].y+1);break;
		}
		//E
		case 2: {newH = new Po((H[pos].x+1)%m[0].length,H[pos].y);break;
		}
		//W
		case 4: {newH = new Po(util.negMod(H[pos].x-1,m[0].length),H[pos].y);break;
		}
		default: return null;
		}
		return newH;
	}
	Po d2p(byte dir,Po pos) {
		Po newH;
		byte newX = pos.x;
		byte newY = pos.y;
		switch(dir) {
		//N
		case 1: {newY = (byte) (newY-1);break;
		}
		//S
		case 3: {newY = (byte) (newY+1);break;
		}
		//E
		case 2: {newX = (byte) ((pos.x+1)%m[0].length);break;
		}
		//W
		case 4: {newX = (byte) util.negMod(newX-1,m[0].length);break;
		}
		default: return null;
		}
		newH = new Po(newX,newY);
		return newH;
	}
	byte h2d(int heading) {
		switch(heading) {
		case 0: {return 2;
		}
		case 90: {return 3;
		}
		case 180: {return 4;
		}
		case 270: {return 1;
		}
		default: return 0;
		}
	}
	boolean cm(Po newH,Po old) {
		return canMove(newH,old,(byte)0,(byte)m.length);
	}
	boolean canMove(Po newH,Po old,byte bYL,byte bYH) {
		if (newH == null || old == null ) {
			return false;
		}
		if (newH.y <= bYH && newH.y >= bYL) { //array bounds
			if (distManh(newH,old) == 1) {
				if (m[newH.y][newH.x] == 0) {
					return true;
				}
			}}
		return false;
	}
	boolean canMove(Po newH,int pos) {
		if (newH.y < m.length && newH.y >= 0) { //array bounds
			if (distManh(newH,H[pos]) == 1) {
				if (m[newH.y][newH.x] == 0) {
					return true;
				}
			}}
		return false;
	}
	boolean move(Po newMove,int p) {
		if(canMove(newMove,p)) {
			long[][] zn = z.zNum;
			int[] tm2z = {0,1,1,2,3};
			hk ^= zn[H[p].y*m.length+H[p].x][tm2z[m[H[p].y][H[p].x]]];
			m[H[p].y][H[p].x] = (byte) (p+1);
			hk ^= zn[H[p].y*m.length+H[p].x][tm2z[m[H[p].y][H[p].x]]];
			H[p] = new Po(util.negMod(newMove.x,m[0].length),newMove.y);
			if (H[p].y == 0 || H[p].y == m.length-1) {
				for (byte x = 0; x < m[0].length; x++) {
					hk ^= zn[H[p].y*m.length+x][tm2z[m[H[p].y][x]]];
					m[H[p].y][x] = (byte) (p+1);
					hk ^= zn[H[p].y*m.length+x][tm2z[m[H[p].y][x]]];
				}
			}
			hk ^= zn[H[p].y*m.length+H[p].x][tm2z[m[H[p].y][H[p].x]]];
			m[H[p].y][H[p].x] = (byte) (p+3);
			hk ^= zn[H[p].y*m.length+H[p].x][tm2z[m[H[p].y][H[p].x]]];
			drt[H[p].y] = true;
			mvs++;
			if (H[p].y > 1)			shd[H[p].y-1][H[p].x] = true;
			if (H[p].y < m.length-1)	shd[H[p].y+1][H[p].x] = true;
			shd[H[p].y][(H[p].x+1)%m[0].length] = true;
			shd[H[p].y][util.negMod(H[p].x-1,m[0].length)] = true;
			return true;
		}
		return false;
	}
	//undo the newMove
	boolean undoMove(Po oldPosition,int pos) {
		if (oldPosition.y < m.length && oldPosition.y >= 0) { //array bounds
			if (distManh(oldPosition,H[pos]) == 1) {
				if (m[oldPosition.y][oldPosition.x] == (byte) (pos+1)) {	//move back into your own wall
					long[][] zn = z.zNum;
					int[] mapTM2Z = {0,1,1,2,3};
					hk ^= zn[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
					m[H[pos].y][H[pos].x] = 0;
					hk ^= zn[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
					if (H[pos].y == 0 || H[pos].y == m.length-1) {
						for (byte x = 0; x < m[0].length; x++) {
							hk ^= zn[H[pos].y*m.length+x][mapTM2Z[m[H[pos].y][x]]];
							m[H[pos].y][x] = 0;
							hk ^= zn[H[pos].y*m.length+x][mapTM2Z[m[H[pos].y][x]]];
						}
					}
					H[pos] = new Po(util.negMod(oldPosition.x,m[0].length),oldPosition.y);
					hk ^= zn[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
					m[H[pos].y][H[pos].x] = (byte) (pos+3);
					hk ^= zn[H[pos].y*m.length+H[pos].x][mapTM2Z[m[H[pos].y][H[pos].x]]];
					//dirty[H[pos].y] = true;
					mvs--;
					return true;
				}
			}}
		return false;
	}
	int getInt(Po p) {
		return m.length*p.y+p.x;
	}
	TM getCopy() {
		final byte bXH0 = (byte) (m[0].length);
		final byte bYH0 = (byte) (m.length);
		byte[][] newM = new byte[bYH0][bXH0];
		for (byte y = 0; y < bYH0; y++) {
			newM[y] = Arrays.copyOf(m[y],bXH0);
		}
		TM newTM = new TM(newM,noP2);
		return newTM;
	}
	short[][] getBigCopy() {
		short[][] newM = new short[m.length][m[0].length];
		for (byte y = 0; y < newM.length; y++) {
			for (byte x = 0; x < newM[0].length; x++) {
				newM[y][x] = m[y][x];
			}
		}
		return newM;
	}
	int distManh(Po p1,Po p2) {
		if (p1.y == 0 || p1.y == m.length-1 || p2.y == 0 || p2.y == m.length-1) { //only vertical matters
			return Math.abs(p2.y-p1.y);
		} else {
			int[] d = new int[4];
			int xd = Math.abs(util.negMod((p2.x),m[0].length)-util.negMod((p1.x),m[0].length));
			d[0] = Math.abs(p2.y-p1.y)+xd;
			//-1 at the end because this is distance,not index,relating modding
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
	short[][] mapDistance(Po p,int type) {
		BFS bfs = new BFS(this,p,true);
		short[][] smalld = bfs.dst();
		return smalld;
	}
	short[][] mapVoronoi(int type,int[] territory,boolean[] endgame) {
		final boolean oldV = false;
		final boolean newV = true;
		//long startTime = System.nanoTime();
		endgame[0] = true;
		short[][] mVor = null;
		if (oldV) {
			short[][] md1 = this.mapDistance(this.H[0],type);
			short[][] md2 = this.mapDistance(this.H[1],type);
			short[][][] md = {md1,md2};
			//byte[][] m1 = this.mapDistance(this.H[1],dijkstra);
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
						} else { //equal,we're on the ridge
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
		short[][] mVor2 = null;
		BFS bfs = null;
		if (newV) {
			bfs = new BFS(this,this.H[0],true);
			bfs.start2 = this.H[1];
			mVor2 = bfs.duDst(endgame);
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
					}}
				rbCounter++;
			}
			for (int i = 0; i < 3; i++) {
				territory[i] = territory[i] - Math.abs(redTiles[i]-blackTiles[i]);
			}
		}
		return mVor2;
	}
	short[][] mapVoronoiSolo(int type,int[] territory,boolean[] endgame) {
		short[][] mVor = null;
		BFS bfs = new BFS(this,this.H[0],true);
		short[][] md1 = bfs.dst(); //DONT CLEAR this.H[1]
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
	short hValue(int pos,int type) {
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
			stt = State.Survival;
		} else {
			stt = State.Battle;
		}
		hv = (short) (t[pos]-t[(pos+1)%2]);
//		if (mdist(this.H[0], this.H[1]) == 1) {
//			// Game could draw right now.
//			hv = -20000;
//		}
		if (stt == State.Survival) {
			if (hv == 0) {
				hv = 0;
			} else if (hv > 0) {
				hv = (short) (hv+1000);
			} else {
				hv = (short) (hv - 1000);
			}
		}
		return hv;
	}
	int heading(Po origin,Po p2) {
		double x = p2.x-origin.x*0.99999;
		double y = p2.y-origin.y;
		int bias = 0;
		if (x < 0) {
			bias = 180;
		} else if (y < 0 ) {
			bias = 360;
		}
		//double r = Math.hypot(x,y);
		return (int) Math.round(Math.toDegrees(Math.atan((y)/(x))))+bias;
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
				sb.append("\thVals:"+this.hValue(0,1)+"\t"+this.hValue(1,1));
			}
		}
		return sb.toString();
	}
	@Override
	public int hashCode() {
		//computed incrementally
		return (int)hk;
	}
	long hashCodeLong() {
		return hk;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TM other = (TM) obj;
		if (m.length != other.m.length) {
			return false;
		}
		if (!Arrays.deepEquals(m,other.m)) {
			return false;
		}
		return true;
	}
}
static zorb z = new zorb();
static class zorb {
	long zNum[][];
	zorb() {
		Random r = new Random(0);
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
				}}
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
		zNum = new long[map_sizeSquared][4];
		for (int i = 0; i < map_sizeSquared; i++) {
			for (int j = 0; j < 4; j++) {
				if (z[i][j] == 0) {
					z[i][j] = r.nextLong();
				}
				zNum[i][j] = z[i][j];
			}
		}
	}
}
static class util {
	static int negMod(int x,int mod) {
		if (x >= 0) {
			return x%mod;
		} else {
			return (mod+(x%mod))%mod;
		}
	}
	static void fill(short[][] target,short v) {
		for (short i = 0; i < target.length; i++) {
			for (short j = 0; j < target[0].length; j++) {
				target[i][j] = v;
			}
		}
	}
}
static abstract class Rbt {
	State state;
	long totalTime;
	long timer;
	long timeLimit;
	int pos;
	Po newP;
	Rbt(int pos) {
		this.pos = pos;
		state = State.Survival;
		timeLimit = (long) (5000*0.95);
		totalTime = 0;
	}
	abstract Po nextMove(TM m,ArrayList<Po> p,boolean edgeCase);
	Po move(TM m) {
		timer = System.currentTimeMillis();
		ArrayList<Po> p = m.gam(pos);
		if (p.size() > 0) {
			if (m.H[pos].y == 0 || m.H[pos].y == m.m.length-1) {//edge case
				newP = nextMove(m,p,true);
			} else {
				newP = nextMove(m,p,false);
			}
		} else{
			newP = m.dirToPos((byte) 2,pos);
		}
		m.oH[pos] = m.H[pos];
		//currentPos = newP;
		totalTime += (System.currentTimeMillis()-timer);
		return newP;
	}
	public abstract String toString();
}
static class TptV {short bv,a,b,d,exdep,fl;byte bpx,bpy,bp2x,bp2y,rmx,rmy;boolean old;boolean chkd;TptV() {super();this.bv = 0;this.a = 0;this.b = 0;this.bpx = 0;this.bpy = 0;this.d = 0;this.bp2x = 0;this.bp2y = 0;this.fl = 0;this.exdep = 0;this.old= false;this.chkd= false;}TptV(short bestvalue,short alpha,short beta,byte bestPx,byte bestPy,short d,short expireDepth,byte bestP2x,byte bestP2y,short flag,byte rootMoveX,byte rootMoveY) {super();this.bv = bestvalue;this.a = alpha;this.b = beta;this.bpx = bestPx;this.bpy = bestPy;this.d = d;this.exdep = expireDepth;this.bp2x = bestP2x;this.bp2y = bestP2y;this.fl = flag;this.rmx = rootMoveX;this.rmy = rootMoveY;this.old= false;this.chkd= false;}public TptV clone() {return new TptV(bv,a,b,bpx,bpy,d,exdep,bp2x,bp2y,fl,rmx,rmy);}
@Override
public String toString() {return "TptEntry [bestvalue="+bv+",alpha="+a+",beta="+b+ ",bestPx="+bpx+",bestPy="+bpy+ ",bestP2x="+bp2x+ ",bestP2y="+bp2y+",  d="+d+",flag="+fl+"]";}}
static class Ho implements Comparable<Ho> {int x;int y;Ho(int x,int y) {this.x = x;this.y = y;}
@Override
public int compareTo(Ho o) {if (this.y == o.y) {return 0;}else if (this.y > o.y) {return 1;}else {return -1;}}public String toString() {StringBuilder sb = new StringBuilder();sb.append(x);sb.append(':');sb.append(y);return sb.toString();}}

static class MgT extends Rbt {
	HashMap<Long,TptV> transPosTable;LinkedHashSet cache;int maxdepth;int endDepth;int startDepth;short bestAlpha;
	final short negInf = (short) -5000;final short posInf = (short) 5000;final short hEXACT = 0x04;final short hLOWER = 0x02;final short hUPPER = 0x01;final int maxTableSize = 140000;
	short[][] pvMap;short[][] hMap;String criticalError;long[] timers;Tree<TM> tr;
	int variation;int currentSearchDepth;int moves;final int v = 19;
	boolean doCV,doDijkstra,doAssumeFirstLeft,firstMove,doIterativeDeepening,doAlphaBeta,doTimeLimit,doCache,doWCache,doSingleIncrement;
	private Po bestMove;
	static boolean doTrimTPT;
	MgT(int pos) {
		super(pos);ArrayList<ArrayList<Integer>> params = new ArrayList<ArrayList<Integer>>();ArrayList<Integer> par;maxdepth = 6;firstMove = true;timers = new long[5];par = new ArrayList<Integer>();params.add(0,par);par.add(0);par.add(1);criticalError = "";
		doCV = true;doDijkstra = true;doAssumeFirstLeft = false;doIterativeDeepening = true;doTimeLimit = true;doAlphaBeta = true;
		doCache = false;doWCache = false;doSingleIncrement = false;doTrimTPT = true;state = State.Battle;
		cache = new LinkedHashSet<TM>();
		transPosTable = new HashMap<Long,TptV>();
	}
	@Override
	Po nextMove(TM m,ArrayList<Po> p,boolean edgeCase) {
		state = State.Battle;
		Po nextMove;
		m.hValue(pos,1);
		//battle mode until BFS can't find enemy's head
		if (state == State.Battle) {
			BFS bfs = new BFS(m,m.H[0],true);
			bfs.start2 = m.H[1];
			if (m.stt == State.Survival) {
				state = State.Survival;
			}
		}
		switch (state) {
		case Battle: {
//			int ePos = (pos + 1) % 2;
//			Po enemy = m.H[ePos];
//			ArrayList<Po> eMoves = m.getAllMoves(enemy);
//			TM g = m.getCopy();
//			Po nMove
//			for (int i = 0; i < eMoves.size(); i++) {
//				g.move(eMoves.get(i), ePos);
//				nextMove = nextBattleMove(m,p,edgeCase);
//			}
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
		return nextMove;
	}
	Po nextBattleMove(TM m,ArrayList<Po> p,boolean edgeCase) {
		if (doAssumeFirstLeft) {
			if (pos == 0 && firstMove) {
				firstMove = false;
				return m.dirToPos((byte) 4,pos);
			}
		}
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
		util.fill(pvMap,(short) 0);
		hMap = m.getBigCopy();
		util.fill(hMap,(short) 0);
		Po po = null;
		int depthIncrement;
		if (doSingleIncrement) {
			startDepth -= 1;
			depthIncrement = 2;
		} else {
			depthIncrement = 2;
		}
		this.moves += 2;
		criticalError = "";
		Tree<PoV> tr2 = null;
		for (int d = startDepth; d <= endDepth; d=d+depthIncrement) {
			currentSearchDepth = d;
			cache = new LinkedHashSet<TM>();
			tr = new Tree<TM>(m.getCopy(),pos);
			if (doAlphaBeta) {
				Po rootMove = new Po(-1,-1);
				tr2 = new Tree<>(new PoV(rootMove, 0), pos);
				try {
					tr.rt.a = negaMaxAB2(tr,tr.rt,d,cache,negInf,posInf,pos,rootMove, tr2, tr2.rt);
				} catch (Exception e) {
					String msg = e.getMessage();
					if (!msg.equals("timeout")) {
						e.printStackTrace();
					}
				}}
			//if time limit expired take last round's best go
			if (((System.currentTimeMillis() - timer > timeLimit) && doTimeLimit)) {
				if (po == null) {	//didn't complete 1 ply
					po = p.get(0);
					criticalError += "TIMEOUT: MT found no moves,using default\n";
					System.out.println("MT found no moves,using default");
				}
				break;
			}
			//the current ply completed,so get the best move
			ArrayList<PoV> bestMoves = new ArrayList<>();
			for (int i = 0; i < tr2.rt.c.size(); i++) {
				bestMoves.add(tr2.rt.c.get(i).e);
			}
			Collections.sort(bestMoves);
			Collections.reverse(bestMoves);
			ArrayList<Po> equalmoves = new ArrayList<Po>();
			equalmoves.add(bestMoves.get(0).toPo());
			if (equalmoves.size() > 1) {	//take move that is closest to enemy
				int minDist = 100000;
				int minIdx = 0;
				for (int i = 0; i < equalmoves.size(); i++) {
					int dm = m.distManh(equalmoves.get(0),m.H[(pos+1)%2]);
					if (dm < minDist) {
						minDist = dm;
						minIdx = i;
					}
				}
				po = equalmoves.get(minIdx);
			} else if (equalmoves.size() == 1) {
				po = equalmoves.get(0);
			} else {
				//no moves found AT ALL,serious problem should be reported
				if (po == null) {	//didn't complete 1 ply
					criticalError += "NOCACHE: MT found no moves,using default\n";
					po = p.get(0);
				}
			}
			if (bestAlpha == negInf || bestAlpha == posInf) {	//game over: we've won/lost
				break;
			}
		}
		if (po == null) {
			System.out.println("po == null");
		}
		return po;
	}
	boolean store(TM map,int d,short bestvalue,short alpha,short beta,Po bestP,Po bestP2,short flag,Po rootMove) {
		boolean replace = false;
		boolean newEntry = false;
		if (transPosTable.containsKey(map.hashCodeLong())) {
			TptV val = transPosTable.get(map.hashCodeLong());
			if (val.d < d) {
				replace = true;
			} else if (val.d == d && ((val.fl&flag) == 0)) {	//just update
				if (val.fl > flag) {
					if (val.bpx != -1)	val.bpx = bestP.x;
					if (val.bpy != -1)	val.bpy = bestP.y;
					if (val.bp2x != -1)	val.bp2x = bestP2.x;
					if (val.bp2y != -1)	val.bp2y = bestP2.y;
				}
				val.fl = (short) (val.fl|flag);
				if (flag == hEXACT) {} else if (flag == hLOWER) {
					val.a = alpha;
				} else {
					val.b = beta;
				}
				transPosTable.put(map.hashCodeLong(),val);
			}
		} else {
			newEntry = true;
		}
		if (replace || (newEntry && transPosTable.size() <= maxTableSize)) {
			TptV values = new TptV(bestvalue,alpha,beta,(byte)bestP.x,(byte)bestP.y,(byte)d,(byte)(currentSearchDepth-d+moves),(byte)bestP2.x,(byte)bestP2.y,(byte)flag,(byte)rootMove.x,(byte)rootMove.y);
			transPosTable.put(map.hashCodeLong(),values);
		}
		if (newEntry && transPosTable.size() > maxTableSize) {
			return false;
		}
		return true;
	}
	boolean lookup(TM map,TptV[] hData) {
		if (transPosTable.containsKey(map.hashCodeLong())) {
			TptV hStore = transPosTable.get(map.hashCodeLong());
			hData[0] = hStore.clone();
			if (hStore.old) {}
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	short negaMaxAB2(Tree<TM> tree,No<TM> c,int depth,LinkedHashSet cache,short alpha,short beta,int MaxPos,Po rootMove, Tree<PoV> tree2, No<PoV> root2) throws Exception {
		if ((System.currentTimeMillis() - timer > timeLimit) && doTimeLimit) {
			throw new Exception("timeout");
		}
		TM cM = c.e;
		long temp = System.nanoTime();
		Po sP = null;
		Po sP2 = null;
		if (doWCache) {
			short val = 0;
			temp = System.nanoTime();
			if (v >= 19) {
				TptV hData = new TptV();
				hData.a = alpha;
				hData.b = beta;
				TptV[] hDataHolder = {hData};
				if (this.lookup(cM,hDataHolder)) {
					timers[1] += System.nanoTime()-temp;
					hData = hDataHolder[0];
					if (hData.d >= depth) {
						if ((hData.fl & hEXACT) == hEXACT) {
							val = hData.bv;
							return val;
						}
						if (((hData.fl & hLOWER) == hLOWER) && (hData.a > alpha)) {
							alpha = hData.a;
						} else if (((hData.fl & hUPPER) == hUPPER) && (hData.b < beta)) {
							beta = hData.b;
						}
						if (alpha >= beta)
						{
							val = hData.bv;
							return val;
						}
					}
					sP = new Po(hData.bpx,hData.bpy);
					sP2 = new Po(hData.bp2x,hData.bp2y);
				}
			}}
		Po bestP = new Po(-1,-1);
		Po bestP2 = new Po(-1,-1);
		//short hf = hLOWER;
		ArrayList<Po> mvs = cM.gam(c.pos);
		timers[0] += System.nanoTime()-temp;
		if (mvs.size() == 0) {	//this position has lost
			return negInf;
		}
		ArrayList<Po> enemyMoves = cM.gam((c.pos+1)%2);
		if (enemyMoves.size() == 0) {	//this position wins
			return posInf;
		}
		if (depth > 0) {
			pvMap[cM.H[c.pos].y][cM.H[c.pos].x]++;
			pvMap[cM.H[(c.pos+1)%2].y][cM.H[(c.pos+1)%2].x]++;
		}
		if (currentSearchDepth - depth == 1) {
			rootMove = new Po(cM.H[(c.pos+1)%2]);
		}
		if (depth <= 0) {
			int type = doDijkstra ? 1 : 2;
			temp = System.nanoTime();
			short val = 0;
			temp = System.nanoTime();
			if (doWCache) {
				val = cM.hValue(MaxPos,type);
				timers[2] += System.nanoTime()-temp;
				hMap[cM.H[c.pos].y][cM.H[c.pos].x]++;
				temp = System.nanoTime();
				if (v >= 19) {
					if(v <= alpha)
						this.store(cM,0,negInf, val,beta,bestP,bestP2,hLOWER,rootMove);
					else if(v >= beta)
						this.store(cM,0,negInf,alpha,val,bestP,bestP2,hUPPER,rootMove);
					else                           // a true minimax value
						this.store(cM,0,val  ,alpha,beta,bestP,bestP2,hEXACT,rootMove);
				}
				timers[1] += System.nanoTime()-temp;
			} else {
				val = cM.hValue(MaxPos,type);
				timers[2] += System.nanoTime()-temp;
			}
			c.a = val;
			return val;
		}
		//int alpha = Integer.MIN_VALUE;
		short localalpha = alpha;
		short bestvalue = negInf;
		short value;
		Po currentP = new Po(cM.H[c.pos]);
		if (v >= 18) {
			int moveOrdering;
			//moveOrdering = 0; //None
			//moveOrdering = 1; //Random
			//moveOrdering = 2; //TPT
			moveOrdering = 2;
			//moves.add(moves.remove(0));
			if (moveOrdering == 0) {} else if (moveOrdering == 1) {
				Collections.shuffle(mvs);
			} else if (moveOrdering == 2) {
				if ((sP != null) && (sP.x != -1)) {
					for (int i = 0; i < mvs.size(); i++) {
						if (sP.equals(mvs.get(i))) {
							mvs.add(0,mvs.remove(i));
							break;
						}
					}}
				if ((sP2 != null) && (sP2.x != -1)) {
					for (int i = 1; i < mvs.size(); i++) {
						if (sP2.equals(mvs.get(i))) {
							mvs.add(1,mvs.remove(i));
							break;
						}
					}
				}
			} else if (moveOrdering == 3) {
				ArrayList<Ho> moveholder = new ArrayList<Ho>();
				ArrayList<Po> newOrder = new ArrayList<Po>();
				for (int i = 0; i < mvs.size(); i++) {
					TM newM = cM.getCopy();
					if (!newM.move(mvs.get(i),c.pos)) {
						System.out.println("MOVE ORDER FAIL");
					}
					moveholder.add(new Ho(i,newM.hValue(c.pos,1)));
				}
				Collections.sort(moveholder);
				//Collections.reverse(moveholder);
				for (int i = 0; i < moveholder.size(); i++) {
					newOrder.add(mvs.get(moveholder.get(i).x));
				}
				mvs = newOrder;
			}
		}
		int depthLimit = currentSearchDepth-depth;
		if (depthLimit%2 == 0) {
			depthLimit++;
		}
		for (int i = 0; i < mvs.size(); i++) {
			if(cM.move(mvs.get(i),c.pos)) {
				if (doCache) {cache.add(cM);}
				No<TM> newChild = tree.add(cM,c);
				No<PoV> newChild2 = tree2.add(new PoV(mvs.get(i), 0),root2);
				value  = (short) (-1*negaMaxAB2(tree,newChild,depth-1,cache,(short)(-1*beta),(short)(-1*localalpha),MaxPos,rootMove, tree2, newChild2));
				newChild2.e.v = value;
				if (value > bestvalue) {
					bestP2 = new Po(bestP);
					bestP = new Po(cM.H[c.pos]);
				}
				bestvalue = (short) Math.max(value,bestvalue);
				if(bestvalue>=beta) {
					if (!cM.undoMove(currentP,c.pos)) {
						System.out.print("FAILED UNDO MOVE");
					}
					//hf = hUPPER;
					break;
				}
				if(bestvalue>localalpha) {
					localalpha=bestvalue;
				}
				if (!cM.undoMove(currentP,c.pos)) {
					System.out.print("FAILED UNDO MOVE");
				}
			}}
		c.a = bestvalue;
		if (doWCache) {
			temp = System.nanoTime();
			//this.store(currentM,depth,bestvalue,alpha,beta,bestP,bestP2,hf);
			if (v >= 19) {
				if(bestvalue <= alpha)
					this.store(cM,depth,bestvalue,alpha,beta,bestP,bestP2,hLOWER,rootMove);
				else if(bestvalue >= beta)
					this.store(cM,depth,bestvalue,alpha,beta,bestP,bestP2,hUPPER,rootMove);
				else
					this.store(cM,depth,bestvalue,alpha,beta,bestP,bestP2,hEXACT,rootMove);
			}
			timers[1] += System.nanoTime()-temp;
		}
		if (depth == 2) {
			this.bestMove = bestP;
		}
		return bestvalue;
	}
	@Override
	public String toString() {
		return "MT:"+variation;
	}
	Po nextSurvivalMove2(TM m,ArrayList<Po> p, boolean edgeCase,boolean followWall) {
		//survival
		//boolean followWall = false;
		//find longest path
		//hug the walls
		//find the move that has the least number of edges,but not zero
		ArrayList<Ho> h = new ArrayList<Ho>();
		for (int i = 0; i < p.size(); i++) {
			ArrayList<Po> nMov = m.getAllMoves(p.get(i));
			if (nMov.size() >0) {
				h.add(new Ho(i,nMov.size()));
			}
		}
		Po newP = null;
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
			if (m.oH[pos] != null) {
				int heading = (int) (Math.round(m.heading(m.oH[pos],m.H[pos])/90.0)*90);
				byte dir = m.h2d(heading);
				Po pos2 = m.d2p(dir,m.H[pos]);
				Po p05 = null;
				Po p15 = null;
				if (pos2 != null) {
					p05 = m.d2p((byte)(util.negMod(dir+1-1,4)+1),pos2);
					p15 = m.d2p((byte)(util.negMod(dir-1-1,4)+1),pos2);
				}
				if (!m.cm(p05,newP) && !m.cm(p15,newP)) {
					//yes,do multi BFS until all but 1 terminates
					ArrayList<BFS> bfs = new ArrayList<BFS>();
					ArrayList<Po> nMov = m.getAllMoves(m.H[pos]);
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
							}}
						return bfs.get(0).st;
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
	}
	public static final int[] dx = {-1, 0, 1, 0};
	public static final int[] dy = {0, 1, 0, -1};
	Po nextSurvivalMove3(TM m,ArrayList<Po> p, boolean edgeCase,boolean followWall) {
		//survival
		//find longest path
		//hug the walls
		//find the move that has the least number of edges,but not zero
		Random rand = new Random(0);
		int maxMoves = 0;
		Po newP = null;
		int[] movePref = {1, 2, 3, 0};
		long t1 = System.currentTimeMillis();
		for (int j = 0; j < 1000000000; j++) {
			if (j % 100 == 0) {
				if (System.currentTimeMillis() - t1 > this.timeLimit) {
					break;
				}
			}
			TM g = m.getCopy();
			int moves = 0;
			Po first = null;
			int[] c1 = new int[]{g.H[pos].x, g.H[pos].y};
			while (true) {
				int[][] options = new int[4][3];
				int[] dist = new int[4];
				int[] finalc = {-1, -1};
				int possible = 0;
				for (int i = 0; i < dx.length; i++) {
					final int nx = c1[0] + dx[i];
					final int ny = c1[1] + dy[i];
					if (g.m[ny][nx] == 0) {
						int nm = 0;
						for (int k = 0; k < dx.length; k++) {
							final int n2x = nx + dx[k];
							final int n2y = ny + dy[k];
							if (g.m[n2y][n2x] == 0) {
								nm++;
							}
						}
						options[i] = new int[]{nx, ny, nm};
						dist[nm]++;
						possible++;
					} else {
						options[i] = new int[]{nx, ny, -1};
					}
				}
				if (rand.nextInt(1000) >= 0) {
					// normal
					for (int k = 0; k < movePref.length; k++) {
						if (dist[movePref[k]] > 0) {
							int choice = rand.nextInt(dist[movePref[k]]);
							for (int i = 0; i < options.length; i++) {
								if (options[i][2] != movePref[k]) {
									choice++;
								} else if (i == choice) {
									finalc[0] = options[i][0];
									finalc[1] = options[i][1];
									break;
								}
							}
						}
						if (finalc[0] != -1) {
							break;
						}
					}
				}
				if (possible == 0) {
					break;
				}
				c1 = finalc;
				Po nP = new Po(finalc[0], finalc[1]);
				if (first == null) {
					first = nP;
				}
				if (!g.move(nP, pos)) {
					System.out.println("Invalid move!");
				}
				moves++;
				
			}
			if (moves > maxMoves) {
//				System.out.println("New max: " + moves);
				maxMoves = moves;
				newP = first; 
			}
		}
		if (newP == null) {
			System.out.println("newP == null");
		}
//		System.out.println("fast best: " + maxMoves);
		return newP;
	}
	Po nextSurvivalMove(TM m,ArrayList<Po> p, boolean edgeCase,boolean followWall) {
		//survival
		//find longest path
		//hug the walls
		//find the move that has the least number of edges,but not zero
		Random rand = new Random(0);
		int maxMoves = 0;
		Po newP = null;
		long t1 = System.currentTimeMillis();
		for (int j = 0; j < 2000000000; j++) {
			if (j % 100 == 0) {
				if (System.currentTimeMillis() - t1 > this.timeLimit) {
					break;
				}
			}
			TM g = m.getCopy();
			int moves = 0;
			Po first = null;
			while (true) {				
				Po c = g.H[pos];
				ArrayList<Po> am = g.getAllMoves(c);
				if (am.isEmpty()) {
					break;
				}
				moves++;
				ArrayList<Ho> h = new ArrayList<Ho>();
				for (int i = 0; i < am.size(); i++) {
					ArrayList<Po> nMov = g.getAllMoves(am.get(i));
					if (nMov.size() >0) {
						h.add(new Ho(i,nMov.size()));
					}
				}
				Po nP = null;
				if (h.size() > 0) {
					Collections.sort(h);
					int min = h.get(0).y;
					ArrayList<Po> possible = new ArrayList<>();
					for (int i = 0; i < h.size(); i++) {
						if (h.get(i).y > min) {
							break;
						}
						possible.add(am.get(h.get(i).x));
					}
					if (rand.nextInt(100) == 0) {
						nP = am.get(h.get(rand.nextInt(h.size())).x);	
					} else {
						nP = possible.get(rand.nextInt(possible.size()));
					}
				} else { //no good moves left
					if (am.size() > 0) {
						nP = am.get(0);
					} else {
						nP = g.dirToPos((byte) 1,pos);
					}
				}
				if (first == null) {
					first = nP;
				}
				g.move(nP, pos);
			}
			if (moves > maxMoves) {
				maxMoves = moves;
				newP = first; 
			}
		}
		if (newP == null) {
			System.out.println("newP == null");
		}
		return newP;
	}
	void trimTPT(Po nextMove) {
		if (nextMove == null) {
			return;
		}
		//trim
		ArrayList<Long> old = new ArrayList<Long>();
		for (Entry<Long,TptV> e : this.transPosTable.entrySet()) {
			if (e.getValue().exdep < this.moves) {
				old.add(e.getKey());
				e.getValue().old = true;
			} else {
				if (e.getValue().rmx != nextMove.x || e.getValue().rmy != nextMove.y) {
					if (!e.getValue().chkd) {
						old.add(e.getKey());
						e.getValue().old = true;
					}
				} else {
					e.getValue().chkd = true;
				}
			}}
		doTrimTPT = true;
		if (doTrimTPT) {
			for (int i = 0; i < old.size(); i++) {
				this.transPosTable.remove(old.get(i));
			}
		}
	}
}
static class Tree<E> {
	No<E> rt;
	int size;
	int maxDepth;
	Tree(E rootData,int pos) {
		rt = new No<E>(rootData,null,pos);
		rt.d = 0;
		size = 1;
		maxDepth = 0;
	}
	No<E> add(E element,No<E> parent) {
		No<E> newChild = new No<E>(element,parent,(parent.pos+1)%2);
		parent.c.add(newChild);
		size++;
		if (newChild.d > maxDepth) {maxDepth = newChild.d;
		}
		return newChild;
	}
}
static class MyScanner {
	BufferedReader br;
	StringTokenizer st;
	MyScanner(InputStream in) {
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
	int nextInt() {
		return Integer.parseInt(next());
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
}}