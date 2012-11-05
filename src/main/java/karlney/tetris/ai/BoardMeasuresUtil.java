package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.Square;

/**
 * TODO javadoc
 *
 * AI planning methods
 *
 * <p/>
 * Date: 2012-11-04
 * Time: 10:01
 *
 * @author karl.neyvaldt
 */
public class BoardMeasuresUtil {

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

    /*
    public static int getFullRows(Board board){
        int removed=0;
        for(int j= board.getRows(); j>0; j--){
            boolean fullRow = true;
            for(int i= board.getCols(); i>0; i--){
                fullRow = (fullRow && board.getBoard()[i][j].isFilled());
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

    /*
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
    */

    /*
    private int getBlocksInRow(int j){
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


    /*
    It’s best to try not to have any holes at all, but sometimes having a hole or two is inevitable.
    What can we do after we have holes in our formation? Good question, but we should not pile more blocks on top of our holes.
    If we define a blockade as any block that’s directly above a hole, we should penalize blockades:
     /
    public int getBlockades() {
        throw new UnsupportedOperationException();
    }

    */

    /*
1. Landing Height: The height where the piece is put (= the height of the column + (the height of the piece / 2))
     */
    public static double getLandingHeight(int boardRows, Piece piece) {
        return boardRows - piece.getY() - piece.getSize()/2.0 +1;
    }

    /*
3. Row Transitions: The total number of row transitions. A row transition occurs when an empty cell is adjacent to a filled cell on the same row and vice versa.
    */
    public static int getRowTransitions(Board board) {
        int nr=0;
        for (int j=board.getRows(); j>0; j--){
            for (int i=1; i<board.getCols(); i++)	{
                if (board.isFilled(i+1,j)!=board.isFilled(i,j)){
                    nr++;
                }
            }
        }
        return nr;
    }

    /*
4. Column Transitions: The total number of column transitions. A column transition occurs when an empty cell is adjacent to a filled cell on the same column and vice versa.
     */
    public static int getColumnTransitions(Board board) {
        int nr=0;
        for (int j=board.getRows()+1; j>1; j--){
            for (int i=1; i<board.getCols()+1; i++)	{
                if (board.isFilled(i,j-1)!=board.isFilled(i,j)){
                    nr++;
                }
            }
        }
        return nr;
    }

    /*
5. Number of Holes: A hole is an empty cell that has at least one filled cell above it in the same column.
    */
    public static int getNrHoles(Board board){
        int nr=0;
        for (int i=0; i<=board.getCols(); i++)	{
            int last=0;
            for (int j= board.getRows(); j>0; j--){
                if (board.isFilled(i, j)){
                    nr+=last;
                    last=0;
                }
                else{
                    last++;
                }
            }
        }
        return nr;
    }

    /*
6. Well Sums: A well is a succession of empty cells such that their left cells and right cells are both filled.
    */
    public static int getWellSums(Board board) {
        int nr=0;
        for (int i=1; i<=board.getCols()+1; i++){
            for (int j= board.getRows(); j>0; j--){
                if (!board.getSquare(i,j).isFilled() && board.getSquare(i-1,j).isFilled() && board.getSquare(i+1,j).isFilled()){
                    //Found well cell, count it plus the number of empty cells below it
                    nr++;
                    for (int k = j+1; k<board.getRows(); k++){
                        if (!board.getSquare(i,k).isFilled()) {
                            nr++;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        return nr;
    }

}
