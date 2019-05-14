package MAIN;

import DTOs.Entry;
import DTOs.GrammaticalFeature;
import DTOs.LexicalEntry;
import DTOs.ResultsDTO;
import SONAMOISTJA.Sona;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Uurija extends Thread {
    private static List<ResultsDTO> sonakogu = new ArrayList<ResultsDTO>();

    public static boolean onGrammatilineTekst(ResultsDTO vaste, String otsitud_kategooria, String otsitud_omadus) {
        for (LexicalEntry leksiline_kirje : vaste.getLexicalEntries()) {
            if (leksiline_kirje.getLexicalCategory().equals(otsitud_kategooria)) {
                for (Entry kirje : leksiline_kirje.getEntries()){
                    for (GrammaticalFeature grammatiline_omadus : kirje.getGrammaticalFeatures()) {
                        if (grammatiline_omadus.getText().equals(otsitud_omadus)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean onNimisonaFraas(List<Sona> sonad) {
        if(sonad.get(0).equals("not")) {
            sonad.remove(0);
        }
        //Tegija võimalused
        List<Integer> tegija_indeksid = new ArrayList<Integer>();
        for (int i = 0; i < sonad.size(); i++) {
            if (sonad.get(i).getLexical_category().contains("Noun") || sonad.get(i).getLexical_category().contains("Prooun")) {
                tegija_indeksid.add(i);
            }
        }
        if (tegija_indeksid.size() > 0) {
            return true;
        }
        return false;
    }

    // Teeb paringuid sonade kohta
    public static ResultsDTO sonaleidja(String sona) throws IOException {
        //Otsib paringut sonakogust

        for (ResultsDTO kirje : sonakogu  ) {if (kirje.getId().equalsIgnoreCase(sona)) {return kirje;}}

        //Kui ei leia, parib OxfordAPIlt ja lisab sonakokku
        ResultsDTO vaste = Paring_OxfordAPI.sonaVaste(sona).getResults().get(0);
        sonakogu.add(vaste);
        return vaste;
    }
}
