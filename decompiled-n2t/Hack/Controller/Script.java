package Hack.Controller;

import Hack.Utilities.Conversions;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Script {
  public static final int MAX_SIMULATOR_COMMAND_ARGUMENTS = 4;
  
  public static final int MAX_OUTPUT_LIST_ARGUMENTS = 20;
  
  private Vector commands;
  
  private Vector lineNumbers;
  
  private String scriptName;
  
  private ScriptTokenizer input;
  
  public Script(String paramString) throws ScriptException, ControllerException {
    this.scriptName = paramString;
    try {
      this.input = new ScriptTokenizer(new FileReader(paramString));
    } catch (IOException iOException) {
      throw new ScriptException("Script " + paramString + " not found");
    } 
    this.commands = new Vector();
    this.lineNumbers = new Vector();
    buildScript();
  }
  
  private void buildScript() throws ScriptException, ControllerException {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    Command command = null;
    int i = 0;
    while (this.input.hasMoreTokens()) {
      i = this.input.getLineNumber() - 1;
      this.input.advance();
      if (bool3 && this.input.getTokenType() == 2 && this.input.getSymbol() == '}')
        scriptError("An empty " + (bool1 ? "Repeat" : "While") + " block is not allowed"); 
      bool3 = false;
      switch (this.input.getTokenType()) {
        case 1:
          command = createControllerCommand();
          if (command.getCode() == 8) {
            if (bool1 || bool2) {
              scriptError("Nested Repeat and While are not allowed");
              break;
            } 
            bool1 = true;
            bool3 = true;
            break;
          } 
          if (command.getCode() == 10) {
            if (bool1 || bool2) {
              scriptError("Nested Repeat and While are not allowed");
              break;
            } 
            bool2 = true;
            bool3 = true;
            break;
          } 
          if (command.getCode() == 4) {
            bool4 = true;
            break;
          } 
          if (command.getCode() == 5 && !bool4)
            scriptError("No output list created"); 
          break;
        case 3:
          command = createSimulatorCommand();
          break;
        case 4:
          scriptError("A command cannot begin with " + this.input.getIntValue());
        case 2:
          if (this.input.getSymbol() == '}') {
            if (!bool1 && !bool2) {
              scriptError("a '}' without a Repeat or While");
              break;
            } 
            if (bool1) {
              command = new Command((byte)9);
              bool1 = false;
              break;
            } 
            if (bool2) {
              command = new Command((byte)11);
              bool2 = false;
            } 
            break;
          } 
          scriptError("A command cannot begin with '" + this.input.getSymbol() + "'");
          break;
      } 
      switch (this.input.getSymbol()) {
        case ',':
          command.setTerminator((byte)1);
          break;
        case ';':
          command.setTerminator((byte)2);
          break;
        case '!':
          command.setTerminator((byte)3);
          break;
      } 
      this.commands.addElement(command);
      this.lineNumbers.addElement(new Integer(i));
    } 
    if (bool1 || bool2)
      scriptError("Repeat or While not closed"); 
    command = new Command((byte)12);
    this.commands.addElement(command);
    this.lineNumbers.addElement(new Integer(i));
  }
  
  private Command createSimulatorCommand() throws ControllerException, ScriptException {
    String[] arrayOfString1 = readArgs(4);
    byte b;
    for (b = 0; b < arrayOfString1.length && arrayOfString1[b] != null; b++);
    String[] arrayOfString2 = new String[b];
    System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, b);
    return new Command((byte)1, arrayOfString2);
  }
  
  private Command createControllerCommand() throws ControllerException, ScriptException {
    Command command = null;
    switch (this.input.getKeywordType()) {
      case 1:
        command = createOutputFileCommand();
        break;
      case 2:
        command = createCompareToCommand();
        break;
      case 3:
        command = createOutputListCommand();
        break;
      case 4:
        command = createOutputCommand();
        break;
      case 9:
        command = createEchoCommand();
        break;
      case 10:
        command = createClearEchoCommand();
        break;
      case 5:
        command = createBreakpointCommand();
        break;
      case 6:
        command = createClearBreakpointsCommand();
        break;
      case 7:
        command = createRepeatCommand();
        break;
      case 8:
        command = createWhileCommand();
        break;
    } 
    return command;
  }
  
  private Command createOutputFileCommand() throws ControllerException, ScriptException {
    this.input.advance();
    String[] arrayOfString = readArgs(1);
    return new Command((byte)2, arrayOfString[0]);
  }
  
  private Command createCompareToCommand() throws ControllerException, ScriptException {
    this.input.advance();
    String[] arrayOfString = readArgs(1);
    return new Command((byte)3, arrayOfString[0]);
  }
  
  private Command createOutputListCommand() throws ControllerException, ScriptException {
    this.input.advance();
    String[] arrayOfString = readArgs(20);
    byte b1;
    for (b1 = 0; b1 < arrayOfString.length && arrayOfString[b1] != null; b1++);
    VariableFormat[] arrayOfVariableFormat = new VariableFormat[b1];
    for (byte b2 = 0; b2 < b1; b2++) {
      int i = arrayOfString[b2].indexOf('%');
      if (i == -1) {
        i = arrayOfString[b2].length();
        arrayOfString[b2] = arrayOfString[b2] + "%B1.1.1";
      } 
      String str = arrayOfString[b2].substring(0, i);
      char c = arrayOfString[b2].charAt(i + 1);
      if (c != 'B' && c != 'D' && c != 'X' && c != 'S')
        scriptError("%" + c + " is not a legal format"); 
      int j = 0;
      int k = arrayOfString[b2].indexOf('.', i);
      if (k == -1)
        scriptError("Missing '.'"); 
      try {
        j = Integer.parseInt(arrayOfString[b2].substring(i + 2, k));
      } catch (NumberFormatException numberFormatException) {
        scriptError("padL must be a number");
      } 
      if (j < 0)
        scriptError("padL must be positive"); 
      int m = 0;
      int n = arrayOfString[b2].indexOf('.', k + 1);
      if (n == -1)
        scriptError("Missing '.'"); 
      try {
        m = Integer.parseInt(arrayOfString[b2].substring(k + 1, n));
      } catch (NumberFormatException numberFormatException) {
        scriptError("len must be a number");
      } 
      if (m < 1)
        scriptError("len must be greater than 0"); 
      int i1 = 0;
      try {
        i1 = Integer.parseInt(arrayOfString[b2].substring(n + 1));
      } catch (NumberFormatException numberFormatException) {
        scriptError("padR must be a number");
      } 
      if (i1 < 0)
        scriptError("padR must be positive"); 
      arrayOfVariableFormat[b2] = new VariableFormat(str, c, j, i1, m);
    } 
    return new Command((byte)4, arrayOfVariableFormat);
  }
  
  private Command createOutputCommand() throws ControllerException, ScriptException {
    this.input.advance();
    checkTerminator();
    return new Command((byte)5);
  }
  
  private Command createEchoCommand() throws ControllerException, ScriptException {
    this.input.advance();
    String[] arrayOfString = readArgs(1);
    return new Command((byte)13, arrayOfString[0]);
  }
  
  private Command createClearEchoCommand() throws ControllerException, ScriptException {
    this.input.advance();
    checkTerminator();
    return new Command((byte)14);
  }
  
  private Command createBreakpointCommand() throws ControllerException, ScriptException {
    this.input.advance();
    String[] arrayOfString = readArgs(2);
    byte b;
    for (b = 0; b < arrayOfString.length && arrayOfString[b] != null; b++);
    if (b < 2)
      scriptError("Not enough arguments"); 
    String str = arrayOfString[1];
    if (str.startsWith("%S")) {
      str = str.substring(2);
    } else if (arrayOfString[1].startsWith("%")) {
      str = Conversions.toDecimalForm(str);
    } 
    Breakpoint breakpoint = new Breakpoint(arrayOfString[0], str);
    return new Command((byte)6, breakpoint);
  }
  
  private Command createClearBreakpointsCommand() throws ControllerException, ScriptException {
    this.input.advance();
    checkTerminator();
    return new Command((byte)7);
  }
  
  private Command createRepeatCommand() throws ScriptException, ControllerException {
    this.input.advance();
    int i = 0;
    if (this.input.getTokenType() == 4) {
      i = this.input.getIntValue();
      if (i < 1)
        scriptError("Illegal repeat quantity"); 
      this.input.advance();
    } 
    if (this.input.getTokenType() != 2 || this.input.getSymbol() != '{')
      scriptError("Missing '{' in repeat command"); 
    return new Command((byte)8, new Integer(i));
  }
  
  private Command createWhileCommand() throws ScriptException, ControllerException {
    this.input.advance();
    ScriptCondition scriptCondition = null;
    try {
      scriptCondition = new ScriptCondition(this.input);
    } catch (ScriptException scriptException) {
      scriptError(scriptException.getMessage());
    } 
    if (this.input.getTokenType() != 2 || this.input.getSymbol() != '{')
      scriptError("Missing '{' in while command"); 
    return new Command((byte)10, scriptCondition);
  }
  
  private String[] readArgs(int paramInt) throws ControllerException, ScriptException {
    String[] arrayOfString = new String[paramInt];
    byte b = 0;
    while (this.input.hasMoreTokens() && this.input.getTokenType() != 2 && b < paramInt) {
      arrayOfString[b++] = this.input.getToken();
      this.input.advance();
    } 
    checkTerminator();
    if (b == 0)
      scriptError("Missing arguments"); 
    return arrayOfString;
  }
  
  private void checkTerminator() throws ScriptException {
    if (this.input.getTokenType() != 2) {
      if (this.input.hasMoreTokens()) {
        scriptError("too many arguments");
      } else {
        scriptError("Script ends without a terminator");
      } 
    } else if (this.input.getSymbol() != ',' && this.input.getSymbol() != ';' && this.input.getSymbol() != '!') {
      scriptError("Illegal terminator: '" + this.input.getSymbol() + "'");
    } 
  }
  
  private void scriptError(String paramString) throws ScriptException {
    throw new ScriptException(paramString, this.scriptName, this.input.getLineNumber());
  }
  
  public Command getCommandAt(int paramInt) {
    return this.commands.elementAt(paramInt);
  }
  
  public int getLineNumberAt(int paramInt) {
    return ((Integer)this.lineNumbers.elementAt(paramInt)).intValue();
  }
  
  public int getLength() {
    return this.commands.size();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/Script.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */