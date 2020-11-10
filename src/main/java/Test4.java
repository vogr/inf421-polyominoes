import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Test4 {
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
			ExactCover exact = new ExactCover(M);
			System.out.println("M = \n" + exact.M_repr());
			Instant start = Instant.now();
			List<List<Integer>> P = exact.solve();
			Duration d = Duration.between(start, Instant.now());
			System.out.println(String.format("(M) Nombre de solutions : %d\t(%d ms)", P.size(), d.toMillis()));
			exact.print_solutions(P);
		}
		System.out.println("\n\n*******  TEST WITH ALL SUBSETS *******");
		Subsets sub = new Subsets();
		for (int size=1; size < 11; size++)
		{
			ExactCover exact = new ExactCover(
							Subsets.transformToMatrix(sub.getAllSubsets(size), size)
			);
			Instant start = Instant.now();
			List<List<Integer>> P = exact.solve();
			Duration d = Duration.between(start, Instant.now());
			System.out.println(String.format("(%d) Nombre de solutions : %d  (%d ms)", size,  P.size(), d.toMillis()));
		}
	}
}
