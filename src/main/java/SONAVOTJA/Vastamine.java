package SONAVOTJA;

import DTOs.*;
import MAIN.Uurija;
import SONAMOISTJA.Lause;

import java.io.IOException;
import java.util.List;

public class Vastamine {

    //tester, mis loob lause, tukeldab selle ja demonstreerib selle liigitamist, lopuks valjastab suvalistest sonadest sonumi
    public static String tester(int testmode, String sisend) throws IOException {
        if (testmode == 1) {
            ResultsDTO results = Uurija.sonaleidja(sisend);
            LexicalEntry lexicalEntries = results.getLexicalEntries().get((int)(Math.random()*results.getLexicalEntries().size()));
            Entry entries = lexicalEntries.getEntries().get((int)(Math.random()*lexicalEntries.getEntries().size()));
            Sense senses = entries.getSenses().get((int)(Math.random()*entries.getSenses().size()));
            Sense subsenses = senses.getSubsenses().get((int)(Math.random()*senses.getSubsenses().size()));
            String domains = subsenses.getDomains().get((int)(Math.random()*subsenses.getDomains().size()));
            Example example = subsenses.getExamples().get(0);
            String text = example.getText();
            return domains + ": " + text;
        }
        if (testmode == 2) {
            String testsone = "";
            //loob lause sisendiga
            Lause lause = new Lause(sisend);
            List<String> lauseosad = lause.lauseTukeldaja(lause.getToores_lause());
            lause.listiprintija(lauseosad,0);
            lause.lauseTolk(lauseosad);
            testsone = lause.listiprintija(lauseosad,0);
            return testsone;
        }
        return "(*￣m￣)";
    }

    public static String vasta(String kuuldud_lause) throws IOException {
        String vastus = tester(2,kuuldud_lause);

        Lause lause = new Lause(kuuldud_lause);
        lause.lauseTolk(lause.lauseTukeldaja(lause.getToores_lause()));

        if (lause.getGrammatiline_mood()[9]) {
            //on küsimus, siis vastus muutub
        } else {
            //pole küsimus
        }

        return vastus;
    }
}
