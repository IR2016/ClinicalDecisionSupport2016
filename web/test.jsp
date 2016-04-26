<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>index</title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="style.css">
	</head>

	<body>
		

    <div class = "container">
    	<div class="main">	
	    	<div class="header">
	    		<h1>CLINICAL DECISION SUPPORT SYSTEM</h1>
	    		<h3>Evidence-based Search for Better Dignosis and Treatment</h3>
	    	</div><!--header-->
			
			<div class="searchBar">
				<!--tab-->
				<ul class="nav nav-tabs nav-justified">
				  <li role="presentation" class="customize-tab inactive"><a href="index.jsp">Free Text</a></li>
				  <li role="presentation" class="customize-tab"><a href="test.jsp">Test Topic</a></li>
				</ul>
				
				<!--input area-->
				<form action="QueryServlet" method="post">
					<div class="input-group" style="background: rgba(120, 191, 218, 1)">
						<select name="testTopic" class="form-control selection customize-input">
							  <option value="1">1. A 44-year-old man with coffee-ground emesis, tachycardia, hypoxia, hypotension and cool, clammy extremities.</option>
							  <option value="2">2. A 62-year-old immunosuppressed male with fever, cough and intranuclear inclusion bodies in bronchoalveolar lavage</option>
							  <option value="3">3. A 65-year-old male presents with dyspnea, tachypnea, chest pain on inspiration, and swelling and pain in the right calf.</option>
							  <option value="4">4. 82-year-old woman awakened by acute stabbing chest pain, with ST-segment elevation, hypertension, sinus tachycardia, no diseases on coronary angiography and left ventricular dysfunction on ventriculography.</option>
							  <option value="5">5. Young adult woman with 2 weeks of fever and migrating joint inflammation.</option>
							  <option value="6">6. A 46-year-old woman with sweaty hands, exophthalmia, and weight loss despite increased eating.</option>
							  <option value="7">7. A 22 year old female presents with changes in appetite and sleeping, fatigue, diminished ability to think or concentrate, anhedonia and feelings of guilt.</option>
							  <option value="8">8. A 10-year-old boy with difficulty concentrating, daytime sleepiness, and failure to thrive. The boy sleeps restlessly, snores, sweats, breathes heavily through his mouth and gasps in his sleep.</option>
							  <option value="9">9. A 10 year old child with recent history of pork consumption presents with fever, myalgia, facial edema and eosinophilia</option>
							  <option value="10">10. A 38 year old woman with severe dysmenorrhea, menorrhagia, and menometrorrhagia. PMH of infertility treatment and ectopic pregnancy</option>
							  <option value="11">11. A 56-year old Caucasian female presents with sensitivity to cold, fatigue, and constipation. Physical examination reveals hyporeflexia with delayed relaxation of knee and ankle reflexes, and very dry skin.</option>
							  <option value="12">12. A 44-year-old man complains of severe headache and fever. Nuchal rigidity was found on physical examination.</option>
							  <option value="13">13. A 5-year-old boy presents with difficulty in breathing, stridor, drooling, fever, dysphagia and voice change.</option>
							  <option value="14">14. A young woman in her second gestation presenting with anemia resistant to improvement by iron supplementation, elevated LDH, anisocytosis, poikilocytosis, hemosiderinuria and normal clotting screen.</option>
							  <option value="15">15. A 72-year-old woman with history of hypertension, type 2 diabetes, cryptogenic stroke, normal EKG, no rmal echocardiogram, normal angiograms, and complaints of occasional shortness of breath and palpitations.</option>
							  <option value="16">16. A 4 year old boy presents with wheezing after playing in the backyard unobserved.</option>
							  <option value="17">17. A 32 year old female with screening that was positive for human papilloma virus with normal Pap smears.</option>
							  <option value="18">18. A 65-year-old African-American male with progressive dyspnea on exertion and while lying flat; bilateral pitting lower-extremity edema. The lungs revealed bilateral basilar crackles.</option>
							  <option value="19">19. A 66-year-old female smoker presents with worsening dyspnea, productive chronic cough, moderate respiratory distress and unintentional weight loss.</option>
							  <option value="20">20. An 89-year-old man with progressive change in personality, poor memory, and myoclonic jerks.</option>
							  <option value="21">21. A 32-year-old male presents with diarrhea and foul-smelling stools. Stool smear reveals protozoan parasites.</option>
							  <option value="22">22. A 65-year-old male complains of productive cough with tinges of blood. Chest X-ray reveals a round opaque mass within a cavity in his lung. Culture of the sputum revealed fungal elements.</option>
							  <option value="23">23. An 18-year-old male returned from Asia a week ago. He presents with high fever, severe headache and joint pain. His blood analysis reveals leukopenia, increased hematocrit and thrombocytopenia.</option>
							  <option value="24">24. A 31 year old male presents with productive cough, chest pain, fever and chills. On exam he has audible wheezing with decreased breath sounds and dullness to percussion.</option>
							  <option value="25">25. 10-year-old boy with progressive right knee and left leg pain and edema, lethargy and an osteolytic lesion. No history of trauma, fever, tachycardia, or urinary incontinence.</option>
							  <option value="26">26. An obese 28 yo female with non-ruptured ectopic pregnancy and history of adhesions.</option>
							  <option value="27">27. A 15 yo girl with fatigue, pale skin, low hemoglobin and ferritin.</option>
							  <option value="28">28. An 8-year-old boy presents with a swollen right knee, lower extremity pain and fever. The parents report no history of trauma. The parents noticed a tick bite several months earlier.</option>
							  <option value="29">29. A 4-year-old girl with persistent high fever, skin rash, strawberry tongue, swollen red hands, and bilateral nonexudative conjunctivitis..</option>
							  <option value="30">30. A 47 year old male who fell on his outstretched left arm presents with pain, swelling, and inability to bend the arm. The x-ray, shows posterior elbow dislocation.</option>

						</select>
						  <div class="input-group-btn">
						        <button class="btn customize-btn" type="submit">Let's GO!</button>
						  </div>
					</div><!--input group>
				</form>

	   		</div><!--searchbar-->
	   	</div> <!--main-->

   	<footer class="footer">
      <div class="footer_container">

	    <p id="by">By Jie Song, Shi Qiu, Hanwei Cheng<p>
        <p class="text-muted">&copy; 2016 IS2140 Information Storage & Retrieval &middot; <a href="#">Privacy</a>
                            &middot; <a href="#">Terms</a></p>
      </div>
  		</div>
    </footer>
    
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
   	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous">
   	</script>
	</body>
</html>
