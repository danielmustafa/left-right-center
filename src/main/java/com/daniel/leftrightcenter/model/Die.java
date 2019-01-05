package com.daniel.leftrightcenter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Die {

    public enum Face {
        R,
        L,
        C,
        DOT
    }

    private Face[] faces = {
            Face.R,
            Face.L,
            Face.C,
            Face.DOT,
            Face.DOT,
            Face.DOT
    };

    private Random random;

    public Die(){
        this.random = new Random();
    }

    public Face roll(){
        return faces[random.nextInt(faces.length-1)];
    }

    public List<Face> roll(int numTimes){
        List<Face> dieRolls = new ArrayList<>();
        for (int i = 0; i < numTimes; i++) {
            dieRolls.add(roll());
        }
        return dieRolls;
    }
}
