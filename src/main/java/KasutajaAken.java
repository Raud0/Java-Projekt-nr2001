import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class KasutajaAken extends JFrame {

    private Image aken_ikoon;

    private JPanel pildi_paneel;
    private JPanel suhtlus_paneel;

    private JLabel silt_vastus;
    private JTextField vastus;
    private String vastus_tekst;
    public String getVastus_tekst() { return vastus_tekst; }
    public void setVastus_tekst(String vastus_tekst) { this.vastus_tekst = vastus_tekst; }

    private JLabel silt_sisend;
    private JTextField sisend;
    private String sisend_tekst;
    public String getSisend_tekst() { return sisend_tekst; }

    public void setSisend_tekst(String sisend_tekst) { this.sisend_tekst = sisend_tekst; }

    private JButton suhtle;

    private JLabel pilt_ala;
    private BufferedImage pilt;

    public KasutajaAken (String algtekst) {
        setVastus_tekst(algtekst);
        pildi_paneel = new JPanel();
        pildi_paneel.setLayout(new GridLayout(1,1,1,1));
        suhtlus_paneel = new JPanel();
        suhtlus_paneel.setLayout(new GridLayout(5,1,2,2));

        try {
            pilt = ImageIO.read(new File("whale-fall.png"));
            //pildid peaksid olema laiusega 380
            pilt_ala = new JLabel(new ImageIcon(pilt));
            pildi_paneel.add(pilt_ala);
            pildi_paneel.setMaximumSize(new Dimension(410,410));
        } catch(IOException e) {
            e.printStackTrace(); //ma ei tea miks, aga kuskil oli nii?
        }



        silt_vastus = new JLabel(" Master: ");
        suhtlus_paneel.add(silt_vastus);
        vastus = new JTextField(vastus_tekst,3);
        vastus.setEditable(false);
        vastus.setBackground(new Color(191,255,244));
        suhtlus_paneel.add(vastus);

        silt_sisend = new JLabel(" Student: ");
        suhtlus_paneel.add(silt_sisend);
        sisend = new JTextField(sisend_tekst,3);
        sisend.setBackground(new Color(191,255,244));
        suhtlus_paneel.add(sisend);

        suhtle = new JButton(" Speak ");
        suhtle.setBackground(new Color(208,239,160));
        suhtlus_paneel.add(suhtle);

        suhtlus_paneel.setSize(410,220);
        pildi_paneel.setSize(pilt.getWidth(),pilt.getHeight());
        add(pildi_paneel,BorderLayout.BEFORE_FIRST_LINE);
        add(suhtlus_paneel);
        setSize(410,suhtlus_paneel.getHeight()+pildi_paneel.getHeight());

        setTitle("Java Projekt 2001: Zen Master");
        setBackground(new Color(244,164,164));

        aken_ikoon = Toolkit.getDefaultToolkit().getImage("meistriikoon.png");
        setIconImage(aken_ikoon);

        setVisible(true);
    }

    /*public static void main(String[] args) {
        KasutajaAken programm = new KasutajaAken();
    }*/

}