import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileIndexer extends Thread {
  private Index index;
  private String[] fileNames;
  private Set<String> stopWords;

  public FileIndexer(Index index, String[] fileNames, Set<String> stopWords) {
    this.index = index;
    this.fileNames = fileNames;
    this.stopWords = stopWords;
  }

  public void run() {
    try {
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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
