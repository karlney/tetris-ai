package karlney.tetris.ai;

import karlney.tetris.core.*;
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
public class LandingHeightBoardMeasureTest {


    @Test
    public void testWithPieceI_Expect3(){
        Board board = createBoard(4, 6, new int[][]{{0,1,0,0}});
        Piece pi = new PieceI(0,2,board,new Square[4][4]);
        assertEquals(3.0,BoardMeasuresUtil.getLandingHeight(board.getRows(),pi));
    }


    @Test
    public void testWithPieceZ_Expect2() throws UnableToPlacePieceException {
        Board board = createBoard(4, 6, new int[][]{{0,1,0,0}});
        Piece pi = createPiece(PieceType.Z,board);
        pi.rotateIfPossible();
        pi.stepDownAFAP();
        board.placePieceOnBoard(pi);
        assertEquals(1.5, BoardMeasuresUtil.getLandingHeight(board.getRows(), pi));
    }


}
