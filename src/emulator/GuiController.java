package emulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GuiController {

    private Main _main;
    @FXML
    private ImageView screenImg;
    @FXML
    private MenuItem b_m_f_load;
    @FXML
    private MenuItem b_m_f_run;
    @FXML
    private MenuItem b_m_o_viewer;
    @FXML
    private MenuItem b_m_o_debug;

    public void setMain(Main main) {
        _main = main;
    }

    @FXML
    void doCPUViewer(ActionEvent event) {
        _main.doCPUViewer();
    }

    @FXML
    void doDebug(ActionEvent event) {

    }

    @FXML
    void doLoad(ActionEvent event) {
        _main.doLoadFile();
        if (_main._flagFileIsLoaded.get()) {
            b_m_f_run.setDisable(false);
            b_m_o_viewer.setDisable(false);
            b_m_o_debug.setDisable(false);
        }
        screenImg.setImage(_main._currentFrame);
    }

    @FXML
    void doRun(ActionEvent event) {
        _main.doRun();
    }

    public void updateCurrentFrame(Image image) {
        screenImg.setImage(image);
    }

}
