package PreProcessData;

import java.io.IOException;
import java.util.Map;
import Classes.Path;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * Implementation of DocumentCollection
 */
public class TrecwebCollection implements DocumentCollection {
	//you can add essential private methods or variables
	private FileInputStream fis = null;
        private BufferedReader reader = null; 
        private String line = null;
        private Map<String, Object> doc = new HashMap<String,Object>();
        
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrecwebCollection() throws IOException {
		// This constructor should open the file in Path.DataWebDir
		// and also should make preparation for function nextDocument()
		// Do not load the whole corpus into memory!!!
            
            // read file
            fis = new FileInputStream(Path.DataWebDir);
            reader = new BufferedReader(new InputStreamReader(fis));
	}       
        
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NT: the returned content of the document should be cleaned, all html tags should be removed.
		// NTT: remember to close the file that you opened, when you do not use it any more
            doc.clear();
            
            Pattern pDocBegin = Pattern.compile("<DOC>");
            Pattern pDocEnd = Pattern.compile("</DOC>");
            // match get the docnumber and content
            Pattern pNoAndText = Pattern.compile("<DOC>.*<DOCNO>(.*)</DOCNO>.*</DOCHDR>\\s*((\\W*|.*)*)\\s*</DOC>");
            //Pattern specialChars = Pattern.compile("\\W*");
            String docStr = "";
            String docNo = "";
            String text  = new String();

            line = reader.readLine();

            while(line != null) {
                if (pDocBegin.matcher(line).find()) {
                    docStr += line;
                    line = reader.readLine();
                    // match the end of doc
                    while(!pDocEnd.matcher(line).find()) {
                        docStr += line;
                        line = reader.readLine();
                    }
                    docStr += line;
                    Matcher mNoAndText = pNoAndText.matcher(docStr);
                    mNoAndText.matches();
                    // get doc number
                    docNo = mNoAndText.group(1);
                    // get doc context
                    text = mNoAndText.group(2);
//System.out.println(text);
                    // put to doc as map
                    doc.put(docNo, text.toCharArray());
                    
                    break;
                }
                line = reader.readLine();
            }
            
            // return doc if is not the end of the file
             if(line == null) {
                reader.close();
                fis.close();
                return null;
            } else {
                return doc;
            }
	}
	
}
