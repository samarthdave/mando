package Hack.ComputerParts;

import Hack.Events.ErrorEvent;
import Hack.Events.ErrorEventListener;
import java.util.Vector;

public abstract class InteractiveValueComputerPart extends ValueComputerPart implements ComputerPartEventListener, ErrorEventListener {
  private Vector errorListeners = new Vector();
  
  private short minValue = Short.MIN_VALUE;
  
  private short maxValue = Short.MAX_VALUE;
  
  private int startEnabledRange;
  
  private int endEnabledRange;
  
  private boolean grayDisabledRange;
  
  public InteractiveValueComputerPart(boolean paramBoolean) {
    super(paramBoolean);
    this.startEnabledRange = -1;
    this.endEnabledRange = -1;
  }
  
  public InteractiveValueComputerPart(boolean paramBoolean, short paramShort1, short paramShort2) {
    super(paramBoolean);
  }
  
  public void addErrorListener(ComputerPartErrorEventListener paramComputerPartErrorEventListener) {
    this.errorListeners.addElement(paramComputerPartErrorEventListener);
  }
  
  public void removeErrorListener(ComputerPartErrorEventListener paramComputerPartErrorEventListener) {
    this.errorListeners.removeElement(paramComputerPartErrorEventListener);
  }
  
  public void notifyErrorListeners(String paramString) {
    ComputerPartErrorEvent computerPartErrorEvent = new ComputerPartErrorEvent(this, paramString);
    for (byte b = 0; b < this.errorListeners.size(); b++)
      ((ComputerPartErrorEventListener)this.errorListeners.elementAt(b)).computerPartErrorOccured(computerPartErrorEvent); 
  }
  
  public void clearErrorListeners() {
    ComputerPartErrorEvent computerPartErrorEvent = new ComputerPartErrorEvent(this, null);
    for (byte b = 0; b < this.errorListeners.size(); b++)
      ((ComputerPartErrorEventListener)this.errorListeners.elementAt(b)).computerPartErrorOccured(computerPartErrorEvent); 
  }
  
  public void errorOccured(ErrorEvent paramErrorEvent) {
    notifyErrorListeners(paramErrorEvent.getErrorMessage());
  }
  
  public void valueChanged(ComputerPartEvent paramComputerPartEvent) {
    short s = paramComputerPartEvent.getValue();
    int i = paramComputerPartEvent.getIndex();
    clearErrorListeners();
    if ((s < this.minValue || s > this.maxValue) && s != this.nullValue) {
      notifyErrorListeners("Value must be in the range " + this.minValue + ".." + this.maxValue);
      quietUpdateGUI(i, getValueAt(i));
    } else {
      setValueAt(i, s, true);
    } 
  }
  
  public void guiGainedFocus() {}
  
  public void enableUserInput() {
    if (this.hasGUI)
      ((InteractiveValueComputerPartGUI)getGUI()).enableUserInput(); 
  }
  
  public void disableUserInput() {
    if (this.hasGUI)
      ((InteractiveValueComputerPartGUI)getGUI()).disableUserInput(); 
  }
  
  public int[] getEnabledRange() {
    return new int[] { this.startEnabledRange, this.endEnabledRange };
  }
  
  public void setEnabledRange(int paramInt1, int paramInt2, boolean paramBoolean) {
    this.startEnabledRange = paramInt1;
    this.endEnabledRange = paramInt2;
    this.grayDisabledRange = paramBoolean;
    if (this.displayChanges)
      ((InteractiveValueComputerPartGUI)getGUI()).setEnabledRange(paramInt1, paramInt2, paramBoolean); 
  }
  
  public void refreshGUI() {
    if (this.displayChanges && this.startEnabledRange != -1 && this.endEnabledRange != -1)
      ((InteractiveValueComputerPartGUI)getGUI()).setEnabledRange(this.startEnabledRange, this.endEnabledRange, this.grayDisabledRange); 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/InteractiveValueComputerPart.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */