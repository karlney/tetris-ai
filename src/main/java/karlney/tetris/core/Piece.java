package karlney.tetris.core;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-01
 * Time: 15:15
 *
 * @author karl.neyvaldt
 */
public interface Piece {

    /**
     * This method will rotate the piece if its possible, otherwise it will do nothing
     * @return true if the rotate was successful, false otherwise
     */
    boolean rotateIfPossible();

    /**
     * This method will move the piece down one row if possible, otherwise it will do nothing
     * @return true if the block was moved, false otherwise
     */
    boolean moveDown();

    /**
     * This method will move the piece left or right if possible, otherwise it will do nothing
     * @param dir either left or right
     * @return true if the block was moved, false otherwise
     */
    boolean moveSideWays(PlayerInput dir);

    /**
     * This method will move the piece down one row, or if the piece is in its lowest possible position (i.e it has hit the 'floor')
     * then it will fix the piece to the board and prevent further movement
     *
     * @return true if the piece is fixed to the board, false if its still movable
     */
    boolean stepDown();

    /**
     * This method will move the piece to the very bottom and fix it to the board
     */
    void fallDown();

    /**
     *
     * @return true if this piece is falling down
     */
    boolean isFalling();

    /**
     * Rotates the piece without checking if its possible or not (useful for AI planning)
     */
    void rotateNoCheck();

    /**
     * Moves the piece down as far as possible (useful for AI planning)
     */
    void stepDownAFAP();


    /**
     *
     * @return the size of the piece
     */
    int getSize();

    /**
     *
     * @param i
     * @param j
     * @return if the square in the piece is filled
     */
    boolean isFilled(int i, int j);

    int getX();

    int getY();

    Square getSquare(int i, int j);

}
