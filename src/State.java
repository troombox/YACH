import java.util.HashMap;
import java.util.Map;

public class State {

    private OpCode _currentOp;
    private DReg _registers;
    private Memory _memory;
    private Display _display;
    private Timers _timers;
    private Map<Integer,Runnable> _opFunc;

    public State(){
        _currentOp = new OpCode(0);
        _registers = new DReg();
        _memory = new Memory();
        _display = new Display();
        _timers = new Timers();
        _opFunc = new HashMap<>();
        populateFuncMap();
    }

    private void populateFuncMap(){
        _opFunc.put(1,this::op0x1);
        _opFunc.put(2,this::op0x2);
    }

    private void fetchOPCode(){
        try{
            _currentOp = new OpCode(_memory.readMemoryAtAddress(_registers.readPCReg()),
                    _memory.readMemoryAtAddress(_registers.readPCReg() + 1));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void executeCurrentOpCode(){
        Runnable f = _opFunc.getOrDefault(_currentOp.getFirstByte().getFirstQuadbit(), this::opNULL);
        f.run();
    }

    /*functions corresponding to opcodes*/

    private void op0x0(){}

    private void op0x1(){
        _registers.writePCReg(_currentOp.getAddressValue());
    }

    private void op0x2(){
        try{
            if(_registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                    == _currentOp.getSecondByte().intValue()){
                _registers.writePCReg(_registers.readPCReg() + 2);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x3(){}

    private void op0x4(){}

    private void op0x5(){}

    private void op0x6(){}

    private void op0x7(){}

    private void op0x8(){}

    private void op0x9(){}

    private void op0xa(){}

    private void op0xb(){}

    private void op0xc(){}

    private void op0xd(){}

    private void op0xe(){}

    private void op0xf(){}


    private void opNULL(){
       /*do nothing*/
    }

}
