import java.util.*;

public class ExactCover {
	int[][] M;


	public ExactCover (int[][] M) {
		this.M = M;
	}

	int best_x(HashSet<Integer> X, LinkedHashSet<Integer> C) {
		Iterator<Integer> it = X.iterator();
		int x_min = it.next();
		int x_min_count = 0;
		for (int c : C) {
			x_min_count += M[c][x_min];
		}
		while(it.hasNext()) {
			int x = it.next();
			int x_count = 0;
			for (int c : C) {
				x_count += M[c][x];
			}
			if (x_count < x_min_count) {
				x_min = x;
				x_min_count = x_count;
			}
		}
		return x_min;
	}

	public List<List<Integer>> exactCover(HashSet<Integer> X, LinkedHashSet<Integer> C) {

		List<List<Integer>> P = new ArrayList<>();
		if (X.size() == 0)	{ P.add(new ArrayList<>()); return P; }
		int x = best_x(X,C);
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
				for(List<Integer> partition : exactCover(X_prime,C_prime)) {
					partition.add(s);
					P.add(partition);
				}
			}
		}
		return P;
	}

	public List<List<Integer>> solve() {
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

	public void print_solutions(List<List<Integer>> P) {
		for(List<Integer> partition : P) {
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
