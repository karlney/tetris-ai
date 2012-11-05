package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.PieceGenerator;
import karlney.tetris.core.Player;

/**
 * Date: 2012-11-05
 * Time: 14:50
 *
 * @author karl.neyvaldt
 */
public class AIFactory {

    private AIFactory(){}

     public static AIPlayer getInstantMoveOnePieceAIPlayer(int delay, Board board, PieceGenerator generator, BoardEvaluator evaluator){
        return new AIPlayer(
                board,
                generator,
                1,
                new AIThread(
                        delay,
                        new OnePieceNoPathPiecePlacer(
                                evaluator
                        ),
                        new InstantMovePathFinder()
                )
        );
    }
}
