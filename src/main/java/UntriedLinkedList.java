public class UntriedLinkedList {
  UntriedLinkedListCell cell;

  public UntriedLinkedList () {
    cell = null;
  }

  public UntriedLinkedList(UntriedLinkedListCell c) {
    cell = c;
  }

  public Coordinates pop() {
    Coordinates v = cell.head;
    cell = cell.tail;
    return v;
  }

  public void push(Coordinates v) {
    UntriedLinkedListCell t = cell;
    cell = new UntriedLinkedListCell(v, t);
  }


  @Override
  public String toString() {
    return String.format("[%s]", cell);
  }

  public boolean isEmpty() {
    return (cell == null);
  }
}
