package Hack.Translators;

public class HackTranslatorException extends Exception {
  public HackTranslatorException(String paramString) {
    super(paramString);
  }
  
  public HackTranslatorException(String paramString, int paramInt) {
    super("In line " + paramInt + ", " + paramString);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Translators/HackTranslatorException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */