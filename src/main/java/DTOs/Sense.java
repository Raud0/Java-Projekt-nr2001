package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Sense {

    private List<String> definitions;
    private List<String> domains;
    private List<Example> examples;
    private List<Note> notes;
    private List<String> short_definitions;
    private List<ThesaurusLink> thesaurusLinks;
    private List<Sense> subsenses;

    public List<String> getDefinitions() {return definitions;}
    public List<String> getDomains() {return domains;}
    public List<Example> getExamples() {return examples;}
    public List<Note> getNotes() {return notes;}
    public List<String> getShort_definitionss() {return short_definitions;}
    public List<ThesaurusLink> getThesaurusLinks() {return thesaurusLinks;}
    public List<Sense> getSubsenses() {
        if (subsenses.size() == 0) {subsenses.add(this);}
        return subsenses;
    }

    public Sense() {}


}