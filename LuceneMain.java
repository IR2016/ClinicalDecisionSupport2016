import IndexingLucene.PreProcessedCorpusReader;
import SearchLucene.CollectionProcesser;
import SearchLucene.IndexBuilder;
import SearchLucene.SearchEngine;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.document.Document;

import java.util.HashMap;
import java.util.Map;


public class LuceneMain {

    public static void main(String[] args) throws Exception {
        // main entrance
        LuceneMain lucene = new LuceneMain();

        long startTime=System.currentTimeMillis();
        long endTime=System.currentTimeMillis();

		startTime=System.currentTimeMillis();
        lucene.buildIndex("trectext");
		endTime=System.currentTimeMillis();
		System.out.println("index text corpus running time: "+(endTime-startTime)/60000.0+" min");

        // Search Part
        startTime=System.currentTimeMillis();
        // instantiate the search engine
        SearchEngine se = new SearchEngine();
        String queryString = "An 8-year-old boy presents with a swollen right knee lower extremity pain and fever The parents report no history of trauma The parents noticed a tick bite several months earlier";
        System.out.println("Search for:" + queryString);
        // retrieve top 100 matching document list for the query "Notre Dame museum"
        TopDocs topDocs = se.performSearch(queryString, 10);

        // obtain the ScoreDoc (= documentID, relevanceScore) array from topDocs
        ScoreDoc[] hits = topDocs.scoreDocs;

        // retrieve each matching document from the ScoreDoc array
        for (int i = 0; i < hits.length; i++) {
            Document doc = se.getDocument(hits[i].doc);
            System.out.println("DOCNO:" + doc.get("DOCNO") + "  Score:" + hits[i].score);
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






}
