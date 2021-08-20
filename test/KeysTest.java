import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class KeysTest {
    @Test
    public void TestKeys(){
        Keys k = new Keys();
        for(int i = 0; i < Keys.KEYS_NUM; i++){
            Assertions.assertFalse(k.getKeyState(i));
        }
    }

    @Test
    public void TestGetKeyState(){
        Keys k = new Keys();
        try{
            Assertions.assertFalse(k.getKeyState(0));
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
           k.getKeyState(-1);
        } catch (Exception e){
            Assertions.assertEquals("java.lang.IllegalArgumentException: KEY not exists", e.toString());
        }
        try{
            k.getKeyState(0xf);
        } catch (Exception e){
            Assertions.assertEquals("java.lang.IllegalArgumentException: KEY not exists", e.toString());
        }
    }

    @Test
    public void TestSetKeyState(){
        Keys k = new Keys();
        try{
            Assertions.assertFalse(k.getKeyState(0));
            k.setKeyState(0, true);
            Assertions.assertTrue(k.getKeyState(0));
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            k.setKeyState(-1, true);
        } catch (Exception e){
            Assertions.assertEquals("java.lang.IllegalArgumentException: KEY not exists", e.toString());
        }
        try{
            k.setKeyState(0xf, true);
        } catch (Exception e){
            Assertions.assertEquals("java.lang.IllegalArgumentException: KEY not exists", e.toString());
        }
    }
}
