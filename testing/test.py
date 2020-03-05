from jpype import startJVM, shutdownJVM, java, addClassPath, JClass, JInt
import jpype.imports

CLASS_PATH_SUBPATHS = [
  "bin/lib/AssemblerGUI.jar",
  "bin/lib/Compilers.jar",
  "bin/lib/Hack.jar",
  "bin/lib/HackGUI.jar",
  "bin/lib/Simulators.jar",
  "bin/lib/SimulatorsGUI.jar",
  "bin/lib/TranslatorsGUI.jar"
] + [jpype.getClassPath()]

startJVM(classpath=CLASS_PATH_SUBPATHS, convertStrings=False)

HackController = JClass('Hack.Controller.HackController')
HackSimulator = JClass('Hack.Controller.HackSimulator')
HardwareSimulator = JClass('Hack.HardwareSimulator.HardwareSimulator')

# Runs the test file with the controller
hack_controller = HackController(HardwareSimulator(), "test_scripts/Add4.tst")

shutdownJVM()