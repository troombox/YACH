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

    @Test
    public void TestOp1000(){
        State s = new State();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        s.forceSetCurrentOPCode(new OpCode(0x1aaa));
        s.executeCurrentOpCode();
        Assertions.assertEquals(0xaaa, s.get_registers().readPCReg());
    }

    /*TODO: 0x2000*/

    @Test
    public void TestOp3000(){
        State s = new State();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        try{
            s.get_registers().writeDataReg(0, new PByte(0xaa));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x30aa));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
        try{
            s.get_registers().writeDataReg(1, new PByte(0xaa));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x31aa));
        int oldPC = s.get_registers().readPCReg();
        s.executeCurrentOpCode();
        Assertions.assertEquals(oldPC + 2, s.get_registers().readPCReg());
    }

    @Test
    public void TestOp4000(){
        State s = new State();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        try{
            s.get_registers().writeDataReg(0, new PByte(0xaa));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x40aa));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        s.forceSetCurrentOPCode(new OpCode(0x40ab));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
    }

    @Test
    public void TestOp5000(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(0, new PByte(0xaa));
            s.get_registers().writeDataReg(1, new PByte(0xaa));
            s.get_registers().writeDataReg(2, new PByte(0xbb));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x5020));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        s.forceSetCurrentOPCode(new OpCode(0x5010));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
    }

    @Test
    public void TestOp6000(){
        State s = new State();
        try{
            Assertions.assertEquals(0, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x60ab));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xab, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void TestOp7000(){
        State s = new State();
        try{
            Assertions.assertEquals(0, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x70ab));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xab, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x7001));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xac, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x70ff));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xab, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8000(){
        State s = new State();
        try{
            Assertions.assertEquals(0, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0, s.get_registers().readDataReg(0xa).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x61ff));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8010));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(1).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x81a0));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8a00));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0xa).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8001(){
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0x610f));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x60f0));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8011));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8002(){
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0x610f));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x60f0));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8012));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals((0xf0 & 0x0f), s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8003(){
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0x6111));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x60ac));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8013));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals((0x11 ^ 0xac), s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8004(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(0, new PByte(0x1));
            s.get_registers().writeDataReg(1, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x8014));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(0, new PByte(0x1));
            s.forceSetCurrentOPCode(new OpCode(0x8004));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x2, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8005(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(1, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x8015));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(0, new PByte(0xf));
            s.forceSetCurrentOPCode(new OpCode(0x8005));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8006(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(1, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x81f6));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x7f, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(1, new PByte(0x10));
            s.forceSetCurrentOPCode(new OpCode(0x81f6));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x8, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8007(){
        State s = new State();
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
