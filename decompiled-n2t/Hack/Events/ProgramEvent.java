package Hack.Events;

import java.util.EventObject;

public class ProgramEvent extends EventObject {
  public static final byte LOAD = 1;
  
  public static final byte SAVE = 2;
  
  public static final byte CLEAR = 3;
  
  private String programFileName;
  
  private byte eventType;
  
  public ProgramEvent(Object paramObject, byte paramByte, String paramString) {
    super(paramObject);
    this.programFileName = paramString;
    this.eventType = paramByte;
  }
  
  public String getProgramFileName() {
    return this.programFileName;
  }
  
  public byte getType() {
    return this.eventType;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Events/ProgramEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */