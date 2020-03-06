package Hack.Controller;

public class ProgramException extends Exception {
  public ProgramException(String paramString) {
    super(paramString);
  }
  
  public ProgramException(String paramString, int paramInt) {
    super("In line " + paramInt + ", " + paramString);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/ProgramException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */