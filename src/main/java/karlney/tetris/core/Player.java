package karlney.tetris.core;

import java.util.Map;
import java.util.TreeMap;

public class Player implements Runnable{

    private static final int FALLING_DELAY = 3;

    private final Board board;
    private final PieceGenerator generator;
    private final Thread t = new Thread(this);

    protected Piece currentPiece;
    protected Piece nextPiece;

    private int score = 0;
    private int lines = 0;
    private int freeFallIterations = 0;
    private Map<PieceType,Integer> pieceStatistics;

    private int delay;
    private int level;
    private boolean running;

    public Player(Board board, PieceGenerator generator, int level) {
        this.board = board;
        this.generator = generator;
        this.level = level;
        score = 0;
        lines = 0;
        freeFallIterations = 0;
        currentPiece = generator.getNextBlock(board);
        nextPiece = generator.getNextBlock(board);
        running = false;
        pieceStatistics = new TreeMap<PieceType, Integer>();
        for (PieceType t: PieceType.values()){
            pieceStatistics.put(t,0);
        }
        updateDistribution(currentPiece.getSquare(0, 0).getType());
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
            commitCurrentPieceToBoard();
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

    protected void commitCurrentPieceToBoard(){
        int rows= board.placePieceOnBoard(currentPiece);
        updatePlaceScore(rows);
        currentPiece = nextPiece;
        nextPiece    = generator.getNextBlock(board);
        freeFallIterations = 0;
        updateDistribution(currentPiece.getSquare(0, 0).getType());
    }

    private void updateDistribution(PieceType type) {
        if (pieceStatistics.containsKey(type)){
            pieceStatistics.put(type,pieceStatistics.get(type)+1);
        }
    }

    public void processInput(PlayerInput input){
        switch (input){
            case ROTATE: currentPiece.rotateIfPossible(); break;
            case DOWN: t.interrupt(); break;
            case LEFT: currentPiece.moveSideWays(input); break;
            case RIGHT: currentPiece.moveSideWays(input); break;
            case DROP: currentPiece.fallDown(); t.interrupt(); break;
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

    public int getNumberOfPieces() {
        int out = 0;
        for (Integer i: pieceStatistics.values()){
            out+=i;
        }
        return out;
    }

}