package Hack.Utilities;

public class Conversions {
  private static final String ZEROS = "0000000000000000000000000000000000000000";
  
  private static final int[] powersOf2 = new int[] { 
      1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 
      1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 
      1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 
      1073741824, Integer.MIN_VALUE };
  
  private static final int[] powersOf16 = new int[] { 1, 16, 256, 4096, 65536, 1048576, 16777216, 268435456 };
  
  public static String toDecimalForm(String paramString) {
    if (paramString.startsWith("%B")) {
      paramString = String.valueOf(binaryToInt(paramString.substring(2)));
    } else if (paramString.startsWith("%X")) {
      if (paramString.length() == 6) {
        paramString = String.valueOf(hex4ToInt(paramString.substring(2)));
      } else {
        paramString = String.valueOf(hexToInt(paramString.substring(2)));
      } 
    } else if (paramString.startsWith("%D")) {
      paramString = paramString.substring(2);
    } else {
      try {
        int i = Integer.parseInt(paramString);
        paramString = String.valueOf(i);
      } catch (NumberFormatException numberFormatException) {}
    } 
    return paramString;
  }
  
  public static int binaryToInt(String paramString) throws NumberFormatException {
    int i = 0;
    int j = paramString.length() - 1;
    for (int k = 1; j >= 0; k <<= 1) {
      char c = paramString.charAt(j);
      if (c == '1') {
        i = (short)(i | k);
      } else if (c != '0') {
        throw new NumberFormatException();
      } 
      j--;
    } 
    return i;
  }
  
  public static int hexToInt(String paramString) throws NumberFormatException {
    int i = 0;
    int j = 1;
    int k = paramString.length() - 1;
    while (k >= 0) {
      char c = paramString.charAt(k);
      if (c >= '0' && c <= '9') {
        i += (c - 48) * j;
      } else if (c >= 'a' && c <= 'f') {
        i += (c - 97 + 10) * j;
      } else if (c >= 'A' && c <= 'F') {
        i += (c - 65 + 10) * j;
      } else {
        throw new NumberFormatException();
      } 
      k--;
      j *= 16;
    } 
    return i;
  }
  
  public static int hex4ToInt(String paramString) throws NumberFormatException {
    int i = hexToInt(paramString);
    if (i > 32767)
      i -= 65536; 
    return i;
  }
  
  public static String decimalToBinary(int paramInt1, int paramInt2) {
    paramInt1 &= powersOf2[paramInt2] - 1;
    String str = Integer.toBinaryString(paramInt1);
    if (str.length() < paramInt2)
      str = "0000000000000000000000000000000000000000".substring(0, paramInt2 - str.length()) + str; 
    return str;
  }
  
  public static String decimalToHex(int paramInt1, int paramInt2) {
    paramInt1 &= powersOf16[paramInt2] - 1;
    String str = Integer.toHexString(paramInt1);
    if (str.length() < paramInt2)
      str = "0000000000000000000000000000000000000000".substring(0, paramInt2 - str.length()) + str; 
    return str;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Utilities/Conversions.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */