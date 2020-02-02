import java.util.ArrayList;
import java.util.HashSet;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;

public class Test8 {
	public static void main(String[] args) {
		Canva canva = new Canva(2000, 2000, 10);
		//Set<Coordinates> squaresT = new LinkedHashSet<Coordinates>();
		//Set<Coordinates> squaresQ = new LinkedHashSet<Coordinates>();
		Set<Coordinates> squareP = new LinkedHashSet<Coordinates>();
		//squaresT.add(new Coordinates(0,0));
		//squaresT.add(new Coordinates(1,0));
		//squaresT.add(new Coordinates(1,1));
		
		
		
		//PolyominoCoordList T = new PolyominoCoordList(squaresT); 
		Set<Polyomino> S = Polyomino.generate_fixed_polyominoes(4);
		//add data;
		//S.add(T);
		
		
		//PolyominoCoordList Q = new PolyominoCoordList(squaresQ); 
		//squaresQ.add(new Coordinates(0,0));
		//S.add(Q);
		List<Coordinates> listCoord = new ArrayList<>();
		
		
		listCoord.add(new Coordinates(0,0));
		listCoord.add(new Coordinates(0,1));
		listCoord.add(new Coordinates(0,2));
		listCoord.add(new Coordinates(0,3));
		listCoord.add(new Coordinates(1,0));
		listCoord.add(new Coordinates(1,1));
		listCoord.add(new Coordinates(1,2));
		listCoord.add(new Coordinates(1,3));
		listCoord.add(new Coordinates(2,0));
		listCoord.add(new Coordinates(2,1));
		listCoord.add(new Coordinates(2,2));
		listCoord.add(new Coordinates(2,3));
		
		for(Coordinates cor : listCoord)
			squareP.add(cor);
		
		Polyomino P = new Polyomino(squareP);
		Tiling t = new Tiling();
		Tiling.adaptRepresentation(P, S);
		for (int i = 0; i < t.M.length; i++) {
			for (int j = 0; j < t.M[i].length; j++)
				System.out.print(t.M[i][j] + " ");
			System.out.println();
		}
		DancingLinks data = new DancingLinks(t.M);

		//List<List<List<Integer>>> cover = data.exactCover();
		//System.out.println(cover);
		
		List<Color> listOfColors = new LinkedList<>();
		listOfColors.add(Color.red);
		listOfColors.add(Color.blue);
		listOfColors.add(Color.green);
		listOfColors.add(Color.yellow);
		listOfColors.add(Color.orange);
		listOfColors.add(Color.black);

		/*
		int i;
		for(HashSet<HashSet<Integer>> set : cover) {
			i = 0;
			for(HashSet<Integer> polyoInt : set) {
				Set<Coordinates> coords = new HashSet<>();
				for(int cor : polyoInt) {
					coords.add(listCoord.get(cor-1));
				}
				Polyomino polyo = new Polyomino(coords);
				polyo.draw_on(canva, listOfColors.get(i));
				i++;
			}
			canva.offset();
		}
		canva.show();

		 */
		
	}
}
