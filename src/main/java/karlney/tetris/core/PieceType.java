package karlney.tetris.core;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-01
 * Time: 14:27
 *
 * @author karl.neyvaldt
 */
public enum PieceType {
    O((byte)1),
    I((byte)2),
    S((byte)3),
    Z((byte)4),
    L((byte)5),
    J((byte)6),
    T((byte)7),
    BOARD((byte)10);

    private final byte value;

    private PieceType(byte value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
