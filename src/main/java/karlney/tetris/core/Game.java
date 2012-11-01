package karlney.tetris.core;

import java.util.Random;

/**
 * This is the Game class. It is responsible of generating new blocks and handling block move down
 */
public class Game {

    private final static int NUMBER_OF_BLOCKS = 7;
    private final static int START_LEVEL = 4;
    private static final int STRAIGHT_BLOCK_NR = 5;
    private static final int MAX_LEVEL = 8; //level 9 is only for AI training

    private static final int[] LEVEL_DELAYS = {1000,800,600,450,350,250,180,120,80,0};

    //Random generator for next blocks
    private static Random generator = new Random();

    //False when the game is lost/stopped
    private boolean	gameRunning;

    //True when the game is paused
    private boolean paused=false;

    //Current level
    private int level;

    public Game(int level) {
        this.level=level;
        paused=false;
        gameRunning=true;
    }

    public Piece generateNextBlock(Board board){
        if (!gameRunning){
            throw new IllegalStateException("The game is stopped.");
        }

        int nr=generator.nextInt(NUMBER_OF_BLOCKS);

        if	(nr< STRAIGHT_BLOCK_NR)
            return new BasePiece(board,nr);
        else if (nr==5)
            return new IPiece(board,nr);
        else
            return new OPiece(board,nr);
    }

    public int getDelay(){
        if	(!paused)	{
            return LEVEL_DELAYS[level];
        }
        return Integer.MAX_VALUE;
    }

    public void increaseLevel(){
        level++;
        if	(level>= MAX_LEVEL)	level= MAX_LEVEL;
    }

    public void decreaseLevel(){
        level--;
        if	(level<0) level=0;
    }

    public void togglePause(){
        paused = !paused;
    }

    public void stop(){
        gameRunning=false;
    }

}
