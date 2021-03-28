package com.chatbot.Chatbot.chatbot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Service
public class Chatbot {

    private JSONArray intents;

    public void load() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        // load chats.json file
        FileReader reader = new FileReader("data/chats.json");

        // parse json
        Object obj = jsonParser.parse(reader);
        JSONObject chats = (JSONObject) obj;

        // get array of intents
        intents = (JSONArray) chats.get("intents");
    }

    // get response of a asked question
    public String getResponse(String asked) throws IOException, ParseException {

        load();

        // intent grade
        ArrayList<Double> grades = new ArrayList<>();


        for (int i=0; i<intents.size();i++){

            // select each intent
            JSONObject intent = (JSONObject) intents.get(i);

            // get question array of each intent
            JSONArray questions = (JSONArray) intent.get("questions");

            // get best grade from questions array
            grades.add(getGradeFromQuestionList(questions, asked));

        }



        // get index of largest element in an array
        int maxElementIndex = maxElementIndex(grades);

        if (grades.get(maxElementIndex) < 0.26){
            return "Sorry, I don't understand.";
        }

        // get a random answer from correct intent
        return extractResponse(maxElementIndex);

    }

    // get best grade from questions array
    private double getGradeFromQuestionList(JSONArray questions, String asked){

        double bestGrade = 0;
        double currentGrade = 0;

        for (int j=0; j<questions.size();j++){

            // for each question
            String question = (String) questions.get(j);

            // get word array question
            ArrayList<String> questionWords = sentenceBreaker(question);

            // get word array of asked
            ArrayList<String> askedWords = sentenceBreaker(asked);

            // get current grade from asked word array and question array
            currentGrade = calculateGrade(questionWords, askedWords);

            // set currentGrade to bestGrade if it is greater
            if(currentGrade > bestGrade){
                bestGrade = currentGrade;
            }
        }

        return bestGrade;
    }

    // split a sentence to a word array
    private ArrayList<String> sentenceBreaker(String sentence){
        return (new ArrayList<String>(Arrays.asList(sentence.split("\\s+"))));
    }


    // calculate similarity of two word arrays, and provide a grade for it
    private double calculateGrade(ArrayList<String> questionWords, ArrayList<String> askedWords){
        int matchCount = 0;

        // compare word by word
        for(String x_element : questionWords){
            for(String y_element : askedWords){
                if(x_element.toLowerCase().equals(y_element.toLowerCase())) ++matchCount;
            }
        }

        // value will be between 0 to 1
        return (double) matchCount/questionWords.size();
    }


    // get index of largest element in an array
    private int maxElementIndex(ArrayList<Double> arrayList){
        int maxAt = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            maxAt = arrayList.get(i) > arrayList.get(maxAt) ? i : maxAt;
        }

        return maxAt;
    }

    // extract an answer from a particular intent
    private String extractResponse(int position){
        JSONObject intent = (JSONObject) intents.get(position);
        JSONArray responses = (JSONArray) intent.get("answers");

        Random random = new Random();

        int randomValue = random.nextInt(responses.size());

        return (String) responses.get(randomValue);
    }

}
