package chess;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 * 
 *         Rook - class extends Piece abstract class. Rook piece behavior logic
 *         is created in this class
 *
 */
public class Rook extends Piece {
	/**
	 * 
	 * @param t - this boolean parameter is used to determined the team that the
	 *          piece (Knight) belongs to
	 */
	public Rook(boolean t) {
		super.isWhite = t;
	}
	/**
	 * 
	 * @param str - this parameter will receive the move string from the user
	 * @return - the method will return true if the move is legal and false if the
	 *         move is illegal
	 */

	@Override
	public boolean legalMove(String str) {
		/**
		 * y1 - This field takes on the second bit of the string (move String) entered
		 * and transforms it in a numeric value that represents a point in the Y-axis
		 *
		 * x1 - This field takes on the first bit of the string (move String) entered
		 * and transforms it in a numeric value that represents a point in the X-axis
		 * 
		 * y2 - This field takes on the last bit of the string (move String) entered and
		 * transforms it in a numeric value that represents a point in the Y-axis
		 * 
		 * x2 - This field takes on the third bit of the string (move String) entered
		 * and transforms it in a numeric value that represents a point in the X-axis
		 * 
		 */
		int y1 = Character.getNumericValue(str.charAt(1));
		int x1 = Character.getNumericValue(str.charAt(0));
		int y2 = Character.getNumericValue(str.charAt(4));
		int x2 = Character.getNumericValue(str.charAt(3));
		if(x1==x2) {
			if(y2!=y1) {
				return true;
			}
		}
		if(y1==y2) {
			if(x2!=x1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean legalCastle(String str) {
		if(super.hasMoved==false) {
		return true;
		}else {
			return false;
		}
	}
}
