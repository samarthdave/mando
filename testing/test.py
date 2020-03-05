from jpype import startJVM, shutdownJVM, java, addClassPath, JClass, JInt
import jpype.imports

CLASS_PATH_SUBPATHS = [
  "tools/buildInChips",
  "tools/bin/classes",
  "tools/bin/lib/AssemblerGUI.jar",
  "tools/bin/lib/Compilers.jar",
  "tools/bin/lib/Hack.jar",
  "tools/bin/lib/HackGUI.jar",
  "tools/bin/lib/Simulators.jar",
  "tools/bin/lib/SimulatorsGUI.jar",
  "tools/bin/lib/TranslatorsGUI.jar"
]

startJVM(classpath=":".join(CLASS_PATH_SUBPATHS), convertStrings=False)

HackController = JClass('Hack.Controller.HackController')
HackSimulator = JClass('Hack.Controller.HackSimulator')
HardwareSimulator = JClass('Hack.HardwareSimulator.HardwareSimulator')

# Runs the test file with the controller
hack_controller = HackController(HardwareSimulator(), "test_scripts/Add4.tst")

shutdownJVM()