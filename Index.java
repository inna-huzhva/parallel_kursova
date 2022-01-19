import java.util.concurrent.*;
import java.util.*;

public class Index {
  private ConcurrentHashMap<String, Set<WordEntry>> index = new ConcurrentHashMap<>();

  public void update(String fileName, Map<String, Integer> words) {
    words.forEach((w, c) -> {
      WordEntry e = new WordEntry(fileName, c);
      index.computeIfAbsent(w, k -> new HashSet<>());
      index.computeIfPresent(w, (k, es) -> {
        es.add(e);
        return es;
      });
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
