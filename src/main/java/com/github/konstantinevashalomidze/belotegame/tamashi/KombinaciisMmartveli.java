package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.ArrayList;
import java.util.List;

public class KombinaciisMmartveli {
    private final KombinaciisAmomcnobi amomcnobi;
    private final KombinaciisShemdarebeli shemdarebeli;

    public KombinaciisMmartveli(Cveti koziriCveti) {
        amomcnobi = new KombinaciisAmomcnobi(koziriCveti);
        shemdarebeli = new KombinaciisShemdarebeli(koziriCveti);
    }


    public KombinaciisShedegi visiKombinaciaGadis(Gundi gundiA, Gundi gundiB) {
        List<Kombinacia> gundiAKombinaciebi = ipoveYvelaKombinaciaGundis(gundiA);
        List<Kombinacia> gundiBKombinaciebi = ipoveYvelaKombinaciaGundis(gundiB);

        if (gundiAKombinaciebi.isEmpty()) return new KombinaciisShedegi(gundiB, gundiBKombinaciebi);
        if (gundiBKombinaciebi.isEmpty()) return new KombinaciisShedegi(gundiA, gundiAKombinaciebi);

        Kombinacia aGundisUdzlieresiKombinacia = shemdarebeli.ipoveUdzlieresiKombinacia(gundiAKombinaciebi);
        Kombinacia bGundisUdzlieresiKombinacia = shemdarebeli.ipoveUdzlieresiKombinacia(gundiBKombinaciebi);

        if (shemdarebeli.metia(aGundisUdzlieresiKombinacia, bGundisUdzlieresiKombinacia)) {
            return new KombinaciisShedegi(gundiA, gundiAKombinaciebi);
        } else {
            return new KombinaciisShedegi(gundiB, gundiBKombinaciebi);
        }
    }

    private List<Kombinacia> ipoveYvelaKombinaciaGundis(Gundi gundi) {
        List<Kombinacia> yvela = new ArrayList<>();
        for (Motamashe motamashe : gundi.motamasheebi()) {
            yvela.addAll(amomcnobi.impoveKombinaciebi(motamashe));
        }
        return yvela;
    }

}
