package karlney.tetris.ai;

import karlney.tetris.core.Piece;
import karlney.tetris.core.PlayerInput;

import java.util.List;

/**
 * This class executes the AI action loop, that is: plan the destination of the current piece, plan the path to get there, move according to the path
 *
 * The AI action loop is parametrised with different delays, PiecePlacer's and PathFinder's to make it behave differently.
 *
 * Date: 2012-11-03
 * Time: 09:36
 *
 * @see PiecePlacer
 * @see PathFinder
 *
 * @author karl.neyvaldt
 */
public class AIThread implements Runnable {

    private Thread t = new Thread(this);
    private AIPlayer player;
    private PiecePlacer placer;
    private PathFinder pathFinder;
    private int delay;

    public AIThread(AIPlayer player,
                    int delay,
                    PiecePlacer placer,
                    PathFinder pathFinder) {
        this.player = player;
        this.delay = delay;
        this.placer = placer;
        this.pathFinder = pathFinder;
    }

    public void run(){

        try {
            while (true){
                //Sleep to avoid AI 'cheating' of doing things too quickly
                if (delay>0){
                    Thread.sleep(delay);
                }

                //If destination is unknown then find it
                if (!player.hasDestination()){
                    //Find the best placement of the piece according the placer - given the board, the current and the next piece
                    Piece p = placer.bestPlacement(player.getBoard(), player.getCurrentPiece(), player.getNextPiece());
                    //Set the current destination for the player
                    player.setDestination(p);
                }

                //Calculate the path (do this in every iteration to account for 'slow AI reaction') - the piece may have moved down since last time
                List<PlayerInput> path = pathFinder.findPath(player.getBoard(),player.getCurrentPiece(),player.getDestination());

                //Execute the first action in the path
                if (path.size()>0){
                    player.processInput(path.get(0));
                }
            }
        }
        catch(InterruptedException	e)	{}
    }

    public void start() {
        t.start();
    }

    public void stop(){
        t.interrupt();
    }

    /*
    public Piece findBestPlacement(piecePlacer evaluator, Board board, Piece currentPiece, Piece nextPiece){

        double bestU=-10000;
        int y=0;

        Piece tb,best=new Piece();


        for (int h=0; h<4; h++){
            for (int x=0; x< board.DEFAULT_COLS +1; x++){
                tb = currentPiece.getCopy();
                tb.overrideValues(x, y, h, board);
                tb.stepDownAFAP();

                for (int k=-1; k<=1; k++) {
                    tb.x+=k;
                    double utility=evalMove(tb);
                    if (bestU<utility){
                        bestU=utility;
                        best=tb.getCopy();
                    }
                    tb.x-=k;
                }
            }
        }

        return best;
    }

    /*
    public double evalMove(Piece b){
        if ( !board.allowedPlacement(b)) {
            return -10000;
        }
        double u=calcUtility(b);
        return u;
    }


    public double calcUtility(Piece b){
        board.createBackup();
        board.placeBlock(b);
        int R= board.removeFullRows();
        double H= board.getNrHoles();

        //   double F=gf.getNearlyFullRows();
        board.retriveBackup();
        return 200*R -200*H-5*Math.pow(board.DEFAULT_ROWS -b.y,2) ;
    }


    */
}
