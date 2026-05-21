package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.List;

public class Gundi {
    private final List<Motamashe> motamasheebi;
    private int qula;
    private int gayinuliQula;


    public Gundi(Motamashe motamashe1, Motamashe motamashe2) {
        this.motamasheebi = List.of(motamashe1, motamashe2);
    }



    public void daamateQula(int qula) {
        this.qula += qula;
    }

    public void daamateGayinuliQula(int gayinuliQula) {
        this.gayinuliQula += gayinuliQula;
    }

    public void gayinuliQulisMomateba() {
        this.qula += gayinuliQula;
        gayinuliQula = 0;
    }

    public boolean mogebulia() {
        return qula >= 1_001;
    }

    public boolean sheicavs(Motamashe motamashe) {
        return motamasheebi.contains(motamashe);
    }

    public List<Motamashe> motamasheebi() {
        return motamasheebi;
    }

    public int qula() {
        return qula;
    }

    public int gayinuliQula() {
        return gayinuliQula;
    }



}
