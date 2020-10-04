package chess;

import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 * 
 * 
 * 
 *         This is the class that contains the main class, and other methods
 *         such as the game flow logic, and decode methods for user input
 * 
 * 
 */
public class Chess {
	/**
	 * 
	 * @param args regular arg parameters for main
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board = new Board();
		boolean drawCall=false;
		board.initBoard();
		boolean hasThird=false;
		boolean isEnd=false;
		boolean whiteTurn = true;
		//boolean blackTurn = false;
		Scanner sc = new Scanner(System.in);
		
		while(!isEnd) {
			hasThird=false;
			System.out.println(board.printBoard());
			if(whiteTurn) {
				String kingLoc = board.findKing(true);
				
				if(board.checkCheck(kingLoc, true)) {
					if(board.checkMate(kingLoc, true)) {
						System.out.println();
						System.out.println("Checkmate");
						System.out.println("Black Wins!\n");
						break;
					}
				}
				System.out.print("White's Turn: ");
				String str = sc.nextLine();
				String code = decode(str);
				String[] extra = code.split(" ");
				
				if(code.equals("Invalid string")) {
					System.out.println();
					System.out.println("Illegal Move, try again\n");
					continue;
				}if(code.equals("resign")) {
					System.out.println();
					System.out.println("Black Wins!\n");
					break;
				}
				if(drawCall) {
					//System.out.println(code);
					if(code.equals("draw")) {
						System.out.println();
						System.out.println("Draw\n");
						break;
					}else {
						drawCall=false;
					}
				}else {
					if(code.equals("draw")) {
						System.out.println();
						System.out.println("Illegal Move, try again\n");
						continue;
					}
				}
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
				int y1 = Character.getNumericValue(code.charAt(1));
				int x1 = Character.getNumericValue(code.charAt(0));
				int y2 = Character.getNumericValue(code.charAt(4));
				int x2 = Character.getNumericValue(code.charAt(3));
				
				if( board.board[y1][x1].currPiece==null || whiteTurn!=board.board[y1][x1].currPiece.isWhite) {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}
				if(x1==x2 && y1==y2) {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}
				if(board.board[y1][x1].currPiece instanceof Knight) {
					board.movePiece(code);
					
				}
				else if(!board.hasPiecesInbetween(code)) {
					board.movePiece(code);
					
				}else if(board.hasPiecesInbetween(code)) {
					System.out.println();
					System.out.println("Illegal Move, try again\n");
					continue;
				}else {
					System.out.println();
					System.out.println("Illegal Move, try again\n");
					continue;
				}
				System.out.println();
				kingLoc = board.findKing(false);
				if(board.canPromote) {
					try{
						if(extra[2].equals("N")) {
							board.board[y2][x2].currPiece= new Knight(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}if(extra[2].equals("B")) {
							board.board[y2][x2].currPiece= new Bishop(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}
						if(extra[2].equals("R")) {
							board.board[y2][x2].currPiece= new Rook(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}
						if(extra[2].equals("Q")) {
							board.board[y2][x2].currPiece= new Queen(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}
						if(extra[2].equals("draw?")) {
							board.board[y2][x2].currPiece= new Queen(board.board[y2][x2].currPiece.isWhite);
							drawCall=true;
						}
					}catch(ArrayIndexOutOfBoundsException e){
						board.board[y2][x2].currPiece= new Queen(board.board[y2][x2].currPiece.isWhite);
					}
					
					
				}else {
					try {
						if(extra[2].equals("draw?")) {
							//System.out.println(extra[2]);
							
							drawCall=true;
						}
					}catch(ArrayIndexOutOfBoundsException e){
						
					}
				}
				
				if(board.checkCheck(kingLoc, false)) {
					if(board.checkMate(kingLoc, false)) {
						//System.out.println();
						System.out.println("Checkmate");
						System.out.println("White Wins!\n");
						break;
					}
					//System.out.println();
					System.out.println("Check\n");
				}
				if(board.board[y1][x1].currPiece!=null) {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}
				whiteTurn=false;
			}else if(!whiteTurn) {
				hasThird=false;
				String kingLoc = board.findKing(true);
				if(board.checkCheck(kingLoc, true)) {
					if(board.checkMate(kingLoc, true)) {
						System.out.println();
						System.out.println("Checkmate");
						System.out.println("White Wins!\n");
						break;
					}
				}
				System.out.print("Black's Turn: ");
				String str = sc.nextLine();
				String code = decode(str);
				String[] extra = code.split(" ");
				
				if(code.equals("resign")) {
					System.out.println();
					System.out.println("White Wins!\n");
					break;
				}
				if(drawCall) {
					//System.out.println(code);
					if(code.equals("draw")) {
						System.out.println();
						System.out.println("Draw\n");
						break;
					}else {
						drawCall=false;
					}
				}
				if(code.equals("Invalid string")) {
					System.out.println();
					System.out.println("Illegal Move, try again\n");
					continue;
				}
					
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
				int y1 = Character.getNumericValue(code.charAt(1));
				int x1 = Character.getNumericValue(code.charAt(0));
				int y2 = Character.getNumericValue(code.charAt(4));
				int x2 = Character.getNumericValue(code.charAt(3));
				
				if( board.board[y1][x1].currPiece==null || whiteTurn!=board.board[y1][x1].currPiece.isWhite) {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}
				if(x1==x2 && y1==y2) {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}
				if(board.board[y1][x1].currPiece instanceof Knight) {
					board.movePiece(code);
					
				}
				else if(!board.hasPiecesInbetween(code)) {
					board.movePiece(code);
					
				}else if(board.hasPiecesInbetween(code)) {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}else {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}
				System.out.println();
				kingLoc = board.findKing(true);
				if(board.canPromote) {
					try{
						if(extra[2].equals("N")) {
							board.board[y2][x2].currPiece= new Knight(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}if(extra[2].equals("B")) {
							board.board[y2][x2].currPiece= new Bishop(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}
						if(extra[2].equals("R")) {
							board.board[y2][x2].currPiece= new Rook(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}
						if(extra[2].equals("Q")) {
							board.board[y2][x2].currPiece= new Queen(board.board[y2][x2].currPiece.isWhite);
							try {
								if(extra[3].equals("draw?")) {
									//System.out.println(extra[2]);
									
									drawCall=true;
								}
							}catch(ArrayIndexOutOfBoundsException e){
								
							}
						}
						if(extra[2].equals("draw?")) {
							board.board[y2][x2].currPiece= new Queen(board.board[y2][x2].currPiece.isWhite);
							drawCall=true;
						}
					}catch(ArrayIndexOutOfBoundsException e){
						board.board[y2][x2].currPiece= new Queen(board.board[y2][x2].currPiece.isWhite);
					}
					
					
				}else {
					try {
						if(extra[2].equals("draw?")) {
							//System.out.println(extra[2]);
							
							drawCall=true;
						}
					}catch(ArrayIndexOutOfBoundsException e){
						
					}
				}
				
				if(board.checkCheck(kingLoc, true)) {
					if(board.checkMate(kingLoc, true)) {
						//System.out.println();
						System.out.println("Checkmate");
						System.out.println("Black Wins!\n");
						break;
					}
					//System.out.println();
					System.out.println("Check\n");
				}
				if(board.board[y1][x1].currPiece!=null) {
					System.out.println();
					System.out.println("Illegal move, try again\n");
					continue;
				}
				whiteTurn=true;
			}
			
		}
	
	}
	/**
	 * turns movement commands into Y and X coordinates: "e2 e4" to "41 43" (in the
	 * array Y comes first but in the string Y comes last)
	 * 
	 * @param raw - this is the String input (move) that will be decoded
	 * @return a string is return with computed values for X and Y
	 */
	public static String decode(String raw) {
		StringTokenizer stk = new StringTokenizer(raw); 
		boolean hasThird=false;
		String token = "";
		String str="";
		if(raw.equals("resign")) {
			return "resign";
		}
		if(raw.equals("draw")) {
			return "draw";
		}
		for(int i=0; stk.hasMoreTokens(); i++) {
			token=stk.nextToken();
			if(i<2 ) {
				if(token.length()!=2 && !raw.equals("draw")) {
					return "Invalid string";
				}
				switch (token.charAt(0)) {
		            case 'a': str+= '0';
		                     break;
		            case 'b':  str+= '1';
		                     break;
		            case 'c':  str+= '2';
		                     break;
		            case 'd':  str+= '3';
		                     break;
		            case 'e':  str+= '4';
		                     break;
		            case 'f':  str+= '5';
		                     break;
		            case 'g':  str+= '6';
		                     break;
		            case 'h':  str+= '7';
	                	break;
		            default: return "Invalid string";           
				}
				if(token.charAt(1)=='9' || token.charAt(1)=='0') {
					return "Invalid string";
				}
				if(Character.isDigit(token.charAt(1))) {
					
					if(Character.getNumericValue(token.charAt(1))<9 ) {
						str+=(8-Character.getNumericValue(token.charAt(1)));
						str+= " ";
					}
				}
				
			}
			/**
			 *  if more than 2 tokens must be promotion or draw
			 */
			
			if(i==2) {
				if(token.equals("N")) {
					hasThird=true;
					str+="N";
				}
				else if(token.equals("B")) {
					hasThird=true;
					str+="B";
				}
				else if(token.equals("Q")) {
					hasThird=true;
					str+="Q";
				}
				else if(token.equals("R")) {
					hasThird=true;
					str+="R";
				}
				else if(token.equals("draw?")) {
					str+= "draw?";
				}else if(!token.equals("")){
					return "Invalid string";
				}
			}
			if(i==3) {
				if(hasThird==true) {
					if(token.equals("draw?")) {
						str+= "draw?";
					}else {
						return "Invalid string";
					}
				}
			}
		}
		//System.out.println(str);
		return str;
	}

}
