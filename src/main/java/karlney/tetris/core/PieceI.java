package karlney.tetris.core;

public class PieceI extends PieceBase {

    private boolean tilted;

    public PieceI(PieceI copy, Board board) {
        super(copy, board);
        this.tilted = copy.tilted;
    }

    public PieceI(PieceType type, Board board, Square[][] piece) {
        super(type, board, piece);
        tilted=true;
    }

    public PieceI(PieceI copy, int x, int y, int rotation, Board board) {
        super(copy, board);
        this.tilted = copy.tilted;
        this.x=x;
        this.y=y;
        for (int i=0; i<rotation; i++){
            rotateNoCheck();
        }
    }

    @Override
    public synchronized boolean rotateIfPossible(){
        if(board.checkMove(x, y, getRotatedShape())) {
            piece = getRotatedShape();
            tilted=!tilted;
            return true;
        }
        return false;
    }

    @Override
    public void rotateNoCheck(){
        piece = getRotatedShape();
    }

    protected Square[][] getRotatedShape(){
        Square[][] sh= new Square[4][4];
        for (int i=0; i<4;i++){
            for (int j=0; j<4;j++){
                boolean filled = (j==1 && tilted) || (i==1 && !tilted);
                sh[i][j]=new Square(PieceType.I,filled);
            }
        }
        return sh;
    }


}