package com.example.pawsandlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DogBreedAdapter extends BaseAdapter {
    private final List<DogBreed> dogs;
    private final Context context;

    public DogBreedAdapter(Context context, List<DogBreed> dogs) {
        this.context = context;
        this.dogs = dogs;
    }

    @Override
    public int getCount() {
        return dogs.size();
    }

    @Override
    public Object getItem(int i) {
        return dogs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.list_item_dog, viewGroup, false);

        DogBreed dog = dogs.get(i);

        TextView nameTextView = view.findViewById(R.id.breedName);
        TextView breedGroup = view.findViewById(R.id.breedGroup);
        TextView originTextView = view.findViewById(R.id.origin);
        TextView lifeSpanTextView = view.findViewById(R.id.lifeSpan);
        ImageView imageView = view.findViewById(R.id.breedImage);

        nameTextView.setText("Breed: " + dog.name);
        breedGroup.setText("BreedGroup: " + dog.breedGroup);
        lifeSpanTextView.setText("Lifespan: " + dog.lifeSpan);
        if (dog.origin == "Unknown" || dog.origin == "") {
            originTextView.setText("");
        } else {
            originTextView.setText("Origin: " + dog.origin);
        }
        if (dog.imageUrl != null && !dog.imageUrl.isEmpty()) {
            Picasso.get().load(dog.imageUrl).into(imageView);
        }

        return view;
    }
}
