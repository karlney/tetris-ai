package karlney.tetris.ai;

import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceType;
import karlney.tetris.core.PlayerInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger log = LoggerFactory.getLogger(AIThread.class);

    private Thread t = new Thread(this);
    private AIPlayer player;
    private PiecePlacer placer;
    private PathFinder pathFinder;
    private int delay;

    public AIThread(int delay,
                    PiecePlacer placer,
                    PathFinder pathFinder) {
        this.delay = delay;
        this.placer = placer;
        this.pathFinder = pathFinder;
    }

    public void run(){

        try {
            while (player.isRunning()){

                //If destination is unknown then find it
                if (!player.hasDestination()){
                    //Find the best placement of the piece according the placer - given the board, the current and the next piece
                    Piece p = placer.bestPlacement(player.getBoard(), player.getCurrentPiece(), player.getNextPiece());
                    //Set the current destination for the player
                    player.setDestination(p);

                    if (player.getCurrentPiece().getType()== PieceType.I){
                        player.getCurrentPiece().getType();
                    }
                }

                //Calculate the path (do this in every iteration to account for 'slow AI reaction') - the piece may have moved down since last time
                List<PlayerInput> path = pathFinder.findPath(player.getBoard(),player.getCurrentPiece(),player.getDestination());

                //Sleep to avoid AI 'cheating' of doing things too quickly
                if (delay>0){
                    Thread.sleep(delay);
                }

                //Execute the first action in the path
                if (path.size()>0){
                    player.processInput(path.get(0));
                }
            }
        }catch (InterruptedException e){
            //Do nothing..
        }catch(Exception	e)	{
            log.error("AI thread crashed.", e);
        }
    }

    public void start() {
        t.start();
        log.info("AI Thread started.");
    }

    public void stop(){
        t.interrupt();
        log.info("AI Thread stopped.");
    }

    public void setPlayer(AIPlayer player) {
        this.player = player;
    }
}
