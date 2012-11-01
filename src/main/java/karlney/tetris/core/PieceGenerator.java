package karlney.tetris.core;

import java.util.Random;

/**
 * This is the PieceGenerator class. It is responsible of generating new blocks
 */
public class PieceGenerator {

    private static boolean[][][] PIECE_INITS ={
            {{true,true},{true,true}},                                     // O = 1
            {{false,false,true,false},{false,false,true,false},{false,false,true,false},{false,false,true,false}}, // I = 2
            {{true,false,false},  {true,true,false}, {false,true,false}},  // S = 3
            {{false,true,false},  {true,true,false}, {true,false,false}},  // Z = 4
            {{false,false,false}, {true,true,true},  {false,false,true}},  // L = 5
            {{false,false,false}, {true,true,true},  {true,false,false}},  // J = 6
            {{false,true,false},  {true,true,false}, {false,true,false}}}; // T = 7

    private final static int NUMBER_OF_BLOCKS = 7;

    //Random generator for next blocks
    private Random generator;


    public PieceGenerator() {
        generator = new Random();
    }

    public PieceGenerator(int seed) {
        generator = new Random(seed);
    }


    private Square[][] buildShape(PieceType type) {
        boolean[][] bools = PIECE_INITS[type.getValue()-1];
        Square[][] out = new Square[bools.length][bools.length];
        for (int i=0;i<bools.length;i++){
            for (int j=0;j<bools[i].length;j++){
                out[i][j] = new Square(type,bools[i][j]);
            }
        }
        return out;
    }

    public Piece getNextBlock(Board board){
        PieceType type = PieceType.valueOf(generator.nextInt(NUMBER_OF_BLOCKS));
        if	(type == PieceType.I)
            return new PieceI(PieceType.I,board,buildShape(type));
        else if (type == PieceType.O)
            return new PieceO(PieceType.O,board,buildShape(type));
        else
            return new PieceBase(type,board,buildShape(type));
    }

}
