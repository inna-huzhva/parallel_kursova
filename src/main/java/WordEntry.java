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
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != this.getClass()) {
      return false;
    }
    WordEntry other = (WordEntry) obj;
    return other.fileName.equals(this.fileName) && other.frequency == this.frequency;
  }

  @Override
  public String toString() {
    return "(" + fileName + " - " + frequency + ")";
  }
}
