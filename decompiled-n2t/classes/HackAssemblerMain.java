import Hack.Assembler.HackAssemblerGUI;
import AssemblerGUI.AssemblerComponent;
import javax.swing.UIManager;
import Hack.Translators.HackTranslatorException;
import Hack.Assembler.HackAssembler;

// 
// Decompiled by Procyon v0.5.36
// 

public class HackAssemblerMain
{
    public static void main(final String[] array) {
        if (array.length > 1) {
            System.err.println("Usage: java HackAssembler [.asm name]");
            System.exit(-1);
        }
        if (array.length == 1) {
            try {
                final HackAssembler hackAssembler = new HackAssembler(array[0], 32768, (short)0, true);
            }
            catch (HackTranslatorException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch (Exception ex3) {}
            try {
                final AssemblerComponent assemblerComponent = new AssemblerComponent();
                ((HackAssemblerGUI)assemblerComponent).setAboutFileName("bin/help/asmAbout.html");
                ((HackAssemblerGUI)assemblerComponent).setUsageFileName("bin/help/asmUsage.html");
                final HackAssembler hackAssembler2 = new HackAssembler((HackAssemblerGUI)assemblerComponent, 32768, (short)0, (String)null);
            }
            catch (HackTranslatorException ex2) {
                System.err.println(ex2.getMessage());
                System.exit(-1);
            }
        }
    }
}
