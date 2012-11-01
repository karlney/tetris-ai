package karlney.tetris.core;

public class OPiece extends BasePiece {

    public OPiece(OPiece copy, Board board) {
        super(copy, board);
    }

    public OPiece(PieceType type, Board board) {
        super(type, board);
    }

    public OPiece(OPiece copy, int x, int y, int rotation, Board board) {
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