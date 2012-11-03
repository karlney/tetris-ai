package karlney.tetris.ai;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;

/**
 * <p>
 http://ielashi.com/el-tetris-an-improvement-on-pierre-dellacheries-algorithm/

 There are six features in total, formally outlined as follows:

 1. Landing Height: The height where the piece is put (= the height of the column + (the height of the piece / 2))
 2. Rows eliminated: The number of rows eliminated.
 3. Row Transitions: The total number of row transitions. A row transition occurs when an empty cell is adjacent to a filled cell on the same row and vice versa.
 4. Column Transitions: The total number of column transitions. A column transition occurs when an empty cell is adjacent to a filled cell on the same column and vice versa.
 5. Number of Holes: A hole is an empty cell that has at least one filled cell above it in the same column.
 6. Well Sums: A well is a succession of empty cells such that their left cells and right cells are both filled.

 * </p>
 *
 * Date: 2012-11-03
 * Time: 19:56
 *
 * @author karl.neyvaldt
 */

/*
 */
public class IETetrisBoardEvaluator implements BoardEvaluator{

    @Override
    public double score(Board board, Piece piece, int linesRemoved) {
        int landHeight = getLandingHeight(board.getRows(),piece.getY());
        int rowTransitions = board.getRowTransistions();
        int columnTransitions = board.getColumnTransistions();
        int holes = board.getNrHoles();
        int wellSums = board.getWellSums();
        return 0;
    }

    private int getLandingHeight(int boardRows, int y) {
        return 0;
    }

}
