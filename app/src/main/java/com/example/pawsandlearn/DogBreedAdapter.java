package com.example.pawsandlearn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DogBreedAdapter extends ArrayAdapter<DogBreed> {
    public DogBreedAdapter(Context context, List<DogBreed> breeds) {
        super(context, 0, breeds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_dog, parent, false);
        }

        DogBreed breed = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.breedName);
        TextView breedGroupTextView = convertView.findViewById(R.id.breedGroup);
        TextView lifeSpanTextView = convertView.findViewById(R.id.lifeSpan);
        TextView originTextView = convertView.findViewById(R.id.origin);
        ImageView imageView = convertView.findViewById(R.id.breedImage);

        nameTextView.setText(breed.name);
        breedGroupTextView.setText("Group: " + breed.breedGroup);
        lifeSpanTextView.setText("Lifespan: " + breed.lifeSpan);
        originTextView.setText("Origin: " + breed.origin);

        new ImageLoaderTask(imageView).execute(breed.imageUrl);

        return convertView;
    }

    private static class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public ImageLoaderTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
