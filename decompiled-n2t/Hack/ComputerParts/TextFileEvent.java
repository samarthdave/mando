package Hack.ComputerParts;

import java.util.EventObject;

public class TextFileEvent extends EventObject {
  private int rowIndex;
  
  private String rowString;
  
  public TextFileEvent(Object paramObject, String paramString, int paramInt) {
    super(paramObject);
    this.rowString = paramString;
    this.rowIndex = paramInt;
  }
  
  public String getRowString() {
    return this.rowString;
  }
  
  public int getRowIndex() {
    return this.rowIndex;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/TextFileEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */