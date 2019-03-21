package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Entries {

    private List<GrammaticalFeatures> grammaticalFeatures;
    private String homographNumber;
    private List<Senses> senses;

    public List<GrammaticalFeatures> getGrammaticalFeatures() {return grammaticalFeatures;}
    public String getHomograph_number() {return homographNumber;}
    public List<Senses> getSenses() {return senses;}

    public Entries() {}
    public Entries(List<GrammaticalFeatures> grammaticalFeatures, String homographNumber, List<Senses> senses) {
        this.grammaticalFeatures = grammaticalFeatures;
        this.homographNumber = homographNumber;
        this.senses = senses;
    }
}
