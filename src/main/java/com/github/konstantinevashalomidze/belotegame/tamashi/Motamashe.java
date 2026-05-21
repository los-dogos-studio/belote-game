package com.github.konstantinevashalomidze.belotegame.tamashi;

public class Motamashe {

    private final int pozicia; // 0, 1, 2, 3 saatis isris mimartulebit
    private final Xeli xeli = new Xeli();

    public Motamashe(int pozicia) {
        this.pozicia = pozicia;
    }

    public int pozicia() {
        return pozicia;
    }

    public Xeli xeli() {
        return xeli;
    }

}
