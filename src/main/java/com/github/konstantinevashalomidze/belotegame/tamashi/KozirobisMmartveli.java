package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.ArrayList;
import java.util.List;

public class KozirobisMmartveli {

    private final List<Motamashe> motamasheebi;
    private final KozirobisMdgomareoba kozirobisMdgomareoba;

    public KozirobisMmartveli(List<Motamashe> motamasheebi, KozirobisMdgomareoba kozirobisMdgomareoba) {
        this.motamasheebi = motamasheebi;
        this.kozirobisMdgomareoba = kozirobisMdgomareoba;
    }


    public void sheamowmeAmotrialebuliKartiValetze() {
        Motamashe pirveliMotamashe = motamasheebi.get((kozirobisMdgomareoba.kartisDamrigebelisIndexi() + 1) % 4);
        Karti amotrialebuliKarti = kozirobisMdgomareoba.amotrialebuliKarti();
        boolean valetia = pirveliMotamashe.xeli().kartebi().stream()
                .anyMatch(k -> k.cveti() == amotrialebuliKarti.cveti() && k.ranki() == Ranki.VALETI);
        if (valetia) kozirobisMdgomareoba.ikozira(pirveliMotamashe, amotrialebuliKarti.cveti());
    }


    public void motamashemTqvaPasi(Motamashe motamashe) {
        davushvitRomSityvisTqmisUflebaaqvs(motamashe);

        kozirobisMdgomareoba.sityvaShemdegMotamashezeGadavida();

        if (kozirobisMdgomareoba.kozirobisFaza() == KozirobisFaza.PIRVELI_KRUGI
                && kozirobisMdgomareoba.sityvaVinujdenzea()) {
            kozirobisMdgomareoba.shemdegiKrugiDaiwyo();
        }
    }

    public void ikozira(Motamashe motamashe, Cveti cveti) {
        davushvitRomSityvisTqmisUflebaaqvs(motamashe);

        if (cveti == kozirobisMdgomareoba.amotrialebuliKarti().cveti()) {
            throw new IllegalArgumentException("წინა ხელში უნდა გეკოზირა თუ გინდოდა მაგი.");
        }
        kozirobisMdgomareoba.ikozira(motamashe, cveti);
    }


    public void vinujdenmaIkozira(Cveti cveti) {
        Motamashe vinujdeni = motamasheebi.get(kozirobisMdgomareoba.kartisDamrigebelisIndexi());
        if (cveti == kozirobisMdgomareoba.amotrialebuliKarti().cveti()) {
            throw new IllegalArgumentException("თუ გინდოდა წინა კრუგზე წაგეღო კვერცხო.");
        }
        kozirobisMdgomareoba.ikozira(vinujdeni, cveti);
    }

    private void davushvitRomSityvisTqmisUflebaaqvs(Motamashe motamashe) {
        if (motamasheebi.indexOf(motamashe) != kozirobisMdgomareoba.mimdinareMotamashisIndexi()) {
            throw new IllegalStateException("შენი ჯერი არაა ბლიად, რა მოტყანი ტვინი.");
        }
    }


}
