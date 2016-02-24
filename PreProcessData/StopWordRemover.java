package PreProcessData;
import Classes.Path;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * StopWordRemover is a class takes charge of judging whether a given word
 * is a stopword by calling the method <i>isStopword(word)</i>.
 */
public class StopWordRemover {
	//you can add essential private methods or variables
	HashSet<String> stopWordSet = new HashSet<String>();
    
	public StopWordRemover( ) throws FileNotFoundException, IOException {
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
		// address of stopword.txt should be Path.StopwordDir
            FileInputStream stopWords = null;
            BufferedReader buffer = null;
            stopWords = new FileInputStream(Path.StopwordDir);
            buffer = new BufferedReader(new InputStreamReader(stopWords));
            
            // put all stop words to map
            String word = buffer.readLine();
            while(word != null) {
                // add word to hashmap
                stopWordSet.add(word);  
                word = buffer.readLine();
            }
          buffer.close();
          stopWords.close();
	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword( char[] word ) {
		// return true if the input word is a stopword, or false if not
            String strWord = new String(word);

            return stopWordSet.contains(strWord);
	}
}
