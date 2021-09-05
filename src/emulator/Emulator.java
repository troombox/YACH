package emulator;

import chip.State;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Emulator implements Runnable {

    Main _main;

    //Data
    State _state;
    Image _currentFrame;

    public Emulator(Main main) {
        _state = new State();
        _currentFrame = displayToFrame();
        _main = main;
    }

    @Override
    public void run() {
        doStep();
    }

    private void doStep() {
        //Do the step
        _state.fetchOPCode();
        _state.executeCurrentOpCode();
        _state.checkMessages();
        updateFrame();
        //Do the keep-up with the GUI
        _main.updateCurrentFrame();
        _main.updateCPUViewer();

        _state.step();
    }

    private void updateFrame() {
        if (_state.displayGetChangedFlag()) {
            _currentFrame = displayToFrame();
            _state.displayTurnOffChangedFlag();
        }
    }

    private Image displayToFrame() {
        int height = 128;
        int width = 256;
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        Color offPixel = Color.BLACK;
        Color onPixel = Color.GREEN;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setColor(x, y, ((_state.displayGetDataAtCoord(x / 4, y / 4)) ? onPixel : offPixel));
            }
        }
        return image;
    }

    public ArrayList<Integer> stateReport() {
        return _state.stateReport();
    }

}
