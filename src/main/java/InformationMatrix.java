import java.util.ArrayList;
import java.util.List;

public class InformationMatrix<T> {
  int p;
  List<List<T>> info;
  int xoffset;
  int yoffset;

  public InformationMatrix(int p, T v) {
    this.p = p;

    xoffset = - p + 1;
    yoffset = -1;


    info = new ArrayList<>(2 * p - 1);
    for(int i = 0 ; i < 2 * p; i++) {
      List<T> l = new ArrayList<>(p + 1);
      info.add(l);
      for(int j=0; j < p + 2 ; j++) {
        l.add(v);
      }
    }
  }

  private InformationMatrix(int p, List<List<T>> info, int xoffset, int yoffset){
    this.p = p;
    this.info = info;
    this.xoffset = xoffset;
    this.yoffset = yoffset;
  }

  public void set(int x, int y, T value) {
    info.get(x - xoffset).set(y - yoffset, value);
  }
  public T get(int x, int y) {
    return info.get(x - xoffset).get(y - yoffset);
  }

  public void set(Coordinates c, T value) {
    set(c.x, c.y, value);
  }

  public T get(Coordinates c) {
    return get(c.x, c.y);
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (List<T> l : info) {
      for (T e : l) {
        b.append(e);
        b.append(" ");
      }
      b.append("\n");
    }
    return b.toString();
  }

  public InformationMatrix clone() {
    List<List<T>> clone = new ArrayList<>(2 * p - 1);
    for (List<T> l : info) {
      clone.add(new ArrayList<>(l));
    }
    return new InformationMatrix(p, clone, xoffset, yoffset);
  }
}

