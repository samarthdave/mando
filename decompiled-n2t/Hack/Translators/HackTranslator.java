package Hack.Translators;

import Hack.ComputerParts.TextFileEvent;
import Hack.ComputerParts.TextFileEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.Timer;

public abstract class HackTranslator implements HackTranslatorEventListener, ActionListener, TextFileEventListener {
  private static final int FAST_FORWARD_DELAY = 750;
  
  private PrintWriter writer;
  
  protected String sourceFileName;
  
  protected String destFileName;
  
  protected int programSize;
  
  protected short[] program;
  
  protected String[] source;
  
  protected HackTranslatorGUI gui;
  
  private Timer timer;
  
  private boolean singleStepLocked;
  
  private SingleStepTask singleStepTask;
  
  private FullCompilationTask fullCompilationTask;
  
  private FastForwardTask fastForwardTask;
  
  private LoadSourceTask loadSourceTask;
  
  protected boolean compilationStarted;
  
  protected int destPC;
  
  protected int sourcePC;
  
  private boolean updateGUI;
  
  protected Hashtable compilationMap;
  
  protected boolean inFullCompilation;
  
  protected boolean inFastForward;
  
  public HackTranslator(String paramString, int paramInt, short paramShort, boolean paramBoolean) throws HackTranslatorException {
    if (paramString.indexOf(".") < 0)
      paramString = paramString + "." + getSourceExtension(); 
    checkSourceFile(paramString);
    this.source = new String[0];
    init(paramInt, paramShort);
    loadSource(paramString);
    fullCompilation();
    if (paramBoolean)
      save(); 
  }
  
  public HackTranslator(HackTranslatorGUI paramHackTranslatorGUI, int paramInt, short paramShort, String paramString) throws HackTranslatorException {
    this.gui = paramHackTranslatorGUI;
    paramHackTranslatorGUI.addHackTranslatorListener(this);
    paramHackTranslatorGUI.getSource().addTextFileListener(this);
    paramHackTranslatorGUI.setTitle(getName() + getVersionString());
    this.singleStepTask = new SingleStepTask(this);
    this.fullCompilationTask = new FullCompilationTask(this);
    this.fastForwardTask = new FastForwardTask(this);
    this.loadSourceTask = new LoadSourceTask(this);
    this.timer = new Timer(750, this);
    init(paramInt, paramShort);
    File file = loadWorkingDir();
    paramHackTranslatorGUI.setWorkingDir(file);
    if (paramString == null) {
      paramHackTranslatorGUI.disableSingleStep();
      paramHackTranslatorGUI.disableFastForward();
      paramHackTranslatorGUI.disableStop();
      paramHackTranslatorGUI.disableRewind();
      paramHackTranslatorGUI.disableFullCompilation();
      paramHackTranslatorGUI.disableSave();
      paramHackTranslatorGUI.enableLoadSource();
      paramHackTranslatorGUI.disableSourceRowSelection();
    } else {
      loadSource(paramString);
      paramHackTranslatorGUI.setSourceName(paramString);
    } 
  }
  
  protected abstract String getSourceExtension();
  
  protected abstract String getDestinationExtension();
  
  protected abstract String getName();
  
  private static String getVersionString() {
    return " (2.5)";
  }
  
  protected int[] compileLineAndCount(String paramString) throws HackTranslatorException {
    int[] arrayOfInt = null;
    int i = this.destPC;
    compileLine(paramString);
    int j = this.destPC - i;
    if (j > 0)
      arrayOfInt = new int[] { i, this.destPC - 1 }; 
    return arrayOfInt;
  }
  
  protected abstract void compileLine(String paramString) throws HackTranslatorException;
  
  protected void init(int paramInt, short paramShort) {
    this.program = new short[paramInt];
    for (byte b = 0; b < paramInt; b++)
      this.program[b] = paramShort; 
    this.programSize = 0;
  }
  
  private void checkSourceFile(String paramString) throws HackTranslatorException {
    if (!paramString.endsWith("." + getSourceExtension()))
      throw new HackTranslatorException(paramString + " is not a ." + getSourceExtension() + " file"); 
    File file = new File(paramString);
    if (!file.exists())
      throw new HackTranslatorException("file " + paramString + " does not exist"); 
  }
  
  private void checkDestinationFile(String paramString) throws HackTranslatorException {
    if (!paramString.endsWith("." + getDestinationExtension()))
      throw new HackTranslatorException(paramString + " is not a ." + getDestinationExtension() + " file"); 
  }
  
  protected void restartCompilation() {
    this.compilationMap = new Hashtable();
    this.sourcePC = 0;
    this.destPC = 0;
    if (this.gui != null) {
      this.compilationStarted = false;
      this.gui.getDestination().reset();
      hidePointers();
      this.gui.enableSingleStep();
      this.gui.enableFastForward();
      this.gui.disableStop();
      this.gui.enableRewind();
      this.gui.enableFullCompilation();
      this.gui.disableSave();
      this.gui.enableLoadSource();
      this.gui.disableSourceRowSelection();
    } 
  }
  
  private void loadSource(String paramString) throws HackTranslatorException {
    Vector vector1 = new Vector();
    Vector vector2 = null;
    String str = null;
    try {
      if (this.gui != null) {
        this.gui.disableSingleStep();
        this.gui.disableFastForward();
        this.gui.disableStop();
        this.gui.disableRewind();
        this.gui.disableFullCompilation();
        this.gui.disableSave();
        this.gui.disableLoadSource();
        this.gui.disableSourceRowSelection();
        this.gui.displayMessage("Please wait...", false);
      } 
      checkSourceFile(paramString);
      this.sourceFileName = paramString;
      vector2 = new Vector();
      BufferedReader bufferedReader = new BufferedReader(new FileReader(this.sourceFileName));
      String str1;
      while ((str1 = bufferedReader.readLine()) != null) {
        vector1.addElement(str1);
        if (this.gui != null)
          vector2.addElement(str1); 
      } 
      bufferedReader.close();
      this.source = new String[vector1.size()];
      vector1.toArray(this.source);
      if (this.gui != null) {
        String[] arrayOfString = new String[vector2.size()];
        vector2.toArray(arrayOfString);
        this.gui.getSource().setContents(arrayOfString);
      } 
      this.destFileName = this.sourceFileName.substring(0, this.sourceFileName.indexOf('.')) + "." + getDestinationExtension();
      initSource();
      restartCompilation();
      resetProgram();
      if (this.gui != null) {
        this.gui.setDestinationName(this.destFileName);
        this.gui.displayMessage("", false);
      } 
    } catch (HackTranslatorException hackTranslatorException) {
      str = hackTranslatorException.getMessage();
    } catch (IOException iOException) {
      str = "error reading from file " + this.sourceFileName;
    } 
    if (str != null) {
      if (this.gui != null)
        this.gui.enableLoadSource(); 
      throw new HackTranslatorException(str);
    } 
  }
  
  protected abstract void initSource() throws HackTranslatorException;
  
  protected void resetProgram() {
    this.programSize = 0;
    if (this.gui != null)
      this.gui.getDestination().reset(); 
  }
  
  protected abstract void initCompilation() throws HackTranslatorException;
  
  protected abstract void finalizeCompilation();
  
  protected void successfulCompilation() throws HackTranslatorException {
    if (this.gui != null)
      this.gui.displayMessage("File compilation succeeded", false); 
  }
  
  private void fullCompilation() throws HackTranslatorException {
    try {
      this.inFullCompilation = true;
      initCompilation();
      if (this.gui != null) {
        this.gui.disableSingleStep();
        this.gui.disableFastForward();
        this.gui.disableRewind();
        this.gui.disableFullCompilation();
        this.gui.disableLoadSource();
        this.gui.getSource().setContents(this.sourceFileName);
      } 
      this.updateGUI = false;
      while (this.sourcePC < this.source.length) {
        int[] arrayOfInt = compileLineAndCount(this.source[this.sourcePC]);
        if (arrayOfInt != null)
          this.compilationMap.put(new Integer(this.sourcePC), arrayOfInt); 
        this.sourcePC++;
      } 
      successfulCompilation();
      finalizeCompilation();
      this.programSize = this.destPC;
      if (this.gui != null) {
        showProgram(this.programSize);
        this.gui.getDestination().clearHighlights();
        this.gui.enableRewind();
        this.gui.enableLoadSource();
        this.gui.enableSave();
        this.gui.enableSourceRowSelection();
      } 
      this.inFullCompilation = false;
    } catch (HackTranslatorException hackTranslatorException) {
      this.inFullCompilation = false;
      throw new HackTranslatorException(hackTranslatorException.getMessage());
    } 
  }
  
  protected abstract String getCodeString(short paramShort, int paramInt, boolean paramBoolean);
  
  protected void addCommand(short paramShort) throws HackTranslatorException {
    if (this.destPC >= this.program.length)
      throw new HackTranslatorException("Program too large"); 
    this.program[this.destPC++] = paramShort;
    if (this.updateGUI)
      this.gui.getDestination().addLine(getCodeString(paramShort, this.destPC - 1, true)); 
  }
  
  protected void replaceCommand(int paramInt, short paramShort) {
    this.program[paramInt] = paramShort;
    if (this.updateGUI)
      this.gui.getDestination().setLineAt(paramInt, getCodeString(paramShort, paramInt, true)); 
  }
  
  protected void showProgram(int paramInt) {
    this.gui.getDestination().reset();
    String[] arrayOfString = new String[paramInt];
    for (byte b = 0; b < paramInt; b++)
      arrayOfString[b] = getCodeString(this.program[b], b, true); 
    this.gui.getDestination().setContents(arrayOfString);
  }
  
  protected void fastForward() {
    this.gui.disableSingleStep();
    this.gui.disableFastForward();
    this.gui.enableStop();
    this.gui.disableRewind();
    this.gui.disableFullCompilation();
    this.gui.disableLoadSource();
    this.inFastForward = true;
    this.timer.start();
  }
  
  private void singleStep() {
    this.singleStepLocked = true;
    try {
      initCompilation();
      if (!this.compilationStarted)
        this.compilationStarted = true; 
      this.gui.getSource().addHighlight(this.sourcePC, true);
      this.gui.getDestination().clearHighlights();
      this.updateGUI = true;
      int[] arrayOfInt = compileLineAndCount(this.source[this.sourcePC]);
      if (arrayOfInt != null)
        this.compilationMap.put(new Integer(this.sourcePC), arrayOfInt); 
      this.sourcePC++;
      if (this.sourcePC == this.source.length) {
        successfulCompilation();
        this.programSize = this.destPC;
        this.gui.enableSave();
        this.gui.enableSourceRowSelection();
        end(false);
      } 
      finalizeCompilation();
    } catch (HackTranslatorException hackTranslatorException) {
      this.gui.displayMessage(hackTranslatorException.getMessage(), true);
      end(false);
    } 
    this.singleStepLocked = false;
  }
  
  protected void hidePointers() {
    this.gui.getSource().clearHighlights();
    this.gui.getDestination().clearHighlights();
    this.gui.getSource().hideSelect();
    this.gui.getDestination().hideSelect();
  }
  
  protected void end(boolean paramBoolean) {
    this.timer.stop();
    this.gui.disableSingleStep();
    this.gui.disableFastForward();
    this.gui.disableStop();
    this.gui.enableRewind();
    this.gui.disableFullCompilation();
    this.gui.enableLoadSource();
    this.inFastForward = false;
    if (paramBoolean)
      hidePointers(); 
  }
  
  protected void stop() {
    this.timer.stop();
    this.gui.enableSingleStep();
    this.gui.enableFastForward();
    this.gui.disableStop();
    this.gui.enableRewind();
    this.gui.enableLoadSource();
    this.gui.enableFullCompilation();
    this.inFastForward = false;
  }
  
  protected void rewind() {
    restartCompilation();
    resetProgram();
  }
  
  private void save() throws HackTranslatorException {
    try {
      this.writer = new PrintWriter(new FileWriter(this.destFileName));
      dumpToFile();
      this.writer.close();
    } catch (IOException iOException) {
      throw new HackTranslatorException("could not create file " + this.destFileName);
    } 
  }
  
  public short[] getProgram() {
    return this.program;
  }
  
  private void dumpToFile() {
    for (short s = 0; s < this.programSize; s = (short)(s + 1))
      this.writer.println(getCodeString(this.program[s], s, false)); 
    this.writer.close();
  }
  
  protected void clearMessage() {
    this.gui.displayMessage("", false);
  }
  
  protected int[] rowIndexToRange(int paramInt) {
    Integer integer = new Integer(paramInt);
    return (int[])this.compilationMap.get(integer);
  }
  
  protected File loadWorkingDir() {
    String str = ".";
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("bin/" + getName() + ".dat"));
      str = bufferedReader.readLine();
      bufferedReader.close();
    } catch (IOException iOException) {}
    return new File(str);
  }
  
  protected void saveWorkingDir(File paramFile) {
    try {
      PrintWriter printWriter = new PrintWriter(new FileWriter("bin/" + getName() + ".dat"));
      printWriter.println(paramFile.getAbsolutePath());
      printWriter.close();
    } catch (IOException iOException) {}
    this.gui.setWorkingDir(paramFile);
  }
  
  public void rowSelected(TextFileEvent paramTextFileEvent) {
    int i = paramTextFileEvent.getRowIndex();
    int[] arrayOfInt = rowIndexToRange(i);
    this.gui.getSource().addHighlight(i, true);
    this.gui.getSource().hideSelect();
    if (arrayOfInt != null) {
      this.gui.getDestination().clearHighlights();
      for (int j = arrayOfInt[0]; j <= arrayOfInt[1]; j++)
        this.gui.getDestination().addHighlight(j, false); 
    } else {
      this.gui.getDestination().clearHighlights();
    } 
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (!this.singleStepLocked)
      singleStep(); 
  }
  
  protected void error(String paramString) throws HackTranslatorException {
    throw new HackTranslatorException(paramString, this.sourcePC);
  }
  
  public void actionPerformed(HackTranslatorEvent paramHackTranslatorEvent) {
    Thread thread;
    String str;
    File file;
    switch (paramHackTranslatorEvent.getAction()) {
      case 7:
        str = (String)paramHackTranslatorEvent.getData();
        file = new File(str);
        saveWorkingDir(file);
        this.gui.setTitle(getName() + getVersionString() + " - " + str);
        this.loadSourceTask.setFileName(str);
        thread = new Thread(this.loadSourceTask);
        thread.start();
        break;
      case 6:
        clearMessage();
        str = (String)paramHackTranslatorEvent.getData();
        try {
          checkDestinationFile(str);
          this.destFileName = str;
          file = new File(str);
          saveWorkingDir(file);
          this.gui.setTitle(getName() + getVersionString() + " - " + str);
          save();
        } catch (HackTranslatorException hackTranslatorException) {
          this.gui.setDestinationName("");
          this.gui.displayMessage(hackTranslatorException.getMessage(), true);
        } 
        break;
      case 1:
        clearMessage();
        if (this.sourceFileName == null) {
          this.gui.displayMessage("No source file specified", true);
          break;
        } 
        if (this.destFileName == null) {
          this.gui.displayMessage("No destination file specified", true);
          break;
        } 
        thread = new Thread(this.singleStepTask);
        thread.start();
        break;
      case 2:
        clearMessage();
        thread = new Thread(this.fastForwardTask);
        thread.start();
        break;
      case 3:
        stop();
        break;
      case 4:
        clearMessage();
        rewind();
        break;
      case 5:
        clearMessage();
        thread = new Thread(this.fullCompilationTask);
        thread.start();
        break;
    } 
  }
  
  class LoadSourceTask implements Runnable {
    private String fileName;
    
    private final HackTranslator this$0;
    
    LoadSourceTask(HackTranslator this$0) {
      this.this$0 = this$0;
    }
    
    public void run() {
      try {
        this.this$0.loadSource(this.fileName);
      } catch (HackTranslatorException hackTranslatorException) {
        this.this$0.gui.setSourceName("");
        this.this$0.gui.displayMessage(hackTranslatorException.getMessage(), true);
      } 
    }
    
    public void setFileName(String param1String) {
      this.fileName = param1String;
    }
  }
  
  class FastForwardTask implements Runnable {
    private final HackTranslator this$0;
    
    FastForwardTask(HackTranslator this$0) {
      this.this$0 = this$0;
    }
    
    public void run() {
      this.this$0.fastForward();
    }
  }
  
  class SingleStepTask implements Runnable {
    private final HackTranslator this$0;
    
    SingleStepTask(HackTranslator this$0) {
      this.this$0 = this$0;
    }
    
    public void run() {
      if (!this.this$0.singleStepLocked)
        this.this$0.singleStep(); 
    }
  }
  
  class FullCompilationTask implements Runnable {
    private final HackTranslator this$0;
    
    FullCompilationTask(HackTranslator this$0) {
      this.this$0 = this$0;
    }
    
    public void run() {
      this.this$0.gui.displayMessage("Please wait...", false);
      try {
        this.this$0.restartCompilation();
        this.this$0.fullCompilation();
      } catch (HackTranslatorException hackTranslatorException) {
        this.this$0.end(false);
        this.this$0.gui.getSource().addHighlight(this.this$0.sourcePC, true);
        this.this$0.gui.displayMessage(hackTranslatorException.getMessage(), true);
      } 
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Translators/HackTranslator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */