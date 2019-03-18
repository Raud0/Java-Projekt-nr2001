import java.io.IOException;

public class Zen2001Programm {
    public static void main(String[] args) throws IOException {

        OxfordAPIVasteDTO result = Paring_OxfordAPI.sonaVaste("dog");
        KasutajaAken aken = new KasutajaAken(result.getMetadataDTO().getProvider());
    }
}
