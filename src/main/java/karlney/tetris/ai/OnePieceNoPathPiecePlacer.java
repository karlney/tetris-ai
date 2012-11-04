package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.UnableToPlacePieceException;

/**
 * This PiecePlacer takes into consideration the board state and the current piece when evaluating where to place the latter
 * it uses a heuristics TODO..
 *
 * NOTE: This is a simplified movement and limits the AI quite much!
 *
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
        double bestUtility = Integer.MIN_VALUE;
        int y = 1;
        Piece best = currentPiece.getTranslatedCopy(currentPiece.getX(), currentPiece.getY(), currentPiece.getRotation(), board);

        for (int h=0; h<currentPiece.getPossibleRotations(); h++){
            for (int x=0; x< board.getCols() +1; x++){
                Board boardCopy = new Board(board);
                Piece tb = currentPiece.getTranslatedCopy(x, y, h, boardCopy);
                try {
                    if (board.allowedPlacement(tb)){
                        tb.stepDownAFAP();
                        boardCopy.placePieceOnBoard(tb);
                        int rows = board.removeFullRows();
                        double utility = evaluator.score(boardCopy,tb,rows);
                        if (utility>bestUtility){
                            bestUtility=utility;
                            best=tb.getTranslatedCopy(x, y, h, boardCopy);
                        }
                    }
                } catch (UnableToPlacePieceException e) {
                    //Do nothing, move on to next position
                }

            }
        }
        return best;
    }
}
