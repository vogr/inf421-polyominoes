import java.time.Duration;
import java.time.Instant;

public class Test2 {
  public static void main(String args[]) {

    System.out.println("Naive fixed polyominos generation:");
    for (int size = 0; size < 11; size++) {
      Instant start = Instant.now();
      int count = Polyomino.generate_fixed_polyominoes(size).size();
      Duration d = Duration.between(start, Instant.now());
      System.out.println(String.format("\tsize %d: %d fixed polyominoes (%d ms)", size, count, d.toMillis()));
    }

    System.out.println("\nNaive free polyominos generation:");
    for (int size = 0; size < 12; size++) {
      Instant start = Instant.now();
      int count = Polyomino.generate_free_polyominoes(size).size();
      Duration d = Duration.between(start, Instant.now());
      System.out.println(String.format("\tsize %d: %d free polyominoes (%d ms)", size, count, d.toMillis()));
    }
  }
}
