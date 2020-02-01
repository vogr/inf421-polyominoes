import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Coordinates {
	int x,y;
	
	Coordinates(int x, int y){
		this.x = x;
		this.y = y;		
	}
	
	Coordinates(){
		this(0,0);
	}
	
	public void add(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void addCoordinates(Coordinates that) {
		this.add(that.x, that.y);
	}
	
	public void center() {
		this.x = 0;
		this.y = 0;
	}
	
	public void inv() {
		this.x = - this.x;
		this.y = - this.y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public List<Coordinates> getNeighbors(){
		List<Coordinates> neighbors = new ArrayList<Coordinates>();
		neighbors.add(new Coordinates(this.x+1,this.y));
		neighbors.add(new Coordinates(this.x,this.y+1));
		neighbors.add(new Coordinates(this.x-1,this.y));
		neighbors.add(new Coordinates(this.x,this.y-1));
		return neighbors;
	}
	
	public Coordinates copy() {
		return new Coordinates(this.x, this.y);
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
	
	  @Override
	  public boolean equals(Object o) {
	    if (o == null)
	      return false;
	    if (! (o instanceof  Coordinates))
	      return false;
	    Coordinates c2 = (Coordinates) o;
	    return (this.x == c2.x && this.y == c2.y);
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	
	
	
}
