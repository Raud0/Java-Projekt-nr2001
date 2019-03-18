import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OxfordAPIVasteDTO {

    MetadataDTO metadata;
    //ResultsDTO results;

    public OxfordAPIVasteDTO() {
    }

    public OxfordAPIVasteDTO(MetadataDTO metadata) {
        this.metadata = metadata;
    }

    public MetadataDTO getMetadataDTO() {
        return metadata;
    }

    public void setMetadataDTO(MetadataDTO metadataDTO) {
        this.metadata = metadataDTO;
    }

    /*public OxfordAPIVasteDTO(ResultsDTO results) {
        this.results = results;
    }

    public ResultsDTO getResultsDTO() {
        return results;
    }

    public void setResultsDTO(ResultsDTO results) {
        this.results = results;
    }*/
}