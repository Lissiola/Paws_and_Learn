package com.example.pawsandlearn;

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

public class QuizActivity extends AppCompatActivity {
    RequestQueue queue;
    ArrayList<JSONObject> catBreeds, dogBreeds, questions;
    int qCount=0;
    ImageView imgBreed;
    TextView txtResult;
    Button btnRed, btnBlue, btnYellow, btnGreen;
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
        dogBreeds=new ArrayList<>();
        catBreeds=new ArrayList<>();
        questions = new ArrayList<>();

        imgBreed = findViewById(R.id.imgBreed);
        txtResult = findViewById(R.id.txtResult);
        btnRed = findViewById(R.id.btnRed);
        btnBlue = findViewById(R.id.btnBlue);
        btnYellow = findViewById(R.id.btnYellow);
        btnGreen = findViewById(R.id.btnGreen);

        queue = Volley.newRequestQueue(this);
        String url1  = "https://api.thedogapi.com/v1/breeds?api_key=live_ZTcjNqmEYLr05bs1a5FVB3bXD5geERNEhKgHLiDTgpg5dNOXQSEjvTtARtKYZeM2";
        String url2 = "https://api.thecatapi.com/v1/breeds?api_key=live_VlMdBZ0vyekrJGwlOn2X79pqhW2X6O2vhOeWpM77dRWjtRwITvhlun6ZMBsZHGCX";

        JsonArrayRequest dogRequest = new JsonArrayRequest(Request.Method.GET, url1, null, response -> {
            System.out.println(response);
            try {
                dogBreeds.clear();
                JSONArray res=(JSONArray) response;
                for(int i=0;i<res.length();i++){
                    JSONObject dogObj = response.getJSONObject(i);
                    String dogName = dogObj.getString("name");
                    String dogURL = response.getJSONObject(i).getJSONObject("image").getString("url");

                    JSONObject dogData = new JSONObject();
                    dogData.put("name", dogName);
                    dogData.put("image", dogURL);

                    dogBreeds.add(dogData);
                }
                start();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            System.out.println(error);
        });
        JsonArrayRequest catRequest = new JsonArrayRequest(Request.Method.GET, url2, null, response -> {
            System.out.println(response);
            try {
                catBreeds.clear();
                JSONArray res=(JSONArray) response;
                for(int i=0;i<res.length();i++){
                    if(i==30||i==41){
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
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            System.out.println(error);
        });

        queue.add(dogRequest);
        queue.add(catRequest);
    }

    private void start(){
        if(dogBreeds.size()<5||catBreeds.size()<5){
            return;
        }

        questions.clear();
        //loops 5 times adding 5 dog breeds to questions array list
        for(int i=0; i<5; i++){
            Random random = new Random();
            int randInd = random.nextInt(dogBreeds.size());
            //int randInd = (int) (Math.random() * dogBreeds.size());
            //questions.add(dogBreeds.get(randInd));
            questions.add(dogBreeds.get(randInd)); //adds a random index of the dog breeds to the questions array list
        }

        //loops 5 times adding 5 cat breeds to questions array list
        for(int i=0; i<5; i++){
            Random random = new Random();
            int randInd = random.nextInt(catBreeds.size());
            //int randInd = (int) (Math.random() * dogBreeds.size());
            //questions.add(dogBreeds.get(randInd));
            questions.add(catBreeds.get(randInd)); //adds a random index of the dog breeds to the questions array list
        }

        //do the same for cat breeds
        //note: we do not need to shuffle the different breeds and orders around since they are already randomly picked
        System.out.println("Questions size: " + questions.size());
        nextQ();
    }

    private void nextQ(){
        try{
            JSONObject currentQ = questions.get(qCount);
            String name = currentQ.getString("name");
            String imageURL = currentQ.getString("image");
            Picasso.get().load(imageURL).into(imgBreed);

            String[] multChoice = new String[4];
            //int rightAnswer = (int)(Math.random()*4);
            Random random = new Random();
            int rightAnswer = random.nextInt(4);
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

                while(true){
                    Random random2 = new Random();
                    int randomIndex = random2.nextInt(breedList.size());
                    //int randomIndex = (int)(Math.random()*breedList.size());
                    String randomBreed = breedList.get(randomIndex).getString("name");
                    if (!randomBreed.equals(name)) {
                        multChoice[i] = randomBreed;
                        break;
                    }
                }
            }
            btnRed.setText(multChoice[0]);
            btnBlue.setText(multChoice[1]);
            btnYellow.setText(multChoice[2]);
            btnGreen.setText(multChoice[3]);

            // Button click logic
            btnRed.setOnClickListener(v -> checkAnswer(multChoice[0], name));
            btnBlue.setOnClickListener(v -> checkAnswer(multChoice[1], name));
            btnYellow.setOnClickListener(v -> checkAnswer(multChoice[2], name));
            btnGreen.setOnClickListener(v -> checkAnswer(multChoice[3], name));

        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }

    private void checkAnswer(String selected, String correct) {
        if (selected.equals(correct)) {
            txtResult.setText("Correct!");
            txtResult.setTextColor(Color.GREEN);
            System.out.print("Correct!");
        } else {
            txtResult.setText("Wrong! It was " + correct);
            txtResult.setTextColor(Color.RED);
            System.out.println("Incorrect! It was" + correct);
        }

        qCount++;
        if(qCount<10) {
            nextQ();
        } else {
            txtResult.setText("Quiz Completed!");
            txtResult.setTextColor(Color.MAGENTA);
            finish();
        }
    }

    private void prevClick(View v){
        finish();
    }

    /*
    private void yellowClick(View v){
        checkAnswer(multChoice[2], name);
    }

    private void greenClick(View v){
        checkAnswer(multChoice[3], name);
    }

    private void blueClick(View v){
        checkAnswer(multChoice[1], name);
    }

    private void redClick(View v){
        checkAnswer(multChoice[0], name);
    }

    private void prevClick(View v){
        finish();
    }

     */

}