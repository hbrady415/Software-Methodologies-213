package chess;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 *         Piece class will be the abstract class from which all pieces of type
 *         Piece will instantiate from
 *
 */
public abstract class Piece {
	/**
	 * 
	 * isWhite - boolean field that determines if a piece belongs to the White
	 * (True) team or the Black (False) team.
	 * 
	 * firstMove - will turn True if it is a Piece's first move in the game and will
	 * defer to false after that condition has been fulfilled
	 * 
	 */
	boolean isWhite;
	boolean hasMoved= false;
	/**
	 * 
	 * @param str - this parameter will receive the move string from the user
	 * @return - the method will return true if the move is legal and false if the
	 *         move is illegal
	 */
	public abstract boolean legalMove(String str);
	
	
}
