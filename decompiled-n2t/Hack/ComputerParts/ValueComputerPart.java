package Hack.ComputerParts;

public abstract class ValueComputerPart extends ComputerPart {
  private static final int FLASH_TIME = 500;
  
  protected short nullValue;
  
  public ValueComputerPart(boolean paramBoolean) {
    super(paramBoolean);
  }
  
  public void setValueAt(int paramInt, short paramShort, boolean paramBoolean) {
    doSetValueAt(paramInt, paramShort);
    if (this.displayChanges)
      if (paramBoolean) {
        quietUpdateGUI(paramInt, paramShort);
      } else {
        updateGUI(paramInt, paramShort);
      }  
  }
  
  public abstract void doSetValueAt(int paramInt, short paramShort);
  
  public abstract short getValueAt(int paramInt);
  
  public synchronized void updateGUI(int paramInt, short paramShort) {
    if (this.displayChanges) {
      ValueComputerPartGUI valueComputerPartGUI = (ValueComputerPartGUI)getGUI();
      valueComputerPartGUI.setValueAt(paramInt, paramShort);
      if (this.animate) {
        valueComputerPartGUI.flash(paramInt);
        try {
          wait(500L);
        } catch (InterruptedException interruptedException) {}
        valueComputerPartGUI.hideFlash();
      } 
      valueComputerPartGUI.highlight(paramInt);
    } 
  }
  
  public void quietUpdateGUI(int paramInt, short paramShort) {
    if (this.displayChanges)
      ((ValueComputerPartGUI)getGUI()).setValueAt(paramInt, paramShort); 
  }
  
  public void hideHighlight() {
    if (this.displayChanges)
      ((ValueComputerPartGUI)getGUI()).hideHighlight(); 
  }
  
  public void setNumericFormat(int paramInt) {
    if (this.displayChanges)
      ((ValueComputerPartGUI)getGUI()).setNumericFormat(paramInt); 
  }
  
  public void setNullValue(short paramShort, boolean paramBoolean) {
    this.nullValue = paramShort;
    if (this.hasGUI) {
      ValueComputerPartGUI valueComputerPartGUI = (ValueComputerPartGUI)getGUI();
      valueComputerPartGUI.setNullValue(paramShort, paramBoolean);
    } 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/ValueComputerPart.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */