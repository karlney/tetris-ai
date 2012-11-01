package karlney.tetris.core;

import karlney.tetris.GameController;

import java.awt.*;

public class Block{

    private static boolean[][][] n ={{{false,true,false},{true,true,false},{false,true,false}}, // .:.
            {{true,false,false},{true,true,false},{false,true,false}}, //     .:´
            {{false,true,false},{true,true,false},{true,false,false}}, // ´:.
            {{false,false,false},{true,true,true},{true,false,false}}, //     ``:
            {{false,false,false},{true,true,true},{false,false,true}}}; // ..:

    public final static int LEFT=0,RIGHT=1;
    public final static Color c1=new Color(250,250,0), // Färger för de olika blocken
            c2=new Color(0,250,0),
            c3=new Color(0,250,250),
            c4=new Color(250,0,250),
            c5=new Color(250,0,0);


    public Square[][] shape;
    private boolean falling=false,firstTry=true;
    public int x=4,y=-1,
            size,
            number;

    GameField gameField;

    protected Block(){
    }


    public Block(GameField gf,GameController gc,int nr){ // Konstruktor
        size=3;
        shape = new Square[3][3];

        for (int i=0; i<3;i++)
            for (int j=0; j<3;j++)
                shape[i][j]=new Square(nr,n[nr][i][j]);

        gameField=gf;
        number=nr;
    }

    public void draw(Graphics g){ // Ritar ut
        for(int j=0; j<size; j++){
            for(int i=0; i<size; i++){
                shape[i][j].draw(g,x*20+20*i,y*20+20*j);
            }
        }
    }
    public void draw(Graphics g,int x,int y){
        for(int j=0; j<size; j++){
            for(int i=0; i<size; i++){
                shape[i][j].draw(g,x+20*i,y+20*j);
            }
        }
    }


    public void rotate(){ // Rotation
        if(checkMove(x,y,rotateTry()))
            shape=rotateTry();
    }

    public Square[][] rotateTry(){ //Testar om rotation är möjlig
        Square[][] shapeClone= new Square[3][3];
        for(int x=0; x<size; x++){
            for(int y=0; y<size; y++){
                shapeClone[x][y]=shape[y][2-x];
            }
        }
        return shapeClone;
    }


    public boolean checkMove(int x,int y,Square[][] shape){ //Testar om förflyttning är möjlig      
        for(int j=0; j<size; j++){
            for(int i=0; i<size; i++){
                try{
                    if(gameField.gameField[x+i][y+j].filled && shape[i][j].filled)
                        return false;
                }
                catch (Exception e) { }
            }
        }
        return true;
    }


    public boolean stepDown(){
        boolean out=checkMove(x,y+1,shape);
        if (out){
            y++;
        }
        return out;
    }

    public void  stepDownAFAP(){
        while (stepDown()){
        }
    }


    public synchronized void moveDown(TetrisPlayer plr){// Förflyttning neråt
        if (checkMove(x,y+1,shape)){
            y=y+1;
            if (falling)
                plr.score+=(plr.gc.level+1)*2;
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

    public synchronized void moveSideWays(int dir){ // Förflyttning i sidled
        if (falling)
            return;
        if (dir==LEFT){
            if (checkMove(x-1,y,shape)){
                x=x-1;
            }
        }
        if (dir==RIGHT){
            if (checkMove(x+1,y,shape)){
                x=x+1;
            }
        }
        else {}
    }

    public void fallDown(){
        falling=true;
    }

    public void setXY(int x,int y,int h,GameField gf){
        this.x=x;
        this.y=y;
        for (int i=0; i<h; i++){
            rotate();
        }
        gameField=gf;
    }


    public Block copy(){
        return(Block)this.clone();
    }


    public Object clone(){
        Block out=new Block();
        out.shape=(Square[][])shape.clone();
        out.x=x;
        out.y=y;
        out.size=size;
        out.number=number;
        out. gameField= gameField;
        return out;
    }

    public String toString(){
        String s="x="+x+" y="+y+"\n";
        return s;
    }

}
