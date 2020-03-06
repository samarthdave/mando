package Hack.VirtualMachine;

public class HVMInstruction {
  private byte opCode;
  
  private short arg0;
  
  private short arg1;
  
  private String stringArg;
  
  private short numberOfArgs;
  
  public HVMInstruction(byte paramByte, short paramShort1, short paramShort2) {
    this.opCode = paramByte;
    this.arg0 = paramShort1;
    this.arg1 = paramShort2;
    this.numberOfArgs = 2;
  }
  
  public HVMInstruction(byte paramByte, short paramShort) {
    this.opCode = paramByte;
    this.arg0 = paramShort;
    this.numberOfArgs = 1;
  }
  
  public HVMInstruction(byte paramByte) {
    this.opCode = paramByte;
    this.numberOfArgs = 0;
  }
  
  public short getOpCode() {
    return (short)this.opCode;
  }
  
  public short getArg0() {
    return this.arg0;
  }
  
  public short getArg1() {
    return this.arg1;
  }
  
  public void setStringArg(String paramString) {
    this.stringArg = paramString;
  }
  
  public String getStringArg() {
    return this.stringArg;
  }
  
  public short getNumberOfArgs() {
    return this.numberOfArgs;
  }
  
  public String[] getFormattedStrings() {
    String[] arrayOfString = new String[3];
    HVMInstructionSet hVMInstructionSet = HVMInstructionSet.getInstance();
    arrayOfString[1] = "";
    arrayOfString[2] = "";
    arrayOfString[0] = hVMInstructionSet.instructionCodeToString(this.opCode);
    if (arrayOfString[0] == null)
      arrayOfString[0] = ""; 
    switch (this.opCode) {
      case 10:
        arrayOfString[1] = hVMInstructionSet.segmentCodeToVMString((byte)this.arg0);
        arrayOfString[2] = String.valueOf(this.arg1);
        break;
      case 11:
        if (this.numberOfArgs == 2) {
          arrayOfString[1] = hVMInstructionSet.segmentCodeToVMString((byte)this.arg0);
          arrayOfString[2] = String.valueOf(this.arg1);
        } 
        break;
      case 12:
        arrayOfString[1] = this.stringArg;
        break;
      case 13:
        arrayOfString[1] = this.stringArg;
        break;
      case 14:
        arrayOfString[1] = this.stringArg;
        break;
      case 15:
        arrayOfString[1] = this.stringArg;
        arrayOfString[2] = String.valueOf(this.arg0);
        break;
      case 17:
        arrayOfString[1] = this.stringArg;
        arrayOfString[2] = String.valueOf(this.arg1);
        break;
    } 
    return arrayOfString;
  }
  
  public String toString() {
    String[] arrayOfString = getFormattedStrings();
    StringBuffer stringBuffer = new StringBuffer();
    if (!arrayOfString[0].equals("")) {
      stringBuffer.append(arrayOfString[0]);
      if (!arrayOfString[1].equals("")) {
        stringBuffer.append(" ");
        stringBuffer.append(arrayOfString[1]);
        if (!arrayOfString[2].equals("")) {
          stringBuffer.append(" ");
          stringBuffer.append(arrayOfString[2]);
        } 
      } 
    } 
    return stringBuffer.toString();
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/VirtualMachine/HVMInstruction.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */