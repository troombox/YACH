import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class OpCodeTest {
    @Test
    public void TestOpCode(){
        PByte p1 = new PByte(0xab);
        PByte p2 = new PByte(0xcd);
        OpCode o = new OpCode(p1, p2);
        Assertions.assertEquals("ABCD",o.toString());
        o = new OpCode(0xab, 0xcd);
        Assertions.assertEquals("ABCD",o.toString());
        o = new OpCode(0xabcd);
        Assertions.assertEquals("ABCD",o.toString());
        try{
            o = new OpCode(-1);
        } catch (Exception e){
            Assertions.assertEquals("java.lang.NumberFormatException: Value out of range. Value:\"-1\"", e.toString());
        }
    }

    @Test
    public void TestIntValue(){
        OpCode o = new OpCode(0xabcd);
        Assertions.assertEquals(0xabcd, o.intValue());
        o = new OpCode(0x0);
        Assertions.assertEquals(0, o.intValue());
    }

    @Test
    public void TestGetAddressValue(){
        OpCode o = new OpCode(0xabcd);
        Assertions.assertEquals(0xbcd, o.getAddressValue());
        o = new OpCode(0x0);
        Assertions.assertEquals(-1, o.getAddressValue());
    }

    @Test
    public void TestGetFirstByteValue(){
        OpCode o = new OpCode(0xabcd);
        Assertions.assertEquals(0xab, o.getFirstByteValue());
    }

    @Test
    public void TestGetSecondByteValue(){
        OpCode o = new OpCode(0xabcd);
        Assertions.assertEquals(0xcd, o.getSecondByteValue());
    }

    @Test
    public void TestGetFirstByte(){
        PByte p1 = new PByte(0xab);
        PByte p2 = new PByte(0xcd);
        OpCode o = new OpCode(p1, p2);
        Assertions.assertEquals(p1, o.getFirstByte());
    }

    @Test
    public void TestGetSecondByte(){
        PByte p1 = new PByte(0xab);
        PByte p2 = new PByte(0xcd);
        OpCode o = new OpCode(p1, p2);
        Assertions.assertEquals(p2, o.getSecondByte());
    }

    @Test
    public void TestGetFirstRegister(){
        OpCode o = new OpCode(0x3bcd);
        Assertions.assertEquals(0xb, o.getFirstRegister());
        Assertions.assertEquals(-1, o.getSecondRegister());
        o = new OpCode(0xe100);
        Assertions.assertEquals(0x1, o.getFirstRegister());
        Assertions.assertEquals(-1, o.getSecondRegister());
        o = new OpCode(0x0);
        Assertions.assertEquals(-1, o.getFirstRegister());
    }

    @Test
    public void TestGetSecondRegister(){
        OpCode o = new OpCode(0x5ab0);
        Assertions.assertEquals(0xa, o.getFirstRegister());
        Assertions.assertEquals(0xb, o.getSecondRegister());
        o = new OpCode(0xd100);
        Assertions.assertEquals(0x1, o.getFirstRegister());
        Assertions.assertEquals(0x0, o.getSecondRegister());
        o = new OpCode(0x0);
        Assertions.assertEquals(-1, o.getSecondRegister());
    }
}
