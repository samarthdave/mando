package Hack.Assembler;

public class AssemblerException extends Exception {
  public AssemblerException(String paramString) {
    super(paramString);
  }
  
  public AssemblerException(String paramString, int paramInt) {
    super("In line " + paramInt + ", " + paramString);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Assembler/AssemblerException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */