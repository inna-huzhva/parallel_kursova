import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileIndexer {
  public static void buildIndex(Index index, String[] fileNames, Set<String> stopWords) throws Exception {
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
      index.update(fileName, words);
    }
  }
}
