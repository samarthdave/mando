package SimulatorsGUI;

import Hack.Gates.GatesPanelGUI;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class GatesPanel implements GatesPanelGUI {
  private JPanel nullLayoutGatesPanel = new JPanel();
  
  private JPanel flowLayoutGatesPanel = new JPanel();
  
  private boolean flowLayout = false;
  
  public GatesPanel() {
    this.nullLayoutGatesPanel.setLayout((LayoutManager)null);
    this.flowLayoutGatesPanel.setLayout(new FlowLayout(1, 1, 1));
  }
  
  public void addGateComponent(Component paramComponent) {
    this.flowLayoutGatesPanel.add(paramComponent);
    if (this.flowLayout) {
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
    this.flowLayout = false;
    this.nullLayoutGatesPanel.removeAll();
    this.flowLayoutGatesPanel.removeAll();
    this.nullLayoutGatesPanel.revalidate();
    this.flowLayoutGatesPanel.revalidate();
    this.nullLayoutGatesPanel.repaint();
    this.flowLayoutGatesPanel.repaint();
  }
  
  public JPanel getGatesPanel() {
    return this.flowLayout ? this.flowLayoutGatesPanel : this.nullLayoutGatesPanel;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/GatesPanel.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */