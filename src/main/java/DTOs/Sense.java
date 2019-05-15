package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Sense {

    private List<String> definitions;
    private List<String> domains = new ArrayList<String>();
    private List<Example> examples = new ArrayList<Example>();
    private List<Note> notes;
    private List<String> short_definitions;
    private List<ThesaurusLink> thesaurusLinks;
    private List<Sense> subsenses = new ArrayList<Sense>();

    public List<String> getDefinitions() {return definitions;}
    public List<String> getDomains() {return domains;}
    public List<Example> getExamples() {return examples;}
    public List<Note> getNotes() {return notes;}
    public List<String> getShortDefinitionss() {return short_definitions;}
    public List<ThesaurusLink> getThesaurusLinks() {return thesaurusLinks;}
    public List<Sense> getSubsenses() {return subsenses;}

    public void givedefaultvalues() {
        if (domains.size() == 0) {domains.add("Unknown");}
        if (subsenses.size() == 0) {subsenses.add(this);}
        if (examples.size() == 0) {examples.add(new Example("No example"));}
    }

    public Sense() {
        givedefaultvalues();
    }
    public Sense(String id) {
        givedefaultvalues();
    }
    public Sense(List<String> definitions, List<String> domains, List<Example> examples, List<Note> notes, List<String> short_definitions, List<ThesaurusLink> thesaurusLinks, List<Sense> subsenses) {
        this.definitions = definitions;
        this.domains = domains;
        this.examples = examples;
        this.notes = notes;
        this.short_definitions = short_definitions;
        this.thesaurusLinks = thesaurusLinks;
        this.subsenses = subsenses;
        givedefaultvalues();
    }
}