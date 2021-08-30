package chip;

import java.util.ArrayList;

public class ErrorMsg {
    private ArrayList<String> _messages;

    public ErrorMsg() {
        _messages = new ArrayList<>();
    }

    public void addMsg(String msg){
        _messages.add(msg);
    }

    public String getMsg(){
        if(_messages.isEmpty())
            return "";
        String msg = _messages.get(_messages.size()-1);
        _messages.remove(_messages.size()-1);
        return msg;
    }

    public String getMsgOldest(){
        if(_messages.isEmpty())
            return "";
        String msg = _messages.get(0);
        _messages.remove(0);
        return msg;
    }

    public String getMsgKeep(){
        if(_messages.isEmpty())
            return "";
        return _messages.get(_messages.size()-1);
    }

    public String getMsgOldestKeep(){
        if(_messages.isEmpty())
            return "";
        return _messages.get(0);
    }

    public int getCount(){
        return _messages.size();
    }

    public boolean messageWaiting(){
        return !_messages.isEmpty();
    }

}
