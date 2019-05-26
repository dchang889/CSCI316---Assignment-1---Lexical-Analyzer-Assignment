import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LexicalAnalyzer {

	private ArrayList<String> inputLine;
	private int currInputLineIndex;
	private int currCharIndex;
	private int currCharPosNum;

	public LexicalAnalyzer(String filename){

		FileInputStream fstream = null;
		BufferedReader br = null;

		inputLine = new ArrayList<String>();
		currInputLineIndex = 0;
		currCharIndex = 0;
		currCharPosNum = 0;
		
		try {
			fstream = new FileInputStream(filename);
			br = new BufferedReader(new InputStreamReader(fstream));
		}
		catch (Exception e1){
			e1.printStackTrace();
			System.out.println("Error: Input Filename "+filename+" could not be found.");
			return;
		}

		try {
			String line;
			while ((line=br.readLine())!=null) {
				inputLine.add(line);
			}
			br.close();
			fstream.close();
		}
		catch(Exception e2) {
			e2.printStackTrace();
			System.out.println("An error occured reading a line from input file: "+filename);
		}
	}

	public Token getToken() {
		String lexeme = "";
		char currChar;
		
		try {
			while(inputLine.get(currInputLineIndex).length()==currCharIndex){
				System.out.println( inputLine.get(currInputLineIndex));
				currInputLineIndex++;
				currCharIndex = 0;
				currCharPosNum = currCharIndex;
				if (currInputLineIndex == inputLine.size()) {
					throw new Exception("No EOI");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("The file read did not contain an End of Input line");
			System.exit(0);
		}
		
		currChar = getCharacter(inputLine.get(currInputLineIndex));

		while(currChar == ' ') {
			currCharIndex++;
			if (inputLine.get(currInputLineIndex).length()==currCharIndex)
				return getToken();
			currChar = getCharacter(inputLine.get(currInputLineIndex));
		}

		currCharPosNum = currCharIndex;

		if(Character.isLetter(currChar)) {
			do {
				lexeme += currChar;	
				currCharIndex++;
				if (inputLine.get(currInputLineIndex).length()==currCharIndex) break;
				else currChar = getCharacter(inputLine.get(currInputLineIndex));	
				if ((lexeme.toLowerCase()).equals("end") && currChar == ' ') {
					String s1 = inputLine.get(currInputLineIndex);
					String s2 = s1.substring(currCharIndex-3, Math.min(s1.length(),12));
					s2 = s2.toLowerCase();
					if (s2.equals("end of input")) {
						lexeme = s2;
						break;
					}
				}
			}while (Character.isLetter(currChar) | Character.isDigit(currChar) );
			return lookUp(lexeme);
		}

		else if ( Character.isDigit(currChar) ) {

			do {
				lexeme += currChar;																		
				currCharIndex++;
				if (inputLine.get(currInputLineIndex).length()==currCharIndex) break;								
				else currChar = getCharacter(inputLine.get(currInputLineIndex));							
			}while ( Character.isDigit(currChar) );

			if ( Character.isLetter(currChar) ) {
				do {
					lexeme += currChar;																		
					currCharIndex++;
					if (inputLine.get(currInputLineIndex).length()==currCharIndex) break;								
					else currChar = getCharacter(inputLine.get(currInputLineIndex));							
				}while ( Character.isDigit(currChar) | Character.isLetter(currChar) );
				
				return badToken(lexeme);
			}
			
			return lookupNum(lexeme);
		}

		else if (!Character.isLetter(currChar) && !Character.isDigit(currChar) && currChar != ' ' ){

			lexeme += currChar;																	
			currCharIndex++;
			if (inputLine.get(currInputLineIndex).length()==currCharIndex) return symbol(lexeme);					
			else currChar = getCharacter(inputLine.get(currInputLineIndex));

			String t = lexeme+currChar;
			if ( t.equals("/=") | t.equals("<=") | t.equals(">=") | t.equals(":=") ) {
				lexeme += currChar;																	
				currCharIndex++;
			}
			return symbol(lexeme);
		}
		else return null;

	}

	private Token lookupNum(String s) {
		Token t;
		s = s.substring( 0 , Math.min( s.length() , 10 ) );
		try {
			int i = Integer.parseInt(s);
			t = new Token(Symbol.NUMLIT, "NULL", i);
		}catch(Exception e){
			System.out.println("First 10 Digits exceeds int32 capacity. Replacing "+s+" with 2,147,483,647");
			t = new Token(Symbol.NUMLIT, "NULL", 2147483647);
		}
		return t;
	}	

	private Token symbol(String s) {
		Token t;
		switch(s) {
		case ":=" :
			t = new Token(Symbol.BECOMES, "NULL", 0);
			break;
		case ":" :
			t = new Token(Symbol.COLON, "NULL", 0);
			break;
		case "=":
			t = new Token(Symbol.EQL, "NULL", 0);
			break;
		case ">=":
			t = new Token(Symbol.GEQ, "NULL", 0);
			break;
		case ">":
			t = new Token(Symbol.GTR, "NULL", 0);
			break;
		case "<=":
			t = new Token(Symbol.LEQ, "NULL", 0);
			break;
		case "<":
			t = new Token(Symbol.LSS, "NULL", 0);
			break;
		case "/=":
			t = new Token(Symbol.NEQ, "NULL", 0);
			break;
		case "/":
			t = new Token(Symbol.SLASH, "NULL", 0);
			break;
		case "+":
			t = new Token(Symbol.PLUS, "NULL", 0);
			break;
		case "-":
			t = new Token(Symbol.MINUS, "NULL", 0);
			break;
		case "*":
			t = new Token(Symbol.TIMES, "NULL", 0);
			break;
		case "(":
			t = new Token(Symbol.LPAREN, "NULL", 0);
			break;
		case ")":
			t = new Token(Symbol.RPAREN, "NULL", 0);
			break;
		case ",":
			t = new Token(Symbol.COMMA, "NULL", 0);
			break;
		case ";":
			t = new Token(Symbol.SEMICOLON, "NULL", 0);
			break;
		default:
			t = badToken(s);
			break;
		}
		return t;
	}

	private Token lookUp(String s) {
		Token t;
		String line = s.toLowerCase();
		switch(line) {
		case "true":
			t = new Token(Symbol.TRUESYM, "NULL", 0);
			break;
		case "false":
			t = new Token(Symbol.FALSESYM, "NULL", 0);
			break;
		case "not":
			t = new Token(Symbol.NOTSYM, "NULL", 0);
			break;
		case "rem":
			t = new Token(Symbol.REMSYM, "NULL", 0);
			break;
		case "end of input":
			t = new Token(Symbol.EOI, "NULL", 0);
			break;
		case "begin":
			t = new Token(Symbol.BEGINSYM, "NULL", 0);
			break;
		case "end":
			t = new Token(Symbol.ENDSYM, "NULL", 0);
			break;
		case "if":
			t = new Token(Symbol.IFSYM, "NULL", 0);
			break;
		case "then":
			t = new Token(Symbol.THENSYM, "NULL", 0);
			break;
		case "else":
			t = new Token(Symbol.ELSESYM, "NULL", 0);
			break;
		case "while":
			t = new Token(Symbol.WHILESYM, "NULL", 0);
			break;
		case "loop":
			t = new Token(Symbol.LOOPSYM, "NULL", 0);
			break;
		case "get":
			t = new Token(Symbol.GETSYM, "NULL", 0);
			break;
		case "put":
			t = new Token(Symbol.PUTSYM, "NULL", 0);
			break;
		case "newline":
			t = new Token(Symbol.NEWLINE, "NULL", 0);
			break;
		case "null":
			t = new Token(Symbol.NULLSYM, "NULL", 0);
			break;
		case "boolean":
			t = new Token(Symbol.BOOLSYM, "NULL", 0);
			break;
		case "integer":
			t = new Token(Symbol.INTSYM, "NULL", 0);
			break;
		case "is":
			t = new Token(Symbol.ISSYM, "NULL", 0);
			break;
		case "procedure":
			t = new Token(Symbol.PROCSYM, "NULL", 0);
			break;
		default:
			s = s.substring(0, Math.min(s.length(), 20));
			t = new Token(Symbol.IDENT, s, 0);
			break;
		}
		return t;
	}

	private Token badToken(String s) {
		Token t;
		t = new Token(Symbol.NUL, s, 0);
		return t;
	}


	private char getCharacter(String s) {
		return s.charAt(currCharIndex);
	}
	
	public int getCurrCharPosNum() {
		return currCharPosNum;
	}

}
