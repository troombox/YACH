package emulator;

import chip.Disassembler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Viewer {

    private Emulator _emu;

    @FXML
    private TextField textV0;

    @FXML
    private TextField textV1;

    @FXML
    private TextField textV2;

    @FXML
    private TextField textV3;

    @FXML
    private TextField textV5;

    @FXML
    private TextField textV4;

    @FXML
    private TextField textV6;

    @FXML
    private TextField textV7;

    @FXML
    private TextField textV8;

    @FXML
    private TextField textV9;

    @FXML
    private TextField textVA;

    @FXML
    private TextField textVB;

    @FXML
    private TextField textVC;

    @FXML
    private TextField textVD;

    @FXML
    private TextField textVE;

    @FXML
    private TextField textVF;

    @FXML
    private TextField textPCr;

    @FXML
    private TextField textIr;

    @FXML
    private TextField textOpCode;

    @FXML
    private TextField textOpCodeDs;

    public void setValuesToViewer() {
        try {
            textV0.setText(Emulator.exposeState().get_registers().readDataReg(0x0).toString());
            textV1.setText(Emulator.exposeState().get_registers().readDataReg(0x1).toString());
            textV2.setText(Emulator.exposeState().get_registers().readDataReg(0x2).toString());
            textV3.setText(Emulator.exposeState().get_registers().readDataReg(0x3).toString());
            textV4.setText(Emulator.exposeState().get_registers().readDataReg(0x4).toString());
            textV5.setText(Emulator.exposeState().get_registers().readDataReg(0x5).toString());
            textV6.setText(Emulator.exposeState().get_registers().readDataReg(0x6).toString());
            textV7.setText(Emulator.exposeState().get_registers().readDataReg(0x7).toString());
            textV8.setText(Emulator.exposeState().get_registers().readDataReg(0x8).toString());
            textV9.setText(Emulator.exposeState().get_registers().readDataReg(0x9).toString());
            textVA.setText(Emulator.exposeState().get_registers().readDataReg(0xa).toString());
            textVB.setText(Emulator.exposeState().get_registers().readDataReg(0xb).toString());
            textVC.setText(Emulator.exposeState().get_registers().readDataReg(0xc).toString());
            textVD.setText(Emulator.exposeState().get_registers().readDataReg(0xd).toString());
            textVE.setText(Emulator.exposeState().get_registers().readDataReg(0xe).toString());
            textVF.setText(Emulator.exposeState().get_registers().readDataReg(0xf).toString());
            textOpCode.setText(Emulator.exposeState().getCurrentOPCode().toString());
            textOpCodeDs.setText(new Disassembler().decodeOpcode(Emulator.exposeState().getCurrentOPCode()));
            textPCr.setText(Integer.toString(Emulator.exposeState().get_registers().readPCReg()));
            textIr.setText(Integer.toString(Emulator.exposeState().get_registers().readIReg()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setEmulator(Emulator emu) {
        _emu = emu;
    }


}
