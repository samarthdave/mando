package SimulatorsGUI;

import java.util.EventObject;

public class PinValueEvent extends EventObject {
  private String valueStr;
  
  private boolean isOk;
  
  public PinValueEvent(Object paramObject, String paramString, boolean paramBoolean) {
    super(paramObject);
    this.valueStr = paramString;
    this.isOk = paramBoolean;
  }
  
  public String getValueStr() {
    return this.valueStr;
  }
  
  public boolean getIsOk() {
    return this.isOk;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/PinValueEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */