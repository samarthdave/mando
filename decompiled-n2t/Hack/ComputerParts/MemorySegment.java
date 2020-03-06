package Hack.ComputerParts;

public class MemorySegment extends InteractiveValueComputerPart {
  protected MemorySegmentGUI gui;
  
  protected Memory mainMemory;
  
  protected int startAddress;
  
  public MemorySegment(Memory paramMemory, MemorySegmentGUI paramMemorySegmentGUI) {
    super((paramMemorySegmentGUI != null));
    init(paramMemory, paramMemorySegmentGUI);
  }
  
  public MemorySegment(Memory paramMemory, MemorySegmentGUI paramMemorySegmentGUI, short paramShort1, short paramShort2) {
    super((paramMemorySegmentGUI != null), paramShort1, paramShort2);
    init(paramMemory, paramMemorySegmentGUI);
  }
  
  private void init(Memory paramMemory, MemorySegmentGUI paramMemorySegmentGUI) {
    this.mainMemory = paramMemory;
    this.gui = paramMemorySegmentGUI;
    if (this.hasGUI) {
      paramMemorySegmentGUI.addListener(this);
      paramMemorySegmentGUI.addErrorListener(this);
    } 
  }
  
  public void setStartAddress(int paramInt) {
    this.startAddress = paramInt;
    if (this.displayChanges)
      this.gui.setStartAddress(paramInt); 
  }
  
  public int getStartAddress() {
    return this.startAddress;
  }
  
  public void doSetValueAt(int paramInt, short paramShort) {
    if (this.mainMemory.getValueAt(this.startAddress + paramInt) != paramShort)
      this.mainMemory.setValueAt(this.startAddress + paramInt, paramShort, true); 
  }
  
  public short getValueAt(int paramInt) {
    return this.mainMemory.getValueAt(this.startAddress + paramInt);
  }
  
  public ComputerPartGUI getGUI() {
    return this.gui;
  }
  
  public void refreshGUI() {
    super.refreshGUI();
    if (this.displayChanges)
      this.gui.setStartAddress(this.startAddress); 
  }
  
  public void scrollTo(int paramInt) {
    if (this.displayChanges)
      this.gui.scrollTo(this.startAddress + paramInt); 
  }
  
  public void hideSelect() {
    if (this.displayChanges)
      this.gui.hideSelect(); 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/MemorySegment.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */