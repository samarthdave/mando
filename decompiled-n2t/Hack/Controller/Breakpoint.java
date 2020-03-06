package Hack.Controller;

public class Breakpoint {
  private String varName;
  
  private String value;
  
  private boolean reached;
  
  public Breakpoint(String paramString1, String paramString2) {
    this.varName = paramString1;
    this.value = paramString2;
    this.reached = false;
  }
  
  public String getVarName() {
    return this.varName;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void off() {
    this.reached = false;
  }
  
  public void on() {
    this.reached = true;
  }
  
  public boolean isReached() {
    return this.reached;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/Breakpoint.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */