package com.github.konstantinevashalomidze.belotegame.tamashi;

import static com.github.konstantinevashalomidze.belotegame.tamashi.RaundisShedegi.ShedegisTipi.*;

public record RaundisShedegi(
        ShedegisTipi tipi,
        int mokozirisQulebi,
        int mowinaaghmdegisQulebi
) {
    public enum ShedegisTipi {
        CHVEULEBRIVI, CHAVARDNA, KAPO, FRE
    }

    public static RaundisShedegi kapo(int vincChaijvaMagisQula) {
        return new RaundisShedegi(KAPO, 250, vincChaijvaMagisQula);
    }

    public static RaundisShedegi fre() {
        return new RaundisShedegi(FRE, 0, 0);
    }

    public static RaundisShedegi chveulebrivi(int mokozirisQulebi, int mowinaaghmdegisQulebi) {
        return new RaundisShedegi(CHVEULEBRIVI, mokozirisQulebi, mowinaaghmdegisQulebi);
    }

    public static RaundisShedegi chavardna(int mowinaaghmdegeGundisQula) {
        return new RaundisShedegi(CHAVARDNA, -50, mowinaaghmdegeGundisQula);
    }
}
