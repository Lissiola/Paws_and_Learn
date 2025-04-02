package com.example.pawsandlearn;

import java.util.Objects;

public class DogBreed {
    String name;
    String imageUrl;
    String breedGroup;
    String lifeSpan;
    String origin;
    String referenceImageID;

    public DogBreed(String name, String imageUrl, String breedGroup, String lifeSpan, String origin, String referenceImageID) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.breedGroup = breedGroup;
        this.lifeSpan = lifeSpan;
        this.origin = origin;
        this.referenceImageID = referenceImageID;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "breed='" + breedGroup + '\'' +
                ", origin=" + origin +
                ", lifespan=" + lifeSpan +
                ", imageURL=" + imageUrl +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(breedGroup, origin, lifeSpan, imageUrl);
    }
}
