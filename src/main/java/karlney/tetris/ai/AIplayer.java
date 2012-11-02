//package karlney.tetris.ai;
//
//import karlney.tetris.swing.SwingGame;
//import karlney.tetris.core.*;
//
//public class AIplayer extends TetrisPlayer implements Runnable{
//
//    private int desiredX,desiredY,desiredHeading,heading;
//
//    private Thread AIt;
//
//    private Piece copyCurrentPiece;
//
//    public AIplayer(SwingGame gc){
//        super(gc);
//        AIt= new Thread(this);
//    }
//
//    public void newGame(){
//        super.newGame();
//        AIt.start();
//        t.setDelay(1000000);
//    }
//
//
//    /*
//            This method is called every time a new block
//            is entering in the game and
//
//            alla currentBlock => testBlock !!!!
//
//        */
//    public PieceBase findBestPlacement(){
//
//        double bestU=-10000;
//        int y=0;
//
//        PieceBase tb,best=new PieceBase();
//
//
//        for (int h=0; h<4; h++){
//            for (int x=0; x< board.DEFAULT_COLS +1; x++){
//                tb = currentPiece.getCopy();
//                tb.overrideValues(x, y, h, board);
//                tb.stepDownAFAP();
//
//                for (int k=-1; k<=1; k++) {
//                    tb.x+=k;
//                    double utility=evalMove(tb);
//                    if (bestU<utility){
//                        bestU=utility;
//                        best=tb.getCopy();
//                    }
//                    tb.x-=k;
//                }
//            }
//        }
//
//        return best;
//    }
//
//
//
//    public void plotMove(Board gf,PieceBase b){
//
//        System.out.println("R="+gf.getFullRows());
//        System.out.println("H="+gf.getNrHoles());
//        System.out.println("L="+gf.getMaxHeight());
//        //System.out.println("F="+gf.getNearlyFullRows());
//        double u=calcUtility(b);
//        System.out.println("Utility="+u);
//        PieceBase bk= currentPiece.getCopy();
//        currentPiece =b;
//        try{
//            Thread.sleep(500);
//        }
//        catch(Exception e){}
//        currentPiece =bk;
//
//    }
//
//
//    public double evalMove(PieceBase b){
//        if ( !board.allowedPlacement(b)) {
//            return -10000;
//        }
//        double u=calcUtility(b);
//        return u;
//    }
//
//
//    public double calcUtility(PieceBase b){
//        board.createBackup();
//        board.placeBlock(b);
//        int R= board.checkFullRow();
//        double H= board.getNrHoles();
//
//        //   double F=gf.getNearlyFullRows();
//        board.retriveBackup();
//        return 200*R -200*H-5*Math.pow(board.DEFAULT_ROWS -b.y,2) ;
//    }
//
//
//    public Board createTestArena(){
//        Board gf=new Board();
//        for (int i=1; i<=gf.DEFAULT_COLS; i++){
//            int ystart= gf.DEFAULT_ROWS -(int)Math.max(0,Math.round(4* SwingGame.generator.nextDouble()));
//            for (int j=ystart;j<=gf.DEFAULT_ROWS; j++){
//                if (SwingGame.generator.nextDouble()<0.79)
//                    gf.addSquare(i,j,new Square(10,true));
//            }
//        }
//
//        return gf;
//    }
//
//    public void run(){
//
//        try {
//            while	(true){
//                //gameField=createTestArena();
//                Thread.sleep(50);
//                PieceBase bestPiece = findBestPlacement();
//                board.placePieceOnBoard(bestPiece);
//                currentPiece = nextPiece;
//                nextPiece =gc.generateNextBlock(board);
//            }
//        }
//        catch(InterruptedException	e)	{}
//    }
//
//}