package com.example.pawsandlearn;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
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

public class Dog_Activity extends AppCompatActivity {
    private RequestQueue queue;
    private ArrayList<JSONObject> dogBreeds;
    private ArrayList<DogBreed> dogs;
    private DogBreedAdapter adapter;
    private ListView listView;
    private static final String URL = "https://api.thedogapi.com/v1/breeds?api_key=live_stAtLKSQnzXGRwD20nAN4hFSUC10a2n8zSy7goAoCoFgVp3y3GnOFyjWbbKfA4Vu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dogListView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.dogListView);
        dogs = new ArrayList<>();
        adapter = new DogBreedAdapter(this, dogs);
        listView.setAdapter(adapter);

        dogBreeds = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        fetchDogBreeds();
    }

    private void fetchDogBreeds() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        dogBreeds.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject item = response.getJSONObject(i);
                            String name = item.getString("name");
                            String origin = item.optString("origin", "Unknown");
                            String breedGroup = item.optString("breed_group", "Unknown");
                            String lifeSpan = item.optString("life_span", "Unknown");
                            String imageUrl = item.has("image") ? item.getJSONObject("image").getString("url") : null;
                            DogBreed breed = new DogBreed(name, imageUrl, breedGroup, lifeSpan, origin, "");
                            dogs.add(breed);
                            dogBreeds.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> System.out.println(error));

        queue.add(request);
    }
}
