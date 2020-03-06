package Hack.ComputerParts;

public interface InteractiveValueComputerPartGUI extends ValueComputerPartGUI, InteractiveComputerPartGUI {
  void addListener(ComputerPartEventListener paramComputerPartEventListener);
  
  void removeListener(ComputerPartEventListener paramComputerPartEventListener);
  
  void notifyListeners(int paramInt, short paramShort);
  
  void notifyListeners();
  
  void enableUserInput();
  
  void disableUserInput();
  
  void setEnabledRange(int paramInt1, int paramInt2, boolean paramBoolean);
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/InteractiveValueComputerPartGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */