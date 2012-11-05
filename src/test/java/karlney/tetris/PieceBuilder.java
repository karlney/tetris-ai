package karlney.tetris;

import karlney.tetris.core.Board;
import karlney.tetris.core.Piece;
import karlney.tetris.core.PieceGenerator;
import karlney.tetris.core.PieceType;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-05
 * Time: 13:00
 *
 * @author karl.neyvaldt
 */
public class PieceBuilder {

    private static PieceGenerator gen = new PieceGenerator();

    public static Piece createPiece(PieceType type, Board board){
        Piece p;
        do{
            p = gen.getNextBlock(board);
        }while (type != p.getType());
        return p;
    }

}
