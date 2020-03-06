package SimulatorsGUI;

import Hack.VMEmulator.CalculatorGUI;
import HackGUI.Format;
import HackGUI.Utilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class StackCalculator extends JPanel implements CalculatorGUI {
  private JTextField firstInput = new JTextField();
  
  private JTextField command = new JTextField();
  
  private JTextField secondInput = new JTextField();
  
  private JTextField output = new JTextField();
  
  private static final BasicStroke wideStroke = new BasicStroke(3.0F);
  
  private static final BasicStroke regularStroke = new BasicStroke(1.0F);
  
  protected short nullValue;
  
  protected boolean hideNullValue;
  
  public StackCalculator() {
    jbInit();
  }
  
  public void disableUserInput() {}
  
  public void enableUserInput() {}
  
  public void setNullValue(short paramShort, boolean paramBoolean) {
    this.nullValue = paramShort;
    this.hideNullValue = paramBoolean;
  }
  
  protected String translateValueToString(short paramShort) {
    return this.hideNullValue ? ((paramShort == this.nullValue) ? "" : Format.translateValueToString(paramShort, 0)) : Format.translateValueToString(paramShort, 0);
  }
  
  public void hideCalculator() {
    setVisible(false);
  }
  
  public void showCalculator() {
    setVisible(true);
  }
  
  public void hideLeftInput() {
    this.firstInput.setVisible(false);
  }
  
  public void showLeftInput() {
    this.firstInput.setVisible(true);
  }
  
  public void setOperator(char paramChar) {
    this.command.setText(String.valueOf(paramChar));
  }
  
  public void hideFlash() {
    this.firstInput.setBackground(UIManager.getColor("Button.background"));
    this.secondInput.setBackground(UIManager.getColor("Button.background"));
    this.output.setBackground(UIManager.getColor("Button.background"));
  }
  
  public void flash(int paramInt) {
    switch (paramInt) {
      case 0:
        this.firstInput.setBackground(Color.orange);
        break;
      case 1:
        this.secondInput.setBackground(Color.orange);
        break;
      case 2:
        this.output.setBackground(Color.orange);
        break;
    } 
  }
  
  public void hideHighlight() {
    this.firstInput.setForeground(Color.black);
    this.secondInput.setForeground(Color.black);
    this.output.setForeground(Color.black);
  }
  
  public void highlight(int paramInt) {
    switch (paramInt) {
      case 0:
        this.firstInput.setForeground(Color.blue);
        break;
      case 1:
        this.secondInput.setForeground(Color.blue);
        break;
      case 2:
        this.output.setForeground(Color.blue);
        break;
    } 
  }
  
  public String getValueAsString(int paramInt) {
    switch (paramInt) {
      case 0:
        return this.firstInput.getText();
      case 1:
        return this.secondInput.getText();
      case 2:
        return this.output.getText();
    } 
    return "";
  }
  
  public void reset() {
    this.firstInput.setText(translateValueToString(this.nullValue));
    this.secondInput.setText(translateValueToString(this.nullValue));
    this.output.setText(translateValueToString(this.nullValue));
    hideFlash();
    hideHighlight();
  }
  
  public Point getCoordinates(int paramInt) {
    Point point = getLocation();
    switch (paramInt) {
      case 0:
        return new Point((int)(point.getX() + this.firstInput.getLocation().getX()), (int)(point.getY() + this.firstInput.getLocation().getY()));
      case 1:
        return new Point((int)(point.getX() + this.secondInput.getLocation().getX()), (int)(point.getY() + this.secondInput.getLocation().getY()));
      case 2:
        return new Point((int)(point.getX() + this.output.getLocation().getX()), (int)(point.getY() + this.output.getLocation().getY()));
    } 
    return null;
  }
  
  public void setValueAt(int paramInt, short paramShort) {
    String str = translateValueToString(paramShort);
    switch (paramInt) {
      case 0:
        this.firstInput.setText(str);
        break;
      case 1:
        this.secondInput.setText(str);
        break;
      case 2:
        this.output.setText(str);
        break;
    } 
  }
  
  public void paintComponent(Graphics paramGraphics) {
    Graphics2D graphics2D = (Graphics2D)paramGraphics;
    graphics2D.setPaint(Color.black);
    graphics2D.setStroke(wideStroke);
    graphics2D.draw(new Line2D.Double(18.0D, 60.0D, 142.0D, 60.0D));
    graphics2D.setStroke(regularStroke);
  }
  
  public void setNumericFormat(int paramInt) {}
  
  private void jbInit() {
    setLayout((LayoutManager)null);
    this.firstInput.setHorizontalAlignment(4);
    this.firstInput.setBounds(new Rectangle(18, 8, 124, 19));
    this.firstInput.setBackground(UIManager.getColor("Button.background"));
    this.firstInput.setFont(Utilities.valueFont);
    this.command.setFont(Utilities.bigLabelsFont);
    this.command.setHorizontalAlignment(0);
    this.command.setBounds(new Rectangle(2, 34, 13, 19));
    this.command.setBackground(UIManager.getColor("Button.background"));
    this.command.setBorder((Border)null);
    this.secondInput.setHorizontalAlignment(4);
    this.secondInput.setBounds(new Rectangle(18, 34, 124, 19));
    this.secondInput.setBackground(UIManager.getColor("Button.background"));
    this.secondInput.setFont(new Font("Courier New", 0, 12));
    this.output.setHorizontalAlignment(4);
    this.output.setBounds(new Rectangle(18, 70, 124, 19));
    this.output.setBackground(UIManager.getColor("Button.background"));
    this.output.setFont(Utilities.valueFont);
    add(this.secondInput, (Object)null);
    add(this.firstInput, (Object)null);
    add(this.output, (Object)null);
    add(this.command, (Object)null);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/StackCalculator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */