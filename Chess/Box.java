package chess;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class Box {
	/**
	 * 
	 * x - Determines the x-axis position of the Piece (curr Piece)
	 * 
	 * y - Determine the y - axis position of the Piece (currPiece)
	 * 
	 * currPiece - this field is used to determine the instance of Piece currently
	 * being position in the board
	 * 
	 * enpassant - this field is to determine if the Pawn instance of a piece
	 * qualifies for enPassant
	 * 
	 * 
	 */
	int x;
	int enPassant;
	int y;
	Piece currPiece;
	/**
	 * 
	 * @return the return type of this method is boolean. White = True, Black =
	 *         False
	 */

	//boolean black= false;
	public boolean isBlack() {
		if(y%2==0) {
			if(x%2==1) {
				return true;
			}
		}else if(y%2==1) {
			if(x%2==0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * toString - this method sets the String name for the Piece in the board based
	 * on the instance of Piece/Team
	 * 
	 */

	@Override
	public String toString() {
		if(currPiece instanceof Rook) {
			if(currPiece.isWhite) {
				return "wR";
			}else if(!currPiece.isWhite) {
				return "bR";
			}
		}
		if(currPiece instanceof Pawn) {
			if(currPiece.isWhite) {
				return "wP";
			}else if(!currPiece.isWhite) {
				return "bP";
			}
		}
		if(currPiece instanceof Queen) {
			if(currPiece.isWhite) {
				return "wQ";
			}else if(!currPiece.isWhite) {
				return "bQ";
			}
		}
		if(currPiece instanceof Knight) {
			if(currPiece.isWhite) {
				return "wN";
			}else if(!currPiece.isWhite) {
				return "bN";
			}
		}
		if(currPiece instanceof Bishop) {
			if(currPiece.isWhite) {
				return "wB";
			}else if(!currPiece.isWhite) {
				return "bB";
			}
		}
		if(currPiece instanceof King) {
			if(currPiece.isWhite) {
				return "wK";
			}else if(!currPiece.isWhite) {
				return "bK";
			}
		}
		if(this.currPiece==null) {
			if(this.isBlack()==true) {
				return "##";
			}if(this.isBlack()==false) {
				return "  ";
			}
		}
		return "er";
	}
	
}