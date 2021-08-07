public class Disassembler {

    public void decodeOpcode(OpCode opcode) {
        int firstQuadbit = opcode.getFirstByte() >> 4;
        String output = "";
        switch(firstQuadbit){
            case 0x00: output += "0 not handled yet"; break;
            case 0x01: output += "1 not handled yet"; break;
            case 0x02: output += "2 not handled yet"; break;
            case 0x03: output += "3 not handled yet"; break;
            case 0x04: output += "4 not handled yet"; break;
            case 0x05: output += "5 not handled yet"; break;
            case 0x06: output += "6 not handled yet"; break;
            case 0x07: output += "7 not handled yet"; break;
            case 0x08: output += "8 not handled yet"; break;
            case 0x09: output += "9 not handled yet"; break;
            case 0x0a: output += "a not handled yet"; break;
            case 0x0b: output += "b not handled yet"; break;
            case 0x0c: output += "c not handled yet"; break;
            case 0x0d: output += "d not handled yet"; break;
            case 0x0e: output += "e not handled yet"; break;
            case 0x0f: output += "f not handled yet"; break;
        }
        System.out.println(output);
    }

    public void decodeOpcode(int opcode){
        decodeOpcode(new OpCode(opcode));
    }


}
