import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class IndexTest {
  static String files = "src/test/resources/files";
  static Index index = new Index();

  private WordEntry we(String fileName, int frequency) {
    return new WordEntry(files + "/" + fileName, frequency);
  }

  @BeforeClass
  public static void loadIndex() throws Exception {
    Set<String> stopWords = new HashSet<String>();
    stopWords.add("s");
    Main.loadIndex(index, files, stopWords, 2);
  }

  @Test
  public void canSearchDog() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("1.txt", 4));
    expected.add(we("3.txt", 2));
    expected.add(we("2.txt", 1));
    assertEquals(expected, index.search("dog"));
  }

  @Test
  public void canSearchSnake() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("3.txt", 2));
    expected.add(we("2.txt", 1));
    assertEquals(expected, index.search("SNAKE"));
  }

  @Test
  public void canSearchCat() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("1.txt", 8));
    expected.add(we("2.txt", 3));
    assertEquals(expected, index.search("cat"));
  }

  @Test
  public void canSearchCar() {
    List<WordEntry> expected = new ArrayList<>();
    assertEquals(expected, index.search("car"));
  }

  @Test
  public void canSearchMonkey() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("1.txt", 2));
    expected.add(we("2.txt", 1));
    assertEquals(expected, index.search("Monkey"));
  }

  @Test
  public void canSearchMoney() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("1.txt", 1));
    assertEquals(expected, index.search("mOnEy"));
  }

  @Test
  public void canSearchMice() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("1.txt", 1));
    assertEquals(expected, index.search("mice"));
  }

  @Test
  public void canSearchSnail() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("2.txt", 2));
    assertEquals(expected, index.search("snail"));
  }

  @Test
  public void canSearchDogAndSnake() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("3.txt", 4));
    expected.add(we("2.txt", 2));
    assertEquals(expected, index.searchAnd("dog", "snake"));
  }

  @Test
  public void canSearchDogAndCar() {
    List<WordEntry> expected = new ArrayList<>();
    assertEquals(expected, index.searchAnd("dog", "car"));
  }

  @Test
  public void canSearchMiceAndSnail() {
    List<WordEntry> expected = new ArrayList<>();
    assertEquals(expected, index.searchAnd("mice", "snail"));
  }

  @Test
  public void canSearchDogAndCat() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("1.txt", 12));
    expected.add(we("2.txt", 4));
    assertEquals(expected, index.searchAnd("dog", "cat"));
  }

  @Test
  public void canSearchMonkeyAndMoney() {
    List<WordEntry> expected = new ArrayList<>();
    expected.add(we("1.txt", 3));
    assertEquals(expected, index.searchAnd("Monkey", "mOnEy"));
  }
}
