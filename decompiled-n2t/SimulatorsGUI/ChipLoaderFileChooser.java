package SimulatorsGUI;

import HackGUI.FileChooserComponent;
import HackGUI.FilesTypeEvent;
import HackGUI.FilesTypeListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ChipLoaderFileChooser extends JFrame {
  private FileChooserComponent workingDir = new FileChooserComponent();
  
  private FileChooserComponent builtInDir = new FileChooserComponent();
  
  private ImageIcon okIcon = new ImageIcon("bin/images/ok.gif");
  
  private ImageIcon cancelIcon = new ImageIcon("bin/images/cancel.gif");
  
  private JButton okButton = new JButton();
  
  private JButton cancelButton = new JButton();
  
  private Vector listeners = new Vector();
  
  public ChipLoaderFileChooser() {
    super("Directories Selection");
    setSelectionToDirectory();
    setNames();
    jbInit();
  }
  
  public void showWindow() {
    setVisible(true);
    this.workingDir.getTextField().requestFocus();
  }
  
  public void addListener(FilesTypeListener paramFilesTypeListener) {
    this.listeners.addElement(paramFilesTypeListener);
  }
  
  public void removeListener(FilesTypeListener paramFilesTypeListener) {
    this.listeners.removeElement(paramFilesTypeListener);
  }
  
  public void notifyListeners(String paramString1, String paramString2) {
    FilesTypeEvent filesTypeEvent = new FilesTypeEvent(this, paramString1, paramString2, null);
    for (byte b = 0; b < this.listeners.size(); b++)
      ((FilesTypeListener)this.listeners.elementAt(b)).filesNamesChanged(filesTypeEvent); 
  }
  
  public void setWorkingDir(File paramFile) {
    this.workingDir.setCurrentFileName(paramFile.getName());
    this.workingDir.showCurrentFileName();
  }
  
  public void setBuiltInDir(File paramFile) {
    this.builtInDir.setCurrentFileName(paramFile.getName());
    this.builtInDir.showCurrentFileName();
  }
  
  private void setSelectionToDirectory() {
    this.workingDir.setSelectionToDirectories();
    this.builtInDir.setSelectionToDirectories();
  }
  
  private void setNames() {
    this.workingDir.setName("Working Dir :");
    this.builtInDir.setName("BuiltIn Dir :");
  }
  
  private void jbInit() {
    getContentPane().setLayout((LayoutManager)null);
    this.workingDir.setBounds(new Rectangle(5, 2, 485, 48));
    this.builtInDir.setBounds(new Rectangle(5, 38, 485, 48));
    this.okButton.setToolTipText("OK");
    this.okButton.setIcon(this.okIcon);
    this.okButton.setBounds(new Rectangle(90, 95, 63, 44));
    this.okButton.addActionListener(new ActionListener(this) {
          private final ChipLoaderFileChooser this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.okButton_actionPerformed(param1ActionEvent);
          }
        });
    this.cancelButton.setBounds(new Rectangle(265, 95, 63, 44));
    this.cancelButton.addActionListener(new ActionListener(this) {
          private final ChipLoaderFileChooser this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.cancelButton_actionPerformed(param1ActionEvent);
          }
        });
    this.cancelButton.setToolTipText("CANCEL");
    this.cancelButton.setIcon(this.cancelIcon);
    getContentPane().add((Component)this.workingDir, (Object)null);
    getContentPane().add((Component)this.builtInDir, (Object)null);
    getContentPane().add(this.okButton, (Object)null);
    getContentPane().add(this.cancelButton, (Object)null);
    setSize(470, 210);
    setLocation(250, 250);
  }
  
  public void okButton_actionPerformed(ActionEvent paramActionEvent) {
    String str1 = null;
    String str2 = null;
    if (this.workingDir.isFileNameChanged()) {
      str1 = this.workingDir.getFileName();
      this.workingDir.setCurrentFileName(str1);
      this.workingDir.showCurrentFileName();
    } 
    if (this.builtInDir.isFileNameChanged()) {
      str2 = this.builtInDir.getFileName();
      this.builtInDir.setCurrentFileName(str2);
      this.builtInDir.showCurrentFileName();
    } 
    if (str1 != null || str2 != null)
      notifyListeners(str1, str2); 
    setVisible(false);
  }
  
  public void cancelButton_actionPerformed(ActionEvent paramActionEvent) {
    this.workingDir.showCurrentFileName();
    this.builtInDir.showCurrentFileName();
    setVisible(false);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/ChipLoaderFileChooser.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */