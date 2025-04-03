package com.example.pawsandlearn;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.activity.EdgeToEdge; // For enabling edge-to-edge UI
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

// Main Activity for displaying a list of dog breeds
public class Dog_Activity extends AppCompatActivity {
    private RequestQueue queue; // RequestQueue for handling network requests
    private ArrayList<JSONObject> dogBreeds; // List to hold raw JSON data of dog breeds
    private ArrayList<DogBreed> dogs; // List to hold DogBreed objects
    private DogBreedAdapter adapter; // Adapter to display dog breeds in the ListView
    private ListView listView; // ListView for displaying the dog breeds
    private static final String URL = "https://api.thedogapi.com/v1/breeds?api_key=live_stAtLKSQnzXGRwD20nAN4hFSUC10a2n8zSy7goAoCoFgVp3y3GnOFyjWbbKfA4Vu"; // URL to fetch dog breeds data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enables edge-to-edge layout for the activity
        setContentView(R.layout.activity_dog); // Sets the layout for the activity

        // Sets a listener for applying window insets (handles system bars like the status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dogListView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Gets the system bars' insets (e.g., status bar height)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Applies padding based on system bar insets
            return insets; // Returns the insets
        });

        listView = findViewById(R.id.dogListView); // Gets the ListView from the layout
        dogs = new ArrayList<>(); // Initializes the list of DogBreed objects
        adapter = new DogBreedAdapter(this, dogs); // Creates an adapter to display the dog breeds in the ListView
        listView.setAdapter(adapter); // Sets the adapter for the ListView

        dogBreeds = new ArrayList<>(); // Initializes the list for raw JSON objects
        queue = Volley.newRequestQueue(this); // Initializes the request queue for network requests
        fetchDogBreeds(); // Fetches the dog breeds data from the API
    }

    // Method to fetch dog breeds from the API
    private void fetchDogBreeds() {
        // Creates a JsonArrayRequest to get the data from the URL
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> { // Success response handler
                    try {
                        dogBreeds.clear(); // Clears the list before adding new data
                        // Loops through the response array to process each breed
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject item = response.getJSONObject(i); // Gets each JSON object (breed)
                            // Extracts properties of the dog breed, with default values if the fields are missing
                            String weight = item.has("weight") ? item.getJSONObject("weight").getString("imperial") : null;
                            String height = item.has("height") ? item.getJSONObject("height").getString("imperial") : null;
                            String name = item.optString("name", "Unknown");
                            String bredFor = item.optString("bred_for", "Unknown");
                            String breedGroup = item.optString("breed_group", "Unknown");
                            String lifeSpan = item.optString("life_span", "Unknown");
                            String temperament = item.optString("temperament", "Unknown");
                            String origin = item.optString("origin", "Unknown");
                            String imageUrl = item.has("image") ? item.getJSONObject("image").getString("url") : null;

                            // Creates a DogBreed object with the extracted data
                            DogBreed breed = new DogBreed(weight, height, name, bredFor, breedGroup, lifeSpan, temperament, origin, imageUrl);
                            dogs.add(breed); // Adds the DogBreed object to the dogs list
                            dogBreeds.add(item); // Adds the raw JSON object to the dogBreeds list
                        }
                        adapter.notifyDataSetChanged(); // Notifies the adapter that the data has changed, so it can update the view
                    } catch (JSONException e) { // Catches any JSON parsing exceptions
                        throw new RuntimeException(e); // Rethrows the exception as a runtime exception
                    }
                },
                error -> System.out.println(error)); // Error handler to print any network errors

        queue.add(request); // Adds the request to the request queue to be executed
    }
}
