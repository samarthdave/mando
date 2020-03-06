package Hack.Events;

import java.util.EventObject;

public class ErrorEvent extends EventObject {
  private String errorMessage;
  
  public ErrorEvent(Object paramObject, String paramString) {
    super(paramObject);
    this.errorMessage = paramString;
  }
  
  public String getErrorMessage() {
    return this.errorMessage;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Events/ErrorEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */