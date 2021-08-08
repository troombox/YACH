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
                output = "JMP " + opcode.getAddressHexString();
                break;
            case 0x02:
                output = "CALL " + opcode.getAddressHexString();
                break;
            case 0x03:
                output = "SKPEQ V" + opcode.getFirstRegisterHexString() + ", " + opcode.getSecondByte();
                break;
            case 0x04:
                output = "SKPNE V"+ opcode.getFirstRegisterHexString() + ", " + opcode.getSecondByte();
                break;
            case 0x05:
                if(opcode.getSecondByte().getSecondQuadbit() == 0x0){
                    output = "SKPEQ V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                }
                break;
            case 0x06:
                output = "MOV V" + opcode.getFirstRegisterHexString() + ", " + opcode.getSecondByte();
                break;
            case 0x07:
                output = "ADD V" + opcode.getFirstRegisterHexString() + ", " + opcode.getSecondByte();
                break;
            case 0x08:
                if(opcode.getSecondByte().getSecondQuadbit() == 0x0) {
                    output = "MOV V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0x1){
                    output = "OR V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0x2){
                    output = "AND V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0x3){
                    output = "XOR V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0x4){
                    output = "ADD. V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0x5){
                    output = "SUB. V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0x6){
                    output = "SHR. V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0x7){
                    output = "SUBB. V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                } else if(opcode.getSecondByte().getSecondQuadbit() == 0xe){
                    output = "SHL. V" + opcode.getFirstRegisterHexString();
                }
                break;
            case 0x09:
                if(opcode.getSecondByte().getSecondQuadbit() == 0x0){
                    output = "SKPNE V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString();
                }
                break;
            case 0x0a:
                output = "MOV I, " + opcode.getAddressHexString();
                break;
            case 0x0b:
                output = "JMP " + opcode.getAddressHexString() + "(V0)";
                break;
            case 0x0c:
                output = "RAND V" + opcode.getFirstRegisterHexString() + ", " + opcode.getSecondByte();
                break;
            case 0x0d:
                output = "SPRITE V" + opcode.getFirstRegisterHexString() + ", V" + opcode.getSecondRegisterHexString()
                        + ", 0x" + Integer.toHexString(opcode.getSecondByte().getSecondQuadbit()).toUpperCase();
                break;
            case 0x0e:
                if(opcode.getSecondByteValue() == 0x9e){
                    output = "SKPKY V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0xa1){
                    output = "SKPNK V" + opcode.getFirstRegisterHexString();
                }
                break;
            case 0x0f:
                if(opcode.getSecondByteValue() == 0x07){
                    output = "MOV V" + opcode.getFirstRegisterHexString() + ", DELAY";
                } else if(opcode.getSecondByteValue() == 0x0a){
                    output = "WAITKEY V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0x15){
                    output = "MOV DELAY, V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0x18){
                    output = "MOV SOUND, V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0x1e){
                    output = "ADD I, V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0x29){
                    output = "SPRT V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0x33){
                    output = "MOVBCD V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0x55){
                    output = "MOVM (I), V0 - V" + opcode.getFirstRegisterHexString();
                } else if(opcode.getSecondByteValue() == 0x65){
                    output = "MOVM, V0 - V" + opcode.getFirstRegisterHexString() + ", (I)";
                }
                break;
        }
        return output;
    }

    public String decodeOpcode(int opcode){
        return decodeOpcode(new OpCode(opcode));
    }

}
