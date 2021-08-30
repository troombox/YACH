import chip.DReg;
import chip.PByte;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.stream.IntStream;

public class DRegTest {
    @Test
    public void DRegInitTest(){
        DReg d = new DReg();
        IntStream.range(0,DReg.DATA_REG_COUNT).forEach(i -> {
            try{
                Assertions.assertEquals(new PByte(0).intValue(), d.readDataReg(i).intValue());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        IntStream.range(0,DReg.DATA_REG_COUNT).forEach(i -> {
            try{
                d.writeDataReg(i,new PByte(i));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        IntStream.range(0,DReg.DATA_REG_COUNT).forEach(i -> {
            try{
                Assertions.assertEquals(i,d.readDataReg(i).intValue());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Test
    public void TestIReg(){
        DReg d = new DReg();
        Assertions.assertEquals(0,d.readIReg());
        d.writeIReg(0xff);
        Assertions.assertEquals(0xff,d.readIReg());
    }

    @Test
    public void TestPCReg(){
        DReg d = new DReg();
        Assertions.assertEquals(0,d.readPCReg());
        d.writePCReg(0xff);
        Assertions.assertEquals(0xff,d.readPCReg());
    }

    @Test
    public void TestSPReg(){
        DReg d = new DReg();
        Assertions.assertEquals(0,d.readSPReg());
        d.writeSPReg(0xff);
        Assertions.assertEquals(0xff,d.readSPReg());
    }

    @Test
    public void TestFlagReg(){
        DReg d = new DReg();
        Assertions.assertEquals(0,d.readFlagReg().intValue());
        d.writeFlagReg(new PByte(0xff));
        Assertions.assertEquals(0xff,d.readFlagReg().intValue());
    }
}
