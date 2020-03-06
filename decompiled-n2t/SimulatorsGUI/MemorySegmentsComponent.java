package SimulatorsGUI;

import HackGUI.MemorySegmentComponent;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

public class MemorySegmentsComponent extends JPanel {
  private JSplitPane segmentsSplitPane1;
  
  private JSplitPane segmentsSplitPane2;
  
  private JSplitPane segmentsSplitPane3;
  
  private JSplitPane segmentsSplitPane4;
  
  private JSplitPane segmentsSplitPane5;
  
  private MemorySegmentComponent staticSegment = new MemorySegmentComponent();
  
  private MemorySegmentComponent localSegment;
  
  private MemorySegmentComponent argSegment;
  
  private MemorySegmentComponent thisSegment;
  
  private MemorySegmentComponent thatSegment;
  
  private MemorySegmentComponent tempSegment;
  
  public MemorySegmentsComponent() {
    this.staticSegment.setSegmentName("Static");
    this.localSegment = new MemorySegmentComponent();
    this.localSegment.setSegmentName("Local");
    this.argSegment = new MemorySegmentComponent();
    this.argSegment.setSegmentName("Argument");
    this.thisSegment = new MemorySegmentComponent();
    this.thisSegment.setSegmentName("This");
    this.thatSegment = new MemorySegmentComponent();
    this.thatSegment.setSegmentName("That");
    this.tempSegment = new MemorySegmentComponent();
    this.tempSegment.setSegmentName("Temp");
    this.segmentsSplitPane5 = new JSplitPane(0, (Component)this.thatSegment, (Component)this.tempSegment);
    this.segmentsSplitPane4 = new JSplitPane(0, (Component)this.thisSegment, this.segmentsSplitPane5);
    this.segmentsSplitPane3 = new JSplitPane(0, (Component)this.argSegment, this.segmentsSplitPane4);
    this.segmentsSplitPane2 = new JSplitPane(0, (Component)this.localSegment, this.segmentsSplitPane3);
    this.segmentsSplitPane1 = new JSplitPane(0, (Component)this.staticSegment, this.segmentsSplitPane2);
    this.segmentsSplitPane1.setOneTouchExpandable(true);
    this.segmentsSplitPane2.setOneTouchExpandable(true);
    this.segmentsSplitPane3.setOneTouchExpandable(true);
    this.segmentsSplitPane4.setOneTouchExpandable(true);
    this.segmentsSplitPane5.setOneTouchExpandable(true);
    this.segmentsSplitPane5.setBorder((Border)null);
    this.segmentsSplitPane4.setBorder((Border)null);
    this.segmentsSplitPane3.setBorder((Border)null);
    this.segmentsSplitPane2.setBorder((Border)null);
    this.segmentsSplitPane1.setDividerLocation(30 + this.staticSegment.getTable().getRowHeight() * 5);
    this.segmentsSplitPane2.setDividerLocation(30 + this.localSegment.getTable().getRowHeight() * 5);
    this.segmentsSplitPane3.setDividerLocation(30 + this.argSegment.getTable().getRowHeight() * 5);
    this.segmentsSplitPane4.setDividerLocation(30 + this.thisSegment.getTable().getRowHeight() * 5);
    this.segmentsSplitPane5.setDividerLocation(30 + this.thatSegment.getTable().getRowHeight() * 2);
    this.segmentsSplitPane1.setSize(new Dimension(195, 587));
    this.segmentsSplitPane1.setPreferredSize(new Dimension(195, 587));
  }
  
  public JSplitPane getSplitPane() {
    return this.segmentsSplitPane1;
  }
  
  public MemorySegmentComponent getStaticSegment() {
    return this.staticSegment;
  }
  
  public MemorySegmentComponent getLocalSegment() {
    return this.localSegment;
  }
  
  public MemorySegmentComponent getArgSegment() {
    return this.argSegment;
  }
  
  public MemorySegmentComponent getThisSegment() {
    return this.thisSegment;
  }
  
  public MemorySegmentComponent getThatSegment() {
    return this.thatSegment;
  }
  
  public MemorySegmentComponent getTempSegment() {
    return this.tempSegment;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/MemorySegmentsComponent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */