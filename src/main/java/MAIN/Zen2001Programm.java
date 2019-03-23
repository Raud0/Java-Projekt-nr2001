package MAIN;

import DTOs.*;
import SONAMOISTJA.*;

import java.util.List;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Zen2001Programm {

    //tester, mis otsib sona ning leiab sealt ules naitekasutuse ja kasutusdomeeni
    public static String tester1(String sona) throws IOException {

        ResultsDTO results = Uurija.sonaleidja(sona);
        //System.out.println(results);
        LexicalEntry lexicalEntries = results.getLexicalEntries().get((int)(Math.random()*results.getLexicalEntries().size()));
        //System.out.println(lexicalEntries);
        Entry entries = lexicalEntries.getEntries().get((int)(Math.random()*lexicalEntries.getEntries().size()));
        //System.out.println(entries);
        Sense senses = entries.getSenses().get((int)(Math.random()*entries.getSenses().size()));
        //System.out.println(senses);
        Sense subsenses = senses.getSubsenses().get((int)(Math.random()*senses.getSubsenses().size()));
        //System.out.println(subsenses);
        String domains = subsenses.getDomains().get((int)(Math.random()*subsenses.getDomains().size()));
        //System.out.println(domains);
        Example example = subsenses.getExamples().get(0);
        //System.out.println(examples);
        String text = example.getText();
        //System.out.println(text);
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
        lause.listiprintija(lauseosad,1);

        //suvalise lause genereerija tagastamiseks (see pole vaga oluline :D)
        int piir = Math.min(1 + (int)(Math.random()*6),lauseosad.size());
        for(int i = 0; i < piir; i++)
            testsone += lauseosad.get((int)(Math.random()*(lauseosad.size()))) + " ";
        return testsone;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String testsone = "(*￣m￣)"; //vaikevaartus zen-meistri poolt, ausalt, voiks talle kaomoji pagasi anda | ヽ(￣ω￣(。。 )ゝ

        //selle võiks jätta nulli peale, kui midagi, mis ei nõua päringute tegemist, testida, vähem raiskamist
        int testmode = 0;

        if (testmode == 1) //sona otsingu tester + korduvotsingu tester
            testsone = tester1("set") + tester1("home") + tester1("set");
        if (testmode == 2) //lause tester
            testsone = tester2(" Why do they want Rathenau tonight? What did Caesar really whisper to his protégé as he fell? Et tu, Brute, the official lie, is about what you’d expect to get from them—it says exactly nothing. The moment of assassination is the moment when power and the ignorance of power come together, with Death as validator. When one speaks to the other then it is not to pass the time of day with et-tu-Brutes. What passes is a truth so terrible that history—at best a conspiracy, not always among gentlemen, to defraud—will never admit it. The truth will be repressed or in ages of particular elegance be disguised as something else. What will Rathenau, past the moment, years into a new otherside existence, have to say about the old dispensation? Probably nothing as incredible as what he might have said just as the shock flashed his mortal nerves, as the Angel swooped in…");

        //ajutine aknatest
        KasutajaAken aken = new KasutajaAken(testsone);
        aken.setTujutekst("ヽ(￣ω￣(。。 )ゝ");

        //ajutine paralleeljooksutamistest
        System.out.println("\nmulti-track drifting\n");
    }
}
