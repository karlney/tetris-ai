package se.karney.tetris;

public class AIplayer extends TetrisPlayer implements Runnable{

    private int desiredX,desiredY,desiredHeading,heading;

    private Thread AIt;

    private Block copyCurrentBlock;

    public AIplayer(GameController gc){
        super(gc);
        AIt= new Thread(this);
    }

    public void newGame(){
        super.newGame();
        AIt.start();
        t.setDelay(1000000);
    }


    /*
            This method is called every time a new block
            is entering in the game and

            alla currentBlock => testBlock !!!!

        */
    public Block findBestPlacement(){

        double bestU=-10000;
        int y=0;

        Block tb,best=new Block();


        for (int h=0; h<4; h++){
            for (int x=0; x<gameField.COL+1; x++){
                tb =currentBlock.copy();
                tb.setXY(x,y,h,gameField);
                tb.stepDownAFAP();

                for (int k=-1; k<=1; k++) {
                    tb.x+=k;
                    double utility=evalMove(tb);
                    if (bestU<utility){
                        bestU=utility;
                        best=tb.copy();
                    }
                    tb.x-=k;
                }
            }
        }

        return best;
    }



    public void plotMove(GameField gf,Block b){

        System.out.println("R="+gf.getFullRows());
        System.out.println("H="+gf.getNrHoles());
        System.out.println("L="+gf.getMaxHeight());
        //System.out.println("F="+gf.getNearlyFullRows());
        double u=calcUtility(b);
        System.out.println("Utility="+u);
        Block bk=currentBlock.copy();
        currentBlock=b;
        try{
            Thread.sleep(500);
        }
        catch(Exception e){}
        currentBlock=bk;

    }


    public double evalMove(Block b){
        if ( !gameField.allowedPlacement(b)) {
            return -10000;
        }
        double u=calcUtility(b);
        return u;
    }


    public double calcUtility(Block b){
        gameField.createBackup();
        gameField.placeBlock(b);
        int R=gameField.checkFullRow();
        double H=gameField.getNrHoles();

        //   double F=gf.getNearlyFullRows();
        gameField.retriveBackup();
        return 200*R -200*H-5*Math.pow(gameField.ROW-b.y,2) ;
    }


    public GameField createTestArena(){
        GameField gf=new GameField();
        for (int i=1; i<=gf.COL; i++){
            int ystart= gf.ROW-(int)Math.max(0,Math.round(4* GameController.generator.nextDouble()));
            for (int j=ystart;j<=gf.ROW; j++){
                if (GameController.generator.nextDouble()<0.79)
                    gf.addSquare(i,j,new Square(10,true));
            }
        }

        return gf;
    }

    public void run(){

        try {
            while	(true){
                //gameField=createTestArena();
                Thread.sleep(50);
                Block bestBlock = findBestPlacement();
                gameField.checkRemoved(bestBlock);
                currentBlock=nextBlock;
                nextBlock=gc.generateNextBlock(gameField);
            }
        }
        catch(InterruptedException	e)	{}
    }

}