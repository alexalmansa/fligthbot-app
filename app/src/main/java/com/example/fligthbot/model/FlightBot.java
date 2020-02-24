package com.example.fligthbot.model;

import com.neovisionaries.i18n.CountryCode;

public class FlightBot {

    private Context context;
    private WordList wordList;
    private Suggestions suggestions;


    public FlightBot() {
        context = Context.WELCOME;
        wordList = new WordList();
        suggestions = new Suggestions();
    }

    public boolean saveSuggestions(String question) {
        CountryCode cc = (CountryCode) CountryCode.findByName(question);
        if (cc != null) {
            suggestions.getCountries().add(cc.getName());
            return true;
        }// cal guardar les altres tipus de suggerencies

        return false;
    }

    public String getResponse(String question) {
        String[] splitted = question.split("\\s+");

        for (String word : splitted) {
            Context actual = context.wordExists(word);//tractarem el string per fer la similaritat, amb una altra lliberia
            if (actual != null) {
                /*if (actual.getNextContext() == null) { //ultima, BYE
                    System.out.println("stoppìng...");
                }*/

                ResponseData data = actual.getBotResponse(question);

                if (data.getCity() != null && !data.getCity().isEmpty()) {
                    suggestions.getCities().add(data.getCity());
                    suggestions.getCountries().add(data.getCountry());
                }

                return data.getResponse();
            }
        }

        return BotResponse.UNKNOWN.getResponse(BotResponse.UNKNOWN, question).getResponse();
    }
}
