package SimulatorsGUI;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class VMFileFilter extends FileFilter {
  public boolean accept(File paramFile) {
    if (paramFile.isDirectory())
      return true; 
    String str = getExtension(paramFile);
    return (str != null && str.equals("vm"));
  }
  
  public static String getExtension(File paramFile) {
    String str1 = null;
    String str2 = paramFile.getName();
    int i = str2.lastIndexOf('.');
    if (i > 0 && i < str2.length() - 1)
      str1 = str2.substring(i + 1).toLowerCase(); 
    return str1;
  }
  
  public String getDescription() {
    return "VM Files / Dirs";
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/SimulatorsGUI.jar!/SimulatorsGUI/VMFileFilter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */