import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DisplayTest {
    @Test
    public void TestDisplay(){
        Display d = new Display();
        try {
            Assertions.assertFalse(d.setPixelOff(0, 0));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestSetPixel(){
        Display d = new Display();
        try{
            d.setPixelOn(-5,-5);
        }catch (Exception e){
            Assertions.assertEquals("java.lang.IllegalArgumentException", e.toString());
        }
        try{
            Assertions.assertFalse(d.setPixelOn(0, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Assertions.assertTrue(d.setPixelOff(0, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Assertions.assertFalse(d.setPixelOff(1, 1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestClearDisplay(){
        Display d = new Display();
        try{
            Assertions.assertFalse(d.setPixelOn(0, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
        d.clearDisplay();
        try{
            Assertions.assertFalse(d.setPixelOn(0, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestDrawDisplayToOut(){
        Display d = new Display();
        d.drawDisplayToOut();
        System.out.println();
        try{
            d.setPixelOn(1,1);
        }catch (Exception e){
            e.printStackTrace();
        }
        d.drawDisplayToOut();
        System.out.println();
        d.clearDisplay();
        try{
            for(int i = 0; i < 64; i++)
                d.setPixelOn(i%32,i%64);
        }catch (Exception e){
            e.printStackTrace();
        }
        d.drawDisplayToOut();
        System.out.println();
    }

}
