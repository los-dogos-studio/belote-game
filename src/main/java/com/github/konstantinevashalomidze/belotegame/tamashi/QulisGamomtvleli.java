package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.List;

public class QulisGamomtvleli {
    private final Cveti koziriCveti;

    public QulisGamomtvleli(Cveti koziriCveti) {
        this.koziriCveti = koziriCveti;
    }


    public int gamotvale(List<Krugi> krugebi, boolean boloKrugia) {
        int qula = krugebi.stream()
                .flatMap(k -> k.natamashebiKartebi().stream())
                .mapToInt(nk -> kartisQula(nk.karti()))
                .sum();

        if (boloKrugia) {
            qula += 10;
        }

        return qula;
    }

    private int kartisQula(Karti karti) {
        boolean koziria = karti.cveti() == koziriCveti;
        return koziria ? karti.ranki().qula(true) : karti.ranki().qula(false);
    }


}
