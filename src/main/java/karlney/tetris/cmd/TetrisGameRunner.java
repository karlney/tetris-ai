package karlney.tetris.cmd;

import karlney.tetris.ai.AIFactory;
import karlney.tetris.ai.ElAshiTetrisBoardEvaluator;
import karlney.tetris.core.Board;
import karlney.tetris.core.PieceGenerator;
import karlney.tetris.core.Player;
import karlney.tetris.core.TetrisGame;

import java.util.Arrays;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-05
 * Time: 15:06
 *
 * @author karl.neyvaldt
 */
public class TetrisGameRunner {

    public static void main(String[] args) throws InterruptedException {
        TetrisGame game;
        PieceGenerator generator = new PieceGenerator();
        Player player = AIFactory.getInstantMoveOnePieceAIPlayer(0, new Board(), generator, new ElAshiTetrisBoardEvaluator());
        game = new TetrisGame(1, Arrays.asList(player));
        game.start();

        while (!game.hasEnded()){
            Thread.sleep(100);
        }
        System.exit(0);
    }
}
