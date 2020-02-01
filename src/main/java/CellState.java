public enum CellState {
  BORDER,
  OCCUPIED,
  REACHABLE,
  FREE;

  @Override
  public String toString() {
    switch (this) {
      case BORDER:
        return "B";
      case OCCUPIED:
        return "O";
      case REACHABLE:
        return "R";
      case FREE:
        return "F";
      default:
        throw new IllegalArgumentException();
    }
  }
}