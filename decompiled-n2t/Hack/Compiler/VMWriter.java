package Hack.Compiler;

import Hack.VirtualMachine.VirtualMachine;
import java.io.PrintWriter;

public class VMWriter implements VirtualMachine {
  private PrintWriter writer;
  
  public VMWriter(PrintWriter paramPrintWriter) {
    this.writer = paramPrintWriter;
  }
  
  public void close() {
    this.writer.flush();
    this.writer.close();
  }
  
  public void add() {
    this.writer.println("add");
  }
  
  public void substract() {
    this.writer.println("sub");
  }
  
  public void negate() {
    this.writer.println("neg");
  }
  
  public void equal() {
    this.writer.println("eq");
  }
  
  public void greaterThan() {
    this.writer.println("gt");
  }
  
  public void lessThan() {
    this.writer.println("lt");
  }
  
  public void and() {
    this.writer.println("and");
  }
  
  public void or() {
    this.writer.println("or");
  }
  
  public void not() {
    this.writer.println("not");
  }
  
  public void push(String paramString, short paramShort) {
    this.writer.println("push " + paramString + " " + paramShort);
  }
  
  public void pop(String paramString, short paramShort) {
    this.writer.println("pop " + paramString + " " + paramShort);
  }
  
  public void label(String paramString) {
    this.writer.println("label " + paramString);
  }
  
  public void goTo(String paramString) {
    this.writer.println("goto " + paramString);
  }
  
  public void ifGoTo(String paramString) {
    this.writer.println("if-goto " + paramString);
  }
  
  public void function(String paramString, short paramShort) {
    this.writer.println("function " + paramString + " " + paramShort);
  }
  
  public void returnFromFunction() {
    this.writer.println("return");
  }
  
  public void callFunction(String paramString, short paramShort) {
    this.writer.println("call " + paramString + " " + paramShort);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Compiler/VMWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */