public class Disassembler {

    public String decodeOpcode(OpCode opcode) {
        String output = "DISASSEMBLER ERROR";
        switch(opcode.getFirstByte().getFirstQuadbit()){
            case 0x00:
                if(opcode.getSecondByteValue() == 0xee){
                    output = "RET";
                } else if (opcode.getSecondByteValue() == 0xe0){
                    output = "CLS";
                }
                break;
            case 0x01:
                output = "JMP " + Integer.toHexString(opcode.getAddressValue()).toUpperCase();
                break;
            case 0x02:
                output = "CALL " + Integer.toHexString(opcode.getAddressValue()).toUpperCase();
                break;
            case 0x03:
                output = "SKPEQ V" + Integer.toHexString(opcode.getFirstRegister()).toUpperCase()
                        + " " + opcode.getSecondByte();
                break;
            case 0x04:
                output = "SKPNE V"+ Integer.toHexString(opcode.getFirstRegister()).toUpperCase()
                        + " " + opcode.getSecondByte();
                break;
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
        //test print!
        System.out.println(output);
        return output;
    }

    public String decodeOpcode(int opcode){
        return decodeOpcode(new OpCode(opcode));
    }

}
