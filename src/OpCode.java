public class OpCode {
    /*
    * opcodes in chip8 are two bytes long and big-endian, we will mark "lower" byte as "first"
    * and mark "higher" byte as "second"
    * due to Java not having unsigned bytes and limiting byte value to 127 we going to use ints
    * */
    private final int _first;
    private final int _second;

    public OpCode(int firstByte, int secondByte){
        _first = firstByte;
        _second = secondByte;
    }

    public OpCode(int opcode){
        _first =  opcode >> 8;
        _second =  opcode & 0xFF;
    }

    public final int getFirstByte(){
        return _first;
    }

    public final int getSecondByte(){
        return _second;
    }

}
