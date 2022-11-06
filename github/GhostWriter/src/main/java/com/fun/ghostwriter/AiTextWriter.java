package com.fun.ghostwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fun.ghostWriterModel.InputGhostStringsModel;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;

@SuppressWarnings("deprecation")
@RestController
public class AiTextWriter {
	
	final private static String OpenAiApiKey = "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";

	private ArrayList<CompletionChoice> getGhostText(String ghostPrompt){
		System.out.println("Welcome to GhostWriter\n\n\n");

		OpenAiService service = new OpenAiService(OpenAiApiKey,50000);

        Engine davinci = service.getEngine("davinci");

    	ArrayList<CompletionChoice> storyArray = new ArrayList<>();

    	CompletionRequest completionRequest = CompletionRequest.builder()
								                .prompt(ghostPrompt)
								                .temperature(0.9)
								                .maxTokens(1500)
								                .topP(1.0)
								                .frequencyPenalty(0.0)
								                .presencePenalty(0.3)
								                .echo(true)
								                .build();
    	service.createCompletion("davinci", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
		return storyArray;
	}


	
	public String duplicateLinesEliminater(String s) throws IOException {
		
		String[] tokens = s.split("\n");
		StringBuilder resultBuilder = new StringBuilder();
		Set<String> alreadyPresent = new HashSet<String>();

		boolean first = true;
		for(String token : tokens) {

		    if(!alreadyPresent.contains(token)) {
		        if(first) first = false;
		        else resultBuilder.append("\n");

		        if(!alreadyPresent.contains(token))
		            resultBuilder.append(token);
		    }

		    alreadyPresent.add(token);
		}
		s = resultBuilder.toString();
		
		System.out.println("After Duplicate lines removed " +s);
		
		return s;
		
	}
	

	public String spellChecker(String str) throws IOException {
		String[] input = str.split(" ");
		JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
	    for (Rule rule : langTool.getAllRules()) {
	      if (!rule.isDictionaryBasedSpellingRule()) {
	        langTool.disableRule(rule.getId());
	      }
	    }
		
		for (int i =0; i<input.length;i++) {
			
		    List<RuleMatch> matches = langTool.check(input[i]);
		    for (RuleMatch match : matches) {
		          List<String> suggestions = match.getSuggestedReplacements();
		          if(!suggestions.isEmpty()) {
		        	  input[i]= suggestions.get(0);
		          }
		    }
		}
		str = "";
		for(String s :input) {
			str = str+s+" ";
		}
		return "the sentence :\n"+str;
	}
	
	@PostMapping(value = "/getGhostArticles",headers = {"content-type=application/json" }, consumes = "application/json", produces = "application/json")
	@ResponseBody public List<ArrayList<CompletionChoice>> ghostWriterRequestHandler(@RequestBody String[] ghostPromptinputStrings) throws IOException {
		
		
		System.out.println("Hello Y'all!!! Welcome to Ghost Writer-AI with GPT-3 support, powered by OpenAi \n\n\n");
		
		List<ArrayList<CompletionChoice>> outputStringArray= new ArrayList();
        
		for(int i = 0; i<ghostPromptinputStrings.length; i++){
        
			ArrayList<CompletionChoice> storyArray = new ArrayList<>();
        	
			storyArray = getGhostText(ghostPromptinputStrings[i]);
        	
			String s = storyArray.get(0).getText();
        	
	
			int len = (s.split(" ")).length;
			startOverAgain:
			if(  len < 700  ) {
				while( len < 700 )
				{
					storyArray = getGhostText(ghostPromptinputStrings[i]);
					s = storyArray.get(0).getText();
					
					s = duplicateLinesEliminater(s);
					s = spellChecker(s);
					
					len = (s.split(" ")).length;
					
				}
				
			}
			else {
				
				s = duplicateLinesEliminater(s);
				s = spellChecker(s);
				len = (s.split(" ")).length;
				if(len < 700) {
					break startOverAgain;
				}
			}

			storyArray.get(0).setText(s);
        	
        	System.out.println("Word Count for ghost Prompt \" " +ghostPromptinputStrings[i]+ "\" : - "+ len +"\n\n");
        	
        	outputStringArray.add(storyArray);
        }

		return outputStringArray;

	}
	
	@PostMapping(value = "/SpellCheck",headers = {"content-type=application/json" }, consumes = "application/json", produces = "application/json")
	@ResponseBody public String testApi(@RequestBody String str) throws IOException {
		
		String[] input = str.split(" ");
		JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
	    for (Rule rule : langTool.getAllRules()) {
	      if (!rule.isDictionaryBasedSpellingRule()) {
	        langTool.disableRule(rule.getId());
	      }
	    }
		
		for (int i =0; i<input.length;i++) {
			
		    List<RuleMatch> matches = langTool.check(input[i]);
		    for (RuleMatch match : matches) {
		          List<String> suggestions = match.getSuggestedReplacements();
		          if(!suggestions.isEmpty()) {
		        	  input[i]= suggestions.get(0);
		          }
		    }
		}
		
		str = "";
		for(String s :input) {
			str = str+s+" ";
		}
		return "the sentence :\n"+str;
	}
	
}


/*
 * 
 * for (Rule rule : langTool.getAllRules()) { if
 * (!rule.isDictionaryBasedSpellingRule()) { langTool.disableRule(rule.getId());
 * } }
 * 
 */ 
//List<RuleMatch> matches = langTool.check(str);
//for (RuleMatch match : matches) {
//  res.append("Potential typo at characters " +
//      match.getFromPos() + "-" + match.getToPos() + ": " +
//      match.getMessage()+"\n");
//  res.append("Suggested correction(s): " +
//      match.getSuggestedReplacements()+"\n");
//}






//String finishReason = storyArray.get(i).getFinish_reason();



//****************************************************************************************************************************

//while(  !finishReason.equals("length")  ) {

//	storyArray = getGhostText(ghostPromptinputStrings[i]);
	
//	finishReason = storyArray.get(0).getFinish_reason();
//}
//****************************************************************************************************************************



        /*
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
        String[] inputStringArray = { "Money is tt" };
        List<ArrayList<CompletionChoice>> outputStringArray= new ArrayList();

// ****************************************************************************************************************************

		String token = System.getenv("OpenAiApiKey");
		OpenAiService service = new OpenAiService(token);

        Engine davinci = service.getEngine("davinci");

        for(int i =0; i<inputStringArray.length; i++){
        	System.out.println("\nGhost Text with Topic : "+ i +"  <<<<"+ inputStringArray[i]+">>>>\n\n");

        	ArrayList<CompletionChoice> storyArray = new ArrayList<>();

        	CompletionRequest completionRequest = CompletionRequest.builder()
									                .prompt(inputStringArray[i])
									                .temperature(0.9)
									                .maxTokens(340)
									                .topP(1.0)
									                .frequencyPenalty(0.0)
									                .presencePenalty(0.3)
									                .echo(true)
									                .build();
        	service.createCompletion("text-davinci-001", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        	outputStringArray.add(cleanData(storyArray));

		}
         * 
         * 
         * Spell Checker
         * 
         * StringBuilder sb = new StringBuilder(s);
		
		JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
	    for (Rule rule : langTool.getAllRules()) {
	      if (!rule.isDictionaryBasedSpellingRule()) {
	        langTool.disableRule(rule.getId());
	      }
	    }
	    List<RuleMatch> matches = langTool.check(s);
	    for (RuleMatch match : matches) {
	          List<String> suggestions = match.getSuggestedReplacements();
	          sb.replace(match.getFromPos(), match.getToPos(), suggestions.get(0));
	    }
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
        ArrayList<CompletionChoice> storyArray = new ArrayList<CompletionChoice>();

        /*
        String prompt = "Why were you unable to completely accomplish the purpose of your "
        		+ "visit today at our website? Because, I don't know whether there are any "
        		+ "IT careers at Ford Motor Companies. So, I think its not completely ";

        *//*

        String prompt = "I realy like you Michelle Kelly, I like your personality, honesty and the way you live. I want to ask you out ";

        //String prompt = "how to teach discipline kids as a mom, ";

        System.out.println("\nBrewing up a story with Topic "+ prompt+"\n\n");
        CompletionRequest completionRequest = null;

	        	completionRequest = CompletionRequest.builder()
										                .prompt(prompt)
										                .temperature(0.9)
										                .maxTokens(340)
										                .topP(1.0)
										                .frequencyPenalty(0.0)
										                .presencePenalty(0.3)
										                .echo(true)
										                .build();

        //service.createCompletion("text-ada-001", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("text-davinci-001", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("davinci-instruct-beta", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("text-curie-001", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("ada", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});


         service.createCompletion("text-davinci-001", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});


        //service.createCompletion("babbage", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("text-babbage-001", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("curie-instruct-beta", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("curie", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("text-davinci-002", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
        //service.createCompletion("audio-transcribe-001", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});

     //   System.out.println(storyArray);

 */


//   ****************************************************************************************************************************/


