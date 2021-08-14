import java.util.stream.IntStream;

public class DReg {

    public static final int DATA_REG_COUNT = 16;

    private PByte[] _dataReg;
    private int _iReg;
    private int _pcReg;
    private int _spReg;

    public DReg(){
        _dataReg = new PByte[DATA_REG_COUNT];
        IntStream.range(0,DATA_REG_COUNT).forEach(i -> _dataReg[i] = new PByte(0));
        _iReg = 0;
        _pcReg = 0;
        _spReg = 0;
    }

    public void writeDataReg(int register, PByte newValue) throws Exception{
        if(register < 0 || register >= DATA_REG_COUNT){
            throw new Exception("WRITING non-existing register");
        }
        _dataReg[register] = newValue;
    }

    public PByte readDataReg(int register) throws Exception{
        if(register < 0 || register >= DATA_REG_COUNT){
            throw new Exception("READING non-existing register");
        }
        return _dataReg[register];
    }

    public void writeIReg(int value){
        _iReg = value;
    }

    public int readIReg(){
        return _iReg;
    }

    public void writePCReg(int value){
        _pcReg = value;
    }

    public int readPCReg(){ return _pcReg; }

    public void writeSPReg(int value){
        _spReg = value;
    }

    public int readSPReg(){ return _spReg; }

    public void writeFlagReg(PByte value) { _dataReg[15] = value; }

    public PByte readFlagReg(){ return _dataReg[15]; }

}
