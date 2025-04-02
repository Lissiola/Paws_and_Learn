package com.example.pawsandlearn;

import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.pawsandlearn.databinding.ActivityPetQuizBinding;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PetQuizActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    //private ActivityPetQuizBinding binding;
    RequestQueue queue;
    ArrayList<JSONObject> catBreeds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
     //binding = ActivityPetQuizBinding.inflate(getLayoutInflater());
     //setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pet_quiz);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });*/
        queue = Volley.newRequestQueue(this);
        String url1  = "https://api.thedogapi.com/v1/breeds?api_key=live_ZTcjNqmEYLr05bs1a5FVB3bXD5geERNEhKgHLiDTgpg5dNOXQSEjvTtARtKYZeM2";
        String url2 = "https://api.thecatapi.com/v1/breeds?api_key=live_VlMdBZ0vyekrJGwlOn2X79pqhW2X6O2vhOeWpM77dRWjtRwITvhlun6ZMBsZHGCX";
        JsonArrayRequest r = new JsonArrayRequest(Request.Method.GET, url1, null, response -> {
            System.out.println(response);
            try {
                JSONArray att = (JSONArray)(response.get("attachments"));
                System.out.println( ((JSONObject)att.get(0)).get("text"));
                String joke = (String) ((JSONObject)att.get(0)).get("text");
                TextView tv = findViewById(R.id.txtJoke);
                tv.setText(joke);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            System.out.println(error);
        });


        queue.add(r);
    }
    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pet_quiz);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

     */
}