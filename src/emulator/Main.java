package emulator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    //Controllers
    GuiController _guiController = null;
    ViewerController _viewerController = null;

    //Flags
    AtomicBoolean _flagFileIsLoaded = new AtomicBoolean(false);
    AtomicBoolean _flagFileIsRunning = new AtomicBoolean(false);
    AtomicBoolean _flagWindowCPUViewerIsOpen = new AtomicBoolean(false);

    //Data
    Emulator _emulator = null;
    Image _currentFrame = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        _emulator = new Emulator(this);

        Pane root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("guiFxml.fxml"));
            root = loader.load();
            _guiController = loader.getController();
            _guiController.setMain(this);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("YACH");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Opens "Choose File" dialog and loads selected file into Emulator memory
     */
    public void doLoadFile() {
        Stage stage = new Stage();
        stage.setTitle("Open a File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Chip8 Files", "*.ch8"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            //At this point we are expected to stop old running file if there's one, clear mem and reset
            if (_flagFileIsLoaded.get()) {
                _emulator = new Emulator(this);
                _flagFileIsLoaded.set(false);
                _flagFileIsRunning.set(false);
            }
            _emulator._state.loadFileToMem(file);
            _currentFrame = _emulator._currentFrame;
            _flagFileIsLoaded.set(true);
        }
    }

    /**
     * Opens CPU Viewer window
     */
    public void doCPUViewer() {
        if (_flagWindowCPUViewerIsOpen.get())
            return;
        Pane root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ViewerFxml.fxml"));
            root = loader.load();
            _viewerController = loader.getController();
            _viewerController.setMain(this);
            _flagWindowCPUViewerIsOpen.set(true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("CPU viewer");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> _flagWindowCPUViewerIsOpen.set(false));
        updateCPUViewer();
        stage.show();
    }


    public void doRun() {
        if (!_flagFileIsLoaded.get())
            return;
        _flagFileIsRunning.set(true);
        // long-running operation runs on different thread
        Thread thread = new Thread(() -> {
            Runnable emulator = _emulator;
            // main loop part
            while (_flagFileIsRunning.get()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // UI update is run on the Application thread
                Platform.runLater(emulator);
            }
        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

    public void updateCurrentFrame() {
        _currentFrame = _emulator._currentFrame;
        _guiController.updateCurrentFrame(_currentFrame);
    }

    public void updateCPUViewer() {
        if (!_flagWindowCPUViewerIsOpen.get()) {
            return;
        }
        if (_viewerController == null) {
            return;
        }
        _viewerController.setValuesToFields(_emulator.stateReport());
    }

    //TODO: DELETE THIS!
    private void press() {
        // long-running operation runs on different thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable emulator = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Clock");
                    }
                };
                // main loop part
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(emulator);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

}