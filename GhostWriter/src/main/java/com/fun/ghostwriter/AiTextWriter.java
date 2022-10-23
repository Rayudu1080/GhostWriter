package com.fun.ghostwriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;

@RestController
public class AiTextWriter {

	private ArrayList<CompletionChoice> getGhostText(String ghostPrompt) {
		System.out.println("Welcome to GhostWriter\n\n\n");




		String token = System.getenv("OpenAiApiKey");
		OpenAiService service = new OpenAiService(token);

        Engine davinci = service.getEngine("davinci");


        	ArrayList<CompletionChoice> storyArray = new ArrayList<>();

        	CompletionRequest completionRequest = CompletionRequest.builder()
									                .prompt(ghostPrompt)
									                .temperature(0.9)
									                .maxTokens(1200)
									                .topP(1.0)
									                .frequencyPenalty(0.0)
									                .presencePenalty(0.3)
									                .echo(true)
									                .build();
        	service.createCompletion("davinci", completionRequest).getChoices().forEach(line -> {storyArray.add(line);});
		return storyArray;
		
	}
	
	@RequestMapping("/home")
//	public List<ArrayList<CompletionChoice>> sample() {
	public String sample() {
		
		System.out.println("Hello Y'all!!! Welcome to Ghost Writer-AI with GPT-3 support, powered by OpenAi \n\n\n");

		String ghostPrompt = "Electric Vehicles Ford ";
		

		return getGhostText(ghostPrompt).toString();


	}
}

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


