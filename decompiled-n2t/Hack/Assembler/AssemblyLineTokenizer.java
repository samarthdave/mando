package Hack.Assembler;

import Hack.Translators.LineTokenizer;
import java.io.IOException;
import java.text.StringCharacterIterator;

public class AssemblyLineTokenizer extends LineTokenizer {
  public AssemblyLineTokenizer(String paramString) throws IOException {
    super(removeSpaces(paramString));
    resetSyntax();
    slashSlashComments(true);
    whitespaceChars(32, 32);
    whitespaceChars(10, 10);
    whitespaceChars(13, 13);
    whitespaceChars(9, 9);
    wordChars(48, 57);
    wordChars(65, 90);
    wordChars(97, 122);
    wordChars(95, 95);
    wordChars(43, 43);
    wordChars(45, 45);
    wordChars(46, 46);
    wordChars(58, 58);
    wordChars(33, 33);
    wordChars(38, 38);
    wordChars(124, 124);
    wordChars(36, 36);
    nextToken();
  }
  
  private static String removeSpaces(String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(paramString);
    stringCharacterIterator.first();
    while (stringCharacterIterator.current() != Character.MAX_VALUE) {
      if (stringCharacterIterator.current() != ' ')
        stringBuffer.append(stringCharacterIterator.current()); 
      stringCharacterIterator.next();
    } 
    return stringBuffer.toString();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Assembler/AssemblyLineTokenizer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */