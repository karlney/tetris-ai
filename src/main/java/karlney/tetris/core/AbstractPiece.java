package karlney.tetris.core;

public abstract class AbstractPiece implements Piece {

    protected final Board board;

    protected Square[][] shape;
    protected boolean falling;

    protected int slides;

    protected int x;
    protected int y;
    protected int rotation;


    /**
     * Default constructor
     *
     * @param x start pos x
     * @param y start pos y
     * @param board the new board that this piece is placed in
     * @param shape the piece squares
     */
    public AbstractPiece(int x, int y, Board board, Square[][] shape){
        this.board = board;
        falling=false;
        slides = 0;
        this.shape = shape;
        this.x=x;
        this.y=y;
        this.rotation=0;
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
        this(x,y,board,copyPiece(copy.shape));
        for (int i=0; i<rotation; i++){
            rotateNoCheck();
        }
    }

    private static Square[][] copyPiece(Square[][] piece) {
        Square[][] out = new Square[piece.length][piece.length];
        for (int i=0; i<piece.length;i++){
            System.arraycopy(piece[i], 0, out[i], 0, piece.length);
        }
        return out;
    }


    @Override
    public abstract boolean rotateIfPossible();

    @Override
    public abstract void rotateNoCheck();

    @Override
    public abstract Piece getTranslatedCopy(int x, int y, int rotation, Board board);

    @Override
    public abstract int getPossibleRotations();

    @Override
    public synchronized boolean stepDown(){
        if (falling) {
            return false;
        }
        boolean downMovePossible = board.checkMove(x,y+1, shape);
        if (downMovePossible){
            y++;
        }
        return downMovePossible;
    }

    @Override
    public synchronized void stepDownAFAP(){
        while (stepDown()){
        }
    }

    @Override
    public synchronized boolean moveDown(){
        if (board.checkMove(x,y+1, shape)){
            y++;
            slides=0;
            return false;
        } else {
            if (falling) return true;
            if (slides<1){
                slides++;
                return false;
            }else{
                slides=0;
                return true;
            }
        }
    }

    @Override
    public synchronized boolean moveSideWays(PlayerInput dir){
        if (falling) {
            return false;
        }
        if (dir==PlayerInput.LEFT){
            if (board.checkMove(x - 1, y, shape)){
                x=x-1;
                return true;
            }
        }
        if (dir==PlayerInput.RIGHT){
            if (board.checkMove(x + 1, y, shape)){
                x=x+1;
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized void fallDown(){
        falling=true;
    }

    @Override
    public synchronized boolean isFalling() {
        return falling;
    }

    public String toString(){
        return this.shape[0][0].getType()+"{x="+x+" y="+y+"}";
    }

    public int getSize() {
        return shape.length;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    @Override
    public synchronized Square getSquare(int i, int j) {
        return shape[i][j];
    }

    @Override
    public synchronized Square[][] getShape() {
        return shape;
    }

    @Override
    public synchronized boolean isFilled(int i, int j) {
        return shape[i][j].isFilled();
    }

    @Override
    public synchronized int getRotation() {
        return rotation;
    }

}
