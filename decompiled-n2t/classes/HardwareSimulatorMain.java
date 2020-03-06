import Hack.Controller.HackSimulator;
import Hack.HardwareSimulator.HardwareSimulator;
import Hack.Controller.HackController;
import Hack.HardwareSimulator.HardwareSimulatorGUI;
import Hack.HardwareSimulator.HardwareSimulatorControllerGUI;
import Hack.HardwareSimulator.HardwareSimulatorApplication;
import SimulatorsGUI.HardwareSimulatorComponent;
import SimulatorsGUI.HardwareSimulatorControllerComponent;
import javax.swing.UIManager;

// 
// Decompiled by Procyon v0.5.36
// 

public class HardwareSimulatorMain
{
    public static void main(final String[] array) {
        if (array.length > 1) {
            System.err.println("Usage: java HardwareSimulatorMain [script name]");
        }
        else if (array.length == 0) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch (Exception ex) {}
            final HardwareSimulatorApplication hardwareSimulatorApplication = new HardwareSimulatorApplication((HardwareSimulatorControllerGUI)new HardwareSimulatorControllerComponent(), (HardwareSimulatorGUI)new HardwareSimulatorComponent(), "bin/scripts/defaultHW.txt", "bin/help/hwUsage.html", "bin/help/hwAbout.html");
        }
        else {
            new HackController((HackSimulator)new HardwareSimulator(), array[0]);
        }
    }
}
