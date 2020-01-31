import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Tiling {
	static int[][] M;
	//static int[] indexOfPolyos;
	
	public static void adaptRepresentation(PolyominoCoordList P, Set<PolyominoCoordList> S) {
		int nbRows = 0;
		int index = 1;
		//indexOfPolyos = new int[S.size()];
		Set<LinkedHashSet<Set<Integer>>> sAdapted = new HashSet<LinkedHashSet<Set<Integer>>>();
		for(PolyominoCoordList polyomino : S) {
			//Place the polyomino on the different possible squares
			LinkedHashSet<Set<Integer>> listPosPolyo = P.possiblePositions_With_Mirror(polyomino);
			if(!sAdapted.contains(listPosPolyo)) {	//System.out.println("haha stupid");
				sAdapted.add(listPosPolyo);
				nbRows += listPosPolyo.size();
			}
			//indexOfPolyos[index] = nbRows;
		}
		System.out.println(nbRows);
		//Create the cover matrix
		M = new int[nbRows][P.coords.size()];
		int i = 0;
		for(LinkedHashSet<Set<Integer>> listPosPolyo : sAdapted)
			for(Set<Integer> s : listPosPolyo) {
				for(int j : s)	M[i][j] = 1;
				i++;
			}
		
	}
}
