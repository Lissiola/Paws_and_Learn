package com.example.pawsandlearn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

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

        //get listview fields
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvOrigin = view.findViewById(R.id.tvOrigin);
        TextView tvLifespan = view.findViewById(R.id.tvLifespan);
        ImageView ivCatPic = view.findViewById(R.id.ivCatPic);

        //set listview info
        tvName.setText("Breed: " + cat.breed);
        tvOrigin.setText("Origin: " + cat.origin);
        tvLifespan.setText("Lifespan: " + cat.lifespan + " years");
        Picasso.get().load(cat.picURL).into(ivCatPic);

        //set onclick listener for each listview element. Call the popup function.
        view.setOnClickListener(v -> showPopupDialog(cat));

        return view;
    }

    void showPopupDialog(Cats cat) {
        // hintlayout is the xml I created for my popup
        // in it you would need to define the layout of the popup
        View dialogView = LayoutInflater.from(context).inflate(R.layout.cat_hintlayout, null);
        // define all of the elements in the xml
        ImageView imageView = dialogView.findViewById(R.id.ivCatHintImage);
        TextView tvBreedHint = dialogView.findViewById(R.id.tvBreedHint);
        TextView tvOriginHint = dialogView.findViewById(R.id.tvOriginHint);
        TextView tvLifespanHint = dialogView.findViewById(R.id.tvLifespanHint);
        TextView tvWeightHint = dialogView.findViewById(R.id.tvWeightHint);
        TextView tvTemperamentHint = dialogView.findViewById(R.id.tvTemperamentHint);
        TextView tvDescriptionHint = dialogView.findViewById(R.id.tvDescriptionHint);
        Button btnWiki = dialogView.findViewById(R.id.btnWiki);

        //put the information in all of the correct fields
        Picasso.get().load(cat.picURL).into(imageView);
        tvBreedHint.setText("Breed: " + cat.breed);
        tvOriginHint.setText("Origin: " + cat.origin);
        tvLifespanHint.setText("Lifespan: " + cat.lifespan + " years");
        tvWeightHint.setText("Weight: " + cat.weight + " pounds");
        tvTemperamentHint.setText("Temperment: " + cat.temperament);
        tvDescriptionHint.setText("Description " + cat.description);

        //onclick for the button
        btnWiki.setOnClickListener(v -> {
            //pass cat wiki url to new intent
            String url = cat.wiki;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });

        //build popup
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();
        dialog.show();
    }
}
