package Hack.ComputerParts;

public interface TextFileGUI extends ComputerPartGUI {
  void addTextFileListener(TextFileEventListener paramTextFileEventListener);
  
  void removeTextFileListener(TextFileEventListener paramTextFileEventListener);
  
  void notifyTextFileListeners(String paramString, int paramInt);
  
  void setContents(String paramString);
  
  void setContents(String[] paramArrayOfString);
  
  void addLine(String paramString);
  
  void addHighlight(int paramInt, boolean paramBoolean);
  
  void clearHighlights();
  
  void addEmphasis(int paramInt);
  
  void removeEmphasis(int paramInt);
  
  String getLineAt(int paramInt);
  
  void setLineAt(int paramInt, String paramString);
  
  int getNumberOfLines();
  
  void select(int paramInt1, int paramInt2);
  
  void hideSelect();
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/TextFileGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */