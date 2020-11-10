import java.time.Duration;
import java.time.Instant;

public class Test3 {
  public static void main(String args[]) {
    System.out.println("Fixed polyominos generation with Radelmeier's algorithm (iterative):");
    for (int size = 0; size < 14; size++) {
      Instant start = Instant.now();
      int count = RedelmeierFixedIterator.fixed_polyomino_count(size);
      Duration d = Duration.between(start, Instant.now());
      System.out.println(String.format("\tsize %d: %d fixed polyominoes (%d ms)", size, count, d.toMillis()));
    }
  }
}
