import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
	public enum TokenType {
		Variable("-?[a-zA-Z]+"), // Не знаю, что значит -?, но если убрать
		// то начинает выводить каждый символ отдельно, а не целым словом
		Digit("-?[0-9]+"),
		Operation("[+|-|/|*]"),
		AssignOp("="),
		Whitespace("[ \t\f\r\n]+");
		
		public final String pattern;
		
		private TokenType(String pattern) {
			this.pattern = pattern;
		}
	}
	
	public static class Token {
	    public TokenType type;
	    public String data;

	    public Token(TokenType type, String data) {
	      this.type = type;
	      this.data = data;
	    }
	
	public String toString() {
	      return String.format("(%s %s)", type.name(), data);
	    }
	}
	
	public static ArrayList<Token> lex(String input) {
		ArrayList<Token> tokens = new ArrayList<Token>();

		  // Лексер 
		  StringBuffer tokenPatternsBuffer = new StringBuffer();
		  for (TokenType tokenType : TokenType.values())
		    tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
		  Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

		  // Сравниваем токены
		  Matcher matcher = tokenPatterns.matcher(input);
		  while (matcher.find()) {
		     for (TokenType tk: TokenType.values()) {
		          if (matcher.group(TokenType.Whitespace.name()) != null)
		              continue;
		          else if (matcher.group(tk.name()) != null) {
		              tokens.add(new Token(tk, matcher.group(tk.name())));
		              break;
		          }
		      }
		  }

		  return tokens;
	  }

	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter string line:");
		String input = scan.nextLine();
		System.out.println("Your input string is: " + input);
		
		ArrayList<Token> tokens = lex(input);
		    for (Token token : tokens)
		      System.out.println(token);
	}
}
