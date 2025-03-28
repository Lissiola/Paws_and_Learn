package com.example.pawsandlearn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {
    ImageView ivCat, ivDog, ivQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ivCat=findViewById(R.id.ivCat);
        ivDog=findViewById(R.id.ivDog);
        ivQuiz=findViewById(R.id.ivQuiz);
        ivCat.setImageResource(R.drawable.cat_image);
        ivDog.setImageResource(R.drawable.dog_image);
        ivQuiz.setImageResource(R.drawable.quiz_image);
        ivCat.setOnClickListener(view -> {
//            Intent intent=new Intent(this,CatsActivity.class);
//            startActivity(intent);
            System.out.println("Start CatsActivity");
        });
        ivDog.setOnClickListener(view -> {
//            Intent intent=new Intent(this,DogsActivity.class);
//            startActivity(intent);
            System.out.println("Start DogsActivity");
        });
        ivQuiz.setOnClickListener(view -> {
//            Intent intent=new Intent(this,QuizActivity.class);
//            startActivity(intent);
            System.out.println("Start QuizActivity");
        });
    }
}