package Hack.Controller;

public class CommandException extends Exception {
  public CommandException(String paramString, String[] paramArrayOfString) {
    super(paramString + ": " + commandString(paramArrayOfString));
  }
  
  private static String commandString(String[] paramArrayOfString) {
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < paramArrayOfString.length; b++)
      stringBuffer.append(paramArrayOfString[b] + " "); 
    return stringBuffer.toString();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/CommandException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */