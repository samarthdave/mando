package Hack.ComputerParts;

import Hack.Events.ErrorEvent;
import Hack.Events.ErrorEventListener;
import java.util.Vector;

public abstract class InteractiveComputerPart extends ComputerPart implements ErrorEventListener {
  private Vector errorListeners = new Vector();
  
  public InteractiveComputerPart(boolean paramBoolean) {
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
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/InteractiveComputerPart.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */