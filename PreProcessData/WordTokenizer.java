package PreProcessData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 * 
 * TextTokenizer can split a sequence of text into individual word tokens, the delimiters can be any common punctuation marks(space, period etc.).
 */
public class WordTokenizer {
	//you can add any essential private method or variable
	String[] tokens;
        int tokenPoint;
    
	// YOU MUST IMPLEMENT THIS METHOD
	public WordTokenizer( char[] texts ) {
		// this constructor will tokenize the input texts (usually it is the char array for a whole document)

//            String strTexts = new String(texts);
//            // get rid of html tag
//            Pattern tag = Pattern.compile("<[/|\\!|\\?|a-zA-Z][^>]*>");
//            Matcher mTag = tag.matcher(strTexts);
//            String strTextsNoTag = mTag.replaceAll(" ");
//            
//            // split by whitespace
//            tokens = strTextsNoTag.split("\\s+");
//            // get rid of punctuations
//            Pattern punctuations = Pattern.compile("[,|.|;|?|!|\"|\'|)|(|{|}|:|-|+]");
//            int i;
//            for (i = 0; i < tokens.length; i++) {
//                Matcher m = punctuations.matcher(tokens[i]);
//                tokens[i] = m.replaceAll("");             
//            }
//            tokenPoint = 0;
            
            
            String strTexts = new String(texts);
            // get rid of html tag
            Pattern tag = Pattern.compile("\\[[<|a-zA-Z|\\d][^\\]]*\\]|<[/|!|\\?|a-zA-Z][^>]*>|[,|.|;|?|!|\"|\'|)|" +
                    "(|{|}|:|\\-|+|\\[|\\]|_|/|>|<]");
            Matcher mTag = tag.matcher(strTexts);
            String strTextsNoTag = mTag.replaceAll(" ");
            
            // split by whitespace
            tokens = strTextsNoTag.split("\\s+");
            
            tokenPoint = 0;
            
            

        }
	
	// YOU MUST IMPLEMENT THIS METHOD
	public char[] nextToken() {
		// read and return the next word of the document
		// or return null if it reaches the end of the document
            // get the next token
            if (tokenPoint == tokens.length) {
                return null;
            } else {
                char[] tokensChar = tokens[tokenPoint].toCharArray();
                tokenPoint++;
                return tokensChar;
            }
	}
	
}
