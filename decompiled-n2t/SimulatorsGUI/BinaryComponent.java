package SimulatorsGUI;

import Hack.Utilities.Conversions;
import HackGUI.Utilities;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class BinaryComponent extends JPanel implements MouseListener, KeyListener {
  private JTextField bit0 = new JTextField(1);
  
  private JTextField bit1 = new JTextField(1);
  
  private JTextField bit2 = new JTextField(1);
  
  private JTextField bit3 = new JTextField(1);
  
  private JTextField bit4 = new JTextField(1);
  
  private JTextField bit5 = new JTextField(1);
  
  private JTextField bit6 = new JTextField(1);
  
  private JTextField bit7 = new JTextField(1);
  
  private JTextField bit8 = new JTextField(1);
  
  private JTextField bit9 = new JTextField(1);
  
  private JTextField bit10 = new JTextField(1);
  
  private JTextField bit11 = new JTextField(1);
  
  private JTextField bit12 = new JTextField(1);
  
  private JTextField bit13 = new JTextField(1);
  
  private JTextField bit14 = new JTextField(1);
  
  private JTextField bit15 = new JTextField(1);
  
  private JTextField[] bits = new JTextField[16];
  
  private StringBuffer valueStr;
  
  private JButton okButton = new JButton();
  
  private JButton cancelButton = new JButton();
  
  private ImageIcon okIcon = new ImageIcon("bin/images/smallok.gif");
  
  private ImageIcon cancelIcon = new ImageIcon("bin/images/smallcancel.gif");
  
  private Vector listeners = new Vector();
  
  private boolean isOk = false;
  
  private Border binaryBorder;
  
  private int numberOfBits;
  
  public BinaryComponent() {
    jbInit();
  }
  
  public void addListener(PinValueListener paramPinValueListener) {
    this.listeners.addElement(paramPinValueListener);
  }
  
  public void removeListener(PinValueListener paramPinValueListener) {
    this.listeners.removeElement(paramPinValueListener);
  }
  
  public void notifyListeners() {
    PinValueEvent pinValueEvent = new PinValueEvent(this, this.valueStr.toString(), this.isOk);
    for (byte b = 0; b < this.listeners.size(); b++)
      ((PinValueListener)this.listeners.elementAt(b)).pinValueChanged(pinValueEvent); 
  }
  
  public void setNumOfBits(int paramInt) {
    this.numberOfBits = paramInt;
    for (byte b = 0; b < this.bits.length; b++) {
      if (b < this.bits.length - paramInt) {
        this.bits[b].setText("");
        this.bits[b].setBackground(Color.darkGray);
        this.bits[b].setEnabled(false);
      } else {
        this.bits[b].setBackground(Color.white);
        this.bits[b].setEnabled(true);
      } 
    } 
  }
  
  public void setValue(short paramShort) {
    this.valueStr = new StringBuffer(Conversions.decimalToBinary(paramShort, 16));
    for (byte b = 0; b < this.bits.length; b++)
      this.bits[b].setText(String.valueOf(this.valueStr.charAt(b))); 
  }
  
  public short getValue() {
    return (short)Conversions.binaryToInt(this.valueStr.toString());
  }
  
  private void updateValue() {
    this.valueStr = new StringBuffer(16);
    for (byte b = 0; b < this.bits.length; b++) {
      char c;
      if (this.bits[b].getText().equals("")) {
        c = '0';
      } else {
        c = this.bits[b].getText().charAt(0);
      } 
      this.valueStr.append(c);
    } 
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    if (paramMouseEvent.getClickCount() == 2) {
      JTextField jTextField = (JTextField)paramMouseEvent.getSource();
      if (jTextField.getText().equals("0")) {
        jTextField.setText("1");
      } else if (jTextField.getText().equals("1")) {
        jTextField.setText("0");
      } 
    } 
  }
  
  public void keyTyped(KeyEvent paramKeyEvent) {
    JTextField jTextField = (JTextField)paramKeyEvent.getSource();
    if (paramKeyEvent.getKeyChar() == '0' || paramKeyEvent.getKeyChar() == '1') {
      jTextField.transferFocus();
      jTextField.selectAll();
    } else if (paramKeyEvent.getKeyChar() == '\n') {
      approve();
    } else if (paramKeyEvent.getKeyChar() == '\033') {
      hideBinary();
    } else {
      jTextField.selectAll();
      jTextField.getToolkit().beep();
    } 
  }
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyPressed(KeyEvent paramKeyEvent) {}
  
  private void jbInit() {
    this.binaryBorder = BorderFactory.createLineBorder(Color.black, 3);
    setLayout((LayoutManager)null);
    this.bit0.setFont(Utilities.valueFont);
    this.bit0.setText("0");
    this.bit0.setHorizontalAlignment(4);
    this.bit0.setBounds(new Rectangle(211, 8, 13, 19));
    this.bit0.addMouseListener(this);
    this.bit0.addKeyListener(this);
    this.bit1.setFont(Utilities.valueFont);
    this.bit1.setText("0");
    this.bit1.setHorizontalAlignment(4);
    this.bit1.setBounds(new Rectangle(198, 8, 13, 19));
    this.bit1.addMouseListener(this);
    this.bit1.addKeyListener(this);
    this.bit2.setFont(Utilities.valueFont);
    this.bit2.setText("0");
    this.bit2.setHorizontalAlignment(4);
    this.bit2.setBounds(new Rectangle(185, 8, 13, 19));
    this.bit2.addMouseListener(this);
    this.bit2.addKeyListener(this);
    this.bit3.setFont(Utilities.valueFont);
    this.bit3.setText("0");
    this.bit3.setHorizontalAlignment(4);
    this.bit3.setBounds(new Rectangle(172, 8, 13, 19));
    this.bit3.addMouseListener(this);
    this.bit3.addKeyListener(this);
    this.bit4.setFont(Utilities.valueFont);
    this.bit4.setText("0");
    this.bit4.setHorizontalAlignment(4);
    this.bit4.setBounds(new Rectangle(159, 8, 13, 19));
    this.bit4.addMouseListener(this);
    this.bit4.addKeyListener(this);
    this.bit5.setFont(Utilities.valueFont);
    this.bit5.setText("0");
    this.bit5.setHorizontalAlignment(4);
    this.bit5.setBounds(new Rectangle(146, 8, 13, 19));
    this.bit5.addMouseListener(this);
    this.bit5.addKeyListener(this);
    this.bit6.setFont(Utilities.valueFont);
    this.bit6.setText("0");
    this.bit6.setHorizontalAlignment(4);
    this.bit6.setBounds(new Rectangle(133, 8, 13, 19));
    this.bit6.addMouseListener(this);
    this.bit6.addKeyListener(this);
    this.bit7.setFont(Utilities.valueFont);
    this.bit7.setText("0");
    this.bit7.setHorizontalAlignment(4);
    this.bit7.setBounds(new Rectangle(120, 8, 13, 19));
    this.bit7.addMouseListener(this);
    this.bit7.addKeyListener(this);
    this.bit8.setFont(Utilities.valueFont);
    this.bit8.setText("0");
    this.bit8.setHorizontalAlignment(4);
    this.bit8.setBounds(new Rectangle(107, 8, 13, 19));
    this.bit8.addMouseListener(this);
    this.bit8.addKeyListener(this);
    this.bit9.setFont(Utilities.valueFont);
    this.bit9.setText("0");
    this.bit9.setHorizontalAlignment(4);
    this.bit9.setBounds(new Rectangle(94, 8, 13, 19));
    this.bit9.addMouseListener(this);
    this.bit9.addKeyListener(this);
    this.bit10.setFont(Utilities.valueFont);
    this.bit10.setText("0");
    this.bit10.setHorizontalAlignment(4);
    this.bit10.setBounds(new Rectangle(81, 8, 13, 19));
    this.bit10.addMouseListener(this);
    this.bit10.addKeyListener(this);
    this.bit11.setFont(Utilities.valueFont);
    this.bit11.setText("0");
    this.bit11.setHorizontalAlignment(4);
    this.bit11.setBounds(new Rectangle(68, 8, 13, 19));
    this.bit11.addMouseListener(this);
    this.bit11.addKeyListener(this);
    this.bit12.setFont(Utilities.valueFont);
    this.bit12.setText("0");
    this.bit12.setHorizontalAlignment(4);
    this.bit12.setBounds(new Rectangle(55, 8, 13, 19));
    this.bit12.addMouseListener(this);
    this.bit12.addKeyListener(this);
    this.bit13.setFont(Utilities.valueFont);
    this.bit13.setText("0");
    this.bit13.setHorizontalAlignment(4);
    this.bit13.setBounds(new Rectangle(42, 8, 13, 19));
    this.bit13.addMouseListener(this);
    this.bit13.addKeyListener(this);
    this.bit14.setFont(Utilities.valueFont);
    this.bit14.setText("0");
    this.bit14.setHorizontalAlignment(4);
    this.bit14.setBounds(new Rectangle(29, 8, 13, 19));
    this.bit14.addMouseListener(this);
    this.bit14.addKeyListener(this);
    this.bit15.setFont(Utilities.valueFont);
    this.bit15.setText("0");
    this.bit15.setHorizontalAlignment(4);
    this.bit15.setBounds(new Rectangle(16, 8, 13, 19));
    this.bit15.addMouseListener(this);
    this.bit15.addKeyListener(this);
    this.okButton.setHorizontalTextPosition(0);
    this.okButton.setIcon(this.okIcon);
    this.okButton.setBounds(new Rectangle(97, 29, 23, 20));
    this.okButton.addActionListener(new ActionListener(this) {
          private final BinaryComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.okButton_actionPerformed(param1ActionEvent);
          }
        });
    this.cancelButton.setHorizontalTextPosition(0);
    this.cancelButton.setIcon(this.cancelIcon);
    this.cancelButton.setBounds(new Rectangle(125, 29, 23, 20));
    this.cancelButton.addActionListener(new ActionListener(this) {
          private final BinaryComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.cancelButton_actionPerformed(param1ActionEvent);
          }
        });
    setBorder(this.binaryBorder);
    add(this.bit15, (Object)null);
    add(this.bit14, (Object)null);
    add(this.bit13, (Object)null);
    add(this.bit12, (Object)null);
    add(this.bit11, (Object)null);
    add(this.bit10, (Object)null);
    add(this.bit9, (Object)null);
    add(this.bit8, (Object)null);
    add(this.bit7, (Object)null);
    add(this.bit6, (Object)null);
    add(this.bit5, (Object)null);
    add(this.bit4, (Object)null);
    add(this.bit3, (Object)null);
    add(this.bit2, (Object)null);
    add(this.bit1, (Object)null);
    add(this.bit0, (Object)null);
    add(this.cancelButton, (Object)null);
    add(this.okButton, (Object)null);
    this.bits[0] = this.bit15;
    this.bits[1] = this.bit14;
    this.bits[2] = this.bit13;
    this.bits[3] = this.bit12;
    this.bits[4] = this.bit11;
    this.bits[5] = this.bit10;
    this.bits[6] = this.bit9;
    this.bits[7] = this.bit8;
    this.bits[8] = this.bit7;
    this.bits[9] = this.bit6;
    this.bits[10] = this.bit5;
    this.bits[11] = this.bit4;
    this.bits[12] = this.bit3;
    this.bits[13] = this.bit2;
    this.bits[14] = this.bit1;
    this.bits[15] = this.bit0;
  }
  
  private void approve() {
    this.isOk = true;
    updateValue();
    notifyListeners();
    setVisible(false);
  }
  
  public void okButton_actionPerformed(ActionEvent paramActionEvent) {
    approve();
  }
  
  public void cancelButton_actionPerformed(ActionEvent paramActionEvent) {
    hideBinary();
  }
  
  public void hideBinary() {
    this.isOk = false;
    notifyListeners();
    setVisible(false);
  }
  
  public void showBinary() {
    setVisible(true);
    this.bits[16 - this.numberOfBits].grabFocus();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/BinaryComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */