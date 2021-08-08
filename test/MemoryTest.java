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
}
