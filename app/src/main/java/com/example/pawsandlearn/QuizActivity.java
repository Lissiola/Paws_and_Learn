package com.example.pawsandlearn;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {
    RequestQueue queue;
    ArrayList<JSONObject> catBreeds, dogBreeds;
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
        queue = Volley.newRequestQueue(this);
        String url1  = "https://api.thedogapi.com/v1/breeds?api_key=live_ZTcjNqmEYLr05bs1a5FVB3bXD5geERNEhKgHLiDTgpg5dNOXQSEjvTtARtKYZeM2";
        String url2 = "https://api.thecatapi.com/v1/breeds?api_key=live_VlMdBZ0vyekrJGwlOn2X79pqhW2X6O2vhOeWpM77dRWjtRwITvhlun6ZMBsZHGCX";

        JsonArrayRequest dogRequest = new JsonArrayRequest(Request.Method.GET, url1, null, response -> {
            System.out.println(response);
            try {
                dogBreeds.clear();
                JSONArray res=(JSONArray) response;
                for(int i=0;i<res.length();i++){
                    dogBreeds.add((JSONObject) res.get(i));
                }
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
                    catBreeds.add((JSONObject) res.get(i));
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
}