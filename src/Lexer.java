
public class Lexer {
	String input = null;
	int currentPosition = 0;
	Lexer(String inp)
	{
		input = inp;
	}
	
	void skipWhitespace()
	{
		while(currentPosition < input.length() && Character.isWhitespace(input.charAt(currentPosition)))
		{
			currentPosition++;
		}
	}
	
	Token nextToken()
	{
		skipWhitespace();
		if( currentPosition >= input.length() )
		{
			return new TokenEOF();
		}
		
		return new TokenError("Unknown character in input: '" + input.charAt(currentPosition) + "'");
	}
}
