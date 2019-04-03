package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Entry {

    private List<GrammaticalFeature> grammaticalFeatures;
    private String homographNumber;
    private List<Sense> senses;

    public List<GrammaticalFeature> getGrammaticalFeatures() {return grammaticalFeatures;}
    public String getHomograph_number() {return homographNumber;}
    public List<Sense> getSenses() {return senses;}

    public Entry() {}
    public Entry(String id) {
        this.senses = new ArrayList<Sense>();
        this.senses.add(new Sense(id));
        this.grammaticalFeatures = new ArrayList<GrammaticalFeature>();
    }
    public Entry(List<GrammaticalFeature> grammaticalFeatures, String homographNumber, List<Sense> senses) {
        this.grammaticalFeatures = grammaticalFeatures;
        this.homographNumber = homographNumber;
        this.senses = senses;
    }
}
