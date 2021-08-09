import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Memory {

    private class Membyte {
        private final int _address;
        private boolean _modified;
        private PByte _value;
        public Membyte(int address){
            _value = new PByte(0);
            _address = address;
            _modified = false;
        }
        public void setMemValue(PByte value){
            _value = value;
            _modified = true;
        }
        public void setMemValue(int value){ setMemValue(new PByte(value)); }
        public PByte getMemValue(){ return _value; }
        public int getAddress(){ return _address; }
        public boolean wasModified(){ return _modified; }
        @Override
        public String toString(){
            return _address + ": " + _value;
        }
    }

    private static final int MAX_MEMORY_SIZE = 4096;
    private static final int MEMORY_START_POINT = 0x200; //should be 512 / 0x200. TODO: set the custom memory size
    private static final int STACK_START_POINT = 0xea0; // 96 bytes, up to 0xEFF
    private static final int DISPLAY_START_POINT = 0xf00; //256 bytes, up to 0xFFF

    private Membyte[] _memory;
    private int _memorySize;

    public Memory(){
        _memory = new Membyte[MAX_MEMORY_SIZE];
        for(int i = 0; i < MAX_MEMORY_SIZE; i++){
            _memory[i] = new Membyte(i);
        }
        _memorySize = MAX_MEMORY_SIZE;
        loadCharSetAtAddress(0);
    }

    public Memory(int customMemSize){
        _memorySize = customMemSize;
        if(_memorySize < 0x200 || _memorySize > MAX_MEMORY_SIZE ){
            _memorySize = MAX_MEMORY_SIZE;
            System.err.println("MEMORY set to MAX_MEMORY_SIZE");
        }
        _memory = new Membyte[_memorySize];
        for(int i = 0; i < _memorySize; i++){
            _memory[i] = new Membyte(i);
        }
        loadCharSetAtAddress(0);
    }

    private void loadCharSetAtAddress(int address){
        int[] chip8_fontset = {
                0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
                0x20, 0x60, 0x20, 0x20, 0x70, // 1
                0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
                0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
                0x90, 0x90, 0xF0, 0x10, 0x10, // 4
                0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
                0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
                0xF0, 0x10, 0x20, 0x40, 0x40, // 7
                0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
                0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
                0xF0, 0x90, 0xF0, 0x90, 0x90, // A
                0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
                0xF0, 0x80, 0x80, 0x80, 0xF0, // C
                0xE0, 0x90, 0x90, 0x90, 0xE0, // D
                0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
                0xF0, 0x80, 0xF0, 0x80, 0x80  // F
        };
        for(int i = 0; i < chip8_fontset.length; i++){
            _memory[address + i].setMemValue(chip8_fontset[i]);
        }
    }

    public void clearMemory(){
        _memory = new Membyte[_memorySize];
        loadCharSetAtAddress(0);
    }

    public int getMemorySize(){
        return _memorySize;
    }

    public PByte readMemoryAtAddress(int address) throws Exception {
        if ( address < 0 || address >= _memorySize ){
            //System.err.println("MEMORY access error");
            throw new Exception("MEMORY access error");
        }
        return _memory[address].getMemValue();
    }

    public void writeMemoryAtAddress(int address, PByte data) throws Exception {
        if ( address < 0 || address >= _memorySize ){
            //System.err.println("MEMORY access error");
            throw new Exception("MEMORY access error");
        }
        _memory[address].setMemValue(data);
    }

    public void dumpMemoryToOut(){
        Arrays.stream(_memory).filter(e -> e.wasModified()).forEach(e -> System.out.println(e));
    }

    public void dumpMemoryToFile(String path){
        try {
            PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
            Arrays.stream(_memory).filter(Membyte::wasModified).forEach(writer::println);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMemoryFromFile(String filename, int start_pc){
        try{
            FileInputStream in = new FileInputStream(filename);
            int read;
            while((read = in.read()) != -1){
                writeMemoryAtAddress(start_pc++, new PByte(read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
