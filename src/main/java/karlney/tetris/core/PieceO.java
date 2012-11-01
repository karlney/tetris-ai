package karlney.tetris.core;

public class PieceO extends PieceBase {

    public PieceO(PieceO copy, Board board) {
        super(copy, board);
    }

    public PieceO(PieceType type, Board board, Square[][] piece) {
        super(type, board, piece);
    }

    public PieceO(PieceO copy, int x, int y, int rotation, Board board) {
        super(copy, x, y, rotation, board);
    }

    public Square[][] getRotatedShape(){
        return piece;
    }

    @Override
    public void rotateNoCheck() {}

    @Override
    public boolean rotateIfPossible() {return false;}

}