package Hack.Controller;

public class VariableFormat {
  public static final char BINARY_FORMAT = 'B';
  
  public static final char DECIMAL_FORMAT = 'D';
  
  public static final char HEX_FORMAT = 'X';
  
  public static final char STRING_FORMAT = 'S';
  
  public String varName;
  
  public int padL;
  
  public int padR;
  
  public int len;
  
  public char format;
  
  public VariableFormat(String paramString, char paramChar, int paramInt1, int paramInt2, int paramInt3) {
    this.varName = paramString;
    this.format = paramChar;
    this.padL = paramInt1;
    this.padR = paramInt2;
    this.len = paramInt3;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/VariableFormat.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */