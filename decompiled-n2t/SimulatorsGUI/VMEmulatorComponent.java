package SimulatorsGUI;

import Hack.CPUEmulator.KeyboardGUI;
import Hack.CPUEmulator.ScreenGUI;
import Hack.ComputerParts.BusGUI;
import Hack.ComputerParts.LabeledPointedMemoryGUI;
import Hack.ComputerParts.MemorySegmentGUI;
import Hack.ComputerParts.PointedMemorySegmentGUI;
import Hack.VMEmulator.CalculatorGUI;
import Hack.VMEmulator.CallStackGUI;
import Hack.VMEmulator.VMEmulatorGUI;
import Hack.VMEmulator.VMProgramGUI;
import HackGUI.AbsolutePointedMemorySegmentComponent;
import HackGUI.BusComponent;
import HackGUI.LabeledMemoryComponent;
import HackGUI.MemoryChangeListener;
import HackGUI.MemoryComponent;
import HackGUI.TrimmedValuesOnlyAbsoluteMemorySegmentComponent;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.BorderFactory;

public class VMEmulatorComponent extends HackSimulatorComponent implements VMEmulatorGUI {
  private static final int WIDTH = 1018;
  
  private static final int HEIGHT = 611;
  
  private KeyboardComponent keyboard = new KeyboardComponent();
  
  private ScreenComponent screen = new ScreenComponent();
  
  private CallStackComponent callStack;
  
  private ProgramComponent program;
  
  private LabeledMemoryComponent ram = new LabeledMemoryComponent();
  
  private AbsolutePointedMemorySegmentComponent stack;
  
  private MemorySegmentsComponent segments;
  
  private BusComponent bus = new BusComponent();
  
  private StackCalculator calculator;
  
  private TrimmedValuesOnlyAbsoluteMemorySegmentComponent workingStack;
  
  public VMEmulatorComponent() {
    this.ram.setName("RAM");
    this.callStack = new CallStackComponent();
    this.program = new ProgramComponent();
    this.segments = new MemorySegmentsComponent();
    this.workingStack = new TrimmedValuesOnlyAbsoluteMemorySegmentComponent();
    this.workingStack.setSegmentName("Stack");
    this.stack = new AbsolutePointedMemorySegmentComponent();
    this.calculator = new StackCalculator();
    setSegmentsRam();
    setStackName();
    jbInit();
    this.ram.setTopLevelLocation(this);
    this.segments.getStaticSegment().setTopLevelLocation(this);
    this.segments.getLocalSegment().setTopLevelLocation(this);
    this.segments.getArgSegment().setTopLevelLocation(this);
    this.segments.getThisSegment().setTopLevelLocation(this);
    this.segments.getThatSegment().setTopLevelLocation(this);
    this.segments.getTempSegment().setTopLevelLocation(this);
    this.stack.setTopLevelLocation(this);
    this.workingStack.setTopLevelLocation(this);
  }
  
  public void setWorkingDir(File paramFile) {
    this.program.setWorkingDir(paramFile);
  }
  
  public void loadProgram() {
    this.program.loadProgram();
  }
  
  public CalculatorGUI getCalculator() {
    return this.calculator;
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
  
  public LabeledPointedMemoryGUI getRAM() {
    return (LabeledPointedMemoryGUI)this.ram;
  }
  
  public VMProgramGUI getProgram() {
    return this.program;
  }
  
  public CallStackGUI getCallStack() {
    return this.callStack;
  }
  
  public PointedMemorySegmentGUI getStack() {
    return (PointedMemorySegmentGUI)this.stack;
  }
  
  public MemorySegmentGUI getStaticSegment() {
    return (MemorySegmentGUI)this.segments.getStaticSegment();
  }
  
  public MemorySegmentGUI getLocalSegment() {
    return (MemorySegmentGUI)this.segments.getLocalSegment();
  }
  
  public MemorySegmentGUI getArgSegment() {
    return (MemorySegmentGUI)this.segments.getArgSegment();
  }
  
  public MemorySegmentGUI getThisSegment() {
    return (MemorySegmentGUI)this.segments.getThisSegment();
  }
  
  public MemorySegmentGUI getThatSegment() {
    return (MemorySegmentGUI)this.segments.getThatSegment();
  }
  
  public MemorySegmentGUI getTempSegment() {
    return (MemorySegmentGUI)this.segments.getTempSegment();
  }
  
  public PointedMemorySegmentGUI getWorkingStack() {
    return (PointedMemorySegmentGUI)this.workingStack;
  }
  
  public Point getAdditionalDisplayLocation() {
    return new Point(492, 10);
  }
  
  private void setSegmentsRam() {
    this.segments.getStaticSegment().setMemoryComponent((MemoryComponent)this.ram);
    this.segments.getLocalSegment().setMemoryComponent((MemoryComponent)this.ram);
    this.segments.getArgSegment().setMemoryComponent((MemoryComponent)this.ram);
    this.segments.getThisSegment().setMemoryComponent((MemoryComponent)this.ram);
    this.segments.getThatSegment().setMemoryComponent((MemoryComponent)this.ram);
    this.segments.getTempSegment().setMemoryComponent((MemoryComponent)this.ram);
    this.stack.setMemoryComponent((MemoryComponent)this.ram);
    this.workingStack.setMemoryComponent((MemoryComponent)this.ram);
    this.ram.addChangeListener((MemoryChangeListener)this.segments.getStaticSegment());
    this.ram.addChangeListener((MemoryChangeListener)this.segments.getLocalSegment());
    this.ram.addChangeListener((MemoryChangeListener)this.segments.getArgSegment());
    this.ram.addChangeListener((MemoryChangeListener)this.segments.getThisSegment());
    this.ram.addChangeListener((MemoryChangeListener)this.segments.getThatSegment());
    this.ram.addChangeListener((MemoryChangeListener)this.segments.getTempSegment());
    this.ram.addChangeListener((MemoryChangeListener)this.stack);
    this.ram.addChangeListener((MemoryChangeListener)this.workingStack);
  }
  
  private void setStackName() {
    this.stack.setSegmentName("Global Stack");
  }
  
  private void jbInit() {
    setLayout((LayoutManager)null);
    this.keyboard.setBounds(492, 270, this.keyboard.getWidth(), this.keyboard.getHeight());
    this.screen.setBounds(492, 10, this.screen.getWidth(), this.screen.getHeight());
    this.program.setVisibleRows(15);
    this.program.setBounds(new Rectangle(6, 10, this.program.getWidth(), this.program.getHeight()));
    this.ram.setVisibleRows(15);
    this.ram.setBounds(new Rectangle(766, 327, this.ram.getWidth(), this.ram.getHeight()));
    this.stack.setVisibleRows(15);
    this.stack.setBounds(new Rectangle(561, 327, this.stack.getWidth(), this.stack.getHeight()));
    this.segments.getSplitPane().setBounds(new Rectangle(289, 10, this.segments.getSplitPane().getWidth(), this.segments.getSplitPane().getHeight()));
    this.bus.setBounds(new Rectangle(0, 0, 1018, 611));
    this.calculator.setBorder(BorderFactory.createLoweredBevelBorder());
    this.calculator.setBounds(new Rectangle(137, 331, 148, 103));
    this.calculator.setVisible(false);
    this.workingStack.setVisibleRows(7);
    this.workingStack.setBounds(new Rectangle(8, 304, this.workingStack.getWidth(), this.workingStack.getHeight()));
    this.callStack.setVisibleRows(7);
    this.callStack.setBounds(new Rectangle(8, 458, this.callStack.getWidth(), this.callStack.getHeight()));
    add((Component)this.bus, (Object)null);
    add(this.screen, (Object)null);
    add(this.keyboard, (Object)null);
    add(this.program, (Object)null);
    add((Component)this.workingStack, (Object)null);
    add(this.callStack, (Object)null);
    add(this.calculator, (Object)null);
    add((Component)this.stack, (Object)null);
    add((Component)this.ram, (Object)null);
    add(this.callStack, (Object)null);
    add(this.segments.getSplitPane(), (Object)null);
    setSize(1018, 611);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/VMEmulatorComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */