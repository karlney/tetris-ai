package se.karney.tetris;

public class SquareBlock extends Block {


    protected SquareBlock(){
    }

    public SquareBlock(GameField gf,GameController gc, int blockNumber){
        size=2;
        shape = new Square[2][2];
        x=5;
        for (int i=0; i<2;i++)
            for (int j=0; j<2;j++)
                shape[i][j]=new Square(6,true);

        number=blockNumber;
        gameField=gf;
    }

    public SquareBlock(Block copy,GameField gf,int x, int y,int rot){  // Used For AI planning
        super(copy,gf,x,y,rot);
    }

    public void rotate(int dir){ // Rotation
    }

    public Square[][] rotateTry(){
        return shape;
    }

    public Block copy(){
        return (SquareBlock)this.clone();
    }

    public Object clone(){
        SquareBlock out=new SquareBlock();
        out.shape=(Square[][])shape.clone();
        out.x=x;
        out.y=y;
        out.size=size;
        out.number=number;
        out. gameField= gameField;
        return out;
    }

}