package karlney.tetris.core;

public class Player implements Runnable{

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

    public Player(Board board, PieceGenerator generator, int level) {
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
        score=score+(level+1)*1;

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
        try{
            int rows= board.placePieceOnBoard(piece);
            updatePlaceScore(rows);
            currentPiece = nextPiece;
            nextPiece    = generator.getNextBlock(board);
        }catch (UnableToPlacePieceException e) {
            //TODO
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void processInput(PlayerInput input){
        if(input == PlayerInput.ROTATE)
            currentPiece.rotateIfPossible();

        if(input == PlayerInput.DOWN)
            t.interrupt();

        if(input == PlayerInput.LEFT || input == PlayerInput.RIGHT)
            currentPiece.moveSideWays(input);

        if(input == PlayerInput.DROP ){
            int fallDownRows = currentPiece.fallDown();
            updateFallScore(fallDownRows);
            setDelay(10);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void run(){
        while (running){
            try {
                Thread.sleep(delay);
            }catch(InterruptedException e) {}
            moveDown();
        }
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Board getBoard() {
        return board;
    }
}