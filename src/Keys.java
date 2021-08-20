import java.util.stream.IntStream;

public class Keys {
    public static final int KEYS_NUM = 0xf;
    private boolean[] _keys;
    private boolean _keyFlag;

    public Keys(){
        _keys = new boolean[KEYS_NUM];
        IntStream.range(0x0,KEYS_NUM).forEach(i -> _keys[i] = false);
        _keyFlag = false;
    }

    public boolean getKeyState(int key){
        if(key < 0 || key >= KEYS_NUM){
            throw new IllegalArgumentException("KEY not exists");
        }
        return _keys[key];
    }

    public void setKeyState(int key, boolean state){
        if(key < 0 || key >= KEYS_NUM){
            throw new IllegalArgumentException("KEY not exists");
        }
        _keys[key] = state;
    }

    public void setKeyFlag(boolean state){
        _keyFlag = state;
    }

    public boolean getKeyFlag(){
        return _keyFlag;
    }
}
