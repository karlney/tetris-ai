package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceI;
import karlney.tetris.core.Square;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.ai.BoardBuilder.createBoard;

/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class LandingHeightBoardMeasureTest {


    @Test
    public void testBoardWithPieces_Expect2(){
        Board board = createBoard(4, 6, new int[][]{{0,1,0,0}});
        Piece pi = new PieceI(0,2,board,new Square[4][4]);
        assertEquals(3,BoardMeasuresUtil.getLandingHeight(board.getRows(),pi));
    }



}
