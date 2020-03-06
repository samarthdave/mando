package Hack.Assembler;

import Hack.Translators.HackTranslatorException;
import Hack.Utilities.Conversions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class HackAssemblerTranslator {
  public static final short NOP = -32768;
  
  private static final Short ZERO = new Short((short)-5504);
  
  private static final Short ONE = new Short((short)-4160);
  
  private static final Short MINUS_ONE = new Short((short)-4480);
  
  private static final Short EXP_D = new Short((short)-7424);
  
  private static final Short NOT_D = new Short((short)-7360);
  
  private static final Short EXP_M = new Short((short)-1024);
  
  private static final Short EXP_A = new Short((short)-5120);
  
  private static final Short NOT_M = new Short((short)-960);
  
  private static final Short NOT_A = new Short((short)-5056);
  
  private static final Short MINUS_D = new Short((short)-7232);
  
  private static final Short MINUS_M = new Short((short)-832);
  
  private static final Short MINUS_A = new Short((short)-4928);
  
  private static final Short D_PLUS_ONE = new Short((short)-6208);
  
  private static final Short M_PLUS_ONE = new Short((short)-576);
  
  private static final Short A_PLUS_ONE = new Short((short)-4672);
  
  private static final Short D_MINUS_ONE = new Short((short)-7296);
  
  private static final Short M_MINUS_ONE = new Short((short)-896);
  
  private static final Short A_MINUS_ONE = new Short((short)-4992);
  
  private static final Short D_PLUS_M = new Short((short)-3968);
  
  private static final Short D_PLUS_A = new Short((short)-8064);
  
  private static final Short D_MINUS_M = new Short((short)-2880);
  
  private static final Short D_MINUS_A = new Short((short)-6976);
  
  private static final Short M_MINUS_D = new Short((short)-3648);
  
  private static final Short A_MINUS_D = new Short((short)-7744);
  
  private static final Short D_AND_M = new Short((short)-4096);
  
  private static final Short D_AND_A = new Short((short)-8192);
  
  private static final Short D_OR_M = new Short((short)-2752);
  
  private static final Short D_OR_A = new Short((short)-6848);
  
  private static final Short A = new Short((short)32);
  
  private static final Short M = new Short((short)8);
  
  private static final Short D = new Short((short)16);
  
  private static final Short AM = new Short((short)40);
  
  private static final Short AD = new Short((short)48);
  
  private static final Short MD = new Short((short)24);
  
  private static final Short AMD = new Short((short)56);
  
  private static final Short JMP = new Short((short)7);
  
  private static final Short JMP_LESS_THEN = new Short((short)4);
  
  private static final Short JMP_EQUAL = new Short((short)2);
  
  private static final Short JMP_GREATER_THEN = new Short((short)1);
  
  private static final Short JMP_NOT_EQUAL = new Short((short)5);
  
  private static final Short JMP_LESS_EQUAL = new Short((short)6);
  
  private static final Short JMP_GREATER_EQUAL = new Short((short)3);
  
  private static HackAssemblerTranslator instance;
  
  private Hashtable expToCode;
  
  private Hashtable destToCode;
  
  private Hashtable jmpToCode;
  
  private Hashtable expToText;
  
  private Hashtable destToText;
  
  private Hashtable jmpToText;
  
  private HackAssemblerTranslator() {
    instance = this;
    initExp();
    initDest();
    initJmp();
  }
  
  public static HackAssemblerTranslator getInstance() {
    if (instance == null)
      new HackAssemblerTranslator(); 
    return instance;
  }
  
  public short getExpByText(String paramString) throws AssemblerException {
    Short short_ = (Short)this.expToCode.get(paramString);
    if (short_ == null)
      throw new AssemblerException("Illegal exp: " + paramString); 
    return short_.shortValue();
  }
  
  public String getExpByCode(short paramShort) throws AssemblerException {
    String str = (String)this.expToText.get(new Short(paramShort));
    if (str == null)
      throw new AssemblerException("Illegal exp: " + paramShort); 
    return str;
  }
  
  public short getDestByText(String paramString) throws AssemblerException {
    Short short_ = (Short)this.destToCode.get(paramString);
    if (short_ == null)
      throw new AssemblerException("Illegal dest: " + paramString); 
    return short_.shortValue();
  }
  
  public String getDestByCode(short paramShort) throws AssemblerException {
    String str = (String)this.destToText.get(new Short(paramShort));
    if (str == null)
      throw new AssemblerException("Illegal dest: " + paramShort); 
    return str;
  }
  
  public short getJmpByText(String paramString) throws AssemblerException {
    Short short_ = (Short)this.jmpToCode.get(paramString);
    if (short_ == null)
      throw new AssemblerException("Illegal jmp: " + paramString); 
    return short_.shortValue();
  }
  
  public String getJmpByCode(short paramShort) throws AssemblerException {
    String str = (String)this.jmpToText.get(new Short(paramShort));
    if (str == null)
      throw new AssemblerException("Illegal jmp: " + paramShort); 
    return str;
  }
  
  public short textToCode(String paramString) throws AssemblerException {
    short s1 = 0;
    short s2 = 0;
    short s3 = 0;
    short s4 = 0;
    try {
      AssemblyLineTokenizer assemblyLineTokenizer = new AssemblyLineTokenizer(paramString);
      if (assemblyLineTokenizer.isToken("@")) {
        assemblyLineTokenizer.advance(true);
        try {
          s1 = Short.parseShort(assemblyLineTokenizer.token());
        } catch (NumberFormatException numberFormatException) {
          throw new AssemblerException("A numeric value is expected");
        } 
      } else {
        Short short_;
        String str = assemblyLineTokenizer.token();
        assemblyLineTokenizer.advance(false);
        if (assemblyLineTokenizer.isToken("=")) {
          short_ = (Short)this.destToCode.get(str);
          if (short_ == null)
            throw new AssemblerException("Destination expected"); 
          s4 = short_.shortValue();
          assemblyLineTokenizer.advance(true);
        } 
        if (!str.equals("=") && s4 == 0) {
          short_ = (Short)this.expToCode.get(str);
        } else {
          short_ = (Short)this.expToCode.get(assemblyLineTokenizer.token());
        } 
        if (short_ == null)
          throw new AssemblerException("Expression expected"); 
        s2 = short_.shortValue();
        assemblyLineTokenizer.advance(false);
        if (assemblyLineTokenizer.isToken(";"))
          assemblyLineTokenizer.advance(false); 
        if (!assemblyLineTokenizer.isEnd()) {
          Short short_1 = (Short)this.jmpToCode.get(assemblyLineTokenizer.token());
          if (short_1 == null)
            throw new AssemblerException("Jump directive expected"); 
          s3 = short_1.shortValue();
          assemblyLineTokenizer.ensureEnd();
        } 
        s1 = (short)(s4 + s2 + s3);
      } 
    } catch (IOException iOException) {
      throw new AssemblerException("Error while parsing assembly line");
    } catch (HackTranslatorException hackTranslatorException) {
      throw new AssemblerException(hackTranslatorException.getMessage());
    } 
    return s1;
  }
  
  public String codeToText(short paramShort) throws AssemblerException {
    StringBuffer stringBuffer = new StringBuffer();
    if (paramShort != Short.MIN_VALUE)
      if ((paramShort & 0x8000) == 0) {
        stringBuffer.append('@');
        stringBuffer.append(paramShort);
      } else {
        short s1 = (short)(paramShort & 0xFFC0);
        short s2 = (short)(paramShort & 0x38);
        short s3 = (short)(paramShort & 0x7);
        String str = getExpByCode(s1);
        if (!str.equals("")) {
          if (s2 != 0) {
            stringBuffer.append(getDestByCode(s2));
            stringBuffer.append('=');
          } 
          stringBuffer.append(str);
          if (s3 != 0) {
            stringBuffer.append(';');
            stringBuffer.append(getJmpByCode(s3));
          } 
        } 
      }  
    return stringBuffer.toString();
  }
  
  public static short[] loadProgram(String paramString, int paramInt, short paramShort) throws AssemblerException {
    short[] arrayOfShort = null;
    File file = new File(paramString);
    if (!file.exists())
      throw new AssemblerException(paramString + " doesn't exist"); 
    if (paramString.endsWith(".hack")) {
      arrayOfShort = new short[paramInt];
      for (byte b = 0; b < paramInt; b++)
        arrayOfShort[b] = paramShort; 
      try {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(paramString));
        byte b1 = 0;
        String str;
        while ((str = bufferedReader.readLine()) != null) {
          short s = 0;
          if (b1 >= paramInt)
            throw new AssemblerException("Program too large"); 
          try {
            s = (short)Conversions.binaryToInt(str);
          } catch (NumberFormatException numberFormatException) {
            throw new AssemblerException("Illegal character");
          } 
          arrayOfShort[b1++] = s;
        } 
        bufferedReader.close();
      } catch (IOException iOException) {
        throw new AssemblerException("IO error while reading " + paramString);
      } 
    } else if (paramString.endsWith(".asm")) {
      try {
        HackAssembler hackAssembler = new HackAssembler(paramString, paramInt, paramShort, false);
        arrayOfShort = hackAssembler.getProgram();
      } catch (HackTranslatorException hackTranslatorException) {
        throw new AssemblerException(hackTranslatorException.getMessage());
      } 
    } else {
      throw new AssemblerException(paramString + " is not a .hack or .asm file");
    } 
    return arrayOfShort;
  }
  
  private void initExp() {
    this.expToCode = new Hashtable();
    this.expToText = new Hashtable();
    this.expToCode.put("0", ZERO);
    this.expToCode.put("1", ONE);
    this.expToCode.put("-1", MINUS_ONE);
    this.expToCode.put("D", EXP_D);
    this.expToCode.put("!D", NOT_D);
    this.expToCode.put("NOTD", NOT_D);
    this.expToCode.put("M", EXP_M);
    this.expToCode.put("A", EXP_A);
    this.expToCode.put("!M", NOT_M);
    this.expToCode.put("NOTM", NOT_M);
    this.expToCode.put("!A", NOT_A);
    this.expToCode.put("NOTA", NOT_A);
    this.expToCode.put("-D", MINUS_D);
    this.expToCode.put("-M", MINUS_M);
    this.expToCode.put("-A", MINUS_A);
    this.expToCode.put("D+1", D_PLUS_ONE);
    this.expToCode.put("M+1", M_PLUS_ONE);
    this.expToCode.put("A+1", A_PLUS_ONE);
    this.expToCode.put("D-1", D_MINUS_ONE);
    this.expToCode.put("M-1", M_MINUS_ONE);
    this.expToCode.put("A-1", A_MINUS_ONE);
    this.expToCode.put("D+M", D_PLUS_M);
    this.expToCode.put("M+D", D_PLUS_M);
    this.expToCode.put("D+A", D_PLUS_A);
    this.expToCode.put("A+D", D_PLUS_A);
    this.expToCode.put("D-M", D_MINUS_M);
    this.expToCode.put("D-A", D_MINUS_A);
    this.expToCode.put("M-D", M_MINUS_D);
    this.expToCode.put("A-D", A_MINUS_D);
    this.expToCode.put("D&M", D_AND_M);
    this.expToCode.put("M&D", D_AND_M);
    this.expToCode.put("D&A", D_AND_A);
    this.expToCode.put("A&D", D_AND_A);
    this.expToCode.put("D|M", D_OR_M);
    this.expToCode.put("M|D", D_OR_M);
    this.expToCode.put("D|A", D_OR_A);
    this.expToCode.put("A|D", D_OR_A);
    this.expToText.put(ZERO, "0");
    this.expToText.put(ONE, "1");
    this.expToText.put(MINUS_ONE, "-1");
    this.expToText.put(EXP_D, "D");
    this.expToText.put(NOT_D, "!D");
    this.expToText.put(EXP_M, "M");
    this.expToText.put(EXP_A, "A");
    this.expToText.put(NOT_M, "!M");
    this.expToText.put(NOT_A, "!A");
    this.expToText.put(MINUS_D, "-D");
    this.expToText.put(MINUS_M, "-M");
    this.expToText.put(MINUS_A, "-A");
    this.expToText.put(D_PLUS_ONE, "D+1");
    this.expToText.put(M_PLUS_ONE, "M+1");
    this.expToText.put(A_PLUS_ONE, "A+1");
    this.expToText.put(D_MINUS_ONE, "D-1");
    this.expToText.put(M_MINUS_ONE, "M-1");
    this.expToText.put(A_MINUS_ONE, "A-1");
    this.expToText.put(D_PLUS_M, "D+M");
    this.expToText.put(D_PLUS_A, "D+A");
    this.expToText.put(D_MINUS_M, "D-M");
    this.expToText.put(D_MINUS_A, "D-A");
    this.expToText.put(M_MINUS_D, "M-D");
    this.expToText.put(A_MINUS_D, "A-D");
    this.expToText.put(D_AND_M, "D&M");
    this.expToText.put(D_AND_A, "D&A");
    this.expToText.put(D_OR_M, "D|M");
    this.expToText.put(D_OR_A, "D|A");
  }
  
  private void initDest() {
    this.destToCode = new Hashtable();
    this.destToText = new Hashtable();
    this.destToCode.put("A", A);
    this.destToCode.put("M", M);
    this.destToCode.put("D", D);
    this.destToCode.put("AM", AM);
    this.destToCode.put("AD", AD);
    this.destToCode.put("MD", MD);
    this.destToCode.put("AMD", AMD);
    this.destToText.put(A, "A");
    this.destToText.put(M, "M");
    this.destToText.put(D, "D");
    this.destToText.put(AM, "AM");
    this.destToText.put(AD, "AD");
    this.destToText.put(MD, "MD");
    this.destToText.put(AMD, "AMD");
  }
  
  private void initJmp() {
    this.jmpToCode = new Hashtable();
    this.jmpToText = new Hashtable();
    this.jmpToCode.put("JMP", JMP);
    this.jmpToCode.put("JLT", JMP_LESS_THEN);
    this.jmpToCode.put("JEQ", JMP_EQUAL);
    this.jmpToCode.put("JGT", JMP_GREATER_THEN);
    this.jmpToCode.put("JNE", JMP_NOT_EQUAL);
    this.jmpToCode.put("JLE", JMP_LESS_EQUAL);
    this.jmpToCode.put("JGE", JMP_GREATER_EQUAL);
    this.jmpToText.put(JMP, "JMP");
    this.jmpToText.put(JMP_LESS_THEN, "JLT");
    this.jmpToText.put(JMP_EQUAL, "JEQ");
    this.jmpToText.put(JMP_GREATER_THEN, "JGT");
    this.jmpToText.put(JMP_NOT_EQUAL, "JNE");
    this.jmpToText.put(JMP_LESS_EQUAL, "JLE");
    this.jmpToText.put(JMP_GREATER_EQUAL, "JGE");
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Assembler/HackAssemblerTranslator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */