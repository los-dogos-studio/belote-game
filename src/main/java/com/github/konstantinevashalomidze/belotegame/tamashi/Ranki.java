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

    public int dzala(boolean koziria) {
        if (koziria) {
            return switch (this) {
                case VALETI -> 7;
                case CXRA -> 6;
                case TUZI -> 5;
                case ATI -> 4;
                case KAROLI -> 3;
                case DAMA -> 2;
                case RVA -> 1;
                case SHVIDI -> 0;
            };
        } else {
            return switch (this) {
                case TUZI -> 7;
                case ATI -> 6;
                case KAROLI -> 5;
                case DAMA -> 4;
                case VALETI -> 3;
                case CXRA -> 2;
                case RVA -> 1;
                case SHVIDI -> 0;
            };
        }
    }

    public int qula(boolean koziria) {
        if (koziria) {
            return switch (this) {
                case VALETI -> 20;
                case CXRA -> 14;
                case TUZI -> 11;
                case ATI -> 10;
                case KAROLI -> 4;
                case DAMA -> 3;
                case RVA -> 0;
                case SHVIDI -> 0;
            };
        } else {
            return switch (this) {
                case TUZI -> 11;
                case ATI -> 10;
                case KAROLI -> 4;
                case DAMA -> 3;
                case VALETI -> 2;
                case CXRA -> 0;
                case RVA -> 0;
                case SHVIDI -> 0;
            };
        }
    }




}
