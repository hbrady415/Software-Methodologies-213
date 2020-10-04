package chess;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 * 
 *         Pawn - class extends Piece abstract class. Pawn piece behavior logic
 *         is created in this class
 *
 */
public class Pawn extends Piece{
	/**
	 * 
	 * @param t - this boolean parameter is used to determined the team that the
	 *          piece (Knight) belongs to
	 */
	public Pawn(boolean t) {
		super.isWhite = t;
	}
	/**
	 * 
	 * 
	 * 
	 * @param str Move String input by the user
	 * @return return true if the move is to perform an attack
	 */
	public boolean isAttack(String str) {
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
		int x1 = Character.getNumericValue(str.charAt(0));
		int y1 = Character.getNumericValue(str.charAt(1));
		int x2 = Character.getNumericValue(str.charAt(3));
		int y2 = Character.getNumericValue(str.charAt(4));
		if(super.isWhite) {
			if(y2==y1-1 && (x2==x1+1 || x2==x1-1)) {
				return true;
			}
		}
		if(!super.isWhite) {
			if(y2==y1+1 && (x2==x1+1 || x2==x1-1)) {
				return true;
			}
		}
	
		return false;
	}
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
		 * 
		 * hasMoved - boolean field that return True if the piece has moved and false if
		 * the piece has not moved
		 * 
		 */
		int x1 = Character.getNumericValue(str.charAt(0));
		int y1 = Character.getNumericValue(str.charAt(1));
		int x2 = Character.getNumericValue(str.charAt(3));
		int y2 = Character.getNumericValue(str.charAt(4));
		//System.out.println(y1);
		if(x1==x2) {
			
		
			if(super.isWhite) {
				if(y1==y2+2 && !hasMoved) {
					//this.hasMoved=true;
					return true;
				
				}
				if(y1==y2+1) {
					//this.hasMoved=true;
					return true;
				}
			}if(!super.isWhite) {
				if(y1==y2-2 && !hasMoved) {
					//this.hasMoved=true;
					return true;
				
				}
				if(y1==y2-1) {
					//this.hasMoved=true;
					return true;
				}
			}
		}
		return false;
	}
}
