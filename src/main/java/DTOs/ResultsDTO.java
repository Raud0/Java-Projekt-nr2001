package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResultsDTO {

    private String id;
    private String language;
    private List<LexicalEntry> lexicalEntries;
    private String type;
    private String word;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getLanguage() {return language;}
    public List<LexicalEntry> getLexicalEntries() {return lexicalEntries;}
    public void setLexicalEntries(List<LexicalEntry> lexicalEntries) {this.lexicalEntries = lexicalEntries;}
    public String getType() {return type;}
    public String getWord() {return word;}

    public ResultsDTO() {}
    public ResultsDTO(String id){
        this.id = id;
        this.word = id;
        this.lexicalEntries = new ArrayList<LexicalEntry>();
        this.lexicalEntries.add(new LexicalEntry(id));
    }
    public ResultsDTO(String id, String language, List<LexicalEntry> lexicalEntries, String type, String word) {
        this.id = id;
        this.language = language;
        this.lexicalEntries = lexicalEntries;
        this.type = type;
        this.word = word;
    }
}