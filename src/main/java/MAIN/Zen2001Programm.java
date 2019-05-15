package MAIN;

import DTOs.*;
import SONAMOISTJA.*;
import SONAVOTJA.Vastamine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Zen2001Programm {


    private static final String[] emotsioonid = {
            "           ",
            "ヽ(￣ω￣(。。 )ゝ",
            "(｡・//ε//・｡)",
            "(^_<)X(>_^)",
            "(╯°益°)╯彡┻━┻",
            "｀、ヽ｀ヽ｀、ヽ(ノ＞＜)ノ ｀、ヽ｀☂ヽ｀、ヽ",
            "(=O*_*)=O Q(*_*Q)",
            "(ノ°∀°)ノ⌒･*:.｡. .｡.:*･゜ﾟ･*☆",
            "( • )( • )ԅ(≖‿≖ԅ)",
    };
    private static ArrayList<Character> tuju_saba = new ArrayList<Character>();
    public static ArrayList<Character> getTuju_saba() {return tuju_saba;}

    public static void lisaSappa(int i){
        ArrayList<Character> tuju_saba = getTuju_saba();
        tuju_saba.addAll(0,karakteriseerija(emotsioonid[i]));
        tuju_saba.addAll(0,karakteriseerija(emotsioonid[0]));
    }

    public static String tulemuse_tekitaja() {

        try {
            BufferedReader fail = new BufferedReader(new FileReader("slowandsteady.txt"));
            String faili_sisu = fail.readLine();
            fail.close();
            String muteeritud_sisu = Vastamine.tester(2,faili_sisu);
            System.out.println("done");
            return muteeritud_sisu;

        } catch (IOException exception) {
            System.out.println(exception.getMessage());

        }

        return "";
    }

    public static List<Character> karakteriseerija(String sone) {
        List<Character> karakterid = new ArrayList<Character>();
        for (char karakter : sone.toCharArray()) {
            karakterid.add(karakter);
        }
        return karakterid;
    }

    //tester, mis otsib sona ning leiab sealt ules naitekasutuse ja kasutusdomeeni


    public Zen2001Programm() {}

    public static void main(String[] args) throws IOException, InterruptedException {

        //Aken init.
        String algtekst = Vastamine.tester(0,"What do they want of Rathenau tonight?");
        KasutajaAken aken = new KasutajaAken(algtekst);

        try {
            FileWriter fail = new FileWriter("slowandsteady.txt", true);
            fail.write("\n");
            fail.write("\n");
            fail.write("Uus aken meie hinge                              " + new java.sql.Timestamp(System.currentTimeMillis()));
            fail.write("\n");
            fail.write("\n");


            fail.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        //Tujukas
        StringBuilder sone_ehitaja = new StringBuilder(aken.getTujutekst());

        //Loop
        int ajaluger = 0;
        String siesendi_samasuse_kontroll = "";
        while(true){
            ajaluger++;
            if (ajaluger%10 == 0 && getTuju_saba().size() <= 3) {
                lisaSappa((int)(Math.random()*(emotsioonid.length-2)+1));
            }

            Thread.sleep(1000);
            if (getTuju_saba().size()>0) {
                sone_ehitaja.insert(0,getTuju_saba().get(getTuju_saba().size()-1));
                getTuju_saba().remove(getTuju_saba().size()-1);
            } else {
                sone_ehitaja.insert(0,' ');
            }

            sone_ehitaja.deleteCharAt(sone_ehitaja.length()-1);
            aken.setTujutekst(sone_ehitaja.toString());

            String sisestatud_lause = KasutajaAken.gib_sisestatu();

            if(!(sisestatud_lause.equals(""))){
                if(!(sisestatud_lause.equals(siesendi_samasuse_kontroll))) {

                    siesendi_samasuse_kontroll = sisestatud_lause;
                    String vastus = Vastamine.vasta(sisestatud_lause);
                    aken.setfinall(vastus);
                    aken.vastsue_kuvar();
                }
            }
        }
    }
}
