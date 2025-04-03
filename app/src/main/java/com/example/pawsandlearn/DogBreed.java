package com.example.pawsandlearn;

import java.util.Objects; // Importing the Objects class for utility methods like hashCode()

// DogBreed class holds information about a dog breed
public class DogBreed {
    // Instance variables to store dog breed attributes
    String weight; // Weight of the dog
    String height; // Height of the dog
    String name; // Name of the dog breed
    String bredFor; // Purpose the dog was bred for (e.g., herding, guarding)
    String breedGroup; // Group to which the breed belongs (e.g., working, sporting)
    String lifeSpan; // Life span of the breed
    String temperament; // Temperament of the breed (e.g., friendly, aggressive)
    String origin; // The origin or country of the breed
    String imageUrl; // URL to an image of the breed

    // Constructor to initialize all instance variables with passed values
    public DogBreed(String weight, String height, String name, String bredFor, String breedGroup, String lifeSpan, String temperament, String origin, String imageUrl) {
        this.weight = weight; // Initialize weight
        this.height = height; // Initialize height
        this.name = name; // Initialize name
        this.bredFor = bredFor; // Initialize bredFor
        this.breedGroup = breedGroup; // Initialize breedGroup
        this.lifeSpan = lifeSpan; // Initialize lifeSpan
        this.temperament = temperament; // Initialize temperament
        this.origin = origin; // Initialize origin
        this.imageUrl = imageUrl; // Initialize imageUrl
    }

    // Override the toString method to return a string representation of the DogBreed object
    @Override
    public String toString() {
        return "DogBreed{" +
                "name='" + name + '\'' + // Includes the name of the breed
                ", weight='" + weight + '\'' + // Includes the weight
                ", height='" + height + '\'' + // Includes the height
                ", bredFor='" + bredFor + '\'' + // Includes the bredFor
                ", breedGroup='" + breedGroup + '\'' + // Includes the breedGroup
                ", lifeSpan='" + lifeSpan + '\'' + // Includes the lifeSpan
                ", temperament='" + temperament + '\'' + // Includes the temperament
                ", origin='" + origin + '\'' + // Includes the origin
                ", imageUrl='" + imageUrl + '\'' + // Includes the image URL
                '}';
    }

    // Override the hashCode method to generate a hash code for DogBreed objects
    @Override
    public int hashCode() {
        // Generate a hash code based on all attributes of the DogBreed object
        return Objects.hash(weight, height, name, bredFor, breedGroup, lifeSpan, temperament, origin, imageUrl);
    }
}
