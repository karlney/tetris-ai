package karlney.tetris.core;

public class Block<T extends Square>{

   /*
    private static boolean[][][] BLOCK_SHAPES ={
            {{false,true,false},  {true,true,false}, {false,true,false}},  // .:.
            {{true,false,false},  {true,true,false}, {false,true,false}},  // .:´
            {{false,true,false},  {true,true,false}, {true,false,false}},  // ´:.
            {{false,false,false}, {true,true,true},  {true,false,false}},  // ``:
            {{true,true},{true,true}},                                     // ::

            {{false,false,false}, {true,true,true},  {false,false,true}}}; // ..:
     */

    private int number;


    protected static final int START_POS_X = 4;
    protected static final int START_POS_Y = -1;


    private T[][] shape;
    private int size;

    private boolean falling;
    private boolean firstTry;
    private int x;
    private int y;

    private GameField gameField;


    protected Block(){
    }

    public Block(GameField gameField, T[][] shape){
        this.gameField = gameField;
        this.shape = shape;
        this.size = shape.length;
        x= START_POS_X;
        y= START_POS_Y;
        falling=false;
        firstTry=true;
    }

    /**
     * If possible rotate this block
     */
    public void rotate(){
        if(gameField.checkMove(x,y,getRotatedShape()))
            shape= getRotatedShape();
    }


    public void rotateNoCheck(){
        shape= getRotatedShape();
    }

    /**
     * @return the shape matrix for this block rotated 90 degrees
     */
    public T[][] getRotatedShape(){
        T[][] shapeClone= shape[0][0].getClone(size);
        for(int x=0; x<size; x++){
            for(int y=0; y<size; y++){
                shapeClone[x][y]=shape[y][2-x];
            }
        }
        return shapeClone;
    }



    public boolean stepDown(){
        boolean out=gameField.checkMove(x,y+1,shape);
        if (out){
            y++;
        }
        return out;
    }

    public void  stepDownAFAP(){
        while (stepDown()){
        }
    }


    public synchronized void moveDown(TetrisPlayer plr){
        if (gameField.checkMove(x,y+1,shape)){
            y=y+1;
            //TODO score should NOT be calculated by the block!
            /* if (falling)
            plr.score+=(plr.gc.level+1)*2;
            */
        }
        else {
            if (firstTry){
                firstTry=!firstTry;
            }
            else{
                firstTry=true;
                plr.newBlock(this);
            }
        }
    }

    public synchronized void moveSideWays(MoveDirection dir){
        if (falling)
            return;
        if (dir==MoveDirection.LEFT){
            if (gameField.checkMove(x - 1, y, shape)){
                x=x-1;
            }
        }
        if (dir==MoveDirection.RIGHT){
            if (gameField.checkMove(x + 1, y, shape)){
                x=x+1;
            }
        }
        else {}
    }

    public void fallDown(){
        falling=true;
    }


    /**
     * This method can be used to move this block to a new arbitrary position and rotation and to a new GameField
     *
     * This is very useful for AI planning.
     *
     * NOTE this method does NOT check if the final position is valid!
     *
     * @param x the new x position
     * @param y the new y position
     * @param rotation the new rotation (relative to the current rotation), 0 means current rotation, 1 means to rotate one time, 2 two times etc.
     * @param gameField the new game field that this block is placed in
     */
    public void overrideValues(int x, int y, int rotation, GameField gameField){
        this.x=x;
        this.y=y;
        for (int i=0; i<rotation; i++){
            rotateNoCheck();
        }
        this.gameField=gameField;
    }


    public Block copy(){
        return(Block)this.clone();
    }


    public Object clone(){
        Block out=new Block();
        out.shape=(T[][])shape.clone();
        out.x=x;
        out.y=y;
        out.size=size;
        out. gameField= gameField;
        return out;
    }

    public String toString(){
        String s="x="+x+" y="+y+"\n";
        return s;
    }

    public int getSize() {
        return size;
    }

    /*
    public boolean isFilled(int i, int j) {
        return shape[i][j].isFilled();
    }
    */
}
