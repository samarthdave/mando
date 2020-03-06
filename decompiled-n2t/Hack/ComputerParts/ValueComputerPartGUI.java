package Hack.ComputerParts;

import java.awt.Point;

public interface ValueComputerPartGUI extends ComputerPartGUI {
  Point getCoordinates(int paramInt);
  
  void setValueAt(int paramInt, short paramShort);
  
  String getValueAsString(int paramInt);
  
  void highlight(int paramInt);
  
  void hideHighlight();
  
  void flash(int paramInt);
  
  void hideFlash();
  
  void setNumericFormat(int paramInt);
  
  void setNullValue(short paramShort, boolean paramBoolean);
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/ValueComputerPartGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */