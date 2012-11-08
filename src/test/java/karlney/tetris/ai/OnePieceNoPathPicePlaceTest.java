package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceType;
import karlney.tetris.test.BoardBuilder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.test.BoardBuilder.createBoard;
import static karlney.tetris.test.PieceBuilder.createPiece;


/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class OnePieceNoPathPicePlaceTest {

    @Test
    public void testCOmplexBoard_ExpectIplacedatWall(){
        Board boardBefore = BoardBuilder.createBoard(20, new int[][]{
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},//
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0},//
                {0, 0, 1, 0, 0, 1, 1, 1, 1, 1},//
                {0, 0, 1, 0, 1, 1, 1, 1, 1, 0},//
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1},//
                {0, 0, 0, 0, 0, 1, 1, 0, 1, 0},//
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}});

        Board boardAfter = BoardBuilder.createBoard(20, new int[][]{
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},//
                {1, 0, 0, 1, 0, 1, 1, 1, 0, 0},//
                {1, 0, 1, 0, 0, 1, 1, 1, 1, 1},//
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 0},//
                {1, 0, 0, 0, 0, 1, 1, 0, 1, 0},//
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},//
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}});

        Piece piece = createPiece(PieceType.I, boardBefore);
        Piece out = new OnePieceNoPathCheckPiecePlacer()
                .bestPlacement(new ElAshiTetrisBoardEvaluator(),boardBefore,piece,null);
        assertEquals(-571.2945084331699,new ElAshiTetrisBoardEvaluator().score(boardAfter,out,1));
        assertEquals(out.getX(),-1);
        assertEquals(1,out.getOrientation());
        assertEquals(out.getY(),15);
    }

}
