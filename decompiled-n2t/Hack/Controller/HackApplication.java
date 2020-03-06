package Hack.Controller;

public abstract class HackApplication {
  public HackApplication(HackSimulator paramHackSimulator, ControllerGUI paramControllerGUI, HackSimulatorGUI paramHackSimulatorGUI, String paramString1, String paramString2, String paramString3) {
    try {
      paramHackSimulatorGUI.setUsageFileName(paramString2);
      paramHackSimulatorGUI.setAboutFileName(paramString3);
      createController(paramHackSimulator, paramControllerGUI, paramString1);
    } catch (ScriptException scriptException) {
      System.err.println(scriptException.getMessage());
      System.exit(-1);
    } catch (ControllerException controllerException) {
      System.err.println(controllerException.getMessage());
      System.exit(-1);
    } catch (Exception exception) {
      System.err.println("Unexpected Error: " + exception.getMessage());
      exception.printStackTrace();
      System.exit(-1);
    } 
  }
  
  protected void createController(HackSimulator paramHackSimulator, ControllerGUI paramControllerGUI, String paramString) throws ScriptException, ControllerException {
    HackController hackController = new HackController(paramControllerGUI, paramHackSimulator, paramString);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Controller/HackApplication.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */