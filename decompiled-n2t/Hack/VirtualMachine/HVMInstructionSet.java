package Hack.VirtualMachine;

import java.util.Hashtable;

public class HVMInstructionSet {
  public static final byte ADD_CODE = 1;
  
  public static final byte SUBSTRACT_CODE = 2;
  
  public static final byte NEGATE_CODE = 3;
  
  public static final byte EQUAL_CODE = 4;
  
  public static final byte GREATER_THAN_CODE = 5;
  
  public static final byte LESS_THAN_CODE = 6;
  
  public static final byte AND_CODE = 7;
  
  public static final byte OR_CODE = 8;
  
  public static final byte NOT_CODE = 9;
  
  public static final byte PUSH_CODE = 10;
  
  public static final byte POP_CODE = 11;
  
  public static final byte LABEL_CODE = 12;
  
  public static final byte GOTO_CODE = 13;
  
  public static final byte IF_GOTO_CODE = 14;
  
  public static final byte FUNCTION_CODE = 15;
  
  public static final byte RETURN_CODE = 16;
  
  public static final byte CALL_CODE = 17;
  
  public static final byte UNKNOWN_INSTRUCTION = -99;
  
  public static final String ADD_STRING = "add";
  
  public static final String SUBSTRACT_STRING = "sub";
  
  public static final String NEGATE_STRING = "neg";
  
  public static final String EQUAL_STRING = "eq";
  
  public static final String GREATER_THAN_STRING = "gt";
  
  public static final String LESS_THAN_STRING = "lt";
  
  public static final String AND_STRING = "and";
  
  public static final String OR_STRING = "or";
  
  public static final String NOT_STRING = "not";
  
  public static final String PUSH_STRING = "push";
  
  public static final String POP_STRING = "pop";
  
  public static final String LABEL_STRING = "label";
  
  public static final String GOTO_STRING = "goto";
  
  public static final String IF_GOTO_STRING = "if-goto";
  
  public static final String FUNCTION_STRING = "function";
  
  public static final String RETURN_STRING = "return";
  
  public static final String CALL_STRING = "call";
  
  public static final int NUMBER_OF_ACTUAL_SEGMENTS = 5;
  
  public static final byte LOCAL_SEGMENT_CODE = 0;
  
  public static final byte ARG_SEGMENT_CODE = 1;
  
  public static final byte THIS_SEGMENT_CODE = 2;
  
  public static final byte THAT_SEGMENT_CODE = 3;
  
  public static final byte TEMP_SEGMENT_CODE = 4;
  
  public static final byte STATIC_SEGMENT_CODE = 100;
  
  public static final byte CONST_SEGMENT_CODE = 101;
  
  public static final byte POINTER_SEGMENT_CODE = 102;
  
  public static final byte UNKNOWN_SEGMENT = -1;
  
  public static final String STATIC_SEGMENT_VM_STRING = "static";
  
  public static final String LOCAL_SEGMENT_VM_STRING = "local";
  
  public static final String ARG_SEGMENT_VM_STRING = "argument";
  
  public static final String THIS_SEGMENT_VM_STRING = "this";
  
  public static final String THAT_SEGMENT_VM_STRING = "that";
  
  public static final String TEMP_SEGMENT_VM_STRING = "temp";
  
  public static final String CONST_SEGMENT_VM_STRING = "constant";
  
  public static final String POINTER_SEGMENT_VM_STRING = "pointer";
  
  private static HVMInstructionSet instance;
  
  private Hashtable instructionToCode;
  
  private Hashtable instructionToString;
  
  private Hashtable segmentCodes;
  
  private Hashtable segmentStrings;
  
  private Hashtable segmentPointerStrings;
  
  private HVMInstructionSet() {
    instance = this;
    initInstructions();
    initSegmentStrings();
    initSegmentCodes();
  }
  
  public static HVMInstructionSet getInstance() {
    if (instance == null)
      new HVMInstructionSet(); 
    return instance;
  }
  
  private void initInstructions() {
    this.instructionToCode = new Hashtable();
    this.instructionToCode.put("add", new Byte((byte)1));
    this.instructionToCode.put("sub", new Byte((byte)2));
    this.instructionToCode.put("neg", new Byte((byte)3));
    this.instructionToCode.put("eq", new Byte((byte)4));
    this.instructionToCode.put("gt", new Byte((byte)5));
    this.instructionToCode.put("lt", new Byte((byte)6));
    this.instructionToCode.put("and", new Byte((byte)7));
    this.instructionToCode.put("or", new Byte((byte)8));
    this.instructionToCode.put("not", new Byte((byte)9));
    this.instructionToCode.put("push", new Byte((byte)10));
    this.instructionToCode.put("pop", new Byte((byte)11));
    this.instructionToCode.put("label", new Byte((byte)12));
    this.instructionToCode.put("goto", new Byte((byte)13));
    this.instructionToCode.put("if-goto", new Byte((byte)14));
    this.instructionToCode.put("function", new Byte((byte)15));
    this.instructionToCode.put("return", new Byte((byte)16));
    this.instructionToCode.put("call", new Byte((byte)17));
    this.instructionToString = new Hashtable();
    this.instructionToString.put(new Byte((byte)1), "add");
    this.instructionToString.put(new Byte((byte)2), "sub");
    this.instructionToString.put(new Byte((byte)3), "neg");
    this.instructionToString.put(new Byte((byte)4), "eq");
    this.instructionToString.put(new Byte((byte)5), "gt");
    this.instructionToString.put(new Byte((byte)6), "lt");
    this.instructionToString.put(new Byte((byte)7), "and");
    this.instructionToString.put(new Byte((byte)8), "or");
    this.instructionToString.put(new Byte((byte)9), "not");
    this.instructionToString.put(new Byte((byte)10), "push");
    this.instructionToString.put(new Byte((byte)11), "pop");
    this.instructionToString.put(new Byte((byte)12), "label");
    this.instructionToString.put(new Byte((byte)13), "goto");
    this.instructionToString.put(new Byte((byte)14), "if-goto");
    this.instructionToString.put(new Byte((byte)15), "function");
    this.instructionToString.put(new Byte((byte)16), "return");
    this.instructionToString.put(new Byte((byte)17), "call");
  }
  
  private void initSegmentStrings() {
    this.segmentPointerStrings = new Hashtable();
    this.segmentPointerStrings.put("local", "LCL");
    this.segmentPointerStrings.put("argument", "ARG");
    this.segmentPointerStrings.put("this", "THIS");
    this.segmentPointerStrings.put("that", "THAT");
    this.segmentStrings = new Hashtable();
    this.segmentStrings.put(new Byte((byte)100), "static");
    this.segmentStrings.put(new Byte((byte)0), "local");
    this.segmentStrings.put(new Byte((byte)1), "argument");
    this.segmentStrings.put(new Byte((byte)2), "this");
    this.segmentStrings.put(new Byte((byte)3), "that");
    this.segmentStrings.put(new Byte((byte)4), "temp");
    this.segmentStrings.put(new Byte((byte)101), "constant");
    this.segmentStrings.put(new Byte((byte)102), "pointer");
  }
  
  private void initSegmentCodes() {
    this.segmentCodes = new Hashtable();
    this.segmentCodes.put("static", new Byte((byte)100));
    this.segmentCodes.put("local", new Byte((byte)0));
    this.segmentCodes.put("argument", new Byte((byte)1));
    this.segmentCodes.put("this", new Byte((byte)2));
    this.segmentCodes.put("that", new Byte((byte)3));
    this.segmentCodes.put("temp", new Byte((byte)4));
    this.segmentCodes.put("constant", new Byte((byte)101));
    this.segmentCodes.put("pointer", new Byte((byte)102));
  }
  
  public byte instructionStringToCode(String paramString) {
    Byte byte_ = (Byte)this.instructionToCode.get(paramString);
    return (byte_ != null) ? byte_.byteValue() : -99;
  }
  
  public String instructionCodeToString(byte paramByte) {
    return (String)this.instructionToString.get(new Byte(paramByte));
  }
  
  public boolean isLegalVMSegment(String paramString) {
    return (this.segmentCodes.get(paramString) != null);
  }
  
  public byte segmentVMStringToCode(String paramString) {
    Byte byte_ = (Byte)this.segmentCodes.get(paramString);
    return (byte_ != null) ? byte_.byteValue() : -1;
  }
  
  public String segmentStringVMToPointer(String paramString) {
    return (String)this.segmentPointerStrings.get(paramString);
  }
  
  public String segmentCodeToVMString(byte paramByte) {
    return (String)this.segmentStrings.get(new Byte(paramByte));
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/VirtualMachine/HVMInstructionSet.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */