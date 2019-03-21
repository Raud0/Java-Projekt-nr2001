package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Senses {

    private List<String> definitions;
    private List<String> domains;
    private List<Examples> examples;
    private List<Notes> notes;
    private List<String> short_definitions;
    private List<ThesaurusLinks> thesaurusLinks;
    private List<Senses> subsenses;

    public List<String> getDefinitions() {return definitions;}
    public List<String> getDomains() {return domains;}
    public List<Examples> getExamples() {return examples;}
    public List<Notes> getNotes() {return notes;}
    public List<String> getShort_definitions() {return short_definitions;}
    public List<ThesaurusLinks> getThesaurusLinks() {return thesaurusLinks;}
    public List<Senses> getSubsenses() {
        if (subsenses.size() == 0) {subsenses.add(this);}
        return subsenses;
    }

    public Senses() {}


}