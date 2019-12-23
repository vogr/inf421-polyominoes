import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Polyomino {
  int[] xcoords;
  int[] ycoords;

  Polyomino(int[] xcoords, int[] ycoords) {
    assert xcoords.length == ycoords.length;
    this.xcoords = xcoords;
    this.ycoords = ycoords;
  }

  protected Polyomino copy() {
    return new Polyomino(xcoords.clone(), ycoords.clone());
  }

  static Polyomino fromString(String repr) {
    LinkedList<Integer> xcoords = new LinkedList<>();
    LinkedList<Integer> ycoords = new LinkedList<>();

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
      i = parse_coords(repr, i, xcoords, ycoords);
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

    // List<Integer> to int[]
    // see https://stackoverflow.com/a/23945015
    return new Polyomino(xcoords.stream().mapToInt(k->k).toArray(), ycoords.stream().mapToInt(k->k).toArray());
  }


  private static int parse_coords(String repr, int i, List<Integer> xcoords, List<Integer> ycoords) {
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

    xcoords.add(x);
    ycoords.add(y);

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
    StringBuilder b = new StringBuilder();
    b.append("Polydomino[");
    for(int i = 0; i < xcoords.length; i++) {
      b.append('(');
      b.append(xcoords[i]);
      b.append(", ");
      b.append(ycoords[i]);
      b.append(')');
      if (i < xcoords.length -1) {
        b.append(',');
      }
    }
    b.append(']');
    return b.toString();
  }

  /*
   * /!\ All these methods are inplace!
   */

  public Polyomino translate(int dx, int dy) {
    for (int i = 0; i < xcoords.length; i++) {
      xcoords[i] += dx;
      ycoords[i] += dy;
    }
    return this;
  }

  public Polyomino rotate (int n_quarter_turns) {
    switch (n_quarter_turns % 4) {
      case 1:
        for(int i = 0; i < xcoords.length; i++) {
          int t = xcoords[i];
          xcoords[i] = - ycoords[i];
          ycoords[i] = t;
        }
        break;
      case 2:
        for(int i = 0; i < xcoords.length; i++) {
          xcoords[i] *= -1;
          ycoords[i] *= -1;
        }
        break;
      case 3:
        for(int i = 0; i < xcoords.length; i++) {
          int t = xcoords[i];
          xcoords[i] = ycoords[i];
          ycoords[i] = - t;
        }
        break;
      default:
        break;
    }
    return this;
  }

  public Polyomino mirror_y_axis(int y0) {
    for(int i = 0; i < xcoords.length; i++) {
      ycoords[i] = 2 * y0 - ycoords[i];
    }
    return this;
  }

  public Polyomino mirror_x_axis(int x0) {
    for(int i = 0; i < xcoords.length; i++) {
      ycoords[i] = 2 * x0 - ycoords[i];
    }
    return this;
  }

  public Polyomino center() {
    if (xcoords.length < 1) {
      return this;
    }
    int dx = xcoords[0];
    int dy = ycoords[0];
    for (int i=0; i < xcoords.length; i++) {
      if (xcoords[i] < dx) {
        dx = xcoords[i];
      }
      if (ycoords[i] <= dy) {
        dy = ycoords[i];
      }
    }
    for (int i = 0; i < xcoords.length; i++) {
      xcoords[i] -= dx;
      ycoords[i] -= dy;
    }
    return this;
  }

  public Polyomino set(int x, int y) {
    return this.center().translate(x, y);
  }
}


