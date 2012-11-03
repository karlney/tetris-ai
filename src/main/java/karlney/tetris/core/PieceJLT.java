package karlney.tetris.core;

public class PieceJLT extends AbstractPiece {

    public PieceJLT(int x, int y,  Board board, Square[][] piece) {
        super(x,y,board,piece);
    }

    public PieceJLT(PieceJLT copy, int x, int y, int rotation, Board board) {
        super(copy,x,y,rotation,board);
    }

    @Override
    public Piece getTranslatedCopy(int x, int y, int rotation, Board board) {
        return new PieceJLT(this, x, y, rotation, board);
    }

    @Override
    public int getPossibleRotations() {
        return 4;
    }

    @Override
    public synchronized boolean rotateIfPossible(){
        boolean rotationPossible = board.checkMove(x,y,getRotatedShape());
        if(rotationPossible){
            piece = getRotatedShape();
            rotation = (rotation+1)%getPossibleRotations();
        }
        return rotationPossible;
    }


    @Override
    public void rotateNoCheck(){
        piece = getRotatedShape();
        rotation = (rotation+1)%getPossibleRotations();
    }

    /**
     * @return the shape matrix for this block rotated 90 degrees
     */
    private Square[][] getRotatedShape(){
        Square[][] shapeClone= new Square[getSize()][getSize()];
        for (int i=0; i<3;i++){
            for(int x=0; x<getSize(); x++){
                for(int y=0; y<getSize(); y++){
                    shapeClone[x][y]= piece[y][2-x];
                }
            }
        }
        //The pieces needs to be rotated counter clockwise, therefore this is needed
        Square[][] out= new Square[getSize()][getSize()];
        for (int i=0; i<3;i++){
            for(int x=0; x<getSize(); x++){
                for(int y=0; y<getSize(); y++){
                    out[x][y]= shapeClone[2-x][2-y];
                }
            }
        }
        return out;
    }

}

/*
x,y   -> xp,yp
2,2 -> 2,0
1,1 -> 1,1
1,2 -> 2,1
0,0 -> 0,2
*/
