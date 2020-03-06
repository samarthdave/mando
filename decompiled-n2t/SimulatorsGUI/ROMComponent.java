package SimulatorsGUI;

import Hack.Assembler.AssemblerException;
import Hack.Assembler.HackAssemblerTranslator;
import Hack.CPUEmulator.ROMGUI;
import Hack.Events.ProgramEvent;
import Hack.Events.ProgramEventListener;
import HackGUI.Format;
import HackGUI.MouseOverJButton;
import HackGUI.PointedMemoryComponent;
import HackGUI.TranslationException;
import HackGUI.Utilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

public class ROMComponent extends PointedMemoryComponent implements ROMGUI {
  private Vector programEventListeners = new Vector();
  
  private static final int ASM_FORMAT = 4;
  
  protected MouseOverJButton loadButton = new MouseOverJButton();
  
  private ImageIcon loadIcon = new ImageIcon("bin/images/open2.gif");
  
  private FileFilter filter = new ROMFileFilter();
  
  private JFileChooser fileChooser = new JFileChooser();
  
  private HackAssemblerTranslator translator = HackAssemblerTranslator.getInstance();
  
  private JTextField messageTxt = new JTextField();
  
  private String[] format = new String[] { "Asm", "Dec", "Hex", "Bin" };
  
  protected JComboBox romFormat = new JComboBox(this.format);
  
  private String programFileName;
  
  public ROMComponent() {
    this.fileChooser.setFileFilter(this.filter);
    jbInit();
  }
  
  public void setNumericFormat(int paramInt) {
    super.setNumericFormat(paramInt);
    switch (paramInt) {
      case 4:
        this.romFormat.setSelectedIndex(0);
        break;
      case 0:
        this.romFormat.setSelectedIndex(1);
        break;
      case 1:
        this.romFormat.setSelectedIndex(2);
        break;
      case 2:
        this.romFormat.setSelectedIndex(3);
        break;
    } 
  }
  
  public void addProgramListener(ProgramEventListener paramProgramEventListener) {
    this.programEventListeners.addElement(paramProgramEventListener);
  }
  
  public void removeProgramListener(ProgramEventListener paramProgramEventListener) {
    this.programEventListeners.removeElement(paramProgramEventListener);
  }
  
  public void notifyProgramListeners(byte paramByte, String paramString) {
    ProgramEvent programEvent = new ProgramEvent(this, paramByte, paramString);
    for (byte b = 0; b < this.programEventListeners.size(); b++)
      ((ProgramEventListener)this.programEventListeners.elementAt(b)).programChanged(programEvent); 
  }
  
  public void notifyClearListeners() {
    super.notifyClearListeners();
    notifyProgramListeners((byte)3, (String)null);
  }
  
  protected DefaultTableCellRenderer getCellRenderer() {
    return (DefaultTableCellRenderer)new ROMTableCellRenderer(this);
  }
  
  public void setProgram(String paramString) {
    this.programFileName = paramString;
  }
  
  public String getValueAsString(int paramInt) {
    return (this.dataFormat != 4) ? super.getValueAsString(paramInt) : Format.translateValueToString(this.values[paramInt], 0);
  }
  
  public void hideMessage() {
    this.messageTxt.setText("");
    this.messageTxt.setVisible(false);
    this.loadButton.setVisible(true);
    this.searchButton.setVisible(true);
    this.romFormat.setVisible(true);
  }
  
  public void showMessage(String paramString) {
    this.messageTxt.setText(paramString);
    this.loadButton.setVisible(false);
    this.searchButton.setVisible(false);
    this.romFormat.setVisible(false);
    this.messageTxt.setVisible(true);
  }
  
  protected short translateValueToShort(String paramString) throws TranslationException {
    short s = 0;
    if (this.dataFormat != 4) {
      s = super.translateValueToShort(paramString);
    } else {
      try {
        s = this.translator.textToCode(paramString);
      } catch (AssemblerException assemblerException) {
        throw new TranslationException(assemblerException.getMessage());
      } 
    } 
    return s;
  }
  
  protected String translateValueToString(short paramShort) {
    String str = null;
    if (this.dataFormat != 4) {
      str = super.translateValueToString(paramShort);
    } else {
      try {
        str = this.translator.codeToText(paramShort);
      } catch (AssemblerException assemblerException) {}
    } 
    return str;
  }
  
  private void jbInit() {
    this.loadButton.setIcon(this.loadIcon);
    this.loadButton.setBounds(new Rectangle(97, 2, 31, 25));
    this.loadButton.setToolTipText("Load Program");
    this.loadButton.addActionListener(new ActionListener(this) {
          private final ROMComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.loadButton_actionPerformed(param1ActionEvent);
          }
        });
    this.messageTxt.setBackground(SystemColor.info);
    this.messageTxt.setEnabled(false);
    this.messageTxt.setFont(Utilities.labelsFont);
    this.messageTxt.setPreferredSize(new Dimension(70, 20));
    this.messageTxt.setDisabledTextColor(Color.red);
    this.messageTxt.setEditable(false);
    this.messageTxt.setHorizontalAlignment(0);
    this.messageTxt.setBounds(new Rectangle(37, 3, 154, 22));
    this.messageTxt.setVisible(false);
    this.romFormat.setPreferredSize(new Dimension(125, 23));
    this.romFormat.setBounds(new Rectangle(39, 3, 56, 23));
    this.romFormat.setFont(Utilities.thinLabelsFont);
    this.romFormat.setToolTipText("Display Format");
    this.romFormat.addActionListener(new ActionListener(this) {
          private final ROMComponent this$0;
          
          public void actionPerformed(ActionEvent param1ActionEvent) {
            this.this$0.romFormat_actionPerformed(param1ActionEvent);
          }
        });
    add(this.messageTxt, null);
    add((Component)this.loadButton);
    add(this.romFormat, null);
  }
  
  public void setWorkingDir(File paramFile) {
    this.fileChooser.setCurrentDirectory(paramFile);
  }
  
  public void loadProgram() {
    int i = this.fileChooser.showDialog((Component)this, "Load ROM");
    if (i == 0)
      notifyProgramListeners((byte)1, this.fileChooser.getSelectedFile().getAbsolutePath()); 
  }
  
  public void loadButton_actionPerformed(ActionEvent paramActionEvent) {
    loadProgram();
  }
  
  public void romFormat_actionPerformed(ActionEvent paramActionEvent) {
    String str = (String)this.romFormat.getSelectedItem();
    if (str.equals(this.format[0])) {
      setNumericFormat(4);
    } else if (str.equals(this.format[1])) {
      setNumericFormat(0);
    } else if (str.equals(this.format[2])) {
      setNumericFormat(1);
    } else if (str.equals(this.format[3])) {
      setNumericFormat(2);
    } 
  }
  
  public class ROMTableCellRenderer extends PointedMemoryComponent.PointedMemoryTableCellRenderer {
    private final ROMComponent this$0;
    
    public ROMTableCellRenderer(ROMComponent this$0) {
      super(this$0);
      this.this$0 = this$0;
    }
    
    public void setRenderer(int param1Int1, int param1Int2) {
      super.setRenderer(param1Int1, param1Int2);
      if (this.this$0.dataFormat == 4 && param1Int2 == 1)
        setHorizontalAlignment(2); 
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/ROMComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */