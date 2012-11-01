package karlney.tetris.core;

public class SquareBlock extends Block {


    public void rotate(int dir){ // Rotation
    }

    public Square[][] getRotatedShape(){
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