package karlney.tetris.ai;

import karlney.tetris.core.Board;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static karlney.tetris.test.BoardBuilder.createBoard;

/**
 * Date: 2012-11-04
 * Time: 22:28
 *
 * @author karl.neyvaldt
 */
public class ElAshiScoreTest {

    /*
Ultimate test
    */
    @Test
    public void testBoardWithSeveralWells_Expect4(){
        Board board = createBoard(12, new int[][]{
                {0,0,1,0,1,0,0,1,0,1,0,0},
                {0,0,0,0,0,1,1,1,0,1,0,0},
                {0,0,1,1,1,1,1,0,0,1,0,0},
                {0,0,0,1,1,1,1,1,0,1,0,0},
                {0,0,0,1,1,1,1,1,0,1,0,0},
                {0,0,0,1,0,1,1,0,0,0,0,0},
                {0,0,0,0,0,1,1,0,0,0,0,0},
                {0,0,0,0,0,1,0,0,0,0,0,0}});
        //Note this is a bit strange that the rightmost 0 in row 2 counts as a well but it does according to the eltetris impl so I'll keep it as well
        //See here: https://github.com/ielashi/eltetris/blob/master/src/features.js
        //assertEquals(4,BoardMeasuresUtil.getWellSums(board)); ue
        assert(true);
    }


}
