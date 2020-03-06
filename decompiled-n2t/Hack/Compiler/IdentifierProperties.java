package Hack.Compiler;

public class IdentifierProperties {
  private String type;
  
  private short index;
  
  public IdentifierProperties(String paramString, short paramShort) {
    this.type = paramString;
    this.index = paramShort;
  }
  
  public String getType() {
    return this.type;
  }
  
  public short getIndex() {
    return this.index;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Compiler/IdentifierProperties.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */