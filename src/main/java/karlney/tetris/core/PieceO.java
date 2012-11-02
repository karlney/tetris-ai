package karlney.tetris.core;

public class PieceO extends AbstractPiece {

    public PieceO(int x, int y,  Board board, Square[][] piece) {
        super(x,y,board,piece);
    }

    public PieceO(PieceO copy, int x, int y, int rotation, Board board) {
        super(copy,x,y,rotation,board);
    }

    @Override
    public void rotateNoCheck() {}

    @Override
    public boolean rotateIfPossible() {return false;}

}