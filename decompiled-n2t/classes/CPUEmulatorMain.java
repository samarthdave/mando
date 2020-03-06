import Hack.Controller.HackSimulator;
import Hack.CPUEmulator.CPUEmulator;
import Hack.Controller.HackController;
import Hack.CPUEmulator.CPUEmulatorGUI;
import Hack.Controller.ControllerGUI;
import Hack.CPUEmulator.CPUEmulatorApplication;
import SimulatorsGUI.CPUEmulatorComponent;
import HackGUI.ControllerComponent;
import javax.swing.UIManager;

// 
// Decompiled by Procyon v0.5.36
// 

public class CPUEmulatorMain
{
    public static void main(final String[] array) {
        if (array.length > 1) {
            System.err.println("Usage: java CPUEmulatorMain [script name]");
        }
        else if (array.length == 0) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch (Exception ex) {}
            final CPUEmulatorApplication cpuEmulatorApplication = new CPUEmulatorApplication((ControllerGUI)new ControllerComponent(), (CPUEmulatorGUI)new CPUEmulatorComponent(), "bin/scripts/defaultCPU.txt", "bin/help/cpuUsage.html", "bin/help/cpuAbout.html");
        }
        else {
            new HackController((HackSimulator)new CPUEmulator(), array[0]);
        }
    }
}
