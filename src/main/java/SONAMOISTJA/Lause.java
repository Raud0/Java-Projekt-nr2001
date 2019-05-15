package SONAMOISTJA;

import MAIN.Uurija;
import DTOs.*;
import SONAVOTJA.Teadmine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//peamine struktuur, hoiab enamsti uht motet
public class Lause {
    private static final char[] sumbolid = {'-','–','…','?','.',',',':',';','(',')','"'};

    private String tooresLause;
    private List<String> tukeldatudLause;
    private List<Sona> sonad;
    private List<Klausel> klauslid;
    private List<Lauseosa> lauseosad;
    private List<ResultsDTO> vasted;
    private List<Teadmine> teadmised;
    private boolean[] grammatilineMood = {
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
    private String aeg;

    public String getTooresLause() {return tooresLause;}
    public void setTooresLause(String tooresLause) {this.tooresLause = tooresLause;}
    public List<String> getTukeldatudLause() {return tukeldatudLause;}
    public void setTukeldatudLause(List<String> tukeldatudLause) {this.tukeldatudLause = tukeldatudLause;}
    public List<Sona> getSonad() {return sonad;}
    public void setSonad(List<Sona> sonad) {this.sonad = sonad;}
    public List<Klausel> getKlauslid() {return klauslid;}
    public void setKlauslid(List<Klausel> klauslid) {this.klauslid = klauslid;}
    public List<Lauseosa> getLauseosad() {return lauseosad;}
    public void setLauseosad(List<Lauseosa> lauseosad) {this.lauseosad = lauseosad;}
    public static char[] getSumbolid() {return sumbolid;}
    public List<ResultsDTO> getVasted() {return vasted;}
    public List<Teadmine> getTeadmised() {return teadmised;}
    public boolean[] getGrammatilineMood() {return grammatilineMood;}
    public void setGrammatilineMoodAt(int index, boolean truthValue) {this.grammatilineMood[index] = truthValue;}


    //teeb teksti moistetavamaks
    public String lauseSaniteerija(String lause) {

        //eemaldame kõik mitteotsitavad sümbolid
        lause = lause.replaceAll("[^A-Za-z0-9()\\[\\]]"," ");

        //contractions
        // 's 'd ignored
        lause = lause.replaceAll("n't"," not");
        lause = lause.replaceAll("'m"," am");
        lause = lause.replaceAll("'re"," are");
        lause = lause.replaceAll("'ve"," have");
        lause = lause.replaceAll("'ll"," will");
        lause = lause.replaceAll("'t"," it");
        lause = lause.replaceAll("'em"," them");
        lause = lause.replaceAll("y'","you ");
        lause = lause.replaceAll("Y'","You ");

        //koik tuhikud uhekordseks
        while(lause.contains("  ")) {lause = lause.replaceAll(" {2}", " ");}
        //tekst ei alga tuhikutega
        while(lause.startsWith(" ")) {lause = lause.replaceFirst("\\p{javaWhitespace}","");}
        //kolm punkti loetakse kokku uheks sumboliks
        lause = lause.replaceAll("\\.\\.\\.","…");
        return lause;
    }

    //kuna ettesoodetud tekst voetakse sisse karakter haaval, on vaja need tagasi sonedeks teha
    public String karakteridSonaks(List<Character> osa) {
        StringBuilder ehitaja = new StringBuilder(osa.size());
        for (Character x : osa) {
            ehitaja.append(x);
        }
        return ehitaja.toString();
    }

    //teeb lause osadeks (sonad, sumbolid)
    public List<String> lauseTukeldaja(String tooresLause) {
        List<String> tukeldatudLause = new ArrayList<String>();

        //alustan uut sona "osa"
        List<Character> osa = new ArrayList<Character>();
        //kain labi koik karakterit ettesoodetud lauses
        for(int i = 0; i < tooresLause.length(); i++) {
            char x = tooresLause.charAt(i);
            //teen kindlaks, et "osa" on tuhi
            if (i == 0) {osa.clear();}

            //tahed, numbrid ja sidekriipsud voetakse kokku uheks sonaks
            if (Character.isLetterOrDigit(x) || x == '-' || x == '–') {
                osa.add(x);
            } else if (x != ' ') { //tuhikud alustavad uut sona, aga neid ennast ignoreeritakse, sest tuhikute asemel eraldatakse sonu nuud massiivi lahtritega
                tukeldatudLause.add(karakteridSonaks(osa));
                osa.clear();
                osa.add(x);
            } else { //iga uue sumboliga lopetatakse vana sona ara ja alustatakse uut
                if (osa.size() > 0) {tukeldatudLause.add(karakteridSonaks(osa));}
                osa.clear();
            }

            //lopetab kaesoleva sona, kui lausega on lopuni joutud
            if(i == tooresLause.length() - 1 && osa.size() > 0){
                tukeldatudLause.add(karakteridSonaks(osa));
                osa.clear();
            }
        }

        return tukeldatudLause;
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
            ResultsDTO vaste = Uurija.sonaLeidja(sona.getTekst());

            List<String> leksilisedKategooriad = new ArrayList<String>();
            List<Double> kategooriaKaalud = new ArrayList<Double>();
            List<LexicalEntry> leksilisedSisendid = vaste.getLexicalEntries();

            vasted.add(vaste);

            //Lisan võimalikud leksilised kategooriad
            for (LexicalEntry leksilineSisend : leksilisedSisendid) {
                leksilisedKategooriad.add(leksilineSisend.getLexicalCategory());
            }
            //Lisan vastavad algtõenäosused
            for (int i = 0; i < leksilisedKategooriad.size(); i++){
                kategooriaKaalud.add(1.0/leksilisedKategooriad.size());
            }

            sona.setLexicalCategory(leksilisedKategooriad);
            sona.setKategooriaKaalud(kategooriaKaalud);
        }
        long end = System.nanoTime();
        System.out.println("Lõpetasin küsitlemise. Aega kulus " + TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS) + " sekundit.");


        //Leian lihtsamaid Unknown tüüpe.
        for(int i = 0; i < sonad.size(); i++) {
            boolean muudetud = false;
            Sona sona = sonad.get(i);
            String tekst = sona.getTekst();
            List<String> kategooriad = sona.getLexicalCategory();
            List<Double> kaalud = sona.getKategooriaKaalud();

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
                        if (sumbol == '?') {
                            grammatilineMood[9] = false;}
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
        List<Integer> verbiIndeksid = new ArrayList<Integer>();
        for (int i = 0; i < sonad.size(); i++) {
            if (sonad.get(i).getLexicalCategory().contains("Verb")) {
                verbiIndeksid.add(i);
            }
        }

        if (verbiIndeksid.size() == 1) {
            List<Sona> predikaatorSonad = new ArrayList<Sona>();
            Sona sona = sonad.get(verbiIndeksid.get(0));
            sona.teeVerbiks();
            predikaatorSonad.add(sona);
            lauseosad.add(new Lauseosa("Predicator",predikaatorSonad));
        }

        if (verbiIndeksid.size() > 1) {
            for (int i = 0; i < verbiIndeksid.size() - 1; i++) {
                Sona sona1 = sonad.get(verbiIndeksid.get(i));
                Sona sona2 = sonad.get(verbiIndeksid.get(i + 1));

                ResultsDTO vaste = getVasted().get(verbiIndeksid.get(i));
                boolean sobivad = false;
                if (Uurija.onGrammatilineTekst(vaste,"Verb","Auxiliary")) {
                    List<Sona> verbiVahe = sonad.subList(verbiIndeksid.get(i)+1,verbiIndeksid.get(i+1));
                    sobivad = (verbiVahe.size() == 0);
                    if(!sobivad){
                        sobivad = Uurija.onNimisonaFraas(verbiVahe);
                    }

                    if(sobivad) {
                        List<Sona> predikaatorSonad = new ArrayList<Sona>();
                        sona1.teeVerbiks();
                        sona1.setType("Auxiliary");
                        sona2.teeVerbiks();
                        predikaatorSonad.add(sona1);
                        predikaatorSonad.add(sona2);
                        lauseosad.add(new Lauseosa("Predicator",predikaatorSonad));
                    }
                }
            }
        }

        this.setSonad(sonad);
        return sonad;
    }

    public List<Lauseosa> leiaLauseosad(List<Sona> sonad) {
        List<Lauseosa> lauseosad = new ArrayList<Lauseosa>();
        return lauseosad;
    }

    public String listiPrintija(List<String> list, int mode) {
        StringBuilder soneEhitaja = new StringBuilder();
        if (mode == 0) {
            sonad = this.getSonad();
            if (sonad.size() > 0) {
                for (Sona sona : sonad) {
                    double suurimKaal = 0;
                    int suurimKaalIndeks = 0;
                    List<Double> kaalud = sona.getKategooriaKaalud();
                    for (int i = 0; i < kaalud.size(); i++) {
                        if (kaalud.get(i) > suurimKaal) {
                            suurimKaal = kaalud.get(i);
                            suurimKaalIndeks = i;
                        }
                    }
                    soneEhitaja.append(sona.getLexicalCategory().get(suurimKaalIndeks));
                    soneEhitaja.append('|');
                }
                System.out.println(soneEhitaja.toString());
                return soneEhitaja.toString();
            }
        }
        if (mode == 1) {
            sonad = this.getSonad();
            if (sonad.size() > 0) {
                for (Sona sona : sonad) {
                    for (String kategooria : sona.getLexicalCategory()) {
                        soneEhitaja.append(kategooria);
                        soneEhitaja.append('/');
                    }
                    soneEhitaja.append('|');
                }
                System.out.println(soneEhitaja.toString());
                return soneEhitaja.toString();
            }
        }

        //default print, kui soovitud mood ei tööta
        for (String sone : list) {
            soneEhitaja.append(sone);
            soneEhitaja.append('|');
        }

        System.out.println(soneEhitaja.toString());
        return soneEhitaja.toString();
    }

    public Lause(String tooresLause) throws IOException {
        tooresLause = lauseSaniteerija(tooresLause);

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


        String[] laused = tooresLause.split("(?<=\\p{javaUpperCase}{0}\\p{javaLowerCase}{0,3}[\\?\\.\\!] )");
        for (int i = 1; i < laused.length; i++) {
            Lause uus_lause = new Lause(laused[i]);
            // Tee uus staatiline sõnumite logija. Kahekordest listist. Ülemine tase, kus vahetumisi kirjutatakse ja alumine, kus on kõik laused, mis korraga öeldi, koos.
        }

        this.tooresLause = laused[0];
        this.tukeldatudLause = lauseTukeldaja(tooresLause);
        this.sonad = new ArrayList<Sona>();
        this.vasted = new ArrayList<ResultsDTO>();
        this.teadmised = new ArrayList<Teadmine>();
    }
}
