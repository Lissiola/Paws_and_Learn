package com.example.pawsandlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatsAdapter extends BaseAdapter {
    ArrayList<Cats> cats;
    Context context;

    public CatsAdapter(ArrayList<Cats> cats, Context context) {
        this.cats = cats;
        this.context = context;
    }


    @Override
    public int getCount() {
        return cats.size();
    }

    @Override
    public Object getItem(int i) {
        return cats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.cats_list_layout, viewGroup, false);

        Cats cat = cats.get(i);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvOrigin = view.findViewById(R.id.tvOrigin);
        TextView tvLifespan = view.findViewById(R.id.tvLifespan);
        ImageView ivCatPic = view.findViewById(R.id.ivCatPic);


        tvName.setText("Breed: " + cat.breed);
        tvOrigin.setText("Origin: " + cat.origin);
        tvLifespan.setText("Lifespan: " + cat.lifespan);
        Picasso.get().load(cat.picURL).into(ivCatPic); // that’s literally it!


        return view;
    }
}
