import java.util.*;

public class Index {
  private Map<String, Set<WordEntry>> index = new HashMap<>();

  public void update(String fileName, Map<String, Integer> words) {
    words.forEach((w, c) -> {
      WordEntry e = new WordEntry(fileName, c);
      if (!index.containsKey(w)) {
        Set<WordEntry> es = new HashSet<>();
        es.add(e);
        index.put(w, es);
      } else {
        Set<WordEntry> es = index.get(w);
        es.add(e);
      }
    });
  }

  public void print() {
    index.forEach((w, es) -> {
      System.out.println(w + ":");
      es.stream()
        .sorted((x, y) -> y.getFrequency() - x.getFrequency()) // function for comparison
        .forEach(e -> System.out.println("  " + e));
    });
  }
}
