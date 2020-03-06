package Hack.ComputerParts;

import java.util.EventObject;

public class ComputerPartEvent extends EventObject {
  private int index;
  
  private short value;
  
  public ComputerPartEvent(ComputerPartGUI paramComputerPartGUI) {
    super(paramComputerPartGUI);
  }
  
  public ComputerPartEvent(ComputerPartGUI paramComputerPartGUI, int paramInt, short paramShort) {
    super(paramComputerPartGUI);
    this.index = paramInt;
    this.value = paramShort;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public short getValue() {
    return this.value;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/ComputerPartEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */