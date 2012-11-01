package karlney.tetris.core;

public class  TetrisPlayer implements Runnable{

    public final Board board;
    public final PieceGenerator generator;
    private final Thread t = new Thread(this);

    public Piece currentPiece;
    public Piece nextPiece;

    public int score=0;
    public int rowCounter = 0;

    public int delay;
    public int level;
    private boolean running;

    public TetrisPlayer(Board board, PieceGenerator generator, int level) {
        this.board = board;
        this.generator = generator;
        this.level = level;
        score=0;
        rowCounter=0;
        currentPiece =generator.getNextBlock(board);
        nextPiece =generator.getNextBlock(board);
        running = false;
    }

    public void start(int delay){
        if (!running){
            running = true;
            this.delay = delay;
            t.start();
        } else {
            throw new IllegalStateException("Thread already running, can't start twice");
        }
    }

    public void stop(){
        running=false;
        this.delay=0;
        t.interrupt();
    }

    public void	moveDown(){
        boolean pieceIsLanded = currentPiece.moveDown();
        if (pieceIsLanded){
            newBlock(nextPiece);
        }
    }

    public void setDelay(int delay){
        this.delay = delay;
        t.interrupt();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /*
    actualLevel   free-fall   instant-drop
                points       points
===========   =========   ============
     1             6           24
     2             9           27
     3            12           30
     4            15           33
     5            18           36
     6            21           39
     7            24           42
     8            27           45
     9            30           48
    10            33           51
     */

    public void updatePlaceScore(int removedRows){
        //TODO add score for placing piece

        //TODO add score for removing rows
        if	(removedRows==1)
            score=score+(level+1)*50;
        if	(removedRows==2)
            score=score+(level+1)*150;
        if	(removedRows==3)
            score=score+(level+1)*300;
        if	(removedRows==4)
            score=score+(level+1)*1000;

        rowCounter+=removedRows;
    }

    private void updateFallScore(int fallDownRows) {
       score=score+(level+1)*5;
    }

    public void	newBlock(Piece piece){
        int rows= board.checkRemoved(piece);
        if	(rows==Board.UNABLE_TO_PLACE_PIECE){
            stop();
        }
        else{
            updatePlaceScore(rows);
            currentPiece = nextPiece;
            nextPiece    = generator.getNextBlock(board);
        }
    }

    public void processInput(TetrisInput input){
        if(input == TetrisInput.ROTATE)
            currentPiece.rotateIfPossible();

        if(input == TetrisInput.DOWN)
            t.interrupt();

        if(input == TetrisInput.LEFT)
            currentPiece.moveSideWays(MoveDirection.LEFT);

        if(input == TetrisInput.RIGHT)
            currentPiece.moveSideWays(MoveDirection.RIGHT);

        if(input == TetrisInput.DROP ){
            int fallDownRows = currentPiece.fallDown();
            updateFallScore(fallDownRows);
            setDelay(10);
        }
    }


    public void run(){
        while (running){
            try {
                Thread.sleep(delay);
            }catch(InterruptedException e) {}
            moveDown();
        }
    }

}