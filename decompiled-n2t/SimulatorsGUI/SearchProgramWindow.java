package SimulatorsGUI;

import Hack.VirtualMachine.HVMInstruction;
import HackGUI.Utilities;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SearchProgramWindow extends JFrame {
  private JLabel instructionLbl = new JLabel();
  
  private JTextField instruction = new JTextField();
  
  private JButton okButton = new JButton();
  
  private JButton cancelButton = new JButton();
  
  private ImageIcon okIcon = new ImageIcon("bin/images/ok.gif");
  
  private ImageIcon cancelIcon = new ImageIcon("bin/images/cancel.gif");
  
  private JTable table;
  
  private HVMInstruction[] instructions;
  
  public SearchProgramWindow(JTable paramJTable, HVMInstruction[] paramArrayOfHVMInstruction) {
    super("Search");
    this.table = paramJTable;
    jbInit();
  }
  
  public void setInstructions(HVMInstruction[] paramArrayOfHVMInstruction) {
    this.instructions = paramArrayOfHVMInstruction;
  }
  
  private int getSearchedRowIndex() {
    byte b1;
    byte b = -1;
    String str1 = this.instruction.getText();
    StringTokenizer stringTokenizer = new StringTokenizer(str1);
    String str2 = "";
    String str3 = "";
    String str4 = "";
    int i = stringTokenizer.countTokens();
    switch (i) {
      case 1:
        str2 = stringTokenizer.nextToken();
        for (b1 = 0; b1 < this.instructions.length; b1++) {
          String[] arrayOfString = this.instructions[b1].getFormattedStrings();
          if (arrayOfString[0].equalsIgnoreCase(str2)) {
            b = b1;
            break;
          } 
        } 
        break;
      case 2:
        str2 = stringTokenizer.nextToken();
        str3 = stringTokenizer.nextToken();
        for (b1 = 0; b1 < this.instructions.length; b1++) {
          String[] arrayOfString = this.instructions[b1].getFormattedStrings();
          if (arrayOfString[0].equalsIgnoreCase(str2) && arrayOfString[1].equalsIgnoreCase(str3)) {
            b = b1;
            break;
          } 
        } 
        break;
      case 3:
        str2 = stringTokenizer.nextToken();
        str3 = stringTokenizer.nextToken();
        str4 = stringTokenizer.nextToken();
        for (b1 = 0; b1 < this.instructions.length; b1++) {
          String[] arrayOfString = this.instructions[b1].getFormattedStrings();
          if (arrayOfString[0].equalsIgnoreCase(str2) && arrayOfString[1].equalsIgnoreCase(str3) && arrayOfString[2].equalsIgnoreCase(str4)) {
            b = b1;
            break;
          } 
        } 
        break;
    } 
    return b;
  }
  
  public void showWindow() {
    setVisible(true);
    this.instruction.requestFocus();
  }
  
  private void jbInit() {
    this.instructionLbl.setFont(Utilities.thinLabelsFont);
    this.instructionLbl.setText("Text to find :");
    this.instructionLbl.setBounds(new Rectangle(9, 22, 79, 23));
    getContentPane().setLayout((LayoutManager)null);
    this.instruction.setBounds(new Rectangle(82, 25, 220, 18));
    this.instruction.addActionListener(new ActionListener(this) {
          private final SearchProgramWindow this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.instruction_actionPerformed(param1ActionEvent);
          }
        });
    this.okButton.setToolTipText("OK");
    this.okButton.setIcon(this.okIcon);
    this.okButton.setBounds(new Rectangle(66, 68, 63, 44));
    this.okButton.addActionListener(new ActionListener(this) {
          private final SearchProgramWindow this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.okButton_actionPerformed(param1ActionEvent);
          }
        });
    this.cancelButton.setBounds(new Rectangle(190, 68, 63, 44));
    this.cancelButton.addActionListener(new ActionListener(this) {
          private final SearchProgramWindow this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.cancelButton_actionPerformed(param1ActionEvent);
          }
        });
    this.cancelButton.setToolTipText("CANCEL");
    this.cancelButton.setIcon(this.cancelIcon);
    getContentPane().add(this.instruction, (Object)null);
    getContentPane().add(this.okButton, (Object)null);
    getContentPane().add(this.cancelButton, (Object)null);
    getContentPane().add(this.instructionLbl, (Object)null);
    setSize(320, 150);
    setLocation(250, 250);
  }
  
  public void okButton_actionPerformed(ActionEvent paramActionEvent) {
    try {
      int i = getSearchedRowIndex();
      if (i != -1) {
        Rectangle rectangle = this.table.getCellRect(i, 0, true);
        this.table.scrollRectToVisible(rectangle);
        this.table.setRowSelectionInterval(i, i);
        setVisible(false);
      } 
    } catch (NumberFormatException numberFormatException) {
    
    } catch (IllegalArgumentException illegalArgumentException) {}
  }
  
  public void cancelButton_actionPerformed(ActionEvent paramActionEvent) {
    setVisible(false);
  }
  
  public void instruction_actionPerformed(ActionEvent paramActionEvent) {
    okButton_actionPerformed(paramActionEvent);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/SearchProgramWindow.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */