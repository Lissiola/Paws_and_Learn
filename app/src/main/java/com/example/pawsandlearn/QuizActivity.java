package com.example.pawsandlearn;
//IMPORTS
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//Quiz activity!! This activity tests the users on what they learned in previous activities in
// the form of a 10 question quiz, 5 questions about dog breeds, and 10 about cat breed. These are photos randomly
//generated and the user must select the correct multiple choice button for what breed is pictured.
//The score is not kept, this is just for fun to test their knowledge, however it does display whether their answer was
//correct or incorrect, and if it is incorrect then it displays the correct answer in a textView after.
public class QuizActivity extends AppCompatActivity {
    RequestQueue queue; // for API calls
    ArrayList<JSONObject> catBreeds, dogBreeds, questions; //Array lists for all cat breeds, all dog breeds, and the 10 questions (5 of each)
    int qCount=0; //counts index of the questions
    //xml definitions
    ImageView imgBreed;
    TextView txtResult;
    Button btnRed, btnBlue, btnYellow, btnGreen,btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //inializing the array lists
        dogBreeds=new ArrayList<>();
        catBreeds=new ArrayList<>();
        questions = new ArrayList<>();

        //initializing the xml elements
        imgBreed = findViewById(R.id.imgBreed);
        txtResult = findViewById(R.id.txtResult);
        btnRed = findViewById(R.id.btnRed);
        btnBlue = findViewById(R.id.btnBlue);
        btnYellow = findViewById(R.id.btnYellow);
        btnGreen = findViewById(R.id.btnGreen);
        btnPrev=findViewById(R.id.btnPrev);

        btnPrev.setOnClickListener(view -> {
            finish();
        });

        //inializing the volley request queue
        queue = Volley.newRequestQueue(this);
        //storing the api urls
        String url1  = "https://api.thedogapi.com/v1/breeds?api_key=live_ZTcjNqmEYLr05bs1a5FVB3bXD5geERNEhKgHLiDTgpg5dNOXQSEjvTtARtKYZeM2";
        String url2 = "https://api.thecatapi.com/v1/breeds?api_key=live_VlMdBZ0vyekrJGwlOn2X79pqhW2X6O2vhOeWpM77dRWjtRwITvhlun6ZMBsZHGCX";

        //this gets the dog breeds from the api
        JsonArrayRequest dogRequest = new JsonArrayRequest(Request.Method.GET, url1, null, response -> {
            System.out.println(response); //for checking
            try {
                dogBreeds.clear(); //removes old data before adding new
                JSONArray res=(JSONArray) response; //converts response to JSON array
                //loops through each item in the array and adds to JSON dogObject
                for(int i=0;i<res.length();i++){
                    JSONObject dogObj = response.getJSONObject(i);
                    String dogName = dogObj.getString("name"); //gets the dog breed name from object
                    String dogURL = response.getJSONObject(i).getJSONObject("image").getString("url"); //gets image URL from object

                    //creates new object to store the name and url
                    JSONObject dogData = new JSONObject();
                    dogData.put("name", dogName);
                    dogData.put("image", dogURL);

                    //add this new object to array list
                    dogBreeds.add(dogData);
                }
                start(); //calls start method
            } catch (JSONException e) {
                throw new RuntimeException(e); //handles exception
            }

        }, error -> {
            System.out.println(error); //pints errors
        });
        //does the same as the dog but for the cats
        JsonArrayRequest catRequest = new JsonArrayRequest(Request.Method.GET, url2, null, response -> {
            System.out.println(response);
            try {
                catBreeds.clear();
                JSONArray res=(JSONArray) response;
                for(int i=0;i<res.length();i++){
                    if(i==30||i==41){ //added if statement because 30 and 41 is not included in cat api
                        continue;
                    }
                    JSONObject catObj = response.getJSONObject(i);
                    String catName = catObj.getString("name");
                    String catURL = response.getJSONObject(i).getJSONObject("image").getString("url");

                    JSONObject catData = new JSONObject();
                    catData.put("name", catName);
                    catData.put("image", catURL);

                    catBreeds.add(catData);
                }
                System.out.println(Arrays.asList(dogBreeds));
                System.out.println(Arrays.asList(catBreeds));
                start();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            System.out.println(error);
        });

        queue.add(dogRequest);
        queue.add(catRequest);
    }

    //start method called when quiz activity starts(and data is ready)
    private void start(){
        //just returns right away if dogbreed or cat breeds has less than 5 elements (we need 5 of each)
        if(dogBreeds.size()<5||catBreeds.size()<5){
            return;
        }

        questions.clear(); //clears questions array list

        //loops 5 times adding 5 dog breeds to questions array list
        for(int i=0; i<5; i++){
            //generates random number (in scope of dog breed array size)
            Random random = new Random();
            int randInd = random.nextInt(dogBreeds.size());
            questions.add(dogBreeds.get(randInd)); //adds a random index of the dog breeds to the questions array list
        }

        //loops 5 times adding 5 cat breeds to questions array list
        for(int i=0; i<5; i++){
            //generates random number (in scope of cat breed array size)
            Random random = new Random();
            int randInd = random.nextInt(catBreeds.size());
            questions.add(catBreeds.get(randInd)); //adds a random index of the dog breeds to the questions array list
        }

        //note: we do not need to shuffle the different breeds and orders around since they are already randomly picked
        System.out.println("Questions size: " + questions.size()); //just for checking
        //calls next q method
        nextQ();
    }

    //this method generages the next question
    private void nextQ(){
        try{

            JSONObject currentQ = questions.get(qCount); //gets index from questions array list(qcount starts at index 0)
            String name = currentQ.getString("name"); //gets name
            String imageURL = currentQ.getString("image"); //gets image
            Picasso.get().load(imageURL).into(imgBreed); //loads the image using picasso


            String[] multChoice = new String[4]; //multiple choice array to store the options for user to pick
            //creates random number in scope of number of options
            Random random = new Random();
            int rightAnswer = random.nextInt(4);
            //uses the random number to store the right answer in that index (that way the answer isnt always in the same place)
            multChoice[rightAnswer] = name;

            //generates the random mult choice answers for the rest
            for(int i = 0; i<4;i++){
                if(i==rightAnswer){
                    continue; //skips the correct answer so that it doesnt change it
                }
                boolean isDogQ = qCount < 5;
                ArrayList<JSONObject>breedList;
                if(isDogQ){
                    breedList = dogBreeds;
                } else {
                    breedList = catBreeds;
                }
                //assign names to random options in multiple choice array
                while(true){
                    Random random2 = new Random();
                    int randomIndex = random2.nextInt(breedList.size());
                    String randomBreed = breedList.get(randomIndex).getString("name");
                    if (!randomBreed.equals(name)) {
                        multChoice[i] = randomBreed;
                        break;
                    }
                }
            }
            //assigns the answers to the buttons
            btnRed.setText(multChoice[0]);
            btnBlue.setText(multChoice[1]);
            btnYellow.setText(multChoice[2]);
            btnGreen.setText(multChoice[3]);

            //button click listeners calls checkAnswer method
            btnRed.setOnClickListener(v -> checkAnswer(multChoice[0], name));
            btnBlue.setOnClickListener(v -> checkAnswer(multChoice[1], name));
            btnYellow.setOnClickListener(v -> checkAnswer(multChoice[2], name));
            btnGreen.setOnClickListener(v -> checkAnswer(multChoice[3], name));
//exception
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }

    //this method checks if the selected answer is correct or not
    private void checkAnswer(String selected, String correct) {
        if (selected.equals(correct)) {
            txtResult.setText("Correct!");
            txtResult.setTextColor(Color.GREEN);
            System.out.print("Correct!"); //displays green correct if right in the text view
        } else {
            txtResult.setText("Wrong! It was " + correct);
            txtResult.setTextColor(Color.RED);
            System.out.println("Incorrect! It was" + correct); //displays red and the right answer if incorrect in the text view
        }

        //increate question index
        qCount++;
        //ends game when gets to 10
        if(qCount<10) {
            nextQ();
        } else {
            txtResult.setText("Quiz Completed!");
            txtResult.setTextColor(Color.MAGENTA);
            finish();
        }
    }

}