package Hack.Translators;

import Hack.ComputerParts.TextFileGUI;
import java.io.File;

public interface HackTranslatorGUI {
  void addHackTranslatorListener(HackTranslatorEventListener paramHackTranslatorEventListener);
  
  void removeHackTranslatorListener(HackTranslatorEventListener paramHackTranslatorEventListener);
  
  void notifyHackTranslatorListeners(byte paramByte, Object paramObject);
  
  void displayMessage(String paramString, boolean paramBoolean);
  
  void setTitle(String paramString);
  
  TextFileGUI getSource();
  
  TextFileGUI getDestination();
  
  void setSourceName(String paramString);
  
  void setDestinationName(String paramString);
  
  void setWorkingDir(File paramFile);
  
  void setUsageFileName(String paramString);
  
  void setAboutFileName(String paramString);
  
  void enableSingleStep();
  
  void disableSingleStep();
  
  void enableFastForward();
  
  void disableFastForward();
  
  void enableStop();
  
  void disableStop();
  
  void enableRewind();
  
  void disableRewind();
  
  void enableFullCompilation();
  
  void disableFullCompilation();
  
  void enableSave();
  
  void disableSave();
  
  void enableLoadSource();
  
  void disableLoadSource();
  
  void enableSourceRowSelection();
  
  void disableSourceRowSelection();
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Translators/HackTranslatorGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */