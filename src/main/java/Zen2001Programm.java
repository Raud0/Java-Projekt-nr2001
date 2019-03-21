import DTOs.*;
import SONAMOISTJA.*;
import java.io.IOException;
import java.util.List;

public class Zen2001Programm {

    public static String tester1(String word) throws IOException {
        OxfordAPIVasteDTO vaste = Paring_OxfordAPI.sonaVaste(word);
        //System.out.println(vaste);
        ResultsDTO results = vaste.getResults().get(0);
        //System.out.println(results);
        LexicalEntry lexicalEntries = results.getLexicalEntries().get(0);
        //System.out.println(lexicalEntries);
        Entry entries = lexicalEntries.getEntries().get(0);
        //System.out.println(entries);
        Sense senses = entries.getSenses().get(0);
        //System.out.println(senses);
        Sense subsenses = senses.getSubsenses().get(1);
        //System.out.println(subsenses);
        String domains = subsenses.getDomains().get(0);
        //System.out.println(domains);
        Example example = subsenses.getExamples().get(0);
        //System.out.println(examples);
        String text = example.getText();
        //System.out.println(text);
        return domains + ": " + text;
    }

    public static String tester2(String lause_sisend) {
        String testsone = "";
        Lause lause = new Lause(lause_sisend);
        List<String> lauseosad = lause.lauseTukeldaja(lause.getToores_lause());
        lause.listiprintija(lauseosad);

        int piir = Math.min(1 + (int)(Math.random()*6),lauseosad.size());
        for(int i = 0; i < piir; i++)
            testsone += lauseosad.get((int)(Math.random()*(lauseosad.size()))) + " ";
        return testsone;
    }

    public static void main(String[] args) throws IOException {

        int testmode = 2;
        String testsone = "(*￣m￣)";

        if (testmode == 1)
            testsone = tester1("set");
        if (testmode == 2)
            testsone = tester2("Why do they want Rathenau tonight? What did Caesar really whisper to his protégé as he fell? Et tu, Brute, the official lie, is about what you’d expect to get from them—it says exactly nothing. The moment of assassination is the moment when power and the ignorance of power come together, with Death as validator. When one speaks to the other then it is not to pass the time of day with et-tu-Brutes. What passes is a truth so terrible that history—at best a conspiracy, not always among gentlemen, to defraud—will never admit it. The truth will be repressed or in ages of particular elegance be disguised as something else. What will Rathenau, past the moment, years into a new otherside existence, have to say about the old dispensation? Probably nothing as incredible as what he might have said just as the shock flashed his mortal nerves, as the Angel swooped in…");

        KasutajaAken aken = new KasutajaAken(testsone);
        System.out.println("multi-track drifting");

    }
}
