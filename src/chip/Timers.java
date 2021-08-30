package chip;

public class Timers {
    private int _delayTimer;
    private int _soundTimer;

    public Timers() {
        _delayTimer = 0;
        _soundTimer = 0;
    }

    public void setSoundTimer(int value) throws Exception{
        if(value < 0){
            throw new Exception("TIMER negative value");
        }
        _soundTimer = value;
    }

    public void setDelayTimer(int value) throws Exception{
        if(value < 0){
            throw new Exception("TIMER negative value");
        }
        _delayTimer = value;
    }

    public void tickTimers(){
        if(_delayTimer > 0)
            _delayTimer--;
        if(_soundTimer > 0)
            _soundTimer--;
    }

    public int getDelayTimer(){
        return _delayTimer;
    }

    public int getSoundTimer() {
        return _soundTimer;
    }
}
