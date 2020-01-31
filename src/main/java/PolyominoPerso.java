import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class PolyominoPerso {
	LinkedHashSet<Coordinates> squares;
	
	PolyominoPerso(){
		squares = new LinkedHashSet<Coordinates>();
	}
	
	public PolyominoPerso(LinkedHashSet<Coordinates> squares) {
		super();
		this.squares = squares;
	}



	public static Coordinates minimumOf(LinkedHashSet<Coordinates> set) {
		int x = Integer.MAX_VALUE, y = Integer.MAX_VALUE;
		for(Coordinates c : set) {
			if(c.x < x) x = c.x;
			if(c.y < y) y = c.y;
		}
		return new Coordinates(x,y);
	}
	
	public static Coordinates maximumOf(LinkedHashSet<Coordinates> set) {
		int x = 0, y = 0;
		for(Coordinates c : set) {
			if(c.x > x) x = c.x;
			if(c.y > y) y = c.y;
		}
		return new Coordinates(x,y);
	}
	
	public void translate(int dx, int dy) {
		for(Coordinates c : this.squares)
			c.add(dx, dy);
	}
	
	public void set(int x, int y) {
		Coordinates min = minimumOf(this.squares);
		this.translate(x - min.x, y - min.y);
	}
	
	public void set(Coordinates cor) {
		this.set(cor.x, cor.y);
	}
	
	public LinkedHashSet<Set<Integer>> isPlaceable(LinkedHashSet<Coordinates> space) {
		//System.out.println("got here");
		//System.out.println("space = " + space);
		//System.out.println(this.squares);
		LinkedHashSet<Set<Integer>> listPosPolyo = new LinkedHashSet<Set<Integer>>();
		Coordinates minCoords = minimumOf(space);
		minCoords.add(0, -1);
		Coordinates maxCoords = maximumOf(space);
		this.set(minCoords);
		//System.out.println(this.squares);
		for(int dx = 0; dx <= maxCoords.x; dx++) {
			//System.out.println("here");
			for(int dy=0; dy <= maxCoords.y; dy++) {
				//System.out.println("and here");
				this.translate(0, 1);
				//System.out.println(this.squares);
				Set<Integer> set = new HashSet<Integer>();
				
				for(Coordinates c : this.squares) {
					int i = -1;
					for(Coordinates cor : space) {
						i++;
						if(cor.equals(c)) set.add(i);
					}
						
				}
				if(set.size()==this.squares.size())	listPosPolyo.add(set);
			}
			this.translate(1, minCoords.y - maxCoords.y);
		}
		return listPosPolyo;			
	}
	
	public LinkedHashSet<Set<Integer>> possiblePositions(PolyominoPerso polyomino){
		//System.out.println("called");
		return polyomino.isPlaceable(this.squares);
	}
	
	
	
	
}
