package controller;

import SearchLucene.SearchEngine;

import Classes.Path;
import Classes.ResultDoc;
import Classes.Query;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 * Created by qssheep on 2016/4/23.
 */


public class ClinicalDecisionSupport {

    public Query aQuery;
    public Path Path;
    public String ResultFileName;
    public SearchEngine se;
    public List<File> files;
    public int currentpos;
    Pattern frontBodyPat = Pattern.compile(".*<front>(.*)</front>.*<body>(.*)</body>.*");
    Pattern idPat = Pattern.compile(".*<article-id pub-id-type=\"pmc\">(\\d+)</article-id>.*");
    Pattern titlePat = Pattern.compile(".*<article-title>(.+)</article-title>.*");
    Pattern yearPat = Pattern.compile(".*<pub-date pub-type=\"collection\">.*<year>(.+)</year>.*</pub-date>.*");
    Pattern abstractPat = Pattern.compile(".*<abstract>(.+)</abstract>.*");

    public ClinicalDecisionSupport(String query) throws Exception{

        Path = new Path();
        files = new ArrayList<File>();
        currentpos = 0;
        aQuery = new Query();
        aQuery.SetQueryContent(query);
        aQuery.SetTopicId("");
    }
    public List<ResultDoc> retrieveQuery() throws Exception{
        long startTime=System.currentTimeMillis();

        se = new SearchEngine();
        TopDocs topDocs = se.performSearch(aQuery.GetQueryContent(), 10);
        long endTime=System.currentTimeMillis();
        System.out.println("search time: "+(endTime-startTime)/60000.0+" min");

        startTime=System.currentTimeMillis();
        ScoreDoc[] hits = topDocs.scoreDocs;
        String url;
        ResultDoc rdoc;
        List<ResultDoc> result1 = new ArrayList<ResultDoc>();
        String docid;
        File resfile;

        for (int i = 0; i < hits.length; i++) {
            org.apache.lucene.document.Document doc = se.getDocument(hits[i].doc);
            docid = doc.get("DOCNO");
            System.out.println("DOCNO:" + doc.get("DOCNO") + "  Score:" + hits[i].score);
            resfile = SearchFile(docid, Path.DataDir);
            url = ResultFileName;
            System.out.println(url);
            rdoc = GetFileContent(url);
            result1.add(rdoc);
        }
        endTime=System.currentTimeMillis();
        System.out.println("prepare result time: "+(endTime-startTime)/60000.0+" min");
        return result1;
    }

    public ResultDoc GetFileContent(String filename) throws Exception{
        ResultDoc rsd = new ResultDoc();
        FileInputStream fis = new FileInputStream(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line = null;

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
            if (mDoc.matches()) {
                docFront = mDoc.group(1);
                docBody = mDoc.group(2);
            }

            mId = idPat.matcher(docFront);
            if (mId.matches()) {
                id = mId.group(1);
            }

            mTitle = titlePat.matcher(docFront);
            if (mTitle.matches()) {
                title = mTitle.group(1);
            }

            mAbstract = abstractPat.matcher(docFront);
            if (mAbstract.matches()) {
                absText = mAbstract.group(1);
            }
            docText += title + " " + absText + " " + docBody;
        }
        rsd.abst = absText;
        rsd.content = docBody;
        rsd.title = title;
        rsd.keywords = "keywords";
        return rsd;
    }
    public File SearchFile(String filename, String strPath) throws Exception{
        File dir = new File(strPath);
        String fileend = filename+".nxml";
        File currentfile;
        File inputfile = new File(Path.DataDir + "//dir.txt");
        FileReader fr = new FileReader(inputfile);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        while(line != null){
            dir = new File(line);
            files.add(dir);
            line = reader.readLine();
        }
        reader.close();
        fr.close();
        fr.close();
        while(currentpos < files.size()) {
            currentfile = new File(files.get(currentpos).toPath() + "\\" + fileend);
            //System.out.println(currentfile.getPath());
            if(currentfile.exists()){
                ResultFileName = currentfile.getPath();
                System.out.println(files.get(currentpos).toPath() +  "\\" +fileend);

            }

            currentpos++;
        }
        /*
        File outputfile = new File(Path.DataDir + "//dir.txt");
        FileWriter fw = new FileWriter(outputfile);
        BufferedWriter writer = new BufferedWriter(fw);
        String fileend = filename+".nxml";
        File dir = new File(strPath);
        File currentfile;
        File[] currentfiles = dir.listFiles();
        files.add(dir);
        writer.write(dir.toPath().toString());
        writer.newLine();
        while(currentpos < files.size()) {
            currentfile = new File(files.get(currentpos).toPath() + "\\" + fileend);
            //System.out.println(currentfile.getPath());
            if(currentfile.exists()){
                ResultFileName = currentfile.getPath();
                System.out.println(files.get(currentpos).toPath() +  "\\" +fileend);

            }
            else {
                currentfiles = files.get(currentpos).listFiles();
                if (currentfiles != null) {
                    for (int i = 0; i < currentfiles.length; i++) {
                        if(currentfiles[i].isDirectory()) {
                            files.add(currentfiles[i]);
                            writer.write(currentfiles[i].toPath().toString());
                            writer.newLine();
                        }
                    }
                }
            }
            currentpos++;
        }


        writer.close();
        fw.close();
        */
        return null;
    }

    public static void main(String[] args) throws Exception {
        ClinicalDecisionSupport cds = new ClinicalDecisionSupport("symbiot");
        List<ResultDoc> queryresult = cds.retrieveQuery();
        for(int i = 0; i < queryresult.size(); i++){
            System.out.print(queryresult.get(i).title);
            System.out.print(" ");
            System.out.print(queryresult.get(i).abst);
            System.out.print(" ");
            System.out.print(queryresult.get(i).content);
            System.out.print(" ");
            System.out.println(queryresult.get(i).keywords);

        }

    }
}
