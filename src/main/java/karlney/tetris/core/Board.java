package karlney.tetris.core;

import java.awt.*;

//TODO drawing and state needs to be separated!
public class Board {

    public static final int DEFAULT_ROWS =20;
    public static final int DEFAULT_COLS =10;

    public static final int DEFAULT_START_POS_X = 4;
    public static final int DEFAULT_START_POS_Y = -1;

    private int rows;
    private int cols;

    private Square[][] board;
    // private Square[][] backUp;

    public Board(int cols, int rows){
        board = new Square[cols +2][rows +2];

        for (int i=0;i<cols+2;i++){
            board[i][0]=new Square(PieceType.BOARD,false);
            board[i][rows+1]=new Square(PieceType.BOARD,true);
        }
        for (int i=0;i<rows+2;i++){
            board[0][i]=new Square(PieceType.BOARD,true);
            board[cols+1][i]=new Square(PieceType.BOARD,true);
        }

        for (int j=1; j<rows+1;j++)
            for (int i=1; i<cols+1; i++)
                board[i][j]=new Square(PieceType.BOARD,false);
    }

    public void addSquare(int i, int j, Square s){
        board[i][j]=s;
    }

    /*
    public void draw(Graphics g){
        for(int j=0; j<22; j++){
            for(int i=0; i<12; i++){
                board[i][j].draw(g,BLOCKSIZE*i,BLOCKSIZE*j);
            }
        }
    }
    */


    /**
     * This method first place the piece on the board
     * then checks if the newly placed piece completes one (or several) full row(s)
     * if so the row(s) are removed and the number of removed rows is returned
     */
    public int checkRemoved(Piece s){
        try {
            for(int i=0; i<s.getSize(); i++){
                for(int j=0; j<s.getSize(); j++){
                    if(s.isFilled(i, j))
                        board[i+s.getX()][j+s.getY()]=s.getSquare(i,j);
                }
            }
        }
        catch(Exception e) {
            return -1;
        }
        return checkFullRow();
    }


    /**
     * This method checks for full rows.
     * If there are any, they are then removed
     * and the number of removed rows is returned
     * @return
     */
    public int checkFullRow(){

        int removed=0;

        for(int j= rows; j>0; j--){

            boolean fullRow = true;

            for(int i= cols; i>0; i--){
                fullRow = (fullRow && board[i][j].isFilled());
            }
            if(fullRow){
                removeRow(j);
                j++; //Because you are moving j down with 1
                removed++;
            }
        }

        return removed;
    }


    public void removeRow(int j){
        for(int i=1; i<= cols; i++){
            board[i][j]=new Square(PieceType.BOARD,false);
        }
        moveDown(j);
    }


    public void moveDown(int row){
        for(int j=row; j>1; j--){
            for(int i=1; i<11; i++){
                board[i][j]= board[i][j-1];
            }
        }
    }




    /*Backup Functions */
    /*
    public void createBackup(){
        backUp= new Square[cols +2][rows +2];
        for(int i=0; i< cols +2; i++){
            for(int j=0; j< rows +2; j++){
                backUp[i][j]=new Square(board[i][j]);
            }
        }
    }

    public void retriveBackup(){
        board =backUp;
        backUp=null;
    }
    */


    /* ****AI planning methods*****  */

    public boolean allowedPlacement(Piece s){
        try {
            for(int i=0; i<s.getSize(); i++){
                for(int j=0; j<s.getSize(); j++){
                    if(s.isFilled(i,j) && board[i+s.getX()][j+s.getY()].isFilled())
                        return false;
                }
            }
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }


    /*public double getNearlyFullRows(){
       double out=0;
       int lastrow=0;
       for(int j=ROW-getMaxHeight() ; j<ROW; j++){
          int block=0;
          for(int i=COL; i>0; i--){
             if ( gameField[i][j].isFilled())
                block++;
          }
          out=out+block-lastrow;
          lastrow=block;
       }
       return out;
    }*/

    public int getFullRows(){
        int removed=0;
        for(int j= rows; j>0; j--){
            boolean fullRow = true;
            for(int i= cols; i>0; i--){
                fullRow = (fullRow && board[i][j].isFilled());
            }
            if(fullRow){
                removed++;
            }
        }
        return removed;
    }

    public double getBlockDensity(){
        return (getNrOfBlocks()+0.0)/(getMaxHeight()*10.0);
    }

    public double getNrHoles(){
        double nr=0;
        for (int i=0; i<= cols; i++)	{
            int last=0;
            for (int j= rows; j>0; j--){
                if (board[i][j].isFilled()){
                    nr+=1*Math.pow(last,1.25);
                    last=0;
                }
                else{
                    last++;
                }
            }
        }
        return nr;
    }

    public int getBlocksInRow(int j){
        int nr=0;
        for(int i= cols; i>0; i--){
            if ( board[i][j].isFilled()){
                nr++;
            }
        }
        return nr;
    }

    public int getMaxHeight(){
        for(int j=0; j< rows; j++){
            for(int i= cols; i>0; i--){
                if ( board[i][j].isFilled()){
                    return rows -j;
                }
            }
        }
        return 0;
    }

    public int getNrOfBlocks(){
        int nr=0;
        for(int j= rows; j>0; j--){
            for(int i= cols; i>0; i--){
                if ( board[i][j].isFilled()){
                    nr++;
                }
            }
        }
        return nr;
    }

    public double checkForSequences(){
        double out=0;
        for(int j= rows; j>0; j--){
            int seq=0;
            for(int i= cols; i>0; i--){
                if ( board[i][j].isFilled()){
                    seq++;
                }else{
                    out+= Math.pow(2,seq)-1;
                }
            }
        }
        return out;
    }

    public boolean placeBlock(Piece s){
        for(int i=0; i<s.getSize(); i++){
            for(int j=0; j<s.getSize(); j++){
                if(s.isFilled(i,j))
                    try{
                        if (board[i+s.getX()][j+s.getY()].isFilled())
                            return false;
                        else{
                            board[i+s.getX()][j+s.getY()]= s.getSquare(i,j);
                        }
                    }
                    catch(Exception e) {
                        return false;
                    }
            }
        }
        return true;
    }

    /*
     public void removeBlock(Block s){
       for(int i=0; i<s.shape.length; i++){
          for(int j=0; j<s.shape.length; j++){
             if(s.shape[i][j].isFilled())
                gameField[i+s.getX()][j+s.getY()].isFilled()=false;
          }
       }
    }*/


    /*
    public Object clone(){
        Board out =new Board(true);
        out.board = new Square[cols +2][rows +2];
        for(int j= rows +1; j>=0; j--){
            for(int i= cols +1; i>=0; i--){
                out.board[i][j]=new Square(10, board[i][j].isFilled());
            }
        }

        return out;
    }
    */

    public boolean checkMove(int x, int y, Square[][] shape) {
        for(int j=0; j<shape.length; j++){
            for(int i=0; i<shape[0].length; i++){
                try{
                    if(board[x+i][y+j].isFilled() && shape[i][j].isFilled())
                        return false;
                }
                catch (Exception e) { }
            }
        }
        return true;
    }

    //TODO
    public int getStartPosX(PieceType type) {
        return DEFAULT_START_POS_X;
    }

    //TODO
    public int getStartPosY(PieceType type) {
        return DEFAULT_START_POS_Y;
    }

    //TODO
    public int getRowsToFall(BasePiece basePiece) {
        return 0;
    }
}
