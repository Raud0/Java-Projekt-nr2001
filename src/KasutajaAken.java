import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KasutajaAken extends Frame {

    private Image aken_ikoon;

    private Label silt_vastus;
    private TextField vastus;
    private String vastus_tekst;

    private Label silt_sisend;
    private TextField sisend;
    private String sisend_tekst;

    private Button suhtle;

    private JFrame pilt_ala;
    private Image pilt;

    public KasutajaAken () {
        setLayout(new GridLayout(6,1,2,2));
        // layout vasakult paremale, read üksteise all

        silt_vastus = new Label(" Meister: ");
        add(silt_vastus);
        vastus = new TextField(vastus_tekst,3);
        vastus.setEditable(false);
        vastus.setBackground(new Color(191,255,244));
        add(vastus);

        silt_sisend = new Label(" Sina: ");
        add(silt_sisend);
        sisend = new TextField(sisend_tekst,3);
        sisend.setBackground(new Color(191,255,244));
        add(sisend);

        suhtle = new Button(" Suhtle ");
        suhtle.setBackground(new Color(208,239,160));
        add(suhtle);

        setSize(410,220);
        setTitle("Java Projekt 2001: Zen Meister");
        setBackground(new Color(244,164,164));

        aken_ikoon = Toolkit.getDefaultToolkit().getImage("meistriikoon.png");
        setIconImage(aken_ikoon);

        setVisible(true);
    }

    public static void main(String[] args) {
        KasutajaAken programm = new KasutajaAken();
    }


}