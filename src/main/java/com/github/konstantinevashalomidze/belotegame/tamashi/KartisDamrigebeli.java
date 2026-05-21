package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.List;

public class KartisDamrigebeli {
    private final List<Motamashe> motamasheebi; // saatis isris mimart. indexi 0-3
    private final int kartisDamrigebelisIndexi; // romelia dileri am raundshi

    public KartisDamrigebeli(List<Motamashe> motamasheebi, int kartisDamrigebelisIndexi) {
        this.motamasheebi = motamasheebi;
        this.kartisDamrigebelisIndexi = kartisDamrigebelisIndexi;
    }

    public KartisDarigebisShedegi pirveladDaarige() {
        Dasta dasta = new Dasta();
        dasta.achexe();

        for (int step = 1; step <= 4; step++) {
            int motamashisIndexi = (kartisDamrigebelisIndexi + step) % 4;
            for (int micema = 0; micema < 5; micema++) {
                motamasheebi.get(motamashisIndexi).xeli().daimate(dasta.ertisKartisAmogheba());
            }
        }
        Karti amotrialebuliKarti = dasta.ertisKartisAmogheba();

        return new KartisDarigebisShedegi(dasta, amotrialebuliKarti);
    }

}
