package karlney.tetris.ai;

import karlney.tetris.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIPlayer extends Player {

    private static Logger log = LoggerFactory.getLogger(Player.class);

    private AIThread aiThread;

    private Piece destinationPiece = null;

    public AIPlayer(Board board, PieceGenerator generator, int level, AIThread aiThread) {
        super(board, generator, level);
        aiThread.setPlayer(this);
        this.aiThread = aiThread;
    }

    public void start(int delay){
        super.start(delay);
        aiThread.start();
    }

    public void stop(){
        super.stop();
        aiThread.stop();
    }

    public synchronized void processInput(PlayerInput input){
        super.processInput(input);
        if (input == PlayerInput.INSTANT_MOVE){
            currentPiece = destinationPiece;
            destinationPiece = null;
            try {
                commitCurrentPieceToBoard();
            } catch (UnableToPlacePieceException e) {
                log.error("Unable to place piece.",e);
                stop();
            }
        }
    }


    public void clearDestination(){
        destinationPiece = null;
    }

    public void setDestination(Piece destinationPiece) {
        this.destinationPiece = destinationPiece;
    }

    public Piece getDestination() {
        return destinationPiece;
    }

    public boolean hasDestination() {
        return destinationPiece!=null;
    }

}