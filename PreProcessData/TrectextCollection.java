package PreProcessData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Classes.Path;

import java.io.BufferedReader;
import java.io.File;
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
        private List<File> filelist;
        private int filenum = 0;
    private int totalFileNum = 0;

    Pattern frontBodyPat = Pattern.compile(".*<front>(.*)</front>.*<body>(.*)</body>.*");
    Pattern idPat = Pattern.compile(".*<article-id pub-id-type=\"pmc\">(\\d+)</article-id>.*");
    Pattern titlePat = Pattern.compile(".*<article-title>(.+)</article-title>.*");
    Pattern yearPat = Pattern.compile(".*<pub-date pub-type=\"collection\">.*<year>(.+)</year>.*</pub-date>.*");
    Pattern abstractPat = Pattern.compile(".*<abstract>(.+)</abstract>.*");

	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrectextCollection() throws IOException {
		// This constructor should open the file in Path.DataTextDir
		// and also should make preparation for function nextDocument()
                // Do not load the whole corpus into memory!!!

			System.out.println("acquiring file list");
			filelist = new ArrayList<File>();
			filelist = getFileList(Path.DataDir);
            totalFileNum = filelist.size();
			
            fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
            reader = new BufferedReader(new InputStreamReader(fis));
            System.out.println("Total reading files:" + totalFileNum);
	}
	

	public List<File> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); 
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { 
                    getFileList(files[i].getAbsolutePath()); 
                } 
                else  { 
                    String strFileName = files[i].getAbsolutePath();
                   // System.out.println("---" + strFileName);
                    String ignoreFile = ".DS_Store";
                    if (!strFileName.contains(ignoreFile)) {
                        filelist.add(files[i]);
                    }
                } 
            }

        }
        return filelist;
    }

	public void refreshreader() throws IOException {

        filenum++;
        if (filenum < totalFileNum) {
            fis.close();
            reader.close();
            fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
            reader = new BufferedReader(new InputStreamReader(fis));
        } else {
            reader = null;
        }

	}

    // YOU SHOULD IMPLEMENT THIS METHOD
    public Map<String, Object> nextDocument() throws IOException {

        if ( reader != null) { // reach the last file

            StringBuilder docMain = new StringBuilder("");
            String docText = "";

            Matcher mDoc = null;
            String docFront = "";
            String docBody = "";

            Matcher mId = null;
            String id = "";

            Matcher mTitle = null;
            String title = "";

            Matcher mAbstract = null;
            String absText = "";

            Pattern bodyBegin = Pattern.compile("<body>");
            Pattern bodyEnd = Pattern.compile("</body>");

            doc.clear();
            line = reader.readLine();

            int mark = 1;
            while(line != null && mark == 1) {
                if(bodyEnd.matcher(line).find()) {
                    mark = 0;
                }
                docMain.append(line);
                line = reader.readLine();
            }
            // if the doc with no id, ignor it
            if (idPat.matcher(docMain).matches()) {
                // get front
                mDoc = frontBodyPat.matcher(docMain);
                if(mDoc.matches()) {
                    docFront = mDoc.group(1);
                    docBody = mDoc.group(2);
                }

                mId = idPat.matcher(docFront);
                if(mId.matches()) {
                    id = mId.group(1);
                }

                mTitle = titlePat.matcher(docFront);
                if(mTitle.matches()) {
                    title = mTitle.group(1);
                }

                mAbstract = abstractPat.matcher(docFront);
                if(mAbstract.matches()) {
                    absText = mAbstract.group(1);
                }

                docText += title + " " + absText + " " + docBody;

                doc.put(id,docText.toCharArray());

            } else {  // very few doc has no pmc
                System.exit(11);
            }

            refreshreader();

        } else {
            doc = null;
        }

        return doc;
    }

}
