package Hack.Controller;

import Hack.Utilities.Conversions;

public class ScriptCondition {
  public static final byte EQUAL = 1;
  
  public static final byte GREATER = 2;
  
  public static final byte LESS = 3;
  
  public static final byte GREATER_EQUAL = 4;
  
  public static final byte LESS_EQUAL = 5;
  
  public static final byte NOT_EQUAL = 6;
  
  private String arg0;
  
  private String arg1;
  
  private byte comparisonOperator;
  
  public ScriptCondition(ScriptTokenizer paramScriptTokenizer) throws ScriptException, ControllerException {
    if (paramScriptTokenizer.getTokenType() != 3 && paramScriptTokenizer.getTokenType() != 4)
      throw new ScriptException("A condition expected"); 
    this.arg0 = paramScriptTokenizer.getToken();
    paramScriptTokenizer.advance();
    if (paramScriptTokenizer.getTokenType() == 2) {
      String str = paramScriptTokenizer.getToken();
      paramScriptTokenizer.advance();
      if (paramScriptTokenizer.getTokenType() == 2) {
        str = str + paramScriptTokenizer.getToken();
        paramScriptTokenizer.advance();
      } 
      if (str.equals("=")) {
        this.comparisonOperator = 1;
      } else if (str.equals(">")) {
        this.comparisonOperator = 2;
      } else if (str.equals(">=")) {
        this.comparisonOperator = 4;
      } else if (str.equals("<")) {
        this.comparisonOperator = 3;
      } else if (str.equals("<=")) {
        this.comparisonOperator = 5;
      } else if (str.equals("<>")) {
        this.comparisonOperator = 6;
      } else {
        throw new ScriptException("Illegal comparison operator: " + str);
      } 
    } else {
      throw new ScriptException("Comparison operator expected");
    } 
    if (paramScriptTokenizer.getTokenType() != 3 && paramScriptTokenizer.getTokenType() != 4)
      throw new ScriptException("A variable name or constant expected"); 
    this.arg1 = paramScriptTokenizer.getToken();
    paramScriptTokenizer.advance();
  }
  
  public boolean compare(HackSimulator paramHackSimulator) throws ControllerException {
    String str1;
    String str2;
    boolean bool1;
    boolean bool2;
    boolean bool = false;
    int i = 0;
    int j = 0;
    try {
      str1 = paramHackSimulator.getValue(this.arg0);
    } catch (VariableException variableException) {
      str1 = this.arg0;
    } 
    try {
      str2 = paramHackSimulator.getValue(this.arg1);
    } catch (VariableException variableException) {
      str2 = this.arg1;
    } 
    try {
      i = Integer.parseInt(Conversions.toDecimalForm(str1));
      bool1 = true;
    } catch (NumberFormatException numberFormatException) {
      bool1 = false;
    } 
    try {
      j = Integer.parseInt(Conversions.toDecimalForm(str2));
      bool2 = true;
    } catch (NumberFormatException numberFormatException) {
      bool2 = false;
    } 
    if (bool1 && bool2) {
      switch (this.comparisonOperator) {
        case 1:
          bool = (i == j);
          break;
        case 2:
          bool = (i > j);
          break;
        case 3:
          bool = (i < j);
          break;
        case 4:
          bool = (i >= j);
          break;
        case 5:
          bool = (i <= j);
          break;
        case 6:
          bool = (i != j);
          break;
      } 
    } else {
      if (!bool1 && !bool2) {
        switch (this.comparisonOperator) {
          case 1:
            return str1.equals(str2);
          case 6:
            return !str1.equals(str2);
        } 
        throw new ControllerException("Only = and <> can be used to compare strings");
      } 
      throw new ControllerException("Cannot compare an integer with a string");
    } 
    return bool;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/ScriptCondition.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */