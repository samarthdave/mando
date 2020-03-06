package Hack.Utilities;

import java.io.File;
import java.io.FilenameFilter;

public class HackFileFilter implements FilenameFilter {
  private String extension;
  
  public HackFileFilter(String paramString) {
    this.extension = paramString;
  }
  
  public boolean accept(File paramFile, String paramString) {
    return paramString.endsWith(this.extension);
  }
  
  public String getAcceptedExtension() {
    return this.extension;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Utilities/HackFileFilter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */