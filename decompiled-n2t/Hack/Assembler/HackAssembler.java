package Hack.Assembler;

import Hack.ComputerParts.TextFileEvent;
import Hack.ComputerParts.TextFileGUI;
import Hack.Translators.HackTranslator;
import Hack.Translators.HackTranslatorEvent;
import Hack.Translators.HackTranslatorException;
import Hack.Utilities.Conversions;
import Hack.Utilities.Definitions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class HackAssembler extends HackTranslator {
  private BufferedReader comparisonReader;
  
  private String comparisonFileName;
  
  private Hashtable symbolTable;
  
  private short[] comparisonProgram;
  
  private HackAssemblerTranslator translator;
  
  private short varIndex;
  
  public HackAssembler(String paramString, int paramInt, short paramShort, boolean paramBoolean) throws HackTranslatorException {
    super(paramString, paramInt, paramShort, paramBoolean);
  }
  
  public HackAssembler(HackAssemblerGUI paramHackAssemblerGUI, int paramInt, short paramShort, String paramString) throws HackTranslatorException {
    super(paramHackAssemblerGUI, paramInt, paramShort, paramString);
    paramHackAssemblerGUI.enableLoadComparison();
    paramHackAssemblerGUI.hideComparison();
  }
  
  protected String getSourceExtension() {
    return "asm";
  }
  
  protected String getDestinationExtension() {
    return "hack";
  }
  
  protected String getName() {
    return "Assembler";
  }
  
  protected void init(int paramInt, short paramShort) {
    super.init(paramInt, paramShort);
    this.translator = HackAssemblerTranslator.getInstance();
  }
  
  private void checkComparisonFile(String paramString) throws HackTranslatorException {
    if (!paramString.endsWith("." + getDestinationExtension()))
      throw new HackTranslatorException(paramString + " is not a ." + getDestinationExtension() + " file"); 
    File file = new File(paramString);
    if (!file.exists())
      throw new HackTranslatorException("File " + paramString + " does not exist"); 
  }
  
  protected void restartCompilation() {
    super.restartCompilation();
    this.varIndex = 16;
    if (this.gui != null)
      ((HackAssemblerGUI)this.gui).enableLoadComparison(); 
  }
  
  private void resetComparisonFile() throws HackTranslatorException {
    try {
      this.comparisonReader = new BufferedReader(new FileReader(this.comparisonFileName));
      if (this.gui != null) {
        TextFileGUI textFileGUI = ((HackAssemblerGUI)this.gui).getComparison();
        textFileGUI.reset();
        textFileGUI.setContents(this.comparisonFileName);
        this.comparisonProgram = new short[textFileGUI.getNumberOfLines()];
        for (byte b = 0; b < textFileGUI.getNumberOfLines(); b++) {
          if (textFileGUI.getLineAt(b).length() != 16)
            throw new HackTranslatorException("Error in file " + this.comparisonFileName + ": Line " + b + " does not contain exactly " + '\020' + " characters"); 
          try {
            this.comparisonProgram[b] = (short)Conversions.binaryToInt(textFileGUI.getLineAt(b));
          } catch (NumberFormatException numberFormatException) {
            throw new HackTranslatorException("Error in file " + this.comparisonFileName + ": Line " + b + " does not contain only 1/0 characters");
          } 
        } 
      } 
    } catch (IOException iOException) {
      throw new HackTranslatorException("Error reading from file " + this.comparisonFileName);
    } 
  }
  
  protected void initSource() throws HackTranslatorException {
    generateSymbolTable();
  }
  
  private void generateSymbolTable() throws HackTranslatorException {
    this.symbolTable = Definitions.getInstance().getAddressesTable();
    short s = 0;
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(this.sourceFileName));
      String str;
      while ((str = bufferedReader.readLine()) != null) {
        AssemblyLineTokenizer assemblyLineTokenizer = new AssemblyLineTokenizer(str);
        if (!assemblyLineTokenizer.isEnd()) {
          if (assemblyLineTokenizer.isToken("(")) {
            assemblyLineTokenizer.advance(true);
            String str1 = assemblyLineTokenizer.token();
            assemblyLineTokenizer.advance(true);
            if (!assemblyLineTokenizer.isToken(")"))
              error("')' expected"); 
            assemblyLineTokenizer.ensureEnd();
            this.symbolTable.put(str1, new Short(s));
            continue;
          } 
          if (assemblyLineTokenizer.contains("[")) {
            s = (short)(s + 2);
            continue;
          } 
          s = (short)(s + 1);
        } 
      } 
      bufferedReader.close();
    } catch (IOException iOException) {
      throw new HackTranslatorException("Error reading from file " + this.sourceFileName);
    } 
  }
  
  protected void initCompilation() throws HackTranslatorException {
    if (this.gui != null && (this.inFullCompilation || !this.compilationStarted))
      ((HackAssemblerGUI)this.gui).disableLoadComparison(); 
  }
  
  protected void successfulCompilation() throws HackTranslatorException {
    if (this.comparisonReader == null) {
      super.successfulCompilation();
    } else if (this.gui != null) {
      ((HackAssemblerGUI)this.gui).displayMessage("File compilation & comparison succeeded", false);
    } 
  }
  
  protected int[] compileLineAndCount(String paramString) throws HackTranslatorException {
    int[] arrayOfInt = super.compileLineAndCount(paramString);
    if (arrayOfInt != null && this.comparisonReader != null) {
      int i = arrayOfInt[1] - arrayOfInt[0] + 1;
      boolean bool = compare(arrayOfInt);
      if (this.inFullCompilation) {
        if (!bool && this.gui != null) {
          this.programSize = this.destPC + i - 1;
          showProgram(this.programSize);
          this.gui.getSource().addHighlight(this.sourcePC, true);
          this.gui.getDestination().addHighlight(this.destPC - 1, true);
          ((HackAssemblerGUI)this.gui).getComparison().addHighlight(this.destPC - 1, true);
          this.gui.enableRewind();
          this.gui.enableLoadSource();
        } 
      } else if (bool) {
        ((HackAssemblerGUI)this.gui).getComparison().addHighlight(this.destPC + i - 2, true);
      } else {
        this.gui.getDestination().addHighlight(this.destPC - 1, true);
        ((HackAssemblerGUI)this.gui).getComparison().addHighlight(this.destPC - 1, true);
      } 
      if (!bool)
        throw new HackTranslatorException("Comparison failure"); 
    } 
    return arrayOfInt;
  }
  
  private boolean compare(int[] paramArrayOfint) {
    boolean bool = true;
    int i = paramArrayOfint[1] - paramArrayOfint[0] + 1;
    for (byte b = 0; b < i && bool; b++)
      bool = (this.program[paramArrayOfint[0] + b] == this.comparisonProgram[paramArrayOfint[0] + b]) ? true : false; 
    return bool;
  }
  
  protected String getCodeString(short paramShort, int paramInt, boolean paramBoolean) {
    return Conversions.decimalToBinary(paramShort, 16);
  }
  
  protected void fastForward() {
    ((HackAssemblerGUI)this.gui).disableLoadComparison();
    super.fastForward();
  }
  
  protected void hidePointers() {
    super.hidePointers();
    if (this.comparisonReader != null)
      ((HackAssemblerGUI)this.gui).getComparison().clearHighlights(); 
  }
  
  protected void end(boolean paramBoolean) {
    super.end(paramBoolean);
    ((HackAssemblerGUI)this.gui).disableLoadComparison();
  }
  
  protected void stop() {
    super.stop();
    ((HackAssemblerGUI)this.gui).disableLoadComparison();
  }
  
  protected void rewind() {
    super.rewind();
    if (this.comparisonReader != null) {
      ((HackAssemblerGUI)this.gui).getComparison().clearHighlights();
      ((HackAssemblerGUI)this.gui).getComparison().hideSelect();
    } 
  }
  
  protected void compileLine(String paramString) throws HackTranslatorException {
    try {
      AssemblyLineTokenizer assemblyLineTokenizer = new AssemblyLineTokenizer(paramString);
      if (!assemblyLineTokenizer.isEnd() && !assemblyLineTokenizer.isToken("("))
        if (assemblyLineTokenizer.isToken("@")) {
          assemblyLineTokenizer.advance(true);
          boolean bool = true;
          String str = assemblyLineTokenizer.token();
          assemblyLineTokenizer.ensureEnd();
          try {
            Short.parseShort(str);
          } catch (NumberFormatException numberFormatException) {
            bool = false;
          } 
          if (!bool) {
            Short short_ = (Short)this.symbolTable.get(str);
            if (short_ == null) {
              this.varIndex = (short)(this.varIndex + 1);
              short_ = new Short(this.varIndex);
              this.symbolTable.put(str, short_);
            } 
            addCommand(this.translator.textToCode("@" + short_.shortValue()));
          } else {
            addCommand(this.translator.textToCode(paramString));
          } 
        } else {
          try {
            addCommand(this.translator.textToCode(paramString));
          } catch (AssemblerException assemblerException) {
            int i = paramString.indexOf("[");
            if (i >= 0) {
              int j = paramString.lastIndexOf("[");
              int k = paramString.indexOf("]");
              if (i != j || i > k || i + 1 == k)
                throw new AssemblerException("Illegal use of the [] notation"); 
              String str = paramString.substring(i + 1, k);
              compileLine("@" + str);
              compileLine(paramString.substring(0, i).concat(paramString.substring(k + 1)));
            } else {
              throw new AssemblerException(assemblerException.getMessage());
            } 
          } 
        }  
    } catch (IOException iOException) {
      throw new HackTranslatorException("Error reading from file " + this.sourceFileName);
    } catch (AssemblerException assemblerException) {
      throw new HackTranslatorException(assemblerException.getMessage(), this.sourcePC);
    } 
  }
  
  protected void finalizeCompilation() {}
  
  public void rowSelected(TextFileEvent paramTextFileEvent) {
    super.rowSelected(paramTextFileEvent);
    int[] arrayOfInt = rowIndexToRange(paramTextFileEvent.getRowIndex());
    if (arrayOfInt != null) {
      if (this.comparisonReader != null)
        ((HackAssemblerGUI)this.gui).getComparison().select(arrayOfInt[0], arrayOfInt[1]); 
    } else if (this.comparisonReader != null) {
      ((HackAssemblerGUI)this.gui).getComparison().hideSelect();
    } 
  }
  
  public void actionPerformed(HackTranslatorEvent paramHackTranslatorEvent) {
    String str;
    super.actionPerformed(paramHackTranslatorEvent);
    switch (paramHackTranslatorEvent.getAction()) {
      case 7:
        this.comparisonFileName = "";
        this.comparisonReader = null;
        ((HackAssemblerGUI)this.gui).setComparisonName("");
        ((HackAssemblerGUI)this.gui).hideComparison();
        break;
      case 9:
        clearMessage();
        str = (String)paramHackTranslatorEvent.getData();
        try {
          checkComparisonFile(str);
          this.comparisonFileName = str;
          saveWorkingDir(new File(str));
          resetComparisonFile();
          ((HackAssemblerGUI)this.gui).showComparison();
        } catch (HackTranslatorException hackTranslatorException) {
          this.gui.displayMessage(hackTranslatorException.getMessage(), true);
        } 
        break;
    } 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Assembler/HackAssembler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */