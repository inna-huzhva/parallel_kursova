import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class SpeedTest {
  public static void main(String[] args) throws Exception {
    List<String> stopWordsList = Files.readAllLines(Paths.get("stopWords.txt"), StandardCharsets.UTF_8);
    Set<String> stopWords = new HashSet<>(stopWordsList);

    Index index = new Index();
    Main.loadIndex(index, args[0], stopWords, Integer.parseInt(args[1]));
    index.print();
  }
}