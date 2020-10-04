package chess;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 * 
 *         King - class extends Piece abstract class. King piece behavior logic
 *         is created in this class
 *
 */
public class King extends Piece{
	/**
	 * 
	 * @param t - this boolean parameter is used to determined the team that the
	 *          piece (King) belongs to
	 */
	public King(boolean t) {
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
		//System.out.println(str);
		
		
		if(y1==y2-1 || y1==y2+1 || y1==y2) {
			if(x1==x2-1 || x1==x2+1 || x1==x2){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param str - this parameter is comprised by the move (String) entered by the
	 *            player
	 * 
	 * @return - boolean type to determine if a King and Rook piece are eligible for
	 *         castling, this method will return true if the Kind and the Rook have
	 *         not moved, and false if at least one of the pieces moved already.
	 * 
	 */
	public boolean castle(String str) {
		/**
		 * y1 - This field takes on the second bit of the string (move String) entered
		 * and transforms it in a numeric value that represents a point in the Y-axis
		 *
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
		 * hasMoved - boolean field that return True if the piece has moved and false if
		 * the piece has not moved
		 * 
		 */
		int y1 = Character.getNumericValue(str.charAt(1));
		//int x1 = Character.getNumericValue(str.charAt(0));
		int y2 = Character.getNumericValue(str.charAt(4));
		int x2 = Character.getNumericValue(str.charAt(3));
		if(hasMoved==false && (x2==6 || x2==2) && y1==y2) {
			return true;
		}
		return false;
	}
	/**
	 * move - is an abstract method inherited from the abstract Piece class
	 */
}
