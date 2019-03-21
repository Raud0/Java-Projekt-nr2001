package MAIN;

import DTOs.ResultsDTO;
import MAIN.Paring_OxfordAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Uurija {
    private static List<ResultsDTO> sonakogu = new ArrayList<ResultsDTO>();

    public static ResultsDTO sonaleidja(String sona) throws IOException {
        //Otsib paringut sonakogust

        for (ResultsDTO kirje : sonakogu  ) {if (kirje.getId().equalsIgnoreCase(sona)) {return kirje;}}

        //Kui ei leia, kusib jarele ja lisab sonakokku
        ResultsDTO vaste = Paring_OxfordAPI.sonaVaste(sona).getResults().get(0);
        sonakogu.add(vaste);
        return vaste;
    }
}
