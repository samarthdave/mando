package SimulatorsGUI;

import Hack.HardwareSimulator.HardwareSimulatorControllerGUI;
import HackGUI.ControllerComponent;
import HackGUI.FilesTypeListener;
import HackGUI.MouseOverJButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

public class HardwareSimulatorControllerComponent extends ControllerComponent implements HardwareSimulatorControllerGUI {
  private MouseOverJButton loadChipButton;
  
  private MouseOverJButton tickTockButton;
  
  private MouseOverJButton evalButton;
  
  private ImageIcon loadChipIcon;
  
  private ImageIcon tickTockIcon;
  
  private ImageIcon evalIcon;
  
  private ChipLoaderFileChooser settingsWindow;
  
  private JMenuItem loadChipMenuItem;
  
  private JMenuItem evalMenuItem;
  
  private JMenuItem tickTockMenuItem;
  
  private JFileChooser chipFileChooser;
  
  public HardwareSimulatorControllerComponent() {
    this.scriptComponent.updateSize(516, 592);
    this.outputComponent.updateSize(516, 592);
    this.comparisonComponent.updateSize(516, 592);
  }
  
  public void disableEval() {
    this.evalButton.setEnabled(false);
    this.evalMenuItem.setEnabled(false);
  }
  
  public void enableEval() {
    this.evalButton.setEnabled(true);
    this.evalMenuItem.setEnabled(true);
  }
  
  public void disableTickTock() {
    this.tickTockButton.setEnabled(false);
    this.tickTockMenuItem.setEnabled(false);
  }
  
  public void enableTickTock() {
    this.tickTockButton.setEnabled(true);
    this.tickTockMenuItem.setEnabled(true);
  }
  
  protected void init() {
    super.init();
    this.settingsWindow = new ChipLoaderFileChooser();
    this.settingsWindow.addListener((FilesTypeListener)this);
    this.chipFileChooser = new JFileChooser();
    this.chipFileChooser.setFileFilter(new HDLFileFilter());
    initLoadChipButton();
    initTickTockButton();
    initEvalButton();
  }
  
  public void setWorkingDir(File paramFile) {
    super.setWorkingDir(paramFile);
    this.chipFileChooser.setCurrentDirectory(paramFile);
  }
  
  protected void arrangeToolBar() {
    this.toolBar.setSize(1016, 55);
    this.toolBar.add((Component)this.loadChipButton);
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add((Component)this.singleStepButton);
    this.toolBar.add((Component)this.ffwdButton);
    this.toolBar.add((Component)this.stopButton);
    this.toolBar.add((Component)this.rewindButton);
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add((Component)this.evalButton);
    this.toolBar.add((Component)this.tickTockButton);
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add((Component)this.scriptButton);
    this.toolBar.add((Component)this.breakButton);
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add(this.speedSlider);
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add((Component)this.animationCombo);
    this.toolBar.add((Component)this.formatCombo);
    this.toolBar.add((Component)this.additionalDisplayCombo);
  }
  
  protected void arrangeMenu() {
    super.arrangeMenu();
    this.fileMenu.removeAll();
    this.loadChipMenuItem = new JMenuItem("Load Chip", 76);
    this.loadChipMenuItem.addActionListener(new ActionListener(this) {
          private final HardwareSimulatorControllerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.loadChipMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.fileMenu.add(this.loadChipMenuItem);
    this.fileMenu.add(this.scriptMenuItem);
    this.fileMenu.addSeparator();
    this.fileMenu.add(this.exitMenuItem);
    this.runMenu.removeAll();
    this.runMenu.add(this.singleStepMenuItem);
    this.runMenu.add(this.ffwdMenuItem);
    this.runMenu.add(this.stopMenuItem);
    this.runMenu.add(this.rewindMenuItem);
    this.runMenu.addSeparator();
    this.evalMenuItem = new JMenuItem("Eval", 69);
    this.evalMenuItem.addActionListener(new ActionListener(this) {
          private final HardwareSimulatorControllerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.evalMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.runMenu.add(this.evalMenuItem);
    this.tickTockMenuItem = new JMenuItem("Tick Tock", 67);
    this.tickTockMenuItem.addActionListener(new ActionListener(this) {
          private final HardwareSimulatorControllerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.tickTockMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.runMenu.add(this.tickTockMenuItem);
    this.runMenu.addSeparator();
    this.runMenu.add(this.breakpointsMenuItem);
  }
  
  private void initLoadChipButton() {
    this.loadChipIcon = new ImageIcon("bin/images/chip.gif");
    this.loadChipButton = new MouseOverJButton();
    this.loadChipButton.setMaximumSize(new Dimension(39, 39));
    this.loadChipButton.setMinimumSize(new Dimension(39, 39));
    this.loadChipButton.setPreferredSize(new Dimension(39, 39));
    this.loadChipButton.setToolTipText("Load Chip");
    this.loadChipButton.setIcon(this.loadChipIcon);
    this.loadChipButton.addActionListener(new ActionListener(this) {
          private final HardwareSimulatorControllerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.loadChipButton_actionPerformed(param1ActionEvent);
          }
        });
  }
  
  private void initTickTockButton() {
    this.tickTockIcon = new ImageIcon("bin/images/clock2.gif");
    this.tickTockButton = new MouseOverJButton();
    this.tickTockButton.setMaximumSize(new Dimension(39, 39));
    this.tickTockButton.setMinimumSize(new Dimension(39, 39));
    this.tickTockButton.setPreferredSize(new Dimension(39, 39));
    this.tickTockButton.setToolTipText("Tick Tock");
    this.tickTockButton.setIcon(this.tickTockIcon);
    this.tickTockButton.addActionListener(new ActionListener(this) {
          private final HardwareSimulatorControllerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.tickTockButton_actionPerformed(param1ActionEvent);
          }
        });
  }
  
  private void initEvalButton() {
    this.evalIcon = new ImageIcon("bin/images/calculator2.gif");
    this.evalButton = new MouseOverJButton();
    this.evalButton.setMaximumSize(new Dimension(39, 39));
    this.evalButton.setMinimumSize(new Dimension(39, 39));
    this.evalButton.setPreferredSize(new Dimension(39, 39));
    this.evalButton.setToolTipText("Eval");
    this.evalButton.setIcon(this.evalIcon);
    this.evalButton.addActionListener(new ActionListener(this) {
          private final HardwareSimulatorControllerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.evalButton_actionPerformed(param1ActionEvent);
          }
        });
  }
  
  private void loadChipPressed() {
    int i = this.chipFileChooser.showDialog((Component)this, "Load Chip");
    if (i == 0)
      notifyControllerListeners((byte)102, this.chipFileChooser.getSelectedFile().getAbsoluteFile()); 
  }
  
  public void loadChipButton_actionPerformed(ActionEvent paramActionEvent) {
    loadChipPressed();
  }
  
  public void tickTockButton_actionPerformed(ActionEvent paramActionEvent) {
    notifyControllerListeners((byte)100, null);
  }
  
  public void evalButton_actionPerformed(ActionEvent paramActionEvent) {
    notifyControllerListeners((byte)101, null);
  }
  
  public void loadChipMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    loadChipPressed();
  }
  
  public void evalMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    notifyControllerListeners((byte)101, null);
  }
  
  public void tickTockMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    notifyControllerListeners((byte)100, null);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/HardwareSimulatorControllerComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */