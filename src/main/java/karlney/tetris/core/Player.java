package karlney.tetris.core;

public class Player implements Runnable{

    private static final int FALLING_DELAY = 3;

    public final Board board;
    public final PieceGenerator generator;
    private final Thread t = new Thread(this);

    public Piece currentPiece;
    public Piece nextPiece;

    public int score=0;
    public int lines = 0;
    public int freeFallIterations = 0;

    public int delay;
    public int level;
    private boolean running;

    public Player(Board board, PieceGenerator generator, int level) {
        this.board = board;
        this.generator = generator;
        this.level = level;
        score =0;
        lines =0;
        freeFallIterations =0;
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
            newBlock();
            if (!board.allowedPlacement(currentPiece)){
                stop();
            }
        }
    }

    public void setDelay(int delay){
        this.delay = delay;
        t.interrupt();
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void updatePlaceScore(int removedRows){
        score+= ((21+(3*level))-freeFallIterations+1);

        if	(removedRows==1)
            score=score+(level)*50;
        if	(removedRows==2)
            score=score+(level)*150;
        if	(removedRows==3)
            score=score+(level)*300;
        if	(removedRows==4)
            score=score+(level)*1000;

        lines +=removedRows;
    }

    private void newBlock(){
        int rows= board.placePieceOnBoard(currentPiece);
        updatePlaceScore(rows);
        currentPiece = nextPiece;
        nextPiece    = generator.getNextBlock(board);
        freeFallIterations = 0;
    }

    public void processInput(PlayerInput input){
        if(input == PlayerInput.ROTATE)
            currentPiece.rotateIfPossible();

        if(input == PlayerInput.DOWN)
            t.interrupt();

        if(input == PlayerInput.LEFT || input == PlayerInput.RIGHT)
            currentPiece.moveSideWays(input);

        if(input == PlayerInput.DROP ){
            currentPiece.fallDown();
            t.interrupt();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void run(){
        while (running){
            try {
                if (currentPiece.isFalling()){
                    Thread.sleep(FALLING_DELAY);
                }else{
                    freeFallIterations++;
                    Thread.sleep(delay);
                }
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

    public int getScore() {
        return score;
    }

    public int getLines() {
        return lines;
    }
}