import Classes.Path;
import Classes.Document;
import Classes.ResultDoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Classes.Query;
import IndexingLucene.MyIndexReader;
import PseudoRFSearch.PseudoRFRetrievalModel;
import PreProcessData.StopWordRemover;
import PreProcessData.WordNormalizer;
import PreProcessData.WordTokenizer;

/**
 * Created by qssheep on 2016/4/23.
 */


public class ClinicalDecisionSupport {
    public PseudoRFRetrievalModel pm;
    public Query aQuery;
    public Path Path;
    public String ResultFileName;

    Pattern frontBodyPat = Pattern.compile(".*<front>(.*)</front>.*<body>(.*)</body>.*");
    Pattern idPat = Pattern.compile(".*<article-id pub-id-type=\"pmc\">(\\d+)</article-id>.*");
    Pattern titlePat = Pattern.compile(".*<article-title>(.+)</article-title>.*");
    Pattern yearPat = Pattern.compile(".*<pub-date pub-type=\"collection\">.*<year>(.+)</year>.*</pub-date>.*");
    Pattern abstractPat = Pattern.compile(".*<abstract>(.+)</abstract>.*");

    public ClinicalDecisionSupport(String query) throws Exception{
        MyIndexReader ixreader = new MyIndexReader("trectext");
        pm = new PseudoRFRetrievalModel(ixreader);
        Path = new Path();

        //tokenization
        StopWordRemover stopwordRemover = new StopWordRemover();
        WordNormalizer normalizer = new WordNormalizer();
        WordTokenizer tokenizer = new WordTokenizer(query.toCharArray());
        char[] word = null;
        String wr = "";
        while ((word = tokenizer.nextToken()) != null) {
            // each word is transformed into lowercase
            word = normalizer.lowercase(word);
            //System.out.println(word);
            // filter out stopword, and only non-stopword will be written
            // into result file
            if (!stopwordRemover.isStopword(word))
                wr = wr + normalizer.stem(word) + " ";
        }
        aQuery = new Query();
        aQuery.SetQueryContent(wr);
        aQuery.SetTopicId("");
    }
    public List<ResultDoc> retrieveQuery() throws Exception{
        List<Document> results = pm.RetrieveQuery(aQuery, 20, 100, 0.4);
        List<ResultDoc> result1 = new ArrayList<ResultDoc>();
        ResultDoc doc = new ResultDoc();
        String url, docid;
        Document resdoc;
        File resfile;
        System.out.println(results.size());
        for(int i= 0; i < results.size(); i++){
            resdoc = results.get(i);
            docid = resdoc.docno();
            //System.out.println(docid);
            resfile = SearchFile(docid, Path.DataDir);
            url = ResultFileName;
            System.out.println(url);
            doc = GetFileContent(url);

            result1.add(doc);
        }
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
        String fileend = filename+".nxml";
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    SearchFile(filename, files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith(fileend)) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
                    ResultFileName = strFileName;
                    System.out.println("---" + strFileName);
                    return files[i];
                } else {
                    continue;
                }
            }

        }
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
