import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class Paring_OxfordAPI {

    private static final String API_URL = "https://od-api.oxforddictionaries.com/api/v1";
    private static final String KEEL = "en";
    private static final String[] ID = {"599ae726","a84a0789"};
    private static final String[] VOTI = {"973120be482c402f7e13836af6f5447c","e7fc93ec2a8dd81069c96cf6f2a58dda"};

    public static OxfordAPIVasteDTO sonaVaste(String sona) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        CloseableHttpClient http_klient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(API_URL + "/entries/" + KEEL + "/" + sona);
        int i = (int)(Math.random()*2);
        httpGet.setHeader("Accept","application/json");
        httpGet.setHeader("app_id",ID[i]);
        httpGet.setHeader("app_key",VOTI[i]);
        CloseableHttpResponse vastus = http_klient.execute(httpGet);

        OxfordAPIVasteDTO tulemus = null;

        try {
            //System.out.println(vastus.getStatusLine());
            HttpEntity olevus = vastus.getEntity();
            String vaste = EntityUtils.toString(olevus);
            //System.out.println(vaste);
            tulemus = objectMapper.readValue(vaste, OxfordAPIVasteDTO.class);
            EntityUtils.consume(olevus);
        } finally {
            vastus.close();
        }

        return tulemus;
    }

}
