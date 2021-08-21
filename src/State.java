import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class State {

    private OpCode _currentOp;
    private ErrorMsg _emsgs;

    private DReg _registers;
    private Memory _memory;
    private Display _display;
    private Timers _timers;
    private Keys _keys;

    private Map<Integer,Runnable> _opFunc;
    private Map<Integer,Runnable> _opFuncSec;


    public State(){
        _currentOp = new OpCode(0);
        _emsgs = new ErrorMsg();
        _registers = new DReg();
        _registers.writePCReg(Memory.MEMORY_START_POINT);
        _memory = new Memory();
        _display = new Display();
        _timers = new Timers();
        _keys = new Keys();
        _opFunc = new HashMap<>();
        _opFuncSec = new HashMap<>();
        populateFuncMap();
    }

    public ErrorMsg get_emsgs() { return _emsgs; }

    public DReg get_registers() { return _registers; }

    public Memory get_memory() { return _memory; }

    public Display get_display() { return _display; }

    public Timers get_timers() { return _timers; }

    public Keys get_keys() { return _keys; }

    private void populateFuncMap(){
        /*  adding primary opcode functions */
        /*  for primary map the key is first quadbit, e.g. 0xE0A1 -> 0xe */
        _opFunc.put(0x0, this::op0x0);
        _opFunc.put(0x1, this::op0x1);
        _opFunc.put(0x2, this::op0x2);
        _opFunc.put(0x3, this::op0x3);
        _opFunc.put(0x4, this::op0x4);
        _opFunc.put(0x5, this::op0x5);
        _opFunc.put(0x6, this::op0x6);
        _opFunc.put(0x7, this::op0x7);
        _opFunc.put(0x8, this::op0x8);
        _opFunc.put(0x9, this::op0x9);
        _opFunc.put(0xa, this::op0xa);
        _opFunc.put(0xb, this::op0xb);
        _opFunc.put(0xc, this::op0xc);
        _opFunc.put(0xd, this::op0xd);
        _opFunc.put(0xe, this::op0xe);
        _opFunc.put(0xf, this::op0xf);

        /*  adding secondary opcode functions   */
        /*
            the "key" is composed from "first quadbit" + "second byte"
            e.g. opcode 0x8XY1 -> 0x8001 -> 0x8 + 0x01 -> 0x801
         */
        _opFuncSec.put(0x0e0, this::op0x00e0);
        _opFuncSec.put(0x0ee, this::op0x00ee);
        _opFuncSec.put(0x800, this::op0x8000);
        _opFuncSec.put(0x801, this::op0x8001);
        _opFuncSec.put(0x802, this::op0x8002);
        _opFuncSec.put(0x803, this::op0x8003);
        _opFuncSec.put(0x804, this::op0x8004);
        _opFuncSec.put(0x805, this::op0x8005);
        _opFuncSec.put(0x806, this::op0x8006);
        _opFuncSec.put(0x807, this::op0x8007);
        _opFuncSec.put(0x80e, this::op0x800e);
        _opFuncSec.put(0xe9e, this::op0xe09e);
        _opFuncSec.put(0xea1, this::op0xe0a1);
        _opFuncSec.put(0xf07, this::op0xf007);
        _opFuncSec.put(0xf0a, this::op0xf00a);
        _opFuncSec.put(0xf15, this::op0xf015);
        _opFuncSec.put(0xf18, this::op0xf018);
        _opFuncSec.put(0xf1e, this::op0xf01e);
        _opFuncSec.put(0xf29, this::op0xf029);
        _opFuncSec.put(0xf33, this::op0xf033);
        _opFuncSec.put(0xf55, this::op0xf055);
        _opFuncSec.put(0xf65, this::op0xf065);
    }

    public void forceSetCurrentOPCode(OpCode opcode){
        _currentOp = opcode;
    }

    public final OpCode getCurrentOPCode(){
        return _currentOp;
    }

    public void fetchOPCode(){
        try{
            _currentOp = new OpCode(_memory.readMemoryAtAddress(_registers.readPCReg()),
                    _memory.readMemoryAtAddress(_registers.readPCReg() + 1));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void executeCurrentOpCode(){
        _opFunc.getOrDefault(_currentOp.getFirstByte().getFirstQuadbit(), this::opNULL).run();
        if(_emsgs.messageWaiting())
            System.out.println(_emsgs.getMsg());
    }

    /*primary opcode functions*/

    private void op0x0(){
        _opFuncSec.getOrDefault(currentOpToSecMapKey(), this::opNULL).run();
    }

    private void op0x1(){
        _registers.writePCReg(_currentOp.getAddressValue());
    }

    private void op0x2(){
        try{
            _memory.pushToStack(new PByte(_registers.readPCReg()));
        } catch (Exception e){
            e.printStackTrace();
        }
        _registers.writePCReg(_currentOp.getAddressValue());
    }

    private void op0x3(){
        try{
            if(_registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                    == _currentOp.getSecondByte().intValue()){
                _registers.writePCReg(_registers.readPCReg() + 2);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x4(){
        try{
            if(_registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                    != _currentOp.getSecondByte().intValue()){
                _registers.writePCReg(_registers.readPCReg() + 2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x5(){
        try{
            if( _registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                    ==  _registers.readDataReg(_currentOp.getSecondRegister()).intValue()){
                _registers.writePCReg(_registers.readPCReg() + 2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x6(){
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(), _currentOp.getSecondByte());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x7(){
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(),
                    PByte.safeConstructor(_registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                            + _currentOp.getSecondByte().intValue()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8(){
        _opFuncSec.getOrDefault(currentOpToSecMapKey(), this::opNULL).run();
    }

    private void op0x9(){
        try {
            if( _registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                    !=  _registers.readDataReg(_currentOp.getSecondRegister()).intValue()){
                _registers.writePCReg(_registers.readPCReg() + 2);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0xa(){
        _registers.writeIReg(_currentOp.getAddressValue());
    }

    private void op0xb(){
        try{
            _registers.writePCReg(PByte.safeConstructor(_registers.readDataReg(0).intValue()
                    + _currentOp.getAddressValue()).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0xc(){
        Random rand = new Random();
        int random = rand.nextInt(0x100); // [0, 0xff / 250]
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(),
                    PByte.safeConstructor(_registers.readDataReg(_currentOp.getFirstRegister()).intValue() & random));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0xd(){
        try{
            int x = _registers.readDataReg(_currentOp.getFirstRegister()).intValue();
            int y = _registers.readDataReg(_currentOp.getSecondRegister()).intValue();
            int heightN = _registers.readDataReg(_currentOp.getSecondByte().getSecondQuadbit()).intValue();
            boolean flip = false;
            for(int i = 0; i < heightN; i++){
               flip = flip && _display.drawSprite(x, (y + i), _memory.readMemoryAtAddress(_registers.readIReg() + i));
            }
            if(flip){
                _registers.writeFlagReg(new PByte(1));
            } else {
                _registers.writeFlagReg(new PByte(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0xe(){
        _opFuncSec.getOrDefault(currentOpToSecMapKey(), this::opNULL);
    }

    private void op0xf(){
        _opFuncSec.getOrDefault(currentOpToSecMapKey(), this::opNULL).run();
    }

    /*secondary opcode functions*/

    private void op0x00e0(){
        _display.clearDisplay();
    }

    private void op0x00ee(){
        try{
            _registers.writePCReg(_memory.popFromStack().intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8000(){
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(),
                    _registers.readDataReg(_currentOp.getSecondRegister()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8001(){
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(),
                    new PByte(_registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                            | _registers.readDataReg(_currentOp.getSecondRegister()).intValue()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8002(){
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(),
                    new PByte(_registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                            & _registers.readDataReg(_currentOp.getSecondRegister()).intValue()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8003(){
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(),
                    new PByte(_registers.readDataReg(_currentOp.getFirstRegister()).intValue()
                            ^ _registers.readDataReg(_currentOp.getSecondRegister()).intValue()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8004(){
        try{
            int sum = _registers.readDataReg(_currentOp.getFirstRegister()).intValue()
             + _registers.readDataReg(_currentOp.getSecondRegister()).intValue();
            if ((sum > 0xff)) {
                _registers.writeFlagReg(new PByte(1));
            } else {
                _registers.writeFlagReg(new PByte(0));
            }
            _registers.writeDataReg(_currentOp.getFirstRegister(), PByte.safeConstructor(sum));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8005(){
        try{
            int vx = _registers.readDataReg(_currentOp.getFirstRegister()).intValue();
            int vy = _registers.readDataReg(_currentOp.getSecondRegister()).intValue();
            if (vy > vx) {
                _registers.writeFlagReg(new PByte(1));
                vx = vx | 0x100;
            } else {
                _registers.writeFlagReg(new PByte(0));
            }
            _registers.writeDataReg(_currentOp.getFirstRegister(), PByte.safeConstructor(vx-vy));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8006(){
        try{
            int vx = _registers.readDataReg(_currentOp.getFirstRegister()).intValue();
            _registers.writeFlagReg(new PByte(vx & 0x1));
            _registers.writeDataReg(_currentOp.getFirstRegister(),new PByte(vx >> 1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x8007(){
        try{
            int vx = _registers.readDataReg(_currentOp.getFirstRegister()).intValue();
            int vy = _registers.readDataReg(_currentOp.getSecondRegister()).intValue();
            if (vx > vy) {
                _registers.writeFlagReg(new PByte(1));
                vy = vy | 0x100;
            } else {
                _registers.writeFlagReg(new PByte(0));
            }
            _registers.writeDataReg(_currentOp.getFirstRegister(), PByte.safeConstructor(vy-vx));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0x800e(){
        try{
            int vx = _registers.readDataReg(_currentOp.getFirstRegister()).intValue();
            _registers.writeFlagReg(new PByte(vx >> 8 ));
            _registers.writeDataReg(_currentOp.getFirstRegister(), new PByte(vx << 1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void op0xe09e(){
        try{
            if (_keys.getKeyState(_registers.readDataReg(_currentOp.getFirstRegister()).intValue())) {
                _registers.writePCReg(_registers.readPCReg() + 2);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xe0a1(){
        try{
            if (!_keys.getKeyState(_registers.readDataReg(_currentOp.getFirstRegister()).intValue())) {
                _registers.writePCReg(_registers.readPCReg() + 2);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xf007(){
        try{
            _registers.writeDataReg(_currentOp.getFirstRegister(),new PByte(_timers.getDelayTimer()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xf00a(){
            try{
                //TODO: rework the busy-wait!
                while(!_keys.getKeyFlag()){
                    Thread.sleep(16);
                }
                _registers.writeDataReg(_currentOp.getFirstRegister(),new PByte(_timers.getDelayTimer()));
                _keys.setKeyFlag(false);
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    private void op0xf015(){
        try{
            _timers.setDelayTimer(_registers.readDataReg(_currentOp.getFirstRegister()).intValue());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xf018(){
        try{
            _timers.setSoundTimer(_registers.readDataReg(_currentOp.getFirstRegister()).intValue());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xf01e(){
        try{
            _registers.writeIReg( PByte.safeConstructor(_registers.readIReg()
                    + _registers.readDataReg(_currentOp.getFirstRegister()).intValue()).intValue());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xf029(){
        try{
            int sprite = _registers.readDataReg(_currentOp.getFirstRegister()).intValue();
            if (sprite > 0xf){
                sprite = 0x0;
            }
            _registers.writeIReg(Memory.CHAR_SPRITE_START_POINT + (sprite * 5));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xf033(){
        try{
            int value = _registers.readDataReg(_currentOp.getFirstRegister()).intValue();
            int I = _registers.readIReg();
            _memory.writeMemoryAtAddress(I + 2, new PByte(value%10));
            _memory.writeMemoryAtAddress(I + 1, new PByte((value/10)%10));
            _memory.writeMemoryAtAddress(I, new PByte((value/100)%10));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void op0xf055(){
        int regI = _registers.readIReg();
        IntStream.rangeClosed(0x0, _currentOp.getFirstRegister()).forEach(i -> {
            try {
                _memory.writeMemoryAtAddress((regI + i), _registers.readDataReg(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void op0xf065(){
        int regI = _registers.readIReg();
        IntStream.rangeClosed(0x0, _currentOp.getFirstRegister()).forEach(i -> {
            try {
                _registers.writeDataReg(i,_memory.readMemoryAtAddress(regI + i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void opNULL(){
       /*do nothing*/
        _emsgs.addMsg("UNKNOWN INSTRUCTION");
    }

    private int currentOpToSecMapKey(){
        int fqb = _currentOp.getFirstByte().getFirstQuadbit();
        if(fqb == 0x0 || fqb == 0xe || fqb == 0xf)
            return fqb*256 + _currentOp.getSecondByteValue();
        return fqb*256 + _currentOp.getSecondByte().getSecondQuadbit();
    }

}
