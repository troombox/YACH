package chip;

public class Display {
    private final static int DISPLAY_WIDTH = 64;
    private final static int DISPLAY_HEIGHT = 32;
    boolean[][] _display;
    private boolean _displayChangedFlag;

    public Display(){
        _display = new boolean[DISPLAY_HEIGHT][DISPLAY_WIDTH];
        _displayChangedFlag = false;
    }

    public void clearDisplay(){
        for(int i = 0; i < DISPLAY_HEIGHT; i++){
            for(int j = 0; j <DISPLAY_WIDTH; j++){
                _display[i][j] = false;
            }
        }
    }

    public boolean drawSprite(int x, int y, PByte data) {
        if (x < 0 || x >= DISPLAY_WIDTH || y < 0 || y >= DISPLAY_HEIGHT) {
            throw new IllegalArgumentException();
        }
        int bytes = data.intValue();
        boolean returnValue = false;
        try {
            for (int i = 0; i < PByte.BITS; i++) {
                if (x + i >= DISPLAY_WIDTH) {
                    return returnValue;
                }
                int pixel = (bytes >> (PByte.BITS - (1 + i))) & 0x1;
                if (pixel == 0) {
                    if (setPixelOff((x + i), y)) {
                        returnValue = true;
                    }
                } else {
                    setPixelOn((x + i), y);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }

    public boolean setPixelOn(int x, int y) {
        if (x < 0 || x >= DISPLAY_WIDTH) {
            throw new IllegalArgumentException();
        }
        if (y < 0 || y >= DISPLAY_HEIGHT) {
            throw new IllegalArgumentException();
        }
        boolean temp =  _display[y][x];
        _display[y][x] = true;
        return temp;
    }

    public boolean setPixelOff(int x, int y) {
        if (x < 0 || x >= DISPLAY_WIDTH) {
            throw new IllegalArgumentException();
        }
        if (y < 0 || y >= DISPLAY_HEIGHT) {
            throw new IllegalArgumentException();
        }
        boolean temp =  _display[y][x];
        _display[y][x] = false;
        return temp;
    }

    public void drawDisplayToOut(){
        for(boolean[] row : _display){
            for (boolean c : row) {
                if (c)
                    System.out.print("\u25AE");
                else
                    System.out.print("\u25AF");
            }
            System.out.println();
        }
    }

    public boolean getPixelState(int x, int y) {
        if (x < 0 || x >= DISPLAY_WIDTH) {
            throw new IllegalArgumentException();
        }
        if (y < 0 || y >= DISPLAY_HEIGHT) {
            throw new IllegalArgumentException();
        }
        return _display[y][x];
    }


    public boolean getDisplayChangedFlag() {
        return _displayChangedFlag;
    }

    public void setDisplayChangedFlag(boolean flag) {
        _displayChangedFlag = flag;
    }

}
