package com.github.konstantinevashalomidze.belotegame.tamashi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Krugi {
    private final Cveti koziriCveti;
    private final List<NatamashebiKarti> natamashebiKartebi = new ArrayList<>();
    
    public Krugi(Cveti koziriCveti) {
        this.koziriCveti = koziriCveti;
    }
    
    
    public void motamashemChamovidaKarti(Motamashe motamashe, Karti karti) {
        natamashebiKartebi.add(new NatamashebiKarti(karti, motamashe));
    }
    
    public Cveti raaNatarebi() {
        if (natamashebiKartebi.isEmpty()) return null;
        return natamashebiKartebi.getFirst().karti().cveti();
    }
    
    public boolean mokozirda() {
        if (natamashebiKartebi.isEmpty()) return false;
        Cveti natarebiCveti = raaNatarebi();
        return natamashebiKartebi.stream()
                .anyMatch(nk -> nk.karti().cveti() == koziriCveti && koziriCveti != natarebiCveti);
    }
    
    
    public Karti udzlieresiKoziri() {
        return natamashebiKartebi.stream()
                .map(NatamashebiKarti::karti)
                .filter(karti -> karti.cveti() == koziriCveti)
                .max(Comparator.comparingInt(c -> c.ranki().dzala(true)))
                .orElse(null);
    }
    
    public boolean sruliKrugia() {
        return natamashebiKartebi.size() == 4;
    }
    
    
    public List<NatamashebiKarti> natamashebiKartebi() {
        return natamashebiKartebi;
    }
    
    public NatamashebiKarti daadgineGamarjvebuli() {
        if (!sruliKrugia()) throw new IllegalStateException("სამიგაქ, ჯერ არ ჩამოსულა ყველა");
        if (mokozirda()) {
            return natamashebiKartebi.stream()
                    .filter(nk -> nk.karti().cveti() == koziriCveti)
                    .max(Comparator.comparingInt(nk -> nk.karti().ranki().dzala(true)))
                    .orElseThrow();
        } else {
            Cveti natarebiCveti = raaNatarebi();
            return natamashebiKartebi.stream()
                    .filter(nk -> nk.karti().cveti() == natarebiCveti)
                    .max(Comparator.comparingInt(nk -> nk.karti().ranki().dzala(false)))
                    .orElseThrow();
        }
    }

}
