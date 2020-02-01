
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test1 {
  public static void main(String args[]) {
    List<Polyomino> polyominoes;
    try {
      polyominoes = Polyomino.list_from_file(new File("polyominoesINF421.txt"));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    Canva canva = new Canva(1000, 300, 10);
    for (Polyomino p : polyominoes) {
      System.out.println(p);
      p.draw_on(canva, Color.RED);
      canva.offset();
    }
    for (Polyomino p : polyominoes) {
      canva.offset();
      canva.offset();
      p.dilatation(2).rotate(1).translate(0,4).draw_on(canva, Color.RED);
      canva.offset();
      canva.offset();
    }
    canva.show();


  }
}
