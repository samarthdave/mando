package Hack.Translators;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class LineTokenizer extends StreamTokenizer {
  public LineTokenizer(String paramString) throws IOException {
    super(new StringReader(paramString));
    slashSlashComments(true);
  }
  
  public void advance(boolean paramBoolean) throws IOException, HackTranslatorException {
    nextToken();
    if (paramBoolean && this.ttype == -1)
      throw new HackTranslatorException("Unexpected end of line", lineno()); 
  }
  
  public String token() {
    null = null;
    switch (this.ttype) {
      case -2:
        return String.valueOf((int)this.nval);
      case -3:
        return this.sval;
    } 
    return String.valueOf((char)this.ttype);
  }
  
  public int number() {
    return (this.ttype == -2) ? (int)this.nval : 0;
  }
  
  public char symbol() {
    return (this.ttype > 0) ? (char)this.ttype : Character.MIN_VALUE;
  }
  
  public boolean isToken(String paramString) {
    return token().equalsIgnoreCase(paramString);
  }
  
  public boolean isWord() {
    return (this.ttype == -3);
  }
  
  public boolean isNumber() {
    return (this.ttype == -2);
  }
  
  public boolean isSymbol() {
    return (this.ttype > 0);
  }
  
  public boolean isEnd() {
    return (this.ttype == -1);
  }
  
  public void ensureEnd() throws HackTranslatorException, IOException {
    advance(false);
    if (!isEnd())
      throw new HackTranslatorException("end of line expected, '" + token() + "' is found"); 
  }
  
  public boolean contains(String paramString) throws IOException {
    boolean bool = false;
    while (!bool && !isEnd()) {
      if (!(bool = token().equals(paramString)))
        try {
          advance(false);
        } catch (HackTranslatorException hackTranslatorException) {} 
    } 
    return bool;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Translators/LineTokenizer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */