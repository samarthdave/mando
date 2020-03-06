package Hack.Translators;

import java.util.EventObject;

public class HackTranslatorEvent extends EventObject {
  public static final byte SINGLE_STEP = 1;
  
  public static final byte FAST_FORWARD = 2;
  
  public static final byte STOP = 3;
  
  public static final byte REWIND = 4;
  
  public static final byte FULL_COMPILATION = 5;
  
  public static final byte SAVE_DEST = 6;
  
  public static final byte SOURCE_LOAD = 7;
  
  private byte action;
  
  private Object data;
  
  public HackTranslatorEvent(Object paramObject1, byte paramByte, Object paramObject2) {
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


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Translators/HackTranslatorEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */