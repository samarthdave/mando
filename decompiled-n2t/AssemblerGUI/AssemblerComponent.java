package AssemblerGUI;

import Hack.Assembler.HackAssemblerGUI;
import Hack.ComputerParts.TextFileGUI;
import HackGUI.MouseOverJButton;
import HackGUI.TextFileComponent;
import TranslatorsGUI.TranslatorComponent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

public class AssemblerComponent extends TranslatorComponent implements HackAssemblerGUI {
  private ImageIcon equalIcon;
  
  private MouseOverJButton compareButton;
  
  private JMenuItem loadCompareMenuItem;
  
  private JLabel equalLabel;
  
  private TextFileComponent comparison;
  
  protected JFileChooser compareFileChooser;
  
  public AssemblerComponent() {
    super(new ASMFileFilter(), new HACKFileFilter());
    this.comparison.disableUserInput();
    this.comparison.setName("Comparison");
    this.compareFileChooser = new JFileChooser();
    this.compareFileChooser.setFileFilter(this.destFilter);
  }
  
  public void setWorkingDir(File paramFile) {
    super.setWorkingDir(paramFile);
    this.compareFileChooser.setCurrentDirectory(paramFile);
  }
  
  public void disableLoadComparison() {
    this.compareButton.setEnabled(false);
    this.loadCompareMenuItem.setEnabled(false);
  }
  
  public void enableLoadComparison() {
    this.compareButton.setEnabled(true);
    this.loadCompareMenuItem.setEnabled(true);
  }
  
  public void setComparisonName(String paramString) {
    this.compareFileChooser.setName(paramString);
    this.compareFileChooser.setSelectedFile(new File(paramString));
  }
  
  public void showComparison() {
    this.comparison.setVisible(true);
    this.equalLabel.setVisible(true);
  }
  
  public void hideComparison() {
    this.comparison.setVisible(false);
    this.equalLabel.setVisible(false);
  }
  
  public TextFileGUI getComparison() {
    return (TextFileGUI)this.comparison;
  }
  
  protected void arrangeMenu() {
    super.arrangeMenu();
    this.fileMenu.removeAll();
    this.fileMenu.add(this.loadSourceMenuItem);
    this.loadCompareMenuItem = new JMenuItem("Load Comparison File", 67);
    this.loadCompareMenuItem.addActionListener(new ActionListener(this) {
          private final AssemblerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.loadCompareMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.fileMenu.add(this.saveDestMenuItem);
    this.fileMenu.add(this.loadCompareMenuItem);
    this.fileMenu.addSeparator();
    this.fileMenu.add(this.exitMenuItem);
  }
  
  protected void init() {
    super.init();
    this.equalIcon = new ImageIcon("bin/images/equal.gif");
    this.equalLabel = new JLabel();
    this.comparison = new TextFileComponent();
    this.compareButton = new MouseOverJButton();
  }
  
  private void loadComparison() {
    int i = this.compareFileChooser.showDialog((Component)this, "Load Comparison File");
    if (i == 0)
      notifyHackTranslatorListeners((byte)9, this.compareFileChooser.getSelectedFile().getAbsolutePath()); 
  }
  
  protected void arrangeToolBar() {
    super.arrangeToolBar();
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add((Component)this.compareButton);
  }
  
  protected void jbInit() {
    super.jbInit();
    this.equalLabel.setBounds(new Rectangle(632, 324, 88, 71));
    this.equalLabel.setIcon(this.equalIcon);
    this.equalLabel.setVisible(false);
    this.comparison.setVisibleRows(31);
    this.comparison.setVisible(false);
    this.comparison.setBounds(new Rectangle(725, 100, this.comparison.getWidth(), this.comparison.getHeight()));
    this.compareButton.addActionListener(new ActionListener(this) {
          private final AssemblerComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.loadCompareButton_actionPerformed(param1ActionEvent);
          }
        });
    this.compareButton.setMaximumSize(new Dimension(39, 39));
    this.compareButton.setMinimumSize(new Dimension(39, 39));
    this.compareButton.setPreferredSize(new Dimension(39, 39));
    this.compareButton.setSize(new Dimension(39, 39));
    this.compareButton.setToolTipText("Load Comparison File");
    this.compareButton.setIcon(new ImageIcon("bin/images/smallequal.gif"));
    getContentPane().add(this.equalLabel, (Object)null);
    getContentPane().add((Component)this.comparison, (Object)null);
  }
  
  public void loadCompareMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    loadComparison();
  }
  
  public void loadCompareButton_actionPerformed(ActionEvent paramActionEvent) {
    loadComparison();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/AssemblerGUI.jar!/AssemblerGUI/AssemblerComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */