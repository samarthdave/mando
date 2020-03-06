package Hack.Controller;

public class ScriptException extends Exception {
  public ScriptException(String paramString1, String paramString2, int paramInt) {
    super("In script " + paramString2 + ", Line " + paramInt + ", " + paramString1);
  }
  
  public ScriptException(String paramString1, String paramString2) {
    super("In script " + paramString2 + ", " + paramString1);
  }
  
  public ScriptException(String paramString) {
    super(paramString);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/ScriptException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */