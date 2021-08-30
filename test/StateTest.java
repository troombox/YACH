import chip.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import chip.*;

public class StateTest {
    @Test
    public void TestGetCurrentOPCode(){
        State s = new State();
        Assertions.assertEquals(0, s.getCurrentOPCode().intValue());
    }

    @Test
    public void TestSetCurrentOPCode() {
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0xabcd));
        Assertions.assertEquals(0xabcd, s.getCurrentOPCode().intValue());
    }

//    @Test
//    public void TestExecuteCurrentOpCode(){
//        Chip.State s = new Chip.State();
//        s.executeCurrentOpCode();
//        Assertions.assertEquals("UNKNOWN INSTRUCTION", s.get_emsgs().getMsg());
//    }

    @Test
    public void TestOp00E0() {
        State s = new State();
        Assertions.assertFalse(s.get_display().setPixelOn(1, 1));
        Assertions.assertTrue(s.get_display().setPixelOff(1, 1));
        s.get_display().setPixelOn(1, 1);
        Assertions.assertTrue(s.get_display().setPixelOn(1, 1));
        s.forceSetCurrentOPCode(new OpCode(0x00e0));
        s.executeCurrentOpCode();
        Assertions.assertFalse(s.get_display().setPixelOn(1, 1));
    }

    @Test
    public void TestOp00EE() {
        State s = new State();
        try {
            s.get_memory().pushToStack(Memory.MEMORY_START_POINT + 100);
            s.forceSetCurrentOPCode(new OpCode(0x00ee));
            s.executeCurrentOpCode();
            Assertions.assertEquals(Memory.MEMORY_START_POINT + 100, s.get_registers().readPCReg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp1000() {
        State s = new State();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        s.forceSetCurrentOPCode(new OpCode(0x1aaa));
        s.executeCurrentOpCode();
        Assertions.assertEquals(0xaaa, s.get_registers().readPCReg());
    }

    @Test
    public void TestOp2000() {
        State s = new State();
        try {
            s.forceSetCurrentOPCode(new OpCode(0x2aaa));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xaaa, s.get_registers().readPCReg());
            Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_memory().popFromStack());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp3000() {
        State s = new State();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        try {
            s.get_registers().writeDataReg(0, new PByte(0xaa));
        } catch (Exception e) {
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x30aa));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
        try{
            s.get_registers().writeDataReg(1, new PByte(0xaa));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x31aa));
        int oldPC = s.get_registers().readPCReg();
        s.executeCurrentOpCode();
        Assertions.assertEquals(oldPC + 2, s.get_registers().readPCReg());
    }

    @Test
    public void TestOp4000(){
        State s = new State();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        try{
            s.get_registers().writeDataReg(0, new PByte(0xaa));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x40aa));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        s.forceSetCurrentOPCode(new OpCode(0x40ab));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
    }

    @Test
    public void TestOp5000(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(0, new PByte(0xaa));
            s.get_registers().writeDataReg(1, new PByte(0xaa));
            s.get_registers().writeDataReg(2, new PByte(0xbb));
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x5020));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        s.forceSetCurrentOPCode(new OpCode(0x5010));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
    }

    @Test
    public void TestOp6000(){
        State s = new State();
        try{
            Assertions.assertEquals(0, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x60ab));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xab, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void TestOp7000(){
        State s = new State();
        try{
            Assertions.assertEquals(0, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x70ab));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xab, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x7001));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xac, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x70ff));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xab, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8000(){
        State s = new State();
        try{
            Assertions.assertEquals(0, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0, s.get_registers().readDataReg(0xa).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x61ff));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8010));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(1).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x81a0));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8a00));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0xa).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8001(){
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0x610f));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x60f0));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8011));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals(0xff, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8002(){
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0x610f));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x60f0));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8012));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals((0xf0 & 0x0f), s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8003(){
        State s = new State();
        s.forceSetCurrentOPCode(new OpCode(0x6111));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x60ac));
        s.executeCurrentOpCode();
        s.forceSetCurrentOPCode(new OpCode(0x8013));
        s.executeCurrentOpCode();
        try{
            Assertions.assertEquals((0x11 ^ 0xac), s.get_registers().readDataReg(0).intValue());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8004(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(0, new PByte(0x1));
            s.get_registers().writeDataReg(1, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x8014));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(0, new PByte(0x1));
            s.forceSetCurrentOPCode(new OpCode(0x8004));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x2, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8005(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(1, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x8015));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(0, new PByte(0xf));
            s.forceSetCurrentOPCode(new OpCode(0x8005));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8006(){
        State s = new State();
        try{
            s.get_registers().writeDataReg(1, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x81f6));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x7f, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(1, new PByte(0x10));
            s.forceSetCurrentOPCode(new OpCode(0x81f6));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x8, s.get_registers().readDataReg(1).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp8007() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(1, new PByte(0xff));
            s.get_registers().writeDataReg(0, new PByte(0x1));
            s.forceSetCurrentOPCode(new OpCode(0x8017));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xfe, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(1, new PByte(0x0));
            s.get_registers().writeDataReg(0, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x8017));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp800E() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(0, new PByte(0xff));
            s.forceSetCurrentOPCode(new OpCode(0x801e));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xfe, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x1, s.get_registers().readDataReg(0xf).intValue());
            s.get_registers().writeDataReg(0, new PByte(0xf));
            s.forceSetCurrentOPCode(new OpCode(0x801e));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x1e, s.get_registers().readDataReg(0).intValue());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0xf).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOp9000() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(0, new PByte(0xaa));
            s.get_registers().writeDataReg(1, new PByte(0xaa));
            s.get_registers().writeDataReg(2, new PByte(0xbb));
        } catch (Exception e) {
            e.printStackTrace();
        }
        s.forceSetCurrentOPCode(new OpCode(0x9010));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
        s.forceSetCurrentOPCode(new OpCode(0x9020));
        s.executeCurrentOpCode();
        Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
    }

    @Test
    public void TestOpA000() {
        State s = new State();
        try {
            Assertions.assertEquals(0, s.get_registers().readIReg());
            s.forceSetCurrentOPCode(new OpCode(0xaaaa));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xaaa, s.get_registers().readIReg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpB000() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(0, new PByte(0xaa));
            s.forceSetCurrentOPCode(new OpCode(0xb000));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x0aa, s.get_registers().readPCReg());
            s.get_registers().writeDataReg(0, new PByte(0x1));
            s.forceSetCurrentOPCode(new OpCode(0xb111));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x112, s.get_registers().readPCReg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpC000() {
        State s = new State();
        try {
            Assertions.assertEquals(0, s.get_registers().readDataReg(0).intValue());
            s.forceSetCurrentOPCode(new OpCode(0xc000));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            s.forceSetCurrentOPCode(new OpCode(0xc00f));
            s.executeCurrentOpCode();
            Assertions.assertTrue(0x0f >= s.get_registers().readDataReg(0).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpD000() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(0, new PByte(1));
            s.get_registers().writeDataReg(1, new PByte(1));
            s.get_registers().writeIReg(0);
            s.forceSetCurrentOPCode(new OpCode(0xd015));
            s.executeCurrentOpCode();
            s.get_registers().writeDataReg(0, new PByte(0x5));
            s.get_registers().writeDataReg(1, new PByte(0x1));
            s.get_registers().writeIReg(0x5);
            s.forceSetCurrentOPCode(new OpCode(0xd015));
            s.executeCurrentOpCode();
            s.get_registers().writeDataReg(0, new PByte(0xa));
            s.get_registers().writeDataReg(1, new PByte(0x1));
            s.get_registers().writeIReg(0xa);
            s.forceSetCurrentOPCode(new OpCode(0xd015));
            s.executeCurrentOpCode();
            s.get_display().drawDisplayToOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpE09E() {
        State s = new State();
        try {
            Assertions.assertFalse(s.get_keys().getKeyState(0x0));
            Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            s.forceSetCurrentOPCode(new OpCode(0xe09e));
            s.executeCurrentOpCode();
            Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
            s.get_keys().setKeyState(0x0, true);
            s.forceSetCurrentOPCode(new OpCode(0xe09e));
            s.executeCurrentOpCode();
            Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpE0A1() {
        State s = new State();
        try {
            Assertions.assertFalse(s.get_keys().getKeyState(0x0));
            Assertions.assertEquals(Memory.MEMORY_START_POINT, s.get_registers().readPCReg());
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            s.forceSetCurrentOPCode(new OpCode(0xe0a1));
            s.executeCurrentOpCode();
            Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
            s.get_keys().setKeyState(0x0, true);
            s.forceSetCurrentOPCode(new OpCode(0xe0a1));
            s.executeCurrentOpCode();
            Assertions.assertEquals(Memory.MEMORY_START_POINT + 2, s.get_registers().readPCReg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF007() {
        State s = new State();
        try {
            Assertions.assertEquals(0, s.get_timers().getDelayTimer());
            s.get_timers().setDelayTimer(0xaa);
            s.forceSetCurrentOPCode(new OpCode(0xf107));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xaa, s.get_registers().readDataReg(1).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: both 0xF00A function and test need rework, not having threads breaks them.
    @Test
    public void TestOpF00A() {
        State s = new State();
        try {
            Assertions.assertEquals(0x0, s.get_registers().readDataReg(0).intValue());
            s.get_keys().keyPress(0xa);
            s.forceSetCurrentOPCode(new OpCode(0xf00a));
            s.executeCurrentOpCode();
            Thread.sleep(2000);
            Assertions.assertEquals(0xa, s.get_registers().readDataReg(0).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF015() {
        State s = new State();
        try {
            Assertions.assertEquals(0, s.get_timers().getDelayTimer());
            s.get_registers().writeDataReg(2, new PByte(0xaa));
            s.forceSetCurrentOPCode(new OpCode(0xf215));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xaa, s.get_timers().getDelayTimer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF018() {
        State s = new State();
        try {
            Assertions.assertEquals(0, s.get_timers().getSoundTimer());
            s.get_registers().writeDataReg(2, new PByte(0xaa));
            s.forceSetCurrentOPCode(new OpCode(0xf218));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xaa, s.get_timers().getSoundTimer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF01E() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(3, new PByte(0xaa));
            Assertions.assertEquals(0x0, s.get_registers().readIReg());
            s.forceSetCurrentOPCode(new OpCode(0xf31E));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0xaa, s.get_registers().readIReg());
            s.forceSetCurrentOPCode(new OpCode(0xf31E));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0x54, s.get_registers().readIReg());
            Assertions.assertEquals(0, s.get_registers().readDataReg(0xf).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF029() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(2, new PByte(0xf));
            Assertions.assertEquals(0x0, s.get_registers().readIReg());
            s.forceSetCurrentOPCode(new OpCode(0xf229));
            s.executeCurrentOpCode();
            s.get_registers().writeDataReg(0, new PByte(0x1));
            s.forceSetCurrentOPCode(new OpCode(0xD005));
            s.executeCurrentOpCode();
            s.get_display().drawDisplayToOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF033() {
        State s = new State();
        try {
            s.get_registers().writeDataReg(0xa, new PByte(0x7b));
            s.get_registers().writeIReg(Memory.MEMORY_START_POINT);
            s.forceSetCurrentOPCode(new OpCode(0xfa33));
            s.executeCurrentOpCode();
            Assertions.assertEquals(1, s.get_memory().readMemoryAtAddress(Memory.MEMORY_START_POINT).intValue());
            Assertions.assertEquals(2, s.get_memory().readMemoryAtAddress(Memory.MEMORY_START_POINT + 1).intValue());
            Assertions.assertEquals(3, s.get_memory().readMemoryAtAddress(Memory.MEMORY_START_POINT + 2).intValue());
            int ivalue = Memory.MEMORY_START_POINT + 100;
            s.get_registers().writeDataReg(0xa, new PByte(0x38));
            s.get_registers().writeIReg(ivalue);
            s.forceSetCurrentOPCode(new OpCode(0xfa33));
            s.executeCurrentOpCode();
            Assertions.assertEquals(0, s.get_memory().readMemoryAtAddress(ivalue).intValue());
            Assertions.assertEquals(5, s.get_memory().readMemoryAtAddress(ivalue + 1).intValue());
            Assertions.assertEquals(6, s.get_memory().readMemoryAtAddress(ivalue + 2).intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF055() {
        State s = new State();
        try {
            for (int i = 0; i < DReg.DATA_REG_COUNT; i++) {
                s.get_registers().writeDataReg(i, new PByte(i));
            }
            s.get_registers().writeIReg(Memory.MEMORY_START_POINT);
            s.forceSetCurrentOPCode(new OpCode(0xff55));
            s.executeCurrentOpCode();
            for (int i = 0; i < DReg.DATA_REG_COUNT; i++) {
                Assertions.assertEquals(i, s.get_memory().readMemoryAtAddress(Memory.MEMORY_START_POINT + i).intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestOpF066() {
        State s = new State();
        try {
            for (int i = 0; i < DReg.DATA_REG_COUNT; i++) {
                s.get_memory().writeMemoryAtAddress(Memory.MEMORY_START_POINT + i, new PByte(i));
            }
            s.get_registers().writeIReg(Memory.MEMORY_START_POINT);
            s.forceSetCurrentOPCode(new OpCode(0xff65));
            s.executeCurrentOpCode();
            for (int i = 0; i < DReg.DATA_REG_COUNT; i++) {
                Assertions.assertEquals(i, s.get_registers().readDataReg(i).intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
