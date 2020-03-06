package Hack.ComputerParts;

public class TrimmedAbsoluteMemorySegment extends AbsolutePointedMemorySegment {
  public TrimmedAbsoluteMemorySegment(Memory paramMemory, PointedMemorySegmentGUI paramPointedMemorySegmentGUI) {
    super(paramMemory, paramPointedMemorySegmentGUI);
  }
  
  public TrimmedAbsoluteMemorySegment(Memory paramMemory, PointedMemorySegmentGUI paramPointedMemorySegmentGUI, short paramShort1, short paramShort2) {
    super(paramMemory, paramPointedMemorySegmentGUI, paramShort1, paramShort2);
  }
  
  public void setValueAt(int paramInt, short paramShort, boolean paramBoolean) {
    if (this.displayChanges)
      ((PointedMemorySegmentGUI)this.gui).setPointer(paramInt + 1); 
    super.setValueAt(paramInt, paramShort, paramBoolean);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/TrimmedAbsoluteMemorySegment.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */