package SONAMOISTJA;

import java.util.List;

public class Sona {
    private String tekst;
    private List<String> lexical_category;

    public String getTekst() {return tekst;}
    public List<String> getLexical_category() {return lexical_category;}
    public void setLexical_category(List<String> lexical_category) {this.lexical_category = lexical_category;}

    public Sona(String tekst) {
        this.tekst = tekst;
    }
}
