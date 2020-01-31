import java.util.*;

public class RedelmeyerFixedGenerator {
  int size;
  InformationMatrix<CellState> state;
  List<PolyominoCoordList> result;

  int depth = 0;

  RedelmeyerFixedGenerator (int size) {
    this.size = size;
    result = new ArrayList<>();

    state = new InformationMatrix<>(size, CellState.FREE);
    for (int i = - size + 1; i < size; i++) {
      state.set(i, -1, CellState.BORDER);
    }
    for (int i = - size + 1; i < 0; i++)
      state.set(i, 0, CellState.BORDER);
  }

  public void generate_from_parent(Set<Coordinates> parent_coords, UntriedLinkedList untried) {
    depth += 1;
    while (! untried.isEmpty()) {
      Coordinates next = untried.pop();
      // Add the "next" cell to the polyomino
      // set state for debugging only, not useful for the algorithm as
      // the cell is already REACHABLE.
      parent_coords.add(next);
      state.set(next, CellState.OCCUPIED);

      if (parent_coords.size() == size) {
        result.add(new PolyominoCoordList(new HashSet<>(parent_coords)));
      }
      else if (parent_coords.size() < size) {
        UntriedLinkedList new_untried = new UntriedLinkedList(untried.cell);
        //System.out.println(untried);

        List<Coordinates> formerly_free = new ArrayList<>();
        for(Coordinates c : next.getNeighbors()) {
          if (state.get(c) == CellState.FREE) {
            formerly_free.add(c);
            state.set(c, CellState.REACHABLE);
            new_untried.push(c);
          }
        }
        // No need to copy the parent_coords set : each  recursive function call
        // cleans up before returning
        generate_from_parent(parent_coords, new_untried);

        // Mark the formerly free neighbors of the new cell as free again
        for(Coordinates c : formerly_free) state.set(c, CellState.FREE);
      }

      // Remove the new cell
      parent_coords.remove(next);
      state.set(next, CellState.REACHABLE);

    }
  }

  public List<PolyominoCoordList> generate() {
    if(size < 1) {
      return result;
    }
    UntriedLinkedList untried = new UntriedLinkedList();
    untried.push(new Coordinates(0,0));
    state.set(0, 0, CellState.REACHABLE);
    Set<Coordinates> parent_coords = new HashSet<>();
    generate_from_parent(parent_coords, untried);
    return result;
  }
}
