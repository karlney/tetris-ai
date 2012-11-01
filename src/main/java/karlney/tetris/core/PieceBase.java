package karlney.tetris.core;

public class PieceBase implements Piece {

    protected final Board board;

    protected Square[][] piece;
    protected boolean falling;
    protected boolean firstTryUsed;
    protected int x;
    protected int y;


    /**
     * Copy constructor that clones the shape and assigns the piece to a given board
     *
     * @param copy the old piece this new one is based upon
     * @param board the new board that this piece is placed in
     */
    public PieceBase(PieceBase copy, Board board) {
        this.piece = copy.piece.clone();
        this.falling = copy.falling;
        this.firstTryUsed = copy.firstTryUsed;
        this.x = copy.x;
        this.y = copy.y;
        this.board = board;
    }

    /**
     * Default constructor
     *
     * @param type the piece type
     * @param board the new board that this piece is placed in
     */
    public PieceBase(PieceType type, Board board, Square[][] piece){
        this.board = board;
        falling=false;
        firstTryUsed =true;
        this.piece = piece;
        x= board.getStartPosX(type);
        y= board.getStartPosY(type);
    }

    /**
     * This constructor can be used to copy a block and move it to a new arbitrary position and rotation and to a new Board
     * This is very useful for AI planning.
     *
     * NOTE this method does NOT check if the final position is valid!
     *
     * @param copy the old piece this new one is based upon
     * @param x the new x position
     * @param y the new y position
     * @param rotation the new rotation (relative to the current rotation), 0 means current rotation, 1 means to rotate one time, 2 two times etc.
     * @param board the new board that this piece is placed in
     */
    public PieceBase(PieceBase copy, int x, int y, int rotation, Board board){
        this(copy,board);
        this.x=x;
        this.y=y;
        for (int i=0; i<rotation; i++){
            rotateNoCheck();
        }
    }


    @Override
    public synchronized boolean rotateIfPossible(){
        boolean out = board.checkMove(x,y,getRotatedShape());
        if(out){
            piece = getRotatedShape();
        }
        return out;

    }


    @Override
    public void rotateNoCheck(){
        piece = getRotatedShape();
    }

    /**
     * @return the shape matrix for this block rotated 90 degrees
     */
    protected Square[][] getRotatedShape(){
        Square[][] shapeClone= new Square[getSize()][getSize()];
        for(int x=0; x<getSize(); x++){
            for(int y=0; y<getSize(); y++){
                shapeClone[x][y]= piece[y][2-x];
            }
        }
        return shapeClone;
    }


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
    public synchronized boolean moveSideWays(MoveDirection dir){
        if (falling) {
            return false;
        }
        if (dir==MoveDirection.LEFT){
            if (board.checkMove(x - 1, y, piece)){
                x=x-1;
                return true;
            }
        }
        if (dir==MoveDirection.RIGHT){
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
        String s="shape="+this.piece[0][0].getType()+" x="+x+" y="+y+"\n";
        return s;
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
