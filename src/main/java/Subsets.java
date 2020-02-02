import java.util.HashSet;
import java.util.HashMap;

public class Subsets {
	/*
	 * Classe permettant de générer les subsets de taille k d'un ensemble de taille n.
	 * La structure de classe permet de memoïser les générations intermédiaires.
	 */
	static HashMap<Integer, HashSet<HashSet<Integer>>> completeSubsets;
	static HashMap<Coordinates, HashSet<HashSet<Integer>>> kSubsets;
	
	Subsets(){
		completeSubsets = new HashMap<>();
		kSubsets = new HashMap<>();
	}
	
	public HashSet<HashSet<Integer>> getAllSubsets(int n) {
		if(n < 0) 	return null;
		
		//See if already Computed
		HashSet<HashSet<Integer>> S = completeSubsets.get(n);
		if(S!=null)	return S;
		
		//If not
		S = new HashSet<>();
		if(n == 0) {
			completeSubsets.put(n, S);
			return S;
		}
		HashSet<Integer> singleton = new HashSet<>();
		singleton.add(n);
		S.add(singleton);
		if(n == 1) {
			completeSubsets.put(n, S);
			return S;
		}
		HashSet<HashSet<Integer>> lowerSets = getAllSubsets(n-1);
		for(HashSet<Integer> subset : lowerSets) {
			HashSet<Integer> sub = new HashSet<>(subset);
			sub.add(n);
			S.add(subset);
			S.add(sub);
		}
		completeSubsets.put(n, S);
		return S;
	}
	
	public HashSet<HashSet<Integer>> getkSubsets(int n, int k){
		Coordinates couple = new Coordinates(n,k);
		HashSet<HashSet<Integer>> Snk = kSubsets.get(couple);
		if(Snk!=null)	return Snk;
		Snk = new HashSet<HashSet<Integer>>();
		if(k==0 || n==0) {Snk.add(new HashSet<Integer>());kSubsets.put(couple, Snk); return Snk;}
		if(n==k) {
			HashSet<Integer> all = new HashSet<Integer>();
			for(int i = 1; i <= n ; i++)
				all.add(i);
			Snk.add(all);
			kSubsets.put(couple, Snk);
			return Snk;
		}
		//N/K = [N + (N-1/K-1)] + [N-1/K]
		HashSet<HashSet<Integer>> Sn_1k_1 = getkSubsets(n-1,k-1);
		HashSet<HashSet<Integer>> Sn_1k = getkSubsets(n-1,k);
		
		for(HashSet<Integer> subset : Sn_1k_1) {
			HashSet<Integer> sub = new HashSet<>(subset);
			sub.add(n);
			Snk.add(sub);
		}
		for(HashSet<Integer> subset : Sn_1k) {
			HashSet<Integer> sub = new HashSet<>(subset);
			Snk.add(sub);
		}
		
		kSubsets.put(couple, Snk);
		return Snk;
	}
	
	public static int[][] transformToMatrix(HashSet<HashSet<Integer>> S, int n) {
		int nbRows = S.size();
		int[][] M = new int[nbRows][n];
		int i = 0;
		for(HashSet<Integer> set : S) {
			for(int j : set)
				M[i][j-1] = 1;
			i++;
		}
		return M;
	}
	
	public static void main(String[] args) {
		//kSubsets = new HashMap<>();
		Subsets subs = new Subsets();
		HashSet<HashSet<Integer>> S =subs.getkSubsets(4,2);
		System.out.println(S);
		System.out.println(kSubsets);
		S =subs.getkSubsets(3,2);
		System.out.println(S);
		System.out.println(kSubsets);
		
		int[][] M = transformToMatrix(S,3);
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[i].length; j++)
				System.out.print(M[i][j] + " ");
			System.out.println();
		}
	}
	
}
