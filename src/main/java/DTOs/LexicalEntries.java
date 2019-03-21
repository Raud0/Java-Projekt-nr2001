package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LexicalEntries {

    List<Entries> entries;
    String language;
    String lexicalCategory;
    String text;

    public List<Entries> getEntries() {return entries;}
    public String getLanguage() {return language;}
    public String getLexical_category() {return lexicalCategory;}
    public String getText() {return text;}

    public LexicalEntries() {}
    public LexicalEntries(List<Entries> entries, String language, String lexicalCategory, String text) {
        this.entries = entries;
        this.language = language;
        this.lexicalCategory = lexicalCategory;
        this.text = text;
    }
}
