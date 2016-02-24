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
 *
 */
public class TrectextCollection implements DocumentCollection {
	//you can add essential private methods or variables
        private FileInputStream fis = null;
        private BufferedReader reader = null; 
        private String line = null;
        private Map<String, Object> doc = new HashMap<String,Object>();
        
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrectextCollection() throws IOException {
		// This constructor should open the file in Path.DataTextDir
		// and also should make preparation for function nextDocument()
                // Do not load the whole corpus into memory!!!
	
            // open and read the trectext file
            fis = new FileInputStream(Path.DataTextDir);
            reader = new BufferedReader(new InputStreamReader(fis));
            
            
	}
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NTT: remember to close the file that you opened, when you do not use it any more
            Pattern pDocBegin = Pattern.compile("<DOC>");
            Pattern pDocEnd = Pattern.compile("</DOC>");
            // match file withe patttern to get docno and content
            Pattern pNoAndText = Pattern.compile("<DOC>.*<DOCNO>\\s*(.*)\\s*</DOCNO>.*<TEXT>\\s*(.*)\\s*</TEXT>.*</DOC>");
            String docStr = "";
            String docNo = "";
            String text = new String();
            
            // clear previous map, to avoid the map become too large
            doc.clear();
            line = reader.readLine();
            
            while(line != null) {
                // match begin of file
                if (pDocBegin.matcher(line).find()) {
                    docStr += line;
                    line = reader.readLine();
                    // macth end of file
                    while(!pDocEnd.matcher(line).find()) {
                        docStr += line;
                        line = reader.readLine();
                    }
                    docStr += line;
                    Matcher mNoAndText = pNoAndText.matcher(docStr);
                    mNoAndText.matches();
                    docNo = mNoAndText.group(1);
                    text = mNoAndText.group(2);
                    // get two groups, put into doc map
                    doc.put(docNo, text.toCharArray());
                    break;
                }
                line = reader.readLine();
            }
            // return doc when not end
            if(line == null) {
                reader.close();
                fis.close();
                return null;
            } else {
                return doc;
            }
            
            
	}   
	
}
