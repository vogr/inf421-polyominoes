public class UntriedLinkedListCell {
  Coordinates head;
  UntriedLinkedListCell tail;

  public UntriedLinkedListCell(Coordinates p) {
    head = p;
  }
  public UntriedLinkedListCell(Coordinates p, UntriedLinkedListCell c) {
    head = p;
    tail = c;
  }
}
