import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileIndexer {
  public static void buildIndex(Map<String, Set<WordEntry>> index, String[] fileNames, Set<String> stopWords) throws Exception {
    for (String fileName : fileNames) {
      Map<String, Integer> words = new HashMap<>();
      List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
      for (String l : lines) {
        String[] lineWords = l.split("\\W+");
        for (String rw : lineWords) {
          String w = rw.toLowerCase();
          if (!w.isEmpty() && !stopWords.contains(w)) {
            int previous = words.getOrDefault(w, 0);
            words.put(w, previous + 1);
          }
        }
      }
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
  }
}
