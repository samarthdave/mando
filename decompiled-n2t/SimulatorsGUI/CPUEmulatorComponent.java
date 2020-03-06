package SimulatorsGUI;

import Hack.CPUEmulator.ALUGUI;
import Hack.CPUEmulator.CPUEmulatorGUI;
import Hack.CPUEmulator.KeyboardGUI;
import Hack.CPUEmulator.ROMGUI;
import Hack.CPUEmulator.ScreenGUI;
import Hack.ComputerParts.BusGUI;
import Hack.ComputerParts.PointedMemoryGUI;
import Hack.ComputerParts.RegisterGUI;
import HackGUI.BusComponent;
import HackGUI.PointedMemoryComponent;
import HackGUI.RegisterComponent;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;

public class CPUEmulatorComponent extends HackSimulatorComponent implements CPUEmulatorGUI {
  private static final int EMULATOR_WIDTH = 1018;
  
  private static final int EMULATOR_HEIGHT = 611;
  
  private RegisterComponent a;
  
  private RegisterComponent d;
  
  private RegisterComponent pc;
  
  private ScreenComponent screen = new ScreenComponent();
  
  private KeyboardComponent keyboard = new KeyboardComponent();
  
  private PointedMemoryComponent ram = new PointedMemoryComponent();
  
  private ROMComponent rom;
  
  private ALUComponent alu;
  
  private BusComponent bus;
  
  public CPUEmulatorComponent() {
    this.ram.setName("RAM");
    this.rom = new ROMComponent();
    this.rom.setName("ROM");
    this.alu = new ALUComponent();
    this.a = new RegisterComponent();
    this.d = new RegisterComponent();
    this.pc = new RegisterComponent();
    setRegistersNames();
    this.bus = new BusComponent();
    jbInit();
    this.ram.setTopLevelLocation(this);
    this.rom.setTopLevelLocation(this);
  }
  
  public void setWorkingDir(File paramFile) {
    this.rom.setWorkingDir(paramFile);
  }
  
  public void loadProgram() {
    this.rom.loadProgram();
  }
  
  private void setRegistersNames() {
    this.a.setName("A");
    this.d.setName("D");
    this.pc.setName("PC");
  }
  
  public Point getAdditionalDisplayLocation() {
    return new Point(476, 25);
  }
  
  public ALUGUI getALU() {
    return this.alu;
  }
  
  public BusGUI getBus() {
    return (BusGUI)this.bus;
  }
  
  public ScreenGUI getScreen() {
    return this.screen;
  }
  
  public KeyboardGUI getKeyboard() {
    return this.keyboard;
  }
  
  public PointedMemoryGUI getRAM() {
    return (PointedMemoryGUI)this.ram;
  }
  
  public ROMGUI getROM() {
    return this.rom;
  }
  
  public RegisterGUI getA() {
    return (RegisterGUI)this.a;
  }
  
  public RegisterGUI getD() {
    return (RegisterGUI)this.d;
  }
  
  public RegisterGUI getPC() {
    return (RegisterGUI)this.pc;
  }
  
  private void jbInit() {
    setLayout((LayoutManager)null);
    this.pc.setBounds(new Rectangle(35, 527, this.pc.getWidth(), this.pc.getHeight()));
    this.a.setBounds(new Rectangle(278, 527, this.a.getWidth(), this.a.getHeight()));
    this.d.setBounds(new Rectangle(646, 351, this.d.getWidth(), this.d.getHeight()));
    this.screen.setToolTipText("Screen");
    this.screen.setBounds(new Rectangle(476, 25, this.screen.getWidth(), this.screen.getHeight()));
    this.keyboard.setBounds(new Rectangle(476, 285, this.keyboard.getWidth(), this.keyboard.getHeight()));
    this.ram.setVisibleRows(29);
    this.ram.setBounds(new Rectangle(264, 25, this.ram.getWidth(), this.ram.getHeight()));
    this.rom.setVisibleRows(29);
    this.rom.setBounds(new Rectangle(20, 25, this.rom.getWidth(), this.rom.getHeight()));
    this.alu.setBounds(new Rectangle(551, 414, this.alu.getWidth(), this.alu.getHeight()));
    this.bus.setBounds(new Rectangle(0, 0, 1018, 611));
    add((Component)this.bus, (Object)null);
    add((Component)this.ram, (Object)null);
    add(this.screen, (Object)null);
    add((Component)this.rom, (Object)null);
    add((Component)this.a, (Object)null);
    add((Component)this.pc, (Object)null);
    add(this.keyboard, (Object)null);
    add(this.alu, (Object)null);
    add((Component)this.d, (Object)null);
    setSize(1018, 611);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/CPUEmulatorComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */