package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;

/**
 * This PiecePlacer takes into consideration the board state and the current piece when evaluating where to place the latter
 * it uses a heuristics TODO..
 * <p/>
 * Date: 2012-11-03
 * Time: 16:17
 *
 * @author karl.neyvaldt
 */
public class OnePieceNoPathPiecePlacer implements PiecePlacer{

    private BoardEvaluator evaluator;

    @Override
    public Piece bestPlacement(Board board, Piece currentPiece, Piece nextPiece) {
        double bestUtility=-10000;
        int y=1;
        Piece best= currentPiece.getTranslatedCopy(currentPiece.getX(), currentPiece.getY(), currentPiece.getRotation(), board);

        for (int h=0; h<currentPiece.getPossibleRotations(); h++){
            for (int x=0; x< board.getCols() +1; x++){
                Board boardCopy = new Board(board);
                Piece tb = currentPiece.getTranslatedCopy(x, y, h, boardCopy);
                if (board.allowedPlacement(tb)){
                    tb.stepDownAFAP();
                    int rows = boardCopy.placePieceOnBoard(tb);
                    double utility = evaluator.score(boardCopy,tb,rows);
                    if (utility>bestUtility){
                        bestUtility=utility;
                        best=tb.getTranslatedCopy(x, y, h, boardCopy);
                    }
                }

            }
        }

        return best;
    }
}
