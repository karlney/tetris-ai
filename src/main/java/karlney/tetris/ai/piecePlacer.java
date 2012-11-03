package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-03
 * Time: 10:09
 *
 * @author karl.neyvaldt
 */
public interface PiecePlacer {

    Piece bestPlacement(Board board, Piece currentPiece, Piece nextPiece);
}
