package Search;

import Classes.Path;
import Classes.Query;
import Classes.Stemmer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Read and parse TREC queries
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class ExtractQuery {
	private FileInputStream fis = null;
	private BufferedReader br = null;
	private String line = null;
	private HashMap<String, String> topics = null;
	private List<Query> queries = null;
	private HashSet<String> stopWordSet = null;

	private void QueryReader() throws Exception {
		fis = new FileInputStream(Path.TopicDir);
		br = new BufferedReader(new InputStreamReader(fis));
	}
	// remove the punctuations in topic title
	private String queryTokenizer(String topic) throws  Exception {
		Pattern punctuations = Pattern.compile("\\[[<|a-zA-Z|\\d][^\\]]*\\]|<[/|!|\\?|a-zA-Z][^>]*>|[,|.|;|?|!|\"|\'|)|" +
				"(|{|}|:|\\-|+|\\[|\\]|_|/|>|<]");
		Matcher m = punctuations.matcher(topic);
		String topicNoPunc = m.replaceAll("");

		return topicNoPunc;
	}
	// convert all letters to lowercase
	private String[] queryToLowercase(String topic) throws Exception {
		topic = topic.toLowerCase();
		// split by whitespace
		String[] tokens = topic.split("\\s+");
		return tokens;
	}
	// load all stop words from file to a Hashset
	private void stopWordsLoader() throws Exception {
		FileInputStream stopWords = null;
		BufferedReader buffer = null;
		stopWords = new FileInputStream(Path.StopwordDir);
		buffer = new BufferedReader(new InputStreamReader(stopWords));
		stopWordSet = new HashSet<String>();

		// put all stop words to set
		String word = buffer.readLine();
		while(word != null) {
			// add word to hashset
			stopWordSet.add(word);
			word = buffer.readLine();
		}
		buffer.close();
		stopWords.close();
	}

	private boolean isStopword( String word ) {
		// return true if the input word is a stopword, or false if not

		return stopWordSet.contains(word);
	}


	private String queryStemmer(String token) throws Exception {
		//use the stemmer in Classes package to do the stemming on input word, and return the stemmed word
		char[] chars = token.toCharArray();

		Stemmer s = new Stemmer();
		s.add(chars, chars.length);
		s.stem();

		return s.toString();
	}

	public List<Query> GetQueries() throws Exception {
		//you should extract the 4 queries from the Path.TopicDir
		//NT: the query content of each topic should be 1) tokenized, 2) to lowercase, 3) remove stop words, 4) stemming
		//NT: third topic title is ----Star Trek "The Next Generation"-----, if your code can recognize the phrase marked by "", 
		//    and further process the phrase in search, you will get extra points.
		//NT: you can simply pick up title only for query, or you can also use title + description + narrative for the query content.
		Pattern tBegin = Pattern.compile("<topic\\s*.*[^>]*>");
		Pattern tEnd = Pattern.compile("</topic>");
		Pattern tSummary = Pattern.compile(".*<summary>\\s*(.*)\\s*</summary>.*");
//		Pattern tNumAndType = Pattern.compile("<.*number=\"(\\d+)\".*>");
		Pattern tNumAndType = Pattern.compile(".*<topic\\s*number=\"(\\d+)\"\\s*type=\"(.*)\"\\s*[^>]*>.*");

		topics = new HashMap<String, String>();
		String tmpNum = null;
		String tmpTitle = null;
		String tmpType = null;
		queries = new ArrayList<Query>();
		Query query = null;

		QueryReader();
		line = br.readLine();
		// get all topics and put them into a hashmap
		while (line != null) {
			// get one topic
			if(tBegin.matcher(line).find()) {
				while (!tEnd.matcher(line).find())  {
					if (tNumAndType.matcher(line).matches()) {
						Matcher mNumAndType = tNumAndType.matcher(line);
						if (mNumAndType.matches()) {
							tmpNum = mNumAndType.group(1);
							tmpType = mNumAndType.group(2);
						}
					} else if (tSummary.matcher(line).matches()) {
						Matcher mTitle = tSummary.matcher(line);
						if (mTitle.matches())
							tmpTitle = mTitle.group(1);
					}
					line = br.readLine();
				}
				// stored the topic num and title to hashmap
				if (tmpNum != null && tmpTitle != null)
					topics.put(tmpNum, tmpTitle);
			}

			line = br.readLine();
		}
		// pre-process topic titles
		stopWordsLoader();
		for (Map.Entry<String, String> topic : topics.entrySet() ) {
			String topicContent = topic.getValue();
			String topicId = topic.getKey();
			query = new Query();

			String[] tokens = queryToLowercase(queryTokenizer(topicContent));
			// remove stop words and stem every token
			String queryContent = "";
			for (int i = 0; i < tokens.length; i++) {
				if (!isStopword(tokens[i])) {
					queryContent = queryContent + queryStemmer(tokens[i]) + " ";
				}
			}
			query.SetQueryContent(queryContent);
			query.SetTopicId(topicId);

//			System.out.println(query.GetTopicId() + "/" + query.GetQueryContent());
			// put all querys to a query list
			queries.add(query);
		}

		return queries;
	}





}
