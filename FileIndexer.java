import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class FileIndexer {
  public static Map<String, Set<WordEntry>> buildIndex(String[] fileNames, Set<String> stopWords) throws Exception {
    Map<String, Set<WordEntry>> index = new HashMap<>();
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
    return index;
  }

  public static void printIndex(Map<String, Set<WordEntry>> index) {
    index.forEach((w, es) -> {
      System.out.println(w + ":");
      es.stream()
        .sorted((x, y) -> y.getFrequency() - x.getFrequency()) // function for comparison 
        .forEach(e -> System.out.println("  " + e));
    });
  }

  public static void main(String[] args) throws Exception {
    List<String> stopWordsList = Files.readAllLines(Paths.get("stopWords.txt"), StandardCharsets.UTF_8);
    Set<String> stopWords = new HashSet<>(stopWordsList);

    File folder = new File(args[0]);
    List<File> files = Arrays.asList(folder.listFiles());
    String[] fileNames = files.stream().map(f -> f.toString()).toArray(String[]::new);

    Map<String, Set<WordEntry>> index = buildIndex(fileNames, stopWords);
    printIndex(index);
  }
}
