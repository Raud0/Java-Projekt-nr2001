package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OxfordAPIVasteDTO {

    private MetadataDTO metadata;
    private List<ResultsDTO> results;

    public MetadataDTO getMetadata() {return metadata;}
    public List<ResultsDTO> getResults() {return results;}

    public OxfordAPIVasteDTO() {}
    public OxfordAPIVasteDTO(MetadataDTO metadata, List<ResultsDTO> results) {
        this.metadata = metadata;
        this.results = results;
    }

}