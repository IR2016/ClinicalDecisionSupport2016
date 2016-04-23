package Search;

import Classes.Document;
import Classes.Query;
import Classes.ValueComparator;
import IndexingLucene.MyIndexReader;

import java.util.*;

/**
 * A language retrieval model for ranking documents
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class QueryRetrievalModel {

	protected MyIndexReader indexReader;
	private Long collectionLength;
	private int MU = 2000;
	private HashMap<Integer, HashMap> relDocList = null;
	private List<Document> docList = null;

	public QueryRetrievalModel(MyIndexReader ixreader) throws Exception {
		indexReader = ixreader;
		collectionLength = new Long(indexReader.getCollectionLength());

	}

	public String[] toTokens(String qc) {
		return qc.split(" ");
	}

	private void getRelDocList(String[] tokens) throws Exception {

		// get all documents's detail
		relDocList = new HashMap<>();
		HashMap<String, Integer> docDetail = null;
		for (int i = 0; i < tokens.length; i++) {
			int[][] postingList = indexReader.getPostingList(tokens[i]);
			if (postingList != null) {
				for (int j = 0; j < postingList.length; j++) {
					if (!relDocList.containsKey(postingList[j][0])) {
						docDetail = new HashMap<>();
						docDetail.put("docLength", indexReader.docLength(postingList[j][0]));
						docDetail.put(tokens[i], postingList[j][1]);
						relDocList.put(postingList[j][0], docDetail);
					} else {
						docDetail = relDocList.get(postingList[j][0]);
						docDetail.put(tokens[i], postingList[j][1]);
						relDocList.put(postingList[j][0], docDetail);
					}
				}
			}
		}

	}

//	private HashMap<Integer, Double> sortByProbability(HashMap<Integer, Double> result) {
//		List list = new LinkedList(result.entrySet());
//		// Defined Custom Comparator here
//		Collections.sort(list, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				return ((Comparable) ((Map.Entry<Integer, Double>) (o1)).getValue())
//						.compareTo(((Map.Entry<Integer, Double>) (o2)).getValue());
//			}
//		});
//
//		// using LinkedHashMap to preserve the insertion order
//		HashMap<Integer, Double> sortedResult = new LinkedHashMap();
//		for (Iterator it = list.iterator(); it.hasNext();) {
//			Map.Entry<Integer, Double> entry = (Map.Entry<Integer, Double>) it.next();
//			sortedResult.put(entry.getKey(), entry.getValue());
//		}
//		return sortedResult;
//
//	}

	// sort document by probability
	// this method comes from online resource: http://www.programcreek.com/2013/03/java-sort-map-by-value/
	private TreeMap<Integer, Double> sortByValue(HashMap<Integer, Double> result) {
		TreeMap<Integer, Double> sortedResult = new TreeMap<>(new ValueComparator(result));
		sortedResult.putAll(result);
		return sortedResult;
	}

	public HashMap<Integer, HashMap> getQueryScoreForDoc(Query aQuery) throws Exception {
		String queryContent = aQuery.GetQueryContent();
		String[] tokens = toTokens(queryContent);
		HashMap<String, Double> tokenColRef = new LinkedHashMap<>();
		HashMap<Integer, HashMap> queryScores = new HashMap<>();
		HashMap<String, Double> scoreOneDoc = null;

		getRelDocList(tokens);
		// get smooth part, mu*colFreq/colLength
		for (int i = 0; i < tokens.length; i++) {
			long colFreq = indexReader.CollectionFreq(tokens[i]);
			Double colRef = MU*colFreq/collectionLength.doubleValue();
			tokenColRef.put(tokens[i], colRef);
		}

		// get all relevant documents with probabilities
		for (Map.Entry<Integer, HashMap> relDoc : relDocList.entrySet()) {
			scoreOneDoc = new HashMap<>();
			HashMap<String, Integer> docDetail = relDoc.getValue();
			// get score
			for (Map.Entry<String, Double> token : tokenColRef.entrySet()) {
				double score = 0;

				if (token.getValue() != 0) {
					double tokenPro = 0;
					String tokenName = token.getKey();

					if (docDetail.containsKey(tokenName)) {
						tokenPro = (double) docDetail.get(tokenName);
					}
					score = (tokenPro+token.getValue())/(docDetail.get("docLength")+MU);
					scoreOneDoc.put(tokenName, score);
				}
			}
			queryScores.put(relDoc.getKey(), scoreOneDoc);
		}

		return queryScores;
	}

	public List<Document> getTopNresult(HashMap<Integer, Double> result, int TopN) throws Exception {
		List<Document> list = new ArrayList<>();
		Document doc = null;

		// sort document by probabilities
		TreeMap<Integer, Double> sortedResult = sortByValue(result);
		// put topN documents to docList
		Iterator iterator = sortedResult.entrySet().iterator();
		int j = 0;
		while (iterator.hasNext() && j < TopN) {
			Map.Entry<Integer, Double> r = (Map.Entry<Integer, Double>) iterator.next();
			Integer docid = new Integer(r.getKey());
			String docno = indexReader.getDocno(docid);
			doc = new Document(docid.toString(), docno, r.getValue());

			list.add(doc);
			j++;
		}
		return list;
	}

	/**
	 * Search for the topic information.
	 * The returned results (retrieved documents) should be ranked by the score (from the most relevant to the least).
	 * TopN specifies the maximum number of results to be returned.
	 *
	 * @param aQuery The query to be searched for.
	 * @param TopN The maximum number of returned document
	 * @return
	 */

	public List<Document> retrieveQuery(Query aQuery, int TopN ) throws Exception {
		// NT: you will find our IndexingLucene.Myindexreader provides method: docLength()
		// implement your retrieval model here, and for each input query, return the topN retrieved documents
		// sort the docs based on their relevance score, from high to low
		HashMap<Integer, Double> result = new HashMap<>();
		docList = new ArrayList<>();

		HashMap<Integer, HashMap> queryScores = getQueryScoreForDoc(aQuery);

		// get all relevant documents with probabilities
		for (Map.Entry<Integer, HashMap> qScoreForDoc : queryScores.entrySet()) {
			// get all terms scores for every document, and multiple them
			HashMap<String, Double> qs = new HashMap<>();
			qs = qScoreForDoc.getValue();
			double score = 1;
			// Multiple all terms' score
			for (Map.Entry<String, Double> s : qs.entrySet() ) {
				score = score * s.getValue();
			}
			result.put(qScoreForDoc.getKey(), score);
		}

		docList = getTopNresult(result, TopN);

		return docList;
	}

}