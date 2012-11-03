package karlney.tetris.ai;

import karlney.tetris.core.*;

public abstract class AbstractAIPlayer extends Player {

    private AIThread aiThread;

    private Piece destinationPiece = null;

    public AbstractAIPlayer(Board board, PieceGenerator generator, int level) {
        super(board, generator, level);
    }

    public void start(int delay){
        super.start(delay);
        aiThread.start();
    }

    public void stop(){
        super.stop();
        aiThread.stop();
    }

    public void processInput(PlayerInput input){
        super.processInput(input);
        if (input == PlayerInput.INSTANT_MOVE){
            currentPiece = destinationPiece;
            commitCurrentPieceToBoard();
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