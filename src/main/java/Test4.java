import java.util.HashSet;
import java.util.LinkedList;

public class Test4 {
	public static void main(String[] args) {
		ExactCover exact = new ExactCover();
		
		//exact.M = new int[6][7];
		//exact.M[0][2] = exact.M[0][4] = exact.M[0][5] = 1;
		//exact.M[1][0] = exact.M[1][3] = exact.M[1][6] = 1;
		////M[1][1] = 1;
		//exact.M[2][1] = exact.M[2][2] = exact.M[2][5] = 1;
		////M[2][4] = M[2][6] = 1;
		//exact.M[3][0] = exact.M[3][3] = 1;
		//exact.M[4][1] = exact.M[4][6] = 1;
		//exact.M[5][3] = exact.M[5][4] = exact.M[5][6] = 1;
		
		LinkedList<Integer> X = new LinkedList<Integer>();
		//for(int i = 1; i<=exact.M[0].length;i++) {
			//X.add(i);
		//}
		for(int i = 1; i<=6;i++) {
			X.add(i);
		}
		System.out.println("X = " + X);
		Subsets sub = new Subsets();
		exact.M = Subsets.transformToMatrix(sub.getkSubsets(6, 3), 6);
		for (int i = 0; i < exact.M.length; i++) {
			for (int j = 0; j < exact.M[i].length; j++)
				System.out.print(exact.M[i][j] + " ");
			System.out.println();
		}
		HashSet<Integer> C = new HashSet<Integer>();
		for(int i = 1; i<=exact.M.length;i++) {
			C.add(i);
		}
		System.out.println("C = " + C);	

		HashSet<HashSet<Integer>> P = ExactCover.exactCover(X,C);
		System.out.println("nombre de solutions : " + P.size());
		ExactCover.print(P);
	}
}
