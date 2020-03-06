package Hack.Utilities;

public class Shifter {
  public static final short[] powersOf2 = new short[] { 
      1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 
      1024, 2048, 4096, 8192, 16384, Short.MIN_VALUE };
  
  public static short unsignedShiftRight(short paramShort, byte paramByte) {
    short s;
    if (paramShort >= 0) {
      s = (short)(paramShort >> paramByte);
    } else {
      paramShort = (short)(paramShort & Short.MAX_VALUE);
      s = (short)(paramShort >> paramByte);
      s = (short)(s | powersOf2[15 - paramByte]);
    } 
    return s;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Utilities/Shifter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */