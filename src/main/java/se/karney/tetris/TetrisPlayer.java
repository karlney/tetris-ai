package se.karney.tetris;

import java.awt.event.KeyEvent;

public class  TetrisPlayer{

    public GameField gameField;
    public GameController gc;
    public MoveDownThread t;

    public Block  currentBlock,nextBlock;

    public int    score=0,
            rowCounter = 0;


    public TetrisPlayer(GameController gc){
        this.gc=gc;
    }

    public void	newGame(){
        score=0;
        rowCounter=0;
        gameField=new GameField();

        nextBlock=gc.generateNextBlock(gameField);
        currentBlock=gc.generateNextBlock(gameField);
        if	(t!=null) {
            t.stop();
        }
        t = new MoveDownThread(this);	//Nedflyttnings Tr�d
        t.setDelay(gc.getDelay());
    }


    public void	moveDown(){
        currentBlock.moveDown(this);
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


    public void	newBlock(Block	s){
        int rows=gameField.checkRemoved(s);
        if	(rows==-1){
            t.stop();
            //Highscore.updateScore(score);     SE till att fixa!!!!!!!!!!!
            gc.gameOver();
        }
        else{
            updateScores(rows);
            currentBlock=nextBlock;
            nextBlock=gc.generateNextBlock(gameField);
            t.setDelay(gc.getDelay());
        }
    }




    public void processKeyInput(KeyEvent e2){
        if((e2.getKeyCode() == KeyEvent.VK_UP || e2.getKeyCode()	==	KeyEvent.VK_W))
            currentBlock.rotate();

        if((e2.getKeyCode() == KeyEvent.VK_DOWN || e2.getKeyCode() == KeyEvent.VK_S))
            t.speedUp();


        if((e2.getKeyCode() == KeyEvent.VK_LEFT || e2.getKeyCode() == KeyEvent.VK_A))
            currentBlock.moveSideWays(Block.LEFT);

        if((e2.getKeyCode() == KeyEvent.VK_RIGHT || e2.getKeyCode()	==	KeyEvent.VK_D))
            currentBlock.moveSideWays(Block.RIGHT);

        if(e2.getKeyCode() == KeyEvent.VK_SPACE ){
            currentBlock.fallDown();
            t.setDelay(10);
        }
    }
}