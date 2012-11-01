package se.karney.tetris;

import java.awt.*;

public class Square{

    public static final Color c1=new Color(250,250,0),//Gul
            c2=new Color(0,200,0), //Gr�n
            c3=new Color(250,150,150), //Turkos
            c4=new Color(0,150,220), // Rosaish
            c5=new Color(250,0,0), //R�d
            c6=new Color(250,250,250), //Vitt
            c7=new Color(0,120,90), //M�rkgr�n
            c8=new Color(100,0,100), // Lila
            c9=new Color(0,250,250);

    public boolean filled;
    private Color[] f;

    public Square(Square s)  {
        filled=s.filled;
        f=s.f;
    }

    public Square(int nr, boolean draw){
        f=color(nr);
        filled=draw;
    }

    public void draw(Graphics g,int x, int y){
        if(filled==true){
            g.setColor(f[0]);
            g.fillRect(x,y,20,20);
            g.setColor(f[1]);
            g.fillRect(x+3,y+3,14,14);
        }
    }

    public Color[] color(int nr){
        Color[] cOut= new Color[2];
        if(nr==0) {
            cOut[0]=c7; //Ram
            cOut[1]=c2; //Fyllning
        }
        if(nr==1) {
            cOut[0]=c7;
            cOut[1]=c3;
        }
        if(nr==2) {
            cOut[0]=c7;
            cOut[1]=c6;
        }
        if(nr==3) {
            cOut[0]=c7;
            cOut[1]=c1;
        }
        if(nr==4) {
            cOut[0]=c7;
            cOut[1]=c9;
        }
        if(nr==5) {
            cOut[0]=c7;
            cOut[1]=c5;
        }
        if(nr==6){
            cOut[0]=c7;
            cOut[1]=c8;
        }
        if(nr==10) { //RAM
            cOut[0]=c4;
            cOut[1]=c4;
        }
        return cOut;
    }
}
