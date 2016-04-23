package PseudoRFSearch;

import Classes.Document;
import Classes.Query;
import IndexingLucene.MyIndexReader;
import Search.QueryRetrievalModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of Assignment 4.
 * Implement your pseudo feedback retrieval model here
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class PseudoRFRetrievalModel {

	MyIndexReader ixreader;
	List<Document> topKResults = null;
	int MU = 2000;
	int colLen;
	
	public PseudoRFRetrievalModel(MyIndexReader ixreader) throws  Exception
	{
		this.ixreader=ixreader;
		colLen = (int)(long)this.ixreader.getCollectionLength();
	}
	
	/**
	 * Search for the topic with pseudo relevance feedback. 
	 * The returned results (retrieved documents) should be ranked by the score (from the most relevant to the least).
	 * 
	 * @param aQuery The query to be searched for.
	 * @param TopN The maximum number of returned document
	 * @param TopK The count of feedback documents
	 * @param alpha parameter of relevance feedback model
	 * @return TopN most relevant document, in List structure
	 */
	public List<Document> RetrieveQuery(Query aQuery, int TopN, int TopK, double alpha) throws Exception {
		// this method will return the retrieval result of the given Query, and this result is enhanced with pseudo relevance feedback
		// (1) you should first use the original retrieval model to get TopK documents, which will be regarded as feedback documents
		// (2) implement GetTokenRFScore to get each query token's P(token|feedback model) in feedback documents
		// (3) implement the relevance feedback model for each token: combine the each query token's original retrieval score P(token|document) with its score in feedback documents P(token|feedback model)
		// (4) for each document, use the query likelihood language model to get the whole query's new score, P(Q|document)=P(token_1|document')*P(token_2|document')*...*P(token_n|document')

		// get original query score for every document
		QueryRetrievalModel reModel = new QueryRetrievalModel(ixreader);
		HashMap<Integer, HashMap> orgQueryScores = reModel.getQueryScoreForDoc(aQuery);

		//get P(token|feedback documents)
		HashMap<String,Double> TokenRFScore=GetTokenRFScore(aQuery,TopK);
		
		// sort all retrieved documents from most relevant to least, and return TopN
		List<Document> results;
		HashMap<Integer, Double> finalDocScores = new HashMap<>();
		double finalScore = 1;
		for (Map.Entry<Integer, HashMap> qScoreForDoc : orgQueryScores.entrySet()) {
			int orgDocid = qScoreForDoc.getKey();
			HashMap<String, Double> orgQueryScore = qScoreForDoc.getValue();

			for (Map.Entry<String, Double> oqs : orgQueryScore.entrySet()) {
				Double termScore = oqs.getValue();
				String term = oqs.getKey();
				finalScore = finalScore * (alpha * termScore + (1 - alpha) * TokenRFScore.get(term));
			}
			// store docid, doc rank score with feedback to map
			finalDocScores.put(orgDocid, finalScore);
		}

		results = reModel.getTopNresult(finalDocScores, TopN);
		
		return results;
	}
	
	public HashMap<String,Double> GetTokenRFScore(Query aQuery, int TopK) throws Exception
	{
		// for each token in the query, you should calculate token's score in feedback documents: P(token|feedback documents)
		// use Dirichlet smoothing
		// save <token, score> in HashMap TokenRFScore, and return it
		HashMap<String,Double> TokenRFScore=new LinkedHashMap<>();
		double score;

		QueryRetrievalModel model = new QueryRetrievalModel(ixreader);
		List<Document> topKRankDocs = model.retrieveQuery(aQuery, TopK);

		// get doc length
		int topKDocLen = 0;
		for (Document doc : topKRankDocs) {
			int docid = Integer.parseInt(doc.docid());
			topKDocLen = topKDocLen + ixreader.docLength(docid);
		}

		String queryContent = aQuery.GetQueryContent();
		String[] tokens = queryContent.split(" ");

		for (int i = 0; i < tokens.length; i++) {
			int[][] tokenPL = ixreader.getPostingList(tokens[i]);
			HashMap<Integer, Integer> postlistMap = new HashMap<>();

			// get docFreq
			int docFreq = 0;
			if (tokenPL != null) {
				// convert current term's posting list of to a hashmap
				for(int j = 0; j < tokenPL.length; j++) {
					postlistMap.put(tokenPL[j][0], tokenPL[j][1]);
				}
				for (Document doc : topKRankDocs) {
					int docid = Integer.parseInt(doc.docid());
					if(postlistMap.containsKey(docid)) {
						docFreq = docFreq + postlistMap.get(docid);
					}
				}
			}
			// get colFreq
			int colFreq = (int)ixreader.CollectionFreq(tokens[i]);

			// calculate score
			score = (docFreq+MU*((double)colFreq/colLen))/(topKDocLen+MU);

			TokenRFScore.put(tokens[i], score);
		}
		
		return TokenRFScore;
	}
	
	
}















