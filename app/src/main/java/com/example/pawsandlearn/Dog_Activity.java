package com.example.pawsandlearn;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class DogBreed {
    String name;
    String imageUrl;
    String breedGroup;
    String lifeSpan;
    String origin;
    Bitmap image;

    public DogBreed(String name, String imageUrl, String breedGroup, String lifeSpan, String origin) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.breedGroup = breedGroup;
        this.lifeSpan = lifeSpan;
        this.origin = origin;
    }
}

public class Dog_Activity extends AppCompatActivity {
    private DogBreedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);

        ListView listView = findViewById(R.id.dogListView);
        adapter = new DogBreedAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        new FetchDogBreedsTask().execute();
    }

    private class FetchDogBreedsTask extends AsyncTask<Void, Void, List<DogBreed>> {
        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            List<DogBreed> breeds = new ArrayList<>();
            try {
                URL url = new URL("https://api.thedogapi.com/v1/breeds");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("x-api-key", "live_stAtLKSQnzXGRwD20nAN4hFSUC10a2n8zSy7goAoCoFgVp3y3GnOFyjWbbKfA4Vu");

                InputStream inputStream = connection.getInputStream();
                StringBuilder response = new StringBuilder();
                int data;
                while ((data = inputStream.read()) != -1) {
                    response.append((char) data);
                }
                inputStream.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);

                    String name = item.getString("name");
                    String imageUrl = item.has("image") ? item.getJSONObject("image").getString("url") : null;
                    String breedGroup = item.optString("breed_group", "Unknown");
                    String lifeSpan = item.optString("life_span", "Unknown");
                    String origin = item.optString("origin", "Unknown");

                    breeds.add(new DogBreed(name, imageUrl, breedGroup, lifeSpan, origin));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return breeds;
        }

        @Override
        protected void onPostExecute(List<DogBreed> breeds) {
            adapter.clear();
            adapter.addAll(breeds);
        }
    }
}
