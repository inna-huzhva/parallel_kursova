import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class Main {
  public static void main(String[] args) throws Exception {
    Index index = new Index();
    List<String> stopWordsList = Files.readAllLines(Paths.get("stopWords.txt"), StandardCharsets.UTF_8);
    Set<String> stopWords = new HashSet<>(stopWordsList);

    File folder = new File(args[0]);
    List<File> files = Arrays.asList(folder.listFiles());
    String[] fileNames = files.stream().map(f -> f.toString()).toArray(String[]::new);
    int filesCount = fileNames.length;

    int numberOfThreads = Integer.parseInt(args[1]);
    FileIndexer[] workers = new FileIndexer[numberOfThreads];
    for (int i = 0; i < numberOfThreads; i++) {
      int startIndex = filesCount / numberOfThreads * i;
      int endIndex = i == (numberOfThreads - 1) ? filesCount : filesCount / numberOfThreads * (i + 1);
      String[] partOfFileNames = Arrays.copyOfRange(fileNames, startIndex, endIndex);
      workers[i] = new FileIndexer(index, partOfFileNames, stopWords);
      workers[i].start();
    }
    for (int i = 0; i < numberOfThreads; i++) {
      workers[i].join();
    }
    check(index, "dog");
    check(index, "SNAKE");
    check(index, "cat");
    check(index, "car");
    check(index, "Monkey");
    check(index, "mOnEy");
    check(index, "mice");
    check(index, "snail");
    System.out.println();
    checkAnd(index, "dog", "snake");
    checkAnd(index, "dog", "car");
    checkAnd(index, "mice", "snail");
    checkAnd(index, "dog", "cat");
    checkAnd(index, "Monkey", "mOnEy");
    //index.print();
  }

  private static void check(Index index, String word) {
    System.out.println(word + ": " + index.search(word));
  }

  private static void checkAnd(Index index, String word1, String word2) {
    System.out.println(word1 + " AND " + word2 + ": " + index.searchAnd(word1, word2));
  }
}
