package SimulatorsGUI;

import Hack.Controller.HackSimulatorGUI;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class HackSimulatorComponent extends JPanel implements HackSimulatorGUI {
  protected JComponent currentAdditionalDisplay = null;
  
  protected String usageFileName;
  
  protected String aboutFileName;
  
  public void setAdditionalDisplay(JComponent paramJComponent) {
    if (this.currentAdditionalDisplay != null)
      remove(this.currentAdditionalDisplay); 
    JComponent jComponent = paramJComponent;
    this.currentAdditionalDisplay = paramJComponent;
    if (paramJComponent != null) {
      paramJComponent.setLocation(getAdditionalDisplayLocation());
      add(paramJComponent, 1);
      paramJComponent.revalidate();
    } 
    revalidate();
    repaint();
  }
  
  protected abstract Point getAdditionalDisplayLocation();
  
  public void setUsageFileName(String paramString) {
    this.usageFileName = paramString;
  }
  
  public void setAboutFileName(String paramString) {
    this.aboutFileName = paramString;
  }
  
  public String getUsageFileName() {
    return this.usageFileName;
  }
  
  public String getAboutFileName() {
    return this.aboutFileName;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/HackSimulatorComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */