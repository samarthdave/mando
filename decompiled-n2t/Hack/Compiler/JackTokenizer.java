package Hack.Compiler;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Hashtable;

public class JackTokenizer {
  public static final int TYPE_KEYWORD = 1;
  
  public static final int TYPE_SYMBOL = 2;
  
  public static final int TYPE_IDENTIFIER = 3;
  
  public static final int TYPE_INT_CONST = 4;
  
  public static final int TYPE_STRING_CONST = 5;
  
  public static final int KW_CLASS = 1;
  
  public static final int KW_METHOD = 2;
  
  public static final int KW_FUNCTION = 3;
  
  public static final int KW_CONSTRUCTOR = 4;
  
  public static final int KW_INT = 5;
  
  public static final int KW_BOOLEAN = 6;
  
  public static final int KW_CHAR = 7;
  
  public static final int KW_VOID = 8;
  
  public static final int KW_VAR = 9;
  
  public static final int KW_STATIC = 10;
  
  public static final int KW_FIELD = 11;
  
  public static final int KW_LET = 12;
  
  public static final int KW_DO = 13;
  
  public static final int KW_IF = 14;
  
  public static final int KW_ELSE = 15;
  
  public static final int KW_WHILE = 16;
  
  public static final int KW_RETURN = 17;
  
  public static final int KW_TRUE = 18;
  
  public static final int KW_FALSE = 19;
  
  public static final int KW_NULL = 20;
  
  public static final int KW_THIS = 21;
  
  private StreamTokenizer parser;
  
  private Hashtable keywords;
  
  private Hashtable symbols;
  
  private int tokenType;
  
  private int keyWordType;
  
  private char symbol;
  
  private int intValue;
  
  private int lineNumber;
  
  private String stringValue;
  
  private String identifier;
  
  public JackTokenizer(Reader paramReader) {
    try {
      this.parser = new StreamTokenizer(paramReader);
      this.parser.parseNumbers();
      this.parser.slashSlashComments(true);
      this.parser.slashStarComments(true);
      this.parser.ordinaryChar(46);
      this.parser.ordinaryChar(45);
      this.parser.ordinaryChar(60);
      this.parser.ordinaryChar(62);
      this.parser.ordinaryChar(47);
      this.parser.wordChars(95, 95);
      this.parser.nextToken();
      initKeywords();
      initSymbols();
    } catch (IOException iOException) {
      System.out.println("JackTokenizer failed during initialization operation");
      System.exit(-1);
    } 
  }
  
  public void advance() {
    try {
      String str;
      switch (this.parser.ttype) {
        case -2:
          this.tokenType = 4;
          this.intValue = (int)this.parser.nval;
          break;
        case -3:
          str = this.parser.sval;
          if (this.keywords.containsKey(str)) {
            this.tokenType = 1;
            this.keyWordType = ((Integer)this.keywords.get(str)).intValue();
            break;
          } 
          this.tokenType = 3;
          this.identifier = str;
          break;
        case 34:
          this.tokenType = 5;
          this.stringValue = this.parser.sval;
          break;
        default:
          this.tokenType = 2;
          this.symbol = (char)this.parser.ttype;
          break;
      } 
      this.lineNumber = this.parser.lineno();
      this.parser.nextToken();
    } catch (IOException iOException) {
      System.out.println("JackTokenizer failed during advance operation");
      System.exit(-1);
    } 
  }
  
  public int getTokenType() {
    return this.tokenType;
  }
  
  public int getKeywordType() {
    return this.keyWordType;
  }
  
  public char getSymbol() {
    return this.symbol;
  }
  
  public int getIntValue() {
    return this.intValue;
  }
  
  public String getStringValue() {
    return this.stringValue;
  }
  
  public String getIdentifier() {
    return this.identifier;
  }
  
  public boolean hasMoreTokens() {
    return (this.parser.ttype != -1);
  }
  
  public int getLineNumber() {
    return this.lineNumber;
  }
  
  private void initKeywords() {
    this.keywords = new Hashtable();
    this.keywords.put("class", new Integer(1));
    this.keywords.put("method", new Integer(2));
    this.keywords.put("function", new Integer(3));
    this.keywords.put("constructor", new Integer(4));
    this.keywords.put("int", new Integer(5));
    this.keywords.put("boolean", new Integer(6));
    this.keywords.put("char", new Integer(7));
    this.keywords.put("void", new Integer(8));
    this.keywords.put("var", new Integer(9));
    this.keywords.put("static", new Integer(10));
    this.keywords.put("field", new Integer(11));
    this.keywords.put("let", new Integer(12));
    this.keywords.put("do", new Integer(13));
    this.keywords.put("if", new Integer(14));
    this.keywords.put("else", new Integer(15));
    this.keywords.put("while", new Integer(16));
    this.keywords.put("return", new Integer(17));
    this.keywords.put("true", new Integer(18));
    this.keywords.put("false", new Integer(19));
    this.keywords.put("null", new Integer(20));
    this.keywords.put("this", new Integer(21));
  }
  
  private void initSymbols() {
    this.symbols = new Hashtable();
    this.symbols.put("(", "(");
    this.symbols.put(")", ")");
    this.symbols.put("[", "[");
    this.symbols.put("]", "]");
    this.symbols.put("{", "{");
    this.symbols.put("}", "}");
    this.symbols.put(",", ",");
    this.symbols.put(";", ";");
    this.symbols.put("=", "=");
    this.symbols.put(".", ".");
    this.symbols.put("+", "+");
    this.symbols.put("-", "-");
    this.symbols.put("*", "*");
    this.symbols.put("/", "/");
    this.symbols.put("&", "&");
    this.symbols.put("|", "|");
    this.symbols.put("~", "~");
    this.symbols.put("<", "<");
    this.symbols.put(">", ">");
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Compiler/JackTokenizer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */