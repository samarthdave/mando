package SimulatorsGUI;

import Hack.Events.ErrorEvent;
import Hack.Events.ErrorEventListener;
import Hack.Events.ProgramEvent;
import Hack.Events.ProgramEventListener;
import Hack.VMEmulator.VMEmulatorInstruction;
import Hack.VMEmulator.VMProgramGUI;
import Hack.VirtualMachine.HVMInstruction;
import HackGUI.MouseOverJButton;
import HackGUI.Utilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class ProgramComponent extends JPanel implements VMProgramGUI {
  private Vector listeners = new Vector();
  
  private Vector errorEventListeners = new Vector();
  
  protected JTable programTable = new JTable(this.model);
  
  private ProgramTableModel model = new ProgramTableModel(this);
  
  protected VMEmulatorInstruction[] instructions = new VMEmulatorInstruction[0];
  
  protected MouseOverJButton browseButton = new MouseOverJButton();
  
  private ImageIcon browseIcon = new ImageIcon("bin/images/open2.gif");
  
  private JFileChooser fileChooser = new JFileChooser();
  
  private int instructionIndex;
  
  private JTextField messageTxt = new JTextField();
  
  private ColoredTableCellRenderer coloredRenderer = new ColoredTableCellRenderer(this);
  
  private MouseOverJButton searchButton = new MouseOverJButton();
  
  private ImageIcon searchIcon = new ImageIcon("bin/images/find.gif");
  
  private SearchProgramWindow searchWindow;
  
  private JScrollPane scrollPane;
  
  private JLabel nameLbl = new JLabel();
  
  protected MouseOverJButton clearButton = new MouseOverJButton();
  
  private ImageIcon clearIcon = new ImageIcon("bin/images/smallnew.gif");
  
  public ProgramComponent() {
    this.programTable.setDefaultRenderer(this.programTable.getColumnClass(0), this.coloredRenderer);
    this.searchWindow = new SearchProgramWindow(this.programTable, (HVMInstruction[])this.instructions);
    jbInit();
  }
  
  public void addProgramListener(ProgramEventListener paramProgramEventListener) {
    this.listeners.addElement(paramProgramEventListener);
  }
  
  public void removeProgramListener(ProgramEventListener paramProgramEventListener) {
    this.listeners.removeElement(paramProgramEventListener);
  }
  
  public void notifyProgramListeners(byte paramByte, String paramString) {
    ProgramEvent programEvent = new ProgramEvent(this, paramByte, paramString);
    for (byte b = 0; b < this.listeners.size(); b++)
      ((ProgramEventListener)this.listeners.elementAt(b)).programChanged(programEvent); 
  }
  
  public void addErrorListener(ErrorEventListener paramErrorEventListener) {
    this.errorEventListeners.addElement(paramErrorEventListener);
  }
  
  public void removeErrorListener(ErrorEventListener paramErrorEventListener) {
    this.errorEventListeners.removeElement(paramErrorEventListener);
  }
  
  public void notifyErrorListeners(String paramString) {
    ErrorEvent errorEvent = new ErrorEvent(this, paramString);
    for (byte b = 0; b < this.errorEventListeners.size(); b++)
      ((ErrorEventListener)this.errorEventListeners.elementAt(b)).errorOccured(errorEvent); 
  }
  
  public void setWorkingDir(File paramFile) {
    this.fileChooser.setCurrentDirectory(paramFile);
  }
  
  public synchronized void setContents(VMEmulatorInstruction[] paramArrayOfVMEmulatorInstruction, int paramInt) {
    this.instructions = new VMEmulatorInstruction[paramInt];
    System.arraycopy(paramArrayOfVMEmulatorInstruction, 0, this.instructions, 0, paramInt);
    this.programTable.revalidate();
    try {
      wait(100L);
    } catch (InterruptedException interruptedException) {}
    this.searchWindow.setInstructions((HVMInstruction[])this.instructions);
  }
  
  public void setCurrentInstruction(int paramInt) {
    this.instructionIndex = paramInt;
    Utilities.tableCenterScroll(this, this.programTable, paramInt);
  }
  
  public void reset() {
    this.instructions = new VMEmulatorInstruction[0];
    this.programTable.clearSelection();
    repaint();
  }
  
  public void loadProgram() {
    int i = this.fileChooser.showDialog(this, "Load Program");
    if (i == 0)
      notifyProgramListeners((byte)1, this.fileChooser.getSelectedFile().getAbsolutePath()); 
  }
  
  public void browseButton_actionPerformed(ActionEvent paramActionEvent) {
    loadProgram();
  }
  
  public void hideMessage() {
    this.messageTxt.setText("");
    this.messageTxt.setVisible(false);
    this.searchButton.setVisible(true);
    this.clearButton.setVisible(true);
    this.browseButton.setVisible(true);
  }
  
  public void showMessage(String paramString) {
    this.messageTxt.setText(paramString);
    this.messageTxt.setVisible(true);
    this.searchButton.setVisible(false);
    this.clearButton.setVisible(false);
    this.browseButton.setVisible(false);
  }
  
  private void determineColumnWidth() {
    TableColumn tableColumn = null;
    for (byte b = 0; b < 3; b++) {
      tableColumn = this.programTable.getColumnModel().getColumn(b);
      if (b == 0) {
        tableColumn.setPreferredWidth(30);
      } else if (b == 1) {
        tableColumn.setPreferredWidth(40);
      } else if (b == 2) {
        tableColumn.setPreferredWidth(100);
      } 
    } 
  }
  
  public void searchButton_actionPerformed(ActionEvent paramActionEvent) {
    this.searchWindow.showWindow();
  }
  
  public void clearButton_actionPerformed(ActionEvent paramActionEvent) {
    Object[] arrayOfObject = { "Yes", "No", "Cancel" };
    int i = JOptionPane.showOptionDialog(getParent(), "Are you sure you want to clear the program?", "Warning Message", 1, 2, null, arrayOfObject, arrayOfObject[2]);
    if (i == 0)
      notifyProgramListeners((byte)3, (String)null); 
  }
  
  public void setVisibleRows(int paramInt) {
    int i = paramInt * this.programTable.getRowHeight();
    this.scrollPane.setSize(getTableWidth(), i + 3);
    setPreferredSize(new Dimension(getTableWidth(), i + 30));
    setSize(getTableWidth(), i + 30);
  }
  
  public void setNameLabel(String paramString) {
    this.nameLbl.setText(paramString);
  }
  
  public int getTableWidth() {
    return 225;
  }
  
  private void jbInit() {
    this.fileChooser.setFileSelectionMode(2);
    this.fileChooser.setFileFilter(new VMFileFilter());
    this.programTable.getTableHeader().setReorderingAllowed(false);
    this.programTable.getTableHeader().setResizingAllowed(false);
    this.scrollPane = new JScrollPane(this.programTable);
    this.scrollPane.setLocation(0, 27);
    this.browseButton.setToolTipText("Load Program");
    this.browseButton.setIcon(this.browseIcon);
    this.browseButton.setBounds(new Rectangle(119, 2, 31, 24));
    this.browseButton.addActionListener(new ActionListener(this) {
          private final ProgramComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.browseButton_actionPerformed(param1ActionEvent);
          }
        });
    this.messageTxt.setBackground(SystemColor.info);
    this.messageTxt.setEnabled(false);
    this.messageTxt.setFont(Utilities.labelsFont);
    this.messageTxt.setPreferredSize(new Dimension(70, 20));
    this.messageTxt.setDisabledTextColor(Color.red);
    this.messageTxt.setEditable(false);
    this.messageTxt.setBounds(new Rectangle(91, 2, 132, 23));
    this.messageTxt.setVisible(false);
    this.searchButton.setToolTipText("Search");
    this.searchButton.setIcon(this.searchIcon);
    this.searchButton.setBounds(new Rectangle(188, 2, 31, 24));
    this.searchButton.addActionListener(new ActionListener(this) {
          private final ProgramComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.searchButton_actionPerformed(param1ActionEvent);
          }
        });
    setForeground(Color.lightGray);
    setLayout((LayoutManager)null);
    this.nameLbl.setText("Program");
    this.nameLbl.setBounds(new Rectangle(5, 5, 73, 20));
    this.nameLbl.setFont(Utilities.labelsFont);
    this.clearButton.addActionListener(new ActionListener(this) {
          private final ProgramComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.clearButton_actionPerformed(param1ActionEvent);
          }
        });
    this.clearButton.setBounds(new Rectangle(154, 2, 31, 24));
    this.clearButton.setIcon(this.clearIcon);
    this.clearButton.setToolTipText("Clear");
    add(this.scrollPane, (Object)null);
    add(this.nameLbl, (Object)null);
    add((Component)this.searchButton, (Object)null);
    add((Component)this.clearButton, (Object)null);
    add(this.messageTxt, (Object)null);
    add((Component)this.browseButton, (Object)null);
    determineColumnWidth();
    this.programTable.setTableHeader((JTableHeader)null);
    setBorder(BorderFactory.createEtchedBorder());
  }
  
  public boolean confirmBuiltInAccess() {
    String str = "No implementation was found for some functions which are called in the VM code.\nThe VM Emulator provides built-in implementations for the OS functions.\nIf available, should this built-in implementation be used for functions which were not implemented in the VM code?";
    return (JOptionPane.showConfirmDialog(getParent(), str, "Confirmation Message", 0, 3) == 0);
  }
  
  public void notify(String paramString) {
    JOptionPane.showMessageDialog(getParent(), paramString, "Information Message", 1);
  }
  
  class ColoredTableCellRenderer extends DefaultTableCellRenderer {
    private final ProgramComponent this$0;
    
    ColoredTableCellRenderer(ProgramComponent this$0) {
      this.this$0 = this$0;
    }
    
    public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
      setEnabled((param1JTable == null || param1JTable.isEnabled()));
      setBackground((Color)null);
      setForeground((Color)null);
      if (param1Int2 == 0) {
        setHorizontalAlignment(0);
      } else {
        setHorizontalAlignment(2);
      } 
      if (param1Int1 == this.this$0.instructionIndex) {
        setBackground(Color.yellow);
      } else {
        VMEmulatorInstruction vMEmulatorInstruction = this.this$0.instructions[param1Int1];
        String str = vMEmulatorInstruction.getFormattedStrings()[0];
        if (str.equals("function") && (param1Int2 == 1 || param1Int2 == 2))
          setBackground(new Color(190, 171, 210)); 
      } 
      super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2);
      return this;
    }
  }
  
  class ProgramTableModel extends AbstractTableModel {
    private final ProgramComponent this$0;
    
    ProgramTableModel(ProgramComponent this$0) {
      this.this$0 = this$0;
    }
    
    public int getColumnCount() {
      return 3;
    }
    
    public int getRowCount() {
      return this.this$0.instructions.length;
    }
    
    public String getColumnName(int param1Int) {
      return null;
    }
    
    public Object getValueAt(int param1Int1, int param1Int2) {
      short s;
      String[] arrayOfString = this.this$0.instructions[param1Int1].getFormattedStrings();
      switch (param1Int2) {
        case 0:
          s = this.this$0.instructions[param1Int1].getIndexInFunction();
          return (s >= 0) ? new Short(s) : "";
        case 1:
          return arrayOfString[0];
        case 2:
          return arrayOfString[1] + " " + arrayOfString[2];
      } 
      return null;
    }
    
    public boolean isCellEditable(int param1Int1, int param1Int2) {
      return false;
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/ProgramComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */