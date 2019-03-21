package SONAMOISTJA;

import MAIN.Uurija;
import DTOs.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//peamine struktuur, hoiab enamsti uht motet
public class Lause {

    private String toores_lause;
    private List<String> tukeldatud_lause;
    private List<Sona> sonad;
    private List<Klausel> klauslid;
    private List<Lauseosa> lauseosad;

    public String getToores_lause() {return toores_lause;}
    public void setToores_lause(String toores_lause) {this.toores_lause = toores_lause;}
    public List<String> getTukeldatud_lause() {return tukeldatud_lause;}
    public void setTukeldatud_lause(List<String> tukeldatud_lause) {this.tukeldatud_lause = tukeldatud_lause;}
    public List<Sona> getSonad() {return sonad;}
    public void setSonad(List<Sona> sonad) {this.sonad = sonad;}
    public List<Klausel> getKlauslid() {return klauslid;}
    public void setKlauslid(List<Klausel> klauslid) {this.klauslid = klauslid;}
    public List<Lauseosa> getLauseosad() {return lauseosad;}
    public void setLauseosad(List<Lauseosa> lauseosad) {this.lauseosad = lauseosad;}

    //teeb teksti moistetavamaks
    //voib olla peaks tulevikus automaatselt ara tegema "he's" -> "he is", samas, et langetada otsus, kas "'s" nimisonafraasis "the dog's" on contraction voi possessive, peab lauset enne moistma
    public String lausesaniteerija(String lause) {
        //koik tuhikud uhekordseks
        while(lause.contains("  ")) {lause = lause.replaceAll(" {2}", " ");}
        //tekst ei alga tuhikutega
        while(lause.startsWith(" ")) {lause = lause.replaceFirst("\\p{javaWhitespace}","");}
        //kolm punkti loetakse kokku uheks sumboliks
        lause = lause.replaceAll("\\.\\.\\.","…");
        return lause;
    }

    //kuna ettesoodetud tekst voetakse sisse karakter haaval, on vaja need tagasi sonedeks teha
    public String karakterid_sonaks(List<Character> osa) {
        StringBuilder ehitaja = new StringBuilder(osa.size());
        for (Character x : osa) {
            ehitaja.append(x);
        }
        return ehitaja.toString();
    }

    //teeb lause osadeks (sonad, sumbolid)
    public List<String> lauseTukeldaja(String toores_lause) {
        List<String> tukeldatud_lause = new ArrayList<String>();

        //alustan uut sona "osa"
        List<Character> osa = new ArrayList<Character>();
        //kain labi koik karakterit ettesoodetud lauses
        for(int i = 0; i < toores_lause.length(); i++) {
            char x = toores_lause.charAt(i);
            //teen kindlaks, et "osa" on tuhi
            if (i == 0) {osa.clear();}

            //tahed, numbrid ja sidekriipsud voetakse kokku uheks sonaks
            if (Character.isLetterOrDigit(x) || x == '-' || x == '–') {
                osa.add(x);
            } else if (x != ' ') { //tuhikud alustavad uut sona, aga neid ennast ignoreeritakse, sest tuhikute asemel eraldatakse sonu nuud massiivi lahtritega
                tukeldatud_lause.add(karakterid_sonaks(osa));
                osa.clear();
                osa.add(x);
            } else { //iga uue sumboliga lopetatakse vana sona ara ja alustatakse uut
                if (osa.size() > 0) {tukeldatud_lause.add(karakterid_sonaks(osa));}
                osa.clear();
            }

            //lopetab kaesoleva sona, kui lausega on lopuni joutud
            if(i == toores_lause.length() - 1 && osa.size() > 0){
                tukeldatud_lause.add(karakterid_sonaks(osa));
                osa.clear();
            }
        }

        return tukeldatud_lause;
    }

    //sonad hakkavad midagi tahendama
    public List<Sona> lauseTolk(List<String> tukeldatud_lause) throws IOException {
        List<Sona> sonad = new ArrayList<Sona>();

        //Lisan kõik lauseosad sonalisti.
        for(String lauseosa : tukeldatud_lause) {
            sonad.add(new Sona(lauseosa));
        }

        //Otsin välja, mis tüüpi sõna olla saab (verb, noun, jne.)
        for(Sona sona : sonad) {
            ResultsDTO vaste = Uurija.sonaleidja(sona.getTekst());

            List<String> leksilised_kategooriad = new ArrayList<String>();
            List<LexicalEntry> leksilised_sisendid = vaste.getLexicalEntries();

            for (LexicalEntry leksiline_sisend : leksilised_sisendid) {
                leksilised_kategooriad.add(leksiline_sisend.getLexicalCategory());
            }
            sona.setLexical_category(leksilised_kategooriad);
        }

        this.setSonad(sonad);
        return sonad;
    }

    public void listiprintija(List<String> list, int mode) {
        if (mode == 1) {
            sonad = this.getSonad();
            if (sonad.size() > 0) {
                for (Sona sona : sonad) {
                    if (sona.getLexical_category().size() == 1) {
                        System.out.print(sona.getLexical_category().get(0) + "|");
                    } else {
                        System.out.print("Undecided|");
                    }
                }
                System.out.println();
                return;
            }
        }

        //default print, kui soovitud mood ei tööta
        for (String sone : list) {
            System.out.print(sone + "|");
        }
        System.out.println();
    }

    public Lause(String toores_lause) throws IOException {
        toores_lause = lausesaniteerija(toores_lause);

        /*
        Kui sisse antud sõne ei ole üks lause, siis lõikab sõne pooleks pärast esimest '?', '!' või '.'.
        Hetkel viskab teise poole minema, sest teda pole kuhugi salvestada.
         */

        // "(?<=\\p{javaUpperCase}{0}\\p{javaLowerCase}{0,3}[\\?\\.\\!] )" on vajalik selle parast, et initsiaalid ja nimetused nagu "Prof. K. Solmann" mitmeks lauseks ei muutuks.
        // https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#jcc
        // ?<= - tee split paremalt valikust
        // \\p{javaUpperCase}{0} suuri tahti on sonas enne punkti 0
        // \\p{javaLowerCase}{0,3} vaikseid tahti suure tahe vahel voib olla 3
        // [\\?\\.\\!] katkestuskohtades peab olema "?", "." voi "!"
        // " " parast eelnevat sumbolit peab tulema tuhik (voiks olla ka suur taht, aga seda ei saa alati garanteerida
        // (?<=X) votab koik kokku
        //
        // arvestamata on veel jargarvud, aga ma ei tea, kuidas neid teha, sest number voib olla lause lopus, mitte nagu initsiaal
        // ei toota ka sellistel juhtudel, kus nimi on luhike, nagu "I am Mr. K." laheks jargmise lausega kokku


        String[] laused = toores_lause.split("(?<=\\p{javaUpperCase}{0}\\p{javaLowerCase}{0,3}[\\?\\.\\!] )");
        for (int i = 1; i < laused.length; i++) {
            Lause uus_lause = new Lause(laused[i]);
            // Tee uus staatiline sõnumite logija. Kahekordest listist. Ülemine tase, kus vahetumisi kirjutatakse ja alumine, kus on kõik laused, mis korraga öeldi, koos.
        }

        System.out.println(laused[0]);
        this.toores_lause = laused[0];
        this.tukeldatud_lause = lauseTukeldaja(toores_lause);
        this.sonad = new ArrayList<Sona>();
    }
}
