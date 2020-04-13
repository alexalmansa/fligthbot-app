package Model;

import Utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public enum BotResponse {

    SALUTE("Hello! I'm a chatbot that will help you to find a place to travel! Tell me, what do you need", "Hi, how may I assist you?"),
    TRAVEL("Where do you want to go? Maybe ", "I think you could like "),
    TRAVEL2("Where would you like to go?", "You can try telling me a country that you would like to go to"),
    GOODBYE("See you later!", "Bye bye!", "Goodbye, let me know if you need anything else"),
    THANKS("You're Welcome!","I am here to help!"),
    YRESPONSE("Great! Im good at my job :)"),
    NRESPONSE("Humm... tell me again what do you need and I'll try to find something better for you!", "Do you want me to search another city?"),
    QUESTION("I'm a travel boot, we could spend hours talking about other things but that's not my function", "Sorry, I'm not made to answer this kind of questions."),
    UNKNOWN("Sorry, I didn't understand what you said.", "I can't understand this.", "I don't know what you mean.", "Try explaining it with other words please.");


    private String[] botResponses;

    BotResponse(String... botResponses) {
        this.botResponses = botResponses;
    }

    public ResponseData getResponse(BotResponse botResponse, String question) {
        int random = new Random().nextInt(botResponses.length);

        ResponseData response = new ResponseData();

        if (botResponse.equals(TRAVEL)) {

            String destination = destinationFunction("travel", "to",question);
            if (destination.length() == 0){
                destination = destinationFunction("go", "to",question);
                if(destination.length() == 0) {
                    response.setResponse(TRAVEL2.botResponses[random]);
                }
            }
            if (destination.length()!= 0){
                List<String> responses = Utilities.httpRequest(destination);
                response.setResponse(responses.get(0) + " (" + responses.get(1) + ")");
                response.setCity(responses.get(0));
                response.setCountry(responses.get(1));
            }
        } else {
            response.setResponse(botResponses[random]);
        }

        return response;
    }

    private String destinationFunction(String word1, String word2,String question) {
        String destination = "";

            String[] words = question.split(" ");
            if (words.length > 2) {
            int i = 1;
            for (String a : words) {
                if (a.equals(word1) && words[i].equals(word2)) {
                    destination = words[i + 1];
                }
                if (i < words.length - 1) {
                    i++;
                }
            }
            return destination;
        } else {
            return destination;
        }
    }

}
