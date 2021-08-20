import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class MemoryTest {
    @Test
    public void TestMemory(){
        Memory m = new Memory(0x200);
        m.dumpMemoryToOut();
    }

    @Test
    public void TestMemoryDump(){
        Memory m = new Memory(0x200);
        m.dumpMemoryToFile("dump.txt");
    }

    @Test
    public void TestLoadMemoryFromFile(){
        Memory m = new Memory();
        m.loadMemoryFromFile("DVN8.ch8", Memory.MEMORY_START_POINT );
        m.dumpMemoryToFile("test_mem_dump.txt");
    }

    @Test
    public void TestClearMemory(){
        Memory m = new Memory();
        m.dumpMemoryToOut();
        m.loadMemoryFromFile("DVN8.ch8", Memory.MEMORY_START_POINT);
        m.dumpMemoryToOut();
        m.clearMemory();
        m.dumpMemoryToOut();
    }

    @Test
    public void TestGetMemorySize(){
        Assertions.assertEquals(Memory.MAX_MEMORY_SIZE, new Memory().getMemorySize());
    }

    @Test
    public void TestReadMemAtAddress(){
        Memory m = new Memory();
        try{
            Assertions.assertEquals("0xF0",m.readMemoryAtAddress(0).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestWriteMemAtAddress(){
        Memory m = new Memory();
        try{
            Assertions.assertEquals(0,m.readMemoryAtAddress(Memory.MEMORY_START_POINT).intValue());
            m.writeMemoryAtAddress(Memory.MEMORY_START_POINT, new PByte(0x1));
            Assertions.assertEquals(1,m.readMemoryAtAddress(Memory.MEMORY_START_POINT).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            m.writeMemoryAtAddress(-1, new PByte(0x1));
        }catch (Exception e){
            Assertions.assertEquals("java.lang.Exception: MEMORY access error", e.toString());
        }
        try{
            m.writeMemoryAtAddress(Memory.MAX_MEMORY_SIZE + 1 , new PByte(0x1));
        }catch (Exception e){
            Assertions.assertEquals("java.lang.Exception: MEMORY access error", e.toString());
        }
    }

    @Test
    public void TestPushToStack(){
        Memory m = new Memory();
        try{
            //currently 0xfa0 is stack start point
            Assertions.assertEquals(0,m.readMemoryAtAddress(0xfa0).intValue());
            m.pushToStack(new PByte(0xff));
            Assertions.assertEquals(0xff,m.readMemoryAtAddress(0xfa0).intValue());
            m.pushToStack(new PByte(0xaa));
            Assertions.assertEquals(0xaa,m.readMemoryAtAddress(0xfa1).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            for(int i = 0; i < 94; i++){
                m.pushToStack(new PByte(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            m.pushToStack(new PByte(0));
        }catch (Exception e){
            Assertions.assertEquals("java.lang.Exception: STACK full error",e.toString());
        }
    }

    @Test
    public void TestPopFromStack(){
        Memory m = new Memory();
        try{
            m.popFromStack();
        } catch (Exception e){
            Assertions.assertEquals("java.lang.Exception: STACK empty error",e.toString());
        }
        try{
            m.pushToStack(new PByte(0xaa));
            Assertions.assertEquals(0xaa, m.popFromStack().intValue());
            for(int i = 0; i < 20; i++){
                m.pushToStack(new PByte(i));
            }
            for(int i = 0; i < 20; i++){
               Assertions.assertEquals(19-i, m.popFromStack().intValue());
            }
        } catch (Exception e){
           e.printStackTrace();
        }
    }

}
