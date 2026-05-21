package com.github.konstantinevashalomidze.belotegame.tamashi;

public enum Ranki {
    SHVIDI, RVA, CXRA, ATI, VALETI, DAMA, KAROLI, TUZI;


    public int nomeri() {
        return switch (this) {
            case SHVIDI -> 0;
            case RVA -> 1;
            case CXRA -> 2;
            case ATI -> 3;
            case VALETI -> 4;
            case DAMA -> 5;
            case KAROLI -> 6;
            case TUZI -> 7;
        };
    }

}
