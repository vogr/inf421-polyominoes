import java.lang.reflect.Array;
import java.util.*;

public class PolyominoTiling {
  HashMap<Coordinates, Integer> P_to_X;
  ArrayList<Coordinates> X_to_P;

  LinkedHashSet<Coordinates> P;
  List<Polyomino> S;

  boolean allow_rotations, allow_symmetries, allow_repetitions;


  PolyominoTiling(Polyomino P, List<Polyomino> S) {
    this.P = new LinkedHashSet<>(P.coords);
    int i = 0;

    P_to_X = new HashMap<>();
    X_to_P = new ArrayList<>();
    for (Coordinates c : P.coords) {
      P_to_X.put(c, i);
      X_to_P.add(c);
      i++;
    }
    this.S = S;

    allow_rotations = true;
    allow_symmetries = true;
    allow_repetitions = true;
  }

  private int[][] build_M () {
    int space_size = P.size();
    // add virtual cells to prevent repetitions
    if (! allow_repetitions) {
      space_size += S.size();
    }

    List<int[]> res = new ArrayList<>();


    for(int k = 0; k < S.size(); k++) {
      Polyomino p = S.get(k);

      List<Polyomino> placeables;
      // build the set of all translations of the polyomino p (with rotations/symmetries if allowed)
      // that are inside the polyomino to cover P.
      if (allow_rotations) {
        if (allow_symmetries) placeables = p.placeable_translations_with_all_in(P);
        else placeables = p.placeable_translations_with_rotations_in(P);
      }
      else {
        if (allow_symmetries) placeables = p.placeable_translations_with_symmetries_in(P);
        else placeables = p.placeable_translations_in(P);
      }

      for (Polyomino t : placeables) {
        int[] l = new int[space_size];
        for(Coordinates c : t.coords) {
          l[P_to_X.get(c)] = 1;
        }

        if (! allow_repetitions) {
          l[P.size() + k] = 1;
        }
        res.add(l);
      }
    }
    return res.toArray(new int[][]{});
  }

  public List<List<Polyomino>> solve() {
    // each list of polyomino returned exactly covers P

    int[][] M = build_M();

    DancingLinks d = new DancingLinks(M, P.size());
    List<List<Integer>> list_of_covering_lines = d.exactCover();


    // transform lists of lines in M to lists of polyominos
    // use a hashmap to memoize the transformation
    HashMap<Integer, Polyomino> line_to_polyomino = new HashMap<>();

    List<List<Polyomino>> res = new ArrayList<>();
    for (List<Integer> covering_lines : list_of_covering_lines) {
      List<Polyomino> l = new ArrayList<>();
      for (int line : covering_lines) {
        if (! line_to_polyomino.containsKey(l)) {
          Polyomino p = new Polyomino();
          for (int j = 0; j < P.size(); j++) {
            if(M[line][j] == 1) {
              p.coords.add(X_to_P.get(j));
            }
          }
          line_to_polyomino.put(line, p);
        }
        l.add(line_to_polyomino.get(line));
      }
      res.add(l);
    }
    return res;
  }
}
