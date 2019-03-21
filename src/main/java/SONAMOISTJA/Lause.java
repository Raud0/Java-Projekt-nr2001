package SONAMOISTJA;

import SONAMOISTJA.Klausel;

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
            Character x = toores_lause.charAt(i);
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

    public void listiprintija(List<String> list) {
        for (String sone : list) {
            System.out.print(sone + "|");
        }
    }

    public Lause(String toores_lause) {
        this.toores_lause = toores_lause;
        this.tukeldatud_lause = lauseTukeldaja(toores_lause);
    }
}
