import java.util.HashSet;
import java.util.LinkedList;

public class ExactCover {
	public static int[][] M;
	
	public static HashSet<HashSet<Integer>> exactCover(LinkedList<Integer> X,HashSet<Integer> C) {	
		HashSet<HashSet<Integer>> P = new HashSet<HashSet<Integer>>();
		if(X.size()==0)	{P.add(new HashSet<Integer>()); return P;}		
		int x = X.getFirst();
		for(int S : C) {
			if(M[S-1][x-1]==1) {
				LinkedList<Integer> X_prime = (LinkedList<Integer>) X.clone();
				HashSet<Integer> C_prime = (HashSet<Integer>) C.clone();
				for(int y=1; y<=M[0].length;y++) {
					if(M[S-1][y-1] == 1) {
						X_prime.remove((Object) y);
						for(int T : C) {
							if(M[T-1][y-1] == 1) {C_prime.remove(T);}
						}
					}					
				}
				for(HashSet<Integer> Partition : exactCover(X_prime,C_prime)) {
					Partition.add(S);
					P.add(Partition);
				}
			}
		}
		return P;
	}

	public static void print(HashSet<HashSet<Integer>> P) {
		for(HashSet<Integer> partitions : P) {
			System.out.print("une solution est :");
			for(int i : partitions) { 
				System.out.print("{ ");
				for(int j = 0; j<M[i-1].length;j++)
					if(M[i-1][j] == 1) System.out.print(j+1 + " ");
				System.out.print("}");
			}
			System.out.println();
		}
	}
}
