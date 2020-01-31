import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {
	public static final int 	 FREE = 0, OCCUPIED = 1, REACHABLE = 2, BORDER = -1;
	
	public static int fixed(int n) {
		Polyomino pol = new Polyomino(n);
		Coordinates c = new Coordinates(0,0);
		int count;
		LinkedList<Coordinates> untried = new LinkedList<Coordinates>();
		untried.addFirst(c);
		count = fixed(n, pol, untried);
		return count;
	}
	
	public static int fixed(int n, Polyomino pol, LinkedList<Coordinates> untried) {
		int count = 0;
		Coordinates c;
		while(!untried.isEmpty()) {
			//System.out.println("untried size = " + untried.first.size);
			c = untried.removeFirst();
			//if(pol.getCell(c) == REACHABLE) System.out.print("wtf??????????????????");
			pol.setOccupied(c);
			if(pol.size==n) count++;
			else {
				Set<Coordinates> neighbors = c.getNeighbors();
				Set<Coordinates> newNeigh = new HashSet<Coordinates>();
				LinkedList<Coordinates> untried_neigh = new LinkedList<Coordinates>();
				for(Coordinates cor : untried) {
					untried_neigh.add(cor.copy());
				}
				for(Coordinates neigh: neighbors) {
					if(pol.inChess(neigh) && pol.getCell(neigh)==FREE) {
						untried_neigh.addFirst(neigh.copy());
						newNeigh.add(neigh);
					}
				}
				count += fixed(n,pol,untried_neigh);
				for(Coordinates b : newNeigh) {
					pol.setFree(b);
				}
				
				
			}
			pol.setReachable(c);
			pol.size--;
		}
		return count;
		
		
		
	}
	
	
	public static void main(String args[]) {
		Canva canva = new Canva(2000, 2000, 10);

		List<Polyomino> polyominoes = null;
		try {
			polyominoes = Polyomino.list_from_file(new File("polyominoesINF421.txt"));
		} catch (IOException e) {
			System.err.println(String.format("Error reading file: %s", e.getMessage()));
		}

		//for (Polyomino p : polyominoes) {
		//	p.rotate(1).print();
		//	canva.draw_polyomino(p, Color.red);
		//}

		//canva.show();
		Set<PolyominoCoordList> listPol = PolyominoCoordList.generate_fixed_polyominoes(4);
		
		for(PolyominoCoordList pol : listPol) {
			pol = pol.set(0, 0);
			pol.draw_on(canva, Color.red);
		}
		
		canva.show();
		System.out.println(listPol.size());
	}

}
