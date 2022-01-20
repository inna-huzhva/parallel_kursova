import java.util.concurrent.*;
import java.util.stream.*;
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

  public List<WordEntry> search(String word) {
    word = word.toLowerCase();
    return index.getOrDefault(word, new HashSet<>())
      .stream()
      .sorted((x, y) -> y.getFrequency() - x.getFrequency())
      .collect(Collectors.toList());
  }

  public List<WordEntry> searchAnd(String word1, String word2) {
    word1 = word1.toLowerCase();
    word2 = word2.toLowerCase();
    Set<WordEntry> entries1 = index.getOrDefault(word1, new HashSet<>());
    Set<WordEntry> entries2 = index.getOrDefault(word2, new HashSet<>());
    Map<String, Integer> files1 = entriesAsMap(entries1);
    Map<String, Integer> files2 = entriesAsMap(entries2);
    return files1.keySet().stream()
      .filter(f -> files2.containsKey(f))
      .map(f -> new WordEntry(f, files1.get(f) + files2.get(f)))
      .sorted((x, y) -> y.getFrequency() - x.getFrequency())
      .collect(Collectors.toList());
  }

  private Map<String, Integer> entriesAsMap(Set<WordEntry> es) {
    return es.stream().collect(Collectors.toMap(e -> e.getFileName(), e -> e.getFrequency()));
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
