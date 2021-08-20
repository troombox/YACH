import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class StateTest {
    @Test
    public void TestGetCurrentOPCode(){
        State s = new State();
        Assertions.assertEquals(0,s.getCurrentOPCode().intValue());
    }

    @Test
    public void TestSetCurrentOPCode(){
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0xabcd));
        Assertions.assertEquals(0xabcd,s.getCurrentOPCode().intValue());
    }

    @Test
    public void TestExecuteCurrentOpCode(){
        State s = new State();
        s.executeCurrentOpCode();
        Assertions.assertEquals("UNKNOWN INSTRUCTION", s.get_emsgs().getMsg());
    }

    @Test
    public void TestOp00E0(){
        State s = new State();
        Assertions.assertFalse(s.get_display().setPixelOn(1,1));
        Assertions.assertTrue(s.get_display().setPixelOff(1,1));
        s.get_display().setPixelOn(1,1);
        Assertions.assertTrue(s.get_display().setPixelOn(1,1));
        s.forceSetCurrentOPCode(new OpCode(0x00e0));
        s.executeCurrentOpCode();
        Assertions.assertFalse(s.get_display().setPixelOn(1,1));
    }

    /*TODO: 0x00EE*/

}
