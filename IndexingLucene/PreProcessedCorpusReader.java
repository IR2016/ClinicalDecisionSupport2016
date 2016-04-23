package IndexingLucene;
/**
 * Class for Preprocessing
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */

import Classes.Path;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreProcessedCorpusReader {
	

	private BufferedReader reader = null;
	private FileInputStream fis = null;

	private List<File> filelist;
	private int filenum = 0,totalFileNum;
	public PreProcessedCorpusReader(String type) throws IOException {
		// This constructor should open the file in Path.DataTextDir
		// and also should make preparation for function nextDocument()
		// remember to close the file that you opened, when you do not use it any more
		System.out.println("acquiring file list");
		filelist = new ArrayList<File>();
		filelist = getFileList(Path.ResultHM1);
		totalFileNum = filelist.size();
		fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
		reader = new BufferedReader(new InputStreamReader(fis));

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
					String needFile = ".trectext";
					if (strFileName.endsWith(needFile)) {
						filelist.add(files[i]);
					}
				}
			}

		}
		return filelist;
	}


	public Map<String, String> nextDocument() throws IOException {
		String docno=reader.readLine();
		if(docno==null) {
			fis.close();
			reader.close();
			if(filenum == totalFileNum - 1)
				return null;
			else{
				filenum++;
				fis = new FileInputStream(filelist.get(filenum).getAbsoluteFile());
				reader = new BufferedReader(new InputStreamReader(fis));
				docno = reader.readLine();
			}
		}
		String content =reader.readLine();
		Map<String, String> doc = new HashMap<String, String>();
		doc.put(docno, content);
		return doc;
	}

}
