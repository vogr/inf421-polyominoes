import java.util.*;

public class ExactCover {
	int[][] M;


	public ExactCover (int[][] M) {
		this.M = M;
	}

	public HashSet<LinkedHashSet<Integer>> exactCover(HashSet<Integer> X, LinkedHashSet<Integer> C) {

		HashSet<LinkedHashSet<Integer>> P = new HashSet<>();
		if (X.size() == 0)	{ P.add(new LinkedHashSet<>()); return P; }
		int x = X.iterator().next();
		for(int s : C) {
			if(M[s][x]==1) {
				HashSet<Integer> X_prime = new HashSet<>(X);
				LinkedHashSet<Integer> C_prime = new LinkedHashSet<>(C);
				for(int y = 0 ; y < M[0].length ; y++) {
					if(M[s][y] == 1) {
						X_prime.remove(y);
						for(int t : C) {
							if(M[t][y] == 1) {C_prime.remove(t);}
						}
					}
				}
				for(LinkedHashSet<Integer> partition : exactCover(X_prime,C_prime)) {
					partition.add(s);
					P.add(partition);
				}
			}
		}
		return P;
	}

	public HashSet<LinkedHashSet<Integer>> solve() {
		HashSet<Integer> X = new HashSet<>();
		for (int x = 0; x < M[0].length; x++) {
			X.add(x);
		}
		LinkedHashSet<Integer> C = new LinkedHashSet<>();
		for (int c = 0; c < M.length; c++) {
			C.add(c);
		}
		return exactCover(X,C);
	}

	public String M_repr() {
		StringBuilder b = new StringBuilder();
		for(int[] l : M) {
			b.append("[ ");
			for(int v : l) {
				b.append(v);
				b.append(" ");
			}
			b.append("]\n");
		}
		return b.toString();
	}
	public void print_solutions(HashSet<LinkedHashSet<Integer>> P) {
		for(LinkedHashSet<Integer> partition_set : P) {
			List<Integer> partition = new ArrayList<>(partition_set);
			Collections.sort(partition);
			System.out.print("Une solution est : ");
			for(int i : partition) {
				System.out.print("{ ");
				for(int j = 0 ; j < M[i].length ; j++)
					if(M[i][j] == 1) System.out.print(j + " ");
				System.out.print("}");
			}
			System.out.print(String.format("\t(lignes de M : %s)", partition));
			System.out.println();
		}
	}
}
