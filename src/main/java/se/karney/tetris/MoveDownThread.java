package se.karney.tetris;

public class MoveDownThread implements Runnable{

    public int delay;

    Thread t = new Thread(this);
    TetrisPlayer gc;
    boolean running=true;

    public MoveDownThread(TetrisPlayer g){
        gc=g;
        t.start();
    }

    public void setDelay(int d){
        delay=d;
        t.interrupt();

    }

    public void run(){
        while (running){
            try {
                Thread.sleep(delay);
            }catch(InterruptedException e) {}
            gc.moveDown();
        }
    }
    public void stop(){
        running=false;
    }

    public void speedUp(){
        setDelay(delay);
    }

}
