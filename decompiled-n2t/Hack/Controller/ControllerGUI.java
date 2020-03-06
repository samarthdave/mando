package Hack.Controller;

import java.io.File;
import java.util.Vector;
import javax.swing.JComponent;

public interface ControllerGUI {
  void addControllerListener(ControllerEventListener paramControllerEventListener);
  
  void removeControllerListener(ControllerEventListener paramControllerEventListener);
  
  void notifyControllerListeners(byte paramByte, Object paramObject);
  
  void setSimulator(HackSimulatorGUI paramHackSimulatorGUI);
  
  void setTitle(String paramString);
  
  void displayMessage(String paramString, boolean paramBoolean);
  
  void setWorkingDir(File paramFile);
  
  void setScriptFile(String paramString);
  
  void setCurrentScriptLine(int paramInt);
  
  JComponent getScriptComponent();
  
  void setOutputFile(String paramString);
  
  void setCurrentOutputLine(int paramInt);
  
  JComponent getOutputComponent();
  
  void setComparisonFile(String paramString);
  
  void setCurrentComparisonLine(int paramInt);
  
  JComponent getComparisonComponent();
  
  void setAdditionalDisplay(int paramInt);
  
  void setBreakpoints(Vector paramVector);
  
  void setVariables(String[] paramArrayOfString);
  
  void setSpeed(int paramInt);
  
  void setAnimationMode(int paramInt);
  
  void setNumericFormat(int paramInt);
  
  void showBreakpoints();
  
  void outputFileUpdated();
  
  void enableSingleStep();
  
  void disableSingleStep();
  
  void enableFastForward();
  
  void disableFastForward();
  
  void enableStop();
  
  void disableStop();
  
  void enableScript();
  
  void disableScript();
  
  void enableRewind();
  
  void disableRewind();
  
  void enableLoadProgram();
  
  void disableLoadProgram();
  
  void enableSpeedSlider();
  
  void disableSpeedSlider();
  
  void enableAnimationModes();
  
  void disableAnimationModes();
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/ControllerGUI.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */