package karlney.tetris.core;

public class PieceJLT extends AbstractPiece {

    public PieceJLT(int x, int y,  Board board, Square[][] piece) {
        super(x,y,board,piece);
    }

    public PieceJLT(PieceO copy, int x, int y, int rotation, Board board) {
        super(copy,x,y,rotation,board);
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
