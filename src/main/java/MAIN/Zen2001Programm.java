package MAIN;

import SONAVOTJA.Vastamine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


public class Zen2001Programm {

    public static KasutajaAken aken;

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
    private static ArrayList<Character> tujuSaba = new ArrayList<Character>();
    public static ArrayList<Character> getTujuSaba() {return tujuSaba;}

    public static void lisaSappa(int i){
        ArrayList<Character> tuju_saba = getTujuSaba();
        tuju_saba.addAll(0,karakteriseerija(emotsioonid[i]));
        tuju_saba.addAll(0,karakteriseerija(emotsioonid[0]));
    }

    public static String tulemuse_tekitaja() {

        try {
            BufferedReader fail = new BufferedReader(new FileReader("slowandsteady.txt"));
            String failiSisu = fail.readLine();
            fail.close();
            String muteeritudSisu = Vastamine.tester(2,failiSisu);
            System.out.println("done");
            return muteeritudSisu;

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
        aken = new KasutajaAken(algtekst);

        try {
            FileWriter fail = new FileWriter("slowandsteady.txt", true);
            fail.write("\n");
            fail.write("\n");
            fail.write("\n");
            fail.write("Uus aken meie hinge                              " + new java.sql.Timestamp(System.currentTimeMillis()));
            fail.write("\n");
            fail.write("\n");
            fail.write("\n");


            fail.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        //Tujukas
        StringBuilder soneEhitaja = new StringBuilder(aken.getTujutekst());

        //Loop
        int ajaluger = 0;
        String siesendiSamasuseKontroll = "";
        while(true){
            ajaluger++;
            if (ajaluger%10 == 0 && getTujuSaba().size() <= 3) {
                lisaSappa((int)(Math.random()*(emotsioonid.length-2)+1));
            }

            Thread.sleep(1000);
            if (getTujuSaba().size()>0) {
                soneEhitaja.insert(0, getTujuSaba().get(getTujuSaba().size()-1));
                getTujuSaba().remove(getTujuSaba().size()-1);
            } else {
                soneEhitaja.insert(0,' ');
            }

            soneEhitaja.deleteCharAt(soneEhitaja.length()-1);
            aken.setTujutekst(soneEhitaja.toString());

            String sisestatudLause = KasutajaAken.getSisestatu();

            if(!(sisestatudLause.equals(""))){
                if(!(sisestatudLause.equals(siesendiSamasuseKontroll))) {
                    siesendiSamasuseKontroll = sisestatudLause;
                    String vastus = Vastamine.vasta(sisestatudLause);

                    try {
                        FileWriter fail = new FileWriter("slowandsteady.txt", true);
                        String prinditavVastus = vastus.split("  ")[0];
                        fail.write("         ---------->          " + prinditavVastus);
                        fail.write("\n");
                        fail.close();
                        System.out.println("done");
                    } catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }

                    aken.setFinalWord(vastus);
                    aken.kuvaVastus();
                }
            }
        }
    }
}
