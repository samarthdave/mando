package Hack.ComputerParts;

import Hack.Events.ClearEvent;
import Hack.Events.ClearEventListener;

public class Memory extends InteractiveValueComputerPart implements ClearEventListener {
  protected int size;
  
  protected short[] mem;
  
  protected MemoryGUI gui;
  
  public Memory(int paramInt, MemoryGUI paramMemoryGUI) {
    super((paramMemoryGUI != null));
    init(paramInt, paramMemoryGUI);
  }
  
  public Memory(int paramInt, MemoryGUI paramMemoryGUI, short paramShort1, short paramShort2) {
    super((paramMemoryGUI != null), paramShort1, paramShort2);
    init(paramInt, paramMemoryGUI);
  }
  
  private void init(int paramInt, MemoryGUI paramMemoryGUI) {
    this.size = paramInt;
    this.gui = paramMemoryGUI;
    this.mem = new short[paramInt];
    if (this.hasGUI) {
      paramMemoryGUI.setContents(this.mem);
      paramMemoryGUI.addListener(this);
      paramMemoryGUI.addClearListener(this);
      paramMemoryGUI.addErrorListener(this);
    } 
  }
  
  public short getValueAt(int paramInt) {
    return this.mem[paramInt];
  }
  
  public void doSetValueAt(int paramInt, short paramShort) {
    this.mem[paramInt] = paramShort;
  }
  
  public short[] getContents() {
    return this.mem;
  }
  
  public void setContents(short[] paramArrayOfshort, int paramInt) {
    System.arraycopy(paramArrayOfshort, 0, this.mem, paramInt, paramArrayOfshort.length);
    refreshGUI();
  }
  
  public int getSize() {
    return this.size;
  }
  
  public void reset() {
    super.reset();
    for (byte b = 0; b < this.size; b++)
      this.mem[b] = this.nullValue; 
  }
  
  public ComputerPartGUI getGUI() {
    return this.gui;
  }
  
  public void refreshGUI() {
    super.refreshGUI();
    if (this.displayChanges)
      this.gui.setContents(this.mem); 
  }
  
  public void scrollTo(int paramInt) {
    if (this.displayChanges)
      this.gui.scrollTo(paramInt); 
  }
  
  public void clearRequested(ClearEvent paramClearEvent) {
    reset();
  }
  
  public void select(int paramInt1, int paramInt2) {
    if (this.displayChanges)
      this.gui.select(paramInt1, paramInt2); 
  }
  
  public void hideSelect() {
    if (this.displayChanges)
      this.gui.hideSelect(); 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/Memory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */