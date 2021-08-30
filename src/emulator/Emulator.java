package emulator;

import chip.State;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Emulator implements Runnable {

    private static boolean _flagViewOpen;
    private static boolean _flagRunning;
    private static boolean _flagNeedsRedraw;

    private static int _clockSpeed;

    private static State _state;
    private static Image _currentFrame;

    private Viewer _cpuViewer = null;

    public Emulator() {
        _flagViewOpen = false;
        _flagRunning = false;
        _flagNeedsRedraw = false;

        _clockSpeed = 10;

        _state = new State();
        _currentFrame = displayToFrame();

    }

    public static State exposeState() {
        return _state;
    }

    public void connectClasses(Viewer cpuViewer) {
        _cpuViewer = cpuViewer;
        _cpuViewer.setEmulator(this);
    }

    @Override
    public void run() {
        runState();
    }

    private void updateFrame() {
        if (_state.displayGetChangedFlag()) {
            _currentFrame = displayToFrame();
            _state.displayTurnOffChangedFlag();
        }
    }

    private void runState() {
        int ms = 1000 / _clockSpeed;
        while (true) {
            _state.fetchOPCode();
            _state.executeCurrentOpCode();
            _state.checkMessages();
            updateFrame();
            if (_cpuViewer != null)
                _cpuViewer.setValuesToViewer();
            _state.step();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

}
