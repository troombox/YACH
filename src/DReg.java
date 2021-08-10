public class DReg {

    private static final int DATA_REG_COUNT = 16;

    private PByte[] _dataReg;
    private int _iReg;
    private int _pcReg;
    private int _spReg;

    public DReg(){
        _dataReg = new PByte[DATA_REG_COUNT];
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
}
