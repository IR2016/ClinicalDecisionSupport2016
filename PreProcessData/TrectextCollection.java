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
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrectextCollection() throws IOException {
		// This constructor should open the file in Path.DataTextDir
		// and also should make preparation for function nextDocument()
                // Do not load the whole corpus into memory!!!
	

			

			System.out.println("acquiring file list");
			filelist = new ArrayList<File>();
			filelist = getFileList(Path.DataTextDir);
			
            fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
            reader = new BufferedReader(new InputStreamReader(fis));
            System.out.println("reading files");
            
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
                    filelist.add(files[i]);
                } 
            }

        }
        return filelist;
    }

	public void refreshreader() throws IOException {
		filenum++;
		fis.close();
		reader.close();
		fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
        reader = new BufferedReader(new InputStreamReader(fis));
	}

    // YOU SHOULD IMPLEMENT THIS METHOD
    public Map<String, Object> nextDocument() throws IOException {
        refreshreader();






        return null;
    }

}
