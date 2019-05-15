package SONAMOISTJA;

import MAIN.Uurija;
import DTOs.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//peamine struktuur, hoiab enamsti uht motet
public class Lause {
    private static final char[] sumbolid = {'-','–','…','?','.',',',':',';','(',')','"'};

    private String toores_lause;
    private List<String> tukeldatud_lause;
    private List<Sona> sonad;
    private List<Klausel> klauslid;
    private List<Lauseosa> lauseosad;
    private List<ResultsDTO> vasted;
    private boolean[] grammatiline_mood = {
            false, // 0: indicative
            false, // 1: subjunctive
            false, // 2: conditional
            false, // 3: optative
            false, // 4: imperative
            false, // 5: jussive
            false, // 6: potential
            false, // 7: hypothetical
            false, // 8: inferential
            false  // 9: interrogative
    };

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
    public static char[] getSumbolid() {return sumbolid;}
    public List<ResultsDTO> getVasted() {return vasted;}
    public boolean[] getGrammatiline_mood() {return grammatiline_mood;}


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
        System.out.println("Alustan Oxford API küsitlemist.");
        long start = System.nanoTime();
        for(Sona sona : sonad) {
            ResultsDTO vaste = Uurija.sonaleidja(sona.getTekst());

            List<String> leksilised_kategooriad = new ArrayList<String>();
            List<Double> kategooria_kaalud = new ArrayList<Double>();
            List<LexicalEntry> leksilised_sisendid = vaste.getLexicalEntries();

            vasted.add(vaste);

            //Lisan võimalikud leksilised kategooriad
            for (LexicalEntry leksiline_sisend : leksilised_sisendid) {
                leksilised_kategooriad.add(leksiline_sisend.getLexicalCategory());
            }
            //Lisan vastavad algtõenäosused
            for (int i = 0; i < leksilised_kategooriad.size(); i++){
                kategooria_kaalud.add(1.0/leksilised_kategooriad.size());
            }

            sona.setLexical_category(leksilised_kategooriad);
            sona.setKategooria_kaalud(kategooria_kaalud);
        }
        long end = System.nanoTime();
        System.out.println("Lõpetasin küsitlemise. Aega kulus " + TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS) + " sekundit.");


        //Leian lihtsamaid Unknown tüüpe.
        for(int i = 0; i < sonad.size(); i++) {
            boolean muudetud = false;
            Sona sona = sonad.get(i);
            String tekst = sona.getTekst();
            List<String> kategooriad = sona.getLexical_category();
            List<Double> kaalud = sona.getKategooria_kaalud();

            //Nimi
            if (Character.isUpperCase(tekst.charAt(0))) {
                if (!kategooriad.contains("Noun")) {
                    kategooriad.add("Noun");
                    kaalud.add(0.0);}
                int indeks = kategooriad.indexOf("Noun");
                if (i == 0) {kaalud.set(indeks,kaalud.get(indeks) + 0.2);}
                else {kaalud.set(indeks,kaalud.get(indeks) + 0.5);}
            }

            //Sümbol
            if (tekst.length()==1) {
                char teksti_sumbol = tekst.charAt(0);
                for (char sumbol : sumbolid) {
                    if (sumbol == teksti_sumbol) {
                        if (sumbol == '?') {grammatiline_mood[9] = false;}
                        if (!kategooriad.contains("Symbol")) {
                            kategooriad.add("Symbol");
                            kaalud.add(0.0);}
                        int indeks = kategooriad.indexOf("Symbol");
                        if (i == sonad.size()-1) {kaalud.set(indeks,kaalud.get(indeks) + 2);}
                        else {kaalud.set(indeks,kaalud.get(indeks) + 1.5);}
                    }
                }
            }

            //Kinnitamine
            if (!muudetud){

                sona.kaaluseadja(kategooriad,kaalud);
            }
        }


        this.setSonad(sonad);
        return sonad;
    }

    //kui kogu informatsioon sonade kohta on käes, hakkan uurima sõnade võimalikke tähendusi lause kontekstist lähtudes
    public List<Sona> lauseKontekstTolk(List<Sona> sonad) throws IOException {

        //Verbi võimalused
        List<Integer> verbi_indeksid = new ArrayList<Integer>();
        for (int i = 0; i < sonad.size(); i++) {
            if (sonad.get(i).getLexical_category().contains("Verb")) {
                verbi_indeksid.add(i);
            }
        }

        if (verbi_indeksid.size() == 1) {
            Sona sona = sonad.get(verbi_indeksid.get(0));
            sona.teeVerbiks();
        }

        if (verbi_indeksid.size() > 1) {
            for (int i = 0; i < verbi_indeksid.size() - 1; i++) {
                Sona sona_1 = sonad.get(verbi_indeksid.get(i));
                Sona sona_2 = sonad.get(verbi_indeksid.get(i + 1));

                ResultsDTO vaste = getVasted().get(verbi_indeksid.get(i));
                boolean sobivad = false;
                if (Uurija.onGrammatilineTekst(vaste,"Verb","Auxiliary")) {
                    List<Sona> verbi_vahe = sonad.subList(verbi_indeksid.get(i)+1,verbi_indeksid.get(i+1));
                    sobivad = (verbi_vahe.size() == 0);
                    if(!sobivad){
                        sobivad = Uurija.onNimisonaFraas(verbi_vahe);
                    }

                    if(sobivad) {
                        sona_1.teeVerbiks();
                        sona_2.teeVerbiks();
                    }
                }
            }
        }

        this.setSonad(sonad);
        return sonad;
    }

    public String listiprintija(List<String> list, int mode) {
        StringBuilder sone_ehitaja = new StringBuilder();
        if (mode == 0) {
            sonad = this.getSonad();
            if (sonad.size() > 0) {
                for (Sona sona : sonad) {
                    double suurim_kaal = 0;
                    int suurim_kaal_i = 0;
                    List<Double> kaalud = sona.getKategooria_kaalud();
                    for (int i = 0; i < kaalud.size(); i++) {
                        if (kaalud.get(i) > suurim_kaal) {
                            suurim_kaal = kaalud.get(i);
                            suurim_kaal_i = i;
                        }
                    }
                    sone_ehitaja.append(sona.getLexical_category().get(suurim_kaal_i));
                    sone_ehitaja.append('|');
                }
                System.out.println(sone_ehitaja.toString());
                return sone_ehitaja.toString();
            }
        }
        if (mode == 1) {
            sonad = this.getSonad();
            if (sonad.size() > 0) {
                for (Sona sona : sonad) {
                    for (String kategooria : sona.getLexical_category()) {
                        sone_ehitaja.append(kategooria);
                        sone_ehitaja.append('/');
                    }
                    sone_ehitaja.append('|');
                }
                System.out.println(sone_ehitaja.toString());
                return sone_ehitaja.toString();
            }
        }

        //default print, kui soovitud mood ei tööta
        for (String sone : list) {
            sone_ehitaja.append(sone);
            sone_ehitaja.append('|');
        }

        System.out.println(sone_ehitaja.toString());
        return sone_ehitaja.toString();
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

        this.toores_lause = laused[0];
        this.tukeldatud_lause = lauseTukeldaja(toores_lause);
        this.sonad = new ArrayList<Sona>();
        this.vasted = new ArrayList<ResultsDTO>();
    }
}
