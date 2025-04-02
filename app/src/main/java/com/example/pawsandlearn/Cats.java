package com.example.pawsandlearn;

import java.util.Objects;

public class Cats {
    String breed;
    String origin;
    String lifespan;
    String picURL;

    public Cats(String breed, String origin, String lifespan, String picURL) {
        this.breed = breed;
        this.origin = origin;
        this.lifespan = lifespan;
        this.picURL = picURL;
    }

    @Override
    public String toString() {
        return "Cats{" +
                "breed='" + breed + '\'' +
                ", origin=" + origin +
                ", lifespan=" + lifespan +
                ", picURL=" + picURL +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(breed, origin, lifespan, picURL);
    }
}
