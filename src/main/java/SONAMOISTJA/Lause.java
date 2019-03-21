package SONAMOISTJA;

import MAIN.Uurija;
import DTOs.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public String lausesaniteerija(String lause) {
        while(lause.contains("  ")) {lause = lause.replaceAll(" {2}", " ");}
        while(lause.startsWith(" ")) {lause = lause.replaceFirst(" ","");}
        lause = lause.replaceAll("\\.\\.\\.","…");
        return lause;
    }

    public String karakterid_sonaks(List<Character> osa) {
        StringBuilder ehitaja = new StringBuilder(osa.size());
        for (Character x : osa) {
            ehitaja.append(x);
        }
        return ehitaja.toString();
    }

    public List<String> lauseTukeldaja(String toores_lause) {
        List<String> tukeldatud_lause = new ArrayList<String>();

        List<Character> osa = new ArrayList<Character>();
        for(int i = 0; i < toores_lause.length(); i++) {
            char x = toores_lause.charAt(i);
            if (i == 0) {osa.clear();}

            if (Character.isLetterOrDigit(x) || x == '-' || x == '–') {
                osa.add(x);
            } else if (x != ' ') {
                tukeldatud_lause.add(karakterid_sonaks(osa));
                osa.clear();
                osa.add(x);
            } else {
                if (osa.size() > 0) {tukeldatud_lause.add(karakterid_sonaks(osa));}
                osa.clear();
            }

            if(i == toores_lause.length() - 1 && osa.size() > 0){
                tukeldatud_lause.add(karakterid_sonaks(osa));
                osa.clear();
            }
        }

        return tukeldatud_lause;
    }

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
