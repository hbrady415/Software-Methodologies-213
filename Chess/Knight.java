package chess;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 * 
 *         Knight - class extends Piece abstract class. Knight piece behavior
 *         logic is created in this class
 *
 */
public class Knight extends Piece{
	/**
	 * 
	 * @param str - this parameter will receive the move string from the user
	 * @return - the method will return true if the move is legal and false if the
	 *         move is illegal
	 */
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
		 */

		int y1 = Character.getNumericValue(str.charAt(1));
		int x1 = Character.getNumericValue(str.charAt(0));
		int y2 = Character.getNumericValue(str.charAt(4));
		int x2 = Character.getNumericValue(str.charAt(3));
		if(x1==x2+1 || x1==x2-1) {//if x2 is within 1 of x1 then y has to be 2 up or down
			if(y1==y2+2 || y1==y2-2) {
				return true;
			}
		}
		if(x1==x2+2 || x1==x2-2) {//if x2 is over 2 from x1 then y has to be 2 up or down
			if(y1==y2+1 || y1==y2-1) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param t - this boolean parameter is used to determined the team that the
	 *          piece (Knight) belongs to
	 */
	public Knight(boolean t) {
		super.isWhite = t;
	}

}
