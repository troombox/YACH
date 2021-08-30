import chip.Display;
import chip.PByte;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;

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
                d.setPixelOn(i%64,i%32);
        }catch (Exception e){
            e.printStackTrace();
        }
        d.drawDisplayToOut();
        System.out.println();
    }

    @Test
    public void TestDrawSprite(){
        Display d = new Display();
        d.drawSprite(0,1, new PByte(0xff));
        System.out.println();
        ArrayList<Integer> sprite = new ArrayList<Integer>(Arrays.asList(0xF0, 0x90, 0xF0, 0x90, 0x90));
        for(int i = 0; i <sprite.size(); i++)
            Assertions.assertFalse(d.drawSprite(0, 3 + i, new PByte(sprite.get(i))));
        for(int i = 0; i <sprite.size(); i++)
            Assertions.assertFalse(d.drawSprite(4, 3 + i, new PByte(sprite.get(i))));
        Assertions.assertTrue(d.drawSprite(0, 5, new PByte(0x0)));
        d.drawDisplayToOut();
    }

}
