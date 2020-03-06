package Hack.ComputerParts;

public class AbsolutePointedMemorySegment extends PointedMemorySegment {
  public AbsolutePointedMemorySegment(Memory paramMemory, PointedMemorySegmentGUI paramPointedMemorySegmentGUI) {
    super(paramMemory, paramPointedMemorySegmentGUI);
  }
  
  public AbsolutePointedMemorySegment(Memory paramMemory, PointedMemorySegmentGUI paramPointedMemorySegmentGUI, short paramShort1, short paramShort2) {
    super(paramMemory, paramPointedMemorySegmentGUI, paramShort1, paramShort2);
  }
  
  public void setValueAt(int paramInt, short paramShort, boolean paramBoolean) {
    super.setValueAt(paramInt - this.startAddress, paramShort, paramBoolean);
  }
  
  public short getValueAt(int paramInt) {
    return this.mainMemory.getValueAt(paramInt);
  }
  
  public void valueChanged(ComputerPartEvent paramComputerPartEvent) {
    ComputerPartEvent computerPartEvent = new ComputerPartEvent((ComputerPartGUI)paramComputerPartEvent.getSource(), paramComputerPartEvent.getIndex() + this.startAddress, paramComputerPartEvent.getValue());
    super.valueChanged(computerPartEvent);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/AbsolutePointedMemorySegment.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */