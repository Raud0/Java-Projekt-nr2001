package SONAMOISTJA;

import java.util.ArrayList;
import java.util.List;

//hetkel omab ainult teksti ja leksilist kategooriat, tulevikus ilmselt ka domeeni
//sona ei pea ilmtingimata olema sonaraamatust leitav sona. ta voib olla ka nimi voi isegi sumbol
public class Sona {
    private String tekst;
    private List<String> lexicalCategory;
    private String type;
    private List<Double> kategooriaKaalud;

    public String getTekst() {return tekst;}
    public List<String> getLexicalCategory() {return lexicalCategory;}
    public void setLexicalCategory(List<String> lexicalCategory) {this.lexicalCategory = lexicalCategory;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public List<Double> getKategooriaKaalud() {return kategooriaKaalud;}
    public void setKategooriaKaalud(List<Double> kategooriaKaalud) {this.kategooriaKaalud = kategooriaKaalud;}

    public void teeVerbiks() {
        List<String> kategooriad = this.getLexicalCategory();
        List<Double> kaalud = this.getKategooriaKaalud();

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
        this.setLexicalCategory(kategooriad);
        this.setKategooriaKaalud(kaalud);
    }

    public Sona(String tekst) {
        this.tekst = tekst;
        this.lexicalCategory = new ArrayList<String>();
        this.kategooriaKaalud = new ArrayList<Double>();
    }
}
