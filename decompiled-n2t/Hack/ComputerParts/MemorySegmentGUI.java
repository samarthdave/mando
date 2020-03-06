package Hack.ComputerParts;

public interface MemorySegmentGUI extends InteractiveValueComputerPartGUI {
  void addListener(ComputerPartEventListener paramComputerPartEventListener);
  
  void removeListener(ComputerPartEventListener paramComputerPartEventListener);
  
  void notifyListeners(int paramInt, short paramShort);
  
  void setStartAddress(int paramInt);
  
  void hideSelect();
  
  void scrollTo(int paramInt);
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/MemorySegmentGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */