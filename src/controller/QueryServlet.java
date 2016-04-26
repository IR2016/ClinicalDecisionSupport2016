package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

//import Retrieval.ClinicalDecisionSupport;
import Classes.*;
import controller.ClinicalDecisionSupport;
import java.util.Map;

/**
 * Servlet implementation class queryServlet
 */
@WebServlet("/queryServlet")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    Map<Integer, ResultDoc> map;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet() {
        super();
        // TODO Auto-generated constructor stub
        map = new HashMap<>();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	String resultId = request.getParameter("id");
        if (resultId != null && !"".equals(resultId)) {
            
            getDetailById(Integer.parseInt(resultId), request, response);
            return;
        
        }
        
        
        String query = "";  
    	int topicID;
    	
    	//freetext or topic preprocess
    	if(request.getParameter("freeText") != null){
    		query = request.getParameter("freeText");
    	}else if(request.getParameter("testTopic") != null){
        		topicID =Integer.parseInt(request.getParameter("testTopic"));
        		System.out.println("TopicID = " + topicID);
        		switch (topicID) {
                case 1:  query = "A 44-year-old man with coffee-ground emesis, tachycardia, hypoxia, hypotension and cool, clammy extremities.";
                         break;
                case 2:  query = "A 62-year-old immunosuppressed male with fever, cough and intranuclear inclusion bodies in bronchoalveolar lavage";
                         break;
                case 3:  query = "A 65-year-old male presents with dyspnea, tachypnea, chest pain on inspiration, and swelling and pain in the right calf";
                         break;
                case 4:  query = "82-year-old woman awakened by acute stabbing chest pain, with ST-segment elevation, hypertension, sinus tachycardia, no diseases on coronary angiography and left ventricular dysfunction on ventriculography.";
                         break;
                case 5:  query = "Young adult woman with 2 weeks of fever and migrating joint inflammation.";
                         break;
                case 6:  query = "A 46-year-old woman with sweaty hands, exophthalmia, and weight loss despite increased eating.";
                         break;
                case 7:  query = "A 22 year old female presents with changes in appetite and sleeping, fatigue, diminished ability to think or concentrate, anhedonia and feelings of guilt.";
                         break;
                case 8:  query = "A 10-year-old boy with difficulty concentrating, daytime sleepiness, and failure to thrive. The boy sleeps restlessly, snores, sweats, breathes heavily through his mouth and gasps in his sleep.";
                         break;
                case 9:  query = "A 10 year old child with recent history of pork consumption presents with fever, myalgia, facial edema and eosinophilia";
                         break;
                case 10: query = "A 38 year old woman with severe dysmenorrhea, menorrhagia, and menometrorrhagia. PMH of infertility treatment and ectopic pregnancy";
                         break;
                case 11: query = "A 56-year old Caucasian female presents with sensitivity to cold, fatigue, and constipation. Physical examination reveals hyporeflexia with delayed relaxation of knee and ankle reflexes, and very dry skin.";
                         break;
                case 12: query = "A 44-year-old man complains of severe headache and fever. Nuchal rigidity was found on physical examination.";
                         break;
                case 13: query = "A 5-year-old boy presents with difficulty in breathing, stridor, drooling, fever, dysphagia and voice change.";
                	     break;
                case 14: query = "A young woman in her second gestation presenting with anemia resistant to improvement by iron supplementation, elevated LDH, anisocytosis, poikilocytosis, hemosiderinuria and normal clotting screen.";
                		break;
                case 15: query = "A 72-year-old woman with history of hypertension, type 2 diabetes, cryptogenic stroke, normal EKG, normal echocardiogram, normal angiograms, and complaints of occasional shortness of breath and palpitations.";
                		break;
                case 16: query = "A 4 year old boy presents with wheezing after playing in the backyard unobserved.";
                		break;
                case 17: query = "A 32 year old female with screening that was positive for human papilloma virus with normal Pap smears.";
                		break;
                case 18: query = "A 65-year-old African-American male with progressive dyspnea on exertion and while lying flat; bilateral pitting lower-extremity edema. The lungs revealed bilateral basilar crackles.";
                		break;
                case 19: query = "A 66-year-old female smoker presents with worsening dyspnea, productive chronic cough, moderate respiratory distress and unintentional weight loss.";
                		break;
                case 20: query = "An 89-year-old man with progressive change in personality, poor memory, and myoclonic jerks.";
                		break;
                case 21: query = "A 32-year-old male presents with diarrhea and foul-smelling stools. Stool smear reveals protozoan parasites.";
                		break;
                case 22: query = "A 65-year-old male complains of productive cough with tinges of blood. Chest X-ray reveals a round opaque mass within a cavity in his lung. Culture of the sputum revealed fungal elements.";
                		break;
                case 23: query = "An 18-year-old male returned from Asia a week ago. He presents with high fever, severe headache and joint pain. His blood analysis reveals leukopenia, increased hematocrit and thrombocytopenia.";
                		break;
                case 24: query = "A 31 year old male presents with productive cough, chest pain, fever and chills. On exam he has audible wheezing with decreased breath sounds and dullness to percussion.";
                		break;
                case 25: query = "10-year-old boy with progressive right knee and left leg pain and edema, lethargy and an osteolytic lesion. No history of trauma, fever, tachycardia, or urinary incontinence.";
                		break;
                case 26: query = "An obese 28 yo female with non-ruptured ectopic pregnancy and history of adhesions.";
                		break;
                case 27: query = "A 15 yo girl with fatigue, pale skin, low hemoglobin and ferritin.";
                		break;
                case 28: query = "An 8-year-old boy presents with a swollen right knee, lower extremity pain and fever. The parents report no history of trauma. The parents noticed a tick bite several months earlier.";
                		break;
                case 29: query = "A 4-year-old girl with persistent high fever, skin rash, strawberry tongue, swollen red hands, and bilateral nonexudative conjunctivitis.";
                		break;
                case 30: query = "A 47 year old male who fell on his outstretched left arm presents with pain, swelling, and inability to bend the arm. The x-ray, shows posterior elbow dislocation.";
                		break;
                
                default: query = "A 44-year-old man with coffee-ground emesis, tachycardia, hypoxia, hypotension and cool, clammy extremities.";
                         break;
        		}
    		}
    		
    		System.out.println("Query is: " + query);
    		
    		//process the searchRes
    		List<ResultDoc> searchRes = new ArrayList<>();
        	List<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
        	
        	ClinicalDecisionSupport cds = new ClinicalDecisionSupport(query); //call the constructor
    		searchRes = cds.retrieveQuery(); //call the function
    		
    		//transfer each target document to a hashmap and put the hashmap in arrayList
                int count = 0;
    		for(ResultDoc i : searchRes){ //iterate each document in the list
                    map.put(count++, i);
                    
                    HashMap<String, String> map = new HashMap<String, String>();
                    //for each variable in the document element
                    map.put("title", i.getTitle());
                    map.put("abst", i.getAbst());
//				map.put("keywords", i.getKeywords());
//				map.put("content", i.getContent());
                    res.add(map);
    		}
    		
    		JSONArray json = new JSONArray(res);
    		
    		request.setAttribute("res", json.toString());
    		//forward server's request to jsp
            getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);   
    	}
    	
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void getDetailById(int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        ResultDoc targetDoc = map.get(id);
        if (targetDoc == null) return;
        
        request.setAttribute("currentTitle", targetDoc.getTitle());
    	request.setAttribute("currentContent", targetDoc.getContent());
        
        getServletContext().getRequestDispatcher("/detail.jsp").forward(request, response);  
        
    }

}
