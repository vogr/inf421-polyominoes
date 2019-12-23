import java.awt.*;

public class Canva {

  Image2d image;
  int  square_size;

  Canva(int width, int height, int square_size) {
    this.image = new Image2d(width, height);
    this.square_size = square_size;
  }

  void draw_polyomino(Polyomino p, Color color) {
    for (int i=0; i < p.xcoords.length; i++) {
      int x = p.xcoords[i] * square_size;
      int y = p.ycoords[i] * square_size;
      int[] square_xcoords = {x, x, x + square_size, x + square_size};
      int[] square_ycoords = {y, y + square_size, y + square_size, y};
      image.addPolygon(square_xcoords, square_ycoords, color);
    }
  }

  void show() {
    new Image2dViewer(image);
  }
}
