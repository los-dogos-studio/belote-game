package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Xeli {
    private final List<Karti> kartebi = new ArrayList<>();

    public void daimate(Karti karti) {
        kartebi.add(karti);
    }

    public void moishore(Karti karti) {
        if (!kartebi.remove(karti)) {
            throw new IllegalArgumentException("Karti ar aris xelshi: " + karti);
        }
    }

    public List<Karti> kartebi() {
        return Collections.unmodifiableList(kartebi);
    }

    boolean sheicavs(Karti karti) {
        return kartebi.contains(karti);
    }

    public boolean sheicavsCvets(Cveti cveti) {
        return kartebi.stream().anyMatch(karti -> karti.cveti().equals(cveti));
    }

    public int raodenba() {
        return kartebi.size();
    }

}
