package karlney.tetris.core;

import karlney.tetris.core.TetrisPlayer;

public class MoveDownThread implements Runnable {

    public int delay;
    private Thread t = new Thread(this);
    private TetrisPlayer player;
    private boolean running=true;

    public MoveDownThread(TetrisPlayer player){
        this.player = player;
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
            player.moveDown();
        }
    }

    public void stop(){
        running=false;
    }

    public void speedUp(){
        setDelay(delay);
    }

}
