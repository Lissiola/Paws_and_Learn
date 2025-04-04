package com.example.pawsandlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.squareup.picasso.Picasso;

import java.util.List;

// Adapter class to bind dog breed data to ListView items
public class DogBreedAdapter extends BaseAdapter {
    private final List<DogBreed> dogs; // List of DogBreed objects to display
    private final Context context; // Context in which the adapter operates (e.g., Activity)

    // Constructor to initialize the adapter with the context and list of dog breeds
    public DogBreedAdapter(Context context, List<DogBreed> dogs) {
        this.context = context;
        this.dogs = dogs;
    }

    @Override
    public int getCount() {
        return dogs.size(); // Returns the number of items (dog breeds) in the list
    }

    @Override
    public Object getItem(int i) {
        return dogs.get(i); // Returns the DogBreed object at position i
    }

    @Override
    public long getItemId(int i) {
        return i; // Returns the ID of the item (used for identifying rows)
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Inflates the view for each list item, if not already inflated
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.list_item_dog, viewGroup, false);

        // Retrieves the DogBreed object at the current position
        DogBreed dog = dogs.get(i);

        // Finds the TextViews and ImageView from the inflated view
        TextView nameTextView = view.findViewById(R.id.breedName);
        TextView breedGroup = view.findViewById(R.id.breedGroup);
        TextView originTextView = view.findViewById(R.id.origin);
        TextView lifeSpanTextView = view.findViewById(R.id.lifeSpan);
        ImageView imageView = view.findViewById(R.id.breedImage);

        // Sets the text for each TextView based on the DogBreed object
        nameTextView.setText("Breed: " + dog.name);
        breedGroup.setText("BreedGroup: " + dog.breedGroup);
        lifeSpanTextView.setText("Lifespan: " + dog.lifeSpan);
        // Only show the origin if it’s not unknown or empty
        if (dog.origin.equals("Unknown") || dog.origin.isEmpty()) {
            originTextView.setText("");
        } else {
            originTextView.setText("Origin: " + dog.origin);
        }

        // Loads and displays the dog image if available using Picasso
        if (dog.imageUrl != null && !dog.imageUrl.isEmpty()) {
            Picasso.get().load(dog.imageUrl).into(imageView);
        }

        // Sets an OnClickListener to show a dialog with more details when the item is clicked
        view.setOnClickListener(v -> showPopupDialog(dog));

        return view; // Returns the view to be displayed in the ListView
    }

    // Method to display a popup dialog with more detailed information about a dog breed
    void showPopupDialog(DogBreed dog) {
        // Inflates the custom layout for the popup dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.popup, null);

        // Finds the TextViews and ImageView from the dialog layout
        TextView popWeight = dialogView.findViewById(R.id.popWeight);
        TextView popHeight = dialogView.findViewById(R.id.popHeight);
        TextView popName = dialogView.findViewById(R.id.popName);
        TextView popBredFor = dialogView.findViewById(R.id.popBredFor);
        TextView popLifeSpan = dialogView.findViewById(R.id.popLifeSpan);
        TextView popTemperament = dialogView.findViewById(R.id.popTemperament);
        TextView popOrigin = dialogView.findViewById(R.id.popOrigin);
        ImageView popImageUrl = dialogView.findViewById(R.id.popImageUrl);

        // Sets the text for the dialog based on the DogBreed object
        popWeight.setText("Weight: " + dog.weight);
        popHeight.setText("Height: " + dog.height);
        popName.setText("Breed: " + dog.name);
        popBredFor.setText("Bred For: " + dog.bredFor);
        popLifeSpan.setText("Life Span: " + dog.lifeSpan);
        popTemperament.setText("Temperament: " + dog.temperament);
        popOrigin.setText("Origin: " + dog.origin);

        // Loads and displays the dog image in the popup dialog if available
        if (dog.imageUrl != null && !dog.imageUrl.isEmpty()) {
            Picasso.get().load(dog.imageUrl).into(popImageUrl);
        }

        // Creates and shows the AlertDialog to display the detailed information
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView) // Sets the custom view for the dialog
                .create(); // Creates the dialog
        dialog.show(); // Shows the dialog
    }
}
