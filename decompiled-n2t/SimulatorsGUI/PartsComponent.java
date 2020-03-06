package SimulatorsGUI;

import Hack.Gates.Gate;
import Hack.HardwareSimulator.PartsGUI;
import HackGUI.Utilities;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class PartsComponent extends JPanel implements PartsGUI {
  private static final String BUILTIN_GATE = "BuiltIn";
  
  private static final String COMPOSITE_GATE = "Composite";
  
  private JTable partsTable = new JTable(this.model);
  
  private Gate[] parts = new Gate[0];
  
  private JScrollPane scrollPane;
  
  private PartsTableModel model = new PartsTableModel(this);
  
  private JLabel nameLbl = new JLabel();
  
  public PartsComponent() {
    jbInit();
  }
  
  public void setVisibleRows(int paramInt) {
    int i = paramInt * this.partsTable.getRowHeight();
    this.scrollPane.setSize(getTableWidth(), i + 3);
    setPreferredSize(new Dimension(getTableWidth(), i + 30));
    setSize(getTableWidth(), i + 30);
  }
  
  public int getTableWidth() {
    return 241;
  }
  
  public void setName(String paramString) {
    this.nameLbl.setText(paramString);
  }
  
  public void setContents(Gate[] paramArrayOfGate) {
    this.parts = new Gate[paramArrayOfGate.length];
    System.arraycopy(paramArrayOfGate, 0, this.parts, 0, paramArrayOfGate.length);
    this.partsTable.clearSelection();
    this.partsTable.revalidate();
  }
  
  public void reset() {
    this.partsTable.clearSelection();
    repaint();
  }
  
  private void determineColumnWidth() {
    TableColumn tableColumn = null;
    for (byte b = 0; b < 3; b++) {
      tableColumn = this.partsTable.getColumnModel().getColumn(b);
      if (b == 0) {
        tableColumn.setPreferredWidth(110);
      } else if (b == 1) {
        tableColumn.setPreferredWidth(72);
      } else if (b == 2) {
        tableColumn.setPreferredWidth(55);
      } 
    } 
  }
  
  private void jbInit() {
    setLayout((LayoutManager)null);
    this.partsTable.setFont(Utilities.valueFont);
    this.partsTable.getTableHeader().setReorderingAllowed(false);
    this.partsTable.getTableHeader().setResizingAllowed(false);
    this.partsTable.addFocusListener(new FocusListener(this) {
          private final PartsComponent this$0;
          
          public void focusGained(FocusEvent param1FocusEvent) {
            this.this$0.partsTable_focusGained(param1FocusEvent);
          }
          
          public void focusLost(FocusEvent param1FocusEvent) {
            this.this$0.partsTable_focusLost(param1FocusEvent);
          }
        });
    setBorder(BorderFactory.createEtchedBorder());
    this.scrollPane = new JScrollPane(this.partsTable);
    this.scrollPane.setLocation(0, 27);
    this.nameLbl.setText("Name :");
    this.nameLbl.setBounds(new Rectangle(3, 3, 102, 21));
    this.nameLbl.setFont(Utilities.labelsFont);
    add(this.scrollPane, (Object)null);
    add(this.nameLbl, (Object)null);
    determineColumnWidth();
  }
  
  public void partsTable_focusGained(FocusEvent paramFocusEvent) {}
  
  public void partsTable_focusLost(FocusEvent paramFocusEvent) {
    this.partsTable.clearSelection();
  }
  
  class PartsTableModel extends AbstractTableModel {
    String[] columnNames;
    
    private final PartsComponent this$0;
    
    PartsTableModel(PartsComponent this$0) {
      this.this$0 = this$0;
      this.columnNames = new String[] { "Chip Name", "Type", "Clocked" };
    }
    
    public int getColumnCount() {
      return this.columnNames.length;
    }
    
    public int getRowCount() {
      return this.this$0.parts.length;
    }
    
    public String getColumnName(int param1Int) {
      return this.columnNames[param1Int];
    }
    
    public Object getValueAt(int param1Int1, int param1Int2) {
      Boolean bool;
      String str = null;
      if (param1Int2 == 0) {
        str = this.this$0.parts[param1Int1].getGateClass().getName();
      } else if (param1Int2 == 1) {
        if (this.this$0.parts[param1Int1] instanceof Hack.Gates.CompositeGate) {
          str = "Composite";
        } else if (this.this$0.parts[param1Int1] instanceof Hack.Gates.BuiltInGate) {
          str = "BuiltIn";
        } 
      } else if (param1Int2 == 2) {
        bool = new Boolean(this.this$0.parts[param1Int1].getGateClass().isClocked());
      } 
      return bool;
    }
    
    public boolean isCellEditable(int param1Int1, int param1Int2) {
      return false;
    }
    
    public Class getColumnClass(int param1Int) {
      return getValueAt(0, param1Int).getClass();
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/PartsComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */