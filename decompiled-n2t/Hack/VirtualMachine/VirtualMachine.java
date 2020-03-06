package Hack.VirtualMachine;

public interface VirtualMachine {
  void add();
  
  void substract();
  
  void negate();
  
  void equal();
  
  void greaterThan();
  
  void lessThan();
  
  void and();
  
  void or();
  
  void not();
  
  void push(String paramString, short paramShort);
  
  void pop(String paramString, short paramShort);
  
  void label(String paramString);
  
  void goTo(String paramString);
  
  void ifGoTo(String paramString);
  
  void function(String paramString, short paramShort);
  
  void returnFromFunction();
  
  void callFunction(String paramString, short paramShort);
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/VirtualMachine/VirtualMachine.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */