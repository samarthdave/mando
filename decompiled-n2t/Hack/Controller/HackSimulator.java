package Hack.Controller;

import Hack.ComputerParts.ComputerPartErrorEventListener;
import Hack.Events.ProgramEvent;
import Hack.Events.ProgramEventListener;
import java.io.File;
import java.util.Vector;

public abstract class HackSimulator implements ProgramEventListener, ComputerPartErrorEventListener {
  private Vector listeners = new Vector();
  
  private Vector programListeners = new Vector();
  
  protected File workingDir;
  
  public abstract String getName();
  
  public abstract String getValue(String paramString) throws VariableException;
  
  public abstract void setValue(String paramString1, String paramString2) throws VariableException;
  
  public abstract void doCommand(String[] paramArrayOfString) throws CommandException, ProgramException, VariableException;
  
  public abstract void restart();
  
  public abstract void setAnimationMode(int paramInt);
  
  public abstract void setNumericFormat(int paramInt);
  
  public abstract void setAnimationSpeed(int paramInt);
  
  public abstract void refresh();
  
  public abstract void prepareFastForward();
  
  public abstract void prepareGUI();
  
  public abstract String[] getVariables();
  
  public int getInitialAnimationMode() {
    return 0;
  }
  
  public int getInitialNumericFormat() {
    return 0;
  }
  
  public int getInitialAdditionalDisplay() {
    return 3;
  }
  
  protected abstract HackSimulatorGUI getGUI();
  
  protected void loadProgram() {
    getGUI().loadProgram();
  }
  
  public void setWorkingDir(File paramFile) {
    File file = paramFile.getParentFile();
    this.workingDir = paramFile.isDirectory() ? paramFile : file;
    HackSimulatorGUI hackSimulatorGUI = getGUI();
    if (hackSimulatorGUI != null)
      getGUI().setWorkingDir(file); 
  }
  
  protected void displayMessage(String paramString, boolean paramBoolean) {
    if (paramBoolean) {
      notifyListeners((byte)26, paramString);
    } else {
      notifyListeners((byte)25, paramString);
    } 
  }
  
  protected void clearMessage() {
    notifyListeners((byte)25, "");
  }
  
  public void addListener(ControllerEventListener paramControllerEventListener) {
    this.listeners.addElement(paramControllerEventListener);
  }
  
  public void removeListener(ControllerEventListener paramControllerEventListener) {
    this.listeners.removeElement(paramControllerEventListener);
  }
  
  public void notifyListeners(byte paramByte, Object paramObject) {
    ControllerEvent controllerEvent = new ControllerEvent(this, paramByte, paramObject);
    for (byte b = 0; b < this.listeners.size(); b++)
      ((ControllerEventListener)this.listeners.elementAt(b)).actionPerformed(controllerEvent); 
  }
  
  public void addProgramListener(ProgramEventListener paramProgramEventListener) {
    this.programListeners.add(paramProgramEventListener);
  }
  
  public void removeProgramListener(ProgramEventListener paramProgramEventListener) {
    this.programListeners.remove(paramProgramEventListener);
  }
  
  protected void notifyProgramListeners(byte paramByte, String paramString) {
    ProgramEvent programEvent = new ProgramEvent(this, paramByte, paramString);
    for (byte b = 0; b < this.programListeners.size(); b++)
      ((ProgramEventListener)this.programListeners.elementAt(b)).programChanged(programEvent); 
  }
  
  public void programChanged(ProgramEvent paramProgramEvent) {
    notifyProgramListeners(paramProgramEvent.getType(), paramProgramEvent.getProgramFileName());
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/HackSimulator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */