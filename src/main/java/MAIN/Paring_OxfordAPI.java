package MAIN;

import DTOs.OxfordAPIVasteDTO;

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
    private static final String[] ID = {"599ae726","a84a0789","68e94e56"};
    private static final String[] VOTI = {"973120be482c402f7e13836af6f5447c","e7fc93ec2a8dd81069c96cf6f2a58dda","6238b35eb91821083abd7a3013bd91d6"};

    // Kusib Oxford APIlt informatsiooni "sone" kohta
    public static OxfordAPIVasteDTO sonaVaste(String sona) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        CloseableHttpClient http_klient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(API_URL + "/entries/" + KEEL + "/" + sona);
        int i = (int)(Math.random()*Math.min(ID.length,VOTI.length));
        httpGet.setHeader("Accept","application/json");
        //Valitakse suvaline votme ja id kombinatsioon otsingu tegemiseks (minu, sinu ja Kristjan II oma)
        httpGet.setHeader("app_id",ID[i]);
        httpGet.setHeader("app_key",VOTI[i]);
        CloseableHttpResponse vastus = http_klient.execute(httpGet);

        OxfordAPIVasteDTO tulemus = null;
        try {
            //System.out.println(vastus.getStatusLine());

            //Kui asi ei toota, luuakse objekt ise koos vaikevaartustega
            if(vastus.getStatusLine().getStatusCode() != 200) {
                tulemus = new OxfordAPIVasteDTO(sona);
            }
            //Kui asi tootab ja annab vastuseks staatuskoodi 200, loetakse andmed vastusest sisse
            else {
                HttpEntity olevus = vastus.getEntity();
                String vaste = EntityUtils.toString(olevus);
                //System.out.println(vaste);
                tulemus = objectMapper.readValue(vaste, OxfordAPIVasteDTO.class);
                EntityUtils.consume(olevus);
            }
        } finally {
            vastus.close();
        }

        return tulemus;
    }

}
