import java.awt.*;

public class Canva {

  Image2d image;
  int  square_size;

  Canva(int width, int height, int square_size) {
    this.image = new Image2d(width, height);
    this.square_size = square_size;
  }

  void draw_polyomino(Polyomino p, Color color) {
	
    for (int i=2*p.p-2; i >= 0; i--) {
    	for(int j =1; j <= p.p; j++) {
    		if(p.data[i][j]==1) {
    			int x = (j-1) * square_size;
        	    int y = (p.p-1-i) * square_size;
        	    int[] square_xcoords = {x, x, x + square_size, x + square_size};
        	    int[] square_ycoords = {y, y + square_size, y + square_size, y};
        	    image.addPolygon(square_xcoords, square_ycoords, color);
    		}
    	}
    }
    image.addPolygon(null, null, null);
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
