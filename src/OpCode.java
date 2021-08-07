public class OpCode {
    /*
    * opcodes in chip8 are two bytes long and big-endian, we will mark "lower" byte as "first"
    * and mark "higher" byte as "second"
    * due to Java not having unsigned bytes and limiting byte value to 127 we going to use our custom PByte class
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

    public final int getFirstByteValue(){
        return _first.intValue();
    }

    public final PByte getFirstByte(){
        return _first;
    }

    public final PByte getSecondByte(){
        return _second;
    }



}
