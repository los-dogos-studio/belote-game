package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.List;
import java.util.stream.Collectors;

public class NatamashebiKartisShemmowmebeli {

    private final Cveti koziriCveti;


    public NatamashebiKartisShemmowmebeli(Cveti koziriCveti) {
        this.koziriCveti = koziriCveti;
    }


    public List<Karti> romeliKartebisTamashiSheidzleba(Motamashe motamashe, Krugi krugi) {
        List<Karti> motamashisKartebi = motamashe.xeli().kartebi();

        Cveti natarebiCveti = krugi.raaNatarebi();
        if (natarebiCveti == null) return motamashisKartebi;

        if (motamashe.xeli().sheicavsCvets(natarebiCveti)) {
            List<Karti> ertiCvetisKartebi = ipoveYvelaErtiCvetisKarti(motamashisKartebi, natarebiCveti);

            if (natarebiCveti == koziriCveti) {
                return ipoveYvelaKoziriRomlitacGadaxtomaSheidzleba(ertiCvetisKartebi, krugi);
            }

            return ertiCvetisKartebi;
        }
        List<Karti> koziriKartebi = ipoveYvelaErtiCvetisKarti(motamashisKartebi, koziriCveti);
        if (koziriKartebi.isEmpty()) { // Sawyals koziri ar yolia
            return motamashisKartebi;
        }

        return ipoveYvelaKoziriRomlitacGadaxtomaSheidzleba(koziriKartebi, krugi);
    }

    private List<Karti> ipoveYvelaKoziriRomlitacGadaxtomaSheidzleba(List<Karti> kozirebi, Krugi krugi) {
        Karti udzlieresiKoziri = krugi.udzlieresiKoziri();
        if (udzlieresiKoziri == null)return kozirebi;

        int udzlieresiKartisPozicia = udzlieresiKoziri.ranki().dzala(true);

        List<Karti> ufroMaghaliKozirebiXelshi = kozirebi.stream()
                .filter(k -> k.ranki().dzala(true) > udzlieresiKartisPozicia)
                .toList();

        return ufroMaghaliKozirebiXelshi.isEmpty() ? kozirebi : ufroMaghaliKozirebiXelshi;

    }

    private List<Karti> ipoveYvelaErtiCvetisKarti(List<Karti> motamashisKartebi, Cveti natarebiCveti) {
        return motamashisKartebi.stream()
                .filter(k -> k.cveti() == natarebiCveti)
                .toList();
    }
}
