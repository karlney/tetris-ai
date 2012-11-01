package karlney.tetris.swing;

import karlney.tetris.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

/**
 * This is the main Game class
 * It uses Java swing for drawing the game frame, menues etc.
 */
public class SwingGame extends JPanel	implements Runnable,KeyListener,ActionListener{

    //Size in pixels of graphical elements
    private final static int
            XSIZE=200+180,
            YSIZE=400+90,
            LOCATION_X = 200,
            LOCATION_Y = 100;

    private static final int DEFAULT_START_LEVEL = 5;
    private static final int ROWS = 20;
    private static final int COLS = 10;

    //Swing Graphics
    private JPanel obs=new	JPanel();
    private JFrame jf= new	JFrame("Tetris");
    private JMenuItem
            exitCmd,
            newGameCmd,
            pauseCmd,
            changeLevelCmd,
            showHighscoreCmd,
            nextLvlCmd,
            prevLvlCmd;


    public TetrisGame game;
    public int level = DEFAULT_START_LEVEL;
    public PieceGenerator generator = new PieceGenerator();

    //public AIplayer player= new AIplayer(this);

    public SwingGame(){
        initGraphics();
        newGame();
    }

    private void initGraphics() {
        jf.setResizable(false);
        jf.setSize(new Dimension(XSIZE,YSIZE));
        jf.setLocation(LOCATION_X,LOCATION_Y);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.addKeyListener(this);
        Container c=jf.getContentPane();
        setMinimumSize(new Dimension(XSIZE,YSIZE));
        c.add(this);
        buildMenu(jf);
        jf.setVisible(true);
    }


    public void buildMenu(JFrame jf){
        jf.setJMenuBar(new JMenuBar());
        jf.getJMenuBar().setBorderPainted(false);

        JMenu	m1= new JMenu("Game");
        m1.setBackground(new Color(150,150,250));

        newGameCmd=m1.add(new JMenuItem("New Game		(Ctrl+N)"));
        newGameCmd.addActionListener(this);

        pauseCmd=m1.add(new JMenuItem("Pause/Unpause	(P)"));
        pauseCmd.addActionListener(this);

        prevLvlCmd=m1.add(new JMenuItem("Decrease	 Level	  (1)"));
        prevLvlCmd.addActionListener(this);

        nextLvlCmd=m1.add(new JMenuItem("Increase	 Level	  (2)"));
        nextLvlCmd.addActionListener(this);

        exitCmd=m1.add(new JMenuItem("Exit	  (Ctrl+Q)"));
        exitCmd.addActionListener(this);

        jf.getJMenuBar().add(m1);
        jf.getJMenuBar().setBackground(new Color(0,0,0));

        newGame();
    }

    /**
     * Tis method starts a new game
     */
    private void newGame() {
        game = new TetrisGame(level,Arrays.asList(new TetrisPlayer(new Board(),generator)));
        game.start();
    }

    /**
     * This methods shuts down the JVM
     */
    public void	quitGame(){
        System.exit(0);
    }

    // User	Input
    public void	keyTyped(KeyEvent	e){}
    public void	keyReleased(KeyEvent	e){}

    /**
     * This method is called when a key is pressed
     *
     * @param e2 key pressed event
     */
    public void	keyPressed(KeyEvent e2){
        if (!game.isPaused())  processKeyInput(e2);

        if(e2.getKeyCode() == KeyEvent.VK_1){
            game.decreaseLevel();
        }
        if(e2.getKeyCode() == KeyEvent.VK_2){
            game.increaseLevel();
        }

        if	(e2.getKeyCode() == KeyEvent.VK_P){
            game.togglePause();
        }
        if	((e2.getKeyCode()	==	KeyEvent.VK_Q)	&&	(e2.isControlDown() || e2.isMetaDown()) ){
            quitGame();
        }
        if	((e2.getKeyCode()	==	KeyEvent.VK_N)	&&	(e2.isControlDown() || e2.isMetaDown()) ){
            newGame();
        }


    }


    public void processKeyInput(KeyEvent e2){
        if((e2.getKeyCode() == KeyEvent.VK_UP || e2.getKeyCode()	==	KeyEvent.VK_W))
            currentPiece.rotateIfPossible();

        if((e2.getKeyCode() == KeyEvent.VK_DOWN || e2.getKeyCode() == KeyEvent.VK_S))
            t.speedUp();


        if((e2.getKeyCode() == KeyEvent.VK_LEFT || e2.getKeyCode() == KeyEvent.VK_A))
            currentPiece.moveSideWays(PieceBase.LEFT);

        if((e2.getKeyCode() == KeyEvent.VK_RIGHT || e2.getKeyCode()	==	KeyEvent.VK_D))
            currentPiece.moveSideWays(PieceBase.RIGHT);

        if(e2.getKeyCode() == KeyEvent.VK_SPACE ){
            currentPiece.fallDown();
            t.setDelay(10);
        }
    }


    /**
     * This method is called when the menu is used (or hot keys)
     *
     * @param e action event
     */
    public void	actionPerformed(ActionEvent e){
        if	(e.getSource()==exitCmd){
            quitGame();
        }

        if	(e.getSource()==pauseCmd){
            pauseGame();
        }
        if(e.getSource()==newGameCmd){
            newGame(level);
        }

        if(e.getSource()==nextLvlCmd){
            level++;
            if	(level>= MAX_LEVEL)	level= MAX_LEVEL;
            getDelay();
        }

        if(e.getSource()==prevLvlCmd){
            level--;
            if	(level<0) level=0;
            getDelay();
        }

        if(e.getSource()==showHighscoreCmd){
            Highscore.showHighscore();
        }
    }


    /**
     * This method is called when the game window needs to be re-painted
     *
     * @param g the graphics handle
     */
    public void	paintComponent(Graphics	g){
        try{
            g.setColor(new	Color(0,0,0));
            g.fillRect(0,0,XSIZE,YSIZE);
            player.board.draw(g);
            player.currentPiece.draw(g);

            g.setColor(Color.RED);
            g.drawString("Score",XSIZE-100,50);
            g.drawString(""+player.currentPiece.y,XSIZE-100,80);
            g.drawString("Rows",	XSIZE-100,110);
            g.drawString(""+player.currentPiece.x,XSIZE-100,140);
            g.drawString("Level", XSIZE-100,170);
            g.drawString(""+level,XSIZE-100,200);

            if(player.nextPiece.number==6){
                player.nextPiece.draw(g,	XSIZE-80, 250);
            }
            else player.nextPiece.draw(g, XSIZE-100, 250);

            if(!gameRunning){	//If games is quit then display GAME OVER
                Font f=g.getFont();
                g.setFont(new Font("Georgia",	Font.BOLD, 50));
                g.setColor(Color.WHITE);
                g.drawString("GAME OVER",20,150);

                g.setFont(new Font("Georgia",	Font.BOLD, 30));
                //g.setColor(Color.YELLOW);
                g.drawString("Final score",100,200);
                g.drawString(""+player.score,130,250);

                g.setFont(f);
            }

            if(paused){	//	//If games is paused then display Paused
                Font f=g.getFont();
                g.setFont(new Font("Arial", Font.BOLD,	30));
                g.setColor(Color.WHITE);
                g.drawString("Paused",60,150);
                g.setFont(f);
            }
        }
        catch(Exception e){}

    }


    //Infinite rendering loop, can only be stopped by system exit
    public void	run(){
        try {
            while(true){
                if (jf.isShowing())
                    repaint();
                Thread.sleep(10);
            }

        }
        catch(InterruptedException	e)	{}
    }

}