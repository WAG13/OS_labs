package memoryManager;

public class Common {

  static public int stringToInt(String s) {
    int i = 0;

    try {
      i = Integer.parseInt(s.trim());
    } catch (NumberFormatException nfe) {
      System.out.println("NumberFormatException: " + nfe.getMessage());
    }

    return i;
  }

  static public byte stringToByte(String s) {
    return (byte) stringToInt(s);
  }

  public static long randomLong(long MAX) {
    return (long) (Math.random()*MAX);
  }
}
