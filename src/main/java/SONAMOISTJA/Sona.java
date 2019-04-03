package SONAMOISTJA;

import java.util.ArrayList;
import java.util.List;

//hetkel omab ainult teksti ja leksilist kategooriat, tulevikus ilmselt ka domeeni
//sona ei pea ilmtingimata olema sonaraamatust leitav sona. ta voib olla ka nimi voi isegi sumbol
public class Sona {
    private String tekst;
    private List<String> lexical_category;
    private List<Double> kategooria_kaalud;

    public String getTekst() {return tekst;}
    public List<String> getLexical_category() {return lexical_category;}
    public void setLexical_category(List<String> lexical_category) {this.lexical_category = lexical_category;}
    public List<Double> getKategooria_kaalud() {return kategooria_kaalud;}
    public void setKategooria_kaalud(List<Double> kategooria_kaalud) {this.kategooria_kaalud = kategooria_kaalud;}

    public void teeVerbiks() {
        List<String> kategooriad = this.getLexical_category();
        List<Double> kaalud = this.getKategooria_kaalud();

        for (int i = 0; i < kategooriad.size(); i++) {
            if (!kategooriad.get(i).equals("Verb")) {
                kaalud.set(i,0.0);
            }
        }

        this.kaaluseadja(kategooriad,kaalud);
    }

    public void kaaluseadja(List<String> kategooriad, List<Double> kaalud) {
        //Eemaldan teadmatuse, kui midagi on teada
        if (kategooriad.size() > 1 && kategooriad.contains("Unknown")) {
            int indeks = kategooriad.indexOf("Unknown");
            kategooriad.remove(indeks);
            kaalud.remove(indeks);
        }

        //Taastan, et kaalude kogusumma oleks 1.
        double kogu_kaal = 0;
        for (double kaal : kaalud) {kogu_kaal += kaal;}
        for (int j = 0; j < kaalud.size(); j++) {kaalud.set(j,kaalud.get(j)/kogu_kaal);}

        //Muudan objekti
        this.setLexical_category(kategooriad);
        this.setKategooria_kaalud(kaalud);
    }

    public Sona(String tekst) {
        this.tekst = tekst;
        this.lexical_category = new ArrayList<String>();
        this.kategooria_kaalud = new ArrayList<Double>();
    }
}
