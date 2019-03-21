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
    public List<String> getDomains() {
        if(domains.size() == 0){domains.add("Unknown");};
        return domains;
    } // Et oleks domeene lihtne kusida ka neilt, millele seda antud pole
    public List<Example> getExamples() {return examples;}
    public List<Note> getNotes() {return notes;}
    public List<String> getShort_definitionss() {return short_definitions;}
    public List<ThesaurusLink> getThesaurusLinks() {return thesaurusLinks;}
    public List<Sense> getSubsenses() {
        if (subsenses.size() == 0) {subsenses.add(this);}
        return subsenses;
    } // Et igal sense'il oleks vahemalt uks subsense, mis puudumise puhul viitab tagasi temale endale.

    public Sense() {}
    public Sense(List<String> definitions, List<String> domains, List<Example> examples, List<Note> notes, List<String> short_definitions, List<ThesaurusLink> thesaurusLinks, List<Sense> subsenses) {
        this.definitions = definitions;
        this.domains = domains;
        this.examples = examples;
        this.notes = notes;
        this.short_definitions = short_definitions;
        this.thesaurusLinks = thesaurusLinks;
        this.subsenses = subsenses;
    }
}