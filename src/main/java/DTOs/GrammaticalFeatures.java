package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GrammaticalFeatures {

    private String text;
    private String type;

    public String getText() {return text;}
    public String getType() {return type;}

    public GrammaticalFeatures() {}
    public GrammaticalFeatures(String text, String type) {
        this.text = text;
        this.type = type;
    }
}