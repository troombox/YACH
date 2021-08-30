import chip.Disassembler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DisassemblerTest {
    @Test
    public void TestDecodeOpCode(){
        Disassembler d = new Disassembler();
        Assertions.assertEquals("RET", d.decodeOpcode(0x00ee));
        Assertions.assertEquals("CLS", d.decodeOpcode(0x00e0));
        Assertions.assertEquals("DISASSEMBLER ERROR", d.decodeOpcode(0x0));
        Assertions.assertEquals("DISASSEMBLER ERROR", d.decodeOpcode(0x000e));
        Assertions.assertEquals("JMP 0xABC", d.decodeOpcode(0x1abc));
        Assertions.assertEquals("CALL 0xABC", d.decodeOpcode(0x2abc));
        Assertions.assertEquals("SKPEQ VA, 0xBC", d.decodeOpcode(0x3abc));
        Assertions.assertEquals("SKPNE V8, 0xBC", d.decodeOpcode(0x48bc));
        Assertions.assertEquals("SKPEQ V3, VA", d.decodeOpcode(0x53a0));
        Assertions.assertEquals("DISASSEMBLER ERROR", d.decodeOpcode(0x53a1));
        Assertions.assertEquals("MOV V3, 0x22", d.decodeOpcode(0x6322));
        Assertions.assertEquals("ADD V3, 0x22", d.decodeOpcode(0x7322));
        Assertions.assertEquals("MOV VA, VB", d.decodeOpcode(0x8ab0));
        Assertions.assertEquals("OR VA, VB", d.decodeOpcode(0x8ab1));
        Assertions.assertEquals("AND VA, VB", d.decodeOpcode(0x8ab2));
        Assertions.assertEquals("XOR VA, VB", d.decodeOpcode(0x8ab3));
        Assertions.assertEquals("ADD. VA, VB", d.decodeOpcode(0x8ab4));
        Assertions.assertEquals("SUB. VA, VB", d.decodeOpcode(0x8ab5));
        Assertions.assertEquals("SHR. VA", d.decodeOpcode(0x8ab6));
        Assertions.assertEquals("SUBB. VA, VB", d.decodeOpcode(0x8ab7));
        Assertions.assertEquals("DISASSEMBLER ERROR", d.decodeOpcode(0x8ab8));
        Assertions.assertEquals("SHL. VA", d.decodeOpcode(0x8abe));
        Assertions.assertEquals("DISASSEMBLER ERROR", d.decodeOpcode(0x9ab1));
        Assertions.assertEquals("SKPNE VA, VB", d.decodeOpcode(0x9ab0));
        Assertions.assertEquals("MOV I, 0xAB0", d.decodeOpcode(0xaab0));
        Assertions.assertEquals("JMP 0xAB0(V0)", d.decodeOpcode(0xbab0));
        Assertions.assertEquals("RAND VA, 0xB0", d.decodeOpcode(0xcab0));
        Assertions.assertEquals("SPRITE V1, V2, 0xF", d.decodeOpcode(0xd12f));
        Assertions.assertEquals("SKPKY V1", d.decodeOpcode(0xe19e));
        Assertions.assertEquals("SKPNK V1", d.decodeOpcode(0xe1a1));
        Assertions.assertEquals("DISASSEMBLER ERROR", d.decodeOpcode(0xe100));
        Assertions.assertEquals("MOV VA, DELAY", d.decodeOpcode(0xfa07));
        Assertions.assertEquals("WAITKEY VA", d.decodeOpcode(0xfa0a));
        Assertions.assertEquals("MOV DELAY, V0", d.decodeOpcode(0xf015));
        Assertions.assertEquals("MOV SOUND, VB", d.decodeOpcode(0xfb18));
        Assertions.assertEquals("ADD I, VB", d.decodeOpcode(0xfb1e));
        Assertions.assertEquals("SPRT VC", d.decodeOpcode(0xfc29));
        Assertions.assertEquals("MOVBCD V0", d.decodeOpcode(0xf033));
        Assertions.assertEquals("MOVM (I), V0 - V0", d.decodeOpcode(0xf055));
        Assertions.assertEquals("MOVM, V0 - V0, (I)", d.decodeOpcode(0xf065));
        Assertions.assertEquals("DISASSEMBLER ERROR", d.decodeOpcode(0xf000));
    }
}
