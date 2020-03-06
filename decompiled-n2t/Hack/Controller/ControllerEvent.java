package Hack.Controller;

import java.util.EventObject;

public class ControllerEvent extends EventObject {
  public static final byte SINGLE_STEP = 1;
  
  public static final byte FAST_FORWARD = 2;
  
  public static final byte SPEED_CHANGE = 3;
  
  public static final byte STOP = 4;
  
  public static final byte BREAKPOINTS_CHANGE = 5;
  
  public static final byte SCRIPT_CHANGE = 6;
  
  public static final byte REWIND = 9;
  
  public static final byte ANIMATION_MODE_CHANGE = 10;
  
  public static final byte NUMERIC_FORMAT_CHANGE = 11;
  
  public static final byte ADDITIONAL_DISPLAY_CHANGE = 12;
  
  public static final byte SHOW_CONTROLLER = 13;
  
  public static final byte HIDE_CONTROLLER = 14;
  
  public static final byte DISABLE_ANIMATION_MODE_CHANGE = 15;
  
  public static final byte ENABLE_ANIMATION_MODE_CHANGE = 16;
  
  public static final byte DISABLE_SINGLE_STEP = 17;
  
  public static final byte ENABLE_SINGLE_STEP = 18;
  
  public static final byte DISABLE_FAST_FORWARD = 19;
  
  public static final byte ENABLE_FAST_FORWARD = 20;
  
  public static final byte HALT_PROGRAM = 21;
  
  public static final byte CONTINUE_PROGRAM = 22;
  
  public static final byte DISABLE_MOVEMENT = 23;
  
  public static final byte ENABLE_MOVEMENT = 24;
  
  public static final byte DISPLAY_MESSAGE = 25;
  
  public static final byte DISPLAY_ERROR_MESSAGE = 26;
  
  public static final byte LOAD_PROGRAM = 27;
  
  private byte action;
  
  private Object data;
  
  public ControllerEvent(Object paramObject1, byte paramByte, Object paramObject2) {
    super(paramObject1);
    this.action = paramByte;
    this.data = paramObject2;
  }
  
  public byte getAction() {
    return this.action;
  }
  
  public Object getData() {
    return this.data;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/ControllerEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */