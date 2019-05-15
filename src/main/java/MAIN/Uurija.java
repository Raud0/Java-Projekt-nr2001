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
        for (LexicalEntry leksilineKirje : vaste.getLexicalEntries()) {
            if (leksilineKirje.getLexicalCategory().equals(otsitud_kategooria)) {
                for (Entry kirje : leksilineKirje.getEntries()){
                    for (GrammaticalFeature grammatilineOmadus : kirje.getGrammaticalFeatures()) {
                        if (grammatilineOmadus.getText().equals(otsitud_omadus)) {
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
        List<Integer> tegijaIndeksid = new ArrayList<Integer>();
        for (int i = 0; i < sonad.size(); i++) {
            if (sonad.get(i).getLexicalCategory().contains("Noun") || sonad.get(i).getLexicalCategory().contains("Pronoun")) {
                tegijaIndeksid.add(i);
            }
        }
        if (tegijaIndeksid.size() > 0) {
            return true;
        }
        return false;
    }

    // Teeb paringuid sonade kohta
    public static ResultsDTO sonaLeidja(String sona) throws IOException {
        //Otsib paringut sonakogust

        for (ResultsDTO kirje : sonakogu  ) {if (kirje.getId().equalsIgnoreCase(sona)) {return kirje;}}

        //Kui ei leia, parib OxfordAPIlt ja lisab sonakokku
        ResultsDTO vaste = ParingOxfordAPI.sonaVaste(sona).getResults().get(0);
        sonakogu.add(vaste);
        return vaste;
    }
}
