package Hack.ComputerParts;

import Hack.Events.ErrorEventListener;

public interface InteractiveComputerPartGUI extends ComputerPartGUI {
  void addErrorListener(ErrorEventListener paramErrorEventListener);
  
  void removeErrorListener(ErrorEventListener paramErrorEventListener);
  
  void notifyErrorListeners(String paramString);
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/InteractiveComputerPartGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */