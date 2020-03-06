import Hack.Controller.HackSimulator;
import Hack.VMEmulator.VMEmulator;
import Hack.Controller.HackController;
import Hack.VMEmulator.VMEmulatorGUI;
import Hack.Controller.ControllerGUI;
import Hack.VMEmulator.VMEmulatorApplication;
import SimulatorsGUI.VMEmulatorComponent;
import HackGUI.ControllerComponent;
import javax.swing.UIManager;

// 
// Decompiled by Procyon v0.5.36
// 

public class VMEmulatorMain
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
            final VMEmulatorApplication vmEmulatorApplication = new VMEmulatorApplication((ControllerGUI)new ControllerComponent(), (VMEmulatorGUI)new VMEmulatorComponent(), "bin/scripts/defaultVM.txt", "bin/help/vmUsage.html", "bin/help/vmAbout.html");
        }
        else {
            new HackController((HackSimulator)new VMEmulator(), array[0]);
        }
    }
}
