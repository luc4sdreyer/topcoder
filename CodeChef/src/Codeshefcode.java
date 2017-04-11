import java.io.* ;
import java.util.* ;
public class Codeshefcode {
	final static int r = 1000000007 ;
	static FileInputStream in=null ;
	static FileOutputStream out=null ;
	static FasterScanner input = new FasterScanner() ;
	static FasterScanner infile ;
	static PrintWriter outfile  ;
	static PrintWriter output = new PrintWriter(System.out) ;
	static int Wall[][] ;
	static int id[] ;
	static int ct[] ;
	static int max ;
	static int Queries[][] ;
	public static void main(String[] args) throws IOException{
		int T = input.nextInt() ;
		int N,M,Q ;
		for(int i=0 ; i<T ; i++){
			N = input.nextInt() ;
			M = input.nextInt() ;
			Q = input.nextInt() ;
			Queries = new int[Q][5] ;
			int ansQ[] = new int[Q] ;
		    for(int j=0 ; j<Q ; j++){
				int type = input.nextInt() ;
				Queries[j][0]=type ;
				switch(type){
				    case 3:{
				    	Queries[j][1]=input.nextInt() ;
				    	Queries[j][2]=input.nextInt() ;
				    	Queries[j][3]=input.nextInt() ;
				    	Queries[j][4]=input.nextInt() ;
					} 
 				    break ;
				    case 4: break ;				    
				    default :{
				    	Queries[j][1]=input.nextInt() ;
					    Queries[j][2]=input.nextInt() ;
				    }
				    break ;
				}
			}
		    Wall = new int[N+1][M+1] ;
			for(int j=1 ; j<=N ; j++)
				for(int k=1 ; k<=M ; k++)
					for(int l=1 ; l<=4 ; l++)
						Wall[j][k]=1 ;
			for(int j=1 ; j<=M ; j++){
				Wall[1][j]*=5 ;
				Wall[N][j]*=2 ;
			}		
			for(int j=1 ; j<=N ; j++){
				Wall[j][1]*=7 ;
				Wall[j][M]*=3 ;
			}		
			for(int j=0 ; j<Q ; j++){
				int type = Queries[j][0] ;
				if(type==1){
					int x = Queries[j][1] ;
					int y = Queries[j][2] ;
					if(Wall[x][y]%3==0){
					     Queries[j][0]=0 ;
					     continue ;
					}						
					Wall[x][y]*=3 ;
					Wall[x][y+1]*=7 ;
				}
				else if(type==2){
					int x = Queries[j][1] ;
					int y = Queries[j][2] ;
					if(Wall[x][y]%2==0){
					     Queries[j][0]=0 ;
					     continue ;
					}					
					Wall[x][y]*=2 ;
					Wall[x+1][y]*=5 ;				
				}
			}
			int size = N*M ;
			id = new int[size+1] ;
			ct = new int[size+1] ;
			Arrays.fill(ct,1) ;
			for(int j=1 ; j<=size ; j++){
				id[j]=j ;
			}
			for(int j=1 ; j<=N ; j++){
				for(int k=1 ; k<=M ; k++){
					if(Wall[j][k]%2!=0)
						Merge(((j-1)*M+k),(j*M+k)) ;
					if(Wall[j][k]%3!=0)
						Merge(((j-1)*M+k),((j-1)*M+k+1)) ;
					if(Wall[j][k]%5!=0)
						Merge(((j-1)*M+k),((j-2)*M+k)) ;
					if(Wall[j][k]%7!=0)
						Merge(((j-1)*M+k),((j-1)*M+k-1)) ;						
				}
			}
		    max=0 ;
			for(int j=1 ; j<=size ; j++)
				if(max<ct[j])
					max=ct[j] ;
		    for(int j=Q-1 ; j>=0 ; j--){
		          int type = Queries[j][0] ;
		          switch(type){
		              case 0 : ansQ[j]=-1 ;
		              break ;
		              case 3 :  ansQ[j]=find(((Queries[j][1]-1)*M+Queries[j][2]),((Queries[j][3]-1)*M+Queries[j][4])) ;
		              break ;
		              case 4 :  ansQ[j]=max ;
		              break ;
		              default :{
		            	        ansQ[j]=-1 ;
		            	        int x = Queries[j][1] ;
							    int y = Queries[j][2] ;
							    if(type==1)
							        Merge(((x-1)*M)+y,((x-1)*M)+(y+1)) ;
							    else
							    	Merge((x-1)*M+y,(x*M)+y) ;							    	
		              }
		              break ;
		          }
		    }
		    long sum=0 ;
		    for(int j=0 ; j<Q ; j++){
		        if(ansQ[j]!=-1)
		        	sum+=ansQ[j] ;
		    }
		    output.println(sum) ;		    
		}		
		Finish() ;	
	}
	static int find(int i,int j){
	     return root(i)==root(j) ? 1 : 0 ;
	}	
	static void Merge(int i,int j){
		i = root(i) ;
		j = root(j) ;
		if(i==j)
			return ;
		if(ct[i]>ct[j]){
			id[j]=i ;
			ct[i]+=ct[j] ;
			ct[j]=0 ;
			if(ct[i]>max)
			    max=ct[i] ;
		}else{
			id[i]=j ;
			ct[j]+=ct[i] ;
			ct[i]=0 ;
			if(ct[j]>max)
			    max=ct[j] ;
		}		
	}	
	static int root(int i){
		while(id[i]!=i){
			id[i]=id[id[i]] ;
			i=id[i] ;
		}
		return i ;
	}		
	static void Start() throws IOException{
		out = new FileOutputStream("C:\\Users\\temporary\\Desktop\\output.txt") ; 
        outfile = new PrintWriter(out) ; 
        String s = "C:\\Users\\temporary\\Desktop\\" ;
	    s += input.nextToken() ;
	    s+=".in" ;
	    in = new FileInputStream(s) ;
	    infile = new FasterScanner(in) ; 
	}
	
	static void Finish(){
		output.flush();
		output.close();
		
	}
	static void FinishF(){
		outfile.flush();
		outfile.close();
	}			
}	
 
 
class FasterScanner{
	BufferedReader br;
	StringTokenizer st;
	public FasterScanner(){
		br=new BufferedReader(new InputStreamReader(System.in));
	}
	public FasterScanner(FileInputStream in){
		br=new BufferedReader(new InputStreamReader(in));
	}
	String nextToken(){
		while(st==null||!st.hasMoreElements())
			try{st=new StringTokenizer(br.readLine());}catch(Exception e){}
		return st.nextToken();
	}
	int nextInt(){
		return Integer.parseInt(nextToken());
	}
	long nextLong(){
		return Long.parseLong(nextToken());
	}
	double nextDouble(){
		return Double.parseDouble(nextToken());
	}
} 