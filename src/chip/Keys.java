package chip;

import java.util.stream.IntStream;

public class Keys {
    public static final int KEYS_NUM = 0xf;
    private boolean[] _keys;
    private boolean _keyFlag;
    private int _lastKey;

    public Keys() {
        _keys = new boolean[KEYS_NUM];
        IntStream.range(0x0,KEYS_NUM).forEach(i -> _keys[i] = false);
        _keyFlag = false;
        _lastKey = -1;
    }

    public boolean getKeyState(int key){
        if(key < 0 || key >= KEYS_NUM){
            throw new IllegalArgumentException("KEY not exists");
        }
        return _keys[key];
    }

    public void setKeyState(int key, boolean state) {
        if (key < 0 || key >= KEYS_NUM) {
            throw new IllegalArgumentException("KEY not exists");
        }
        _keys[key] = state;
    }

    public void keyPress(int key) {
        if (key < 0 || key >= KEYS_NUM) {
            throw new IllegalArgumentException("KEY not exists");
        }
        _keys[key] = true;
        _keyFlag = true;
        _lastKey = key;
    }

    public void keyRelease(int key) {
        if (key < 0 || key >= KEYS_NUM) {
            throw new IllegalArgumentException("KEY not exists");
        }
        _keys[key] = false;
        _lastKey = -1;
    }

    public int getLastKeyPressed() {
        return _lastKey;
    }

    public void setKeyFlag(boolean state) {
        _keyFlag = state;
    }

    public boolean getKeyFlag() {
        return _keyFlag;
    }
}
