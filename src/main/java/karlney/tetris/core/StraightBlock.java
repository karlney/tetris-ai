package karlney.tetris.core;

public class StraightBlock extends Block {

    private boolean tilted=true;

    protected StraightBlock(){
    }

    public StraightBlock(GameField gf, int blockNumber){
        size=4;
        y=-2;
        shape = new Square[4][4];

        for (int i=0; i<4;i++)
            for (int j=0; j<4;j++)
                shape[i][j]=new Square(5,false);

        for (int i=0; i<4;i++)
            shape[1][i].filled=true;

        number=blockNumber;
        gameField=gf;
    }

    public StraightBlock(Block copy,GameField gf,int x, int y,int rot){  // Used For AI planning
        super(copy,gf,x,y,rot);
    }


    public void rotate(int dir){ // Rotation
        if(checkMove(x,y, getRotatedShape())) {
            shape= getRotatedShape();
            tilted=!tilted;
        }
    }


    public Square[][] getRotatedShape(){ //Testar om rotation �r m�jlig
        Square[][] sh= new Square[4][4];

        for (int i=0; i<4;i++)
            for (int j=0; j<4;j++)
                sh[i][j]=new Square(5,false);

        if (tilted){
            for (int i=0; i<4;i++)
                sh[i][1].filled=true;
        }
        else{
            for (int i=0; i<4;i++)
                sh[1][i].filled=true;
        }

        return sh;
    }

    public Block copy(){
        return(StraightBlock)this.clone();
    }

    public Object clone(){
        StraightBlock out=new StraightBlock();
        out.shape=(Square[][])shape.clone();
        out.x=x;
        out.y=y;
        out.size=size;
        out.number=number;
        out.tilted=tilted;
        out. gameField= gameField;
        return out;
    }


}