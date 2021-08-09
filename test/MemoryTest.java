import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

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
        m.loadMemoryFromFile("DVN8.ch8", 0x200 );
        m.dumpMemoryToFile("test_mem_dump.txt");
    }
}
