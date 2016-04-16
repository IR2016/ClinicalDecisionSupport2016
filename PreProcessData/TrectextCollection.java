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
	

			
			//获取文件列表
			System.out.println("acquiring file list");
			filelist = new ArrayList<File>();
			filelist = getFileList(Path.DataTextDir);
			
            fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
            reader = new BufferedReader(new InputStreamReader(fis));
            System.out.println("reading files");
            
	}
	
	//获取文件的函数
	public List<File> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
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
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		//文档格式分析的部分没有写
		int idindex;
		String docNo = "";
		String text = new String();
		//doc.put(docNo, text.toCharArray());
		
		filenum++;
		fis.close();
		reader.close();
		fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
        reader = new BufferedReader(new InputStreamReader(fis));
        line = reader.readLine();
        while(line != null) {
        	if(line.indexOf("article-id pub-id-type=\"pmid\"")!= -1){
        		idindex = line.indexOf("article-id pub-id-type=\"pmid\"") + 30;
        		text = line.substring(idindex, idindex + 9);
        		System.out.println(text);
        	}
        	else
        		System.out.println(filelist.get(filenum).getAbsoluteFile());
        	line = reader.readLine();
        }
        doc.put(docNo, text.toCharArray());
		return doc;
		/*
		
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
          */  
            
	}   
	
}
