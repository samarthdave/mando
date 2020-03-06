package Hack.Compiler;

import java.util.HashMap;

public class SymbolTable {
  public static final int KIND_STATIC = 0;
  
  public static final int KIND_FIELD = 1;
  
  public static final int KIND_PARAMETER = 2;
  
  public static final int KIND_LOCAL = 3;
  
  public static final int SUBROUTINE_TYPE_UNDEFINED = 0;
  
  public static final int SUBROUTINE_TYPE_METHOD = 1;
  
  public static final int SUBROUTINE_TYPE_FUNCTION = 2;
  
  public static final int SUBROUTINE_TYPE_CONSTRUCTOR = 3;
  
  private static short staticsNumbering;
  
  private static short fieldsNumbering;
  
  private static short parametersNumbering;
  
  private static short localsNumbering;
  
  private String className;
  
  private String subroutineName;
  
  private int subroutineType;
  
  private String returnType;
  
  private HashMap fields;
  
  private HashMap parameters;
  
  private HashMap locals;
  
  private HashMap statics;
  
  public SymbolTable(String paramString) {
    this.className = paramString;
    this.fields = new HashMap();
    this.statics = new HashMap();
    this.parameters = new HashMap();
    this.locals = new HashMap();
    fieldsNumbering = 0;
    staticsNumbering = 0;
    localsNumbering = 0;
    parametersNumbering = 1;
    this.subroutineType = 0;
  }
  
  public void startMethod(String paramString1, String paramString2) {
    startSubroutine(paramString1, 1, paramString2, (short)1);
  }
  
  public void startFunction(String paramString1, String paramString2) {
    startSubroutine(paramString1, 2, paramString2, (short)0);
  }
  
  public void startConstructor(String paramString) {
    startSubroutine(paramString, 3, this.className, (short)0);
  }
  
  public void endSubroutine() {
    this.parameters.clear();
    this.locals.clear();
    localsNumbering = 0;
    parametersNumbering = 1;
    this.subroutineType = 0;
    this.subroutineName = null;
    this.returnType = null;
  }
  
  private void startSubroutine(String paramString1, int paramInt, String paramString2, short paramShort) {
    endSubroutine();
    this.subroutineName = paramString1;
    this.subroutineType = paramInt;
    this.returnType = paramString2;
    parametersNumbering = paramShort;
  }
  
  public void define(String paramString1, String paramString2, int paramInt) {
    switch (paramInt) {
      case 0:
        this.statics.put(paramString1, new IdentifierProperties(paramString2, staticsNumbering));
        staticsNumbering = (short)(staticsNumbering + 1);
        break;
      case 1:
        this.fields.put(paramString1, new IdentifierProperties(paramString2, fieldsNumbering));
        fieldsNumbering = (short)(fieldsNumbering + 1);
        break;
      case 2:
        this.parameters.put(paramString1, new IdentifierProperties(paramString2, parametersNumbering));
        parametersNumbering = (short)(parametersNumbering + 1);
        break;
      case 3:
        this.locals.put(paramString1, new IdentifierProperties(paramString2, localsNumbering));
        localsNumbering = (short)(localsNumbering + 1);
        break;
    } 
  }
  
  public int getKindOf(String paramString) throws JackException {
    boolean bool;
    if (this.parameters.containsKey(paramString)) {
      bool = true;
    } else if (this.locals.containsKey(paramString)) {
      bool = true;
    } else if (this.subroutineType != 2 && this.fields.containsKey(paramString)) {
      bool = true;
    } else if (this.statics.containsKey(paramString)) {
      bool = false;
    } else {
      throw new JackException(paramString);
    } 
    return bool;
  }
  
  public String getTypeOf(String paramString) throws JackException {
    return getIdentifierProperties(paramString).getType();
  }
  
  public short getIndexOf(String paramString) throws JackException {
    return getIdentifierProperties(paramString).getIndex();
  }
  
  public short getNumberOfIdentifiers(int paramInt) {
    short s = -1;
    switch (paramInt) {
      case 0:
        s = staticsNumbering;
        break;
      case 1:
        s = fieldsNumbering;
        break;
      case 2:
        s = (short)(parametersNumbering - 1);
        break;
      case 3:
        s = localsNumbering;
        break;
    } 
    return s;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public String getSubroutineName() {
    return this.subroutineName;
  }
  
  public String getReturnType() {
    return this.returnType;
  }
  
  private IdentifierProperties getIdentifierProperties(String paramString) throws JackException {
    IdentifierProperties identifierProperties = null;
    switch (getKindOf(paramString)) {
      case 2:
        identifierProperties = (IdentifierProperties)this.parameters.get(paramString);
        break;
      case 3:
        identifierProperties = (IdentifierProperties)this.locals.get(paramString);
        break;
      case 1:
        identifierProperties = (IdentifierProperties)this.fields.get(paramString);
        break;
      case 0:
        identifierProperties = (IdentifierProperties)this.statics.get(paramString);
        break;
    } 
    return identifierProperties;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Compiler/SymbolTable.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */