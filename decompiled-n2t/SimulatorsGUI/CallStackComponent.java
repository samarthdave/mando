package SimulatorsGUI;

import Hack.VMEmulator.CallStackGUI;
import HackGUI.Utilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class CallStackComponent extends JPanel implements CallStackGUI {
  protected static final int DEFAULT_VISIBLE_ROWS = 10;
  
  private Vector methodNames = new Vector();
  
  private JTable callStackTable = new JTable(this.model);
  
  private CallStackTableModel model = new CallStackTableModel(this);
  
  private JScrollPane scrollPane;
  
  private JLabel nameLbl = new JLabel();
  
  public CallStackComponent() {
    jbInit();
  }
  
  public void setContents(Vector paramVector) {
    this.methodNames = (Vector)paramVector.clone();
    this.callStackTable.revalidate();
    Rectangle rectangle = this.callStackTable.getCellRect(paramVector.size() - 1, 0, true);
    this.callStackTable.scrollRectToVisible(rectangle);
    repaint();
  }
  
  public void reset() {
    this.methodNames.removeAllElements();
    this.callStackTable.revalidate();
    this.callStackTable.clearSelection();
  }
  
  public void setVisibleRows(int paramInt) {
    int i = paramInt * this.callStackTable.getRowHeight();
    this.scrollPane.setSize(getTableWidth(), i + 3);
    setPreferredSize(new Dimension(getTableWidth(), i + 30));
    setSize(getTableWidth(), i + 30);
  }
  
  public int getTableWidth() {
    return 190;
  }
  
  private void jbInit() {
    this.callStackTable.addFocusListener(new FocusListener(this) {
          private final CallStackComponent this$0;
          
          public void focusGained(FocusEvent param1FocusEvent) {
            this.this$0.callStackTable_focusGained(param1FocusEvent);
          }
          
          public void focusLost(FocusEvent param1FocusEvent) {
            this.this$0.callStackTable_focusLost(param1FocusEvent);
          }
        });
    this.callStackTable.setTableHeader((JTableHeader)null);
    this.callStackTable.setDefaultRenderer(this.callStackTable.getColumnClass(0), getCellRenderer());
    this.scrollPane = new JScrollPane(this.callStackTable);
    setVisibleRows(10);
    this.scrollPane.setLocation(0, 27);
    setBorder(BorderFactory.createEtchedBorder());
    setLayout((LayoutManager)null);
    this.nameLbl.setText("Call Stack");
    this.nameLbl.setBounds(new Rectangle(3, 4, 70, 23));
    this.nameLbl.setFont(Utilities.labelsFont);
    add(this.scrollPane, (Object)null);
    add(this.nameLbl, (Object)null);
  }
  
  public void callStackTable_focusGained(FocusEvent paramFocusEvent) {}
  
  public void callStackTable_focusLost(FocusEvent paramFocusEvent) {
    this.callStackTable.clearSelection();
  }
  
  protected DefaultTableCellRenderer getCellRenderer() {
    return new callStackTableCellRenderer(this);
  }
  
  public class callStackTableCellRenderer extends DefaultTableCellRenderer {
    private final CallStackComponent this$0;
    
    public callStackTableCellRenderer(CallStackComponent this$0) {
      this.this$0 = this$0;
    }
    
    public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
      setForeground(null);
      setBackground(null);
      setRenderer(param1Int1, param1Int2);
      super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2);
      return this;
    }
    
    public void setRenderer(int param1Int1, int param1Int2) {
      if (param1Int1 == this.this$0.methodNames.size() - 1)
        setForeground(Color.blue); 
    }
  }
  
  class CallStackTableModel extends AbstractTableModel {
    private final CallStackComponent this$0;
    
    CallStackTableModel(CallStackComponent this$0) {
      this.this$0 = this$0;
    }
    
    public int getColumnCount() {
      return 1;
    }
    
    public int getRowCount() {
      return this.this$0.methodNames.size();
    }
    
    public String getColumnName(int param1Int) {
      return "";
    }
    
    public Object getValueAt(int param1Int1, int param1Int2) {
      return this.this$0.methodNames.elementAt(param1Int1);
    }
    
    public boolean isCellEditable(int param1Int1, int param1Int2) {
      return false;
    }
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/CallStackComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */