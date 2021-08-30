import chip.Timers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TimersTest {
    @Test
    public void TestTimers(){
        Timers t = new Timers();
        Assertions.assertEquals(0,t.getDelayTimer());
        Assertions.assertEquals(0,t.getSoundTimer());
        t.tickTimers();
        Assertions.assertEquals(0,t.getDelayTimer());
        Assertions.assertEquals(0,t.getSoundTimer());
        try{
            t.setDelayTimer(10);
            t.setSoundTimer(11);
        } catch (Exception e){
            e.printStackTrace();
        }
        Assertions.assertEquals(10,t.getDelayTimer());
        Assertions.assertEquals(11,t.getSoundTimer());
        t.tickTimers();
        Assertions.assertEquals(9,t.getDelayTimer());
        Assertions.assertEquals(10,t.getSoundTimer());
    }
}
