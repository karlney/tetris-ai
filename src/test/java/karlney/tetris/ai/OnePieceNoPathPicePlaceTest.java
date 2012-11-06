package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceType;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.BoardBuilder.createBoard;
import static karlney.tetris.PieceBuilder.createPiece;


/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class OnePieceNoPathPicePlaceTest {

    @Test
    public void testBoardWithHoles_Expect7(){
        Board board = createBoard(6,6, new int[][]{
                {0,1,1,1,1,1},
                {0,1,1,1,1,1}});
        Piece piece = createPiece(PieceType.I, board);
        new OnePieceNoPathPiecePlacer(new ElAshiTetrisBoardEvaluator()).bestPlacement(board,piece,null);
    }


}
