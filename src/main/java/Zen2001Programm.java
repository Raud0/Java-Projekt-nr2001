import DTOs.*;
import java.io.IOException;

public class Zen2001Programm {

    public static String tester(String word) throws IOException {
        OxfordAPIVasteDTO vaste = Paring_OxfordAPI.sonaVaste(word);
        //System.out.println(vaste);
        ResultsDTO results = vaste.getResults().get(0);
        //System.out.println(results);
        LexicalEntries lexicalEntries = results.getLexicalEntries().get(0);
        //System.out.println(lexicalEntries);
        Entries entries = lexicalEntries.getEntries().get(0);
        //System.out.println(entries);
        Senses senses = entries.getSenses().get(0);
        //System.out.println(senses);
        Senses subsenses = senses.getSubsenses().get(1);
        //System.out.println(subsenses);
        String domains = subsenses.getDomains().get(0);
        //System.out.println(domains);
        Examples examples = subsenses.getExamples().get(0);
        //System.out.println(examples);
        String text = examples.getText();
        //System.out.println(text);
        return domains + ": " + text;
    }

    public static void main(String[] args) throws IOException {

        String testsone = tester("set");
        KasutajaAken aken = new KasutajaAken(testsone);
    }
}
