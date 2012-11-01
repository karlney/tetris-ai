//package karlney.tetris.swing;
//
//import karlney.tetris.core.Square;
//
//import java.awt.*;
//
//public class DrawableSquare extends Square{
//
//    public static final Color
//            c1=new Color(250,250,0),//Yellow
//            c2=new Color(0,200,0), //Green
//            c3=new Color(250,150,150), //Teal
//            c4=new Color(0,150,220), // Pink
//            c5=new Color(250,0,0), //Red
//            c6=new Color(250,250,250), //White
//            c7=new Color(0,120,90), //Dark green
//            c8=new Color(100,0,100), // Purple
//            c9=new Color(0,250,250); //Blue
//
//    private int nr;
//
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public <T extends Square> T[][] getClone(int size){
//        DrawableSquare[][] out = new DrawableSquare[size][size];
//        return (T[][])out;
//    }
//
//    private Color[] color;
//
//    public DrawableSquare(int nr){
//        color =color(nr);
//    }
//
//    public void draw(Graphics g,int x, int y){
//        if(isFilled()){
//            g.setColor(color[0]);
//            g.fillRect(x,y,20,20);
//            g.setColor(color[1]);
//            g.fillRect(x+3,y+3,14,14);
//        }
//    }
//
//    public Color[] color(int nr){
//        Color[] cOut= new Color[2];
//        if(nr==0) {
//            cOut[0]=c7; //Frame
//            cOut[1]=c2; //Internal
//        }
//        if(nr==1) {
//            cOut[0]=c7;
//            cOut[1]=c3;
//        }
//        if(nr==2) {
//            cOut[0]=c7;
//            cOut[1]=c6;
//        }
//        if(nr==3) {
//            cOut[0]=c7;
//            cOut[1]=c1;
//        }
//        if(nr==4) {
//            cOut[0]=c7;
//            cOut[1]=c9;
//        }
//        if(nr==5) {
//            cOut[0]=c7;
//            cOut[1]=c5;
//        }
//        if(nr==6){
//            cOut[0]=c7;
//            cOut[1]=c8;
//        }
//        if(nr==10) { //Game frame
//            cOut[0]=c4;
//            cOut[1]=c4;
//        }
//        return cOut;
//    }
//
//    public int getNr() {
//        return nr;
//    }
//}
