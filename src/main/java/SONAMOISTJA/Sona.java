package SONAMOISTJA;

import java.util.List;

//hetkel omab ainult teksti ja leksilist kategooriat, tulevikus ilmselt ka domeeni
//sona ei pea ilmtingimata olema sonaraamatust leitav sona. ta voib olla ka nimi voi isegi sumbol
public class Sona {
    private String tekst;
    private List<String> lexical_category;

    public String getTekst() {return tekst;}
    public List<String> getLexical_category() {return lexical_category;}
    public void setLexical_category(List<String> lexical_category) {this.lexical_category = lexical_category;}

    public Sona(String tekst) {this.tekst = tekst;}
}
