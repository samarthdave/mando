package SimulatorsGUI;

import Hack.CPUEmulator.ALUGUI;
import HackGUI.Format;
import HackGUI.Utilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class ALUComponent extends JPanel implements ALUGUI {
  private static final int START_LOCATION_ZERO_X = 7;
  
  private static final int START_LOCATION_ZERO_Y = 39;
  
  private static final int START_LOCATION_ONE_Y = 85;
  
  private static final int START_LOCATION_TWO_X = 237;
  
  private static final int START_LOCATION_TWO_Y = 61;
  
  private static final int START_ALU_X = 159;
  
  private static final int FINISH_ALU_X = 216;
  
  private static final int LOCATION_WIDTH = 124;
  
  private static final int LOCATION_HEIGHT = 19;
  
  private static final BasicStroke wideStroke = new BasicStroke(3.0F);
  
  private static final BasicStroke regularStroke = new BasicStroke(1.0F);
  
  protected int dataFormat = 0;
  
  protected short location0Value;
  
  protected short location1Value;
  
  protected short location2Value;
  
  protected JTextField location0 = new JTextField();
  
  protected JTextField location1 = new JTextField();
  
  protected JTextField location2 = new JTextField();
  
  private JTextField commandLbl = new JTextField();
  
  private Color aluColor = new Color(107, 194, 46);
  
  private JLabel nameLbl = new JLabel();
  
  private Border commandBorder;
  
  private JLabel location0Lbl = new JLabel();
  
  private JLabel location1Lbl = new JLabel();
  
  private JLabel location2Lbl = new JLabel();
  
  protected short nullValue;
  
  protected boolean hideNullValue;
  
  public ALUComponent() {
    jbInit();
  }
  
  public void setNullValue(short paramShort, boolean paramBoolean) {
    this.nullValue = paramShort;
    this.hideNullValue = paramBoolean;
  }
  
  protected String translateValueToString(short paramShort) {
    return this.hideNullValue ? ((paramShort == this.nullValue) ? "" : Format.translateValueToString(paramShort, this.dataFormat)) : Format.translateValueToString(paramShort, this.dataFormat);
  }
  
  public void disableUserInput() {}
  
  public void enableUserInput() {}
  
  public void commandFlash() {
    this.commandLbl.setBackground(Color.red);
    repaint();
  }
  
  public void hideCommandFlash() {
    this.commandLbl.setBackground(new Color(107, 194, 46));
    repaint();
  }
  
  public void bodyFlash() {
    this.aluColor = Color.red;
    this.commandLbl.setBackground(Color.red);
    repaint();
  }
  
  public void hideBodyFlash() {
    this.aluColor = new Color(107, 194, 46);
    this.commandLbl.setBackground(new Color(107, 194, 46));
    repaint();
  }
  
  public void flash(int paramInt) {
    switch (paramInt) {
      case 0:
        this.location0.setBackground(Color.orange);
        break;
      case 1:
        this.location1.setBackground(Color.orange);
        break;
      case 2:
        this.location2.setBackground(Color.orange);
        break;
    } 
  }
  
  public void hideFlash() {
    this.location0.setBackground((Color)null);
    this.location1.setBackground((Color)null);
    this.location2.setBackground((Color)null);
  }
  
  public void hideHighlight() {
    this.location0.setDisabledTextColor(Color.black);
    this.location1.setDisabledTextColor(Color.black);
    this.location2.setDisabledTextColor(Color.black);
    repaint();
  }
  
  public void highlight(int paramInt) {
    switch (paramInt) {
      case 0:
        this.location0.setDisabledTextColor(Color.blue);
        break;
      case 1:
        this.location1.setDisabledTextColor(Color.blue);
        break;
      case 2:
        this.location2.setDisabledTextColor(Color.blue);
        break;
    } 
    repaint();
  }
  
  public Point getCoordinates(int paramInt) {
    Point point = getLocation();
    switch (paramInt) {
      case 0:
        return new Point((int)(point.getX() + this.location0.getLocation().getX()), (int)(point.getY() + this.location0.getLocation().getY()));
      case 1:
        return new Point((int)(point.getX() + this.location1.getLocation().getX()), (int)(point.getY() + this.location1.getLocation().getY()));
      case 2:
        return new Point((int)(point.getX() + this.location2.getLocation().getX()), (int)(point.getY() + this.location2.getLocation().getY()));
    } 
    return null;
  }
  
  public String getValueAsString(int paramInt) {
    switch (paramInt) {
      case 0:
        return this.location0.getText();
      case 1:
        return this.location1.getText();
      case 2:
        return this.location2.getText();
    } 
    return null;
  }
  
  public void reset() {
    this.location0.setText(Format.translateValueToString(this.nullValue, this.dataFormat));
    this.location1.setText(Format.translateValueToString(this.nullValue, this.dataFormat));
    this.location2.setText(Format.translateValueToString(this.nullValue, this.dataFormat));
    setCommand("");
    hideFlash();
    hideHighlight();
  }
  
  public void setValueAt(int paramInt, short paramShort) {
    String str = Format.translateValueToString(paramShort, this.dataFormat);
    switch (paramInt) {
      case 0:
        this.location0Value = paramShort;
        this.location0.setText(str);
        break;
      case 1:
        this.location1Value = paramShort;
        this.location1.setText(str);
        break;
      case 2:
        this.location2Value = paramShort;
        this.location2.setText(str);
        break;
    } 
  }
  
  public void setCommand(String paramString) {
    this.commandLbl.setText(paramString);
  }
  
  public void paintComponent(Graphics paramGraphics) {
    Graphics2D graphics2D = (Graphics2D)paramGraphics;
    graphics2D.setPaint(Color.black);
    int[] arrayOfInt1 = { 159, 216, 216, 159 };
    int[] arrayOfInt2 = { 23, 56, 83, 116 };
    GeneralPath generalPath = new GeneralPath(0, arrayOfInt1.length);
    generalPath.moveTo(arrayOfInt1[0], arrayOfInt2[0]);
    for (byte b = 1; b < arrayOfInt1.length; b++)
      generalPath.lineTo(arrayOfInt1[b], arrayOfInt2[b]); 
    generalPath.closePath();
    graphics2D.setPaint(this.aluColor);
    graphics2D.fill(generalPath);
    graphics2D.setStroke(wideStroke);
    graphics2D.setPaint(Color.black);
    graphics2D.draw(generalPath);
    graphics2D.setStroke(regularStroke);
    graphics2D.draw(new Line2D.Double(131.0D, 48.0D, 159.0D, 48.0D));
    graphics2D.draw(new Line2D.Double(131.0D, 94.0D, 159.0D, 94.0D));
    graphics2D.draw(new Line2D.Double(216.0D, 70.0D, 236.0D, 70.0D));
  }
  
  public void setNumericFormat(int paramInt) {
    this.dataFormat = paramInt;
    this.location0.setText(Format.translateValueToString(this.location0Value, paramInt));
    this.location1.setText(Format.translateValueToString(this.location1Value, paramInt));
    this.location2.setText(Format.translateValueToString(this.location2Value, paramInt));
  }
  
  private void jbInit() {
    setOpaque(false);
    this.commandBorder = BorderFactory.createLineBorder(Color.black, 1);
    setLayout((LayoutManager)null);
    this.location0.setForeground(Color.black);
    this.location0.setDisabledTextColor(Color.black);
    this.location0.setEditable(false);
    this.location0.setHorizontalAlignment(4);
    this.location0.setBounds(new Rectangle(7, 39, 124, 19));
    this.location0.setBackground(UIManager.getColor("Button.background"));
    this.location0.setEnabled(false);
    this.location0.setFont(Utilities.valueFont);
    this.location1.setHorizontalAlignment(4);
    this.location1.setBounds(new Rectangle(7, 85, 124, 19));
    this.location1.setForeground(Color.black);
    this.location1.setDisabledTextColor(Color.black);
    this.location1.setEditable(false);
    this.location1.setBackground(UIManager.getColor("Button.background"));
    this.location1.setEnabled(false);
    this.location1.setFont(Utilities.valueFont);
    this.location2.setHorizontalAlignment(4);
    this.location2.setBounds(new Rectangle(237, 61, 124, 19));
    this.location2.setForeground(Color.black);
    this.location2.setDisabledTextColor(Color.black);
    this.location2.setEditable(false);
    this.location2.setBackground(UIManager.getColor("Button.background"));
    this.location2.setEnabled(false);
    this.location2.setFont(Utilities.valueFont);
    this.commandLbl.setBackground(new Color(107, 194, 46));
    this.commandLbl.setEnabled(false);
    this.commandLbl.setFont(Utilities.labelsFont);
    this.commandLbl.setForeground(Color.black);
    this.commandLbl.setBorder(this.commandBorder);
    this.commandLbl.setDisabledTextColor(Color.black);
    this.commandLbl.setEditable(false);
    this.commandLbl.setHorizontalAlignment(0);
    this.commandLbl.setBounds(new Rectangle(163, 62, 50, 16));
    this.location0Lbl.setText("D Input :");
    this.location0Lbl.setBounds(new Rectangle(7, 23, 56, 16));
    this.location0Lbl.setFont(Utilities.smallLabelsFont);
    this.location0Lbl.setForeground(Color.black);
    this.location1Lbl.setText("M/A Input :");
    this.location1Lbl.setBounds(new Rectangle(7, 69, 70, 16));
    this.location1Lbl.setFont(Utilities.smallLabelsFont);
    this.location1Lbl.setForeground(Color.black);
    this.location2Lbl.setText("ALU output :");
    this.location2Lbl.setBounds(new Rectangle(237, 45, 72, 16));
    this.location2Lbl.setFont(Utilities.smallLabelsFont);
    this.location2Lbl.setForeground(Color.black);
    this.nameLbl.setText("ALU");
    this.nameLbl.setFont(Utilities.labelsFont);
    this.nameLbl.setBounds(new Rectangle(6, 0, 50, 22));
    add(this.commandLbl, (Object)null);
    add(this.location1, (Object)null);
    add(this.location0, (Object)null);
    add(this.location2, (Object)null);
    add(this.location0Lbl, (Object)null);
    add(this.location1Lbl, (Object)null);
    add(this.location2Lbl, (Object)null);
    add(this.nameLbl, (Object)null);
    setBorder(BorderFactory.createEtchedBorder());
    setPreferredSize(new Dimension(368, 122));
    setSize(368, 122);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/ALUComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */