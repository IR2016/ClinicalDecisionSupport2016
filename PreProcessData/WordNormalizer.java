package PreProcessData;
import Classes.Stemmer;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * This class is used for extract the stem of certain word by calling stemmer
 */
public class WordNormalizer {
	//you can add essential private methods or variables
	
	// YOU MUST IMPLEMENT THIS METHOD
	public char[] lowercase( char[] chars ) {
		//transform the uppercase characters in the word to lowercase
                String token = new String(chars);
                token = token.toLowerCase();
                chars = token.toCharArray();
//System.out.println(chars);
                return chars;
	}
	
	public String stem(char[] chars)
	{
		//use the stemmer in Classes package to do the stemming on input word, and return the stemmed word
		String str="";
                
                Stemmer s = new Stemmer();
                s.add(chars, chars.length);
                s.stem();
                str = s.toString();
//System.out.println(str);                
//System.exit(100);               
                
		return str;
	}
	
}
