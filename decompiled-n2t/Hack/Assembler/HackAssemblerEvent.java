package Hack.Assembler;

import Hack.Translators.HackTranslatorEvent;

public class HackAssemblerEvent extends HackTranslatorEvent {
  public static final byte COMPARISON_LOAD = 9;
  
  public HackAssemblerEvent(Object paramObject1, byte paramByte, Object paramObject2) {
    super(paramObject1, paramByte, paramObject2);
  }
}


/* Location:              /Users/chinmayphulse/Desktop/Projects/mando/testing/bin/lib/Compilers.jar!/Hack/Assembler/HackAssemblerEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */