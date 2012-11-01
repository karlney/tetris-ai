package karlney.tetris.swing;

import karlney.tetris.core.Block;

import java.awt.*;

public class DrawableBlock extends Block{

    public final static Color c1=new Color(250,250,0), // Block colors
            c2=new Color(0,250,0),
            c3=new Color(0,250,250),
            c4=new Color(250,0,250),
            c5=new Color(250,0,0);

    public void draw(Graphics g){
        for(int j=0; j<getSize(); j++){
            for(int i=0; i<getSize(); i++){
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

}
