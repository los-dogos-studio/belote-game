package com.github.konstantinevashalomidze.belotegame.tamashi;

import static com.github.konstantinevashalomidze.belotegame.tamashi.BelotisCxadeba.*;
import static com.github.konstantinevashalomidze.belotegame.tamashi.Ranki.DAMA;
import static com.github.konstantinevashalomidze.belotegame.tamashi.Ranki.KAROLI;

public class BelotisMayurebeli {
    private final Cveti koziriCveti;
    private Motamashe belotiVisacYavs;
    private boolean belotiNacxadebia;
    private boolean rebelotiNacxadebia;

    public BelotisMayurebeli(Cveti koziriCveti) {
        this.koziriCveti = koziriCveti;
    }

    public void belotiAnRebelotiCxadda(Motamashe motamashe, Karti karti, BelotisCxadeba belotisCxadeba) {
        if (belotisCxadeba == ARAA_NACXADEBI) {
            return;
        }

        if (karti.cveti() != koziriCveti ||
                (karti.ranki() != KAROLI && karti.ranki() != DAMA)) {
            throw new IllegalArgumentException("ბელოტს რო აცხადებ უნდა ჩამოხვიდე ან დამა ან კაროლი");
        }

        if (belotisCxadeba == BELOTIA_NACXADEBI) {
            if (belotiNacxadebia) {
                throw new IllegalStateException("ბელოტი ხო აცხადე უკვე");
            }
            belotiVisacYavs = motamashe;
            belotiNacxadebia = true;
        }

        if (belotisCxadeba == REBELOTIA_NACXADEBI) {
            if (rebelotiNacxadebia) {
                throw new IllegalStateException("რებელოტი ხო აცხადე უკვე");
            }
            if(!belotiNacxadebia) {
                throw new IllegalStateException("ბელოტი არ გიცხადებია ჯერ");
            }
            rebelotiNacxadebia = true;
        }
    }


    public boolean belotRebelotiArisNatamashebi() {
        return belotiNacxadebia && rebelotiNacxadebia;
    }
    public Motamashe belotiVisacYavs() {
        return belotiVisacYavs;
    }

    public int belotisQula() {
        return belotRebelotiArisNatamashebi() ? 20 : 0;
    }
}
