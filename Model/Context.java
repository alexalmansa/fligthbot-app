package Model;

import static Model.BotResponse.*;

public enum Context {

    WELCOME(null, null, SALUTE, new WordList("hey", "hi","hello")),
    BYE(null, null, GOODBYE, new WordList("bye", "goodbye")),
    GRATITUDE(null, null,THANKS, new WordList("thanks", "thankyou")),
    YRESP(null, null,YRESPONSE, new WordList("yes", "great")),
    NRESP(null, null,NRESPONSE, new WordList("no", "no")),
    QUESTIONS(null, null,QUESTION, new WordList("how are you", "how you doing", "what's your name")),
    TRAVELTO(null, null, TRAVEL, new WordList("travel"));


    private WordList wordList;
    private BotResponse botResponse;
    private Context context;
    private Suggestions suggestions;

    Context(Context context, Suggestions suggestions, BotResponse botResponse, WordList wordList) {
        this.context = context;
        this.suggestions = suggestions;
        this.botResponse = botResponse;
        this.wordList = wordList;
    }

    public boolean isWordInList(String word) {
        return wordList.contains(word);
    }

    public WordList getWordList() {
        return wordList;
    }

    public ResponseData getBotResponse(String question) {
        return botResponse.getResponse(this.botResponse,question );
    }

    public Context getNextContext() {
        return context;
    }

    public Suggestions getSuggestions() {
        return suggestions;
    }

    public Context wordExists(String word) {

        for (Context con : Context.values()) {
            if (con.isWordInList(word)) {
                return con;
            }
        }

        return null;

    }

}
