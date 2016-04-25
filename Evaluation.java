import Classes.Document;
import Classes.Path;
import Classes.Query;
import Search.ExtractQuery;
import SearchLucene.SearchEngine;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qssheep on 2016/4/24.
 */
public class Evaluation {
    public Map<Integer, List<String>> groundtruth;
    public Map<Integer, List<String>> result;
    public int maxtopicnumber = 30;
    public int rlvdocnumber = 50;

    public void Evaluation() throws Exception{
        groundtruth = new HashMap<Integer, List<String>>();
        result = new HashMap<Integer, List<String>>();
    }
    public Map<Integer,Double> compare() throws Exception{
        int hitnum = 0;
        double avgprc = 0;
        Map<Integer, Double> cmp = new HashMap<Integer, Double>();
        List<Integer> hit = new ArrayList<Integer>();
        List<String> truelist = new ArrayList<String>();
        List<String> rlvlist = new ArrayList<String>();
        for(int i = 1; i <= maxtopicnumber; i++){
            System.out.print(groundtruth.size());
            System.out.print(" ");
            System.out.println(i);
            hitnum = 0;
            avgprc = 0;
            truelist = groundtruth.get(i);
            rlvlist = result.get(i);
            for(int j = 0; j < rlvdocnumber; j++){
                if(truelist.contains(rlvlist.get(j))){
                    hitnum++;
                    hit.add(1);
                    avgprc += hitnum/(j + 1);
                }
                else{
                    hit.add(0);
                }
            }
            avgprc = avgprc / rlvdocnumber;
            cmp.put(i, avgprc);
            System.out.println(avgprc);
        }
        return cmp;
    }
    public void retrievequery() throws Exception{
        result = new HashMap<Integer, List<String>>();
        SearchEngine se = new SearchEngine();
        List<Query> QList = new ExtractQuery().GetQueries();
        List<String> rlvlist = new ArrayList<String>();
        if (QList != null) {
            for (Query aQuery : QList) {
                TopDocs topDocs = se.performSearch(aQuery.GetQueryContent(), rlvdocnumber);
                ScoreDoc[] hits = topDocs.scoreDocs;
                for (int i = 0; i < hits.length; i++) {
                    org.apache.lucene.document.Document doc = se.getDocument(hits[i].doc);
                    //System.out.println("DOCNO:" + doc.get("DOCNO") + "  Score:" + hits[i].score);
                    rlvlist.add(doc.get("DOCNO"));
                }
                result.put(Integer.parseInt(aQuery.GetTopicId()),rlvlist);
                rlvlist = new ArrayList<String>();
            }
        }
    }
    public void loadgroundtruth() throws Exception{
        groundtruth = new HashMap<Integer, List<String>>();
        List<String> rlvlist = new ArrayList<String>();
        Path Path1 = new Path();
        FileInputStream fis = new FileInputStream(Path1.DataDir + "//qrels-treceval-2015.txt");;
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line = reader.readLine();
        int tpid = 1;
        String docid;
        while(line != null){
            if(tpid != Integer.parseInt(line.split(" ")[0])){
                if(rlvlist!= null)
                    groundtruth.put(tpid, rlvlist);
                else
                    System.out.println("!!!!!!!");
                tpid = Integer.parseInt(line.split(" ")[0]);
                rlvlist = new ArrayList<String>();
            }
            docid = line.split(" ")[2];
            rlvlist.add(docid);
            line = reader.readLine();
        }
        if(rlvlist!= null)
            groundtruth.put(tpid, rlvlist);
        fis.close();
        reader.close();
    }

    public static void main(String[] args) throws Exception {
        Evaluation eva = new Evaluation();
        eva.loadgroundtruth();
        eva.retrievequery();
        eva.compare();
    }
}
