import java.util.HashSet;

public class Test6 {
	public static void main(String[] args) {
		//int[][] M = new int[6][7];
		//M[0][2] = M[0][4] = M[0][5] = 1;
		//M[1][0] = M[1][3] = M[1][6] = 1;
		////M[1][1] = 1;
		//M[2][1] = M[2][2] = M[2][5] = 1;
		////M[2][4] = M[2][6] = 1;
		//M[3][0] = M[3][3] = 1;
		////M[3][1] = 1;
		//M[4][1] = M[4][6] = 1;
		//M[5][3] = M[5][4] = M[5][6] = 1;
		Subsets sub = new Subsets();
		int[][] M = Subsets.transformToMatrix(sub.getkSubsets(6, 3), 6);
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[i].length; j++)
				System.out.print(M[i][j] + " ");
			System.out.println();
		}
		DataStructure data = new DataStructure();
		data.dancing_links(M);
		data.print();
		
		HashSet<HashSet<HashSet<Integer>>> P = data.exactCover();
		System.out.println(P.size());
		System.out.println(P);
	}
}
