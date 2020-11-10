import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

public class Test6 {
	public static void main(String[] args) {
		System.out.println("*******  TEST MATRICE M *******");
		{
			int[][] M = new int[][]{
							{0, 0, 1, 0, 1, 1, 0},
							{1, 0, 0, 1, 0, 0, 1},
							{0, 1, 1, 0, 0, 1, 0},
							{1, 0, 0, 1, 0, 0, 0},
							{0, 1, 0, 0, 0, 0, 1},
							{0, 0, 0, 1, 1, 0, 1}
			};
			DancingLinks data = new DancingLinks(M);
			Instant start = Instant.now();
			List<List<Integer>> P = data.exactCover();
			Duration d = Duration.between(start, Instant.now());
			System.out.println(String.format("(M) Nombre de solutions : %d\t(%d ms)", P.size(), d.toMillis()));
			data.print_solutions(P);
		}

		System.out.println("\n\n*******  TEST WITH ALL SUBSETS *******");
		Subsets sub = new Subsets();
		for (int size=1; size < 13; size++)
		{

			int[][] M = Subsets.transformToMatrix(sub.getAllSubsets(size), size);
			Instant start = Instant.now();
			DancingLinks data = new DancingLinks(M);
			List<List<Integer>> P = data.exactCover();
			Duration d = Duration.between(start, Instant.now());
			System.out.println(String.format("(%d) Nombre de solutions : %d  (%d ms)", size,  P.size(), d.toMillis()));
			//data.print_solutions(P);
		}
	}
}
