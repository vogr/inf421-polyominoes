import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;

public class Test8 {
	public static void main(String[] args) {
		Canva canva = new Canva(1900, 1000, 20);

		Set<Polyomino> S = Polyomino.generate_fixed_polyominoes(4);
		Polyomino P = Polyomino.fromString("[(0,0),(0,1),(1,0)]").dilatation(2);

		PolyominoTiling tiling = new PolyominoTiling(P, new ArrayList<>(S));

		tiling.allow_repetitions = false;
		tiling.allow_rotations = false;
		tiling.allow_symmetries = false;


		List<List<Polyomino>> list_of_coverages = tiling.solve();

		System.out.println(String.format("%d coverages possible.", list_of_coverages.size()));

		List<Color> listOfColors = new LinkedList<>();
		listOfColors.add(Color.red);
		listOfColors.add(Color.blue);
		listOfColors.add(Color.green);
		listOfColors.add(Color.yellow);
		listOfColors.add(Color.orange);
		listOfColors.add(Color.black);

		for (List<Polyomino> coverage : list_of_coverages) {
			int i = 0;
			for (Polyomino p : coverage) {
				p.draw_on(canva, listOfColors.get(i));
				i = (i + 1) % 6;
			}
			canva.offset();
			canva.offset();
			canva.offset();
			canva.offset();
		}
		canva.show();
	}
}