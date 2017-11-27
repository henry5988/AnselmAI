import static com.HF.countOccurance;
import static com.HF.extractTop;
import static com.HF.out;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Test Module for countOccurance() function in com.HF
public class HFTest {
  public static void main(String[] args) {
    List example = new LinkedList<String>();
    example.add("A");
    example.add("A");
    example.add("A");
    example.add("E");
    example.add("A");
    out("A: " + countOccurance(example, (Object) "A", 0).toString());
    // E: 1
    // A: 4

    Map map = new HashMap();
    map.put("1", 1);
    map.put("2", 2);
    map.put("5", 5);
    map.put("3", 3);
    map.put("4", 4);
    map.put("6", 6);
    example = extractTop(map, 3);
    out("top 3: " + example.toString());
  }
}
