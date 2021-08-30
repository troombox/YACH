package chip;

public class OpCode {
    /*
     * opcodes in chip8 are two bytes long and big-endian, we will mark "lower" byte as "first"
     * and mark "higher" byte as "second"
     * due to Java not having unsigned bytes and limiting byte value to 127 we going to use our custom Chip.PByte class
     * */
    private final PByte _first;
    private final PByte _second;

    public OpCode(PByte firstByte, PByte secondByte){
        _first = firstByte;
        _second = secondByte;
    }

    public OpCode(int firstByte, int secondByte){
        _first = new PByte(firstByte);
        _second = new PByte(secondByte);
    }

    public OpCode(int opcode){
        _first =  new PByte(opcode >> 8);
        _second =  new PByte(opcode & 0xFF);
    }

    /*
     * opcodes that include address are: {1NNN, 2NNN, ANNN, BNNN}
     * if the opcode does not include address -1 will be returned
     * */
    public final int getAddressValue(){
        if(_first.getFirstQuadbit() == 0x01 || _first.getFirstQuadbit() == 0x02 ||
                _first.getFirstQuadbit() == 0x0a || _first.getFirstQuadbit() == 0x0b){
            return ((_first.intValue() << 8) | _second.intValue()) & 0xfff;
        }
        return -1;
    }

    public final String getAddressHexString(){
        int address = getAddressValue();
        return (address >= 0) ? "0x" + Integer.toHexString(address).toUpperCase() : "Chip.OpCode does not include address";
    }

    /*
     * opcodes that include at least one register are:
     * {3NNN, 4NNN, 5NNN, 6NNN, 7NNN, 8NNN, 9NNN, CNNN, DNNN, ENNN, FNNN}
     * if the opcode does not include registers -1 will be returned
     * */
    public final int getFirstRegister(){
        if(_first.getFirstQuadbit() == 0x03 || _first.getFirstQuadbit() == 0x04 ||
                _first.getFirstQuadbit() == 0x05 || _first.getFirstQuadbit() == 0x06 ||
                _first.getFirstQuadbit() == 0x07 || _first.getFirstQuadbit() == 0x08 ||
                _first.getFirstQuadbit() == 0x09 || _first.getFirstQuadbit() == 0x0c ||
                _first.getFirstQuadbit() == 0x0d || _first.getFirstQuadbit() == 0x0e ||
                _first.getFirstQuadbit() == 0x0f ){
            return _first.getSecondQuadbit();
        }
        return -1;
    }

    public final String getFirstRegisterHexString(){
        int reg = getFirstRegister();
        return (reg >= 0) ? Integer.toHexString(reg).toUpperCase() : "Chip.OpCode does not include first register";
    }

    /*
     * opcodes that include two registers are: {5NNN, 8NNN, 9NNN, DNNN}
     * if the opcode does not include second register -1 will be returned
     * */
    public final int getSecondRegister(){
        if(_first.getFirstQuadbit() == 0x05 || _first.getFirstQuadbit() == 0x08 ||
                _first.getFirstQuadbit() == 0x09 || _first.getFirstQuadbit() == 0x0d){
            return _second.getFirstQuadbit();
        }
        return -1;
    }

    public final String getSecondRegisterHexString(){
        int reg = getSecondRegister();
        return (reg >= 0) ? Integer.toHexString(reg).toUpperCase() : "Chip.OpCode does not include second register";
    }


    public final int getFirstByteValue(){
        return _first.intValue();
    }

    public final PByte getFirstByte(){
        return _first;
    }

    public final PByte getSecondByte(){ return _second; }

    public final int getSecondByteValue(){
        return _second.intValue();
    }

    public int intValue(){
        return (_first.intValue() << 8) | _second.intValue();
    }

    @Override
    public String toString() {
        return Integer.toHexString((_first.intValue() << 8) | _second.intValue()).toUpperCase();
    }
}
