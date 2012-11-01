package karlney.tetris;

import karlney.tetris.core.*;
import karlney.tetris.swing.Highscore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This is the main Game class
 * It uses Java swing for drawing the game frame, menues etc.
 */
public class GameController extends JPanel	implements Runnable,KeyListener,ActionListener{

    //Size in pixels of graphical elements
    private final static int
            XSIZE=200+180,
            YSIZE=400+90,
            LOCATION_X = 200,
            LOCATION_Y = 100;

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


    public TetrisPlayer player= new TetrisPlayer(this);
    //public AIplayer player= new AIplayer(this);


    public GameController(){

        //Init graphics
        jf.setResizable(false);
        jf.setSize(new	Dimension(XSIZE,YSIZE));
        jf.setLocation(LOCATION_X,LOCATION_Y);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.addKeyListener(this);
        Container c=jf.getContentPane();
        setMinimumSize(new Dimension(XSIZE,YSIZE));
        c.add(this);
        buildMenu(jf);
        jf.setVisible(true);

        //Init the game
        newGame(START_LEVEL);
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
        if (!paused)  player.processKeyInput(e2);

        if(e2.getKeyCode() == KeyEvent.VK_1){
            level--;

            if	(level<0) level=0;
            getDelay();
        }
        if(e2.getKeyCode() == KeyEvent.VK_2){
            level++;
            if	(level>= LEVEL_DELAYS.length)	level= LEVEL_DELAYS.length-1;
            getDelay();
        }

        if	(e2.getKeyCode() == KeyEvent.VK_P){
            pauseGame();
        }
        if	((e2.getKeyCode()	==	KeyEvent.VK_Q)	&&	(e2.isControlDown() || e2.isMetaDown()) ){
            quitGame();
        }
        if	((e2.getKeyCode()	==	KeyEvent.VK_N)	&&	(e2.isControlDown() || e2.isMetaDown()) ){
            newGame(level);
        }


    }

    /**
     * This methods shuts down the JVM
     */
    public void	quitGame(){
        System.exit(0);
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
            player.gameField.draw(g);
            player.currentBlock.draw(g);

            g.setColor(Color.RED);
            g.drawString("Score",XSIZE-100,50);
            g.drawString(""+player.currentBlock.y,XSIZE-100,80);
            g.drawString("Rows",	XSIZE-100,110);
            g.drawString(""+player.currentBlock.x,XSIZE-100,140);
            g.drawString("Level", XSIZE-100,170);
            g.drawString(""+level,XSIZE-100,200);

            if(player.nextBlock.number==6){
                player.nextBlock.draw(g,	XSIZE-80, 250);
            }
            else player.nextBlock.draw(g, XSIZE-100, 250);

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
