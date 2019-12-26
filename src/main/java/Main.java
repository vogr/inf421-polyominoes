import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String args[]) {
    Canva canva = new Canva(1000, 1000, 40);

    List<Polyomino> polyominoes = null;
    try {
      polyominoes = Polyomino.list_from_file(new File("polyominoesINF421.txt"));
    } catch (IOException e) {
      System.err.println(String.format("Error reading file: %s", e.getMessage()));
    }
    //canva.draw_polyomino(polyominoes.get(1), Color.red);
    canva.draw_polyomino(polyominoes.get(1).copy().center(), Color.blue);
    canva.show();
  }

}
