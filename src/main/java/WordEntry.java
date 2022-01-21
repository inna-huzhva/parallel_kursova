public class WordEntry {
  private String fileName;
  private int frequency;

  public WordEntry(String fileName, int frequency) {
    this.fileName = fileName;
    this.frequency = frequency;
  }

  public String getFileName() {
    return fileName;
  }

  public int getFrequency() {
    return frequency;
  }

  @Override
  public String toString() {
    return "(" + fileName + " - " + frequency + ")";
  }
}
