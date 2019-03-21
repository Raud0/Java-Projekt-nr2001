package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ThesaurusLink {
    private String entry_id;
    private String sense_id;

    public String getEntry_id() {return entry_id;}
    public String getSense_id() {return sense_id;}

    public ThesaurusLink() {}
    public ThesaurusLink(String entry_id, String sense_id) {
        this.entry_id = entry_id;
        this.sense_id = sense_id;
    }
}
