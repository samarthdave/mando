package SimulatorsGUI;

import Hack.ComputerParts.TextFileGUI;
import Hack.Gates.GatesPanelGUI;
import Hack.HardwareSimulator.GateInfoGUI;
import Hack.HardwareSimulator.HardwareSimulatorGUI;
import Hack.HardwareSimulator.PartPinsGUI;
import Hack.HardwareSimulator.PartsGUI;
import Hack.HardwareSimulator.PinsGUI;
import HackGUI.TextFileComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class HardwareSimulatorComponent extends HackSimulatorComponent implements HardwareSimulatorGUI, GatesPanelGUI {
  private static final int WIDTH = 1018;
  
  private static final int HEIGHT = 611;
  
  private PinsComponent inputPins;
  
  private PinsComponent outputPins;
  
  private PinsComponent internalPins;
  
  private TextFileComponent hdlView;
  
  private PartPinsComponent partPins;
  
  private PartsComponent parts;
  
  private JLabel messageLbl = new JLabel();
  
  private JPanel nullLayoutGatesPanel = new JPanel();
  
  private JPanel flowLayoutGatesPanel = new JPanel();
  
  private boolean flowLayout = false;
  
  private GateInfoComponent gateInfo;
  
  public HardwareSimulatorComponent() {
    this.nullLayoutGatesPanel.setLayout((LayoutManager)null);
    this.flowLayoutGatesPanel.setLayout(new FlowLayout(1, 1, 1));
    this.inputPins = new PinsComponent();
    this.inputPins.setPinsName("Input pins");
    this.outputPins = new PinsComponent();
    this.outputPins.setPinsName("Output pins");
    this.internalPins = new PinsComponent();
    this.internalPins.setPinsName("Internal pins");
    this.partPins = new PartPinsComponent();
    this.partPins.setPinsName("Part pins");
    this.parts = new PartsComponent();
    this.parts.setName("Internal Parts");
    this.hdlView = new TextFileComponent();
    this.gateInfo = new GateInfoComponent();
    jbInit();
    this.inputPins.setTopLevelLocation(this);
    this.outputPins.setTopLevelLocation(this);
    this.internalPins.setTopLevelLocation(this);
    this.partPins.setTopLevelLocation(this);
    this.hdlView.setName("HDL");
  }
  
  public void loadProgram() {}
  
  public GatesPanelGUI getGatesPanel() {
    return this;
  }
  
  public TextFileGUI getHDLView() {
    return (TextFileGUI)this.hdlView;
  }
  
  public PinsGUI getInputPins() {
    return this.inputPins;
  }
  
  public PinsGUI getOutputPins() {
    return this.outputPins;
  }
  
  public PinsGUI getInternalPins() {
    return this.internalPins;
  }
  
  public GateInfoGUI getGateInfo() {
    return this.gateInfo;
  }
  
  public PartPinsGUI getPartPins() {
    return this.partPins;
  }
  
  public PartsGUI getParts() {
    return this.parts;
  }
  
  public void showInternalPins() {
    this.internalPins.setVisible(true);
  }
  
  public void hideInternalPins() {
    this.internalPins.setVisible(false);
  }
  
  public void showPartPins() {
    this.partPins.setVisible(true);
  }
  
  public void hidePartPins() {
    this.partPins.setVisible(false);
  }
  
  public void showParts() {
    this.parts.setVisible(true);
  }
  
  public void hideParts() {
    this.parts.setVisible(false);
  }
  
  public void displayMessage(String paramString, boolean paramBoolean) {
    if (paramBoolean) {
      this.messageLbl.setForeground(Color.red);
    } else {
      this.messageLbl.setForeground(UIManager.getColor("Label.foreground"));
    } 
    this.messageLbl.setText(paramString);
  }
  
  public Point getAdditionalDisplayLocation() {
    return new Point(496, 13);
  }
  
  public void setWorkingDir(File paramFile) {}
  
  public void addGateComponent(Component paramComponent) {
    if (this.flowLayout) {
      this.flowLayoutGatesPanel.add(paramComponent);
      this.flowLayoutGatesPanel.revalidate();
      this.flowLayoutGatesPanel.repaint();
    } else {
      Component[] arrayOfComponent = this.nullLayoutGatesPanel.getComponents();
      for (byte b = 0; b < arrayOfComponent.length; b++) {
        Rectangle rectangle = arrayOfComponent[b].getBounds();
        int i = (int)rectangle.getX();
        int j = (int)rectangle.getY();
        int k = (int)(rectangle.getX() + rectangle.getWidth() - 1.0D);
        int m = (int)(rectangle.getY() + rectangle.getHeight() - 1.0D);
        if (paramComponent.getY() <= m && paramComponent.getX() <= k && paramComponent.getY() + paramComponent.getHeight() - 1 >= j && paramComponent.getX() + paramComponent.getWidth() - 1 >= i) {
          this.flowLayout = true;
          if (this.currentAdditionalDisplay == null) {
            this.nullLayoutGatesPanel.setVisible(false);
            this.flowLayoutGatesPanel.setVisible(true);
          } 
          for (b = 0; b < arrayOfComponent.length; b++)
            this.flowLayoutGatesPanel.add(arrayOfComponent[b]); 
          this.flowLayoutGatesPanel.add(paramComponent);
          break;
        } 
      } 
      if (!this.flowLayout) {
        this.nullLayoutGatesPanel.add(paramComponent);
        this.nullLayoutGatesPanel.revalidate();
        this.nullLayoutGatesPanel.repaint();
      } 
    } 
  }
  
  public void removeGateComponent(Component paramComponent) {
    this.nullLayoutGatesPanel.remove(paramComponent);
    this.flowLayoutGatesPanel.remove(paramComponent);
    this.nullLayoutGatesPanel.revalidate();
    this.flowLayoutGatesPanel.revalidate();
    this.nullLayoutGatesPanel.repaint();
    this.flowLayoutGatesPanel.repaint();
  }
  
  public void removeAllGateComponents() {
    this.nullLayoutGatesPanel.removeAll();
    this.flowLayoutGatesPanel.removeAll();
    this.nullLayoutGatesPanel.revalidate();
    this.flowLayoutGatesPanel.revalidate();
    this.nullLayoutGatesPanel.repaint();
    this.flowLayoutGatesPanel.repaint();
    this.flowLayout = false;
    if (this.currentAdditionalDisplay == null) {
      this.nullLayoutGatesPanel.setVisible(true);
      this.flowLayoutGatesPanel.setVisible(false);
    } 
  }
  
  private void jbInit() {
    setLayout((LayoutManager)null);
    this.gateInfo.setBounds(5, 10, this.gateInfo.getWidth(), this.gateInfo.getHeight());
    this.inputPins.setVisibleRows(15);
    this.inputPins.setBounds(5, 53, this.inputPins.getWidth(), this.inputPins.getHeight());
    this.outputPins.setVisibleRows(15);
    this.outputPins.setBounds(247, 53, this.outputPins.getWidth(), this.outputPins.getHeight());
    this.internalPins.setVisibleRows(15);
    this.internalPins.setBounds(247, 332, this.internalPins.getWidth(), this.internalPins.getHeight());
    this.internalPins.setVisible(false);
    this.hdlView.setVisibleRows(15);
    this.hdlView.setBounds(5, 332, this.hdlView.getWidth(), this.hdlView.getHeight());
    this.partPins.setVisibleRows(15);
    this.partPins.setBounds(247, 332, this.partPins.getWidth(), this.partPins.getHeight());
    this.partPins.setVisible(false);
    this.parts.setVisibleRows(15);
    this.parts.setBounds(247, 332, this.parts.getWidth(), this.parts.getHeight());
    this.parts.setVisible(false);
    this.nullLayoutGatesPanel.setBorder(BorderFactory.createEtchedBorder());
    this.nullLayoutGatesPanel.setBounds(new Rectangle(492, 10, 524, 592));
    this.flowLayoutGatesPanel.setBorder(BorderFactory.createEtchedBorder());
    this.flowLayoutGatesPanel.setBounds(new Rectangle(492, 10, 524, 592));
    this.flowLayoutGatesPanel.setVisible(false);
    this.messageLbl.setBorder(BorderFactory.createLoweredBevelBorder());
    this.messageLbl.setBounds(new Rectangle(0, 694, 1010, 20));
    add(this.partPins, (Object)null);
    add((Component)this.hdlView, (Object)null);
    add(this.inputPins, (Object)null);
    add(this.outputPins, (Object)null);
    add(this.internalPins, (Object)null);
    add(this.parts, (Object)null);
    add(this.messageLbl, (Object)null);
    add(this.gateInfo, (Object)null);
    add(this.nullLayoutGatesPanel, (Object)null);
    add(this.flowLayoutGatesPanel, (Object)null);
    setSize(1018, 611);
  }
  
  public void setAdditionalDisplay(JComponent paramJComponent) {
    if (this.currentAdditionalDisplay == null && paramJComponent != null) {
      if (this.flowLayout) {
        this.flowLayoutGatesPanel.setVisible(false);
      } else {
        this.nullLayoutGatesPanel.setVisible(false);
      } 
    } else if (this.currentAdditionalDisplay != null && paramJComponent == null) {
      if (this.flowLayout) {
        this.flowLayoutGatesPanel.setVisible(true);
      } else {
        this.nullLayoutGatesPanel.setVisible(true);
      } 
    } 
    super.setAdditionalDisplay(paramJComponent);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/HardwareSimulatorComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */