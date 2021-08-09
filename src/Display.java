public class Display {
    private final static int DISPLAY_WIDTH = 64;
    private final static int DISPLAY_HEIGHT = 32;
    boolean[][] _display;

    public Display(){
        _display = new boolean[DISPLAY_HEIGHT][DISPLAY_WIDTH];
    }

    public void clearDisplay(){
        for(boolean[] row : _display){
            for(boolean c : row){
                c = false;
            }
        }
    }

    public void setPixelOn(int x, int y){
        _display[x][y] = true;
    }

    public void setPixelOff(int x, int y){
        _display[x][y] = false;
    }

    public void drawDisplay(){
        for(boolean[] row : _display){
            for(boolean c : row){
                if(c)
                    System.out.print("X");
                else
                    System.out.print(" ");
            }
            System.out.println("");
        }
    }

}
