package karlney.tetris.ai;

import karlney.tetris.core.Board;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.BoardBuilder.createBoard;
import static karlney.tetris.BoardBuilder.createEmptyBoard;

/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class ColumnTransitionsBoardMeasureTest {

    @Test
    public void testEmptyBoard_Expect0(){
        Board board = createEmptyBoard(4, 4);
        assertEquals(0,BoardMeasuresUtil.getColumnTransitions(board));
    }

    @Test
    public void testBoardWithoutHoles_Expect2(){
        Board board = createBoard(8, 6, new int[][]{{0,1,1,0,0,0,0,0}});
        assertEquals(2,BoardMeasuresUtil.getColumnTransitions(board));
    }

    @Test
    public void testBoardWithHoles_Expect7(){
        Board board = createBoard(3,4, new int[][]{
                {1,0,1},
                {1,1,0},
                {0,1,1},
                {1,0,0}});
        assertEquals(7,BoardMeasuresUtil.getColumnTransitions(board));
    }


}
