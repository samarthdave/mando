package Hack.ComputerParts;

public class Bus extends ComputerPart {
  private BusGUI gui;
  
  public Bus(BusGUI paramBusGUI) {
    super((paramBusGUI != null));
    this.gui = paramBusGUI;
  }
  
  public ComputerPartGUI getGUI() {
    return this.gui;
  }
  
  public void setAnimationSpeed(int paramInt) {
    if (this.hasGUI)
      this.gui.setSpeed(paramInt); 
  }
  
  public synchronized void send(ValueComputerPart paramValueComputerPart1, int paramInt1, ValueComputerPart paramValueComputerPart2, int paramInt2) {
    if (this.animate && paramValueComputerPart1.animate && this.hasGUI) {
      try {
        wait(100L);
      } catch (InterruptedException interruptedException) {}
      this.gui.move(((ValueComputerPartGUI)paramValueComputerPart1.getGUI()).getCoordinates(paramInt1), ((ValueComputerPartGUI)paramValueComputerPart2.getGUI()).getCoordinates(paramInt2), ((ValueComputerPartGUI)paramValueComputerPart1.getGUI()).getValueAsString(paramInt1));
    } 
    paramValueComputerPart2.setValueAt(paramInt2, paramValueComputerPart1.getValueAt(paramInt1), false);
  }
  
  public void refreshGUI() {}
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/Bus.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */