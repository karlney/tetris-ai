package karlney.tetris.core;

public class Square{

    @SuppressWarnings("unchecked")
    public <T extends Square> T[][] getClone(int size){
        return (T[][])new Square[size][size];
    }

    private boolean filled = false;

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
