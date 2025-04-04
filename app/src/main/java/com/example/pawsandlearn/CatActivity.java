package com.example.pawsandlearn;

import android.os.Bundle;
import android.widget.ListView;

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

public class CatActivity extends AppCompatActivity {
    RequestQueue queue;
    ArrayList<JSONObject> catBreeds;
    ArrayList<Cats> cats;
    CatsAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cat_activiy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);

        cats = new ArrayList<>();
        adapter = new CatsAdapter(cats,this);

        listView.setAdapter(adapter);

        catBreeds=new ArrayList<>();

        //make json request
        queue = Volley.newRequestQueue(this);
        String url="https://api.thecatapi.com/v1/breeds?api_key=live_VlMdBZ0vyekrJGwlOn2X79pqhW2X6O2vhOeWpM77dRWjtRwITvhlun6ZMBsZHGCX";
        JsonArrayRequest r = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            System.out.println(response);
            try {
                catBreeds.clear();
                JSONArray array=response;
                System.out.println();

                for(int i=0;i<array.length();i++) {

                    //api does not have index 30 or 41, so skip
                    if(i==30||i==41)
                        continue;
                    // add the JSONObjects to the catBreeds arrayList
                    String breed = response.getJSONObject(i).getString("name");
                    String origin = response.getJSONObject(i).getString("origin");
                    String lifespan = response.getJSONObject(i).getString("life_span");
                    String picURL = response.getJSONObject(i).getJSONObject("image").getString("url") ;
                    String weight = response.getJSONObject(i).getJSONObject("weight").getString("imperial");
                    String temperament = response.getJSONObject(i).getString("temperament");
                    String description = response.getJSONObject(i).getString("description");
                    String wiki = response.getJSONObject(i).getString("wikipedia_url");

                    Cats u = new Cats (breed,origin,lifespan, picURL, weight, temperament, description, wiki);
                    cats.add(u);
                    catBreeds.add((JSONObject) array.get(i));

                }
                // accessing a specific element
                System.out.println("First cat breed: "+catBreeds.get(0).getJSONObject("image").getString("url"));
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            System.out.println(error);
        });

        queue.add(r);
    }
}