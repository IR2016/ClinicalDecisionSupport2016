package SearchLucene;

/**
 * Created by JS on 4/23/16.
 */

import java.io.IOException;
import java.io.StringReader;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

//import com.sun.java.util.jar.pack.ConstantPool;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;



public class IndexBuilder {
    private IndexWriter indexWriter = null;
    private Directory directory;

    public IndexBuilder() {}
    /** Creates a new instance of Indexer */
    public IndexWriter getIndexWriter(boolean create) throws IOException {
        if (indexWriter == null) {
            directory = FSDirectory.open(Paths.get(Classes.Path.IndexTextDir));
            // StandardAnalyzer or EnglishAnalyzer
            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
            // set buffer
            config.setMaxBufferedDocs(10000);
            // set similarity, LMDirichletSimilarity, can set mu LMDirichletSimilarity(float mu)
            Similarity lmd = new LMDirichletSimilarity();
            config.setSimilarity(lmd);
            indexWriter = new IndexWriter(directory, config);
        }
        return indexWriter;
    }

//    public IndexWriter getIndexWriter(boolean create) throws IOException {
//        if (indexWriter == null) {
//            directory = FSDirectory.open(Paths.get(Classes.Path.IndexTextDir));
//
//            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
//            config.setMaxBufferedDocs(10000);
//            indexWriter = new IndexWriter(directory, config);
//        }
//        return indexWriter;
//    }

//    public void indexDocument(String docno, String contents) throws Exception {
//        IndexWriter writer = getIndexWriter(false);
//        Document doc = new Document();
//        doc.add(new StringField("DOCNO", docno, Field.Store.YES));
////        doc.add(new StringField("year", contents.get("year"), Field.Store.YES));
////        doc.add(new StringField("keyword", contents.get("keyword"), Field.Store.YES));
//        doc.add(new TextField("CONTENT", contents, Field.Store.NO));
//        writer.addDocument(doc);
//    }

    public void indexDocument(String docno, HashMap<String, String> mulDocFields) throws Exception {
        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();
        doc.add(new StringField("DOCNO", docno, Field.Store.YES));
        doc.add(new TextField("TITLE", mulDocFields.get("TITLE"), Field.Store.YES));
        doc.add(new TextField("ABSTRACT", mulDocFields.get("ABSTRACT"), Field.Store.YES));
        doc.add(new StringField("YEAR", mulDocFields.get("YEAR"), Field.Store.YES));
        doc.add(new TextField("KEYWORDS", mulDocFields.get("KEYWORDS"), Field.Store.YES));
        doc.add(new TextField("CONTENT", mulDocFields.get("CONTENT") , Field.Store.NO));
        writer.addDocument(doc);
    }

    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }




}
