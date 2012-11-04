package karlney.tetris.ai;

import karlney.tetris.core.Board;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.ai.BoardBuilder.createBoard;
import static karlney.tetris.ai.BoardBuilder.createEmptyBoard;

/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class WellSumsBoardMeasureTest {

    @Test
    public void testEmptyBoard_Expect0(){
        Board board = createEmptyBoard(4, 4);
        assertEquals(0,BoardMeasuresUtil.getWellSums(board));
    }

    @Test
    public void testBoardWithoutWells_Expect1(){
        Board board = createBoard(8, 6, new int[][]{{0,1,1,0,0,0,0,0}});
        assertEquals(1,BoardMeasuresUtil.getWellSums(board));
    }

    @Test
    public void testBoardWithOneWell_Expect2(){
        Board board = createBoard(3,4, new int[][]{
                {1,0,1},
                {1,0,1},
                {0,0,0}});
        assertEquals(2,BoardMeasuresUtil.getWellSums(board));
    }

        @Test
    public void testBoardWithOneWellAgainstTheWall_Expect1(){
        Board board = createBoard(3,4, new int[][]{
                {0,1,1},
                {0,0,1},
                {0,0,0}});
        assertEquals(1,BoardMeasuresUtil.getWellSums(board));
    }


        /*
6. Well Sums: A well is a succession of empty cells such that their left cells and right cells are both filled.
    */
    @Test
    public void testBoardWithSeveralWells_Expect4(){
        Board board = createBoard(8,5, new int[][]{
                {1,0,1,1,1,1,0,1},
                {1,0,1,0,0,1,0,0},
                {1,0,0,0,0,1,1,0},
                {1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}});
        //Note this is a bit strange that the rightmost 0 in row 2 counts as a well but it does according to the eltetris impl so I'll keep it as well
        //See here: https://github.com/ielashi/eltetris/blob/master/src/features.js
        assertEquals(5,BoardMeasuresUtil.getWellSums(board));
    }


}
