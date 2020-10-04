package chess;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 * 
 *         This class will implement the logic to move pieces, check if there
 *         are pieces in between before performing
 *
 */

import java.util.*;
public class Board {
	/**
	 * board - instance of Box -> 8 X 8 2D array for all pieces
	 * 
	 * brokenCastle- boolean field to determine if the rook and King pieces have
	 * performed their first move if so, it returns true and Castling cannot be
	 * performed
	 * 
	 * canPromote - boolean field to determine if an instance of piece Pawn can be
	 * promoted (True) or not (False)
	 * 
	 */
	Box[][] board = new Box[8][8];
	boolean brokenCastle;
	boolean canPromote;
	
	/**
	 * @author Hunter Brady
	 * @author Alex Rivera
	 * Makes the board of boxes & fills it with pieces
	 */
	public void initBoard() {
		for(int i=0;i<8;i++) {
			for(int j=0; j<8; j++) {
				board[i][j]=new Box();
			}
		}
		
		board[0][7].currPiece = new Rook(false);
		//board[1][7].currPiece = new Rook(true);
		board[0][0].currPiece = new Rook(false);
		//board[2][4].currPiece = new Rook(false);
		board[0][6].currPiece = new Knight(false);
		board[0][1].currPiece = new Knight(false);
		board[0][2].currPiece = new Bishop(false);
		board[0][5].currPiece = new Bishop(false);
		board[0][3].currPiece = new Queen(false);
		board[0][4].currPiece = new King(false);
		//board[4][1].currPiece = new Pawn(false);
		
		//board[3][3].currPiece = new Queen(false);
		
		board[7][0].currPiece = new Rook(true);
		board[7][7].currPiece = new Rook(true);
		board[7][6].currPiece = new Knight(true);
		board[7][1].currPiece = new Knight(true);
		board[7][2].currPiece = new Bishop(true);
		board[7][5].currPiece = new Bishop(true);
		board[7][3].currPiece = new Queen(true);
		board[7][4].currPiece = new King(true);
		
		
		for(int i=0; i<8; i++) {
			for(int j =0; j<8; j++) {
				if(i==1) {
					board[i][j].currPiece = new Pawn(false);
				}
				if(i==6) {
					board[i][j].currPiece = new Pawn(true);
				}
				
				board[i][j].x=j;
				board[i][j].y=i;
				
			}
			
		}
		//board[1][3].currPiece = null;
		//board[1][1].currPiece = null;
		
	}
	/**
	 * Real basic literally just moves the piece if everything else is clear
	 * 
	 * @param str - this parameter is the input String (move) from the user
	 */
	public void movePiece(String str) {
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
		 * brokenCastle- boolean field to determine if the rook and King pieces have
		 * performed their first move if so, it returns true and Castling cannot be
		 * performed
		 * 
		 * canPromote - boolean field to determine if an instance of piece Pawn can be
		 * promoted (True) or not (False)
		 * 
		 * 
		 */
		brokenCastle=false;
		canPromote=false;
		int y1 = Character.getNumericValue(str.charAt(1));
		int x1 = Character.getNumericValue(str.charAt(0));
		int y2 = Character.getNumericValue(str.charAt(4));
		int x2 = Character.getNumericValue(str.charAt(3));
		
		Piece oldPiece=board[y1][x1].currPiece;
		Piece newPiece=board[y2][x2].currPiece;
		
		if(oldPiece==null) {
			//System.out.println("Should never print this");
			return;
		}
		if(oldPiece instanceof King) {
			
			if(newPiece==null && (Math.abs(x1-x2)==1 || Math.abs(x1-x2)==0) && (Math.abs(y1-y2)==1 || Math.abs(y1-y2)==0)) {
				if(oldPiece.legalMove(str)) {
					oldPiece.hasMoved = true;
					board[y2][x2].currPiece=oldPiece;
					board[y1][x1].currPiece = null;
					return;
				}
				//System.out.println("Illegal Move" + oldPiece.toString());
				return;
			}
			if(((King) oldPiece).castle(str)) {
				//System.out.println(x2);
				if(x2==6) {
					String temp = Integer.toString(y1);
					if(board[y1][x2+1].currPiece instanceof Rook) {
						if(board[y1][x2+1].currPiece.isWhite==board[y1][x1].currPiece.isWhite) {
							if(board[y1][x2+1].currPiece.hasMoved==false) {
								if(checkCheck("4"+temp, board[y1][4].currPiece.isWhite)) {
									brokenCastle=true;
								}
								if(checkCheck("5"+temp, board[y1][4].currPiece.isWhite)) {
									brokenCastle=true;
								}
								if(checkCheck("6"+temp, board[y1][4].currPiece.isWhite)) {
									brokenCastle=true;
								}
								if(!hasPiecesInbetween("4" + temp + " " + "7"+ temp) && !brokenCastle) {
									//System.out.println("4" + temp + " " + "7"+ temp);
									oldPiece.hasMoved = true;
									board[y1][x2+1].currPiece.hasMoved=true;
									board[y2][x2].currPiece=oldPiece;
									board[y1][x1].currPiece = null;
									board[y1][x1+1].currPiece = board[y1][x2+1].currPiece;
									board[y1][x2+1].currPiece = null;
									return;
								}
							}
						}
					}
				}if(x2==2) {
					String temp = Integer.toString(y1);
					if(board[y1][x2-2].currPiece instanceof Rook) {
						if(board[y1][x2-2].currPiece.isWhite==board[y1][x1].currPiece.isWhite) {
							if(board[y1][x2-2].currPiece.hasMoved==false) {
								if(checkCheck("4"+temp, board[y1][4].currPiece.isWhite)) {
									brokenCastle=true;
								}
								if(checkCheck("3"+temp, board[y1][4].currPiece.isWhite)) {
									brokenCastle=true;
								}
								if(checkCheck("2"+temp, board[y1][4].currPiece.isWhite)) {
									brokenCastle=true;
								}
								if(!hasPiecesInbetween("4" + temp + " " + "0"+ temp) && !brokenCastle) {
									
									oldPiece.hasMoved = true;
									board[y1][x2-2].currPiece.hasMoved=true;
									board[y2][x2].currPiece=oldPiece;
									board[y1][x1].currPiece = null;
									board[y1][x1-1].currPiece = board[y1][x2-2].currPiece;
									board[y1][x2-2].currPiece = null;
									return;
								}
								
							}
						}
					}
				}
			}
		}
		else if(oldPiece instanceof Pawn) {//Must have special check for pawn stuff because of their weird attack that isn't a legalMove
			//System.out.println("yuh");
			
				if(oldPiece.legalMove(str)) {
					oldPiece.hasMoved = true;
					board[y2][x2].currPiece=oldPiece;
					board[y1][x1].currPiece = null;
					if(y1-y2==2) {
						board[y1-1][x2].enPassant=1;
					}
					if(y1-y2==-2) {
						board[y1+1][x2].enPassant=-1;
					}
					if(board[y2][x2].currPiece.isWhite && y2==0) {
						canPromote=true;
					}else if(board[y2][x2].currPiece.isWhite && y2==7) {
						canPromote=true;
					}
					return;
				}if(((Pawn) oldPiece).isAttack(str)) {
					//System.out.println(board[y2][x2].enPassant);
					
					if(board[y2][x2].enPassant==-1 && board[y2][x2].currPiece==null) {
						oldPiece.hasMoved = true;
						board[y2][x2].currPiece=oldPiece;
						board[y1][x1].currPiece = null;
						//System.out.println(y2-1);
						board[y2+1][x2].currPiece=null;
						board[y2][x2].enPassant=5;
						return;
					}
					if(board[y2][x2].enPassant==1 && board[y2][x2].currPiece==null) {
						oldPiece.hasMoved = true;
						board[y2][x2].currPiece=oldPiece;
						board[y1][x1].currPiece = null;
						board[y2-1][x2].currPiece=null;
						board[y2][x2].enPassant=5;
						return;
					}
					if(newPiece.isWhite!=oldPiece.isWhite) {
						oldPiece.hasMoved = true;
						board[y2][x2].currPiece=oldPiece;
						board[y1][x1].currPiece = null;
						return;
						
					}
					if(newPiece.isWhite==oldPiece.isWhite) {
						//System.out.println("Your Piece error");
						return;
					}
				}
				//System.out.println("Illegal Move" + oldPiece.toString());
				return;
			
		}else if(oldPiece.legalMove(str)) {
			if(newPiece==null) {
				oldPiece.hasMoved = true;
				board[y2][x2].currPiece=oldPiece;
				board[y1][x1].currPiece = null;
				return;
			}
			if(newPiece.isWhite==oldPiece.isWhite) {
				//System.out.println("That is your piece go somewhere else");
				return;
			}
			if(newPiece.isWhite!=oldPiece.isWhite) {
				//System.out.println("Piece taken");
				oldPiece.hasMoved = true;
				board[y2][x2].currPiece=oldPiece;
				board[y1][x1].currPiece = null;
				return;
			}
		}
		
		
	}
	/**
	 * Checks that the path is clear, no longer have different movements for every
	 * piece because legalMove() handles that
	 * 
	 * @param str - this parameter is the input String (move) from the user
	 * @return returns True if there are piece in between and false if not
	 */
	public boolean hasPiecesInbetween(String str) {
		int y1 = Character.getNumericValue(str.charAt(1));
		int x1 = Character.getNumericValue(str.charAt(0));
		int y2 = Character.getNumericValue(str.charAt(4));
		int x2 = Character.getNumericValue(str.charAt(3));
		int deltaY, deltaX;
		boolean seenPiece=false;
			
			// if it's vertical movement
			if (x1 == x2 && y1 != y2) {
				if (y1 < y2) { //movement down
					for (deltaY = y1-1; deltaY > y2; deltaY--) {
						if (board[deltaY][x1].currPiece != null) {
							seenPiece=true;
						}
					}
				}
				if (y1 > y2) //movement up
					for (deltaY = y1+1; deltaY < y2; deltaY++) {
						if (board[deltaY][x1].currPiece != null) {
							seenPiece=true;
						}
					}
			}
			
			
			// if it's horizontal movement
			if (x1 != x2 && y1 == y2) {
				if (x1 < x2) { //movement right
					for (deltaX = x1+1; deltaX < x2; deltaX++) {
						if (board[y1][deltaX].currPiece != null) {
							seenPiece=true;
						}
					}
				}if (x1 > x2) { //movement left
					for (deltaX = x1-1; deltaX > x2; deltaX--) {
						if (board[y1][deltaX].currPiece != null) {
							seenPiece=true;
						}
					}
				}
			}
			
			// if it's diagonal movement
			int xDif = x1 - x2;
			int yDif = y1 - y2;
			if (Math.abs(xDif) == Math.abs(yDif)) {
				// up right if both quantities are negative
				if (yDif < 0 && xDif < 0) {
					deltaX = x1+1;
					for (deltaY = y1+1; deltaY < y2; deltaY++) {	
						//System.out.println(deltaX+" " +deltaY);
						if (board[deltaY][deltaX].currPiece != null) {
							seenPiece=true;
						}
						deltaX++;
					}
				}
				// up left if the yDif is positive and the xDif is negative
				if (yDif > 0 && xDif < 0) {
					deltaX = x1-1;
					for (deltaY = y1-1; deltaY > y2; deltaY--) {
						//System.out.println(deltaX+" " + deltaY);
						
						if (board[deltaY][deltaX].currPiece != null) {
							seenPiece=true;
						}
						deltaX++;
					}
				}
				// down right if the yDif is negative and the xDif is positive
				if (yDif < 0 && xDif > 0) {
					deltaX = x1+1;
					for (deltaY = y1-1; deltaY > y2; deltaY--) {
						//System.out.println(deltaX+" " +deltaY);
					
						if (board[deltaY][deltaX].currPiece != null) {
							seenPiece=true;
						}
						deltaX--;
					}
				}
				// downleft if both quantities are positive
				if (yDif > 0 && xDif > 0) {
					deltaX= x1-1;
					for (deltaY = y1+1; deltaY < y2; deltaY++) {
						//System.out.println(deltaX+" " +deltaY);
						if (board[deltaY][deltaX].currPiece != null) {
							seenPiece=true;
						}
						deltaX--;
					}
				}
			}
			
			return seenPiece;
		}

	public String findKing(boolean isWhiteKing) {
		for(int i=0; i<8; i++) {
			for(int j =0; j<8; j++) {
				if(board[i][j].currPiece instanceof King && board[i][j].currPiece.isWhite==isWhiteKing) {
					String taco = Integer.toString(j) + Integer.toString(i);
					//System.out.println(taco);
					return taco;
				}
			}
			
		}
		return "Didn't find it";
	}
	
	/**
	 * Searches to see if there is a checkmate once it has king location, probably
	 * one of the least efficient ways to do this but it works
	 * 
	 * @param kingLoc     string for king piece location
	 * @param isWhiteKing return true if King piece is white and false for the
	 *                    Opposite
	 * @return - boolean value True if there's a checkmate and false if not
	 */
	public boolean checkMate(String kingLoc, boolean isWhiteKing) {
		int y = Character.getNumericValue(kingLoc.charAt(1));
		int x = Character.getNumericValue(kingLoc.charAt(0));
		int xMinus=x-1;
		boolean hasAMove=false;
		int xPlus=x+1;
		int yMinus=y-1;
		int yPlus= y+1;
		if(xPlus<8 && yPlus<8) {
				String temp = Integer.toString(xPlus)+Integer.toString(yPlus);
			//one=(Integer.toString(x) + Integer.toString(y) + " " + Integer.toString(xPlus) + Integer.toString(yPlus));
				if(!(checkCheck(temp, isWhiteKing)) && (board[yPlus][xPlus].currPiece==null || board[yPlus][xPlus].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
					//System.out.println("1");
					hasAMove=true;
				}
		}if(xPlus<8) {
			String temp = Integer.toString(xPlus)+Integer.toString(y);
			if(!(checkCheck(temp, isWhiteKing)) && (board[y][xPlus].currPiece==null || board[y][xPlus].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
				//System.out.println("2");
				hasAMove=true;
			}
		}
		if(xPlus<8 && yMinus>=0) {
			String temp = Integer.toString(xPlus)+Integer.toString(yMinus);
			if(!(checkCheck(temp, isWhiteKing)) && (board[yMinus][xPlus].currPiece==null || board[yMinus][xPlus].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
				//System.out.println("3");
				hasAMove=true;
			}
		}
		if(yMinus>=0) {
			String temp = Integer.toString(x)+Integer.toString(yMinus);
			if(!(checkCheck(temp, isWhiteKing)) && (board[yMinus][x].currPiece==null || board[yMinus][x].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
				//System.out.println("4");
				hasAMove=true;
			}
		}
		if(xMinus>=0 && yMinus>=0) {
			String temp = Integer.toString(xMinus)+Integer.toString(yMinus);
			if(!(checkCheck(temp, isWhiteKing)) && (board[yMinus][xMinus].currPiece==null || board[yMinus][xMinus].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
				//System.out.println("5");
				hasAMove=true;
			}
		}
		if(xMinus>=0) {
			String temp = Integer.toString(xMinus)+Integer.toString(y);
			if(!(checkCheck(temp, isWhiteKing)) && (board[y][xMinus].currPiece==null || board[y][xMinus].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
				//System.out.println("6");
				hasAMove=true;
			}
		}
		if(xMinus>=0 && yPlus<8) {
			String temp = Integer.toString(xMinus)+Integer.toString(yPlus);
			if(!(checkCheck(temp, isWhiteKing)) && (board[yPlus][xMinus].currPiece==null || board[yPlus][xMinus].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
				//System.out.println("7");
				hasAMove=true;
			}
		}
		if(yPlus<8) {
			String temp = Integer.toString(x)+Integer.toString(yPlus);
			if(!(checkCheck(temp, isWhiteKing)) && (board[yPlus][x].currPiece==null || board[yPlus][x].currPiece.isWhite!=board[y][x].currPiece.isWhite)) {
				//System.out.println("8");
				hasAMove=true;
			}
		}
		if(hasAMove==true) {
			//System.out.println("hasAMove");
			return false;
		}else {
		return true;
		}
		
	}
	/**
	 * 
	 * 
	 * Searches to see if there is a check once it has king location, probably one
	 * of the least efficient ways to do this but it works
	 * 
	 * 
	 * @param kingLoc     string for king piece location
	 * @param isWhiteKing return true if King piece is white and false for the
	 *                    Opposite
	 * @return - boolean value True if there's a check and false if not
	 */
	public boolean checkCheck(String kingLoc, boolean isWhiteKing) {
		int y = Character.getNumericValue(kingLoc.charAt(1));
		int x = Character.getNumericValue(kingLoc.charAt(0));
		//System.out.println(kingLoc);
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				String xt = Integer.toString(j);
				String yt = Integer.toString(i);
				if(board[i][j].currPiece==null) {
					continue;
				}
				if(board[i][j].currPiece.legalMove(xt+yt+" " +x+y) && isWhiteKing!=board[i][j].currPiece.isWhite) {
					if(board[i][j].currPiece instanceof Pawn) {
						continue;
					}else if(board[i][j].currPiece instanceof Knight) {
						//System.out.println("Check");
						return true;
					}else {
						//System.out.println(xt+yt+" "+x+y);
						if(!hasPiecesInbetween(xt+yt+" "+x+y)) {
							//System.out.println("sauce");
							//System.out.println("Check");
							return true;
						}
					}
				}if(board[i][j].currPiece instanceof Pawn) {
					if(((Pawn) board[i][j].currPiece).isAttack(xt+yt+" "+x+y)) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
	/**
	 * 
	 * @return prints out a new board with the specified set up. Double hash tags
	 *         represent a black spot in the board
	 */
	public String printBoard() {
		String str ="";
		for(int i=0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				str+=board[i][j].toString();
				str+=" ";
				if(j==7) {
					str+= 8-i+"\n";
				}
			}
		}
		str+=" a  b  c  d  e  f  g  h\n";
		return str;
	}
}