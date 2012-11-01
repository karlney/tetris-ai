package karlney.tetris.core;

import java.util.List;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-01
 * Time: 20:36
 *
 * @author karl.neyvaldt
 */
public class TetrisGame {

    // http://colinfahey.com/tetris/tetris.html
    private static final int MAX_LEVEL = 10; //level 10 is only for AI training
    private static final int[] LEVEL_DELAYS = {500,450,400,350,300,250,200,150,100,50,0};

    //True when the game is started (False only if the game is created but not yet started)
    private boolean gameStarted;

    //True when the game is paused
    private boolean paused;

    //Current level
    private int level;

    //The players
    private List<TetrisPlayer> players;

    public TetrisGame(int startLevel, List<TetrisPlayer> players) {
        this.level = startLevel;
        this.players = players;
        this.gameStarted = false;
    }

    public void start(){
        if (!gameStarted){
            gameStarted = true;
            for (TetrisPlayer pl:players) {
                pl.start(getDelay());
            }
        }
    }

    public void stop(){
        for (TetrisPlayer pl:players) {
            pl.stop();
        }
    }

    public void increaseLevel(){
        level++;
        if	(level>= MAX_LEVEL)	level= MAX_LEVEL;
        for (TetrisPlayer pl:players) {
            pl.setDelay(getDelay());
        }
    }

    public void decreaseLevel(){
        level--;
        if	(level<0) level=0;
        for (TetrisPlayer pl:players) {
            pl.setDelay(getDelay());
        }
    }

    public void togglePause(){
        paused = !paused;
        for (TetrisPlayer pl:players) {
            pl.setDelay(getDelay());
        }
    }

    private int getDelay(){
        if	(!paused)	{
            return LEVEL_DELAYS[level];
        }
        return Integer.MAX_VALUE;
    }

    public boolean isPaused() {
        return paused;
    }

    public List<TetrisPlayer> getPlayers() {
        return players;
    }

    public boolean hasEnded() {
        boolean allStopped = true;
        for (TetrisPlayer pl:players){
            allStopped = allStopped && !pl.isRunning();
        }
        return allStopped;
    }
}
