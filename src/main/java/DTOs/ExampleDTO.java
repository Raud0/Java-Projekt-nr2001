/*
//Tuupiline DTO klassi struktuur

package DTOs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ei lisa objekti vaartustamata tunnuseid
@JsonIgnoreProperties(ignoreUnknown = true) // Ei lisa objekti tunnuseid, mida pole kusitud
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // Automaatslet tuvastab noutud tunnuseid
public class ExampleDTO {

    //Valjade nimed peavad vastama OxfordAPI kaest vastene saadud JSON valjade nimetustele, muidu ei loeta neid sisse
    private String id; // Objekti unikaalsed tunnused
    private List<String> comments; // Objekti loendatavad tunnused
    private List<ExamploidDTO> examploids; // Objekti alamobjektid

    // Getterid ja (kui on vaja olnud, olen lisanud ka setterid)
    public String getId() {return id;}

    public List<String> getComments() {return comments;}
    public void setComments(List<String> comments) {this.comments = comments;}
    public List<ExamploidDTO> getExamploids() {return examploids;}

    public ExampleDTO(String id, List<String> comments, List<ExamploidDTO> examploids) { // Objekti valjadega konstruktor voiks ka olla
        this.id = id;
        this.comments = comments;
        this.examploids = examploids;
    }
}
*/