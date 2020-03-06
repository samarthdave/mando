package SimulatorsGUI;

import Hack.HardwareSimulator.GateInfoGUI;
import HackGUI.Utilities;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SystemColor;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GateInfoComponent extends JPanel implements GateInfoGUI {
  private JLabel chipNameLbl = new JLabel();
  
  private JLabel timeLbl = new JLabel();
  
  private JTextField chipNameTxt = new JTextField();
  
  private JTextField timeTxt = new JTextField();
  
  private boolean clockUp;
  
  private String chipName;
  
  public GateInfoComponent() {
    jbInit();
  }
  
  public void setChip(String paramString) {
    this.chipName = paramString;
    this.chipNameTxt.setText(paramString);
  }
  
  public void setClock(boolean paramBoolean) {
    this.clockUp = paramBoolean;
    if (paramBoolean)
      this.timeTxt.setText(this.timeTxt.getText() + "+"); 
  }
  
  public void setClocked(boolean paramBoolean) {
    if (paramBoolean) {
      this.chipNameTxt.setText(this.chipName + " (Clocked) ");
    } else {
      this.chipNameTxt.setText(this.chipName);
    } 
  }
  
  public void setTime(int paramInt) {
    if (this.clockUp) {
      this.timeTxt.setText(String.valueOf(paramInt) + "+");
    } else {
      this.timeTxt.setText(String.valueOf(paramInt));
    } 
  }
  
  public void reset() {
    this.chipNameTxt.setText("");
    this.timeTxt.setText("0");
  }
  
  public void enableTime() {
    this.timeLbl.setEnabled(true);
    this.timeTxt.setEnabled(true);
  }
  
  public void disableTime() {
    this.timeLbl.setEnabled(false);
    this.timeTxt.setEnabled(false);
  }
  
  private void jbInit() {
    setLayout((LayoutManager)null);
    this.chipNameLbl.setText("Chip Name :");
    this.chipNameLbl.setBounds(new Rectangle(11, 7, 74, 21));
    this.timeLbl.setBounds(new Rectangle(341, 8, 42, 21));
    this.timeLbl.setText("Time :");
    this.chipNameTxt.setBackground(SystemColor.info);
    this.chipNameTxt.setFont(Utilities.thinBigLabelsFont);
    this.chipNameTxt.setEditable(false);
    this.chipNameTxt.setHorizontalAlignment(2);
    this.chipNameTxt.setBounds(new Rectangle(89, 8, 231, 20));
    this.timeTxt.setBackground(SystemColor.info);
    this.timeTxt.setFont(Utilities.thinBigLabelsFont);
    this.timeTxt.setEditable(false);
    this.timeTxt.setBounds(new Rectangle(388, 8, 69, 20));
    add(this.chipNameTxt, (Object)null);
    add(this.chipNameLbl, (Object)null);
    add(this.timeLbl, (Object)null);
    add(this.timeTxt, (Object)null);
    setSize(483, 37);
    setBorder(BorderFactory.createEtchedBorder());
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/GateInfoComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */