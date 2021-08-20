import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ErrorMsgTest {
    @Test
    public void TestErrorMsg(){
        ErrorMsg m = new ErrorMsg();
        Assertions.assertEquals("",m.getMsgKeep());
        Assertions.assertFalse(m.messageWaiting());
        m.addMsg("test");
        Assertions.assertTrue(m.messageWaiting());
        Assertions.assertEquals("test",m.getMsgKeep());
        Assertions.assertEquals("test",m.getMsgOldestKeep());
        m.addMsg("test1");
        Assertions.assertEquals("test1",m.getMsgKeep());
        Assertions.assertEquals(2, m.getCount());
    }

    @Test
    public void TestGetMessage(){
        ErrorMsg m = new ErrorMsg();
        m.addMsg("test");
        Assertions.assertEquals("test",m.getMsg());
        Assertions.assertEquals("",m.getMsg());
        m.addMsg("test");
        m.addMsg("test1");
        Assertions.assertEquals("test",m.getMsgOldest());
        Assertions.assertEquals("test1",m.getMsgOldest());
        Assertions.assertEquals("",m.getMsgOldest());
    }
}
