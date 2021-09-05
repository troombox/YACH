package emulator;

import chip.Disassembler;
import chip.OpCode;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ViewerController {

    private Main _main;
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

    public void setMain(Main main) {
        _main = main;
    }

    /**
     * Sets values from list to fields, expected list structure: reg0x0,...,reg0xf,regPC,regI,opCode
     */
    public void setValuesToFields(ArrayList<Integer> list) {
        textV0.setText(Integer.toHexString(list.get(0x0)));
        textV1.setText(Integer.toHexString(list.get(0x1)));
        textV2.setText(Integer.toHexString(list.get(0x2)));
        textV3.setText(Integer.toHexString(list.get(0x3)));
        textV4.setText(Integer.toHexString(list.get(0x4)));
        textV5.setText(Integer.toHexString(list.get(0x5)));
        textV6.setText(Integer.toHexString(list.get(0x6)));
        textV7.setText(Integer.toHexString(list.get(0x7)));
        textV8.setText(Integer.toHexString(list.get(0x8)));
        textV9.setText(Integer.toHexString(list.get(0x9)));
        textVA.setText(Integer.toHexString(list.get(0xa)));
        textVB.setText(Integer.toHexString(list.get(0xb)));
        textVC.setText(Integer.toHexString(list.get(0xc)));
        textVD.setText(Integer.toHexString(list.get(0xd)));
        textVE.setText(Integer.toHexString(list.get(0xe)));
        textVF.setText(Integer.toHexString(list.get(0xf)));
        textPCr.setText(list.get(0x10).toString());
        textIr.setText(list.get(0x11).toString());
        textOpCode.setText(new OpCode(list.get(0x12)).toString());
        textOpCodeDs.setText(new Disassembler().decodeOpcode(list.get(0x12)));
    }

}
