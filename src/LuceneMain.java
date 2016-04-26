import SearchLucene.CollectionProcesser;
import SearchLucene.IndexBuilder;
import SearchLucene.SearchEngine;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.document.Document;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class LuceneMain {

    public static void main(String[] args) throws Exception {

        // main entrance
        LuceneMain lucene = new LuceneMain();

        long startTime=System.currentTimeMillis();
        long endTime=System.currentTimeMillis();

//		startTime=System.currentTimeMillis();
//        lucene.buildIndex("trectext");
//		endTime=System.currentTimeMillis();
//		System.out.println("index text corpus running time: "+(endTime-startTime)/60000.0+" min");

        // Search Part
        startTime=System.currentTimeMillis();
        // instantiate the search engine
        SearchEngine se = new SearchEngine();



        String queryString = "automobile accident where he sustained a skull fracture." +
                " clear fluid dripping from his nose." +
                "severe headache and fever." +
                " Nuchal rigidity was found on physical examination.";
//        String queryString = "A 44-year-old man was recently in an automobile accident where he sustained a skull fracture." +
//                "    In the emergency room, he noted clear fluid dripping from his nose." +
//                "    The following day he started complaining of severe headache and fever." +
//                "     Nuchal rigidity was found on physical examination.";
//        String queryString = "headache and fever Nuchal rigidity automobile accident where he sustained a skull " +
//                "fracture clear fluid dripping from his nose. severe headache and fever." +
//                " Nuchal rigidity was found on physical examination.";
//        String queryString = "headache and fever Nuchal rigidity";
        System.out.println("Search for:" + queryString);
        // retrieve top 100 matching document list for the query "Notre Dame museum"
        TopDocs topDocs = se.performSearch(queryString, 10);

        // obtain the ScoreDoc (= documentID, relevanceScore) array from topDocs
        ScoreDoc[] hits = topDocs.scoreDocs;

        // retrieve each matching document from the ScoreDoc array
        for (int i = 0; i < hits.length; i++) {
            Document doc = se.getDocument(hits[i].doc);
            System.out.println("DOCNO:" + doc.get("DOCNO") + "  Score:" + hits[i].score + " CONTENT:" + doc.get("CONTENT"));
        }

        endTime=System.currentTimeMillis();
		System.out.println("index text corpus running time: "+(endTime-startTime)/60000.0+" min");
    }

    public void buildIndex(String dataType) throws Exception {
//        // Initiate pre-processed collection file reader
//        PreProcessedCorpusReader corpus=new PreProcessedCorpusReader(dataType);
//
//        // initiate the output object
//        IndexBuilder output=new IndexBuilder();
//
//// initiate a doc object, which can hold document number and document content of a document
//        Map<String, String> doc = null;
//
//        int count=0;
//        // build index of corpus document by document
//        while ((doc = corpus.nextDocument()) != null) {
//            // load document number and content of the document
//            String docno = doc.keySet().iterator().next();
//            String content = doc.get(docno);
//            // index this document
//            output.indexDocument(docno, content);
//
//            count++;
//            if(count%10000==0)
//                System.out.println("finish "+count+" docs");
//        }
//        System.out.println("totaly document count:  "+count);
//        output.closeIndexWriter();


        // Initiate pre-processed collection file reader
        CollectionProcesser corpus=new CollectionProcesser();

        // initiate the output object
        IndexBuilder output=new IndexBuilder();

// initiate a doc object, which can hold document number and document content of a document
        Map<String, HashMap> doc = null;

        int count=0;
        // build index of corpus document by document
        while ((doc = corpus.nextDocument()) != null) {
            // load document number and content of the document
            String docno = doc.keySet().iterator().next();
            HashMap<String,String> mulDocFields = doc.get(docno);

            // index this document
            output.indexDocument(docno, mulDocFields);

            count++;
            if(count%10000==0)
                System.out.println("finish "+count+" docs");
        }
        System.out.println("totaly document count:  "+count);
        output.closeIndexWriter();



    }

        /*
        *   <topic number="12" type="test">
    <description>A 44-year-old man was recently in an automobile accident where he sustained a skull fracture.
    In the emergency room, he noted clear fluid dripping from his nose.
    The following day he started complaining of severe headache and fever.
     Nuchal rigidity was found on physical examination.</description>
    <summary>A 44-year-old man complains of severe headache and fever.
    Nuchal rigidity was found on physical examination.</summary>
  </topic>


  12 0 2630299 0
12 0 2630300 0
12 0 2630302 0
12 0 2630936 0
12 0 2631053 0
12 0 2631758 0
12 0 2631759 0
12 0 2631890 0
12 0 2634631 0
12 0 2635124 0
12 0 2637257 0
12 0 2637292 0
12 0 2640162 0
12 0 2643119 0
12 0 2649908 0
        * */




}
