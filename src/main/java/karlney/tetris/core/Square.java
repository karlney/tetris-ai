package karlney.tetris.core;

/**
 *
 * A square represents one cell on the Tetris board.
 * It always has a type so that we can color the different squares differently.
 *
 */
public class Square{

    private PieceType type;

    private boolean filled;

    public Square(PieceType type) {
        this.type = type;
        this.filled = false;
    }

    public Square(PieceType type, boolean filled) {
        this.type = type;
        this.filled = filled;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }
}
