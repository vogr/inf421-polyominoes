import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;




public class Polyomino {
  Set<Coordinates> coords;

  Polyomino() {
    this.coords = new LinkedHashSet<>();
  }

  Polyomino(Set<Coordinates> coords) {
    this.coords = coords;
  }

  Polyomino(List<Integer> xcoords, List<Integer> ycoords) {
    assert xcoords.size() == ycoords.size();
    coords = new LinkedHashSet<>();
    for (int i = 0; i < xcoords.size(); i++)
      coords.add(new Coordinates(xcoords.get(i), ycoords.get(i)));
  }

  public Polyomino clone() {
    return new Polyomino(new LinkedHashSet<>(coords));
  }


  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (! (o instanceof Polyomino))
      return false;
    Polyomino p2 = (Polyomino) o;
    return (this.coords.equals(p2.coords));
  }

  @Override
  public int hashCode() {
    return coords.hashCode();
  }

  static Polyomino fromString(String repr) {
    Set<Coordinates> coords = new LinkedHashSet<>();

    if (repr.length() < 2) {
      throw new RuntimeException("String is too short to represent a polydomino.");
    }
    if (repr.charAt(0) != '[') {
      throw new RuntimeException("Expected '[' as the first character of the string.");
    }
    if (repr.charAt(repr.length() - 1) != ']') {
      throw new RuntimeException("Expected ']' as the last character of the string.");
    }

    boolean stop = false;
    int i = 1;
    while (!stop) {
      if (i >= repr.length()) {
        throw new RuntimeException("Malformed list of tuples");
      }
      i = parse_coords(repr, i, coords);
      switch (repr.charAt(i)) {
        case ',':
          if (repr.charAt(i+1) == ' ') {
            i++;
          }
          i++;
          break;
        case ']':
          stop = true;
          break;
        default:
          throw new RuntimeException("Expected ',' or ']'.");
      }
    }
    return new Polyomino(coords);
  }


  private static int parse_coords(String repr, int i, Set<Coordinates> coords) {
    // i should point to the '(' of the tuple
    // returns the position just after the ')'

    if (repr.charAt(i) != '(') {
      throw new RuntimeException("Malformed tuple");
    }

    int l = i + 1;
    int r = l + 1;

    if (! Character.isDigit(repr.charAt(l))) {
      throw new RuntimeException("Malformed tuple");
    }
    while (Character.isDigit(repr.charAt(r))) {
      r++;
    }

    if (repr.charAt(r) != ',') {
      throw new RuntimeException("Malformed tuple");
    }

    int x = Integer.parseInt(repr.substring(l, r));

    l = r+1;
    r = l+1;
    if (! Character.isDigit(repr.charAt(l))) {
      throw new RuntimeException("Malformed tuple");
    }
    while (Character.isDigit(repr.charAt(r))) {
      r++;
    }

    if (repr.charAt(r) != ')') {
      throw new RuntimeException("Malformed tuple");
    }
    int y = Integer.parseInt(repr.substring(l, r));

    coords.add(new Coordinates(x, y));
    return (r + 1);
  }

  static public List<Polyomino> list_from_file(File file) throws IOException {
    List<Polyomino> polyominoes = new LinkedList<>();
    // read a file line-by-line:
    // https://stackoverflow.com/a/5868528
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
      for(String line; (line = br.readLine()) != null; ) {
        polyominoes.add(fromString(line));
      }
    }
    return polyominoes;
  }

  @Override
  public String toString() {
    return String.format("Polyomino%s", coords);
  }

  void draw_on(Canva canva, Color color) {
    for (Coordinates c : coords) {
      int x = c.x * canva.square_size;
      int y = c.y * canva.square_size;
      int[] square_xcoords = {x, x, x + canva.square_size, x + canva.square_size};
      int[] square_ycoords = {y, y + canva.square_size, y + canva.square_size, y};
      canva.image.addPolygon(square_xcoords, square_ycoords, color);
    }
  }

  public Polyomino translate(int dx, int dy) {
    return new Polyomino(
         coords.stream()
            .map(p -> new Coordinates(p.x + dx, p.y + dy))
            .collect(Collectors.toSet())
    );
  }

  public Polyomino rotate (int n_quarter_turns) {
    switch (n_quarter_turns % 4) {
      case 1:
        return new Polyomino(
            coords.stream()
                .map(p -> new Coordinates(-p.y, p.x))
                .collect(Collectors.toSet())
        );
      case 2:
        return new Polyomino(
            coords.stream()
                .map(p -> new Coordinates(-p.x, -p.y))
                .collect(Collectors.toSet())
        );
      case 3:
        return new Polyomino(
            coords.stream()
                .map(p -> new Coordinates(p.y, -p.x))
                .collect(Collectors.toSet())
        );
      default:
        return this.clone();
    }
  }

  public Polyomino mirror_y_axis(int y0) {
    coords = coords.stream()
            .map(p -> new Coordinates(p.x,2 * y0 - p.y))
            .collect(Collectors.toSet());
    return this;
  }

  public Polyomino mirror_x_axis(int x0) {
    return new Polyomino(
        coords.stream()
            .map(p -> new Coordinates(2 * x0 - p.x, p.y))
            .collect(Collectors.toSet())
    );
  }

  public Polyomino canonicalize() {
    if (coords.size() == 0) {
      return new Polyomino();
    }
    Iterator<Coordinates> it = coords.iterator();
    Coordinates p = it.next();
    int dx = p.x;
    int dy = p.y;
    while(it.hasNext()) {
      p = it.next();
      if (p.y < dy) {
        dy = p.y;
        dx = p.x;
      }
      else if (p.y == dy && p.x < dx) {
        dx = p.x;
      }
    }
    // can't use nonfinal variables in lambda function
    final int ddx = dx, ddy = dy;
    return new Polyomino(
        coords.stream()
            .map(Coordinates -> new Coordinates(Coordinates.x - ddx, Coordinates.y - ddy))
            .collect(Collectors.toSet())
    );
  }

  public Polyomino dilatation(int k) {
    Set<Coordinates> coords= new LinkedHashSet<>();
    for (Coordinates p : this.coords)
      for (int i = 0; i < k; i++)
        for (int j = 0; j < k; j++)
          coords.add(new Coordinates(k*p.x + i, k*p.y + j));

    return new Polyomino(coords);
  }

  static Set<Polyomino> generate_fixed_polyominoes(int size) {
    if(size < 1) {
      return new HashSet<>();
    }

    Set<Polyomino> next_polyominoes = new LinkedHashSet<>();

    Polyomino orig_pol = new Polyomino();
    orig_pol.coords.add(new Coordinates(0, 0));
    next_polyominoes.add(orig_pol);

    Set<Polyomino> to_process;

    for(int s = 0; s < size - 1; s++) {
      to_process = next_polyominoes;
      next_polyominoes = new LinkedHashSet<>();

      for (Polyomino polyo : to_process) {
        for(Coordinates p : polyo.coords ) {
          List<Coordinates> neighbors = p.getNeighbors();
          for (Coordinates n : neighbors) {
            if (((n.x >= 0 && n.y >= 0) || (n.x < 0 && n.y >= 1)) && !polyo.coords.contains(n)) {
              Polyomino new_polyo = polyo.clone();
              new_polyo.coords.add(n);
              next_polyominoes.add(new_polyo);
            }
          }
        }
      }
    }
    return new HashSet<>(next_polyominoes);
  }

  boolean has_free_repr_in_set(Set<Polyomino> set) {
    // test all 8 symmetries
    for (int i = 0; i < 4; i++) {
      Polyomino to_test = this.rotate(i).canonicalize();
      if (set.contains(to_test) || set.contains(to_test.mirror_x_axis(0).canonicalize())) {
        return true;
      }
    }
    return false;
  }

  static Set<Polyomino> generate_free_polyominoes(int size) {
    if(size < 1) {
      return new HashSet<>();
    }

    Set<Polyomino> next_polyominoes = new LinkedHashSet<>();

    Polyomino orig_pol = new Polyomino();
    orig_pol.coords.add(new Coordinates(0, 0));
    next_polyominoes.add(orig_pol);

    Set<Polyomino> to_process;

    for(int s = 0; s < size - 1; s++) {
      to_process = next_polyominoes;
      next_polyominoes = new LinkedHashSet<>();

      for (Polyomino polyo : to_process) {
        for (Coordinates p : polyo.coords) {
          List<Coordinates> neighbors = p.getNeighbors();
          for (Coordinates n : neighbors) {
            if (! polyo.coords.contains(n)) {
              Polyomino new_polyo = polyo.clone();
              new_polyo.coords.add(n);
              new_polyo = new_polyo.canonicalize();
              if (! new_polyo.has_free_repr_in_set(next_polyominoes)) {
                next_polyominoes.add(new_polyo);
              }
            }
          }
        }
      }
    }
    return new HashSet<>(next_polyominoes);
  }
  
	public static Coordinates minimumOf(Set<Coordinates> set) {
		int x = Integer.MAX_VALUE, y = Integer.MAX_VALUE;
		for(Coordinates c : set) {
			if(c.x < x) x = c.x;
			if(c.y < y) y = c.y;
		}
		return new Coordinates(x,y);
	}
	
	public static Coordinates maximumOf(Set<Coordinates> set) {
		int x = 0, y = 0;
		for(Coordinates c : set) {
			if(c.x > x) x = c.x;
			if(c.y > y) y = c.y;
		}
		return new Coordinates(x,y);
	}
	
	
	public Polyomino set(int x, int y) {
		Coordinates min = minimumOf(this.coords);
		return this.translate(x - min.x, y - min.y);
	}
	
	public Polyomino set(Coordinates cor) {
		return this.set(cor.x, cor.y);
	}
	
	public LinkedHashSet<Set<Integer>> isPlaceable(Set<Coordinates> space) {
		Polyomino pol = this;
		LinkedHashSet<Set<Integer>> listPosPolyo = new LinkedHashSet<Set<Integer>>();
		Coordinates minCoords = minimumOf(space);
		minCoords.add(0, -1);
		Coordinates maxCoords = maximumOf(space);
		pol = pol.set(minCoords);
		//System.out.println(this.squares);
		for(int dx = 0; dx <= maxCoords.x; dx++) {
			//System.out.println("here");
			for(int dy=0; dy <= maxCoords.y; dy++) {
				//System.out.println("and here");
				pol = pol.translate(0, 1);
				//System.out.println(this.squares);
				Set<Integer> set = new HashSet<Integer>();
				
				for(Coordinates c : pol.coords) {
					int i = -1;
					for(Coordinates cor : space) {
						i++;
						if(cor.equals(c)) set.add(i);
					}
						
				}
				if(set.size()==pol.coords.size())	listPosPolyo.add(set);
			}
			pol = pol.translate(1, minCoords.y - maxCoords.y);
		}
		return listPosPolyo;			
	}
	
	public LinkedHashSet<Set<Integer>> possiblePositions(Polyomino polyomino){
		//System.out.println("called");
		return polyomino.isPlaceable(this.coords);
	}
	
	public LinkedHashSet<Set<Integer>> possiblePositions_With_Rotation(Polyomino polyomino){
		//System.out.println("called");
		LinkedHashSet<Set<Integer>> result = new LinkedHashSet<>();
		for(int i = 1; i < 4 ; i++)
			result.addAll(polyomino.rotate(i).isPlaceable(this.coords));
		return result;
	}
	
	public LinkedHashSet<Set<Integer>> possiblePositions_With_Mirror(Polyomino polyomino){
		//System.out.println("called");
		LinkedHashSet<Set<Integer>> result = new LinkedHashSet<>();
		//result.addAll(polyomino.isPlaceable(this.coords));
		result.addAll(this.possiblePositions_With_Rotation(polyomino));
		result.addAll(this.possiblePositions_With_Rotation(polyomino.mirror_x_axis(0)));
		//result.addAll(polyomino.mirror_y_axis(0).mirror_x_axis(0).isPlaceable(this.coords));
		return result;
	}
	
	public LinkedHashSet<Set<Integer>> possiblePositions_With_Rotation_And_Mirror(Polyomino polyomino){
		//System.out.println("called");
		LinkedHashSet<Set<Integer>> result = new LinkedHashSet<>();
		result.addAll(this.possiblePositions(polyomino));
		result.addAll(this.possiblePositions_With_Mirror(polyomino));
		result.addAll(this.possiblePositions_With_Rotation(polyomino));
		return result;
	}

}


