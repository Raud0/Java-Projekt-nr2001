package MAIN;

import DTOs.*;
import SONAMOISTJA.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Zen2001Programm {


    private static final String[] emotsioonid = {
            "           ",
            "ヽ(￣ω￣(。。 )ゝ",
            "(｡・//ε//・｡)",
            "(^_<)X(>_^)",
            "(╯°益°)╯彡┻━┻",
            "｀、ヽ｀ヽ｀、ヽ(ノ＞＜)ノ ｀、ヽ｀☂ヽ｀、ヽ",
            "(=O*_*)=O Q(*_*Q)",
            "(ノ°∀°)ノ⌒･*:.｡. .｡.:*･゜ﾟ･*☆",
            "( • )( • )ԅ(≖‿≖ԅ)",
    };
    private static ArrayList<Character> tuju_saba = new ArrayList<Character>();
    public static ArrayList<Character> getTuju_saba() {return tuju_saba;}

    public static void lisaSappa(int i){
        ArrayList<Character> tuju_saba = getTuju_saba();
        tuju_saba.addAll(0,karakteriseerija(emotsioonid[i]));
        tuju_saba.addAll(0,karakteriseerija(emotsioonid[0]));
    }

    public static String tulemuse_tekitaja() {

        try {
            BufferedReader fail = new BufferedReader(new FileReader("slowandsteady.txt"));
            String faili_sisu = fail.readLine();
            fail.close();
            String muteeritud_sisu = tester2(faili_sisu);
            System.out.println("done2");
            return muteeritud_sisu;

        } catch (IOException exception) {
            System.out.println(exception.getMessage());

        }

        return "";
    }

    public static List<Character> karakteriseerija(String sone) {
        List<Character> karakterid = new ArrayList<Character>();
        for (char karakter : sone.toCharArray()) {
            karakterid.add(karakter);
        }
        return karakterid;
    }

    //tester, mis otsib sona ning leiab sealt ules naitekasutuse ja kasutusdomeeni
    public static String tester1(String sona) throws IOException {
        ResultsDTO results = Uurija.sonaleidja(sona);
        LexicalEntry lexicalEntries = results.getLexicalEntries().get((int)(Math.random()*results.getLexicalEntries().size()));
        Entry entries = lexicalEntries.getEntries().get((int)(Math.random()*lexicalEntries.getEntries().size()));
        Sense senses = entries.getSenses().get((int)(Math.random()*entries.getSenses().size()));
        Sense subsenses = senses.getSubsenses().get((int)(Math.random()*senses.getSubsenses().size()));
        String domains = subsenses.getDomains().get((int)(Math.random()*subsenses.getDomains().size()));
        Example example = subsenses.getExamples().get(0);
        String text = example.getText();
        return domains + ": " + text;
    }

    //tester, mis loob lause, tukeldab selle ja demonstreerib selle liigitamist, lopuks valjastab suvalistest sonadest sonumi
    public static String tester2(String lause_sisend) throws IOException {
        String testsone = "";

        //loob lause sisendiga
        Lause lause = new Lause(lause_sisend);

        //teeb lause tukkideks klassi sees ja ka tagastab selle
        List<String> lauseosad = lause.lauseTukeldaja(lause.getToores_lause());
        lause.listiprintija(lauseosad,0);

        //alustab lauseosade kategoriseerimist
        lause.lauseTolk(lauseosad);
        testsone = lause.listiprintija(lauseosad,0);
        //suvalise lause genereerija tagastamiseks (see pole vaga oluline :D)
        /*
        int piir = Math.min(1 + (int)(Math.random()*6),lauseosad.size());
        for(int i = 0; i < piir; i++)
            testsone += lauseosad.get((int)(Math.random()*(lauseosad.size()))) + " ";
        */
        return testsone;
    }

    public Zen2001Programm() {}

    public static void main(String[] args) throws IOException, InterruptedException {
        String algtekst = "(*￣m￣)";
        //testerid
        // 0 väldib API küsitlemist
        int testmode = 2;
        if (testmode == 1) //sona otsingu tester
            algtekst = tester1("set");
        if (testmode == 2) //lause tester
            algtekst = tester2(" Why do they want Rathenau tonight? What did Caesar really whisper to his protégé as he fell? Et tu, Brute, the official lie, is about what you’d expect to get from them—it says exactly nothing. The moment of assassination is the moment when power and the ignorance of power come together, with Death as validator. When one speaks to the other then it is not to pass the time of day with et-tu-Brutes. What passes is a truth so terrible that history—at best a conspiracy, not always among gentlemen, to defraud—will never admit it. The truth will be repressed or in ages of particular elegance be disguised as something else. What will Rathenau, past the moment, years into a new otherside existence, have to say about the old dispensation? Probably nothing as incredible as what he might have said just as the shock flashed his mortal nerves, as the Angel swooped in…");

        //Aken init.
        KasutajaAken aken = new KasutajaAken(algtekst);

        //Tujukas
        StringBuilder sone_ehitaja = new StringBuilder(aken.getTujutekst());

        //Loop
        int ajaluger = 0;
        String siesendi_samasuse_kontroll = "";
        while(true){
            ajaluger++;
            if (ajaluger%10 == 0 && getTuju_saba().size() <= 3) {
                lisaSappa((int)(Math.random()*(emotsioonid.length-2)+1));
            }

            Thread.sleep(1000);
            if (getTuju_saba().size()>0) {
                sone_ehitaja.insert(0,getTuju_saba().get(getTuju_saba().size()-1));
                getTuju_saba().remove(getTuju_saba().size()-1);
            } else {
                sone_ehitaja.insert(0,' ');
            }

            sone_ehitaja.deleteCharAt(sone_ehitaja.length()-1);
            aken.setTujutekst(sone_ehitaja.toString());

            String sisestatud_lause = KasutajaAken.gib_sisestatu();

            if(!(sisestatud_lause.equals(""))){
                if(!(sisestatud_lause.equals(siesendi_samasuse_kontroll))) {

                    siesendi_samasuse_kontroll = sisestatud_lause;
                    String vastus = tester2(sisestatud_lause);
                    aken.setfinall(vastus);
                    aken.vastsue_kuvar();
                }
            }
        }
    }
}
