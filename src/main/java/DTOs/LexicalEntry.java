package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LexicalEntry {

    private List<Entry> entries;
    private String language;
    private String lexicalCategory;
    private String text;

    public List<Entry> getEntries() {return entries;}
    public String getLanguage() {return language;}
    public String getLexicalCategory() {return lexicalCategory;}
    public void setLexicalCategory(String lexicalCategory) {this.lexicalCategory = lexicalCategory;}
    public String getText() {return text;}

    public LexicalEntry() {}
    public LexicalEntry(String id) {
        this.lexicalCategory = "unknown";
        this.text = id;
    }
    public LexicalEntry(List<Entry> entries, String language, String lexicalCategory, String text) {
        this.entries = entries;
        this.language = language;
        this.lexicalCategory = lexicalCategory;
        this.text = text;
    }
}
