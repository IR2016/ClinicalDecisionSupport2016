package SearchLucene;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchEngine {
    private IndexSearcher searcher = null;
    private QueryParser parser = null;
    private Directory directory;
    private DirectoryReader reader;

    /** Creates a new instance of SearchEngine */
    public SearchEngine() throws IOException {
//        Path p = Paths.get("");
//        String curPath = p.toAbsolutePath().toString() + Classes.Path.IndexTextDir;

        directory = FSDirectory.open(Paths.get(Classes.Path.IndexTextDir));
        reader = DirectoryReader.open(directory);
        searcher = new IndexSearcher(reader);
        // set similarity, LMDirichletSimilarity, can set mu LMDirichletSimilarity(float mu)
        Similarity lmd = new LMJelinekMercerSimilarity((float) 0.8);
        searcher.setSimilarity(lmd);
        //StandardAnalyzer or EnglishAnalyzer
        parser = new QueryParser("CONTENT", new StandardAnalyzer());
    }

    public TopDocs performSearch(String queryString, int n)
            throws IOException, ParseException {
        Query query = parser.parse(queryString);
        return searcher.search(query, n);
    }

    public Document getDocument(int docId)
            throws IOException {
        return searcher.doc(docId);
    }
}