package Hack.Compiler;

import Hack.Utilities.HackFileFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

public class JackCompiler {
  private CompilationEngine compilationEngine = new CompilationEngine();
  
  public boolean compileFile(File paramFile) {
    String str1 = paramFile.getName().substring(0, paramFile.getName().indexOf('.'));
    String str2 = paramFile.getParent();
    try {
      JackTokenizer jackTokenizer = new JackTokenizer(new FileReader(paramFile.getPath()));
      File file = new File(str2 + "/" + str1 + ".vm");
      VMWriter vMWriter = new VMWriter(new PrintWriter(new FileWriter(file)));
      if (this.compilationEngine.compileClass(jackTokenizer, vMWriter, str1, paramFile.getName()))
        return true; 
      file.delete();
      return false;
    } catch (IOException iOException) {
      System.err.println("Error reading/writing while compiling " + paramFile);
      System.exit(-1);
      return false;
    } 
  }
  
  public boolean compileDirectory(String paramString) {
    boolean bool = true;
    File file = new File(paramString);
    File[] arrayOfFile = file.listFiles((FilenameFilter)new HackFileFilter(".jack"));
    for (byte b = 0; b < arrayOfFile.length; b++)
      bool &= compileFile(arrayOfFile[b]); 
    return bool;
  }
  
  public boolean verify() {
    return this.compilationEngine.verifySubroutineCalls();
  }
  
  public static void main(String[] paramArrayOfString) {
    boolean bool;
    if (paramArrayOfString.length != 1) {
      try {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("bin/help/compiler.txt")));
        String str;
        while ((str = bufferedReader.readLine()) != null)
          System.out.println(str); 
        System.out.println("");
      } catch (FileNotFoundException fileNotFoundException) {
      
      } catch (IOException iOException) {}
      System.out.println("Usage: java JackCompiler <Jack-dir or Jack-file-name>");
      System.exit(-1);
    } 
    JackCompiler jackCompiler = new JackCompiler();
    File file = new File(paramArrayOfString[0]);
    if (!file.exists()) {
      System.err.println("Could not find file or directory: " + paramArrayOfString[0]);
      System.exit(-1);
    } 
    if (file.isDirectory()) {
      bool = jackCompiler.compileDirectory(paramArrayOfString[0]);
    } else {
      bool = jackCompiler.compileFile(file);
    } 
    bool &= jackCompiler.verify();
    System.exit(bool ? 0 : 1);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Compiler/JackCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */