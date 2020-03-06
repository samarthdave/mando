package SimulatorsGUI;

import Hack.HardwareSimulator.HardwareSimulator;
import Hack.HardwareSimulator.PartPinInfo;
import Hack.HardwareSimulator.PartPinsGUI;
import HackGUI.Format;
import HackGUI.Utilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class PartPinsComponent extends PinsComponent implements PartPinsGUI {
  private PartPinInfo[] partPins = new PartPinInfo[0];
  
  private String[] valuesStr = new String[0];
  
  private PartPinsTableCellRenderer renderer = new PartPinsTableCellRenderer(this);
  
  private boolean isEnabled = true;
  
  private JLabel partNameLbl = new JLabel();
  
  public PartPinsComponent() {
    this.pinsTable.setDefaultRenderer(this.pinsTable.getColumnClass(0), this.renderer);
    jbInit();
  }
  
  public void setPartName(String paramString) {
    this.partNameLbl.setText(paramString);
  }
  
  public void enableUserInput() {
    this.isEnabled = true;
  }
  
  public void disableUserInput() {
    this.isEnabled = false;
  }
  
  protected int getValueColumn() {
    return 2;
  }
  
  protected TableModel getTableModel() {
    return new PartPinsTableModel(this);
  }
  
  public void pinValueChanged(PinValueEvent paramPinValueEvent) {
    this.pinsTable.setEnabled(true);
    if (paramPinValueEvent.getIsOk()) {
      this.valuesStr[this.pinsTable.getSelectedRow()] = paramPinValueEvent.getValueStr();
      (this.partPins[this.pinsTable.getSelectedRow()]).value = Format.translateValueToShort(paramPinValueEvent.getValueStr(), this.dataFormat);
    } 
    notifyListeners(this.pinsTable.getSelectedRow(), Format.translateValueToShort(paramPinValueEvent.getValueStr(), this.dataFormat));
  }
  
  public Point getCoordinates(int paramInt) {
    JScrollBar jScrollBar = this.scrollPane.getVerticalScrollBar();
    Rectangle rectangle = this.pinsTable.getCellRect(paramInt, 2, true);
    this.pinsTable.scrollRectToVisible(rectangle);
    return new Point((int)(rectangle.getX() + this.topLevelLocation.getX()), (int)(rectangle.getY() + this.topLevelLocation.getY() - jScrollBar.getValue()));
  }
  
  public void setContents(Vector paramVector) {
    this.partPins = new PartPinInfo[paramVector.size()];
    this.valuesStr = new String[paramVector.size()];
    paramVector.toArray((Object[])this.partPins);
    for (byte b = 0; b < this.partPins.length; b++)
      this.valuesStr[b] = Format.translateValueToString((this.partPins[b]).value, this.dataFormat); 
    this.pinsTable.clearSelection();
    this.pinsTable.revalidate();
    repaint();
  }
  
  public String getValueAsString(int paramInt) {
    return this.valuesStr[paramInt];
  }
  
  public void setValueAt(int paramInt, short paramShort) {
    (this.partPins[paramInt]).value = paramShort;
    this.valuesStr[paramInt] = Format.translateValueToString(paramShort, this.dataFormat);
    repaint();
  }
  
  public void reset() {
    this.pinsTable.clearSelection();
    repaint();
    hideFlash();
    hideHighlight();
  }
  
  protected void determineColumnWidth() {
    TableColumn tableColumn = null;
    for (byte b = 0; b < 2; b++) {
      tableColumn = this.pinsTable.getColumnModel().getColumn(b);
      if (b == 0) {
        tableColumn.setPreferredWidth(20);
      } else if (b == 1) {
        tableColumn.setPreferredWidth(20);
      } else if (b == 2) {
        tableColumn.setPreferredWidth(180);
      } 
    } 
  }
  
  private void jbInit() {
    this.partNameLbl.setFont(Utilities.bigLabelsFont);
    this.partNameLbl.setHorizontalAlignment(0);
    this.partNameLbl.setText("keyboard");
    this.partNameLbl.setForeground(Color.black);
    this.partNameLbl.setBounds(new Rectangle(62, 10, 102, 21));
    add(this.partNameLbl, (Object)null);
  }
  
  class PartPinsTableCellRenderer extends DefaultTableCellRenderer {
    private final PartPinsComponent this$0;
    
    PartPinsTableCellRenderer(PartPinsComponent this$0) {
      this.this$0 = this$0;
    }
    
    public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
      setEnabled((param1JTable == null || param1JTable.isEnabled()));
      if (param1Int2 == 0 || param1Int2 == 1) {
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
  
  class PartPinsTableModel extends AbstractTableModel {
    String[] columnNames;
    
    private final PartPinsComponent this$0;
    
    PartPinsTableModel(PartPinsComponent this$0) {
      this.this$0 = this$0;
      this.columnNames = new String[] { "Part pin", "Gate pin", "Value" };
    }
    
    public int getColumnCount() {
      return this.columnNames.length;
    }
    
    public int getRowCount() {
      return (this.this$0.partPins == null) ? 0 : this.this$0.partPins.length;
    }
    
    public String getColumnName(int param1Int) {
      return this.columnNames[param1Int];
    }
    
    public Object getValueAt(int param1Int1, int param1Int2) {
      String str = "";
      if (param1Int2 == 0) {
        str = HardwareSimulator.getFullPinName((this.this$0.partPins[param1Int1]).partPinName, (this.this$0.partPins[param1Int1]).partPinSubBus);
      } else if (param1Int2 == 1) {
        str = HardwareSimulator.getFullPinName((this.this$0.partPins[param1Int1]).gatePinName, (this.this$0.partPins[param1Int1]).gatePinSubBus);
      } else if (param1Int2 == 2) {
        str = this.this$0.valuesStr[param1Int1];
      } 
      return str;
    }
    
    public boolean isCellEditable(int param1Int1, int param1Int2) {
      return false;
    }
    
    public void setValueAt(Object param1Object, int param1Int1, int param1Int2) {
      String str = ((String)param1Object).trim();
      try {
        this.this$0.valuesStr[param1Int1] = str;
        (this.this$0.partPins[param1Int1]).value = Format.translateValueToShort(str, this.this$0.dataFormat);
        this.this$0.notifyListeners((short)param1Int1, (this.this$0.partPins[param1Int1]).value);
      } catch (NumberFormatException numberFormatException) {
        this.this$0.notifyErrorListeners("Illegal value");
        this.this$0.valuesStr[param1Int1] = Format.translateValueToString((this.this$0.partPins[param1Int1]).value, this.this$0.dataFormat);
      } 
      this.this$0.repaint();
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/PartPinsComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */