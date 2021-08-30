package chip;

public class PByte extends Number implements Comparable<PByte> {

    public static final int MAX_VALUE = 0xff;
    public static final int MIN_VALUE = 0x0;
    public static final int BITS = 0x8;
    int _value;

    public PByte(int value) throws NumberFormatException {
        if (value >= MIN_VALUE && value <= MAX_VALUE) {
            _value = value;
        }else{
            throw new NumberFormatException("Value out of range. Value:\"" + value + "\"");
        }
    }

    public static PByte safeConstructor(int value){
        if(value >= MIN_VALUE && value <= MAX_VALUE ){
            return new PByte(value);
        }else if(value < MIN_VALUE){
            return new PByte(0);
        } else {
            return new PByte(value & 0xff);
        }
    }

    public int getFirstQuadbit(){
        return _value >> 4;
    }

    public int getSecondQuadbit(){
        return _value & 0xf;
    }

    @Override
    public String toString(){
        return "0x" + Integer.toHexString(_value).toUpperCase();
    }

    @Override
    public int compareTo(PByte pbyte) {
        return compare(this, pbyte);
    }

    public static int compare(PByte x, PByte y){
        return (x._value - y._value);
    }

    @Override
    public int intValue() {
        return _value;
    }

    @Override
    public long longValue() {
        return _value;
    }

    @Override
    public float floatValue() {
        return (float)_value;
    }

    @Override
    public double doubleValue() {
        return _value;
    }
}
