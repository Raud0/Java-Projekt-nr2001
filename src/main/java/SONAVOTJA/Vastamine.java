package SONAVOTJA;

import DTOs.*;
import MAIN.KasutajaAken;
import MAIN.Uurija;
import MAIN.Zen2001Programm;
import SONAMOISTJA.Lause;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vastamine {

    private static List<Teadmine> teadmistepagas = new ArrayList<Teadmine>();
    private final static String[] eightBallVastused = {
            "I believe so. My answer is absolute.",
            "Why do you ask me, if you already know the answer?",
            "I would definitely think so.",
            "My computer says null.",
            "That is the whole truth of it.",
            "I cannot say.",
            "You are not ready for the answer!",
            "  YES!\nYES!\n     YES!\n          YES!",
            "In my opinion, no.",
            "It is in your cards.",
            "Depends on whether the stars align.",
            "Double, double toil and trouble...",
            "No, I think it's very improbable.",
            "If you're here for divination, you're a bigger fool than I at first thought.",
            "I have my doubts.",
            "... You should go home.",
            "And isn't that the billion dollar quesiton?",
            "Please wait, the tea kettle is whistling.",
            "No, but I think it is for the best.",
            "Your time, it is not yet."
    };

    private final static String[] eightBallKaomoji = {
            " ╮(︶︿︶)╭",
            "(•ิ_•ิ)?",
            "(￢_￢)",
            "(^ω~)/",
            "|･ω･)",
            " __φ(．．;)",
            "(－.－)...zzz",
            "( ಠ ʖ̯ ಠ)",
    };

    //tester, mis loob lause, tukeldab selle ja demonstreerib selle liigitamist, lopuks valjastab suvalistest sonadest sonumi
    public static String tester(int testmode, String sisend) throws IOException {
        if (testmode == 1) {
            ResultsDTO results = Uurija.sonaLeidja(sisend);
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
            List<String> lauseosad = lause.lauseTukeldaja(lause.getTooresLause());
            lause.listiPrintija(lauseosad,0);
            lause.lauseTolk(lauseosad);
            testsone = lause.listiPrintija(lauseosad,0);
            return testsone;
        }
        return "(*￣m￣)";
    }

    public static String vasta(String kuuldud_lause) throws IOException {
        String vastus;
        ImageIcon pilt = Zen2001Programm.aken.looPilt(true,"");
        Zen2001Programm.aken.muudaPilt(pilt);

        Lause lause = new Lause(kuuldud_lause);
        lause.lauseTolk(lause.lauseTukeldaja(lause.getTooresLause()));

        if (lause.getGrammatilineMood()[9]) {
            //on küsimus, siis vastus muutub
            return eightBallVastused[(int)(Math.random()*eightBallVastused.length)] + "\n" + eightBallKaomoji[(int)(Math.random()*8)];

        } else {
            //vastus = lause.listiPrintija(new ArrayList<String>(),0);
            vastus = lause.listiPrintija(new ArrayList<String>(),0);
        }

        return vastus;
    }
}
