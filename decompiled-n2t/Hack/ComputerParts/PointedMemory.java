package Hack.ComputerParts;

public class PointedMemory extends Memory {
  public PointedMemory(int paramInt, PointedMemoryGUI paramPointedMemoryGUI) {
    super(paramInt, paramPointedMemoryGUI);
  }
  
  public PointedMemory(int paramInt, PointedMemoryGUI paramPointedMemoryGUI, short paramShort1, short paramShort2) {
    super(paramInt, paramPointedMemoryGUI, paramShort1, paramShort2);
  }
  
  public void setPointerAddress(int paramInt) {
    if (this.displayChanges)
      ((PointedMemoryGUI)this.gui).setPointer(paramInt); 
  }
  
  public void reset() {
    setPointerAddress(0);
    super.reset();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/PointedMemory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */