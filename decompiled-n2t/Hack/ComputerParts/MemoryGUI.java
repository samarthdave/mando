package Hack.ComputerParts;

import Hack.Events.ClearEventListener;

public interface MemoryGUI extends InteractiveValueComputerPartGUI {
  void addClearListener(ClearEventListener paramClearEventListener);
  
  void removeClearListener(ClearEventListener paramClearEventListener);
  
  void notifyClearListeners();
  
  void setContents(short[] paramArrayOfshort);
  
  void select(int paramInt1, int paramInt2);
  
  void hideSelect();
  
  void scrollTo(int paramInt);
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/MemoryGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */