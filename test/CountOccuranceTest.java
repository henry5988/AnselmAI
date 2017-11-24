import static com.HF.countOccurance;
import static com.HF.out;

import java.util.LinkedList;
import java.util.List;

// Test Module for countOccurance() function in com.HF
public class CountOccuranceTest {
  public static void main(String[] args) {
    List example = new LinkedList<String>();
    example.add("A");
    example.add("A");
    example.add("A");
    example.add("E");
    example.add("A");
    out("A: " + countOccurance(example, (Object) "E", 0).toString());
    // E: 1
    // A: 4
  }
}
