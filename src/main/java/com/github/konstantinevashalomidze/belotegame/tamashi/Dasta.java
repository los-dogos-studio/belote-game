package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dasta {
    private final List<Karti> kartebi;


    public Dasta() {
        kartebi = new ArrayList<>();
        for (var cveti : Cveti.values()) {
            for (var ranki : Ranki.values()){
                kartebi.add(new Karti(cveti, ranki));
            }
        }
    }


    public void achexe() {
        Collections.shuffle(kartebi);
    }

    public Karti ertisKartisAmogheba() {
        if (kartebi.isEmpty()) throw new IllegalStateException("Deck is empty");
        return kartebi.removeLast();
    }

    public int raodenoba() {
        return kartebi.size();
    }


}


