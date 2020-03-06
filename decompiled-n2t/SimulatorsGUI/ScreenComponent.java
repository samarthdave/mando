package SimulatorsGUI;

import Hack.CPUEmulator.ScreenGUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScreenComponent extends JPanel implements ScreenGUI, ActionListener {
  private static final int ANIMATION_CLOCK_INTERVALS = 50;
  
  private static final int STATIC_CLOCK_INTERVALS = 500;
  
  private short[] data;
  
  private boolean redraw = true;
  
  private int[] x;
  
  private int[] y;
  
  protected Timer timer;
  
  public ScreenComponent() {
    setOpaque(true);
    setBackground(Color.white);
    setBorder(BorderFactory.createEtchedBorder());
    Insets insets = getBorder().getBorderInsets(this);
    int i = insets.left + insets.right;
    int j = insets.top + insets.bottom;
    setPreferredSize(new Dimension(512 + i, 256 + j));
    setSize(512 + i, 256 + j);
    this.data = new short[131072];
    this.x = new int[131072];
    this.y = new int[131072];
    this.x[0] = insets.left;
    this.y[0] = insets.top;
    for (byte b = 1; b < 131072; b++) {
      this.x[b] = this.x[b - 1] + 16;
      this.y[b] = this.y[b - 1];
      if (this.x[b] == 512 + insets.left) {
        this.x[b] = insets.left;
        this.y[b] = this.y[b] + 1;
      } 
    } 
    this.timer = new Timer(500, this);
    this.timer.start();
  }
  
  public void setValueAt(int paramInt, short paramShort) {
    this.data[paramInt] = paramShort;
    this.redraw = true;
  }
  
  public void setContents(short[] paramArrayOfshort) {
    this.data = paramArrayOfshort;
    this.redraw = true;
  }
  
  public void reset() {
    for (byte b = 0; b < this.data.length; b++)
      this.data[b] = 0; 
    this.redraw = true;
  }
  
  public void refresh() {
    if (this.redraw) {
      repaint();
      this.redraw = false;
    } 
  }
  
  public void startAnimation() {
    this.timer.setDelay(50);
  }
  
  public void stopAnimation() {
    this.timer.setDelay(500);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent) {
    if (this.redraw) {
      repaint();
      this.redraw = false;
    } 
  }
  
  public void paintComponent(Graphics paramGraphics) {
    super.paintComponent(paramGraphics);
    for (byte b = 0; b < 131072; b++) {
      if (this.data[b] != 0)
        if (this.data[b] == 65535) {
          paramGraphics.drawLine(this.x[b], this.y[b], this.x[b] + 15, this.y[b]);
        } else {
          short s = this.data[b];
          for (byte b1 = 0; b1 < 16; b1++) {
            if ((s & 0x1) == 1)
              paramGraphics.drawLine(this.x[b] + b1, this.y[b], this.x[b] + b1, this.y[b]); 
            s = (short)(s >> 1);
          } 
        }  
    } 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/ScreenComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */