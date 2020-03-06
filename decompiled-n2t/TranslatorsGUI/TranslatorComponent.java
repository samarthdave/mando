package TranslatorsGUI;

import Hack.ComputerParts.TextFileGUI;
import Hack.Translators.HackTranslatorEvent;
import Hack.Translators.HackTranslatorEventListener;
import Hack.Translators.HackTranslatorGUI;
import HackGUI.HTMLViewFrame;
import HackGUI.MouseOverJButton;
import HackGUI.TextFileComponent;
import HackGUI.Utilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class TranslatorComponent extends JFrame implements HackTranslatorGUI {
  protected static final int TOOLBAR_WIDTH = 1016;
  
  protected static final int TOOLBAR_HEIGHT = 55;
  
  private static final int TRANSLATOR_WIDTH = 1024;
  
  private static final int TRANSLATOR_HEIGHT = 741;
  
  protected static final Dimension separatorDimension = new Dimension(3, 50);
  
  private Vector listeners;
  
  private MouseOverJButton loadButton;
  
  private MouseOverJButton saveButton;
  
  private MouseOverJButton ffwdButton;
  
  private MouseOverJButton stopButton;
  
  private MouseOverJButton singleStepButton;
  
  private MouseOverJButton rewindButton;
  
  private MouseOverJButton fullTranslationButton;
  
  private ImageIcon ffwdIcon = new ImageIcon("bin/images/vcrfastforward.gif");
  
  private ImageIcon stopIcon = new ImageIcon("bin/images/vcrstop.gif");
  
  private ImageIcon singleStepIcon = new ImageIcon("bin/images/vcrforward.gif");
  
  private ImageIcon rewindIcon = new ImageIcon("bin/images/vcrrewind.gif");
  
  private ImageIcon fullTranslationIcon = new ImageIcon("bin/images/hex.gif");
  
  private ImageIcon loadIcon = new ImageIcon("bin/images/opendoc.gif");
  
  private ImageIcon saveIcon = new ImageIcon("bin/images/save.gif");
  
  private ImageIcon arrowIcon = new ImageIcon("bin/images/arrow2.gif");
  
  protected JToolBar toolBar;
  
  protected JMenuBar menuBar;
  
  protected JMenu fileMenu;
  
  protected JMenu runMenu;
  
  protected JMenu helpMenu;
  
  protected JMenuItem loadSourceMenuItem;
  
  protected JMenuItem saveDestMenuItem;
  
  protected JMenuItem exitMenuItem;
  
  protected JMenuItem singleStepMenuItem;
  
  protected JMenuItem ffwdMenuItem;
  
  protected JMenuItem stopMenuItem;
  
  protected JMenuItem rewindMenuItem;
  
  protected JMenuItem fullTranslationMenuItem;
  
  protected JMenuItem aboutMenuItem;
  
  protected JMenuItem usageMenuItem;
  
  protected JFileChooser sourceFileChooser;
  
  protected JFileChooser destFileChooser;
  
  private JLabel arrowLabel;
  
  private JLabel messageLbl;
  
  private TextFileComponent source;
  
  protected TextFileComponent destination;
  
  protected FileFilter sourceFilter;
  
  protected FileFilter destFilter;
  
  private HTMLViewFrame usageWindow;
  
  private HTMLViewFrame aboutWindow;
  
  public TranslatorComponent(FileFilter paramFileFilter1, FileFilter paramFileFilter2) {
    this.sourceFilter = paramFileFilter1;
    this.destFilter = paramFileFilter2;
    init();
    jbInit();
    this.source.setName("Source");
    this.destination.setName("Destination");
    this.sourceFileChooser = new JFileChooser();
    this.sourceFileChooser.setFileFilter(paramFileFilter1);
    this.destFileChooser = new JFileChooser();
    this.destFileChooser.setFileFilter(paramFileFilter2);
    this.source.enableUserInput();
    this.destination.disableUserInput();
  }
  
  public void notifyHackTranslatorListeners(byte paramByte, Object paramObject) {
    HackTranslatorEvent hackTranslatorEvent = new HackTranslatorEvent(this, paramByte, paramObject);
    for (byte b = 0; b < this.listeners.size(); b++)
      ((HackTranslatorEventListener)this.listeners.elementAt(b)).actionPerformed(hackTranslatorEvent); 
  }
  
  public void removeHackTranslatorListener(HackTranslatorEventListener paramHackTranslatorEventListener) {
    this.listeners.removeElement(paramHackTranslatorEventListener);
  }
  
  public void addHackTranslatorListener(HackTranslatorEventListener paramHackTranslatorEventListener) {
    this.listeners.addElement(paramHackTranslatorEventListener);
  }
  
  public void setWorkingDir(File paramFile) {
    this.sourceFileChooser.setCurrentDirectory(paramFile);
    this.destFileChooser.setCurrentDirectory(paramFile);
  }
  
  public void disableStop() {
    this.stopButton.setEnabled(false);
    this.stopMenuItem.setEnabled(false);
  }
  
  public void enableStop() {
    this.stopButton.setEnabled(true);
    this.stopMenuItem.setEnabled(true);
  }
  
  public void disableFastForward() {
    this.ffwdButton.setEnabled(false);
    this.ffwdMenuItem.setEnabled(false);
  }
  
  public void enableFastForward() {
    this.ffwdButton.setEnabled(true);
    this.ffwdMenuItem.setEnabled(true);
  }
  
  public void disableSingleStep() {
    this.singleStepButton.setEnabled(false);
    this.singleStepMenuItem.setEnabled(false);
  }
  
  public void enableSingleStep() {
    this.singleStepButton.setEnabled(true);
    this.singleStepMenuItem.setEnabled(true);
  }
  
  public void disableRewind() {
    this.rewindButton.setEnabled(false);
    this.rewindMenuItem.setEnabled(false);
  }
  
  public void enableRewind() {
    this.rewindButton.setEnabled(true);
    this.rewindMenuItem.setEnabled(true);
  }
  
  public void disableSave() {
    this.saveButton.setEnabled(false);
    this.saveDestMenuItem.setEnabled(false);
  }
  
  public void enableSave() {
    this.saveButton.setEnabled(true);
    this.saveDestMenuItem.setEnabled(true);
  }
  
  public void disableFullCompilation() {
    this.fullTranslationButton.setEnabled(false);
    this.fullTranslationMenuItem.setEnabled(false);
  }
  
  public void enableFullCompilation() {
    this.fullTranslationButton.setEnabled(true);
    this.fullTranslationMenuItem.setEnabled(true);
  }
  
  public void disableLoadSource() {
    this.loadButton.setEnabled(false);
    this.loadSourceMenuItem.setEnabled(false);
  }
  
  public void enableLoadSource() {
    this.loadButton.setEnabled(true);
    this.loadSourceMenuItem.setEnabled(true);
  }
  
  public void enableSourceRowSelection() {
    this.source.enableUserInput();
  }
  
  public void disableSourceRowSelection() {
    this.source.disableUserInput();
  }
  
  public void setSourceName(String paramString) {
    this.sourceFileChooser.setName(paramString);
    this.sourceFileChooser.setSelectedFile(new File(paramString));
  }
  
  public void setDestinationName(String paramString) {
    this.destFileChooser.setName(paramString);
    this.destFileChooser.setSelectedFile(new File(paramString));
  }
  
  public TextFileGUI getSource() {
    return (TextFileGUI)this.source;
  }
  
  public TextFileGUI getDestination() {
    return (TextFileGUI)this.destination;
  }
  
  public void setUsageFileName(String paramString) {
    this.usageWindow = new HTMLViewFrame(paramString);
    this.usageWindow.setSize(450, 430);
  }
  
  public void setAboutFileName(String paramString) {
    this.aboutWindow = new HTMLViewFrame(paramString);
    this.aboutWindow.setSize(450, 420);
  }
  
  public void displayMessage(String paramString, boolean paramBoolean) {
    if (paramBoolean) {
      this.messageLbl.setForeground(Color.red);
    } else {
      this.messageLbl.setForeground(UIManager.getColor("Label.foreground"));
    } 
    this.messageLbl.setText(paramString);
  }
  
  private void loadSource() {
    int i = this.sourceFileChooser.showDialog(this, "Load Source File");
    if (i == 0)
      notifyHackTranslatorListeners((byte)7, this.sourceFileChooser.getSelectedFile().getAbsolutePath()); 
  }
  
  private void saveDest() {
    int i = this.destFileChooser.showDialog(this, "Save Destination File");
    if (i == 0) {
      if (this.destFileChooser.getSelectedFile().exists()) {
        Object[] arrayOfObject = { "Yes", "No", "Cancel" };
        int j = JOptionPane.showOptionDialog(this, "File exists. Replace it ?", "Question", 1, 3, null, arrayOfObject, arrayOfObject[2]);
        if (j != 0)
          return; 
      } 
      String str = this.destFileChooser.getSelectedFile().getAbsolutePath();
      notifyHackTranslatorListeners((byte)6, str);
    } 
  }
  
  protected void arrangeToolBar() {
    this.toolBar.setSize(new Dimension(1016, 55));
    this.toolBar.add((Component)this.loadButton);
    this.toolBar.add((Component)this.saveButton);
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add((Component)this.singleStepButton);
    this.toolBar.add((Component)this.ffwdButton);
    this.toolBar.add((Component)this.stopButton);
    this.toolBar.add((Component)this.rewindButton);
    this.toolBar.addSeparator(separatorDimension);
    this.toolBar.add((Component)this.fullTranslationButton);
  }
  
  protected void arrangeMenu() {
    this.fileMenu = new JMenu("File");
    this.fileMenu.setMnemonic(70);
    this.menuBar.add(this.fileMenu);
    this.runMenu = new JMenu("Run");
    this.runMenu.setMnemonic(82);
    this.menuBar.add(this.runMenu);
    this.helpMenu = new JMenu("Help");
    this.helpMenu.setMnemonic(72);
    this.menuBar.add(this.helpMenu);
    this.loadSourceMenuItem = new JMenuItem("Load Source file", 79);
    this.loadSourceMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.loadSourceMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.fileMenu.add(this.loadSourceMenuItem);
    this.saveDestMenuItem = new JMenuItem("Save Destination file", 83);
    this.saveDestMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.saveDestMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.fileMenu.add(this.saveDestMenuItem);
    this.fileMenu.addSeparator();
    this.exitMenuItem = new JMenuItem("Exit", 88);
    this.exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(88, 8));
    this.exitMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.exitMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.fileMenu.add(this.exitMenuItem);
    this.singleStepMenuItem = new JMenuItem("Single Step", 83);
    this.singleStepMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.singleStepMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.runMenu.add(this.singleStepMenuItem);
    this.ffwdMenuItem = new JMenuItem("Fast Forward", 70);
    this.ffwdMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.ffwdMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.runMenu.add(this.ffwdMenuItem);
    this.stopMenuItem = new JMenuItem("Stop", 84);
    this.stopMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.stopMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.runMenu.add(this.stopMenuItem);
    this.rewindMenuItem = new JMenuItem("Rewind", 82);
    this.rewindMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.rewindMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.runMenu.add(this.rewindMenuItem);
    this.runMenu.addSeparator();
    this.fullTranslationMenuItem = new JMenuItem("Fast Translation", 85);
    this.fullTranslationMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.fullTranslationMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.runMenu.add(this.fullTranslationMenuItem);
    this.usageMenuItem = new JMenuItem("Usage", 85);
    this.usageMenuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
    this.usageMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.usageMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.helpMenu.add(this.usageMenuItem);
    this.aboutMenuItem = new JMenuItem("About...", 65);
    this.aboutMenuItem.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.aboutMenuItem_actionPerformed(param1ActionEvent);
          }
        });
    this.helpMenu.add(this.aboutMenuItem);
  }
  
  protected void init() {
    this.toolBar = new JToolBar();
    this.menuBar = new JMenuBar();
    this.arrowLabel = new JLabel();
    this.messageLbl = new JLabel();
    this.listeners = new Vector();
    this.ffwdButton = new MouseOverJButton();
    this.rewindButton = new MouseOverJButton();
    this.stopButton = new MouseOverJButton();
    this.singleStepButton = new MouseOverJButton();
    this.fullTranslationButton = new MouseOverJButton();
    this.saveButton = new MouseOverJButton();
    this.loadButton = new MouseOverJButton();
    this.source = new TextFileComponent();
    this.destination = new TextFileComponent();
  }
  
  protected void jbInit() {
    getContentPane().setLayout((LayoutManager)null);
    this.loadButton.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.loadButton_actionPerformed(param1ActionEvent);
          }
        });
    this.loadButton.setMaximumSize(new Dimension(39, 39));
    this.loadButton.setMinimumSize(new Dimension(39, 39));
    this.loadButton.setPreferredSize(new Dimension(39, 39));
    this.loadButton.setSize(new Dimension(39, 39));
    this.loadButton.setToolTipText("Load Source File");
    this.loadButton.setIcon(this.loadIcon);
    this.saveButton.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.saveButton_actionPerformed(param1ActionEvent);
          }
        });
    this.saveButton.setMaximumSize(new Dimension(39, 39));
    this.saveButton.setMinimumSize(new Dimension(39, 39));
    this.saveButton.setPreferredSize(new Dimension(39, 39));
    this.saveButton.setSize(new Dimension(39, 39));
    this.saveButton.setToolTipText("Save Destination File");
    this.saveButton.setIcon(this.saveIcon);
    this.singleStepButton.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.singleStepButton_actionPerformed(param1ActionEvent);
          }
        });
    this.singleStepButton.setMaximumSize(new Dimension(39, 39));
    this.singleStepButton.setMinimumSize(new Dimension(39, 39));
    this.singleStepButton.setPreferredSize(new Dimension(39, 39));
    this.singleStepButton.setSize(new Dimension(39, 39));
    this.singleStepButton.setToolTipText("Single Step");
    this.singleStepButton.setIcon(this.singleStepIcon);
    this.ffwdButton.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.ffwdButton_actionPerformed(param1ActionEvent);
          }
        });
    this.ffwdButton.setMaximumSize(new Dimension(39, 39));
    this.ffwdButton.setMinimumSize(new Dimension(39, 39));
    this.ffwdButton.setPreferredSize(new Dimension(39, 39));
    this.ffwdButton.setSize(new Dimension(39, 39));
    this.ffwdButton.setToolTipText("Fast Forward");
    this.ffwdButton.setIcon(this.ffwdIcon);
    this.rewindButton.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.rewindButton_actionPerformed(param1ActionEvent);
          }
        });
    this.rewindButton.setMaximumSize(new Dimension(39, 39));
    this.rewindButton.setMinimumSize(new Dimension(39, 39));
    this.rewindButton.setPreferredSize(new Dimension(39, 39));
    this.rewindButton.setSize(new Dimension(39, 39));
    this.rewindButton.setToolTipText("Rewind");
    this.rewindButton.setIcon(this.rewindIcon);
    this.stopButton.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.stopButton_actionPerformed(param1ActionEvent);
          }
        });
    this.stopButton.setMaximumSize(new Dimension(39, 39));
    this.stopButton.setMinimumSize(new Dimension(39, 39));
    this.stopButton.setPreferredSize(new Dimension(39, 39));
    this.stopButton.setSize(new Dimension(39, 39));
    this.stopButton.setToolTipText("Stop");
    this.stopButton.setIcon(this.stopIcon);
    this.fullTranslationButton.addActionListener(new ActionListener(this) {
          private final TranslatorComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.fullTranslationButton_actionPerformed(param1ActionEvent);
          }
        });
    this.fullTranslationButton.setMaximumSize(new Dimension(39, 39));
    this.fullTranslationButton.setMinimumSize(new Dimension(39, 39));
    this.fullTranslationButton.setPreferredSize(new Dimension(39, 39));
    this.fullTranslationButton.setSize(new Dimension(39, 39));
    this.fullTranslationButton.setToolTipText("Fast Translation");
    this.fullTranslationButton.setIcon(this.fullTranslationIcon);
    this.messageLbl.setFont(Utilities.statusLineFont);
    this.messageLbl.setBorder(BorderFactory.createLoweredBevelBorder());
    this.messageLbl.setBounds(new Rectangle(0, 672, 1016, 20));
    getContentPane().add(this.messageLbl, (Object)null);
    this.arrowLabel.setBounds(new Rectangle(290, 324, 88, 71));
    this.arrowLabel.setIcon(this.arrowIcon);
    this.source.setVisibleRows(31);
    this.destination.setVisibleRows(31);
    this.source.setBounds(new Rectangle(35, 100, this.source.getWidth(), this.source.getHeight()));
    this.destination.setBounds(new Rectangle(375, 100, this.destination.getWidth(), this.destination.getHeight()));
    getContentPane().add((Component)this.source, (Object)null);
    getContentPane().add((Component)this.destination, (Object)null);
    this.toolBar.setFloatable(false);
    this.toolBar.setLocation(0, 0);
    this.toolBar.setLayout(new FlowLayout(0, 3, 0));
    this.toolBar.setBorder(BorderFactory.createEtchedBorder());
    arrangeToolBar();
    getContentPane().add(this.toolBar, (Object)null);
    this.toolBar.revalidate();
    this.toolBar.repaint();
    repaint();
    arrangeMenu();
    setJMenuBar(this.menuBar);
    setDefaultCloseOperation(3);
    setSize(new Dimension(1024, 741));
    setVisible(true);
    getContentPane().add(this.arrowLabel, (Object)null);
  }
  
  public void singleStepButton_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)1, (Object)null);
  }
  
  public void ffwdButton_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)2, (Object)null);
  }
  
  public void stopButton_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)3, (Object)null);
  }
  
  public void rewindButton_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)4, (Object)null);
  }
  
  public void fullTranslationButton_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)5, (Object)null);
  }
  
  public void loadButton_actionPerformed(ActionEvent paramActionEvent) {
    loadSource();
  }
  
  public void saveButton_actionPerformed(ActionEvent paramActionEvent) {
    saveDest();
  }
  
  public void loadSourceMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    loadSource();
  }
  
  public void saveDestMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    saveDest();
  }
  
  public void exitMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    System.exit(0);
  }
  
  public void singleStepMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)1, (Object)null);
  }
  
  public void ffwdMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)2, (Object)null);
  }
  
  public void stopMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)3, (Object)null);
  }
  
  public void rewindMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)4, (Object)null);
  }
  
  public void fullTranslationMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    notifyHackTranslatorListeners((byte)5, (Object)null);
  }
  
  public void usageMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    if (this.usageWindow != null)
      this.usageWindow.setVisible(true); 
  }
  
  public void aboutMenuItem_actionPerformed(ActionEvent paramActionEvent) {
    if (this.aboutWindow != null)
      this.aboutWindow.setVisible(true); 
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/TranslatorsGUI.jar!/TranslatorsGUI/TranslatorComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */