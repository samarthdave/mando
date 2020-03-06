package Hack.ComputerParts;

public class PointedMemorySegment extends MemorySegment {
  public PointedMemorySegment(Memory paramMemory, PointedMemorySegmentGUI paramPointedMemorySegmentGUI) {
    super(paramMemory, paramPointedMemorySegmentGUI);
  }
  
  public PointedMemorySegment(Memory paramMemory, PointedMemorySegmentGUI paramPointedMemorySegmentGUI, short paramShort1, short paramShort2) {
    super(paramMemory, paramPointedMemorySegmentGUI, paramShort1, paramShort2);
  }
  
  public void setPointerAddress(int paramInt) {
    if (this.displayChanges)
      ((PointedMemorySegmentGUI)this.gui).setPointer(paramInt); 
  }
  
  public void reset() {
    super.reset();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/PointedMemorySegment.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */