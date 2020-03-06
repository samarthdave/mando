package Hack.Controller;

import java.io.File;
import javax.swing.JComponent;

public interface HackSimulatorGUI {
  void setAdditionalDisplay(JComponent paramJComponent);
  
  void loadProgram();
  
  void setUsageFileName(String paramString);
  
  void setAboutFileName(String paramString);
  
  String getUsageFileName();
  
  String getAboutFileName();
  
  void setWorkingDir(File paramFile);
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/HackSimulatorGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */