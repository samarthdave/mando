package SimulatorsGUI;

import Hack.ComputerParts.ComputerPartEvent;
import Hack.ComputerParts.ComputerPartEventListener;
import Hack.ComputerParts.ComputerPartGUI;
import Hack.Events.ErrorEvent;
import Hack.Events.ErrorEventListener;
import Hack.Gates.PinInfo;
import Hack.HardwareSimulator.PinsGUI;
import HackGUI.Format;
import HackGUI.Utilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class PinsComponent extends JPanel implements PinsGUI, MouseListener, PinValueListener {
  protected BinaryComponent binary;
  
  protected JTable pinsTable;
  
  private PinInfo[] pins;
  
  private String[] valueStr;
  
  protected int dataFormat = 0;
  
  private Vector listeners;
  
  private Vector errorEventListeners;
  
  protected JScrollPane scrollPane;
  
  protected int flashIndex = -1;
  
  protected Vector highlightIndex;
  
  protected Point topLevelLocation;
  
  private PinsTableCellRenderer renderer = new PinsTableCellRenderer(this);
  
  private JLabel nameLbl = new JLabel();
  
  private boolean isEnabled = true;
  
  protected short nullValue;
  
  protected boolean hideNullValue;
  
  protected int startEnabling;
  
  protected int endEnabling;
  
  private int lastSelectedRow;
  
  public PinsComponent() {
    JTextField jTextField = new JTextField();
    jTextField.setFont(Utilities.bigBoldValueFont);
    jTextField.setBorder((Border)null);
    DefaultCellEditor defaultCellEditor = new DefaultCellEditor(jTextField);
    this.startEnabling = -1;
    this.endEnabling = -1;
    this.pins = new PinInfo[0];
    this.valueStr = new String[0];
    this.listeners = new Vector();
    this.errorEventListeners = new Vector();
    this.highlightIndex = new Vector();
    this.binary = new BinaryComponent();
    this.pinsTable = new JTable(getTableModel());
    this.pinsTable.setDefaultRenderer(this.pinsTable.getColumnClass(0), this.renderer);
    this.pinsTable.getColumnModel().getColumn(getValueColumn()).setCellEditor(defaultCellEditor);
    jbInit();
  }
  
  public void setNullValue(short paramShort, boolean paramBoolean) {
    this.nullValue = paramShort;
    this.hideNullValue = paramBoolean;
  }
  
  public void enableUserInput() {
    this.isEnabled = true;
  }
  
  public void disableUserInput() {
    this.isEnabled = false;
  }
  
  public void setDimmed(boolean paramBoolean) {
    this.pinsTable.setEnabled(!paramBoolean);
  }
  
  protected int getValueColumn() {
    return 1;
  }
  
  protected TableModel getTableModel() {
    return new PinsTableModel(this);
  }
  
  public void setPinsName(String paramString) {
    this.nameLbl.setText(paramString);
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
  
  public void addListener(ComputerPartEventListener paramComputerPartEventListener) {
    this.listeners.addElement(paramComputerPartEventListener);
  }
  
  public void removeListener(ComputerPartEventListener paramComputerPartEventListener) {
    this.listeners.removeElement(paramComputerPartEventListener);
  }
  
  public void notifyListeners(int paramInt, short paramShort) {
    ComputerPartEvent computerPartEvent = new ComputerPartEvent((ComputerPartGUI)this, paramInt, paramShort);
    for (byte b = 0; b < this.listeners.size(); b++)
      ((ComputerPartEventListener)this.listeners.elementAt(b)).valueChanged(computerPartEvent); 
  }
  
  public void notifyListeners() {
    for (byte b = 0; b < this.listeners.size(); b++)
      ((ComputerPartEventListener)this.listeners.elementAt(b)).guiGainedFocus(); 
  }
  
  public void pinValueChanged(PinValueEvent paramPinValueEvent) {
    this.pinsTable.setEnabled(true);
    if (paramPinValueEvent.getIsOk()) {
      (this.pins[this.lastSelectedRow]).value = translateValueToShort(paramPinValueEvent.getValueStr());
      this.valueStr[this.lastSelectedRow] = translateValueToString((this.pins[this.lastSelectedRow]).value, (this.pins[this.lastSelectedRow]).width);
    } 
    notifyListeners(this.lastSelectedRow, (this.pins[this.lastSelectedRow]).value);
  }
  
  public void flash(int paramInt) {
    this.flashIndex = paramInt;
    Utilities.tableCenterScroll(this, this.pinsTable, paramInt);
  }
  
  public void hideFlash() {
    this.flashIndex = -1;
    repaint();
  }
  
  public void highlight(int paramInt) {
    this.highlightIndex.addElement(new Integer(paramInt));
    repaint();
  }
  
  public void hideHighlight() {
    this.highlightIndex.removeAllElements();
    repaint();
  }
  
  public void setEnabledRange(int paramInt1, int paramInt2, boolean paramBoolean) {
    this.startEnabling = paramInt1;
    this.endEnabling = paramInt2;
  }
  
  public String getValueAsString(int paramInt) {
    return this.valueStr[paramInt];
  }
  
  public void setValueAt(int paramInt, short paramShort) {
    (this.pins[paramInt]).value = paramShort;
    this.valueStr[paramInt] = translateValueToString(paramShort, (this.pins[paramInt]).width);
  }
  
  public Point getCoordinates(int paramInt) {
    JScrollBar jScrollBar = this.scrollPane.getVerticalScrollBar();
    Rectangle rectangle = this.pinsTable.getCellRect(paramInt, 1, true);
    this.pinsTable.scrollRectToVisible(rectangle);
    return new Point((int)(rectangle.getX() + this.topLevelLocation.getX()), (int)(rectangle.getY() + this.topLevelLocation.getY() - jScrollBar.getValue()));
  }
  
  public Point getLocation(int paramInt) {
    Rectangle rectangle = this.pinsTable.getCellRect(paramInt, 0, true);
    Point point = Utilities.getTopLevelLocation(this, this.pinsTable);
    return new Point((int)(rectangle.getX() + point.getX()), (int)(rectangle.getY() + point.getY()));
  }
  
  public void reset() {
    this.pinsTable.clearSelection();
    repaint();
    hideFlash();
    hideHighlight();
  }
  
  public void setContents(PinInfo[] paramArrayOfPinInfo) {
    this.pins = new PinInfo[paramArrayOfPinInfo.length];
    this.valueStr = new String[paramArrayOfPinInfo.length];
    System.arraycopy(paramArrayOfPinInfo, 0, this.pins, 0, paramArrayOfPinInfo.length);
    for (byte b = 0; b < paramArrayOfPinInfo.length; b++)
      this.valueStr[b] = translateValueToString((paramArrayOfPinInfo[b]).value, (paramArrayOfPinInfo[b]).width); 
    this.pinsTable.clearSelection();
    this.pinsTable.revalidate();
    repaint();
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {
    if (this.isEnabled && (paramMouseEvent.getModifiers() & 0x10) != 0) {
      if (this.binary.isVisible()) {
        this.binary.hideBinary();
        this.pinsTable.setEnabled(true);
        this.pinsTable.changeSelection(this.pinsTable.rowAtPoint(paramMouseEvent.getPoint()), this.pinsTable.columnAtPoint(paramMouseEvent.getPoint()), false, false);
        this.pinsTable.grabFocus();
      } 
      if (paramMouseEvent.getClickCount() == 2 && this.dataFormat == 2) {
        this.pinsTable.setEnabled(false);
        this.binary.setLocation((int)getLocation(this.pinsTable.getSelectedRow() + 1).getX(), (int)getLocation(this.pinsTable.getSelectedRow() + 1).getY());
        this.binary.setValue((this.pins[this.pinsTable.getSelectedRow()]).value);
        this.binary.setNumOfBits((this.pins[this.pinsTable.getSelectedRow()]).width);
        this.binary.showBinary();
      } 
    } 
  }
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void setTopLevelLocation(Component paramComponent) {
    this.topLevelLocation = Utilities.getTopLevelLocation(paramComponent, this.pinsTable);
  }
  
  protected void determineColumnWidth() {
    TableColumn tableColumn = null;
    for (byte b = 0; b < 2; b++) {
      tableColumn = this.pinsTable.getColumnModel().getColumn(b);
      if (b == 0) {
        tableColumn.setPreferredWidth(116);
      } else {
        tableColumn.setPreferredWidth(124);
      } 
    } 
  }
  
  public void setNumericFormat(int paramInt) {
    this.dataFormat = paramInt;
    for (byte b = 0; b < this.pins.length; b++)
      this.valueStr[b] = translateValueToString((this.pins[b]).value, (this.pins[b]).width); 
    repaint();
  }
  
  protected short translateValueToShort(String paramString) {
    return Format.translateValueToShort(paramString, this.dataFormat);
  }
  
  protected String translateValueToString(short paramShort, int paramInt) {
    String str = null;
    if (paramShort == this.nullValue && this.hideNullValue) {
      str = "";
    } else {
      str = Format.translateValueToString(paramShort, this.dataFormat);
      if (this.dataFormat == 2)
        str = str.substring(str.length() - paramInt, str.length()); 
    } 
    return str;
  }
  
  public void setVisibleRows(int paramInt) {
    int i = paramInt * this.pinsTable.getRowHeight();
    this.scrollPane.setSize(getTableWidth(), i + 3);
    setPreferredSize(new Dimension(getTableWidth(), i + 30));
    setSize(getTableWidth(), i + 30);
  }
  
  public int getTableWidth() {
    return 241;
  }
  
  private void jbInit() {
    this.pinsTable.addFocusListener(new FocusListener(this) {
          private final PinsComponent this$0;
          
          public void focusGained(FocusEvent param1FocusEvent) {
            this.this$0.pinsTable_focusGained(param1FocusEvent);
          }
          
          public void focusLost(FocusEvent param1FocusEvent) {
            this.this$0.pinsTable_focusLost(param1FocusEvent);
          }
        });
    this.pinsTable.addMouseListener(this);
    this.pinsTable.getTableHeader().setReorderingAllowed(false);
    this.pinsTable.getTableHeader().setResizingAllowed(false);
    setLayout((LayoutManager)null);
    this.scrollPane = new JScrollPane(this.pinsTable);
    this.scrollPane.setLocation(0, 27);
    setBorder(BorderFactory.createEtchedBorder());
    this.binary.setSize(new Dimension(240, 52));
    this.binary.setLayout((LayoutManager)null);
    this.binary.setVisible(false);
    this.binary.addListener(this);
    determineColumnWidth();
    this.nameLbl.setText("Name :");
    this.nameLbl.setBounds(new Rectangle(3, 3, 102, 21));
    this.nameLbl.setFont(Utilities.labelsFont);
    this.pinsTable.setFont(Utilities.valueFont);
    add(this.binary, (Object)null);
    add(this.scrollPane, (Object)null);
    add(this.nameLbl, (Object)null);
  }
  
  public void pinsTable_focusGained(FocusEvent paramFocusEvent) {
    notifyListeners();
  }
  
  public void pinsTable_focusLost(FocusEvent paramFocusEvent) {
    this.lastSelectedRow = this.pinsTable.getSelectedRow();
    this.pinsTable.clearSelection();
  }
  
  class PinsTableCellRenderer extends DefaultTableCellRenderer {
    private final PinsComponent this$0;
    
    PinsTableCellRenderer(PinsComponent this$0) {
      this.this$0 = this$0;
    }
    
    public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
      setEnabled((param1JTable == null || param1JTable.isEnabled()));
      if (param1Int2 == 0) {
        setHorizontalAlignment(0);
        setForeground((Color)null);
        setBackground((Color)null);
      } else {
        setHorizontalAlignment(4);
        for (byte b = 0; b < this.this$0.highlightIndex.size(); b++) {
          if (param1Int1 == ((Integer)this.this$0.highlightIndex.elementAt(b)).intValue()) {
            setForeground(Color.blue);
            break;
          } 
          setForeground((Color)null);
        } 
        if (param1Int1 == this.this$0.flashIndex) {
          setBackground(Color.orange);
        } else {
          setBackground((Color)null);
        } 
      } 
      super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2);
      return this;
    }
  }
  
  class PinsTableModel extends AbstractTableModel {
    String[] columnNames;
    
    private final PinsComponent this$0;
    
    PinsTableModel(PinsComponent this$0) {
      this.this$0 = this$0;
      this.columnNames = new String[] { "Name", "Value" };
    }
    
    public int getColumnCount() {
      return this.columnNames.length;
    }
    
    public int getRowCount() {
      return this.this$0.pins.length;
    }
    
    public String getColumnName(int param1Int) {
      return this.columnNames[param1Int];
    }
    
    public Object getValueAt(int param1Int1, int param1Int2) {
      return (param1Int2 == 0) ? ((this.this$0.pins[param1Int1]).name + (((this.this$0.pins[param1Int1]).width > 1) ? ("[" + (this.this$0.pins[param1Int1]).width + "]") : "")) : this.this$0.valueStr[param1Int1];
    }
    
    public boolean isCellEditable(int param1Int1, int param1Int2) {
      return (this.this$0.isEnabled && param1Int2 == 1 && this.this$0.dataFormat != 2 && (this.this$0.endEnabling == -1 || (param1Int1 >= this.this$0.startEnabling && param1Int1 <= this.this$0.endEnabling)));
    }
    
    public void setValueAt(Object param1Object, int param1Int1, int param1Int2) {
      String str = (String)param1Object;
      if (!this.this$0.valueStr[param1Int1].equals(str)) {
        try {
          this.this$0.valueStr[param1Int1] = str;
          if (str.equals("") && this.this$0.hideNullValue) {
            (this.this$0.pins[param1Int1]).value = this.this$0.nullValue;
          } else {
            (this.this$0.pins[param1Int1]).value = Format.translateValueToShort(str, this.this$0.dataFormat);
          } 
          this.this$0.notifyListeners((short)param1Int1, (this.this$0.pins[param1Int1]).value);
        } catch (NumberFormatException numberFormatException) {
          this.this$0.notifyErrorListeners("Illegal value");
          this.this$0.valueStr[param1Int1] = Format.translateValueToString((this.this$0.pins[param1Int1]).value, this.this$0.dataFormat);
        } 
        this.this$0.repaint();
      } 
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/PinsComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */