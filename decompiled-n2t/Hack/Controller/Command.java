package Hack.Controller;

public class Command {
  public static final byte SIMULATOR_COMMAND = 1;
  
  public static final byte OUTPUT_FILE_COMMAND = 2;
  
  public static final byte COMPARE_TO_COMMAND = 3;
  
  public static final byte OUTPUT_LIST_COMMAND = 4;
  
  public static final byte OUTPUT_COMMAND = 5;
  
  public static final byte BREAKPOINT_COMMAND = 6;
  
  public static final byte CLEAR_BREAKPOINTS_COMMAND = 7;
  
  public static final byte REPEAT_COMMAND = 8;
  
  public static final byte END_REPEAT_COMMAND = 9;
  
  public static final byte WHILE_COMMAND = 10;
  
  public static final byte END_WHILE_COMMAND = 11;
  
  public static final byte END_SCRIPT_COMMAND = 12;
  
  public static final byte ECHO_COMMAND = 13;
  
  public static final byte CLEAR_ECHO_COMMAND = 14;
  
  public static final byte NO_TERMINATOR = 0;
  
  public static final byte MINI_STEP_TERMINATOR = 1;
  
  public static final byte SINGLE_STEP_TERMINATOR = 2;
  
  public static final byte STOP_TERMINATOR = 3;
  
  private byte code;
  
  private Object arg;
  
  private byte terminatorType;
  
  public Command(byte paramByte, Object paramObject) {
    this.code = paramByte;
    this.arg = paramObject;
    this.terminatorType = 0;
  }
  
  public Command(byte paramByte) {
    this.code = paramByte;
    this.terminatorType = 0;
  }
  
  public byte getCode() {
    return this.code;
  }
  
  public Object getArg() {
    return this.arg;
  }
  
  public void setTerminator(byte paramByte) {
    this.terminatorType = paramByte;
  }
  
  public byte getTerminator() {
    return this.terminatorType;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/Command.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */