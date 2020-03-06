package Hack.Utilities;

import java.awt.event.KeyEvent;
import java.util.Hashtable;

public class Definitions {
  public static final String version = "2.5";
  
  public static final int RAM_SIZE = 24577;
  
  public static final int ROM_SIZE = 32768;
  
  public static final int BITS_PER_WORD = 16;
  
  private static final int SCREEN_WIDTH_IN_WORDS = 32;
  
  private static final int SCREEN_HEIGHT_IN_WORDS = 256;
  
  public static final short SCREEN_SIZE_IN_WORDS = 8192;
  
  public static final int SCREEN_WIDTH = 512;
  
  public static final int SCREEN_HEIGHT = 256;
  
  public static final int SCREEN_SIZE = 131072;
  
  public static final short VAR_START_ADDRESS = 16;
  
  public static final short VAR_END_ADDRESS = 255;
  
  public static final short STACK_START_ADDRESS = 256;
  
  public static final short STACK_END_ADDRESS = 2047;
  
  public static final short HEAP_START_ADDRESS = 2048;
  
  public static final short HEAP_END_ADDRESS = 16383;
  
  public static final short SCREEN_START_ADDRESS = 16384;
  
  public static final short SCREEN_END_ADDRESS = 24576;
  
  public static final short KEYBOARD_ADDRESS = 24576;
  
  public static final short TEMP_START_ADDRESS = 5;
  
  public static final short TEMP_END_ADDRESS = 12;
  
  public static final short SP_ADDRESS = 0;
  
  public static final short LOCAL_POINTER_ADDRESS = 1;
  
  public static final short ARG_POINTER_ADDRESS = 2;
  
  public static final short THIS_POINTER_ADDRESS = 3;
  
  public static final short THAT_POINTER_ADDRESS = 4;
  
  public static final short R0_ADDRESS = 0;
  
  public static final short R1_ADDRESS = 1;
  
  public static final short R2_ADDRESS = 2;
  
  public static final short R3_ADDRESS = 3;
  
  public static final short R4_ADDRESS = 4;
  
  public static final short R5_ADDRESS = 5;
  
  public static final short R6_ADDRESS = 6;
  
  public static final short R7_ADDRESS = 7;
  
  public static final short R8_ADDRESS = 8;
  
  public static final short R9_ADDRESS = 9;
  
  public static final short R10_ADDRESS = 10;
  
  public static final short R11_ADDRESS = 11;
  
  public static final short R12_ADDRESS = 12;
  
  public static final short R13_ADDRESS = 13;
  
  public static final short R14_ADDRESS = 14;
  
  public static final short R15_ADDRESS = 15;
  
  public static final short UNKNOWN_ADDRESS = -1;
  
  public static final String SCREEN_NAME = "SCREEN";
  
  public static final String KEYBOARD_NAME = "KBD";
  
  public static final String SP_NAME = "SP";
  
  public static final String LOCAL_POINTER_NAME = "LCL";
  
  public static final String ARG_POINTER_NAME = "ARG";
  
  public static final String THIS_POINTER_NAME = "THIS";
  
  public static final String THAT_POINTER_NAME = "THAT";
  
  public static final String R0_NAME = "R0";
  
  public static final String R1_NAME = "R1";
  
  public static final String R2_NAME = "R2";
  
  public static final String R3_NAME = "R3";
  
  public static final String R4_NAME = "R4";
  
  public static final String R5_NAME = "R5";
  
  public static final String R6_NAME = "R6";
  
  public static final String R7_NAME = "R7";
  
  public static final String R8_NAME = "R8";
  
  public static final String R9_NAME = "R9";
  
  public static final String R10_NAME = "R10";
  
  public static final String R11_NAME = "R11";
  
  public static final String R12_NAME = "R12";
  
  public static final String R13_NAME = "R13";
  
  public static final String R14_NAME = "R14";
  
  public static final String R15_NAME = "R15";
  
  public static final short NEWLINE_KEY = 128;
  
  public static final short BACKSPACE_KEY = 129;
  
  public static final short LEFT_KEY = 130;
  
  public static final short UP_KEY = 131;
  
  public static final short RIGHT_KEY = 132;
  
  public static final short DOWN_KEY = 133;
  
  public static final short HOME_KEY = 134;
  
  public static final short END_KEY = 135;
  
  public static final short PAGE_UP_KEY = 136;
  
  public static final short PAGE_DOWN_KEY = 137;
  
  public static final short INSERT_KEY = 138;
  
  public static final short DELETE_KEY = 139;
  
  public static final short ESC_KEY = 140;
  
  public static final short F1_KEY = 141;
  
  public static final short F2_KEY = 142;
  
  public static final short F3_KEY = 143;
  
  public static final short F4_KEY = 144;
  
  public static final short F5_KEY = 145;
  
  public static final short F6_KEY = 146;
  
  public static final short F7_KEY = 147;
  
  public static final short F8_KEY = 148;
  
  public static final short F9_KEY = 149;
  
  public static final short F10_KEY = 150;
  
  public static final short F11_KEY = 151;
  
  public static final short F12_KEY = 152;
  
  private static Definitions instance;
  
  private Hashtable addresses;
  
  private short[] actionKeyCodes;
  
  private Definitions() {
    initAddresses();
    initKeyCodes();
  }
  
  public static Definitions getInstance() {
    if (instance == null)
      instance = new Definitions(); 
    return instance;
  }
  
  public static short computeALU(short paramShort1, short paramShort2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6) {
    short s;
    if (paramBoolean1)
      paramShort1 = 0; 
    if (paramBoolean3)
      paramShort2 = 0; 
    if (paramBoolean2)
      paramShort1 = (short)(paramShort1 ^ 0xFFFFFFFF); 
    if (paramBoolean4)
      paramShort2 = (short)(paramShort2 ^ 0xFFFFFFFF); 
    if (paramBoolean5) {
      s = (short)(paramShort1 + paramShort2);
    } else {
      s = (short)(paramShort1 & paramShort2);
    } 
    if (paramBoolean6)
      s = (short)(s ^ 0xFFFFFFFF); 
    return s;
  }
  
  public Hashtable getAddressesTable() {
    return (Hashtable)this.addresses.clone();
  }
  
  public short getKeyCode(KeyEvent paramKeyEvent) {
    short s = 0;
    char c = paramKeyEvent.getKeyChar();
    short s1 = (short)paramKeyEvent.getKeyCode();
    if (c == Character.MAX_VALUE) {
      s = this.actionKeyCodes[s1];
    } else if (s1 >= 65 && s1 <= 90) {
      s = s1;
    } else if (c == '\b') {
      s = 129;
    } else if (c == '\n') {
      s = 128;
    } else if (c == '\033') {
      s = 140;
    } else if (c == '') {
      s = 139;
    } else {
      s = (short)c;
    } 
    return s;
  }
  
  public String getKeyName(KeyEvent paramKeyEvent) {
    String str = KeyEvent.getKeyModifiersText(paramKeyEvent.getModifiers());
    return str + ((str.length() > 0) ? "+" : "") + KeyEvent.getKeyText(paramKeyEvent.getKeyCode());
  }
  
  private void initAddresses() {
    this.addresses = new Hashtable();
    this.addresses.put("SP", new Short((short)0));
    this.addresses.put("LCL", new Short((short)1));
    this.addresses.put("ARG", new Short((short)2));
    this.addresses.put("THIS", new Short((short)3));
    this.addresses.put("THAT", new Short((short)4));
    this.addresses.put("R0", new Short((short)0));
    this.addresses.put("R1", new Short((short)1));
    this.addresses.put("R2", new Short((short)2));
    this.addresses.put("R3", new Short((short)3));
    this.addresses.put("R4", new Short((short)4));
    this.addresses.put("R5", new Short((short)5));
    this.addresses.put("R6", new Short((short)6));
    this.addresses.put("R7", new Short((short)7));
    this.addresses.put("R8", new Short((short)8));
    this.addresses.put("R9", new Short((short)9));
    this.addresses.put("R10", new Short((short)10));
    this.addresses.put("R11", new Short((short)11));
    this.addresses.put("R12", new Short((short)12));
    this.addresses.put("R13", new Short((short)13));
    this.addresses.put("R14", new Short((short)14));
    this.addresses.put("R15", new Short((short)15));
    this.addresses.put("SCREEN", new Short((short)16384));
    this.addresses.put("KBD", new Short((short)24576));
  }
  
  private void initKeyCodes() {
    this.actionKeyCodes = new short[255];
    this.actionKeyCodes[33] = 136;
    this.actionKeyCodes[34] = 137;
    this.actionKeyCodes[35] = 135;
    this.actionKeyCodes[36] = 134;
    this.actionKeyCodes[37] = 130;
    this.actionKeyCodes[38] = 131;
    this.actionKeyCodes[39] = 132;
    this.actionKeyCodes[40] = 133;
    this.actionKeyCodes[112] = 141;
    this.actionKeyCodes[113] = 142;
    this.actionKeyCodes[114] = 143;
    this.actionKeyCodes[115] = 144;
    this.actionKeyCodes[116] = 145;
    this.actionKeyCodes[117] = 146;
    this.actionKeyCodes[118] = 147;
    this.actionKeyCodes[119] = 148;
    this.actionKeyCodes[120] = 149;
    this.actionKeyCodes[121] = 150;
    this.actionKeyCodes[122] = 151;
    this.actionKeyCodes[123] = 152;
    this.actionKeyCodes[155] = 138;
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Hack.jar!/Hack/Utilities/Definitions.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */