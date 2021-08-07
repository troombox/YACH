import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DisassemblerTest {
    @Test
    public void TestDecodeOpCode(){
        Disassembler d = new Disassembler();
        Assertions.assertEquals("RET", d.decodeOpcode(0x00ee));
        Assertions.assertEquals("CLS", d.decodeOpcode(0x00e0));
        Assertions.assertEquals("JMP ABC", d.decodeOpcode(0x1abc));
        Assertions.assertEquals("CALL ABC", d.decodeOpcode(0x2abc));
        Assertions.assertEquals("SKPEQ VA 0xBC", d.decodeOpcode(0x3abc));
        Assertions.assertEquals("SKPNE V8 0xBC", d.decodeOpcode(0x48bc));
    }
}
