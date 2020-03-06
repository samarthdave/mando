package Hack.ComputerParts;

public abstract class ComputerPart {
  protected boolean displayChanges = true;
  
  protected boolean animate;
  
  protected boolean hasGUI;
  
  public ComputerPart(boolean paramBoolean) {
    this.hasGUI = paramBoolean;
    this.displayChanges = paramBoolean;
    this.animate = false;
  }
  
  public void setDisplayChanges(boolean paramBoolean) {
    this.displayChanges = (paramBoolean && this.hasGUI);
  }
  
  public void setAnimate(boolean paramBoolean) {
    this.animate = (paramBoolean && this.hasGUI);
  }
  
  public void reset() {
    if (this.hasGUI)
      getGUI().reset(); 
  }
  
  public abstract ComputerPartGUI getGUI();
  
  public abstract void refreshGUI();
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/ComputerPart.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */