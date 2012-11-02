package karlney.tetris.core;

public abstract class AbstractPiece implements Piece {

    protected final Board board;

    protected Square[][] piece;
    protected boolean falling;
    protected boolean firstTryUsed;
    protected int x;
    protected int y;


    /**
     * Default constructor
     *
     * @param x start pos x
     * @param y start pos y
     * @param board the new board that this piece is placed in
     * @param piece the piece squares
     */
    public AbstractPiece(int x, int y, Board board, Square[][] piece){
        this.board = board;
        falling=false;
        firstTryUsed =true;
        this.piece = piece;
        this.x=x;
        this.y=y;
    }

    /**
     * This constructor can be used to copy a block and move it to a new arbitrary position and rotation and to a new Board
     * This is very useful for AI planning.
     *
     * NOTE this method does NOT check if the final position is valid!
     *
     * @param copy the old piece this new one is based upon
     * @param x the x position
     * @param y the y position
     * @param rotation the new rotation (relative to the current rotation), 0 means current rotation, 1 means to rotate one time, 2 two times etc.
     * @param board the new board that this piece is placed in
     */
    public AbstractPiece(AbstractPiece copy, int x, int y, int rotation, Board board){
        this(x,y,board,copy.piece.clone());
        for (int i=0; i<rotation; i++){
            rotateNoCheck();
        }
    }


    @Override
    public abstract boolean rotateIfPossible();


    @Override
    public abstract void rotateNoCheck();


    @Override
    public synchronized boolean stepDown(){
        boolean out= board.checkMove(x,y+1, piece);
        if (out){
            y++;
        }
        return out;
    }

    @Override
    public void stepDownAFAP(){
        while (stepDown()){
        }
    }

    @Override
    public synchronized boolean moveDown(){
        if (board.checkMove(x,y+1, piece)){
            y=y+1;
            return false;
        }
        else {
            if (firstTryUsed){
                firstTryUsed =!firstTryUsed;
                return true;
            }
            else{
                firstTryUsed =true;
                return false;
            }
        }
    }

    @Override
    public synchronized boolean moveSideWays(PlayerInput dir){
        if (falling) {
            return false;
        }
        if (dir==PlayerInput.LEFT){
            if (board.checkMove(x - 1, y, piece)){
                x=x-1;
                return true;
            }
        }
        if (dir==PlayerInput.RIGHT){
            if (board.checkMove(x + 1, y, piece)){
                x=x+1;
                return true;
            }
        }
        return false;
    }

    @Override
    public int fallDown(){
        falling=true;
        return board.getRowsToFall(this);
    }


    public String toString(){
        return "shape="+this.piece[0][0].getType()+" x="+x+" y="+y+"\n";
    }

    public int getSize() {
        return piece.length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public Square getSquare(int i, int j) {
        return piece[i][j];
    }

    @Override
    public boolean isFilled(int i, int j) {
        return piece[i][j].isFilled();
    }


}
