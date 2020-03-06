package Hack.Assembler;

import Hack.ComputerParts.TextFileGUI;
import Hack.Translators.HackTranslatorGUI;

public interface HackAssemblerGUI extends HackTranslatorGUI {
  TextFileGUI getComparison();
  
  void setComparisonName(String paramString);
  
  void enableLoadComparison();
  
  void disableLoadComparison();
  
  void showComparison();
  
  void hideComparison();
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Assembler/HackAssemblerGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */