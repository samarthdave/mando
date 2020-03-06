package SimulatorsGUI;

import Hack.CPUEmulator.KeyboardGUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class KeyboardComponent extends JPanel implements KeyboardGUI {
  private ImageIcon keyboardIcon = new ImageIcon("bin/images/keyboard.gif");
  
  private JTextField keyNameText = new JTextField();
  
  private JButton keyButton = new JButton();
  
  public KeyboardComponent() {
    jbInit();
  }
  
  public void setKey(String paramString) {
    this.keyNameText.setText(paramString);
  }
  
  public void clearKey() {
    this.keyNameText.setText("");
  }
  
  public void reset() {}
  
  private void jbInit() {
    this.keyNameText.setBounds(new Rectangle(258, 0, 258, 27));
    this.keyNameText.setEnabled(false);
    this.keyNameText.setFont(new Font("Times New Roman", 1, 14));
    this.keyNameText.setDisabledTextColor(Color.black);
    this.keyNameText.setEditable(false);
    this.keyNameText.setHorizontalAlignment(0);
    this.keyNameText.setBackground(SystemColor.info);
    setLayout((LayoutManager)null);
    this.keyButton.setIcon(this.keyboardIcon);
    this.keyButton.setBounds(new Rectangle(0, 0, 258, 27));
    this.keyButton.addFocusListener(new FocusListener(this) {
          private final KeyboardComponent this$0;
          
          public void focusGained(FocusEvent param1FocusEvent) {
            this.this$0.keyButton_focusGained(param1FocusEvent);
          }
          
          public void focusLost(FocusEvent param1FocusEvent) {
            this.this$0.keyButton_focusLost(param1FocusEvent);
          }
        });
    add(this.keyButton, (Object)null);
    add(this.keyNameText, (Object)null);
    setPreferredSize(new Dimension(516, 27));
    setSize(516, 27);
  }
  
  public JComponent getKeyEventHandler() {
    return this.keyButton;
  }
  
  public void keyButton_focusGained(FocusEvent paramFocusEvent) {
    this.keyButton.setBackground(UIManager.getColor("TextField.selectionBackground"));
  }
  
  public void keyButton_focusLost(FocusEvent paramFocusEvent) {
    this.keyButton.setBackground(UIManager.getColor("Button.background"));
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/KeyboardComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */