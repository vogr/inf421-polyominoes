import java.awt.*;

public class Canva {

  Image2d image;
  int  square_size;

  Canva(int width, int height, int square_size) {
    this.image = new Image2d(width, height);
    this.square_size = square_size;
  }

  void clear() {
    image.getColoredPolygons().clear();
    image.getEdges().clear();
  }
  
  void offset() {
	  image.addPolygon(null, null, null);
  }

  void show() {
    new Image2dViewer(image);
  }
}
