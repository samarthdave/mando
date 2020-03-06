package Hack.ComputerParts;

public class Register extends InteractiveValueComputerPart implements ComputerPartEventListener {
  protected short value;
  
  protected RegisterGUI gui;
  
  public Register(RegisterGUI paramRegisterGUI, short paramShort1, short paramShort2) {
    super((paramRegisterGUI != null), paramShort1, paramShort2);
    init(paramRegisterGUI);
  }
  
  public Register(RegisterGUI paramRegisterGUI) {
    super((paramRegisterGUI != null));
    init(paramRegisterGUI);
  }
  
  private void init(RegisterGUI paramRegisterGUI) {
    this.gui = paramRegisterGUI;
    if (this.hasGUI) {
      paramRegisterGUI.addListener(this);
      paramRegisterGUI.addErrorListener(this);
    } 
  }
  
  public short get() {
    return getValueAt(0);
  }
  
  public void store(short paramShort) {
    setValueAt(0, paramShort, false);
  }
  
  public short getValueAt(int paramInt) {
    return this.value;
  }
  
  public void doSetValueAt(int paramInt, short paramShort) {
    this.value = paramShort;
  }
  
  public void reset() {
    super.reset();
    this.value = this.nullValue;
  }
  
  public ComputerPartGUI getGUI() {
    return this.gui;
  }
  
  public void refreshGUI() {
    super.refreshGUI();
    if (this.displayChanges)
      quietUpdateGUI(0, this.value); 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/ComputerParts/Register.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */