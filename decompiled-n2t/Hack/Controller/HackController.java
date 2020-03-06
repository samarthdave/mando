package Hack.Controller;

import Hack.Events.ProgramEvent;
import Hack.Events.ProgramEventListener;
import Hack.Utilities.Conversions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.StringCharacterIterator;
import java.util.Vector;
import javax.swing.Timer;

public class HackController implements ControllerEventListener, ActionListener, ProgramEventListener {
  public static final int NUMBER_OF_SPEED_UNITS = 5;
  
  public static final float[] SPEED_FUNCTION = new float[] { 0.0F, 0.35F, 0.63F, 0.87F, 1.0F };
  
  public static final int[] FASTFORWARD_SPEED_FUNCTION = new int[] { 500, 1000, 2000, 4000, 15000 };
  
  public static final int DISPLAY_CHANGES = 0;
  
  public static final int ANIMATION = 1;
  
  public static final int NO_DISPLAY_CHANGES = 2;
  
  public static final int DECIMAL_FORMAT = 0;
  
  public static final int HEXA_FORMAT = 1;
  
  public static final int BINARY_FORMAT = 2;
  
  public static final int SCRIPT_ADDITIONAL_DISPLAY = 0;
  
  public static final int OUTPUT_ADDITIONAL_DISPLAY = 1;
  
  public static final int COMPARISON_ADDITIONAL_DISPLAY = 2;
  
  public static final int NO_ADDITIONAL_DISPLAY = 3;
  
  private static final String INITIAL_SCRIPT_DIR = "scripts";
  
  private static final int MAX_MS = 2500;
  
  private static final int MIN_MS = 25;
  
  private static final int INITIAL_SPEED_UNIT = 3;
  
  private static final String SPACES = "                                        ";
  
  protected ControllerGUI gui;
  
  private File currentScriptFile;
  
  private String currentOutputName;
  
  private String currentComparisonName;
  
  private Script script;
  
  protected HackSimulator simulator;
  
  private int currentSpeedUnit;
  
  private int animationMode;
  
  private int currentCommandIndex;
  
  private PrintWriter output;
  
  private BufferedReader comparisonFile;
  
  private int loopCommandIndex;
  
  private int repeatCounter;
  
  private ScriptCondition whileCondititon;
  
  private VariableFormat[] varList;
  
  private Vector breakpoints;
  
  private int compareLinesCounter;
  
  private int outputLinesCounter;
  
  private Timer timer;
  
  protected boolean singleStepLocked;
  
  private SingleStepTask singleStepTask;
  
  private FastForwardTask fastForwardTask;
  
  private SetAnimationModeTask setAnimationModeTask;
  
  private SetNumericFormatTask setNumericFormatTask;
  
  private boolean fastForwardRunning;
  
  private boolean singleStepRunning;
  
  private boolean scriptEnded;
  
  private boolean programHalted;
  
  private int[] delays;
  
  private boolean comparisonFailed;
  
  private int comparisonFailureLine;
  
  private String lastEcho;
  
  private File defaultScriptFile;
  
  public HackController(HackSimulator paramHackSimulator, String paramString) {
    File file = new File(paramString);
    if (!file.exists())
      displayMessage(paramString + " doesn't exist", true); 
    this.simulator = paramHackSimulator;
    this.animationMode = 2;
    paramHackSimulator.setAnimationMode(this.animationMode);
    paramHackSimulator.addListener(this);
    this.breakpoints = new Vector();
    try {
      loadNewScript(file, false);
      saveWorkingDir(file);
    } catch (ScriptException scriptException) {
      displayMessage(scriptException.getMessage(), true);
    } catch (ControllerException controllerException) {
      displayMessage(controllerException.getMessage(), true);
    } 
    this.fastForwardRunning = true;
    while (this.fastForwardRunning)
      singleStep(); 
  }
  
  public HackController(ControllerGUI paramControllerGUI, HackSimulator paramHackSimulator, String paramString) throws ScriptException, ControllerException {
    this.gui = paramControllerGUI;
    this.simulator = paramHackSimulator;
    this.singleStepTask = new SingleStepTask(this);
    this.fastForwardTask = new FastForwardTask(this);
    this.setAnimationModeTask = new SetAnimationModeTask(this);
    this.setNumericFormatTask = new SetNumericFormatTask(this);
    paramHackSimulator.addListener(this);
    paramHackSimulator.addProgramListener(this);
    this.breakpoints = new Vector();
    this.defaultScriptFile = new File(paramString);
    loadNewScript(this.defaultScriptFile, false);
    this.delays = new int[5];
    for (byte b = 0; b < 5; b++)
      this.delays[b] = (int)(2500.0F - SPEED_FUNCTION[b] * 2475.0F); 
    this.currentSpeedUnit = 3;
    this.animationMode = paramHackSimulator.getInitialAnimationMode();
    paramHackSimulator.setAnimationMode(this.animationMode);
    paramHackSimulator.setAnimationSpeed(3);
    paramHackSimulator.setNumericFormat(paramHackSimulator.getInitialNumericFormat());
    this.timer = new Timer(this.delays[this.currentSpeedUnit - 1], this);
    paramControllerGUI.setSimulator(paramHackSimulator.getGUI());
    paramControllerGUI.setTitle(paramHackSimulator.getName() + getVersionString());
    File file = loadWorkingDir();
    paramHackSimulator.setWorkingDir(file);
    paramControllerGUI.setWorkingDir(file);
    paramControllerGUI.addControllerListener(this);
    paramControllerGUI.setSpeed(this.currentSpeedUnit);
    paramControllerGUI.setAnimationMode(this.animationMode);
    paramControllerGUI.setNumericFormat(paramHackSimulator.getInitialNumericFormat());
    paramControllerGUI.setAdditionalDisplay(paramHackSimulator.getInitialAdditionalDisplay());
    paramControllerGUI.setVariables(paramHackSimulator.getVariables());
    stopMode();
    paramHackSimulator.prepareGUI();
  }
  
  private void rewind() {
    try {
      if (this.scriptEnded || this.programHalted) {
        this.gui.enableSingleStep();
        this.gui.enableFastForward();
      } 
      this.scriptEnded = false;
      this.programHalted = false;
      int i = this.animationMode;
      setAnimationMode(0);
      this.simulator.restart();
      refreshSimulator();
      setAnimationMode(i);
      if (this.output != null)
        resetOutputFile(); 
      if (this.comparisonFile != null)
        resetComparisonFile(); 
      this.lastEcho = "";
      this.currentCommandIndex = 0;
      this.gui.setCurrentScriptLine(this.script.getLineNumberAt(0));
    } catch (ControllerException controllerException) {
      displayMessage(controllerException.getMessage(), true);
    } 
  }
  
  private void stopMode() {
    if (this.fastForwardRunning) {
      if (this.gui != null) {
        this.timer.stop();
        this.gui.enableLoadProgram();
        this.gui.enableSpeedSlider();
      } 
      this.fastForwardRunning = false;
    } 
    this.singleStepRunning = false;
    if (this.gui != null) {
      this.gui.enableSingleStep();
      this.gui.enableFastForward();
      this.gui.enableScript();
      this.gui.enableRewind();
      this.gui.disableStop();
      this.gui.enableAnimationModes();
      if (this.animationMode == 2)
        this.gui.setCurrentScriptLine(this.script.getLineNumberAt(this.currentCommandIndex)); 
      refreshSimulator();
    } 
  }
  
  private void fastForward() {
    this.gui.enableStop();
    this.gui.disableSingleStep();
    this.gui.disableRewind();
    this.gui.disableScript();
    this.gui.disableFastForward();
    this.gui.disableAnimationModes();
    this.gui.disableLoadProgram();
    this.fastForwardRunning = true;
    this.simulator.prepareFastForward();
    if (this.animationMode != 2) {
      this.timer.start();
    } else {
      displayMessage("Running...", false);
      this.gui.disableSpeedSlider();
      Thread thread = new Thread(this.fastForwardTask);
      thread.start();
    } 
  }
  
  private synchronized void singleStep() {
    this.singleStepLocked = true;
    try {
      byte b;
      this.singleStepRunning = true;
      do {
        b = miniStep();
      } while (b == 1 && this.singleStepRunning);
      this.singleStepRunning = false;
      if (b == 3) {
        displayMessage("Script reached a '!' terminator", false);
        stopMode();
      } 
      for (byte b1 = 0; b1 < this.breakpoints.size(); b1++) {
        Breakpoint breakpoint = this.breakpoints.elementAt(b1);
        String str = this.simulator.getValue(breakpoint.getVarName());
        if (str.equals(breakpoint.getValue())) {
          if (!breakpoint.isReached()) {
            breakpoint.on();
            this.gui.setBreakpoints(this.breakpoints);
            displayMessage("Breakpoint reached", false);
            this.gui.showBreakpoints();
            stopMode();
          } 
        } else if (breakpoint.isReached()) {
          breakpoint.off();
          this.gui.setBreakpoints(this.breakpoints);
        } 
      } 
    } catch (ControllerException controllerException) {
      stopWithError(controllerException);
    } catch (ProgramException programException) {
      stopWithError(programException);
    } catch (CommandException commandException) {
      stopWithError(commandException);
    } catch (VariableException variableException) {
      stopWithError(variableException);
    } 
    this.singleStepLocked = false;
    notifyAll();
  }
  
  private void stopWithError(Exception paramException) {
    displayMessage(paramException.getMessage(), true);
    stopMode();
  }
  
  private byte miniStep() throws ControllerException, ProgramException, CommandException, VariableException {
    while (true) {
      Command command = this.script.getCommandAt(this.currentCommandIndex);
      boolean bool = false;
      switch (command.getCode()) {
        case 1:
          this.simulator.doCommand((String[])command.getArg());
          break;
        case 2:
          doOutputFileCommand(command);
          break;
        case 3:
          doCompareToCommand(command);
          break;
        case 4:
          doOutputListCommand(command);
          break;
        case 5:
          doOutputCommand(command);
          break;
        case 13:
          doEchoCommand(command);
          break;
        case 14:
          doClearEchoCommand(command);
          break;
        case 6:
          doBreakpointCommand(command);
          break;
        case 7:
          doClearBreakpointsCommand(command);
          break;
        case 8:
          this.repeatCounter = ((Integer)command.getArg()).intValue();
          this.loopCommandIndex = this.currentCommandIndex + 1;
          bool = true;
          break;
        case 10:
          this.whileCondititon = (ScriptCondition)command.getArg();
          this.loopCommandIndex = this.currentCommandIndex + 1;
          if (!this.whileCondititon.compare(this.simulator))
            while (this.script.getCommandAt(this.currentCommandIndex).getCode() != 11)
              this.currentCommandIndex++;  
          bool = true;
          break;
        case 12:
          this.scriptEnded = true;
          stopMode();
          if (this.gui != null) {
            this.gui.disableSingleStep();
            this.gui.disableFastForward();
          } 
          try {
            if (this.output != null)
              this.output.close(); 
            if (this.comparisonFile != null) {
              if (this.comparisonFailed) {
                displayMessage("End of script - Comparison failure at line " + this.comparisonFailureLine, true);
              } else {
                displayMessage("End of script - Comparison ended successfully", false);
              } 
              this.comparisonFile.close();
              break;
            } 
            displayMessage("End of script", false);
          } catch (IOException iOException) {
            throw new ControllerException("Could not read comparison file");
          } 
          break;
      } 
      if (command.getCode() != 12) {
        this.currentCommandIndex++;
        Command command1 = this.script.getCommandAt(this.currentCommandIndex);
        if (command1.getCode() == 9) {
          if (this.repeatCounter == 0 || --this.repeatCounter > 0) {
            this.currentCommandIndex = this.loopCommandIndex;
          } else {
            this.currentCommandIndex++;
          } 
        } else if (command1.getCode() == 11) {
          if (this.whileCondititon.compare(this.simulator)) {
            this.currentCommandIndex = this.loopCommandIndex;
          } else {
            this.currentCommandIndex++;
          } 
        } 
        if (this.animationMode != 2)
          this.gui.setCurrentScriptLine(this.script.getLineNumberAt(this.currentCommandIndex)); 
      } 
      if (!bool)
        return command.getTerminator(); 
    } 
  }
  
  private void doOutputFileCommand(Command paramCommand) throws ControllerException {
    this.currentOutputName = this.currentScriptFile.getParent() + "/" + (String)paramCommand.getArg();
    resetOutputFile();
    if (this.gui != null)
      this.gui.setOutputFile(this.currentOutputName); 
  }
  
  private void doCompareToCommand(Command paramCommand) throws ControllerException {
    this.currentComparisonName = this.currentScriptFile.getParent() + "/" + (String)paramCommand.getArg();
    resetComparisonFile();
    if (this.gui != null)
      this.gui.setComparisonFile(this.currentComparisonName); 
  }
  
  private void doOutputListCommand(Command paramCommand) throws ControllerException {
    if (this.output == null)
      throw new ControllerException("No output file specified"); 
    this.varList = (VariableFormat[])paramCommand.getArg();
    StringBuffer stringBuffer = new StringBuffer("|");
    for (byte b = 0; b < this.varList.length; b++) {
      int i = (this.varList[b]).padL + (this.varList[b]).padR + (this.varList[b]).len;
      String str = ((this.varList[b]).varName.length() > i) ? (this.varList[b]).varName.substring(0, i) : (this.varList[b]).varName;
      int j = (i - str.length()) / 2;
      int k = i - j - str.length();
      stringBuffer.append("                                        ".substring(0, j) + str + "                                        ".substring(0, k) + '|');
    } 
    outputAndCompare(stringBuffer.toString());
  }
  
  private void doOutputCommand(Command paramCommand) throws ControllerException, VariableException {
    if (this.output == null)
      throw new ControllerException("No output file specified"); 
    StringBuffer stringBuffer = new StringBuffer("|");
    for (byte b = 0; b < this.varList.length; b++) {
      String str = this.simulator.getValue((this.varList[b]).varName);
      if ((this.varList[b]).format != 'S') {
        int k;
        try {
          k = Integer.parseInt(str);
        } catch (NumberFormatException numberFormatException) {
          throw new VariableException("Variable is not numeric", (this.varList[b]).varName);
        } 
        if ((this.varList[b]).format == 'X') {
          str = Conversions.decimalToHex(k, 4);
        } else if ((this.varList[b]).format == 'B') {
          str = Conversions.decimalToBinary(k, 16);
        } 
      } 
      if (str.length() > (this.varList[b]).len)
        str = str.substring(str.length() - (this.varList[b]).len); 
      int i = (this.varList[b]).padL + (((this.varList[b]).format == 'S') ? 0 : ((this.varList[b]).len - str.length()));
      int j = (this.varList[b]).padR + (((this.varList[b]).format == 'S') ? ((this.varList[b]).len - str.length()) : 0);
      stringBuffer.append("                                        ".substring(0, i) + str + "                                        ".substring(0, j) + '|');
    } 
    outputAndCompare(stringBuffer.toString());
  }
  
  private void doEchoCommand(Command paramCommand) throws ControllerException {
    this.lastEcho = (String)paramCommand.getArg();
    if (this.gui != null)
      this.gui.displayMessage(this.lastEcho, false); 
  }
  
  private void doClearEchoCommand(Command paramCommand) throws ControllerException {
    this.lastEcho = "";
    if (this.gui != null)
      this.gui.displayMessage("", false); 
  }
  
  private void doBreakpointCommand(Command paramCommand) throws ControllerException {
    Breakpoint breakpoint = (Breakpoint)paramCommand.getArg();
    if (!breakpointExists(this.breakpoints, breakpoint)) {
      this.breakpoints.addElement(breakpoint);
      this.gui.setBreakpoints(this.breakpoints);
    } 
  }
  
  private void doClearBreakpointsCommand(Command paramCommand) throws ControllerException {
    this.breakpoints.removeAllElements();
    this.gui.setBreakpoints(this.breakpoints);
  }
  
  private static boolean compareLineWithTemplate(String paramString1, String paramString2) {
    if (paramString1.length() != paramString2.length())
      return false; 
    StringCharacterIterator stringCharacterIterator1 = new StringCharacterIterator(paramString1);
    StringCharacterIterator stringCharacterIterator2 = new StringCharacterIterator(paramString2);
    stringCharacterIterator1.first();
    stringCharacterIterator2.first();
    while (stringCharacterIterator1.current() != Character.MAX_VALUE) {
      if (stringCharacterIterator2.current() != '*' && stringCharacterIterator1.current() != stringCharacterIterator2.current())
        return false; 
      stringCharacterIterator1.next();
      stringCharacterIterator2.next();
    } 
    return true;
  }
  
  private void outputAndCompare(String paramString) throws ControllerException {
    this.output.println(paramString);
    this.output.flush();
    if (this.gui != null) {
      this.gui.outputFileUpdated();
      this.gui.setCurrentOutputLine(this.outputLinesCounter);
    } 
    this.outputLinesCounter++;
    if (this.comparisonFile != null)
      try {
        String str = this.comparisonFile.readLine();
        if (this.gui != null)
          this.gui.setCurrentComparisonLine(this.compareLinesCounter); 
        this.compareLinesCounter++;
        if (!compareLineWithTemplate(paramString, str)) {
          this.comparisonFailed = true;
          this.comparisonFailureLine = this.compareLinesCounter;
          displayMessage("Comparison failure at line " + this.comparisonFailureLine, true);
          stopMode();
        } 
      } catch (IOException iOException) {
        throw new ControllerException("Could not read comparison file");
      }  
  }
  
  protected void loadNewScript(File paramFile, boolean paramBoolean) throws ControllerException, ScriptException {
    this.currentScriptFile = paramFile;
    this.script = new Script(paramFile.getPath());
    this.breakpoints.removeAllElements();
    this.currentCommandIndex = 0;
    this.output = null;
    this.currentOutputName = "";
    this.comparisonFile = null;
    this.currentComparisonName = "";
    if (this.gui != null) {
      this.gui.setOutputFile("");
      this.gui.setComparisonFile("");
      this.gui.setBreakpoints(this.breakpoints);
      this.gui.setScriptFile(paramFile.getPath());
      this.gui.setCurrentScriptLine(this.script.getLineNumberAt(0));
    } 
    if (paramBoolean)
      displayMessage("New script loaded: " + paramFile.getPath(), false); 
  }
  
  private void resetOutputFile() throws ControllerException {
    try {
      this.output = new PrintWriter(new FileWriter(this.currentOutputName));
      this.outputLinesCounter = 0;
      if (this.gui != null)
        this.gui.setCurrentOutputLine(-1); 
    } catch (IOException iOException) {
      throw new ControllerException("Could not create output file " + this.currentOutputName);
    } 
    if (this.gui != null)
      this.gui.setOutputFile(this.currentOutputName); 
  }
  
  private void resetComparisonFile() throws ControllerException {
    try {
      this.comparisonFile = new BufferedReader(new FileReader(this.currentComparisonName));
      this.compareLinesCounter = 0;
      this.comparisonFailed = false;
      if (this.gui != null)
        this.gui.setCurrentComparisonLine(-1); 
    } catch (IOException iOException) {
      throw new ControllerException("Could not open comparison file " + this.currentComparisonName);
    } 
  }
  
  private void setSpeed(int paramInt) {
    this.currentSpeedUnit = paramInt;
    this.timer.setDelay(this.delays[this.currentSpeedUnit - 1]);
    this.simulator.setAnimationSpeed(paramInt);
  }
  
  private void setAnimationMode(int paramInt) {
    this.simulator.setAnimationMode(paramInt);
    if (this.animationMode == 2 && paramInt != 2) {
      this.simulator.refresh();
      this.gui.setCurrentScriptLine(this.script.getLineNumberAt(this.currentCommandIndex));
    } 
    this.gui.setAnimationMode(paramInt);
    this.animationMode = paramInt;
  }
  
  private void setNumericFormat(int paramInt) {
    this.simulator.setNumericFormat(paramInt);
    this.gui.setNumericFormat(paramInt);
  }
  
  private void setAdditionalDisplay(int paramInt) {
    switch (paramInt) {
      case 3:
        this.simulator.getGUI().setAdditionalDisplay(null);
        break;
      case 0:
        this.simulator.getGUI().setAdditionalDisplay(this.gui.getScriptComponent());
        break;
      case 1:
        this.simulator.getGUI().setAdditionalDisplay(this.gui.getOutputComponent());
        break;
      case 2:
        this.simulator.getGUI().setAdditionalDisplay(this.gui.getComparisonComponent());
        break;
    } 
    this.gui.setAdditionalDisplay(paramInt);
  }
  
  private void setBreakpoints(Vector paramVector) {
    this.breakpoints = new Vector();
    for (byte b = 0; b < paramVector.size(); b++) {
      Breakpoint breakpoint = paramVector.elementAt(b);
      if (!breakpointExists(this.breakpoints, breakpoint))
        this.breakpoints.addElement(breakpoint); 
    } 
  }
  
  private boolean breakpointExists(Vector paramVector, Breakpoint paramBreakpoint) {
    boolean bool = false;
    for (byte b = 0; b < paramVector.size() && !bool; b++) {
      Breakpoint breakpoint = paramVector.elementAt(b);
      if (paramBreakpoint.getVarName().equals(breakpoint.getVarName()) && paramBreakpoint.getValue().equals(breakpoint.getValue()))
        bool = true; 
    } 
    return bool;
  }
  
  private void refreshSimulator() {
    if (this.animationMode == 2) {
      this.simulator.setAnimationMode(0);
      this.simulator.refresh();
      this.simulator.setAnimationMode(2);
    } 
  }
  
  private void displayMessage(String paramString, boolean paramBoolean) {
    if (this.gui != null) {
      this.gui.displayMessage(paramString, paramBoolean);
    } else if (paramBoolean) {
      System.err.println(paramString);
      System.exit(-1);
    } else {
      System.out.println(paramString);
    } 
  }
  
  protected File loadWorkingDir() {
    String str = ".";
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("bin/" + this.simulator.getName() + ".dat"));
      str = bufferedReader.readLine();
      bufferedReader.close();
    } catch (IOException iOException) {}
    return new File(str);
  }
  
  protected void saveWorkingDir(File paramFile) {
    File file1 = paramFile.getParentFile();
    if (this.gui != null)
      this.gui.setWorkingDir(file1); 
    this.simulator.setWorkingDir(paramFile);
    File file2 = paramFile.isDirectory() ? paramFile : file1;
    try {
      PrintWriter printWriter = new PrintWriter(new FileWriter("bin/" + this.simulator.getName() + ".dat"));
      printWriter.println(file2.getAbsolutePath());
      printWriter.close();
    } catch (IOException iOException) {}
  }
  
  private static String getVersionString() {
    return " (2.5)";
  }
  
  protected void reloadDefaultScript() {
    if (!this.currentScriptFile.equals(this.defaultScriptFile)) {
      this.gui.setAdditionalDisplay(3);
      try {
        loadNewScript(this.defaultScriptFile, false);
        rewind();
      } catch (ScriptException scriptException) {
      
      } catch (ControllerException controllerException) {}
    } 
  }
  
  protected void updateProgramFile(String paramString) {
    this.gui.setTitle(this.simulator.getName() + getVersionString() + " - " + paramString);
    File file = new File(paramString);
    saveWorkingDir(file);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (!this.singleStepLocked) {
      Thread thread = new Thread(this.singleStepTask);
      thread.start();
    } 
  }
  
  public void programChanged(ProgramEvent paramProgramEvent) {
    switch (paramProgramEvent.getType()) {
      case 2:
        updateProgramFile(paramProgramEvent.getProgramFileName());
        break;
      case 1:
        updateProgramFile(paramProgramEvent.getProgramFileName());
        if (!this.singleStepLocked)
          reloadDefaultScript(); 
        break;
      case 3:
        this.gui.setTitle(this.simulator.getName() + getVersionString());
        break;
    } 
  }
  
  public void actionPerformed(ControllerEvent paramControllerEvent) {
    try {
      Thread thread;
      File file;
      switch (paramControllerEvent.getAction()) {
        case 1:
          displayMessage(this.lastEcho, true);
          this.gui.disableSingleStep();
          this.gui.disableFastForward();
          this.gui.disableScript();
          this.gui.disableRewind();
          this.gui.enableStop();
          thread = new Thread(this.singleStepTask);
          thread.start();
          return;
        case 2:
          displayMessage(this.lastEcho, true);
          fastForward();
          return;
        case 4:
          if (this.animationMode == 2)
            displayMessage("", false); 
          stopMode();
          return;
        case 9:
          displayMessage("Script restarted", false);
          rewind();
          return;
        case 3:
          setSpeed(((Integer)paramControllerEvent.getData()).intValue());
          return;
        case 5:
          setBreakpoints((Vector)paramControllerEvent.getData());
          return;
        case 6:
          file = (File)paramControllerEvent.getData();
          loadNewScript(file, true);
          setAdditionalDisplay(0);
          saveWorkingDir(file);
          rewind();
          return;
        case 10:
          this.setAnimationModeTask.setMode(((Integer)paramControllerEvent.getData()).intValue());
          thread = new Thread(this.setAnimationModeTask);
          thread.start();
          return;
        case 11:
          this.setNumericFormatTask.setFormat(((Integer)paramControllerEvent.getData()).intValue());
          thread = new Thread(this.setNumericFormatTask);
          thread.start();
          return;
        case 12:
          setAdditionalDisplay(((Integer)paramControllerEvent.getData()).intValue());
          return;
        case 15:
          this.gui.disableAnimationModes();
          return;
        case 16:
          this.gui.enableAnimationModes();
          return;
        case 17:
          this.gui.disableSingleStep();
          return;
        case 18:
          this.gui.enableSingleStep();
          return;
        case 19:
          this.gui.disableFastForward();
          return;
        case 20:
          this.gui.enableFastForward();
          return;
        case 27:
          this.simulator.loadProgram();
          return;
        case 21:
          displayMessage("End of program", false);
          this.programHalted = true;
          if (this.fastForwardRunning)
            stopMode(); 
          this.gui.disableSingleStep();
          this.gui.disableFastForward();
          return;
        case 22:
          if (this.programHalted) {
            this.programHalted = false;
            this.gui.enableSingleStep();
            this.gui.enableFastForward();
          } 
          return;
        case 23:
          this.gui.disableSingleStep();
          this.gui.disableFastForward();
          this.gui.disableRewind();
          return;
        case 24:
          this.gui.enableSingleStep();
          this.gui.enableFastForward();
          this.gui.enableRewind();
          return;
        case 25:
          displayMessage((String)paramControllerEvent.getData(), false);
          return;
        case 26:
          if (this.timer.isRunning())
            stopMode(); 
          displayMessage((String)paramControllerEvent.getData(), true);
          return;
      } 
      doUnknownAction(paramControllerEvent.getAction(), paramControllerEvent.getData());
    } catch (ScriptException scriptException) {
      displayMessage(scriptException.getMessage(), true);
      stopMode();
    } catch (ControllerException controllerException) {
      displayMessage(controllerException.getMessage(), true);
      stopMode();
    } 
  }
  
  protected void doUnknownAction(byte paramByte, Object paramObject) throws ControllerException {}
  
  class SetNumericFormatTask implements Runnable {
    private int numericFormat;
    
    private final HackController this$0;
    
    SetNumericFormatTask(HackController this$0) {
      this.this$0 = this$0;
    }
    
    public void setFormat(int param1Int) {
      this.numericFormat = param1Int;
    }
    
    public void run() {
      this.this$0.setNumericFormat(this.numericFormat);
    }
  }
  
  class SetAnimationModeTask implements Runnable {
    private int animationMode;
    
    private final HackController this$0;
    
    SetAnimationModeTask(HackController this$0) {
      this.this$0 = this$0;
    }
    
    public void setMode(int param1Int) {
      this.animationMode = param1Int;
    }
    
    public void run() {
      this.this$0.setAnimationMode(this.animationMode);
    }
  }
  
  class FastForwardTask implements Runnable {
    private final HackController this$0;
    
    FastForwardTask(HackController this$0) {
      this.this$0 = this$0;
    }
    
    public synchronized void run() {
      try {
        System.runFinalization();
        System.gc();
        wait(300L);
      } catch (InterruptedException interruptedException) {}
      int i = 0;
      int j = HackController.FASTFORWARD_SPEED_FUNCTION[this.this$0.currentSpeedUnit - 1];
      while (this.this$0.fastForwardRunning) {
        this.this$0.singleStep();
        if (i == j) {
          i = 0;
          try {
            wait(1L);
          } catch (InterruptedException interruptedException) {}
        } 
        i++;
      } 
    }
  }
  
  class SingleStepTask implements Runnable {
    private final HackController this$0;
    
    SingleStepTask(HackController this$0) {
      this.this$0 = this$0;
    }
    
    public void run() {
      this.this$0.singleStep();
      if (!this.this$0.fastForwardRunning) {
        if (!this.this$0.scriptEnded && !this.this$0.programHalted) {
          this.this$0.gui.enableSingleStep();
          this.this$0.gui.enableFastForward();
          this.this$0.gui.disableStop();
        } 
        this.this$0.gui.enableScript();
        this.this$0.gui.enableRewind();
      } 
      if (this.this$0.animationMode == 2) {
        this.this$0.refreshSimulator();
        this.this$0.gui.setCurrentScriptLine(this.this$0.script.getLineNumberAt(this.this$0.currentCommandIndex));
      } 
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/HackController.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */