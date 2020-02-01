import java.util.*;


public class RedelmeierFixedIterator implements Iterator<Polyomino> {
  /*
   * Implements the Redelmeyer generation of fixed polyominoes as an iterator.
   * The algorithm is described with recursion in the paper ; this implementation uses
   * an explicit stack to make the process resumable (i.e. save the state, return a polyomino
   * and resume the computation later).
   *
   */

  final int size;
  InformationMatrix<CellState> state;
  Deque<Coordinates> parent_coords;

  Deque<UntriedLinkedList> untried_stack;
  Deque<List<Coordinates>> formerly_free_stack;

  Polyomino next;

  RedelmeierFixedIterator(int size) {
    this.size = size;

    if (size <= 0) {
      this.next = null;
    }
    else {
      state = new InformationMatrix<>(size, CellState.FREE);
      for (int i = -size + 1; i < size; i++) {
        state.set(i, -1, CellState.BORDER);
      }
      for (int i = -size + 1; i < 0; i++)
        state.set(i, 0, CellState.BORDER);

      untried_stack = new ArrayDeque<>();
      UntriedLinkedList untried = new UntriedLinkedList();
      untried.push(new Coordinates(0, 0));
      untried_stack.push(untried);

      formerly_free_stack = new ArrayDeque<>();

      state.set(0, 0, CellState.REACHABLE);

      parent_coords = new ArrayDeque<>();
      advance();
    }
  }

  @Override
  public boolean hasNext() {
    return this.next != null;
  }

  public Polyomino next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    try {
      return this.next;
    }
    finally {
      advance();
    }
  }

  public void advance() {
    while (true) {
      while (untried_stack.peek().isEmpty()) {
        if (untried_stack.size() == 1) {
          // we're done exploring the tree
          this.next = null;
          return;
        }
        // back up in tree while there are no untried cells on the current level
        untried_stack.pop();
        // Remove most-recently added cell
        Coordinates c = parent_coords.pop();
        state.set(c, CellState.REACHABLE);

        // Mark the formerly free neighbors of that cell as free again
        List<Coordinates> formerly_free_neigh = formerly_free_stack.pop();
        for (Coordinates n : formerly_free_neigh) state.set(n, CellState.FREE);
      }

      while ((parent_coords.size() < size - 1)  && ! untried_stack.peek().isEmpty()) {
        Coordinates next = untried_stack.peek().pop();
        // Add the "next" cell to the polyomino
        // set state for debugging only, not useful for the algorithm as
        // the cell is already REACHABLE.
        parent_coords.push(next);
        state.set(next, CellState.OCCUPIED);

        UntriedLinkedList new_untried = new UntriedLinkedList(untried_stack.peek().cell);

        List<Coordinates> formerly_free = new ArrayList<>();
        for (Coordinates c : next.getNeighbors()) {
          if (state.get(c) == CellState.FREE) {
            formerly_free.add(c);
            state.set(c, CellState.REACHABLE);
            new_untried.push(c);
          }
        }
        untried_stack.push(new_untried);
        formerly_free_stack.push(formerly_free);

      }

      if (!untried_stack.peek().isEmpty()) {
        HashSet<Coordinates> res_coords = new HashSet<>(parent_coords);
        res_coords.add(untried_stack.peek().pop());
        this.next = new Polyomino(res_coords);
        return;
      }
    }
  }


  public static int fixed_polyomino_count(int size) {
    RedelmeierFixedIterator it = new RedelmeierFixedIterator(size);
    int count = 0;
    while(it.hasNext()) {
      count++;
      it.next();
    }
    return count;
  }
}
