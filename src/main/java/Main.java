import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;
import static spark.Spark.*;
import com.google.gson.Gson;

public class Main {
  public static void main(String[] args) throws Exception {
    List<String> stopWordsList = Files.readAllLines(Paths.get("stopWords.txt"), StandardCharsets.UTF_8);
    Set<String> stopWords = new HashSet<>(stopWordsList);

    Index index = new Index();
    loadIndex(index, args[0], stopWords, Integer.parseInt(args[1]));
    startApi(index);
  }

  public static void loadIndex(Index index, String directory, Set<String> stopWords, int numberOfThreads) throws Exception {
    File folder = new File(directory);
    List<File> files = Arrays.asList(folder.listFiles());
    String[] fileNames = files.stream().map(f -> f.toString()).toArray(String[]::new);
    int filesCount = fileNames.length;

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
  }

  public static void startApi(Index index) {
    threadPool(8);
    Gson gson = new Gson();
    get("/search/:word", (req, res) -> {
      String word = req.params(":word");
      return gson.toJson(index.search(word));
    });
    get("/search/:word1/and/:word2", (req, res) -> {
      String word1 = req.params(":word1");
      String word2 = req.params(":word2");
      return gson.toJson(index.searchAnd(word1, word2));
    });
  }
}
