package karlney.tetris.core;

import java.awt.*;

//TODO drawing and state needs to be separated!
public class GameField{

    public static final int ROW=20, COL=10,BLOCKSIZE=20;
    public Square[][] gameField = new Square[COL+2][ROW+2];
    private Square[][] backUp;

    public GameField(boolean a){

    }

    public GameField(){
        for (int i=0;i<12;i++){
            gameField[i][0]=new Square(10,false);
            gameField[i][21]=new Square(10,true);
        }
        for (int i=0;i<22;i++){
            gameField[0][i]=new Square(10,true);
            gameField[11][i]=new Square(10,true);
        }

        for (int j=1; j<21;j++)
            for (int i=1; i<11; i++)
                gameField[i][j]=new Square(10,false);
    }

    public void addSquare(int i, int j, Square s ){
        gameField[i][j]=s;
    }

    public void draw(Graphics g){
        for(int j=0; j<22; j++){
            for(int i=0; i<12; i++){
                gameField[i][j].draw(g,BLOCKSIZE*i,BLOCKSIZE*j);
            }
        }
    }


    /*
            This method first place the block on the gamefield
            then checks if the newly placed block completes one (or several) full row(s)
            if so the row is removed and the number of removed rows is returned

            :-) det h�nder mycket h�r!
        */

    public int checkRemoved(Block s){
        try {
            for(int i=0; i<s.shape.length; i++){
                for(int j=0; j<s.shape.length; j++){
                    if(s.shape[i][j].filled)
                        gameField[i+s.x][j+s.y]=s.shape[i][j];
                }
            }
        }
        catch(Exception e) {
            return -1;
        }
        return checkFullRow();
    }


    /*
            This method checks for full rows if there are any they are removed
            and the number of removed rows is returned
        */
    public int checkFullRow(){

        int removed=0;

        for(int j=ROW; j>0; j--){

            boolean fullRow = true;

            for(int i=COL; i>0; i--){
                fullRow = (fullRow && gameField[i][j].filled);
            }
            if(fullRow){
                removeRow(j);
                j++; //Eftersom att man flyttar ner rad j
                removed++;
            }
        }

        return removed;
    }


    public void removeRow(int j){

        for(int i=1; i<=COL; i++){
            gameField[i][j].filled=false;
        }
        moveDown(j);
    }


    public void moveDown(int row){
        for(int j=row; j>1; j--){
            for(int i=1; i<11; i++){
                gameField[i][j]=gameField[i][j-1];
            }
        }
    }




    /*Backup Functions */

    public void createBackup(){
        backUp= new Square[COL+2][ROW+2];
        for(int i=0; i<COL+2; i++){
            for(int j=0; j<ROW+2; j++){
                backUp[i][j]=new Square(gameField[i][j]);
            }
        }
    }

    public void retriveBackup(){
        gameField=backUp;
        backUp=null;
    }


    /* ****AI planning methods*****  */

    public boolean allowedPlacement(Block s){
        try {
            for(int i=0; i<s.shape.length; i++){
                for(int j=0; j<s.shape.length; j++){
                    if(s.shape[i][j].filled && gameField[i+s.x][j+s.y].filled)
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
             if ( gameField[i][j].filled)
                block++;
          }
          out=out+block-lastrow;
          lastrow=block;
       }
       return out;
    }*/

    public int getFullRows(){
        int removed=0;
        for(int j=ROW; j>0; j--){
            boolean fullRow = true;
            for(int i=COL; i>0; i--){
                fullRow = (fullRow && gameField[i][j].filled);
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
        for (int i=0; i<=COL; i++)	{
            int last=0;
            for (int j=ROW; j>0; j--){
                if (gameField[i][j].filled){
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
        for(int i=COL; i>0; i--){
            if ( gameField[i][j].filled){
                nr++;
            }
        }
        return nr;
    }

    public int getMaxHeight(){
        for(int j=0; j<ROW; j++){
            for(int i=COL; i>0; i--){
                if ( gameField[i][j].filled){
                    return ROW-j;
                }
            }
        }
        return 0;
    }

    public int getNrOfBlocks(){
        int nr=0;
        for(int j=ROW; j>0; j--){
            for(int i=COL; i>0; i--){
                if ( gameField[i][j].filled){
                    nr++;
                }
            }
        }
        return nr;
    }

    public double checkForSequences(){
        double out=0;
        for(int j=ROW; j>0; j--){
            int seq=0;
            for(int i=COL; i>0; i--){
                if ( gameField[i][j].filled){
                    seq++;
                }else{
                    out+= Math.pow(2,seq)-1;
                }
            }
        }
        return out;
    }

    public boolean placeBlock(Block s){
        for(int i=0; i<s.shape.length; i++){
            for(int j=0; j<s.shape.length; j++){
                if(s.shape[i][j].filled)
                    try{
                        if (gameField[i+s.x][j+s.y].filled)
                            return false;
                        else{
                            gameField[i+s.x][j+s.y].filled=true;
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
             if(s.shape[i][j].filled)
                gameField[i+s.x][j+s.y].filled=false;
          }
       }
    }*/


    public Object clone(){
        GameField out =new GameField(true);
        out.gameField= new Square[COL+2][ROW+2];
        for(int j=ROW+1; j>=0; j--){
            for(int i=COL+1; i>=0; i--){
                out.gameField[i][j]=new Square(10,gameField[i][j].filled);
            }
        }

        return out;
    }

}
