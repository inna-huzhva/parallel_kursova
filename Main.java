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
    for (int i = 0; i < numberOfThreads; i++) {
      int startIndex = filesCount / numberOfThreads * i;
      int endIndex = i == (numberOfThreads - 1) ? filesCount : filesCount / numberOfThreads * (i + 1);
      String[] partOfFileNames = Arrays.copyOfRange(fileNames, startIndex, endIndex);
      FileIndexer.buildIndex(index, partOfFileNames, stopWords);
    }
    index.print();
  }
}
