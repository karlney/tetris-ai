package karlney.tetris.core;

public abstract class PieceISZ extends AbstractPiece {

    private boolean tilted;

    public PieceISZ(int x, int y, Board board, Square[][] piece) {
        super(x,y,board,piece);
        tilted=true;
    }

    public PieceISZ(PieceISZ copy, int x, int y, int rotation, Board board) {
        this(x, y, board, copy.piece.clone());
        this.tilted = copy.tilted;
        for (int i=0; i<rotation; i++){
            rotateNoCheck();
        }
    }

    @Override
    public synchronized boolean rotateIfPossible(){
        boolean rotationPossible = board.checkMove(x,y,getRotatedShape());
        if(rotationPossible){
            piece = getRotatedShape();
            tilted=!tilted;
            rotation = (rotation+1)%getPossibleRotations();
        }
        return rotationPossible;
    }

    @Override
    public void rotateNoCheck(){
        piece = getRotatedShape();
        tilted=!tilted;
        rotation = (rotation+1)%getPossibleRotations();
    }

    @Override
    public int getPossibleRotations() {
        return 2;
    }

    protected Square[][] buildSquares(boolean[][] shape, PieceType type){
        Square[][] sh= new Square[shape.length][shape.length];
        for (int i=0; i<shape.length;i++){
            for (int j=0; j<shape.length;j++){
                sh[i][j]=new Square(type,shape[i][j]);
            }
        }
        return sh;
    }

    protected abstract Square[][] getRotatedShape();

    protected boolean isTilted() {
        return tilted;
    }
}