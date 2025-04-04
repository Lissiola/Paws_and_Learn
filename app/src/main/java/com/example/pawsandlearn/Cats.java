package com.example.pawsandlearn;

import java.util.Objects;

public class Cats {
    String breed;
    String origin;
    String lifespan;
    String picURL;
    String weight;
    String temperament;
    String description;
    String wiki;

    public Cats(String breed, String origin, String lifespan, String picURL, String weight, String temperment, String description, String wiki) {
        this.breed = breed;
        this.origin = origin;
        this.lifespan = lifespan;
        this.picURL = picURL;
        this.weight = weight;
        this.temperament = temperment;
        this.description = description;
        this.wiki = wiki;
    }

    @Override
    public String toString() {
        return "Cats{" +
                "breed='" + breed + '\'' +
                ", origin=" + origin +
                ", lifespan=" + lifespan +
                ", picURL=" + picURL +
                ", weight=" + weight +
                ", temperament=" + temperament +
                ", description=" + description +
                ", wiki=" + wiki +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(breed, origin, lifespan, picURL, weight, temperament, description, wiki);
    }
}
