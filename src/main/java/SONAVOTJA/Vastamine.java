package SONAVOTJA;

import DTOs.*;
import MAIN.Uurija;
import MAIN.Zen2001Programm;
import SONAMOISTJA.Lause;

import javax.swing.*;
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
            "And isn't that the billion dollar question?",
            "Please wait, the tea kettle is whistling.",
            "No, but I think it is for the best.",
            "Your time, it is not yet."
    };

    private final static String[] uldisedEightBallVastused = {
            "... You should go home.",
            "And isn't that the billion dollar question?",
            "I cannot say.",
            "My computer says null.",
            "Why do you ask me, if you already know the answer?",
            "You are not ready for the answer!",
            "If you're here for divination, you're a bigger fool than I at first thought.",
            "Please wait, the tea kettle is whistling.",
            "Double, double toil and trouble...",

    };

    private final static String[] whenEightBallVastused = {
            "Your time, it is not yet.",
            "Depends on whether the stars align.",
            "Never.",
            "When pigs fly.",
            "When time comes for the snows of yesteryear.",
            "At the end of the universe.",
            "Before you were born.",
            "When you're in the grave.",
            "When the eagles return.",
            "When the king in the mountain rises.",
            "When the arc comes to a close."
    };

    private final static String[] whereEightBallVastused = {
            "Your home.",
            "At the far edge of the universe.",
            "Nowhere.",
            "Wherever the road leads.",
            "Wherever you are, is where it shall be.",
            "Where the river meets the sea.",
    };

    private final static String[] howEightBallVastused = {
            "Badly, I'm afraid.",
            "Well, I'm sure.",
            "I don't need to tell you this. You can see the writing on the wall."
    };

    private final static String[] whoEightBallVastused = {
            "Nobody, but everyone.",
            "The King of Kings.",
            "Look in the mirror.",
            "But who are you?"
    };

    private final static String[] whichEightBallVastused = {
            "The one that was yesterday.",
            "I don't know, but not the one you're thinking of.",
            "The one that your heart knows"
    };

    private final static String[] whyEightBallVastused = {
            "It is in your cards.",
            "Because I said so.",
            "Do you ask a fish why it swims?",
            "I do not know.",
            "Why do you ask?"
            //"That is the whole truth of it.",
    };

    private final static String[] whatEightBallVastused = {
            "Nothing, and everything.",
            "The end to all ends."
    };

    private final static String[] isAreEightBallVastused = {
            "I believe so. My answer is absolute.",
            "I would definitely think so.",
            "I cannot say.",
            "  YES!\nYES!\n     YES!\n          YES!",
            "In my opinion, no.",
            "It is in your cards.",
            "Depends on whether the stars align.",
            "No, I think it's very improbable.",
            "I have my doubts.",
            "No, but I think it is for the best.",
            "Of course!",
            "Never doubt it.",
            "Does the pope shit in the woods?"
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
            String testsone;
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
        ImageIcon piltE = Zen2001Programm.aken.looPilt(false,"lepenseur.jpg");
        Zen2001Programm.aken.muudaPilt(piltE);

        Lause lause = new Lause(kuuldud_lause);
        lause.lauseTolk(lause.lauseTukeldaja(lause.getTooresLause()));

        if (lause.getGrammatilineMood()[9]) {

            String kontrollitavKüsisõna = lause.getSonad().get(0).getTekst().toLowerCase();
            if((Math.random()*10) < 3) {vastus = uldisedEightBallVastused[(int) (Math.random() * uldisedEightBallVastused.length)] + "\n" + eightBallKaomoji[(int) (Math.random() * 8)];}
            else if(kontrollitavKüsisõna.equals("why")){vastus = whyEightBallVastused[(int) (Math.random() * whyEightBallVastused.length)];}
            else if(kontrollitavKüsisõna.equals("is")||kontrollitavKüsisõna.equals("are")){vastus = isAreEightBallVastused[(int) (Math.random() * isAreEightBallVastused.length)];}
            else if(kontrollitavKüsisõna.equals("how")){vastus = howEightBallVastused[(int) (Math.random() * howEightBallVastused.length)];}
            else if(kontrollitavKüsisõna.equals("who")){vastus = whoEightBallVastused[(int) (Math.random() * whoEightBallVastused.length)];}
            else if(kontrollitavKüsisõna.equals("where")){vastus = whereEightBallVastused[(int) (Math.random() * whereEightBallVastused.length)];}
            else if(kontrollitavKüsisõna.equals("when")){vastus = whenEightBallVastused[(int) (Math.random() * whenEightBallVastused.length)];}
            else if(kontrollitavKüsisõna.equals("which")){vastus = whichEightBallVastused[(int) (Math.random() * whichEightBallVastused.length)];}
            else if(kontrollitavKüsisõna.equals("what")){vastus = whatEightBallVastused[(int) (Math.random() * whatEightBallVastused.length)];}
            else{vastus = uldisedEightBallVastused[(int) (Math.random() * uldisedEightBallVastused.length)];}

            vastus = vastus + " " + eightBallKaomoji[(int) (Math.random() * 8)];

        } else {
            //vastus = lause.listiPrintija(new ArrayList<String>(),0);
            vastus = "Sõnade tõlkimis näide...\n" + lause.listiPrintija(new ArrayList<String>(),0);
        }

        ImageIcon piltP = Zen2001Programm.aken.looPilt(true,"");
        Zen2001Programm.aken.muudaPilt(piltP);
        return vastus;
    }
}
