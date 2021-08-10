public class State {
    private OpCode _currentOpCode;
    private DReg _registers;
    private Memory _memory;
    private Display _display;
    private Timers _timers;

    public State(){
        _registers = new DReg();
        _memory = new Memory();
        _display = new Display();
        _timers = new Timers();
    }

    private void executeOPCode(){
        switch (_currentOpCode.getFirstByte().getFirstQuadbit()) {
            case 0x00: System.out.print("0 not handled yet"); break;
            case 0x01: System.out.print("1 not handled yet"); break;
            case 0x02: System.out.print("2 not handled yet"); break;
            case 0x03: System.out.print("3 not handled yet"); break;
            case 0x04: System.out.print("4 not handled yet"); break;
            case 0x05: System.out.print("5 not handled yet"); break;
            case 0x06: System.out.print("6 not handled yet"); break;
            case 0x07: System.out.print("7 not handled yet"); break;
            case 0x08: System.out.print("8 not handled yet"); break;
            case 0x09: System.out.print("9 not handled yet"); break;
            case 0x0a: System.out.print("a not handled yet"); break;
            case 0x0b: System.out.print("b not handled yet"); break;
            case 0x0c: System.out.print("c not handled yet"); break;
            case 0x0d: System.out.print("d not handled yet"); break;
            case 0x0e: System.out.print("e not handled yet"); break;
            case 0x0f: System.out.print("f not handled yet"); break;
        }
    }

    private void unimplementedInstruction(){
        System.out.println("UnimplementedInstruction");
    }

    public void updateState(){

    }

}
