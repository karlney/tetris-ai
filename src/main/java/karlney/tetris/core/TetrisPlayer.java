package karlney.tetris.core;

import karlney.tetris.GameController;

import java.awt.event.KeyEvent;

public class  TetrisPlayer{

    public Board board;
    public GameController gc;
    public MoveDownThread t;

    public BasePiece currentPiece, nextPiece;

    public int    score=0,
            rowCounter = 0;


    public TetrisPlayer(GameController gc){
        this.gc=gc;
    }

    public void	newGame(){
        score=0;
        rowCounter=0;
        board =new Board();

        nextPiece =gc.generateNextBlock(board);
        currentPiece =gc.generateNextBlock(board);
        if	(t!=null) {
            t.stop();
        }
        t = new MoveDownThread(this);	//Nedflyttnings Tr�d
        t.setDelay(gc.getDelay());
    }


    public void	moveDown(){
        currentPiece.moveDown(this);
    }

    public void	setDelay(){
        if	(!gc.paused)	{
            t.setDelay(gc.getDelay());
        }
    }

    public void	updateScores(int removedRows){

        if	(removedRows==1)
            score=score+(gc.level+1)*40;
        if	(removedRows==2)
            score=score+(gc.level+1)*100;
        if	(removedRows==3)
            score=score+(gc.level+1)*300;
        if	(removedRows==4)
            score=score+(gc.level+1)*1200;

        rowCounter+=removedRows;
    }


    public void	newBlock(BasePiece s){
        int rows= board.checkRemoved(s);
        if	(rows==-1){
            t.stop();
            //Highscore.updateScore(score);     SE till att fixa!!!!!!!!!!!
            gc.gameOver();
        }
        else{
            updateScores(rows);
            currentPiece = nextPiece;
            nextPiece =gc.generateNextBlock(board);
            t.setDelay(gc.getDelay());
        }
    }



    //TODO this should be refactored out    Aw
    public void processKeyInput(KeyEvent e2){
        if((e2.getKeyCode() == KeyEvent.VK_UP || e2.getKeyCode()	==	KeyEvent.VK_W))
            currentPiece.rotateIfPossible();

        if((e2.getKeyCode() == KeyEvent.VK_DOWN || e2.getKeyCode() == KeyEvent.VK_S))
            t.speedUp();


        if((e2.getKeyCode() == KeyEvent.VK_LEFT || e2.getKeyCode() == KeyEvent.VK_A))
            currentPiece.moveSideWays(BasePiece.LEFT);

        if((e2.getKeyCode() == KeyEvent.VK_RIGHT || e2.getKeyCode()	==	KeyEvent.VK_D))
            currentPiece.moveSideWays(BasePiece.RIGHT);

        if(e2.getKeyCode() == KeyEvent.VK_SPACE ){
            currentPiece.fallDown();
            t.setDelay(10);
        }
    }
}