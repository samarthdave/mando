package Hack.Compiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class CompilationEngine {
  private static final int GENERAL_TYPE = 1;
  
  private static final int NUMERIC_TYPE = 2;
  
  private static final int INT_TYPE = 3;
  
  private static final int CHAR_TYPE = 4;
  
  private static final int BOOLEAN_TYPE = 5;
  
  private static final int STRING_TYPE = 6;
  
  private static final int THIS_TYPE = 7;
  
  private static final int NULL_TYPE = 8;
  
  private JackTokenizer input;
  
  private VMWriter output;
  
  private SymbolTable identifiers;
  
  private HashMap subroutines = new HashMap();
  
  private HashSet classes = new HashSet();
  
  private Vector subroutineCalls = new Vector();
  
  private int ifCounter;
  
  private int whileCounter;
  
  private int subroutineType;
  
  private int[] expTypes;
  
  private int expIndex;
  
  private String fileName;
  
  private boolean validJack;
  
  public boolean compileClass(JackTokenizer paramJackTokenizer, VMWriter paramVMWriter, String paramString1, String paramString2) {
    this.input = paramJackTokenizer;
    this.output = paramVMWriter;
    this.fileName = paramString2;
    this.expTypes = new int[100];
    this.expIndex = -1;
    this.validJack = true;
    paramJackTokenizer.advance();
    String str = null;
    try {
      if (isKeywordClass()) {
        paramJackTokenizer.advance();
      } else {
        recoverableError("Expected 'class'", -1, "", paramString2);
      } 
      if (isIdentifier()) {
        str = paramJackTokenizer.getIdentifier();
        if (!str.equals(paramString1))
          recoverableError("The class name doesn't match the file name", -1, "", paramString2); 
        paramJackTokenizer.advance();
      } else {
        recoverableError("Expected a class name", -1, "", paramString2);
        str = paramString1;
      } 
      this.identifiers = new SymbolTable(str);
      if (isSymbol('{')) {
        paramJackTokenizer.advance();
      } else {
        recoverableError("Expected {", -1, "", paramString2);
      } 
      compileFieldAndStaticDeclarations();
      compileAllSubroutines();
      if (!isSymbol('}'))
        recoverableError("Expected }", -1, "", paramString2); 
      if (paramJackTokenizer.hasMoreTokens())
        recoverableError("Expected end-of-file", -1, "", paramString2); 
    } catch (JackException jackException) {
    
    } finally {
      paramVMWriter.close();
      if (this.validJack)
        this.classes.add(str); 
      return this.validJack;
    } 
    while (true);
  }
  
  boolean verifySubroutineCalls() {
    this.validJack = true;
    for (Object[] arrayOfObject : this.subroutineCalls) {
      String str1 = (String)arrayOfObject[0];
      boolean bool = ((Boolean)arrayOfObject[1]).booleanValue();
      short s1 = ((Short)arrayOfObject[2]).shortValue();
      String str2 = (String)arrayOfObject[3];
      String str3 = (String)arrayOfObject[4];
      int i = ((Integer)arrayOfObject[5]).intValue();
      if (!this.classes.contains(str1.substring(0, str1.indexOf("."))))
        continue; 
      if (!this.subroutines.containsKey(str1)) {
        recoverableError((bool ? "Method " : "Function or constructor ") + str1 + " doesn't exist", i, str3, str2);
        continue;
      } 
      arrayOfObject = (Object[])this.subroutines.get(str1);
      int j = ((Integer)arrayOfObject[0]).intValue();
      short s2 = ((Short)arrayOfObject[1]).shortValue();
      if (bool && j != 1) {
        recoverableError(((j == 2) ? "Function " : "Constructor ") + str1 + " called as a method", i, str3, str2);
      } else if (!bool && j == 1) {
        recoverableError("Method " + str1 + " called as a function/constructor", i, str3, str2);
      } 
      if (s1 != s2)
        recoverableError("Subroutine " + str1 + " (declared to accept " + s2 + " parameter(s)) called with " + s1 + " parameter(s)", i, str3, str2); 
    } 
    return this.validJack;
  }
  
  private void compileAllSubroutines() throws JackException {
    while (isKeywordMethod() || isKeywordFunction() || isKeywordConstructor()) {
      try {
        if (isKeywordMethod()) {
          compileMethod();
          continue;
        } 
        if (isKeywordFunction()) {
          compileFunction();
          continue;
        } 
        compileConstructor();
      } catch (JackException jackException) {
        while (!isKeywordMethod() && !isKeywordFunction() && !isKeywordConstructor() && this.input.hasMoreTokens())
          this.input.advance(); 
      } 
    } 
  }
  
  private void compileMethod() throws JackException {
    compileSubroutine(1);
  }
  
  private void compileFunction() throws JackException {
    compileSubroutine(2);
  }
  
  private void compileConstructor() throws JackException {
    compileSubroutine(3);
  }
  
  private void compileSubroutine(int paramInt) throws JackException {
    String str3;
    this.subroutineType = paramInt;
    this.ifCounter = 0;
    this.whileCounter = 0;
    this.input.advance();
    String str1 = null;
    if (isKeywordVoid()) {
      str1 = "void";
    } else {
      str1 = getType();
    } 
    int i = this.input.getLineNumber();
    this.input.advance();
    String str2 = null;
    if (isIdentifier()) {
      str2 = this.input.getIdentifier();
      str3 = this.identifiers.getClassName() + "." + str2;
      if (this.subroutines.containsKey(str3))
        recoverableError("Subroutine " + str2 + " redeclared", -1, "", this.fileName); 
      this.input.advance();
    } else {
      recoverableError("Expected a type followed by a subroutine name", -1, "", this.fileName);
      str3 = this.identifiers.getClassName() + "." + "unknownname";
    } 
    switch (paramInt) {
      case 1:
        this.identifiers.startMethod(str2, str1);
        break;
      case 2:
        this.identifiers.startFunction(str2, str1);
        break;
      case 3:
        this.identifiers.startConstructor(str2);
        if (!str1.equals(this.identifiers.getClassName()))
          recoverableError("The return type of a constructor must be of the class type", i); 
        break;
    } 
    if (isSymbol('(')) {
      this.input.advance();
    } else {
      recoverableError("Expected (");
    } 
    short s = compileParametersList();
    this.input.advance();
    compileSubroutineBody(str3);
    this.identifiers.endSubroutine();
    if (str2 != null)
      this.subroutines.put(str3, new Object[] { new Integer(paramInt), new Short(s) }); 
  }
  
  private void compileSubroutineBody(String paramString) throws JackException {
    if (isSymbol('{')) {
      this.input.advance();
    } else {
      recoverableError("Expected {");
    } 
    compileLocalsDeclarations();
    short s = this.identifiers.getNumberOfIdentifiers(3);
    this.output.function(paramString, s);
    if (this.subroutineType == 1) {
      this.output.push("argument", (short)0);
      this.output.pop("pointer", (short)0);
    } else if (this.subroutineType == 3) {
      short s1 = this.identifiers.getNumberOfIdentifiers(1);
      this.output.push("constant", s1);
      this.output.callFunction("Memory.alloc", (short)1);
      this.output.pop("pointer", (short)0);
    } 
    if (compileStatements(true))
      recoverableError("Program flow may reach end of subroutine without 'return'"); 
    this.input.advance();
  }
  
  private short compileParametersList() throws JackException {
    short s = 0;
    if (isSymbol(')'))
      return s; 
    boolean bool = true;
    while (bool) {
      s = (short)(s + 1);
      String str1 = getType();
      this.input.advance();
      String str2 = null;
      if (isIdentifier()) {
        str2 = this.input.getIdentifier();
        this.input.advance();
      } else {
        recoverableError("Expected a type followed by a variable name");
      } 
      this.identifiers.define(str2, str1, 2);
      if (isSymbol(')')) {
        bool = false;
        continue;
      } 
      if (isSymbol(',')) {
        this.input.advance();
        continue;
      } 
      terminalError("Expected ) or , in parameters list");
    } 
    return s;
  }
  
  private void compileFieldAndStaticDeclarations() throws JackException {
    for (boolean bool = true; bool; bool = false) {
      if (isKeywordField()) {
        compileDeclarationLine(1);
        continue;
      } 
      if (isKeywordStatic()) {
        compileDeclarationLine(0);
        continue;
      } 
    } 
  }
  
  private void compileLocalsDeclarations() throws JackException {
    while (isKeywordLocal())
      compileDeclarationLine(3); 
  }
  
  private void compileDeclarationLine(int paramInt) throws JackException {
    this.input.advance();
    String str = getType();
    boolean bool = true;
    while (bool) {
      this.input.advance();
      if (isIdentifier()) {
        this.identifiers.define(this.input.getIdentifier(), str, paramInt);
        this.input.advance();
      } else {
        recoverableError("Expected a type followed by comma-seperated variable names");
      } 
      if (isSymbol(';')) {
        this.input.advance();
        bool = false;
        continue;
      } 
      if (!isSymbol(','))
        terminalError("Expected , or ;"); 
    } 
  }
  
  private void skipToEndOfStatement() {
    while (!isSymbol(';') && !isSymbol('}') && this.input.hasMoreTokens())
      this.input.advance(); 
    if (isSymbol(';') && this.input.hasMoreTokens())
      this.input.advance(); 
  }
  
  private boolean compileStatements(boolean paramBoolean) throws JackException {
    boolean bool = !paramBoolean ? true : false;
    while (!isSymbol('}')) {
      if (!paramBoolean && !bool) {
        warning("Unreachable code");
        bool = true;
      } 
      if (isKeywordDo()) {
        try {
          compileDo();
        } catch (JackException jackException) {
          skipToEndOfStatement();
        } 
        continue;
      } 
      if (isKeywordLet()) {
        try {
          compileLet();
        } catch (JackException jackException) {
          skipToEndOfStatement();
        } 
        continue;
      } 
      if (isKeywordWhile()) {
        paramBoolean = compileWhile(paramBoolean);
        continue;
      } 
      if (isKeywordReturn()) {
        try {
          compileReturn();
        } catch (JackException jackException) {
          skipToEndOfStatement();
        } 
        paramBoolean = false;
        continue;
      } 
      if (isKeywordIf()) {
        paramBoolean = compileIf(paramBoolean);
        continue;
      } 
      if (isSymbol(';')) {
        recoverableError("An empty statement is not allowed");
        this.input.advance();
        continue;
      } 
      String str = "Expected statement(do, let, while, return or if)";
      if (isIdentifier()) {
        recoverableError(str);
        skipToEndOfStatement();
        continue;
      } 
      terminalError(str);
    } 
    return paramBoolean;
  }
  
  private void compileDo() throws JackException {
    this.input.advance();
    compileSubroutineCall();
    this.output.pop("temp", (short)0);
    if (isSymbol(';')) {
      this.input.advance();
    } else {
      recoverableError("Expected ;");
    } 
  }
  
  private void compileSubroutineCall() throws JackException {
    if (!isIdentifier())
      terminalError("Expected class name, subroutine name, field, parameter or local or static variable name"); 
    String str = this.input.getIdentifier();
    int i = this.input.getLineNumber();
    this.input.advance();
    if (isSymbol('.')) {
      compileExternalSubroutineCall(str);
    } else {
      compileInternalSubroutineCall(str, i);
    } 
  }
  
  private void compileExternalSubroutineCall(String paramString) throws JackException {
    String str1 = null;
    String str2 = null;
    boolean bool = true;
    this.input.advance();
    int i = this.input.getLineNumber();
    try {
      str1 = this.identifiers.getTypeOf(paramString);
      int j = this.identifiers.getKindOf(paramString);
      short s1 = this.identifiers.getIndexOf(paramString);
      pushVariable(j, s1);
    } catch (JackException jackException) {
      str1 = paramString;
      bool = false;
    } 
    if (isIdentifier()) {
      str2 = str1 + "." + this.input.getIdentifier();
      this.input.advance();
    } else {
      terminalError("Expected subroutine name");
    } 
    short s = compileExpressionList();
    this.output.callFunction(str2, (short)(s + (bool ? 1 : 0)));
    Object[] arrayOfObject = { str2, new Boolean(bool), new Short(s), this.fileName, this.identifiers.getSubroutineName(), new Integer(i) };
    this.subroutineCalls.addElement(arrayOfObject);
  }
  
  private void compileInternalSubroutineCall(String paramString, int paramInt) throws JackException {
    String str = null;
    str = this.identifiers.getClassName() + "." + paramString;
    this.output.push("pointer", (short)0);
    short s = compileExpressionList();
    this.output.callFunction(str, (short)(s + 1));
    if (this.subroutineType == 2) {
      recoverableError("Subroutine " + str + " called as a method from within a function", paramInt);
    } else {
      Object[] arrayOfObject = { str, Boolean.TRUE, new Short(s), this.fileName, this.identifiers.getSubroutineName(), new Integer(paramInt) };
      this.subroutineCalls.addElement(arrayOfObject);
    } 
  }
  
  private void skipFromParensToBlockStart() {
    while (!isSymbol(';') && !isSymbol('}') && !isSymbol('{') && this.input.hasMoreTokens())
      this.input.advance(); 
  }
  
  private boolean compileWhile(boolean paramBoolean) throws JackException {
    int i = this.whileCounter;
    this.whileCounter++;
    this.input.advance();
    if (isSymbol('(')) {
      this.input.advance();
    } else {
      recoverableError("Expected (");
    } 
    try {
      this.output.label("WHILE_EXP" + i);
      compileNewExpression(1);
      this.output.not();
      if (isSymbol(')')) {
        this.input.advance();
      } else {
        recoverableError("Expected )");
      } 
    } catch (JackException jackException) {
      skipFromParensToBlockStart();
      if (!isSymbol('{'))
        throw jackException; 
    } 
    if (isSymbol('{')) {
      this.input.advance();
    } else {
      recoverableError("Expected {");
    } 
    this.output.ifGoTo("WHILE_END" + i);
    paramBoolean = compileStatements(paramBoolean);
    if (isSymbol('}')) {
      this.input.advance();
    } else {
      recoverableError("Expected }");
    } 
    this.output.goTo("WHILE_EXP" + i);
    this.output.label("WHILE_END" + i);
    return paramBoolean;
  }
  
  private boolean compileIf(boolean paramBoolean) throws JackException {
    int i = this.ifCounter;
    this.ifCounter++;
    this.input.advance();
    if (isSymbol('(')) {
      this.input.advance();
    } else {
      recoverableError("Expected (");
    } 
    try {
      compileNewExpression(1);
      if (isSymbol(')')) {
        this.input.advance();
      } else {
        recoverableError("Expected )");
      } 
    } catch (JackException jackException) {
      skipFromParensToBlockStart();
      if (!isSymbol('{'))
        throw jackException; 
    } 
    if (isSymbol('{')) {
      this.input.advance();
    } else {
      recoverableError("Expected {");
    } 
    this.output.ifGoTo("IF_TRUE" + i);
    this.output.goTo("IF_FALSE" + i);
    this.output.label("IF_TRUE" + i);
    boolean bool1 = compileStatements(paramBoolean);
    if (isSymbol('}')) {
      this.input.advance();
    } else {
      recoverableError("Expected }");
    } 
    if (isKeywordElse()) {
      this.input.advance();
    } else {
      this.output.label("IF_FALSE" + i);
      return true;
    } 
    if (isSymbol('{')) {
      this.input.advance();
    } else {
      recoverableError("Expected {");
    } 
    this.output.goTo("IF_END" + i);
    this.output.label("IF_FALSE" + i);
    boolean bool2 = compileStatements(paramBoolean);
    if (isSymbol('}')) {
      this.input.advance();
    } else {
      recoverableError("Expected }");
    } 
    this.output.label("IF_END" + i);
    return (bool1 || bool2);
  }
  
  private void compileReturn() throws JackException {
    this.input.advance();
    if (this.subroutineType == 3 && !isKeywordThis())
      recoverableError("A constructor must return 'this'"); 
    if (isSymbol(';')) {
      if (!this.identifiers.getReturnType().equals("void"))
        recoverableError("A non-void function must return a value"); 
      this.output.push("constant", (short)0);
      this.output.returnFromFunction();
      this.input.advance();
    } else {
      if (this.identifiers.getReturnType().equals("void"))
        recoverableError("A void function must not return a value"); 
      compileNewExpression(1);
      this.output.returnFromFunction();
      if (isSymbol(';')) {
        this.input.advance();
      } else {
        recoverableError("Expected ;");
      } 
    } 
  }
  
  private void compileLet() throws JackException {
    boolean bool1;
    boolean bool2;
    String str2;
    this.input.advance();
    if (!isIdentifier())
      terminalError("Expected field, parameter or local or static variable name"); 
    String str1 = this.input.getIdentifier();
    try {
      bool1 = this.identifiers.getKindOf(str1);
      bool2 = this.identifiers.getIndexOf(str1);
      str2 = this.identifiers.getTypeOf(str1);
    } catch (JackException jackException) {
      recoverableError(str1 + " is not defined as a field, parameter or local or static variable");
      bool1 = false;
      bool2 = false;
      str2 = "int";
    } 
    this.input.advance();
    if (isSymbol('=')) {
      this.input.advance();
      int i = compileNewExpression(1);
      popVariable(bool1, bool2);
      if (i != 1)
        if (str2.equals("int") && i != 3 && i != 2) {
          recoverableError("an int value is expected", this.input.getLineNumber() - 1);
        } else if (str2.equals("char") && i != 4 && i != 2) {
          recoverableError("a char value is expected", this.input.getLineNumber() - 1);
        } else if (str2.equals("boolean") && i != 2 && i != 3 && i != 5) {
          recoverableError("a boolean value is expected", this.input.getLineNumber() - 1);
        }  
    } else if (isSymbol('[')) {
      this.input.advance();
      compileNewExpression(2);
      if (isSymbol(']')) {
        this.input.advance();
      } else {
        terminalError("Expected ]");
      } 
      pushVariable(bool1, bool2);
      this.output.add();
      if (isSymbol('=')) {
        this.input.advance();
      } else {
        terminalError("Expected =");
      } 
      compileNewExpression(1);
      this.output.pop("temp", (short)0);
      this.output.pop("pointer", (short)1);
      this.output.push("temp", (short)0);
      this.output.pop("that", (short)0);
    } else {
      terminalError("Expected [ or =");
    } 
    if (isSymbol(';')) {
      this.input.advance();
    } else {
      recoverableError("Expected ;");
    } 
  }
  
  private short compileExpressionList() throws JackException {
    if (isSymbol('(')) {
      this.input.advance();
    } else {
      terminalError("Expected (");
    } 
    short s = 0;
    if (isSymbol(')')) {
      this.input.advance();
    } else {
      boolean bool = true;
      while (bool) {
        compileNewExpression(1);
        s = (short)(s + 1);
        if (isSymbol(',')) {
          this.input.advance();
          continue;
        } 
        if (isSymbol(')')) {
          bool = false;
          this.input.advance();
          continue;
        } 
        terminalError("Expected , or ) in expression list");
      } 
    } 
    return s;
  }
  
  private int compileNewExpression(int paramInt) throws JackException {
    this.expIndex++;
    setExpType(paramInt);
    compileExpression();
    this.expIndex--;
    return this.expTypes[this.expIndex + 1];
  }
  
  private void compileExpression() throws JackException {
    boolean bool = false;
    compileTerm();
    do {
      if (this.input.getTokenType() != 2)
        continue; 
      char c = this.input.getSymbol();
      bool = (c == '+' || c == '-' || c == '*' || c == '/' || c == '&' || c == '|' || c == '>' || c == '<' || c == '=') ? true : false;
      if (!bool)
        continue; 
      this.input.advance();
      compileTerm();
      switch (c) {
        case '+':
          this.output.add();
          break;
        case '-':
          this.output.substract();
          break;
        case '*':
          this.output.callFunction("Math.multiply", (short)2);
          break;
        case '/':
          this.output.callFunction("Math.divide", (short)2);
          break;
        case '&':
          this.output.and();
          break;
        case '|':
          this.output.or();
          break;
        case '>':
          this.output.greaterThan();
          break;
        case '<':
          this.output.lessThan();
          break;
        case '=':
          this.output.equal();
          break;
      } 
    } while (bool);
  }
  
  private void compileTerm() throws JackException {
    switch (this.input.getTokenType()) {
      case 4:
        compileIntConst();
        return;
      case 5:
        compileStringConst();
        return;
      case 1:
        compileKeywordConst();
        return;
      case 3:
        compileIdentifierTerm();
        return;
    } 
    if (isSymbol('-')) {
      this.input.advance();
      compileTerm();
      this.output.negate();
    } else if (isSymbol('~')) {
      this.input.advance();
      compileTerm();
      this.output.not();
    } else if (isSymbol('(')) {
      this.input.advance();
      compileNewExpression(1);
      if (isSymbol(')')) {
        this.input.advance();
      } else {
        terminalError("Expected )");
      } 
    } else {
      terminalError("Expected - or ~ or ( in term");
    } 
  }
  
  private void compileIntConst() throws JackException {
    if (this.input.getIntValue() > 32767)
      recoverableError("Integer constant too big"); 
    short s = (short)this.input.getIntValue();
    this.output.push("constant", s);
    if (getExpType() < 2) {
      setExpType(2);
    } else if (getExpType() > 4) {
      recoverableError("a numeric value is illegal here");
    } 
    this.input.advance();
  }
  
  private void compileStringConst() throws JackException {
    if (getExpType() == 1) {
      setExpType(6);
    } else {
      recoverableError("A string constant is illegal here");
    } 
    String str = this.input.getStringValue();
    short s = (short)str.length();
    this.output.push("constant", s);
    this.output.callFunction("String.new", (short)1);
    for (short s1 = 0; s1 < s; s1 = (short)(s1 + 1)) {
      this.output.push("constant", (short)str.charAt(s1));
      this.output.callFunction("String.appendChar", (short)2);
    } 
    this.input.advance();
  }
  
  private void compileKeywordConst() throws JackException {
    int i = this.input.getKeywordType();
    switch (i) {
      case 18:
        this.output.push("constant", (short)0);
        this.output.not();
        break;
      case 19:
      case 20:
        this.output.push("constant", (short)0);
        break;
      case 21:
        if (this.subroutineType == 2)
          recoverableError("'this' can't be referenced in a function"); 
        this.output.push("pointer", (short)0);
        break;
      default:
        terminalError("Illegal keyword in term");
        break;
    } 
    switch (i) {
      case 18:
      case 19:
        if (getExpType() <= 2) {
          setExpType(5);
          break;
        } 
        recoverableError("A boolean value is illegal here");
        break;
      case 20:
        if (getExpType() == 1) {
          setExpType(8);
          break;
        } 
        recoverableError("'null' is illegal here");
        break;
      case 21:
        if (getExpType() == 1) {
          setExpType(7);
          break;
        } 
        recoverableError("'this' is illegal here");
        break;
    } 
    this.input.advance();
  }
  
  private void compileIdentifierTerm() throws JackException {
    if (getExpType() == 6)
      recoverableError("Illegal casting into String constant"); 
    String str = this.input.getIdentifier();
    int i = this.input.getLineNumber();
    this.input.advance();
    if (isSymbol('[')) {
      this.input.advance();
      compileNewExpression(2);
      try {
        pushVariable(this.identifiers.getKindOf(str), this.identifiers.getIndexOf(str));
      } catch (JackException jackException) {
        recoverableError(str + " is not defined as a field, parameter or local or static variable", i);
      } 
      this.output.add();
      this.output.pop("pointer", (short)1);
      this.output.push("that", (short)0);
      if (isSymbol(']')) {
        this.input.advance();
      } else {
        terminalError("Expected ]");
      } 
    } else if (isSymbol('(')) {
      compileInternalSubroutineCall(str, i);
    } else if (isSymbol('.')) {
      compileExternalSubroutineCall(str);
    } else {
      try {
        int j = this.identifiers.getKindOf(str);
        short s = this.identifiers.getIndexOf(str);
        if (this.subroutineType == 2 && j == 1)
          recoverableError("A field may not be referenced in a function", i); 
        pushVariable(j, s);
        String str1 = this.identifiers.getTypeOf(str);
        if (str1.equals("int")) {
          if (getExpType() <= 2) {
            setExpType(3);
          } else if (getExpType() > 3) {
            recoverableError("An int value is illegal here", i);
          } 
        } else if (str1.equals("char")) {
          if (getExpType() <= 2) {
            setExpType(4);
          } else if (getExpType() > 4 || getExpType() == 3) {
            recoverableError("A char value is illegal here", i);
          } 
        } else if (str1.equals("boolean")) {
          if (getExpType() <= 2) {
            setExpType(5);
          } else if (getExpType() != 5) {
            recoverableError("A boolean value is illegal here", i);
          } 
        } 
      } catch (JackException jackException) {
        recoverableError(str + " is not defined as a field, parameter or local or static variable", i);
      } 
    } 
  }
  
  private int getExpType() {
    return this.expTypes[this.expIndex];
  }
  
  private int setExpType(int paramInt) {
    this.expTypes[this.expIndex] = paramInt;
    return paramInt;
  }
  
  private String getType() throws JackException {
    String str = null;
    if (this.input.getTokenType() == 1) {
      switch (this.input.getKeywordType()) {
        case 5:
          return "int";
        case 6:
          return "boolean";
        case 7:
          return "char";
      } 
      terminalError("Expected primitive type or class name");
    } else if (isIdentifier()) {
      str = this.input.getIdentifier();
    } else {
      terminalError("Expected primitive type or class name");
    } 
    return str;
  }
  
  private void pushVariable(int paramInt, short paramShort) throws JackException {
    switch (paramInt) {
      case 2:
        this.output.push("argument", paramShort);
        return;
      case 3:
        this.output.push("local", paramShort);
        return;
      case 1:
        this.output.push("this", paramShort);
        return;
      case 0:
        this.output.push("static", paramShort);
        return;
    } 
    terminalError("Internal Error: Illegal kind");
  }
  
  private void popVariable(int paramInt, short paramShort) throws JackException {
    switch (paramInt) {
      case 3:
        this.output.pop("local", paramShort);
        break;
      case 2:
        this.output.pop("argument", paramShort);
        break;
      case 1:
        this.output.pop("this", paramShort);
        break;
      case 0:
        this.output.pop("static", paramShort);
        break;
    } 
  }
  
  private boolean isKeywordClass() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 1);
  }
  
  private boolean isKeywordStatic() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 10);
  }
  
  private boolean isKeywordField() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 11);
  }
  
  private boolean isKeywordLocal() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 9);
  }
  
  private boolean isIdentifier() {
    return (this.input.getTokenType() == 3);
  }
  
  private boolean isSymbol(char paramChar) {
    return (this.input.getTokenType() == 2 && this.input.getSymbol() == paramChar);
  }
  
  private boolean isKeywordMethod() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 2);
  }
  
  private boolean isKeywordFunction() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 3);
  }
  
  private boolean isKeywordConstructor() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 4);
  }
  
  private boolean isKeywordVoid() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 8);
  }
  
  private boolean isKeywordDo() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 13);
  }
  
  private boolean isKeywordLet() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 12);
  }
  
  private boolean isKeywordWhile() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 16);
  }
  
  private boolean isKeywordReturn() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 17);
  }
  
  private boolean isKeywordIf() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 14);
  }
  
  private boolean isKeywordElse() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 15);
  }
  
  private boolean isKeywordThis() {
    return (this.input.getTokenType() == 1 && this.input.getKeywordType() == 21);
  }
  
  private void terminalError(String paramString) throws JackException {
    terminalError(paramString, -1, null, null);
  }
  
  private void terminalError(String paramString, int paramInt) throws JackException {
    terminalError(paramString, paramInt, null, null);
  }
  
  private void terminalError(String paramString1, int paramInt, String paramString2, String paramString3) throws JackException {
    recoverableError(paramString1, paramInt, paramString2, paramString3);
    throw new JackException(generateMessage(paramString1, paramInt, paramString2, paramString3));
  }
  
  private void warning(String paramString) {
    warning(paramString, -1, null, null);
  }
  
  private void warning(String paramString, int paramInt) {
    warning(paramString, paramInt, null, null);
  }
  
  private void warning(String paramString1, int paramInt, String paramString2, String paramString3) {
    System.err.println(generateMessage("Warning: " + paramString1, paramInt, paramString2, paramString3));
  }
  
  private void recoverableError(String paramString) {
    recoverableError(paramString, -1, null, null);
  }
  
  private void recoverableError(String paramString, int paramInt) {
    recoverableError(paramString, paramInt, null, null);
  }
  
  private void recoverableError(String paramString1, int paramInt, String paramString2, String paramString3) {
    System.err.println(generateMessage(paramString1, paramInt, paramString2, paramString3));
    this.validJack = false;
  }
  
  private String generateMessage(String paramString1, int paramInt, String paramString2, String paramString3) {
    if (paramString3 == null)
      paramString3 = this.fileName; 
    if (paramString2 == null)
      paramString2 = this.identifiers.getSubroutineName(); 
    if (paramInt == -1)
      paramInt = this.input.getLineNumber(); 
    return "In " + paramString3 + " (line " + paramInt + "): " + ("".equals(paramString2) ? "" : ("In subroutine" + ((paramString2 == null) ? "" : (" " + paramString2)) + ": ")) + paramString1;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Compiler/CompilationEngine.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */