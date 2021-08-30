import chip.PByte;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class PByteTest {
    @Test
    public void TestPByte(){
        PByte b = new PByte(0xa2);
        Assertions.assertEquals(0xa2, b.intValue());
    }

    @Test
    public void TestSafeConstructor(){
        PByte b = PByte.safeConstructor(-1);
        Assertions.assertEquals(0,b.intValue());
        b = PByte.safeConstructor(0xab);
        Assertions.assertEquals(0xab, b.intValue());
        b = PByte.safeConstructor(0xabc);
        Assertions.assertEquals(0xbc, b.intValue());
        b = PByte.safeConstructor(0x1 + 0xff);
        Assertions.assertEquals(0x0, b.intValue());
    }

    @Test
    public void TestGetFirstQuadbit(){
        PByte b = new PByte(0xa2);
        Assertions.assertEquals(0xa, b.getFirstQuadbit());
        b = PByte.safeConstructor(0xcde);
        Assertions.assertEquals(0xd, b.getFirstQuadbit());
    }

    @Test
    public void TestGetSecondQuadbit(){
        PByte b = new PByte(0xa2);
        Assertions.assertEquals(0x2, b.getSecondQuadbit());
        b = PByte.safeConstructor(0xcde);
        Assertions.assertEquals(0xe, b.getSecondQuadbit());
    }
}
